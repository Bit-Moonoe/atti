package com.example.back.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.back.domain.Post;
import com.example.back.repository.CommentRepository;
import com.example.back.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	private final CommentRepository commentRepository;

	public List<Post> createPost(final Post postEntity, String nickName) {
		
		validation(postEntity);
		postEntity.setNickName(nickName);
		
		postEntity.setCreatedDate(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
		
		postRepository.save(postEntity);
		
		log.info("Post 생성 완료");
		
		return postRepository.findByNickName(postEntity.getNickName());// 한명의 정보만 보내줌
	};
	
	
	private void validation(final Post postEntity) {
		
		if (postEntity == null) {
			
			log.warn("postEntity cannat be null");
			
			throw new RuntimeException("PostEntity는 null 허용 불가");
		}

	};

	public List<Post> retrieve(final String nickName) {
		
		log.warn(nickName);
		
		return postRepository.findByNickName(nickName);
	}

	public List<Post> update(final Post.Request req, Long postId) {
		
		Post checkPost = Post.Request.toEntity(req);
		
		validation(checkPost);
	
		final Post post = postRepository.findByPostId(postId);
		
		post.setTitle(req.getTitle());
		
		post.setContent(req.getContent());
		
		post.setCreatedDate(new SimpleDateFormat("yyyy/MM/dd").format(new Date()) + " (수정됨)");
		
		postRepository.save(post);

		return retrieve(req.getNickName());
	}

	public List<Post> delete(final Long postId) {

		Post findPost = postRepository.findByPostId(postId);
		
		try {

			commentRepository.deleteAll();

			postRepository.deleteByPostId(postId);
			
		} catch (Exception e) {
			
			log.error("Post 삭제 중 에러 발생.", e);
			
			throw new RuntimeException("삭제 중 에러 발생" + postId);
		}

		return retrieve(findPost.getNickName());
	}

}
