package com.example.board.vel01.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "post")
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
	
	@Column
    private String nickName;
	
	@Column
	@NotNull
    private String title;
	
	@Column
	@NotNull
    private String content;
	
	@Column
    private Long viewCount;
	
	@Column
	private String createdDate;
	
	@Column
	private String err;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_Id")
	private User user;
	
	@OneToMany(mappedBy = "post")
	private List<Comment> comments;
	
	public void setUser(User user) {
		this.user = user;
		user.getPosts().add(this);
	}
   
    @Getter@Setter
	@ToString
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
    public static class Request{
    	
    	private Long postId;
    	
        private String nickName;
    	
        @NotNull(message = "게시글 제목은 null 금지")
        @NotBlank(message = "게시글 제목은 공백 금지")
        private String title;
        
        @NotNull(message = "게시글 내용은 null 금지")
        @NotBlank(message = "게시글 내용은 공백 금지")
        private String content;
        
        private Long viewCount;
    	
    	public static Post toEntity(Post.Request req) {
    		return Post.builder()
    				.postId(req.getPostId())
    				.title(req.getTitle())
    				.content(req.getContent())
    				.viewCount(req.getViewCount())
    				.build();
    	}
    }
    
    @Getter@Setter
   	@ToString
   	@Builder
   	@NoArgsConstructor
   	@AllArgsConstructor
    public static class Response{
    	
    	private Long postId;
        private String nickName;
        private String title;
        private String content;
        private String createdDate;
        private List<Comment.Response> comments;
        private Long viewCount;
        private String err;
        
    	
    	public static Post.Response toResponse(Post postEntity){
    		
    		return Post.Response.builder()
    				.postId(postEntity.getPostId())
    				.nickName(postEntity.getNickName())
    				.title(postEntity.getTitle())
    				.content(postEntity.getContent())
    				.comments(Comment.Response.toResponseList(postEntity.getComments()))
    				.createdDate(postEntity.getCreatedDate())
    				.viewCount(postEntity.getViewCount())
    				.build();
    	}
    	
    	public static List<Post.Response> toResponseList(List<Post> posts){
    		List<Post.Response> postList = new ArrayList<Post.Response>();
    		for (Post post : posts) {
				postList.add(toResponse(post));
			}
    		return postList;
    	}
    }
    
    
}