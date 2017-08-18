package com.TinyUrl.Repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.TinyUrl.Models.UrlMapping;
import com.datastax.driver.core.utils.UUIDs;

public interface UrlRepository extends CrudRepository<UrlMapping, String>{

	@Query("Select * from UrlMap where oldUrl=?0")
	public UrlMapping findByOldUrl(String sOldUrl);
	
	@Query("Select * from UrlMap where email=?0 ALLOW FILTERING")
	public List<UrlMapping> findByEmail(String email);
	
}
