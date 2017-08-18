package com.TinyUrl.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TinyUrl.Models.Role;
import com.TinyUrl.Repositories.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleRepository roleRepo;
	@Override
	public Role getByName(String role) {
		roleRepo.getByName(role);
		return null;
	}
	
	

}
