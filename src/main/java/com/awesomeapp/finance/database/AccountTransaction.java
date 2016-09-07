package com.awesomeapp.finance.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.sql.Date;
import java.util.Calendar;

@Entity
@Table(name="AccountTransaction")
public class AccountTransaction {
	@Id
    @GeneratedValue
    private Long transactionid;
	
	private float amount;
	private Date time;
	private String title;
	private String note;
	
	@ManyToOne
    private User user;
	
	@ManyToOne
	private Category category;
	
	public AccountTransaction(float amount, String title, String note, User user, Category category) {
		this.amount = amount;
		this.title = title;
		this.note = note;
		this.user = user;
		this.category = category;
		
		Calendar calendar = Calendar.getInstance();
		this.time = new Date(calendar.getTime().getTime());
	}
	
	public AccountTransaction() {};
	
	public Long getTransactionId() {
		return this.transactionid;
	}
	
	public float getAmount() {
		return this.amount;
	}
	
	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	public Date getTime() {
		return this.time;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getNote() {
		return this.note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Category getCategory() {
		return this.category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	
}
