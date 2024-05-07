package com.business.model.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.business.auth.entity.LoginUser;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * 千帆大模型AI AccessToken缓存 DAO
 * @Author yxf
 */

@Repository
public class AiAccessTokenRedisDAO {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * redis Key
     */
    public static final String AI_ACCESS_TOKEN = "ai_access_token";
    /**
     * 默认过期时间 10天
     */
    public static final Long EX_TIME = 864000L;

    /**
     * 获取redis缓存中的AccessToken
     * @return AccessToken
     */
    public String get(){
        return stringRedisTemplate.opsForValue().get(AI_ACCESS_TOKEN);
    }

    /**
     * 设置redis中AccessToken缓存
     * @param token token
     */
    public void set(String token){
        stringRedisTemplate.opsForValue().set(AI_ACCESS_TOKEN, token, EX_TIME, TimeUnit.SECONDS);
    }


}
