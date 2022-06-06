package com.blog.system.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.system.dto.PaginatedResult;
import com.blog.system.dto.PostDTO;
import com.blog.system.entity.Post;
import com.blog.system.exceptions.ResourceNotFoundException;
import com.blog.system.repository.PostRepository;

@Service
public class PostServiceImpl implements CrudService<PostDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PostRepository postRepository;

	@Override
	public PaginatedResult paginatedResult(int numberOfPages, int qtyPerPage, String orderBy, String sortBy) {
		Sort sort = sortBy.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(orderBy).ascending()
				: Sort.by(orderBy).descending();
		Pageable pageable = PageRequest.of(numberOfPages, qtyPerPage, sort);
		Page<Post> postsPage = postRepository.findAll(pageable);
		List<Post> posts = postsPage.getContent();
		List<PostDTO> postsDto = posts.stream().map(post -> mapEntityToPostDTO(post)).collect(Collectors.toList());

		PaginatedResult postResult = PaginatedResult.builder().content(postsDto).pageNumber(postsPage.getNumber())
				.qtyPerPage(postsPage.getSize()).totalItems(postsPage.getTotalElements())
				.totalPages(postsPage.getTotalPages()).lastPage(postsPage.isLast()).build();
		return postResult;
	}

	@Override
	public PostDTO save(PostDTO model) {
		Post post = mapPostDTOToEntity(model);
		Post newPost = postRepository.save(post);
		PostDTO result = mapEntityToPostDTO(newPost);
		return result;
	}

	@Override
	public List<PostDTO> list() {
		List<Post> posts = postRepository.findAll();
		List<PostDTO> postsDto = posts.stream().map(post -> mapEntityToPostDTO(post)).collect(Collectors.toList());
		return postsDto;
	}

	@Override
	public PostDTO getById(long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		PostDTO postDto = mapEntityToPostDTO(post);
		return postDto;
	}

	@Override
	public PostDTO update(PostDTO model, long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		post.setContent(model.getContent());
		post.setDescription(model.getDescription());
		post.setTitle(model.getTitle());

		Post updatedPost = postRepository.save(post);
		PostDTO postDto = mapEntityToPostDTO(updatedPost);
		return postDto;
	}

	@Override
	public void delete(long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		postRepository.delete(post);
	}

	private PostDTO mapEntityToPostDTO(Post post) {
		PostDTO postDto = modelMapper.map(post, PostDTO.class);
		return postDto;
	}

	private Post mapPostDTOToEntity(PostDTO postDTO) {
		Post post = modelMapper.map(postDTO, Post.class);
		return post;
	}

}
