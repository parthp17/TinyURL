package com.TinyUrl.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.TinyUrl.Models.Role;

public interface RoleRepository extends CrudRepository<Role, String>{
	
	Role getByName(String role);
}
