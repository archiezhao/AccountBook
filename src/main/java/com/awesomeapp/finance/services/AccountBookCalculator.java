package com.awesomeapp.finance.services;

import java.util.List;

import com.awesomeapp.finance.database.AccountTransaction;
import com.awesomeapp.finance.database.Category;
import com.awesomeapp.finance.database.User;
import com.awesomeapp.finance.util.ErrorMessage;

public class AccountBookCalculator {

	public String getMessage() {
		return "Running AccountBookCalculator";
	}
	
	public float TotalAmountCredit(String date, User user, Category category, ErrorMessage message) {
		@SuppressWarnings("unchecked")
		List<AccountTransaction> transactionList = (List<AccountTransaction>) AccountBookQuery.searchTransactionByDate(date, user, category, message);
		float total = 0;
		
		for(AccountTransaction at : transactionList) {
			if(at.getAmount() > 0)
				total += at.getAmount();
		}
		return total;
	}
	
	public float TotalAmountCredit(String startdate, String enddate, User user, Category category, ErrorMessage message) {
		@SuppressWarnings("unchecked")
		List<AccountTransaction> transactionList = (List<AccountTransaction>) AccountBookQuery.searchTransactionByDate(startdate, enddate, user, category, message);
		float total = 0;
		
		for(AccountTransaction at : transactionList) {
			if(at.getAmount() > 0)
				total += at.getAmount();
		}
		return total;
	}
	
	public float TotalAmountDebit(String date, User user, Category category, ErrorMessage message) {
		@SuppressWarnings("unchecked")
		List<AccountTransaction> transactionList = (List<AccountTransaction>) AccountBookQuery.searchTransactionByDate(date, user, category, message);
		float total = 0;
		
		for(AccountTransaction at : transactionList) {
			if(at.getAmount() < 0)
				total += at.getAmount();
		}
		return total;
	}
	
	public float TotalAmountDebit(String startdate, String enddate, User user, Category category, ErrorMessage message) {
		@SuppressWarnings("unchecked")
		List<AccountTransaction> transactionList = (List<AccountTransaction>) AccountBookQuery.searchTransactionByDate(startdate, enddate, user, category, message);
		float total = 0;
		
		for(AccountTransaction at : transactionList) {
			if(at.getAmount() < 0)
				total += at.getAmount();
		}
		return total;
	}
	
	public float TotalAmount(String date, User user, Category category, ErrorMessage message) {
		@SuppressWarnings("unchecked")
		List<AccountTransaction> transactionList = (List<AccountTransaction>) AccountBookQuery.searchTransactionByDate(date, user, category, message);
		float total = 0;
		
		for(AccountTransaction at : transactionList) {
			total += at.getAmount();
		}
		return total;
	}
	
	public float TotalAmount(String startdate, String enddate, User user, Category category, ErrorMessage message) {
		@SuppressWarnings("unchecked")
		List<AccountTransaction> transactionList = (List<AccountTransaction>) AccountBookQuery.searchTransactionByDate(startdate, enddate, user, category, message);
		float total = 0;
		
		for(AccountTransaction at : transactionList) {
			total += at.getAmount();
		}
		return total;
	}
}
