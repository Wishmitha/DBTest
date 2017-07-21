package redis; /**
 * Created by wishmitha on 7/14/17.
 */
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class RedisTest {

    public static JedisPool pool;


    public static String execute() throws InterruptedException {

        createPool();

        Jedis jedis = pool.getResource();

        System.out.println("Connection to server sucessfully");
        //check whether server is running or not
        System.out.println("Server is running: "+jedis.ping());


        long initTime = System.currentTimeMillis();
        int initConsumeID = Integer.parseInt(jedis.get("cid"));
        int initMessageID = Integer.parseInt(jedis.get("mid"));

        /*for (int i=1;i<=100;i++){
            (new Thread(new Producer("redis.Producer"+Integer.toString(i)))).start();
            (new Thread(new Consumer("redis.Consumer"+Integer.toString(i)))).start();
        }*/

        (new Thread(new Producer("redis.Producer"))).start();
        (new Thread(new Consumer("redis.Consumer"))).start();

        /*System.out.println(System.currentTimeMillis()-initTime);
        System.out.println(Consumer.consumedID-initConsumeID);
        System.out.println(Producer.messageID-initMessageID);*/

        pool.destroy();

        Thread.sleep(10);

        return "Execution Completed";


    }

    private static void createPool(){
        pool = new JedisPool(new JedisPoolConfig(), "localhost");
    }
}
