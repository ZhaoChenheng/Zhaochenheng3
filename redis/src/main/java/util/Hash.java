package util;

import dataobject.Post;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

public class Hash
{
    private static Jedis jedis;
    public static void main(String[] args){
        jedis = new Jedis("127.0.0.1",6379);
        Post post = new Post();
        post.setTitle("This is title");
        post.setAuthor("ZCH");
        post.setContent("This is my content");
        Long postId = savePost(post,jedis);
        System.out.println("保存成功");
        Post myPost = getPost(postId,jedis);
        System.out.println(myPost.getTitle());
        System.out.println(myPost.getAuthor());
        System.out.println(myPost.getContent());
        System.out.println(myPost);
    }

    public static Long savePost(Post post, Jedis jedis){
        Long postId = jedis.incr("posts");
        Map<String,String> blog = new HashMap<String,String>();
        blog.put("title",post.getTitle());
        blog.put("author",post.getAuthor());
        blog.put("content",post.getContent());
        jedis.hmset("post:"+postId+":data",blog);
        return postId;
    }


    public static Post getPost(Long postId,Jedis jedis){
        Map<String,String> myBlog = jedis.hgetAll("post:"+postId+":data");
        Post post = new Post();
        post.setTitle(myBlog.get("title"));
        post.setAuthor(myBlog.get("author"));
        post.setContent(myBlog.get("content"));
        return post;
    }

    public static void updatePost(Long postId, Jedis jedis){
        Map<String,String> myBlog = jedis.hgetAll("post:"+postId+":data");
        myBlog.put("title","This is new title");
        jedis.hmset("post:"+postId+":data",myBlog);
        System.out.println(jedis.hgetAll("post"+postId+":date"));
        System.out.println("修改完成");
    }

    public static void delPost(Long postId, Jedis jedis){
        jedis.hdel("post:"+postId,"author");
        jedis.lrem("list:post",1,"post:"+postId);
    }
}
