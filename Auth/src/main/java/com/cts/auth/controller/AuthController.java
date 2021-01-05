package com.cts.auth.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.auth.jwt.JwtUtil;
import com.cts.auth.model.AuthResponse;
import com.cts.auth.model.User;
import com.cts.auth.model.UserLoginCredential;
import com.cts.auth.model.UserToken;
import com.cts.auth.repository.UserRepo;
import com.cts.auth.service.CustomUserDetailsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/authapp")
public class AuthController {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private UserRepo userRepo;
	
	@PostMapping("/login")
	private ResponseEntity<?> login(@RequestBody UserLoginCredential userLoginCredential) {
		log.info(userLoginCredential.toString());
		final UserDetails userDetails = customUserDetailsService.loadUserByUsername(userLoginCredential.getUserId());
		return new ResponseEntity<>(new UserToken(userLoginCredential.getUserId(), jwtUtil.generateToken(userDetails)), HttpStatus.OK);
	}
	
	@GetMapping("/validate")
	public ResponseEntity<?> getValidity(@RequestHeader("Authorization") final String token) {
		String token1 = token.substring(7);
		AuthResponse res = new AuthResponse();
		if(jwtUtil.validateToken(token1)) {
			res.setUserId(jwtUtil.extractUsername(token1));
			res.setName((userRepo.findById(jwtUtil.extractUsername(token1)).orElse(null).getUserName()));
			res.setValid(true);
		} else {
			res.setValid(false);
		}
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	/*@GetMapping("/getCustomer/{userId}")
	public User getCustomer(@PathVariable String userId) {
		log.info(userId);
		User user = userRepo.getOne(userId);
		return user;
	}*/
	
}
