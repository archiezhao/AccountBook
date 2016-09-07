package com.awesomeapp.finance.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="User")
public class User {
	@Id
    @GeneratedValue
    private Long userid;
	
	private String username;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="user", orphanRemoval=true)
	private List<Category> categories = new ArrayList<Category>();
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="user", orphanRemoval=true)
	private List<AccountTransaction> transactions = new ArrayList<AccountTransaction>();
	
	
	public User(String name) {
		this.username = name;
	}
	
	public User() {};
	
	public long getUserid() {
		return this.userid;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String name) {
		this.username = name;
	}
}
