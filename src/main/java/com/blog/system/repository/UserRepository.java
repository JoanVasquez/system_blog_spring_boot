package com.blog.system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.system.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findByEmail(String email);

	public Optional<User> findByUserNameOrEmail(String userName, String email);

	public Optional<User> findByUserName(String userName);

	public Boolean existsByUserName(String userName);

	public Boolean existsByEmail(String email);

}
