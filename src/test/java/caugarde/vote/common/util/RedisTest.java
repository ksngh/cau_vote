package caugarde.vote.common.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisServer;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @AfterEach
    public void tearDown() {
        // 모든 키 삭제
        stringRedisTemplate.getConnectionFactory().getConnection().flushDb();
    }

    @Test
    public void testSetAndGet() {
        // 데이터 저장
        stringRedisTemplate.opsForValue().set("myKey", "Hello, Docker Redis!");

        // 데이터 조회
        String value = stringRedisTemplate.opsForValue().get("myKey");

        // 검증
        assertEquals("Hello, Docker Redis!", value);
        System.out.println(stringRedisTemplate.opsForValue().get("myKey"));
    }
}