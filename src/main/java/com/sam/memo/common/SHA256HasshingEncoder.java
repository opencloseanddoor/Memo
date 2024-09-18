package com.sam.memo.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

@Component("sha256Hassing")
public class SHA256HasshingEncoder implements HasshingEncoder
{
	@Override
	public String encode(String massage)
	{
		String result = "";
		
		try 
		{
			MessageDigest messageDigest = MessageDigest.getInstance("sha256");
			
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