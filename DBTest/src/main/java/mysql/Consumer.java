package mysql;

import redis.clients.jedis.Jedis;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by wishmitha on 7/14/17.
 */
public class Consumer implements Runnable{

    String name;
    Statement statement = MySQLTest.statement;

    Jedis jedis = new Jedis();

    public static int consumedID;

    public Consumer(String name) throws SQLException {
        this.name=name;
        generateConsumedID();
    }

    public void consume() throws SQLException {

        synchronized (Consumer.class){
            for (int j = consumedID; j< Producer.messageID; j++){

                ResultSet rs2 = statement.executeQuery("SELECT consumed_id FROM consumed_messages ORDER BY consumed_id DESC LIMIT 1");

                if(rs2!=null){

                    while (rs2.next()){
                        consumedID = Integer.parseInt(rs2.getString("consumed_id"));
                    }
                    consumedID ++;

                    statement.execute("INSERT INTO consumed_messages (consumed_id) VALUES "+"("+Integer.toString(consumedID)+")");
                    //System.out.println(this.name+" consumes "+"\""+jedis.get(Integer.toString(j))+"\"");
                    //statement.execute("DELETE FROM produced_messages WHERE produced_id ="+Integer.toString(consumedID-1));
                }


            }
        }


    }

    public void run() {
        try {

            consume();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void generateConsumedID() throws SQLException {
        ResultSet rs1 = statement.executeQuery("SELECT consumed_id FROM consumed_messages ORDER BY consumed_id DESC LIMIT 1");
        while (rs1.next()){
            consumedID = Integer.parseInt(rs1.getString("consumed_id"));
        }
        if(rs1 == null) {
            consumedID = 0;
        }
    }



}
