package com.blog.system.controller;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.system.dto.PaginatedResult;
import com.blog.system.dto.PostDTO;
import com.blog.system.service.CrudService;
import com.blog.system.utils.AppConstant;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private CrudService<PostDTO> crudService;

	@GetMapping
	public ResponseEntity<PaginatedResult> listPost(
			@RequestParam(value = "pageNo", defaultValue = AppConstant.DEFAULT_NUMBER_OF_PAGES, required = false) int numeroDePagina,
			@RequestParam(value = "pageSize", defaultValue = AppConstant.DEFAULT_QTY_PER_PAGE, required = false) int medidaDePagina,
			@RequestParam(value = "sortBy", defaultValue = AppConstant.DEFAULT_ORDER_BY_DEFAULT, required = false) String ordenarPor,
			@RequestParam(value = "sortDir", defaultValue = AppConstant.DEFAULT_ORDER_DIRECTION, required = false) String sortDir) {
		return ResponseEntity.ok(crudService.paginatedResult(numeroDePagina, medidaDePagina, ordenarPor, sortDir));
	}

	@PostMapping
	public ResponseEntity<PostDTO> savePost(@Valid @RequestBody PostDTO postDTO) {
		return new ResponseEntity<>(crudService.save(postDTO), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable(name = "id") Long id) {
		System.out.println("the id is " + id);
		return ResponseEntity.ok(crudService.getById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<PostDTO> updatePost(@PathVariable("id") long id, @Valid @RequestBody PostDTO postDto) {
		PostDTO result = crudService.update(postDto, id);
		return new ResponseEntity<PostDTO>(result, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable("id") long id) {
		return new ResponseEntity<String>("Post deleted successfully", HttpStatus.OK);
	}
}
