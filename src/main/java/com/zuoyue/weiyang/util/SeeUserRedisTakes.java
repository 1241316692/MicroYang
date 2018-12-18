//package com.zuoyue.weiyang.util;
//
//import com.zuoyue.weiyang.bean.RedisLogin;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.List;
//import java.util.logging.Logger;
//
///**
// * Created by zxm on 2017/8/12.
// *
// */
//@Component("seeUserRedisTakes")
//public class SeeUserRedisTakes implements RedisBaiseTakes<String,String,RedisLogin>{
//    @Resource(name="RedisLogin")
//    private RedisTemplate redisTemplate;
//
//    private Logger logger = Logger.getLogger(String.valueOf(SeeUserRedisTakes.class));
//    @Override
//    public void add(String key, String value) {
//        if(redisTemplate==null){
//            logger.warning("redisTemplate 实例化失败");
//            return;
//        }else{
//            redisTemplate.opsForValue().set(key,value);
//        }
//    }
//
//    @Override
//    public void addObj(String objectKey, String key, RedisLogin object) {
//        if(redisTemplate==null){
//            logger.warning("redisTemplate 实例化失败");
//            return;
//        }else{
//            redisTemplate.opsForHash().put(objectKey,key,object);
//        }
//    }
//
//    @Override
//    public void delete(String key) {
//
//    }
//
//    @Override
//    public void delete(List<String> listKeys) {
//
//    }
//
//    @Override
//    public void deletObj(String objecyKey, String key) {
//
//    }
//
//    @Override
//    public void update(String key, String value) {
//
//    }
//
//    @Override
//    public void updateObj(String objectKey, String key, RedisLogin object) {
//
//    }
//
//    @Override
//    public String get(String key) {
//        String value = (String) redisTemplate.opsForValue().get(key);
//        return value;
//    }
//
//    @Override
//    public RedisLogin getObj(String objectKey, String key) {
//        RedisLogin seeUser = (RedisLogin) redisTemplate.opsForHash().get(objectKey,key);
//        return seeUser;
//    }
//}
