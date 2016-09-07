package com.awesomeapp.finance.services;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.awesomeapp.finance.database.Category;
import com.awesomeapp.finance.database.AccountTransaction;
import com.awesomeapp.finance.database.User;
import com.awesomeapp.finance.util.ErrorMessage;
import com.awesomeapp.finance.util.HibernateUtil;

public class AccountBookTransaction {
	private static SessionFactory factory = HibernateUtil.getSessionFactory();
	public String getMessage() {
		return "Running AccountBookTransaction Operations";
	}
	public AccountTransaction getTransactionById(Long transactionid) {
		/* begin get transaction by Id */
		AccountTransaction result = null;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query<?> query = session.createQuery("FROM AccountTransaction T WHERE T.transactionid = :t_id").setParameter("t_id", transactionid);
			List<?> transactions = query.getResultList();
			if(transactions.isEmpty() || transactions == null)
				result = null;
			else
				result = (AccountTransaction)transactions.get(0);
			tx.commit();
		} catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	    }finally {
	    	session.close(); 
	    }	
		return result;
	}
	
	public int addTransactionCredit(float amount, String title, String note, User user, Category category, ErrorMessage message) {
		/* Validate user and category */
		if(user == null) {
			if(message != null) message.setErrorMessage("Error adding Transaction, the uesr provided is not valid");
			return -1;
		}
		if(category == null) {
			if(message != null) message.setErrorMessage("Error adding Transaction, the category provided is not valid");
			return -2;
		}
		if(!category.getUser().getUsername().equals(user.getUsername())) {
			if(message != null) message.setErrorMessage("Error adding Transaction, the provided category and user aren't match");
			return -3;
		}
		Session session = factory.openSession();
		Transaction tx = null;
		Long transactionId = null;
		try {
			tx = session.beginTransaction();
			AccountTransaction transaction = new AccountTransaction(amount, title, note, user, category);
			transactionId = (Long)session.save(transaction);
			tx.commit();
		} catch(HibernateException e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return 0;
	}
	
	public int addTransactionDebit(float amount, String title, String note, User user, Category category, ErrorMessage message) {
		/* Validate user and category */
		if(user == null) {
			if(message != null) message.setErrorMessage("Error adding Transaction, the uesr provided is not valid");
			return -1;
		}
		if(category == null) {
			if(message != null) message.setErrorMessage("Error adding Transaction, the category provided is not valid");
			return -2;
		}
		if(!category.getUser().getUsername().equals(user.getUsername())) {
			if(message != null) message.setErrorMessage("Error adding Transaction, the provided category and user aren't match");
			return -3;
		}
		
		Session session = factory.openSession();
		Transaction tx = null;
		Long transactionId = null;
		try {
			tx = session.beginTransaction();
			AccountTransaction transaction = new AccountTransaction(-amount, title, note, user, category);
			transactionId = (Long)session.save(transaction);
			tx.commit();	
		} catch(HibernateException e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return 0;
	}
	
	public int deleteTransaction(AccountTransaction transaction, User user, ErrorMessage message) {
		/* validate the transaction and user */
		if(transaction == null) {
			if(message != null) message.setErrorMessage("Error deleting transaction, the transaction provided is null");
			return -1;
		}
		if(user == null) {
			if(message != null) message.setErrorMessage("Error deleting transaction, the user provided is null");
			return -2;
		}
		/* validate the "user" parameter to authorize the delete operation */
		if(!transaction.getUser().getUsername().equals(user.getUsername())) {
			if(message != null) message.setErrorMessage("Error deleting transaction, the provided user and transaction are not match");
			return -3;
		}
		/* delete the transaction */
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(transaction);
			tx.commit();
		} catch(HibernateException e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();		
		} finally {
			session.close();
		}
		return 0;
	}
	
	public int updateTransaction(AccountTransaction transaction, float amount, String title, String note, User user, Category category, ErrorMessage message) {
		/* validate the transaction and user and category */
		if(transaction == null) {
			if(message != null) message.setErrorMessage("Error updating transaction, the transaction provided is null");
			return -1;
		}
		if(user == null) {
			if(message != null) message.setErrorMessage("Error updating transaction, the user provided is null");
			return -2;
		}
		if(category == null) {
			if(message != null) message.setErrorMessage("Error updating transaction, the category provided is null");
			return -3;
		}
		/* validate the "user" parameter to authorize the update operation */
		if(!transaction.getUser().getUsername().equals(user.getUsername())) {
			if(message != null) message.setErrorMessage("Error updating transaction, the provided transaction and user are not match");
			return -4;
		}
		/* validate user and category */
		if(!category.getUser().getUsername().equals(user.getUsername())) {
			if(message != null) message.setErrorMessage("Error udpating transaction, the provided category and user are not match");
			return -5;
		}
		/* Update the transaction */
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			transaction.setAmount(amount);
			transaction.setCategory(category);
			transaction.setNote(note);
			transaction.setTitle(title);
			
			session.update(transaction);
			tx.commit();
		} catch(HibernateException e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}	
		return 0;
	}
}
