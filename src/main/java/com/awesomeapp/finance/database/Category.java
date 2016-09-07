package com.awesomeapp.finance.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Category")
public class Category {
	@Id
    @GeneratedValue
    private Long categoryid;
	
	private String categoryname;
	
	@ManyToOne
	private User user;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="category", orphanRemoval=true)
	private List<AccountTransaction> accountTransactions = new ArrayList<AccountTransaction>();
	
	public Category(String name, User user) {
		this.categoryname = name;
		this.user = user;
	}
	
	public Category() {};
	
	public String getCategoryname() {
		return this.categoryname;
	}
	
	public void setCategoryname(String name) {
		this.categoryname = name;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
