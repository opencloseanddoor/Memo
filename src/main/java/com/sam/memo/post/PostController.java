package com.sam.memo.post;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sam.memo.post.domain.Post;
import com.sam.memo.post.service.PostService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@Controller
public class PostController
{
	private PostService postService;
	
	public PostController(PostService postService)
	{
		this.postService = postService;
	}
	
	@GetMapping("/list-view")
	public String memoList(Model model, HttpSession session)
	{
	    Integer userId = (Integer) session.getAttribute("userId");
	    
	    if (userId == null) //로그인 되어있지않은 경우의 대한 예외처리입니다.
	    {
	        return "redirect:user/login"; // 사용자가 로그인하지 않은 경우 로그인 페이지로 리다이렉트
	    }

	    List<Post> postList = postService.getPostList(userId);
	    model.addAttribute("memoList", postList);
	    
	    return "post/list";
	}
	
	@GetMapping("/create-view")
	public String inputMemo()
	{
		return "post/input";
	}
	
	@GetMapping("/detail-view")
	public String memoDetail
	(
		@RequestParam("id") int id,
		Model model	
	)
	{
		Post post = postService.getPost(id);
		
		model.addAttribute("memo", post);
		
		return "post/detail";
	}
}
