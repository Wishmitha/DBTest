package redis;

import redis.clients.jedis.Jedis;

/**
 * Created by wishmitha on 7/14/17.
 */
public class Producer implements Runnable {

    private String name;
    Jedis jedis = RedisTest.pool.getResource();


    public static int messageID;

    public Producer(String name){

        this.name=name;
        generateMessageID();
    }

    public void produce(){

        synchronized (Producer.class){
            for (int i=0;i<100;i++){
                messageID = Integer.parseInt(jedis.get("mid"));
                Message msg = new Message(messageID,"This is message no. "+Integer.toString(messageID));
                messageID++;
                jedis.set("mid",Integer.toString(messageID));
                jedis.set(Integer.toString(msg.getId()),msg.getPayload());
                //System.out.println(this.name + " produces \" "+msg.getPayload()+"\"");
            }
        }

    }


    public void generateMessageID(){
        if(jedis.get("mid")==null){
            jedis.set("mid","0");
        }
    }

    public void run() {
        try {
            produce();
        }finally {
            jedis.close();
        }

    }



}
