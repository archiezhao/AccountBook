package com.awesomeapp.finance.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.awesomeapp.finance.database.*;
import com.awesomeapp.finance.services.AccountBookQuery;
import com.awesomeapp.finance.services.AccountBookServices;
import com.awesomeapp.finance.util.ErrorMessage;
import com.awesomeapp.finance.util.JsonUtil;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RestController
@RequestMapping("/service")
public class ServiceController {
	private AccountBookServices obj;
	ServiceController() {
		super();
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		obj = (AccountBookServices) context.getBean("accountBookServices");
	}
	
	@RequestMapping(value = "/addUser", method = RequestMethod.GET)
	public String addUser(@RequestParam("username") String username) {
		User user = obj.abUser.createUser(username, null);
		if(user == null) {
			return "The username provided is already taken or invalid, please try again";
		}
		return "The user created successfully";
	}
	
	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
	public String deleteUser(@RequestParam("username") String username) {
		User user = obj.abUser.getUserByUsername(username, null);
		if(user == null) {
			return "The user " + username + " cannot be found";
		}
		int err = obj.abUser.deleteUserByForce(user, null);
		if(err == 0) {
			return "The user deleted successfully";
		}
		else {
			return "User deletion failed, error code is " + err;
		}	
	}
	
	@RequestMapping(value = "/addCategory", method = RequestMethod.GET)
	public String addCategory(@RequestParam("username") String username,
							  @RequestParam("categoryname") String categoryname) {
		User user = obj.abUser.getUserByUsername(username, null);
		if(user == null) {
			return "The username provided is not valid or not existing, please try again";
		}
		Category category = obj.abCategory.createCategory(categoryname, user, null);
		if(category == null) {
			return "The category cannot be created due to (1)The categoryname is invalid (2)The categoryname has already been taken (3)The provided user is not valid";
		}
		return "The category created successfully";
	}
	
	@RequestMapping(value = "/deleteCategory", method = RequestMethod.GET)
	public String deleteCategory(@RequestParam("categoryname") String categoryname,
								 @RequestParam("username") String username) {
		User user = obj.abUser.getUserByUsername(username, null);
		if(user == null) {
			return "The user " + username + " cannot be found";
		}
		Category category = obj.abCategory.getCategoryByCategoryname(categoryname, null);
		if(category == null) {
			return "The category " + categoryname + " cannot be found";
		}
		int err = obj.abCategory.deleteCategoryByForce(category, user, null);
		if(err == 0) {
			return "The category deleted successfully";
		}
		else {
			return "Category deletion failed, error code is " + err;
		}	
	}
	
	@RequestMapping(value = "/addTransaction", method = RequestMethod.GET)
	public String addTransaction(@RequestParam("title") String title,
							  	 @RequestParam("note") String note,
							  	 @RequestParam("amount") String amount,
							  	 @RequestParam("type") String type,
							  	 @RequestParam("username") String username,
							  	 @RequestParam("category") String categoryname) {
		//System.out.println("received an addTransaction request");
		/* Get the user by username */
		User user = obj.abUser.getUserByUsername(username, null);
		/* Get the category by categoryname */
		Category category = obj.abCategory.getCategoryByCategoryname(categoryname, null);
		if(user == null) {
			return "User " + username + " not existing";
		}
		if(category == null) {
			return "Category " + categoryname + " not existing";
		}
		if(type.equals("credit")) {
			int err = obj.abTransaction.addTransactionCredit(Float.parseFloat(amount), title, note, user, category, null);
			if(err == 0)
				return "transaction added successfully";
			else
				return "transaction failed to be added, error code is " + err;
		}	
		else if(type.equals("debit")) {
			int err = obj.abTransaction.addTransactionDebit(Float.parseFloat(amount), title, note, user, category, null);
			if(err == 0)	
				return "transaction added successfully";
			else
				return "transaction failed to be added, error code is " + err;
		}	
		else {
			return "unknown type";
		}
	}
	
	@RequestMapping(value = "/updateTransaction", method = RequestMethod.GET)
	public String updateTransaction(@RequestParam("transactionid") String transactionid,
									@RequestParam("title") String title,
									@RequestParam("note") String note,
									@RequestParam("amount") String amount,
									@RequestParam("type") String type,
									@RequestParam("username") String username,
									@RequestParam("category") String categoryname) {
		
		/* Get the user by username */
		User user = obj.abUser.getUserByUsername(username, null);
		/* Get the category by categoryname */
		Category category = obj.abCategory.getCategoryByCategoryname(categoryname, null);
		/* Get the transaction by transactionid */
		AccountTransaction tran = obj.abTransaction.getTransactionById(Long.parseLong(transactionid));
		if(user == null) {
			return "User " + username + " is not existing";
		}
		if(category == null) {
			return "Category " + categoryname + " is not existing";
		}
		if(tran == null) {
			return "Transaction " + transactionid + " is not existing";
		}
		if(type.equals("credit")) {
			int err = obj.abTransaction.updateTransaction(tran, Float.parseFloat(amount), title, note, user, category, null);
			if(err == 0)
				return "transaction updated successfully";
			else
				return "transaction update failed, error code is " + err;
		}	
		else if(type.equals("debit")) {
			int err = obj.abTransaction.updateTransaction(tran, -Float.parseFloat(amount), title, note, user, category, null);
			if(err == 0)
				return "transaction updated successfully";
			else
				return "transaction update failed, error code is " + err;
		}	
		else {
			return "unknown type";
		}
	}
	
