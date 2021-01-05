package com.cts.auth.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cts.auth.model.User;
import com.cts.auth.repository.UserRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info(username+"            hello");
		User custUser = userRepo.findById(username).orElse(null);
		return new org.springframework.security.core.userdetails.User(custUser.getUserName(), custUser.getPassword(), new ArrayList<>());
	}

	
}
