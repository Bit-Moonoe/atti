package com.example.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.back.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	Comment findByCommentId(Long commentId);
	
	void deleteByCommentId(Long commentId);
}
