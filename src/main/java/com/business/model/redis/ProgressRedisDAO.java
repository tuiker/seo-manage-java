package com.business.model.redis;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * 博客生成进度 缓存 DAO
 * @Author yxf
 */

@Repository
public class ProgressRedisDAO {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * redis Key 分子（当前已生成多少篇博客）
     */
    public static final String PROGRESS_NUMERATOR = "progress_numerator";
    /**
     * redis key 分母（总共需要生成多少篇博客）
     */
    public static final String PROGRESS_DENOMINATOR = "progress_denominator";
    /**
     * 默认过期时间 1天
     */
    public static final Long EX_TIME = 86400L;

    /**
     * 获取redis缓存中的博客生成进度分子
     */
    public String getNumerator(){
        return stringRedisTemplate.opsForValue().get(PROGRESS_NUMERATOR);
    }

    /**
     * 设置redis中博客生成进度分子缓存
     */
    public void setNumerator(){
        stringRedisTemplate.opsForValue().set(PROGRESS_NUMERATOR, "0", EX_TIME, TimeUnit.SECONDS);
    }

    /**
     * 对redis中博客生成进度分子进行自增操作
     */
    public void incrNumerator(){
        stringRedisTemplate.opsForValue().increment(PROGRESS_NUMERATOR);
    }

    /**
     * 获取redis缓存中的博客生成进度分母
     */
    public String getDenominator(){
        return stringRedisTemplate.opsForValue().get(PROGRESS_DENOMINATOR);
    }

    /**
     * 设置redis中博客生成进度分母缓存
     */
    public void setDenominator(Integer denominator){
        stringRedisTemplate.opsForValue().set(PROGRESS_DENOMINATOR, String.valueOf(denominator), EX_TIME, TimeUnit.SECONDS);
    }

    /**
     * 移除redis中博客生成进度缓存
     */
    public void removeProgress(){
        stringRedisTemplate.delete(PROGRESS_NUMERATOR);
        stringRedisTemplate.delete(PROGRESS_DENOMINATOR);
    }
}
