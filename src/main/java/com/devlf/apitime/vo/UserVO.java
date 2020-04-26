package com.devlf.apitime.vo;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserVO implements Serializable {
	private static final long serialVersionUID = 448314093358674032L;
	private int id = 0;
	private String email = null;
	private String token = null;
	private Timestamp lastAcess = null;
	private String password = null;
	private String name = null;
	private String tokenEmail = null;

	public String getTokenEmail() {
		return tokenEmail;
	}

	public void setTokenEmail(String tokenEmail) {
		this.tokenEmail = tokenEmail;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getLastAcess() {
		return lastAcess;
	}

	public void setLastAcess(Timestamp lastAcess) {
		this.lastAcess = lastAcess;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
