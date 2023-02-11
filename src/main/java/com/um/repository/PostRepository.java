package com.um.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.um.entity.Post;
import com.um.entity.User;

public interface PostRepository extends JpaRepository<Post, Long>{
	public List<Post> findAllByUser(User user);
}
