package com.awesomeapp.finance.services;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.awesomeapp.finance.database.User;
import com.awesomeapp.finance.util.ErrorMessage;
import com.awesomeapp.finance.util.HibernateUtil;

public class AccountBookUser {
	private static SessionFactory factory = HibernateUtil.getSessionFactory();
	
	public String getMessage() {
		return "Running AccountBookUser";
	}
	
	public User createUser(String username, ErrorMessage message) {
		/* Validate the username */
		if(username.isEmpty() || username == null ) {
			if(message != null) message.setErrorMessage("Error creating User: the provided username is not valid (empty or null)");
			return null;
		}
		/* Check if the username has been taken */
		if(getUserByUsername(username, null) != null) {
			if(message != null) message.setErrorMessage("Error creating User: the provided username has already been taken");
			return null;
		}
		/* Creating the user */
		Session session = factory.openSession();
	    Transaction tx = null;
	    User user = null;
	    try {
	    	tx = session.beginTransaction();
	    	user = new User(username);
	    	session.save(user);
	    	tx.commit();
	    } catch (HibernateException e) {
	    	if (tx!=null) tx.rollback();
	    	e.printStackTrace(); 
	    }finally {
	    	session.close(); 
	    }
	    return user;
	}
	
	public User getUserByUsername(String username, ErrorMessage message) {
		/* Validate the username */
		if(username.isEmpty() || username == null ) {
			if(message != null) message.setErrorMessage("Error getting User by Username: the provided username is not valid (empty or null)");
			return null;
		}
		/* begin get user by username */
		User result = null;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query<?> query = session.createQuery("FROM User U WHERE U.username = :username").setParameter("username", username);
			List<?> users = query.getResultList();
			if(users.isEmpty() || users == null)
				result = null;
			else
				result = (User)users.get(0);
			tx.commit();
		} catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	    }finally {
	    	session.close(); 
	    }	
		return result;
	}
	
	public int deleteUserByForce(User user, ErrorMessage message) {
		/* delete the user and clear the user contents, return 0 (success) 1 (failure) */
		/* validate input user */
		if(user == null) {
			if(message != null) message.setErrorMessage("Error deleting user: the provided user object is null");
			return 1;
		}
		/* Check the user's existence */
		if(getUserByUsername(user.getUsername(), null) == null) {
			if(message != null) message.setErrorMessage("Error deleting user: the provided user is not existing in the records");
			return 1;
		}
		/* Start deleting user */
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(user);
			tx.commit();
		} catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
		}finally {
	         session.close(); 
		}
		return 0;
	}
}