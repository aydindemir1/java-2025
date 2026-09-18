package com.aydindemir.redis.config;

import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import com.aydindemir.redis.redis.pubsub.RedisSubscriber;

@Configuration
public class PubSubConfig {
	public static final String DEMO_CHANNEL = "java40:pubsub:demo";

	@Bean
	ChannelTopic demoTopic() {
		return new ChannelTopic(DEMO_CHANNEL);
	}

	@Bean
	RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory cf, RedisSubscriber sub,
			ChannelTopic topic) {
		RedisMessageListenerContainer c = new RedisMessageListenerContainer();
		c.setConnectionFactory(cf);
		c.addMessageListener((m, p) -> sub.onMessage(new String(m.getBody(), StandardCharsets.UTF_8),
				new String(m.getChannel(), StandardCharsets.UTF_8)), topic);
		return c;
	}
}
