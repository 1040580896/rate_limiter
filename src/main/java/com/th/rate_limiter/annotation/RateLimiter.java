package com.th.rate_limiter.annotation;

import com.th.rate_limiter.enums.LimitType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author xiaokaixin
 * @Date 2022/5/26 07:50
 * @Version 1.0
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RateLimiter {

    /**
     * 限流的key，主要是指前缀
     * @return
     */
    String key() default "rate_limit";

    /**
     * 限流时间窗
     * @return
     */
    int time() default 60;


    /**
     * 在时间窗限流次数
     * @return
     */
    int count() default 100;


    /**
     * 限流的类型
     * @return
     */
    LimitType limitType() default LimitType.Default;


}
