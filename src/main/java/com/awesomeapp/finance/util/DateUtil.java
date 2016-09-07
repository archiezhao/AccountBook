package com.awesomeapp.finance.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	/* A simple function to add a number of days to the current date */
	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); //minus number would decrement the days
		return cal.getTime();
	}
}
