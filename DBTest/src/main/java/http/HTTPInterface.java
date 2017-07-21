package http; /**
 * Created by wishmitha on 7/20/17.
 */

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import redis.RedisTest;

@Path("/db")
public class HTTPInterface {

    @GET
    @Path("/redis")
    @Produces("text/plain")
    public String executeRedis() throws InterruptedException {
        return RedisTest.execute();
    }

    @GET
    @Path("/ldb")
    @Produces("text/plain")
    public String executeLDB() throws InterruptedException {
        return RedisTest.execute();
    }

}