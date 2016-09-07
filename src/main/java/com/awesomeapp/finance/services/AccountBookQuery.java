package com.awesomeapp.finance.services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.awesomeapp.finance.database.AccountTransaction;
import com.awesomeapp.finance.database.Category;
import com.awesomeapp.finance.database.User;
import com.awesomeapp.finance.util.DateUtil;
import com.awesomeapp.finance.util.ErrorMessage;
import com.awesomeapp.finance.util.HibernateUtil;

public class AccountBookQuery {
	private static SessionFactory factory = HibernateUtil.getSessionFactory();
	public String getMessage() {
		return "Running AccountBookQuery";
	}
	
	public static List<?> searchTransactionByDate(String date, User user, Category category, ErrorMessage message) {
		/* Validate user */
		if(user == null) {
			message.setErrorMessage("Error searching transactions: the provided user is not valid");
			return null;
		}
		/* Validate category */
		if(category == null) {
			message.setErrorMessage("Error searching transactions: the provided category is not valid");
			return null;
		}
		/* Check if user and category match */
		if(category.getUser() == user) {
			message.setErrorMessage("Error searching transactions: the provided user and category are not match");
			return null;
		}
		/* Translate the provided date to java.util.Date format */
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		String dateInString = date + " 00:00:00";
		System.out.println("Date received: " + dateInString);
		Date startdate_formated = null;
		Date enddate_formated = null;
		try {
			startdate_formated = sdf.parse(dateInString);
			enddate_formated = DateUtil.addDays(startdate_formated, 1);
		} catch (ParseException e) {
			e.printStackTrace();
			if(message != null) message.setErrorMessage("Error searching transaction: the provided date can't be parsed properly");
			return null;
		}
		/* Start searching transactions */
		Session session = factory.openSession();
		Transaction tx = null;
		List<?> result = null;
		try {
			tx = session.beginTransaction();
			Query<?> query = session.createQuery("FROM AccountTransaction AS at WHERE at.time BETWEEN :startDate And :endDate");
			query.setParameter("startDate", startdate_formated);
			query.setParameter("endDate", enddate_formated);
			result = query.getResultList();
			
			tx.commit();
		} catch(HibernateException e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}
	
	public static List<?> searchTransactionByDate(String startdate, String enddate, User user, Category category, ErrorMessage message) {
		/* Validate user */
		if(user == null) {
			message.setErrorMessage("Error searching transactions: the provided user is not valid");
			return null;
		}
		/* Validate category */
		if(category == null) {
			message.setErrorMessage("Error searching transactions: the provided category is not valid");
			return null;
		}
		/* Check if user and category match */
		if(category.getUser() == user) {
			message.setErrorMessage("Error searching transactions: the provided user and category are not match");
			return null;
		}
		/* Translate the provided date to java.util.Date format */
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		String startdateInString = startdate + " 00:00:00";
		String enddateInString = enddate + " 00:00:00";
		Date startdate_formated = null;
		Date enddate_formated = null;
		try {
			startdate_formated = sdf.parse(startdateInString);
			enddate_formated = sdf.parse(enddateInString);
		} catch (ParseException e) {
			e.printStackTrace();
			message.setErrorMessage("Error searching transaction: the provided date can't be parsed properly");
			return null;
		}
		/* Start searching transactions */
		Session session = factory.openSession();
		Transaction tx = null;
		List<?> result = null;
		try {
			tx = session.beginTransaction();
			Query<?> query = session.createQuery("FROM AccountTransaction AS at WHERE at.time BETWEEN :startDate And :endDate");
			query.setParameter("startDate", startdate_formated);
			query.setParameter("endDate", enddate_formated);
			result = query.getResultList();
			
			tx.commit();
		} catch(HibernateException e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}
}
