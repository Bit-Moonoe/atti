package com.example.back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.back.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findByNickName(String nickName);

	Post findByPostId(Long postId);

	void deleteByPostId(Long postId);
}
