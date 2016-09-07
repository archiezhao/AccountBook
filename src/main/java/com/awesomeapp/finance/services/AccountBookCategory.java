package com.awesomeapp.finance.services;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.awesomeapp.finance.database.Category;
import com.awesomeapp.finance.database.User;
import com.awesomeapp.finance.util.ErrorMessage;
import com.awesomeapp.finance.util.HibernateUtil;

public class AccountBookCategory {
	private static SessionFactory factory = HibernateUtil.getSessionFactory();
	
	public String getMessage() {
		return "Running AccountBookCategory";
	}
	
	public Category getCategoryByCategoryname(String categoryname, ErrorMessage message) {
		/* Validate the categoryname */
		if(categoryname.isEmpty() || categoryname == null ) {
			if(message != null) message.setErrorMessage("Error getting Category by Categoryname: the provided categoryname is not valid (empty or null)");
			return null;
		}
		/* begin get category by categoryname */
		Category result = null;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query<?> query = session.createQuery("FROM Category C WHERE C.categoryname = :categoryname").setParameter("categoryname", categoryname);
			List<?> categories = query.getResultList();
			if(categories.isEmpty() || categories == null)
				result = null;
			else
				result = (Category)categories.get(0);
			tx.commit();
		} catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	    }finally {
	    	session.close(); 
	    }	
		return result;
	}
	
	public Category createCategory(String categoryname, User user, ErrorMessage message) {
		/* Validate the categoryname */
		if(categoryname.isEmpty() || categoryname == null ) {
			if(message != null) message.setErrorMessage("Error creating category: the provided categoryname is not valid (empty or null)");
			return null;
		}
		/* Check if the categoryname has been taken */
		if(getCategoryByCategoryname(categoryname, null) != null) {
			message.setErrorMessage("Error creating category: the provided categoryname has already been taken");
			return null;
		}
		/* Validate the user */
		if(user == null) {
			if(message != null) message.setErrorMessage("Error creating category: the provided user is null");
			return null;
		}
		/* Creating the category */
		Session session = factory.openSession();
	    Transaction tx = null;
	    Category category = null;
	    try {
	    	tx = session.beginTransaction();
	    	category = new Category(categoryname, user);
	    	session.save(category);
	    	tx.commit();
	    } catch (HibernateException e) {
	    	if (tx!=null) tx.rollback();
	    	e.printStackTrace(); 
	    }finally {
	    	session.close(); 
	    }
	    return category;
	}
	
	public int deleteCategoryByForce(Category category, User user, ErrorMessage message) {
		/* delete the category and clear the category contents, return 0 (success) 1 (failure) */
		/* validate input user */
		if(user == null) {
			if(message != null) message.setErrorMessage("Error deleting category: the provided user object is null");
			return 1;
		}
		/* validate input category */
		if(category == null) {
			if(message != null) message.setErrorMessage("Error deleting category: the provided category object is null");
			return 1;
		}
		/* Check the category's existence */
		if(getCategoryByCategoryname(category.getCategoryname(), null) == null) {
			if(message != null) message.setErrorMessage("Error deleting category: the provided category is not existing in the records");
			return 1;
		}
		/* validate user to authorize the delete operation */
		if(!category.getUser().getUsername().equals(user.getUsername())) {
			if(message != null) message.setErrorMessage("Error deleting category: the provided user and category are not match");
			return 1;
		}
		
		/* Start deleting category */
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(category);
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
