package com.aydindemir.redis.redis.pubsub;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RedisSubscriber {
	private static final Logger log = LoggerFactory.getLogger(RedisSubscriber.class);
	private final List<String> messages = new CopyOnWriteArrayList<>();

	public void onMessage(String m, String c) {
		messages.add(m);
		log.info("Redis Pub/Sub received channel={} message={}", c, m);
	}

	public List<String> receivedMessages() {
		return List.copyOf(messages);
	}
}
