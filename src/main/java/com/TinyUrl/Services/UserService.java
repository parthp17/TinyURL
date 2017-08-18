package com.TinyUrl.Services;

import com.TinyUrl.Models.User;

public interface UserService {
	User getByEmail(String email);
	User save(User user);
	Boolean isUser(String email);
}
