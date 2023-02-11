package com.um.entity;

import java.util.Date;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import com.um.dto.UserDto;

@NamedNativeQuery(name = "findAllUserFriends", resultSetMapping = "UserFriendResulSetMapping", 
	query = "select u.user_id,u.first_name,u.last_name from user u inner join ( select user_id2 friend_user_id  from friends f  where user_id1 = :userId union select user_id1 friend_user_id  from friends f2  where user_id2 = :userId) f on u.user_id = f.friend_user_id")
@SqlResultSetMapping(
	    name="UserFriendResulSetMapping",
	    classes={
	      @ConstructorResult(
	        targetClass=UserDto.class,
	        columns={
	          @ColumnResult(name="user_id", type=Long.class),
	          @ColumnResult(name="first_name", type=String.class),
	          @ColumnResult(name="last_name", type=String.class)})})
@Entity
@Table(name = "friends")
public class Friends {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id1")
	private User user1;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id2")
	private User user2;

	private Date createdOn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser1() {
		return user1;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}

	public User getUser2() {
		return user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
