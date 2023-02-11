package com.um.dto;

import java.util.Date;

public class CommentsDto {
	private Long id;
	private String message;
	private Date createdOn;
	private Long commentorUserId;
	private UserDto user;
	

	public Long getCommentorUserId() {
		return commentorUserId;
	}

	public void setCommentorUserId(Long commentorUserId) {
		this.commentorUserId = commentorUserId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

}
