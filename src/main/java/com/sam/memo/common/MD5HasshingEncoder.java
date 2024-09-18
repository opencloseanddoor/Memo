package com.sam.memo.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

@Component("md5Hasshing") //스프링이 자동으로 관리하는 빈을 자동으로 등록을 하는 @Component어노테이션을 사용 
//@Primary
public class MD5HasshingEncoder
{
	public static String encode(String massage)
	{
		String result = "";
		
		try 
		{
			MessageDigest messageDigest = MessageDigest.getInstance("md5");
			
			byte[] bytes = massage.getBytes();
			
			messageDigest.update(bytes);
			
			byte[] digest = messageDigest.digest();
			
			// byte 배열의 값을 16진수 문자열로 변환
			for(int i = 0; i < digest.length; i++)
			{
				result += Integer.toHexString(digest[i] & 0xff); //AND연산으로 둘다 1이 되는 것만 1이 된다.
			}
		} 
		
		catch (NoSuchAlgorithmException e) 
		{
			return null;
		}
		return result;
	}
}