package com.msoft.security.jwt.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msoft.security.jwt.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);
}
