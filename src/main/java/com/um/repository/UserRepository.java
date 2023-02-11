package com.um.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.um.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUserNameAndPassword(String userName, String password);
}