package com.blog.system.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDTO {

	@NotEmpty(message = "user name or email cannot be empty")
	private String userNameOrEmail;

	@NotEmpty(message = "password cannot empty")
	private String password;

}
