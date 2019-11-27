package util;

import dataobject.Post;
import redis.clients.jedis.Jedis;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestRedis {
    private static Jedis jedis;
    static {
        try
        {
            jedis = new Jedis("127.0.0.1",6379);
            System.out.println(jedis);
        }catch (Exception e)
        {

        }finally
        {
            if(jedis !=null)
            {
                jedis.close();
            }
        }

    }

    public static void main(String[] args)
    {
        System.out.println(jedis.get("hello"));

        Map<String,String> map = new HashMap<String, String>();
        map.put("name","张三");
        map.put("age","20");
        map.put("sex","男");
        jedis.hmset("map",map);
        List<String> list = jedis.hmget("map","name","age","sex");
        System.out.println(list);

        Post post = new Post();
    }
}
