package com.example.back.domain;

import io.jsonwebtoken.Claims;
import lombok.*;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;


@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "user_info")
public class User {
	@Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    
    @Column(nullable = false)
    @Size(min = 2,max = 12)
    @NotNull
    private String nickName;
    
    @Column(length = 100, nullable = false)
    @NotNull
    private String pwd;

    private String token;
    
    @Column(name = "join_date")
    private String joinDate;
    
    @OneToMany(mappedBy = "user")
    private List<Post> posts;
    
    @OneToMany(mappedBy = "user")
    public List<Comment> comments;

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Request {
    	
    	private String id;
    	
    	@NotBlank(message = "공백 허용하지 않음")
        @NotNull(message = "null 허용하지 않음")
        private String nickName;
    	
    	@NotBlank(message = "공백 허용하지 않음")
        @NotNull(message = "null 허용하지 않음")
        private String pwd;
    	
    	private Post post;

        public static User toEntity(final Request request) {
            return User.builder()
            		.id(request.getId())
                    .nickName(request.getNickName())
                    .pwd(request.getPwd())
                    .posts(new ArrayList<>())
                    .comments(new ArrayList<>())
                    .build();
        }
    }

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Response {
        private String id;
        private String nickName;
        private String joinDate;
        private List<Post.Response> posts;
        private List<Comment.Response> comments;
        private String token;


        public static User.Response toResponse(final User user) {
            return User.Response.builder()
                    .id(user.getId())
                    .nickName(user.getNickName())
                    .joinDate(user.getJoinDate())
                    .posts(Post.Response.toResponseList(user.getPosts()))
                    .comments(Comment.Response.toResponseList(user.getComments()))
                    .token(user.getToken())
                    
                    .build();
        }
        public static List<Response> toResponseList(final List<User> users) {
            List<Response> list = new ArrayList<>();
            for (User user : users) {
                list.add(toResponse(user));
            }
            return list;

        }
    }
}