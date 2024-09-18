package com.sam.memo.user.service;

import org.springframework.stereotype.Service;

import com.sam.memo.common.MD5HasshingEncoder;
import com.sam.memo.user.domain.User;
import com.sam.memo.user.repository.UserRepository;

@Service
public class UserService 
{
	private UserRepository userRepository;
	// DI(Dependency injection): 의존성 주입
	// IoC : 제어의 역전
	//private HasshingEncoder encoder;
	
	//@Autowired로 생성한 생성자가 1개만 존재하는 경우 @Autowired를 생략할 수 있다
	//즉 @Autowired로 생성한 생성자가 2개 이상 존재하는 경우 @Autowired를 무조건 사용을 해야한다
	
	//@Autowired	
	public UserService(UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}
	
	public int addUser
	(
			String loginId,
			String password,
			String name,
			String email
	)
	{
		String encryptPassword = MD5HasshingEncoder.encode(password);
		
		return userRepository.insertUser(loginId, encryptPassword, name, email);
	}
	
	public boolean isDuplicateId(String loginId)
	{
		int count = userRepository.selectCountByLoginId(loginId);
		
		if(count == 0)
		{
			return false;
		}
		
		else 
		{
			return true;
		}
	}
	
	public User getUser
	(
		String loginId,
		String password
	)
	{
		String encryptPassword = MD5HasshingEncoder.encode(password);
		
		return userRepository.selectUser(loginId, encryptPassword);
	}
}