package com.um.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.um.entity.Likes;
import com.um.entity.Post;

public interface LikeRepository extends JpaRepository<Likes, Long>{

	@Query(value = "select l.id, u.userId,u.firstName,u.lastName"
			+ " from Likes l inner join User u on l.user.userId = u.userId where l.post.id = :postId")
	 Object[][] findAllByPostWithUser(Long postId);
	
	int deleteAllByPost(Post post);

}
