package com.th.rate_limiter.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: rate_limiter
 * @description:
 * @author: xiaokaixin
 * @create: 2022-05-26 17:19
 **/
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(RateLimitException.class)
    public Map<String, Object> rateLimitException(RateLimitException e) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", 500);
        map.put("message", e.getMessage());
        return map;
    }
}
