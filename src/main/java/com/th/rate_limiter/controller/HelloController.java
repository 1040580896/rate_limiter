package com.th.rate_limiter.controller;

import com.th.rate_limiter.annotation.RateLimiter;
import com.th.rate_limiter.enums.LimitType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @program: rate_limiter
 * @description:
 * @author: xiaokaixin
 * @create: 2022-05-26 17:44
 **/
@RestController
public class HelloController {

    /**
     * 限流，10s之内，可以访问3次
     * @return
     */
    @GetMapping("/hello")
    @RateLimiter(time = 10,count = 3,limitType = LimitType.IP)
    public String hello(){
        return "hello";
    }
}
