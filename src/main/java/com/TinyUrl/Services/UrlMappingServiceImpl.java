package com.TinyUrl.Services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TinyUrl.Models.UrlMapping;
import com.TinyUrl.Repositories.UrlRepository;
import com.datastax.driver.core.utils.UUIDs;

@Service
public class UrlMappingServiceImpl implements UrlMappingService{

	@Autowired
	private UrlRepository urlMapRepo;
	
	@Override
	public UrlMapping getByNewUrl(String sNewUrl) {
		UrlMapping url = urlMapRepo.findOne(sNewUrl);
		return url;
	}

	@Override
	public UrlMapping save(UrlMapping urlMapping) {
		UrlMapping url = urlMapRepo.save(urlMapping);
		return url;
	}

	@Override
	public UrlMapping findByOldUrl(String sOldUrl) {
		try
		{
			return urlMapRepo.findByOldUrl(sOldUrl);
		}
		catch (Exception e){
			return null;	
		}
		
	}

	@Override
	public List<UrlMapping> findByEmail(String email) {
		return urlMapRepo.findByEmail(email);
	}
}
