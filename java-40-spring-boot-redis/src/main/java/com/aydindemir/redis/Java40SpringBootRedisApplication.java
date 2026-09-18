package com.aydindemir.redis;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
@EnableCaching
@SpringBootApplication
public class Java40SpringBootRedisApplication {
    public static void main(String[] args) { SpringApplication.run(Java40SpringBootRedisApplication.class, args); }
}
