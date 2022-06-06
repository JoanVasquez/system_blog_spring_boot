package com.blog.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.system.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	public List<Comment> findByPostId(long id);
}
