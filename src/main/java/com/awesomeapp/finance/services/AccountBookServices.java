package com.awesomeapp.finance.services;

public class AccountBookServices {
	public AccountBookCalculator abCalculator;
	public AccountBookCategory abCategory;
	public AccountBookQuery abQuery;
	public AccountBookUser abUser;
	public AccountBookTransaction abTransaction;
	
	public void setCalculatorClass(AccountBookCalculator myClass) {
		this.abCalculator = myClass;
	}
	public void setCategoryClass(AccountBookCategory myClass) {
		this.abCategory = myClass;
	}
	public void setQueryClass(AccountBookQuery myClass) {
		this.abQuery = myClass;
	}
	public void setUserClass(AccountBookUser myClass) {
		this.abUser = myClass;
	}
	public void setTransactionClass(AccountBookTransaction myClass) {
		this.abTransaction = myClass;
	}
}
