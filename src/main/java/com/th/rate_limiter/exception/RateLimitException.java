package com.th.rate_limiter.exception;

/**
 * @program: rate_limiter
 * @description:
 * @author: xiaokaixin
 * @create: 2022-05-26 17:18
 **/
public class RateLimitException extends Exception{

    public RateLimitException(String message) {
        super(message);
    }
}
