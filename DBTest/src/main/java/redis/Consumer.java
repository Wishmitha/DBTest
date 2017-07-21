package redis;

import redis.clients.jedis.Jedis;

/**
 * Created by wishmitha on 7/14/17.
 */
public class Consumer implements Runnable{

    String name;
    Jedis jedis = RedisTest.pool.getResource();

    public static int consumedID;

    public Consumer(String name){
        this.name=name;
        generateConsumedID();
    }

    public void consume() {

        synchronized (Consumer.class){
            for (int j=consumedID;j<Producer.messageID;j++){

                if(jedis.get(Integer.toString(j))!=null){
                    consumedID = Integer.parseInt(jedis.get("cid"));
                    consumedID++;
                    jedis.set("cid",Integer.toString(consumedID));
                    //System.out.println(this.name+" consumes "+"\""+jedis.get(Integer.toString(j))+"\"");
                    jedis.del(Integer.toString(j));
                }


            }
        }


    }

    public void run() {
        try {

            consume();

        }finally {

            jedis.close();

        }

    }

    private void generateConsumedID(){
        if(jedis.get("cid")==null) {
            jedis.set("cid", "0");
        }

        consumedID = Integer.parseInt(jedis.get("cid"));
    }



}
