package org.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        try(JedisPool jedisPool = new JedisPool("127.0.0.1", 6379)){
            try(Jedis jedis = jedisPool.getResource()){
                jedis.hset("users:2:info", "name", "kim");
                HashMap<String, String> userInfo = new HashMap<>();
                userInfo.put("email", "kim@naver.com");
                userInfo.put("phone", "010-xxxx-xxxx");

                jedis.hset("users:2:info", userInfo);

                jedis.hdel("users:2:info", "phone");

                System.out.println(jedis.hget("users:2:info", "email"));
                Map<String, String> user2Info = jedis.hgetAll("users:2:info");
                user2Info.forEach((k, v) -> System.out.println(k + ' ' + v));

                jedis.hincrBy("users:2:info", "visits",31);
            }
        }
    }
}