package com.aydindemir.redis.service;
import java.util.*; import org.springframework.data.redis.core.*; import org.springframework.stereotype.Service;
@Service public class RedisDataStructureService {
 private final StringRedisTemplate redis; public RedisDataStructureService(StringRedisTemplate redis){this.redis=redis;}
 public void hashPut(String k,String f,String v){redis.opsForHash().put(k,f,v);} public Map<Object,Object> hashEntries(String k){return redis.opsForHash().entries(k);}
 public void listLeftPush(String k,String v){redis.opsForList().leftPush(k,v);} public List<String> listRange(String k,long s,long e){return redis.opsForList().range(k,s,e);}
 public void setAdd(String k,String v){redis.opsForSet().add(k,v);} public Set<String> setMembers(String k){return redis.opsForSet().members(k);}
 public void sortedSetAdd(String k,String m,double score){redis.opsForZSet().add(k,m,score);} public Set<ZSetOperations.TypedTuple<String>> sortedSetTop(String k,long c){return redis.opsForZSet().reverseRangeWithScores(k,0,Math.max(0,c-1));}
}
