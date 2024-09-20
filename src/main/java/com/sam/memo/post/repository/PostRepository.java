package com.sam.memo.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sam.memo.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Integer>
{	
	// WHERE `userId` = #{userId} ORDER BY `id` DESC
	public List<Post> findByUserIdOrderByIdDesc(int userId);
	
	//public int deleteMemo(@Param("userId") int userId);
}