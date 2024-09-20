package com.sam.memo.post.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sam.memo.common.FileManager;
import com.sam.memo.post.domain.Post;
import com.sam.memo.post.repository.PostRepository;

@Service
public class PostService 
{
	private PostRepository postRepository;
	
	PostService(PostRepository postRepository)
	{
		this.postRepository = postRepository;
	}
	
	public Post addPost(int userId, String title, String contents, MultipartFile file)
	{
		String urlPath = FileManager.saveFile(userId, file);
		
		Post post = Post.builder()
					.userId(userId)
					.title(title)
					.contents(contents)
					.imagePath(urlPath)
					.build();
		
		Post result = postRepository.save(post);
		
		return result;
	}
	
	public List<Post> getPostList(int userId)
	{
		return postRepository.findByUserIdOrderByIdDesc(userId);
	}
	
	public Post getPost(int id)
	{
		// OPtional을 사용하는 이유는 Null Pointer Exception을 방지하기위해서 사용을 한다
		// Null Pointer Exception이 발생을 하면 프로그램이 강제로 종료가 되기 때문에 Jpa를 사용을 하면 무조건 OPtional객체를 통해서 repositroy의 값을 전달을 받아야한다
		Optional<Post> optionalPost = postRepository.findById(id);
		
		Post post = optionalPost.orElse(null);
		
		return post;
	}
	
//	public int dropMemo(int userId)
//	{
//		return postRepository.deleteMemo(userId);
//	}
}
