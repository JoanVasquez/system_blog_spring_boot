package com.blog.system.service;

import java.util.List;

import com.blog.system.dto.CommentDTO;

public interface CommentService {
	public List<CommentDTO> getCommentsByPostId(long postId);
}
