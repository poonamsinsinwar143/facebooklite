package com.um.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.um.entity.Comments;
import com.um.entity.Post;

public interface CommentRepository extends JpaRepository<Comments, Long>{

	@Query(value = "select c.id as commentId, c.message, c.createdOn, u.userId, u.firstName,u.lastName "+
	" from Comments c inner join User u on c.user.userId = u.userId where c.post.id = :postId")
	Object[][] findAllByPostWithUser(Long postId);
	
	int deleteAllByPost(Post post);

}
