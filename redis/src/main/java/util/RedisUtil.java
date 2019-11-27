package util;
import redis.clients.jedis.Jedis;

public class RedisUtil
{
    static
    {
        Jedis jedis = null;
        try
        {
            jedis = new Jedis("127.0.0.1",6379);
            jedis.get("hello");
        }catch(Exception e)
        {
//            logger.error(e.getMessage(),e);
        }finally{
            if(jedis !=null)
            {
                jedis.close();
            }
        }
    }
}
