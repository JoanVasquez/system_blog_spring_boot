package com.blog.system.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

	private Long id;

	@NotEmpty(message = "The title cannot be empty")
	@Size(min = 2, message = "The title must be at least 2 characters")
	private String title;

	@NotEmpty(message = "The description cannot be empty")
	@Size(min = 10, message = "The description must be at least 10 characters")
	private String description;

	@NotEmpty(message = "The content cannot be empty")
	private String content;

}
