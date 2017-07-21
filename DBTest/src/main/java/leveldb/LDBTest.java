package leveldb;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;

import java.io.File;
import java.io.IOException;

import static org.fusesource.leveldbjni.JniDBFactory.factory;

/**
 *
 * Created by wishmitha on 7/14/17.
 */


public class LDBTest {

    public static DB db;

    public static String execute() throws IOException, InterruptedException {

        Options options = new Options();
        db = factory.open(new File("ldb"), options);

        long initTime = System.currentTimeMillis();
        /*int initConsumeID = Integer.parseInt(asString(db.get(bytes("cid"))));
        int initMessageID = Integer.parseInt(asString(db.get(bytes("mid"))));*/

        for (int i=1;i<=1000;i++) {
            (new Thread(new Producer("Producer" + Integer.toString(i)))).start();
            (new Thread(new Consumer("Consumer" + Integer.toString(i)))).start();
        }

        System.out.println(System.currentTimeMillis()-initTime);
        System.out.println(Consumer.consumedID);
        System.out.println(Producer.messageID);

        Thread.sleep(10);

        return "Execution Completed";

    }


}
