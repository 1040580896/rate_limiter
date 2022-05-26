package com.th.rate_limiter.enums;

/**
 * @program: rate_limiter
 * @description:
 * @author: xiaokaixin
 * @create: 2022-05-26 07:48
 **/

/**
 * 限流类型
 */
public enum LimitType {

    /**
     * 默认的限流侧月，针对某一个接口进行限流
     */
    Default,
    /**
     * 针对某一个 IP 进行限流
     */
    IP
}
