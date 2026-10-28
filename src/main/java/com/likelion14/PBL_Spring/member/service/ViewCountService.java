package com.likelion14.PBL_Spring.member.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class ViewCountService {

    private static final Duration TTL = Duration.ofMinutes(10);

    private final StringRedisTemplate redisTemplate;

    public ViewCountService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private String key(Long assignmentId) {
        return "assignment:" + assignmentId + ":viewCount";
    }

    // 조회수 1 증가 후 현재 값 반환
    public Long increase(Long assignmentId) {
        String key = key(assignmentId);
        Long count = redisTemplate.opsForValue().increment(key);

        // 키가 처음 만들어졌을 때만 TTL 설정
        if (count != null && count == 1L) {
            redisTemplate.expire(key, TTL);
        }
        return count;
    }

    // 현재 조회수만 조회
    public Long get(Long assignmentId) {
        String value = redisTemplate.opsForValue().get(key(assignmentId));
        return value == null ? 0L : Long.parseLong(value);
    }
}