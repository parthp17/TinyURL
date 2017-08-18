package com.TinyUrl.Services;

public interface SecurityService {

	String findLoggedInEmail();

	void autoLogin(String email, String password);

}
