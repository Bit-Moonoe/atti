package com.example.back.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.back.domain.Comment;
import com.example.back.domain.Post;
import com.example.back.repository.CommentRepository;
import com.example.back.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

	private final CommentRepository commentRepository;

	private final PostRepository postRepository;

	// 댓글 작성 = C
	public void saveComment(Comment comment) {
		
		commentRepository.save(comment);
		
	}

	// 댓글 전체 조회 = R
	public List<Comment> retrieveCommentList(long postId) {
		
		Post getPost = postRepository.findByPostId(postId);
		
		List<Comment> comments = getPost.getComments();
		
		return comments;
	}

	// 댓글 수정 = U
	public Comment updateComment(Comment.Request req) {
		
		Comment commentEntity = Comment.Request.toEntity(req);
		
		validation(commentEntity);
		
		Optional<Comment> findComment = commentRepository.findById(req.getCommentId());

		findComment.ifPresent(comment -> {
			comment.setContent(req.getContent());
			comment.setCreatedDate(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
			commentRepository.save(comment);
		});
		
		Comment updateComment = findComment.get();
		
		return updateComment;
	}

	// 댓글 삭제 = D
	public List<Comment> deleteComment(final Comment comment) {
		
		commentRepository.deleteByCommentId(comment.getCommentId());
		
		List<Comment> comments = commentRepository.findAll();

		return comments;
	}

	private void validation(final Comment commentEntity) {
		
		if (commentEntity == null) {
			
			log.warn("commentEntity cannat be null");
			
			throw new RuntimeException("commentEntity는 null 허용 불가");
		}

		if (commentEntity.getContent() == null) {
			
			log.warn("Conntent == null");
			
			throw new RuntimeException("내용을 입력해주세요.");
		}
	};

}
