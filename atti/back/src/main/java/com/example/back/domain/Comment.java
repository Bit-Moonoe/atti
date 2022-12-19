package com.example.back.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentId; // 댓글 고유 번호

	@Column
	private String nickName;

	@Column
	private String content; // 내용

	@Column
	private Long postSeq; // 게시글 번호

	@Column
	private String createdDate; // 댓글 작성날짜

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_Id")
	private User user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "post_Id")
	private Post post;

	public void setPost(Post post) {
		this.post = post;
		post.getComments().add(this);
	}

	public void setUser(User user) {
		this.user = user;
		user.getComments().add(this);
	}

	@Getter
	@Setter
	@ToString
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Request {

		private Long commentId; // db의 id와 맞추기 위한 용도의 id

		private String nickName;

		@NotBlank(message = "글 내용 공백 불가")
		@NotNull(message = "글 내용 null 불가")
		private String content;

		private Long postSeq;

		public static Comment toEntity(Comment.Request req) {
			return Comment.builder().commentId(req.getCommentId()).postSeq(req.getPostSeq()).content(req.getContent())
					.build();
		}

	}

	@Getter
	@Setter
	@ToString
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response {

		private Long commentId;
		private String nickName;
		private String content;
		private Long postSeq;
		private String createdDate;
		private String resMessage;

		public static Comment.Response toResponse(Comment comment) {
			return Comment.Response.builder().commentId(comment.getCommentId()).nickName(comment.getNickName())
					.content(comment.getContent()).postSeq(comment.getPostSeq()).createdDate(comment.getCreatedDate())
					.build();
		}

		public static List<Comment.Response> toResponseList(List<Comment> comments) {
			List<Comment.Response> commentList = new ArrayList<Comment.Response>();
			for (Comment comment : comments) {
				commentList.add(toResponse(comment));
			}

			return commentList;
		}
	}
}
