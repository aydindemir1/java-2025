package com.aydindemir.redis.redis.stream;

import java.util.List;
import java.util.Map;

import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.connection.stream.StreamReadOptions;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisStreamService {
	public static final String STREAM_KEY = "java40:stream:events";
	private final StringRedisTemplate redis;

	public RedisStreamService(StringRedisTemplate r) {
		redis = r;
	}

	public RecordId publish(Map<String, String> fields) {
		return redis.opsForStream().add(StreamRecords.mapBacked(fields).withStreamKey(STREAM_KEY));
	}

	public List<MapRecord<String, Object, Object>> readFromStart(long count) {
		return redis.opsForStream().read(StreamReadOptions.empty().count(count), StreamOffset.fromStart(STREAM_KEY));
	}

	public Long size() {
		return redis.opsForStream().size(STREAM_KEY);
	}
}
