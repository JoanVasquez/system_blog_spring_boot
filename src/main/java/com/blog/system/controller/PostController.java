package com.blog.system.controller;

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

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private CrudService<PostDTO> crudService;
	

//	@GetMapping
//	public ResponseEntity<PaginatedResult> listPost(@RequestParam(value = "pageNo", defaultValue = AppConstantes.NUMERO_DE_PAGINA_POR_DEFECTO, required = false) int numeroDePagina,
//			@RequestParam(value = "pageSize", defaultValue = AppConstantes.MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int medidaDePagina,
//			@RequestParam(value = "sortBy", defaultValue = AppConstantes.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
//			@RequestParam(value = "sortDir", defaultValue = AppConstantes.ORDENAR_DIRECCION_POR_DEFECTO, required = false) String sortDir) {
//		
//	}

	@PostMapping
	public ResponseEntity<PostDTO> savePost(@RequestBody PostDTO postDTO) {
		return new ResponseEntity<>(crudService.save(postDTO), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable("id") long id) {
		return ResponseEntity.ok(crudService.getById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<PostDTO> updatePost(@PathVariable("id") long id, @RequestBody PostDTO postDto) {
		PostDTO result = crudService.update(postDto, id);
		return new ResponseEntity<PostDTO>(result, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable("id") long id) {
		return new ResponseEntity<String>("Post deleted successfully", HttpStatus.OK);
	}
}
