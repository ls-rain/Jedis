package org.example;

import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.args.GeoUnit;
import redis.clients.jedis.params.GeoSearchParam;
import redis.clients.jedis.resps.GeoRadiusResponse;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        try(JedisPool jedisPool = new JedisPool("127.0.0.1", 6379)){
            try(Jedis jedis = jedisPool.getResource()){
                jedis.geoadd("stores:1:geo", 127.02985530619755, 37.49911212874, "some1");
                jedis.geoadd("stores:1:geo", 127.0333352287619, 37.491921163986234, "some2");

                Double geodist = jedis.geodist("stores:1:geo", "some1", "some2");
                System.out.println(geodist);

                /*List<GeoRadiusResponse> geosearch = jedis.geosearch(
                        "stores:1:geo",
                        new GeoCoordinate(127.031, 37.495),
                        100,
                       GeoUnit.M
                );
                */
                List<GeoRadiusResponse> geosearch = jedis.geosearch(
                        "stores:1:geo",
                        new GeoSearchParam()
                                .fromLonLat(new GeoCoordinate(127.031, 37.495))
                                .byRadius(500, GeoUnit.M)
                                .withCoord()
                );
                geosearch.forEach(geo -> System.out.println(geo.getMemberByString()
                                                        + ' '
                                                        + geo.getCoordinate().getLatitude()
                                                        + ' '
                                                        + geo.getCoordinate().getLongitude()));
                jedis.unlink("stores:1:geo");
            }
        }
    }
}