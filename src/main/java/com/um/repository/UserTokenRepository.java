package com.um.repository;

import org.springframework.data.repository.CrudRepository;

import com.um.entity.UserToken;

public interface UserTokenRepository extends CrudRepository<UserToken, Long>{

	UserToken findByUserId(Long userId);

	void deleteByUserId(Long userId);

}
