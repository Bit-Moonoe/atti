package com.example.board.vel01.domain;

import io.jsonwebtoken.Claims;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Getter @Setter
@Builder
@Table(name = "user_info")
@ToString
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
    
    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<Post>();

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Request {
    	
    	@NotBlank(message = "공백 허용하지 않음")
        @NotNull(message = "null 허용하지 않음")
        private String nickName;
    	
    	@NotBlank(message = "공백 허용하지 않음")
        @NotNull(message = "null 허용하지 않음")
        private String pwd;

        public static User toEntity(final Request request) {
            return User.builder()
                    .nickName(request.getNickName())
                    .pwd(request.getPwd())
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
        private String token;


        public static User.Response toResponse(final User user) {
            return User.Response.builder()
                    .id(user.getId())
                    .nickName(user.getNickName())
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