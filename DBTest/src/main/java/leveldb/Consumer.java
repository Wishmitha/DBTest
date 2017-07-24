package leveldb;

import org.iq80.leveldb.DB;

import static org.fusesource.leveldbjni.JniDBFactory.asString;
import static org.fusesource.leveldbjni.JniDBFactory.bytes;

/**
 * Created by wishmitha on 7/14/17.
 */
public class Consumer implements Runnable{

    String name;
    DB db = LDBTest.db;

    public static int consumedID;

    public Consumer(String name){
        this.name=name;
        generateConsumedID();
    }

    public void consume() {

        synchronized (Consumer.class){
            for (int j=consumedID;j<Producer.messageID;j++){

                if(db.get(bytes(Integer.toString(j)))!=null){
                    consumedID = Integer.parseInt(asString(db.get(bytes("cid"))));
                    //db.put(bytes("Tampa"), bytes("rocks"));
                    consumedID++;
                    db.put(bytes("cid"),bytes(Integer.toString(consumedID)));
                    //System.out.println(this.name+" consumes "+"\""+asString(db.get(bytes(Integer.toString(j))))+"\"");
                    db.delete(bytes(Integer.toString(j)));
                }


            }
        }


    }

    public void run() {
        consume();
    }

    private void generateConsumedID(){
        if(db.get(bytes("cid"))==null) {
            db.put(bytes("cid"), bytes("0"));
        }

        consumedID = Integer.parseInt(asString(db.get(bytes("cid"))));
    }



}
