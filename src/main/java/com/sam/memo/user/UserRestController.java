package com.sam.memo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sam.memo.user.domain.User;
import com.sam.memo.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserRestController
{
	private UserService userService;
	
	//@Autowired
	//@Autowired를 사용을 하는 생성자가 1개면 @Autowired를 생략을 하여도 된다.
	//단 @Autowired를 사용을 하는 생성자가 2개 이상이면 @Autowired를 무조건 사용을 하여야 한다.
	public UserRestController(UserService userService)
	{
		this.userService = userService;
	}
	
	@PostMapping("join")
	public Map<String, String> join
	(
		@RequestParam("loginId") String loginId,
		@RequestParam("password") String password,
		@RequestParam("name") String name,
		@RequestParam("email") String email
	)
	{
		int count = userService.addUser(loginId, password, name, email);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(count == 1)
		{
			resultMap.put("result", "success");
		}
		
		else
		{
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}
	
	@GetMapping("/duplicate-id")
	public Map<String, Boolean> isDuplicateId(@RequestParam("loginId") String loginId)
	{
		boolean isDuplicate = userService.isDuplicateId(loginId);
		
		Map<String, Boolean> resultMap = new HashMap<>();
		
		resultMap.put("isDuplicate", isDuplicate);
		
		return resultMap;
	}
	
	@PostMapping("login")
	public Map<String, String> login
	(
		@RequestParam("loginId") String loginId,
		@RequestParam("password") String password,
		HttpServletRequest request
	)
	{
		User user = userService.getUser(loginId, password);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(user != null)
		{
			resultMap.put("result", "success");
			
			HttpSession session = request.getSession();
			
			session.setAttribute("userId", user.getId());
			session.setAttribute("userName", user.getName());
		}
		
		else
		{
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
}
