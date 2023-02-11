package com.um.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.um.dto.UserDto;
import com.um.entity.Friends;

public interface FriendRepository extends JpaRepository<Friends, Long> {

	@Query(name = "findAllUserFriends", nativeQuery = true)
	List<UserDto> findAllUserFriend(Long userId);
	

}
