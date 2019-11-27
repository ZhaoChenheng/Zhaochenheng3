package util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Transaction;

import java.util.List;

public class pipeline {
    public static void main(java.lang.String[] args)
    {
    }

    public static java.lang.String mset(){
        Jedis jedis = new Jedis("127.0.0.1");
        jedis.mset("name","Zch","age","20","addr","HangZhou");
        List<java.lang.String> list = jedis.mget("name","age","addr");
        return null;
    }

    public static void mdel(List<java.lang.String> keys){
        Jedis jedis = new Jedis("127.0.0.1");
        Pipeline pipeline = jedis.pipelined();
        for (java.lang.String key:keys){
            pipeline.del(key);
        }
        pipeline.sync();
    }


    public static  void syncAndReturnAll(){
        Jedis jedis = new Jedis("127.0.0.1");
        Pipeline pipeline = jedis.pipelined();
        pipeline.set("helld","world");
        pipeline.incr("counter");
        List<Object> resultList = pipeline.syncAndReturnAll();
        for (Object object:resultList){
            System.out.println(object);
        }

    }


    public static int doubleAccount(Jedis jedis, java.lang.String userId){
        java.lang.String key = keyFor(userId);
        while (true){
            jedis.watch(key);
            int value = Integer.parseInt(jedis.get(key));
            value = 2;
            Transaction tx = jedis.multi();
            tx.set(key, java.lang.String.valueOf(2));
            List<Object> res = tx.exec();
            if(res!=null){
                break;
            }
        }
      return Integer.parseInt(jedis.get(key));
    }

    public static java.lang.String keyFor(java.lang.String userId){
        return java.lang.String.format("account_()",userId);
    }
}
