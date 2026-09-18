package com.aydindemir.redis.redis.cache;
import java.util.Set; import org.springframework.data.redis.core.*; import org.springframework.stereotype.Service;
@Service public class HotKeyDetectorService { private static final String K="java40:metrics:key-access"; private final StringRedisTemplate redis; public HotKeyDetectorService(StringRedisTemplate redis){this.redis=redis;}
 public void recordAccess(String logicalKey){redis.opsForZSet().incrementScore(K,logicalKey,1);} public Set<ZSetOperations.TypedTuple<String>> top(long c){return redis.opsForZSet().reverseRangeWithScores(K,0,Math.max(0,c-1));}
}
