package com.blog.system.controller;

import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.system.dto.JWTAuthResonseDTO;
import com.blog.system.dto.LoginDTO;
import com.blog.system.dto.UserDTO;
import com.blog.system.entity.Role;
import com.blog.system.entity.User;
import com.blog.system.repository.RoleRepository;
import com.blog.system.repository.UserRepository;
import com.blog.system.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@PostMapping("/signIn")
	public ResponseEntity<JWTAuthResonseDTO> authUser(@Valid @RequestBody LoginDTO loginDTO) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDTO.getUserNameOrEmail(), loginDTO.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtTokenProvider.generateToken(authentication);

		JWTAuthResonseDTO jwtAuthResonseDTO = new JWTAuthResonseDTO();
		jwtAuthResonseDTO.setAccessToken(token);

		return ResponseEntity.ok(jwtAuthResonseDTO);
	}

	@PostMapping("signUp")
	public ResponseEntity<?> signUp(@Valid @RequestBody UserDTO userDTO) {
		if (userRepository.existsByUserName(userDTO.getUserName())) {
			return new ResponseEntity<>("This username already exist", HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(userDTO.getEmail())) {
			return new ResponseEntity<>("This email already exist", HttpStatus.BAD_REQUEST);
		}

		User user = new User();
		user.setName(userDTO.getName());
		user.setUserName(userDTO.getUserName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

		Role roles = roleRepository.findByName("ROLE_ADMIN").get();

		user.setRoles(Collections.singleton(roles));

		return new ResponseEntity<>("User successfully registered", HttpStatus.OK);

	}
}
