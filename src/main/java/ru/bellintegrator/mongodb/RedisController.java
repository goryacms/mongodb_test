package ru.bellintegrator.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
@CacheConfig(cacheNames = "redisVOCache")
public class RedisController {
    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = "/set", method = RequestMethod.POST)
    public RedisVO insert(@RequestBody RedisVO redisVO){
        mongoTemplate.insert(redisVO, "redisCollection");
        return redisVO;
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @Cacheable(keyGenerator="keyGenerator")
    public RedisVO get(@PathVariable Integer id){
        return mongoTemplate.findById(id, RedisVO.class, "redisCollection");
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @CachePut(keyGenerator="keyGenerator")
    public RedisVO update(@RequestBody RedisVO redisVO){
        mongoTemplate.save(redisVO, "redisCollection");
        return redisVO;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @CacheEvict(keyGenerator="keyGenerator")
    public String delete(@PathVariable Integer id){
        Query q = new Query();
        q.addCriteria(Criteria.where("keyID").is(id));

        mongoTemplate.findAndRemove(q, RedisVO.class, "redisCollection");

        return "Deleted by Id: " + id;
    }
}
