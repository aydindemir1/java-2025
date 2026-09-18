package com.aydindemir.redis.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aydindemir.redis.dto.CollectionValueRequest;
import com.aydindemir.redis.dto.HashFieldRequest;
import com.aydindemir.redis.dto.SortedSetRequest;
import com.aydindemir.redis.service.RedisDataStructureService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/redis/data-structures")
@Tag(name = "02 - Hash / List / Set / Sorted Set")
public class RedisDataStructuresController {
	private final RedisDataStructureService s;

	public RedisDataStructuresController(RedisDataStructureService s) {
		this.s = s;
	}

	@PutMapping("/hash")
	public void hp(@Valid @RequestBody HashFieldRequest r) {
		s.hashPut(r.key(), r.field(), r.value());
	}

	@GetMapping("/hash/{key}")
	public Map<Object, Object> hg(@PathVariable String key) {
		return s.hashEntries(key);
	}

	@PostMapping("/list")
	public void lp(@Valid @RequestBody CollectionValueRequest r) {
		s.listLeftPush(r.key(), r.value());
	}

	@GetMapping("/list/{key}")
	public Object lg(@PathVariable String key) {
		return s.listRange(key, 0, -1);
	}

	@PostMapping("/set")
	public void sa(@Valid @RequestBody CollectionValueRequest r) {
		s.setAdd(r.key(), r.value());
	}

	@GetMapping("/set/{key}")
	public Object sg(@PathVariable String key) {
		return s.setMembers(key);
	}

	@PostMapping("/sorted-set")
	public void za(@Valid @RequestBody SortedSetRequest r) {
		s.sortedSetAdd(r.key(), r.member(), r.score());
	}

	@GetMapping("/sorted-set/{key}")
	public Object zt(@PathVariable String key, @RequestParam(defaultValue = "10") long count) {
		return s.sortedSetTop(key, count);
	}
}
