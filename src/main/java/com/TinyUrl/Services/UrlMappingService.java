package com.TinyUrl.Services;

import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import com.TinyUrl.Models.UrlMapping;

public interface UrlMappingService {

	@Cacheable(value = "UrlMapping", key = "#UrlMapping.newUrl")
	UrlMapping getByNewUrl(String sNewUrl);
	
	@CachePut(value="UrlMapping", key = "#UrlMapping.newUrl")
	UrlMapping save(UrlMapping urlMapping);

	UrlMapping findByOldUrl(String sOldUrl);
	List<UrlMapping> findByEmail(String email);
}