package mysql;

import redis.RedisTest;
import redis.clients.jedis.Jedis;

import java.sql.*;

/**
 * Created by wishmitha on 7/14/17.
 */
public class Producer implements Runnable {

    private String name;
    Statement statement = MySQLTest.statement;

    public static int messageID;

    public Producer(String name) throws SQLException {

        this.name=name;
        generateMessageID();
    }

    public void produce() throws SQLException {

        synchronized (Producer.class){
            for (int i=0;i<100;i++){

                Message msg = new Message(messageID,"\'This is message no. "+Integer.toString(messageID)+"\'");
                messageID++;
                statement.execute("INSERT INTO produced_messages (produced_id, content) VALUES"+"("+Integer.toString(messageID)+","+msg.getPayload()+")");
                //System.out.println(this.name + " produces \" "+msg.getPayload()+"\"");
            }
        }

    }


    public void generateMessageID() throws SQLException {
        ResultSet rs1 = statement.executeQuery("SELECT produced_id FROM produced_messages ORDER BY produced_id DESC LIMIT 1");
        while (rs1.next()){
            messageID = Integer.parseInt(rs1.getString("produced_id"));
        }
        if(rs1 == null) {
            messageID = 0;
        }
    }

    public void run() {
        try {
            produce();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



}
