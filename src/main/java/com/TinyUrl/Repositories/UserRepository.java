package com.TinyUrl.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.TinyUrl.Models.User;

public interface UserRepository extends CrudRepository<User, String> {	
	
}