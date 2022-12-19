package com.example.back.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.back.domain.Comment;
import com.example.back.domain.Post;
import com.example.back.domain.User;
import com.example.back.repository.CommentRepository;
import com.example.back.repository.PostRepository;
import com.example.back.repository.UserRepository;
import com.example.back.service.CommentService;
import com.example.back.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("comment")
@RequiredArgsConstructor
public class CommentController {

	private final UserRepository userRepository;

	private final PostRepository postreRepository;

	private final CommentRepository commentRepository;

	private final CommentService commentService;
	
	private final UserService userService;

	// 댓글 작성 = C
	@PostMapping
	public ResponseEntity<?> createComment(@AuthenticationPrincipal String nickName, @Valid @RequestBody Comment.Request req) {
		
		Comment comment = Comment.Request.toEntity(req);
		
		comment.setCreatedDate(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));

		Post findPost = postreRepository.findByPostId(comment.getPostSeq());
		
		User findUser = userRepository.findByNickName(nickName);
		
		comment.setUser(findUser);
		
		comment.setPost(findPost);
		
		comment.setNickName(nickName);
		
		comment.setPostSeq(findPost.getPostId());
		
		commentService.saveComment(comment);

		Comment.Response res = Comment.Response.toResponse(comment);
		
		return ResponseEntity.ok().body(res);
	}

	// 댓글 전체 조회 = R
	@GetMapping
	public ResponseEntity<?> getCommentList(@RequestBody Comment.Request req) {

		List<Comment> comments = commentService.retrieveCommentList(req.getPostSeq());
		
		List<Comment.Response> commentList = Comment.Response.toResponseList(comments);

		return ResponseEntity.ok().body(commentList);
	}


	// 댓글 수정 = U
	@PutMapping
	public ResponseEntity<?> updateComment(HttpServletRequest request, @AuthenticationPrincipal String nickName, @Valid @RequestBody Comment.Request req) {

		Comment updatedComment = commentRepository.findByCommentId(req.getCommentId());

		if (userService.checkNickName(request, updatedComment.getNickName())) {

			commentService.updateComment(req);
			
			Comment.Response res = Comment.Response.toResponse(updatedComment);
			
			return ResponseEntity.ok().body(res);
		}

		return ResponseEntity.ok().body("해당 댓글 작성자만 이 요청을 할 수 있습니다.");
	}

	// 댓글 삭제 = D
	@DeleteMapping
	@Transactional
	public ResponseEntity<?> deleteComment(HttpServletRequest request, @AuthenticationPrincipal String nickName, @RequestBody Comment.Request req) {

		Comment findComment = commentRepository.findByCommentId(req.getCommentId());

		if (userService.checkNickName(request, findComment.getNickName())) {

			commentService.deleteComment(findComment);

			List<Comment> updatedComments = commentService.retrieveCommentList(findComment.getPostSeq());

			List<Comment.Response> res = Comment.Response.toResponseList(updatedComments);

			return ResponseEntity.ok().body(res);
		}

		return ResponseEntity.badRequest().body("해당 댓글 작성자만 이 요청을 할 수 있습니다.");
	}
}
