package com.blog.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.system.dto.CommentDTO;
import com.blog.system.service.CommentService;
import com.blog.system.service.CrudService;

@RestController
@RequestMapping("/api/")
public class CommentController {

	@Autowired
	private CrudService<CommentDTO> crudService;

	@Autowired
	private CommentService commentService;

	@GetMapping("/post/{postId}/comments")
	public ResponseEntity<List<CommentDTO>> listCommentsByPostId(@PathVariable("postId") long postId) {
		List<CommentDTO> commentsDTO = commentService.getCommentsByPostId(postId);
		return ResponseEntity.ok(commentsDTO);
	}

	@GetMapping("/comments/{commentId}")
	public ResponseEntity<CommentDTO> getCommentById(@PathVariable("commentId") long id) {
		CommentDTO commentDTO = crudService.getById(id);
		return ResponseEntity.ok(commentDTO);
	}

	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDTO> saveComment(@PathVariable("postId") long postId,
			@RequestBody CommentDTO commentDTO) {
		commentDTO.setPostId(postId);
		CommentDTO newComment = crudService.save(commentDTO);
		return new ResponseEntity<>(newComment, HttpStatus.CREATED);
	}

	@PutMapping("/comments/{id}")
	public ResponseEntity<CommentDTO> updateComment(@PathVariable("id") long id, @RequestBody CommentDTO commentDTO) {
		CommentDTO updatedComment = crudService.update(commentDTO, id);
		return ResponseEntity.ok(updatedComment);
	}

	@DeleteMapping("/comments/{id}")
	public ResponseEntity<String> deleteComment(@PathVariable("id") long id) {
		crudService.delete(id);
		return ResponseEntity.ok("Comment deleted successfully");
	}

}
