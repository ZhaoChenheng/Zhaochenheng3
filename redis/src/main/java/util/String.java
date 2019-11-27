package util;

import com.alibaba.fastjson.JSON;
import dataobject.Article;
import redis.clients.jedis.Jedis;

public class String {
    private static Jedis jedis;
    public static Long save(Jedis jedis, Article article){
        String articleJson=JSON.toJSONString(article);
        Long articleId=jedis.incr("article");
        jedis.set("article"+articleId+"Data",articleJson);
        return articleId;
    }
    public static void del(Jedis jedis,Integer id){

        jedis.del("article"+id+"Data");
    }
    public static void update(Jedis jedis,Integer id){

        String articlejson=jedis.get("article"+id+"Data");
        Article article=JSON.parseObject(articlejson,Article.class);
        article.setTittle("handsome");
        article.setContent("jake");
        String post=JSON.toJSONString(article);
        jedis.set("article"+id+"Data",post);
    }
    public static Article find(Jedis jedis,Integer id){

        String articlejson=jedis.get("article"+id+"Data");
        Article article=JSON.parseObject(articlejson,Article.class);
        return article;
    }

}
