package com.sam.memo.post;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sam.memo.post.domain.Post;
import com.sam.memo.post.service.PostService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/post")
public class PostRestController 
{
	private PostService postService;
	
	public PostRestController(PostService postService)
	{
		this.postService = postService;
	}
	
	@PostMapping("/create") //경로를 지정(전송되는 데이터는 고정된 값이 아니며, 매우 중요한 정보도 들어있으므로 Get이 아닌 Post로 한다)
	public Map<String, String>createMemo // ajax를 사용을 할 예정이므로 Map을 반환을 해야한다
	(
		@RequestParam("title") String title, //ajax에서 제목을 전달 받는다.
		@RequestParam("contents") String contents, //ajax에서 내용을 전달 받는다.
		@RequestParam(value="imageFile", required=false) MultipartFile file, //ajax에서 이미지를 전달을 받는다.
		HttpSession session //로그인된 정보를 일정시간동안 기록을 하는 Session을 자료형으로 가지는 변수를 선언을 한다.
	)
	{
		int userId = (Integer)session.getAttribute("userId");
		
		Post post = postService.addPost(userId, title, contents, file);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(post != null)
		{
			resultMap.put("result", "success");
		}
		else
		{
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}
	
//	@PostMapping("/deleteMemo")
//	public Map<String, String> deleteMemo
//	(
//		HttpSession session
//	)
//	{
//		int userId = (Integer)session.getAttribute("userId");
//		
//		int count = postService.dropMemo(userId);
//		
//		Map<String, String> resultMap = new HashMap<>();
//		
//		if(count == 1)
//		{
//			resultMap.put("result", "success");
//		}
//		else
//		{
//			resultMap.put("result", "fail");
//		}
//		
//		return resultMap;
//	}
}
