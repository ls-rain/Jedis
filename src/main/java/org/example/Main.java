package org.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        try(JedisPool jedisPool = new JedisPool("127.0.0.1", 6379)){
            try(Jedis jedis = jedisPool.getResource()){
                jedis.setbit("request-somepage-20240503", 100, true);
                jedis.setbit("request-somepage-20240503", 200, true);
                jedis.setbit("request-somepage-20240503", 300, true);

                System.out.println(jedis.getbit("request-somepage-20240503", 100));
                System.out.println(jedis.getbit("request-somepage-20240503", 50));

                System.out.println(jedis.bitcount("request-somepage-20240503"));

                //bitmap vs set 메모리 사용률
                Pipeline pipelined = jedis.pipelined();
                IntStream.rangeClosed(0, 100000).forEach(i -> pipelined.sadd("request-somepage-20240502", String.valueOf(i), "1"));
            }
        }
    }
}