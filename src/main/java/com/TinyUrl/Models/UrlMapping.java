package com.TinyUrl.Models;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import com.datastax.driver.core.utils.UUIDs;


@Table("UrlMap")
public class UrlMapping implements Serializable{

	@PrimaryKey
	private String newUrl;
	private String oldUrl;
	private String email;
	
	public UrlMapping()
	{
		
	}
	
	public UrlMapping(String sNewUrl, String sOldUrl, String email)
	{
		this.newUrl = sNewUrl;
		this.oldUrl = sOldUrl;
		this.email = email;
	}
	
	public String getNewUrl() {
		return newUrl;
	}
	public void setNewUrl(String newUrl) {
		this.newUrl = newUrl;
	}
	public String getOldUrl() {
		return oldUrl;
	}
	public void setOldUrl(String oldUrl) {
		this.oldUrl = oldUrl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
		
}
