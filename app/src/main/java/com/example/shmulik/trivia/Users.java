package com.example.shmulik.trivia;

import java.io.Serializable;

public class Users implements Serializable{


	private String name;
	private String email;
	private String password;


    public Users(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;

    }

    public Users()
	{

	}

    public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
