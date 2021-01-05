package com.cts.auth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@Column(name = "userid", length = 20)
	private String userId;
	@Column(name = "uname", length = 20)
	private String userName;
	@Column(name = "upassword", length = 20)
	private String password;
	
}
