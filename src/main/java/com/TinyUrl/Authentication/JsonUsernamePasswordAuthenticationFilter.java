package com.TinyUrl.Authentication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

public class JsonUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter{

	public JsonUsernamePasswordAuthenticationFilter() {
		super(new AntPathRequestMatcher("/api/login", "POST"));
		// TODO Auto-generated constructor stub
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException {
		
		if (!HttpMethod.POST.name().equals(request.getMethod())) {
            if(logger.isDebugEnabled()) {
                logger.debug("Authentication method not supported. Request method: " + request.getMethod());
            }
            throw new AuthenticationException("Authentication method not supported"){};
        }
		StringBuffer jb = new StringBuffer();
		String line =null;
		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null)
		      jb.append(line);
		
		JSONObject jsonObj = null;
		Authentication token = null;
		try {
			
			jsonObj = new JSONObject(jb.toString());
			String username = (String) jsonObj.get("username");
			String password = (String) jsonObj.get("password");
	        token = new UsernamePasswordAuthenticationToken(username,password );

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        token =  this.getAuthenticationManager().authenticate(token);
        if(token.isAuthenticated())
		{
			SecurityContextHolder.getContext().setAuthentication(token);
			HttpSession session = request.getSession(true);
	        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
	        
		}
        return token;
		
	}
	@Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
		JSONObject data = new JSONObject();
    	try {
			data.put("statusCode", 200);
			data.put("message", "Success");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String sJson = data.toString();
    	PrintWriter writer = response.getWriter();
    	writer.write(sJson);
    	writer.flush();
    	writer.close();
        getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }
	
	
	@Override
	protected AuthenticationSuccessHandler getSuccessHandler() {
		// TODO Auto-generated method stub
		return super.getSuccessHandler();
	}
	
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        getFailureHandler().onAuthenticationFailure(request, response, failed);
    }

    @Autowired
    @Qualifier("authenticationManager")
    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
}
