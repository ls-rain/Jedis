package org.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        try(var jedisPool = new JedisPool("127.0.0.1", 6379)){
            try(Jedis jedis = jedisPool.getResource()){

                jedis.set("users:300:email", "seungwoo@naver.com");
                jedis.set("users:300:name", "kim00");
                jedis.set("users:300:age", "100");

                String userEmail = jedis.get("users:300:email");
                System.out.println(userEmail);

                List<String> userInfo = jedis.mget("users:300:email", "users:300:name", "users:300:age");
                userInfo.forEach(System.out::println);

//                long counter = jedis.incr("counter");
//                System.out.println(counter);
                long counter = jedis.incrBy("counter", 10L);
                System.out.println(counter);

                Pipeline pipelined = jedis.pipelined();
                pipelined.set("user:400:email", "seungwoo@gmail.com");
                pipelined.set("user:400:name", "seungwoo");
                pipelined.set("user:400:age", "26");
                List<Object> objects = pipelined.syncAndReturnAll();
                objects.forEach(i -> System.out.println(i.toString()));

            }
        }
    }
}