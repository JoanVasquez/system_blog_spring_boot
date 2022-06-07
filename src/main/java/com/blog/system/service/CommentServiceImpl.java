package com.blog.system.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.system.dto.CommentDTO;
import com.blog.system.dto.PaginatedResult;
import com.blog.system.entity.Comment;
import com.blog.system.entity.Post;
import com.blog.system.exception.ResourceNotFoundException;
import com.blog.system.repository.CommentRepository;
import com.blog.system.repository.PostRepository;

@Service
public class CommentServiceImpl implements CrudService<CommentDTO>, CommentService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

	@Override
	public PaginatedResult paginatedResult(int numberOfPages, int qtyPerPage, String orderBy, String sortBy) {
		return null;
	}

	@Override
	public CommentDTO save(CommentDTO model) {
		Comment comment = mapCommentDTOToEntity(model);
		Post post = postRepository.findById(model.getPostId())
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", model.getPostId()));
		comment.setPost(post);
		Comment newComment = commentRepository.save(comment);
		return mapEntityToCommentDTO(newComment);
	}

	@Override
	public List<CommentDTO> list() {
		List<Comment> comments = commentRepository.findAll();
		List<CommentDTO> commentsDto = comments.stream().map(comment -> mapEntityToCommentDTO(comment))
				.collect(Collectors.toList());
		return commentsDto;
	}

	@Override
	public CommentDTO getById(long id) {
		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
		CommentDTO commentDTO = mapEntityToCommentDTO(comment);
		return commentDTO;
	}

	@Override
	public List<CommentDTO> getCommentsByPostId(long postId) {
		List<Comment> comments = commentRepository.findByPostId(postId);
		return comments.stream().map(comment -> mapEntityToCommentDTO(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDTO update(CommentDTO model, long id) {
		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
		comment.setBody(model.getBody());
		comment.setEmail(model.getEmail());
		comment.setName(model.getName());

		Comment commentUpdated = commentRepository.save(comment);
		return mapEntityToCommentDTO(commentUpdated);
	}

	@Override
	public void delete(long id) {
		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
		commentRepository.delete(comment);
	}

	private CommentDTO mapEntityToCommentDTO(Comment comment) {
		CommentDTO commentDto = modelMapper.map(comment, CommentDTO.class);
		return commentDto;
	}

	private Comment mapCommentDTOToEntity(CommentDTO commentDto) {
		Comment comment = modelMapper.map(commentDto, Comment.class);
		return comment;
	}
}
