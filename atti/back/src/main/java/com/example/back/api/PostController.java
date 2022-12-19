package com.example.back.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.back.domain.Post;
import com.example.back.domain.User;
import com.example.back.repository.PostRepository;
import com.example.back.repository.UserRepository;
import com.example.back.service.PostService;
import com.example.back.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("post")
@RequiredArgsConstructor
@Slf4j
public class PostController {

	private final UserRepository userRepository;

	private final PostRepository postRepository;

	private final PostService postService;
	
	private final UserService userService;

	// 게시글 작성 = C
	@PostMapping("/writePost")
	public ResponseEntity<?> createPost(@AuthenticationPrincipal String nickName, @RequestBody Post.Request req) {

		if (req.getTitle().equals("")) {
			
			Post.Response res = Post.Response.builder().resMessage("제목을 입력해주세요.").build();
			
			return ResponseEntity.badRequest().body(res);
			
		} else if (req.getContent().equals("")) {
			
			Post.Response res = Post.Response.builder().resMessage("내용을 입력해주세요.").build();
			
			return ResponseEntity.badRequest().body(res);
			
		}


		try {
			Post postEntity = Post.Request.toEntity(req);
			
			User findUser = userRepository.findByNickName(nickName);
			
			postEntity.setUser(findUser);
			postService.createPost(postEntity, findUser.getNickName());
			
			Post.Response res = Post.Response.builder().resMessage("작성을 완료하였습니다.").build();
			
			return ResponseEntity.ok().body(res);
			
		} catch (Exception e) {
			
			Post.Response res = Post.Response.builder().resMessage("작성을 완료하지 못했습니다.").build();
			
			return ResponseEntity.badRequest().body(res);
		}
	}

	// 단일 게시글 조회
	@GetMapping("/selectPost/{postId}")
	public ResponseEntity<?> selectPost(@PathVariable Long postId) {

		Post findPost = postRepository.findByPostId(postId);

		Post.Response dto = Post.Response.toResponse(findPost);

		Post.Response res = Post.Response.builder().postId(dto.getPostId()).nickName(dto.getNickName())
				.title(dto.getTitle()).content(dto.getContent()).viewCount(dto.getViewCount()).createdDate(dto.getCreatedDate())
				.comments(dto.getComments()).build();

		return ResponseEntity.ok().body(res);
	}

	// 전체 게시글 목록
	@GetMapping("/postList/main")
	public ResponseEntity<?> allPosts() {

		List<Post> posts = postRepository.findAll();
		
		return ResponseEntity.ok().body(Post.Response.toResponseList(posts));
	}

	// 작성자명으로 게시글 조회 = R
	@GetMapping("/postList/{nickName}")
	public ResponseEntity<?> retrievePostList(@AuthenticationPrincipal @PathVariable String nickName) {
		
		List<Post> postEntities = postService.retrieve(nickName);
		
		List<Post.Response> res = Post.Response.toResponseList(postEntities);
		
		return ResponseEntity.ok().body(res);
	}

	// 게시글 수정 = U // 
	@PutMapping
	public ResponseEntity<?> updatePost(HttpServletRequest request, @AuthenticationPrincipal String nickName, @Valid @RequestBody Post.Request req) {

		Post searchPost = postRepository.findByPostId(req.getPostId());
		
		
		
		System.out.println("게시글 작성 유저 닉네임: " + searchPost.getNickName());
		
		System.out.println("로그인된 유저 닉네임: " + nickName);
		
		
		
		if (userService.checkNickName(request, searchPost.getNickName())) {
			
			req.setNickName(nickName);
			
			List<Post> postEntities = postService.update(req, req.getPostId());
			
			List<Post.Response> res = Post.Response.toResponseList(postEntities);

			return ResponseEntity.ok().body(res);
			
		} else {
			
			return ResponseEntity.badRequest().body(Post.Response.builder().resMessage("해당 요청은 작성자만 가능합니다.").build());
			
		}
	}

	// 게시글 삭제 = D
	@Transactional
	@DeleteMapping
	public ResponseEntity<?> deletePost(HttpServletRequest request ,@AuthenticationPrincipal String nickName, @RequestBody Post.Request req) {

		Post searchPost = postRepository.findByPostId(req.getPostId());
		
		log.warn("게시글 작성 유저 닉네임: " + searchPost.getNickName());
		
		log.warn("로그인된 유저 닉네임: " + nickName);
		
		if (userService.checkNickName(request, searchPost.getNickName())) {
			try {
				List<Post> postEntities = postService.delete(req.getPostId());

				List<Post.Response> res = Post.Response.toResponseList(postEntities);

				return ResponseEntity.ok().body(res);
				
			} catch (Exception e) {
				
				String err = e.getMessage();
				
				Post.Response res = Post.Response.builder().resMessage(err).build();
				
				return ResponseEntity.badRequest().body(res);
			}
		} else {
			
			return ResponseEntity.badRequest().body(Post.Response.builder().resMessage("해당 요청은 작성자만 가능합니다.").build());
			
		}
	}
	
	@PutMapping("/{postId}")
	public ResponseEntity<?> increaseViewCount(@PathVariable Long postId){
		
		Post findPost = postRepository.findByPostId(postId);
		
		int curViewCount = findPost.getViewCount();
		
		findPost.setViewCount(++curViewCount);
		
		postRepository.save(findPost);
		
		
		Post.Response res = Post.Response.toResponse(findPost);
		res.setViewCount(++curViewCount);
		return ResponseEntity.ok().body(res);
	}

}
