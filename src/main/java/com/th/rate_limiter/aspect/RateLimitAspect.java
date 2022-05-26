package com.th.rate_limiter.aspect;

import com.th.rate_limiter.annotation.RateLimiter;
import com.th.rate_limiter.enums.LimitType;
import com.th.rate_limiter.exception.RateLimitException;
import com.th.rate_limiter.utils.IpUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;

/**
 * @program: rate_limiter
 * @description:
 * @author: xiaokaixin
 * @create: 2022-05-26 17:21
 **/
@Aspect
@Component
public class RateLimitAspect {

    private static final Logger log = LoggerFactory.getLogger(RateLimitAspect.class);

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    RedisScript<Long> redisScript;

    @Before("@annotation(rateLimiter)")
    public void before(JoinPoint jp, RateLimiter rateLimiter) throws RateLimitException {
        int time = rateLimiter.time();
        int count = rateLimiter.count();
        //组合key
        String combineKey = getCombineKey(rateLimiter, jp);

        //调用
        try {
            Long number = redisTemplate.execute(redisScript, Collections.singletonList(combineKey), time, count);
            if (number == null || number.intValue() > count) {
                //超过限流阈值
                log.info("当前接口以达到最大限流次数");
                throw new RateLimitException("访问过于频繁，请稍后访问");
            }
            log.info("一个时间窗内请求次数：{}，当前请求次数：{}，缓存的 key 为 {}", count, number, combineKey);
        } catch (Exception e) {
            throw e;
        }


    }

    /**
     * 这个 key 就是接口调用次数缓存在 redis 中的 key
     * rate_limit:11.11.11.11-com.th.ratelimit.comtroller.HelloController-hello
     * rate_limit:com.th.ratelimit.comtroller.HelloController-hello
     *
     * @param rateLimiter
     * @param jp
     * @return
     */
    private String getCombineKey(RateLimiter rateLimiter, JoinPoint jp) {
        StringBuffer key = new StringBuffer(rateLimiter.key());
        if (rateLimiter.limitType() == LimitType.IP) {
            key.append(IpUtils.getIpAddr(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()))
                    .append("-");
        }
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        key.append(method.getDeclaringClass().getName())
                .append("-")
                .append(method.getName());
        return key.toString();
    }
}