	@RequestMapping(value = "/deleteTransaction", method = RequestMethod.GET)
	public String deleteTransaction(@RequestParam("transactionid") String transactionid,
									@RequestParam("username") String username) {
		/* Get the user by username */
		User user = obj.abUser.getUserByUsername(username, null);
		/* Get the transaction by transactionid */
		AccountTransaction tran = obj.abTransaction.getTransactionById(Long.parseLong(transactionid));
		if(user == null) {
			return "User " + username + " is not existing";
		}
		if(tran == null) {
			return "Cannot find transaction " + transactionid;
		}
		if(obj.abTransaction.deleteTransaction(tran, user, null) == 0) {
			return "Transaction deleted successfully";
		}
		else
			return "Fail to delete transaction";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchTransactionByDate", method = RequestMethod.GET)
	public String searchTransactionByDate(@RequestParam("date") String date,
										  @RequestParam("category") String categoryname,
										  @RequestParam("username") String username) {
		List<AccountTransaction> tranList = null;
		/* Get user by username */
		User user = obj.abUser.getUserByUsername(username, null);
		/* Get category by categoryname */
		Category category = obj.abCategory.getCategoryByCategoryname(categoryname, null);
		
		if(user == null) {
			return "The user provided does not exist";
		}
		if(category == null) {
			return "The category provided does not exist";
		}
		/* Start querying transaction */
		ErrorMessage msg = new ErrorMessage();
		tranList = (List<AccountTransaction>) AccountBookQuery.searchTransactionByDate(date, user, category, msg);

		/* Get json object of the transaction list */
		JSONObject res = JsonUtil.getAccountTransactionJson(tranList);
		
		return res.toString();
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchTransactionByDateRange", method = RequestMethod.GET)
	public String searchTransactionByDate(@RequestParam("startdate") String startdate,
										  @RequestParam("enddate") String enddate,
										  @RequestParam("category") String categoryname,
										  @RequestParam("username") String username) {
		List<AccountTransaction> tranList = null;
		/* Get user by username */
		User user = obj.abUser.getUserByUsername(username, null);
		/* Get category by categoryname */
		Category category = obj.abCategory.getCategoryByCategoryname(categoryname, null);
		
		if(user == null) {
			return "The user provided does not exist";
		}
		if(category == null) {
			return "The category provided does not exist";
		}
		/* Start querying transaction */
		tranList = (List<AccountTransaction>) AccountBookQuery.searchTransactionByDate(startdate, enddate, user, category, null);
		/* Get json object of the transaction list */
		JSONObject res = JsonUtil.getAccountTransactionJson(tranList);
		
		return res.toString();
	}
	
	@RequestMapping(value = "/totalTransaction", method = RequestMethod.GET) 
	public String totalTransactionByDateRange(@RequestParam("startdate") String startdate,
										 @RequestParam("enddate") String enddate,
										 @RequestParam("username") String username,
										 @RequestParam("category") String categoryname) {
		float total = 0;
		/* Get user by username */
		User user = obj.abUser.getUserByUsername(username, null);
		if(user == null) {
			return "The user provided does not exist";
		}
		/* Get category by categoryname */
		Category category = obj.abCategory.getCategoryByCategoryname(categoryname, null);
		if(category == null) {
			return "The category provided does not exist";
		}
		/* Start calculating total amount */
		total = obj.abCalculator.TotalAmount(startdate, enddate, user, category, null);
		
		return "" + total;
	}
	
	@RequestMapping(value = "totalCreditTransaction", method = RequestMethod.GET)
	public String totalCreditTransactionByDate(@RequestParam("startdate") String startdate,
											   @RequestParam("enddate") String enddate,
											   @RequestParam("username") String username,
											   @RequestParam("category") String categoryname) {
		float total = 0;
		/* Get user by username */
		User user = obj.abUser.getUserByUsername(username, null);
		if(user == null) {
			return "The user provided does not exist";
		}
		/* Get category by categoryname */
		Category category = obj.abCategory.getCategoryByCategoryname(categoryname, null);
		if(category == null) {
			return "The category provided does not exist";
		}
		/* Start calculating total amount */
		total = obj.abCalculator.TotalAmountCredit(startdate, enddate, user, category, null);
		
		return "" + total;
	}
	
	@RequestMapping(value = "totalDebitTransaction", method = RequestMethod.GET)
	public String totalDebitTransactionByDate(@RequestParam("startdate") String startdate,
											  @RequestParam("enddate") String enddate,
											  @RequestParam("username") String username,
											  @RequestParam("category") String categoryname) {
		float total = 0;
		/* Get user by username */
		User user = obj.abUser.getUserByUsername(username, null);
		if(user == null) {
			return "The user provided does not exist";
		}
		/* Get category by categoryname */
		Category category = obj.abCategory.getCategoryByCategoryname(categoryname, null);
		if(category == null) {
			return "The category provided does not exist";
		}
		/* Start calculating total amount */
		total = obj.abCalculator.TotalAmountDebit(startdate, enddate, user, category, null);
		
		return "" + total;
	}
	
	
}
