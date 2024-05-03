package org.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.resps.Tuple;

import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        try(JedisPool jedisPool = new JedisPool("127.0.0.1", 6379)){
            try(Jedis jedis = jedisPool.getResource()){
                HashMap<String, Double> scores = new HashMap<>();
                scores.put("user1", 100.0);
                scores.put("user2", 30.0);
                scores.put("user3", 50.0);
                scores.put("user4", 80.0);
                scores.put("user5", 15.0);
                jedis.zadd("game:2:scores", scores);

                List<String> zrange = jedis.zrange("game:2:scores", 0, Long.MAX_VALUE);
                zrange.forEach(System.out::println);
                List<Tuple> tuples = jedis.zrangeWithScores("game:2:scores", 0, Long.MAX_VALUE);
                tuples.forEach(System.out::println);

                System.out.println(jedis.zcard("game:2:scores"));

                jedis.zincrby("game:2:scores", 100.0, "user5");
            }
        }
    }
}