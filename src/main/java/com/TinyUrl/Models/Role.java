package com.TinyUrl.Models;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.mapping.Table;

@Table("Role")
public class Role {

	@Id
	private String name;

	public Role( String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
