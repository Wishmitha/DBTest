package http; /**
 * Created by wishmitha on 7/20/17.
 */

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import leveldb.LDBTest;
import mysql.MySQLTest;
import redis.RedisTest;

/*import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;*/

import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import static org.fusesource.leveldbjni.JniDBFactory.factory;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


@Path("/db")
public class HTTPInterface {

    public static DB db;

    //public static JedisPool pool;

    @GET
    @Path("/")
    @Produces("text/plain")
    public String init() throws IOException {
        return "";
    }

    @GET
    @Path("/redis")
    @Produces("text/plain")
    public String executeRedis() throws InterruptedException {
        return RedisTest.execute();
    }

    @GET
    @Path("/ldb")
    @Produces("text/plain")
    public String executeLDB() throws InterruptedException, IOException {
        return LDBTest.execute();
    }

    @GET
    @Path("/mysql")
    @Produces("text/plain")
    public String executeMySQL() throws SQLException, ClassNotFoundException {
        return MySQLTest.mysqlTest();
    }
}