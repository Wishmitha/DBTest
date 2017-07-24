package mysql;

import com.sun.java.browser.plugin2.liveconnect.v1.Result;

import java.sql.*;

/**
 * Created by wishmitha on 7/24/17.
 */
public class MySQLTest {

    public static Statement statement;

    public static String mysqlTest() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = null;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/queue", "root", "wolfpack123");
        statement = conn.createStatement();

        /*statement.execute("INSERT INTO produced_messages (produced_id,content) VALUES (1,'test')");
        ResultSet resultSet = statement.executeQuery("SELECT * FROM  produced_messages WHERE produced_id=1");
        String st1=null,st2=null;
        while (resultSet.next()){
            st1 = resultSet.getString("produced_id");
            st2 = resultSet.getString("content");
        }*/

        Thread t1 = new Thread(new Producer("mysql.Producer"));
        Thread t2 = new Thread(new Consumer("mysql.Consumer"));

        t1.start();
        t2.start();
        //conn.close();

        return "Execution Completed";

    }
}
