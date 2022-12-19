package com.example.back.api;

import com.example.back.base.jwt.JwtTokenProvider;
import com.example.back.domain.User;
import com.example.back.repository.UserRepository;
import com.example.back.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

	private final JwtTokenProvider jwtTokenProvider;

	private final UserService userService;

	private final UserRepository userRepository;

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@GetMapping("/duplicate")
	public ResponseEntity<?> checkDuplicatedNickName(@RequestParam String nickName) {

		log.warn("중복된 닉네임 체크:" + nickName);

		if (userRepository.existsByNickName(nickName)) {

			log.warn("checkDuplicatedNickName message: 닉네임이 이미 존재합니다");

			return ResponseEntity.badRequest().body("닉네임이 이미 존재합니다.");

		}

		return ResponseEntity.ok().body("해당 닉네임은 사용 가능합니다.");
	}

	@GetMapping("/findAll")
	public List<User.Response> findAllUser() {

		List<User> users = userService.findAllUser();

		List<User.Response> response = User.Response.toResponseList(users);

		return response;
	}

	@GetMapping("/find/{nickName}")
	public ResponseEntity<?> findUser(@PathVariable String nickName) {

		if (userRepository.existsByNickName(nickName)) {

			User user = userService.findUser(nickName);

			User.Response searchUser = User.Response.toResponse(user);

			return ResponseEntity.ok().body(searchUser);
		} else {
			return ResponseEntity.ok().body("해당 닉네임은 존재하지 않습니다.");
		}
	}

	@PostMapping("/sign-up")
	public ResponseEntity<?> signUpUser(@RequestBody User.Request req) {

		if (userRepository.existsByNickName(req.getNickName())) {
			
			return ResponseEntity.badRequest().body("닉네임이 이미 존재합니다.");
			
		}

		try {
			if (req == null || req.getPwd() == null) {
				
				throw new RuntimeException("잘못된 요청입니다.");
				
			}

			User user = User.Request.toEntity(req);

			user.setPwd(passwordEncoder.encode(req.getPwd()));

			User savedUser = userService.saveUser(user);

			User.Response response = User.Response.toResponse(savedUser);

			return ResponseEntity.ok().body(response);

		} catch (Exception e) {
			
			return ResponseEntity.badRequest().body("잘못된 요청입니다.");
			
		}

	}

	@PostMapping("/sign-in")
	public ResponseEntity<?> signInUser(@RequestBody User.Request req) {

		User user = userService.getByCredentials(req.getNickName(), req.getPwd(), passwordEncoder);

		if (user != null) {

			String token = jwtTokenProvider.makeJwtToken(user);

			User.Response responseData = User.Response.toResponse(user);

			responseData.setToken(token);

			return ResponseEntity.ok().body(responseData);

		} else {
			
			return ResponseEntity.badRequest().body("닉네임 및 패스워드를 다시 한 번 확인해주세요.");
			
		}
	}

}
