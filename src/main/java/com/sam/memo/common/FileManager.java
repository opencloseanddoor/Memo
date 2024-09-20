package com.sam.memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

public class FileManager 
{	
	// 파일경로는 변하면 안되므로 상수로 선언을 한다
	public static final String FILE_UPLOAD_PATH = "X:\\Spring_project\\upload\\memo"; //서버에서 접근할 파일 저장 공간의 위치를 지정
	
	// 파일 저장
	public static String saveFile(int userId, MultipartFile file) //파일을 저장을 할 메소드 구현
	{
		if(file == null) //전달 받은 파일 값이 null과 같다면 null을 반환
		{
			return null;
		}
		
		// 같은 파일이름으로 전달 될 경우
		// 폴더를 만들어서 파일을 저장
		// 로그인 사용자 userId를 폴더이름 으로 사용
		// 현재 시간정보를 폴더 이름으로 사용
		// UNIX TIME : 1970 1월 1일 부터 흐른 시간을 milli second (1 / 1000초)로 표현한 값
		// ex ) 2_938091328
		
		String directoryName = "/" + userId + "_" + System.currentTimeMillis(); //디렉터리 이름은 중복을 최소화하기위해 /와 userId와 Unix계열에 시간을 얻어와서 디렉터리 이름을 설정한다
		
		//폴더 생성
		String directoryPath = FILE_UPLOAD_PATH + directoryName; //디렉터리의 경로는 파일의 경로와 디렉터리 이름으로 선언한다
		
		File directory = new File(directoryPath); //디렉터리 경로를 인자로 전달을 해서 그 문자열이 디렉터리의 경로가 되도록 한 다음 파일을 생성을한다
		
		if(!directory.mkdir()) // 디렉터리 생성 명령어인 mkdir로 디렉터리를 생성을 한 다음 반환되는 값일 거짓이라면 폴더 생성이 실패하였으므로 null을 반환한다
		{
			// 폴더 생성 실패
			return null;
		}
		
		// 파일 저장
		String filePath = directoryPath + "/" + file.getOriginalFilename(); //directoryPath가 갖고있는 문자열 + / +원본의 파일 네임을 갖고 와서 file변수에 대입을 한다
		
		try 
		{
			byte[] bytes; //0과 1을 저장을 할 수 있는 bytes변수를 선언을 한다
			bytes = file.getBytes(); //파일이 갖고있는 이미지를 바이트로 변환한다.
			Path path = Paths.get(filePath); // 경로를 저장을 할 수 있는 변수인 path를 선언을 한다, 그리고 filePath의 경로를 반환한다
			Files.write(path, bytes); //path가 갖고있는 경로에다가 bytes가 갖고있는 이미지를 넣는다
		}
		
		catch (IOException e) //만약에 예외가 발생을 하면 null을 반환을 한다.
		{
			//파일 저장 실패
			return null;
		}
		
		// 저장된 파일을 접근할 URL path 만들기
		// 파일 저장 경로 : "X:\\Spring_project\\upload\\memo/2_938091328/test.png";
		// URL path : /images/2_938091328/test.png
		
		return "/images" + directoryName + "/" + file.getOriginalFilename(); //최종적으로는 /images + 디렉터리 이름 + / + 파일이 소유한 원본 이미지의 이름을 문자열로 반환한다 
	}
}