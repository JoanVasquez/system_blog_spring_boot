package com.blog.system.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

	private long id;

	@NotEmpty(message = "Name cannot be empty")
	private String name;

	@NotEmpty(message = "Email cannot be empty")
	private String email;

	@NotEmpty(message = "Body cannot empty")
	@Size(min = 10, message = "Body must be at least 10 characters")
	private String body;
	
	private long postId;

}
