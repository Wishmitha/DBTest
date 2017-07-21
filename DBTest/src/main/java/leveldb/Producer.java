package leveldb;

import org.iq80.leveldb.DB;

import static org.fusesource.leveldbjni.JniDBFactory.asString;
import static org.fusesource.leveldbjni.JniDBFactory.bytes;

/**
 * Created by wishmitha on 7/14/17.
 */
public class Producer implements Runnable {

    private String name;
    DB db = LDBTest.db;


    public static int messageID;

    public Producer(String name){

        this.name=name;
        generateMessageID();
    }

    public void produce(){

        synchronized (Producer.class){
            for (int i=0;i<100;i++){
                messageID = Integer.parseInt(asString(db.get(bytes("mid"))));
                Message msg = new Message(messageID,"This is message no. "+Integer.toString(messageID));
                messageID++;
                db.put(bytes("mid"),bytes(Integer.toString(messageID)));
                db.put(bytes(Integer.toString(msg.getId())),bytes(msg.getPayload()));
                //System.out.println(this.name + " produces \" "+msg.getPayload()+"\"");
            }
        }

    }


    public void generateMessageID(){
        if(db.get(bytes("mid"))==null){
            db.put(bytes("mid"),bytes("0"));
        }
    }

    public void run() {
        produce();
    }

}


