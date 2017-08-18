package com.TinyUrl.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.TinyUrl.Models.UrlMapping;
import com.TinyUrl.Models.User;
import com.TinyUrl.Services.SecurityServiceImpl;
import com.TinyUrl.Services.UrlMappingService;
import com.TinyUrl.Services.UserService;
import com.google.common.reflect.ClassPath;


@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder bcrypt;
	@Autowired
	private UrlMappingService urlMappingService;
	@Autowired
	private SecurityServiceImpl securityServiceImpl;
	
	@RequestMapping(value ="/resetPassword", method = RequestMethod.POST)
	@ResponseBody
	public void resetPassword(@RequestBody final Map<String, String> payload, HttpServletResponse response) throws IOException{
		// validate password
		String password = payload.get("password");
		String confirm = payload.get("confirm");
		String email = payload.get("email");
		if(password.equals(confirm))
		{
			User user = userService.getByEmail(email);
			user.setPassword(bcrypt.encode(password));
			userService.save(user);
		}
		response.sendRedirect("/login");
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value="/data", method=RequestMethod.GET)
	public void getMyMappings(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException
	{
		HttpSession session = request.getSession();
		SecurityContext sc = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		Authentication auth = sc.getAuthentication();
		auth.getPrincipal();
		JSONObject data = new JSONObject();
    	data.put("statusCode", 200);
    	data.put("message", "Success");
		List<UrlMapping> list= urlMappingService.findByEmail((String)auth.getPrincipal());
		data.put("urls", list.toArray());
		String sJson = data.toString();
    	PrintWriter writer = response.getWriter();
    	writer.write(sJson);
    	writer.flush();
    	writer.close();
	}
	
	@RequestMapping("/home")
	public ModelAndView home()
	{
		
		ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
	}
	
	@RequestMapping("/profile")
	public Principal sayHello(HttpServletRequest request, HttpServletResponse response, Principal principal) {
		Principal temp = principal;
		System.out.println(temp);
		return principal;
	}
}