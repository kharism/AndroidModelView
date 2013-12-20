package com.khar.isframework.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * validate date
 * @author kharisma
 *
 */
public class DateValidator extends BasicValidator{
	String dateFormat;
	public DateValidator(String attributeName){
		super(attributeName,"Not a valid date format");
		this.dateFormat = "yyyy-MM-dd";
	}
	public DateValidator(String attributeName,String dateFormat){
		super(attributeName,"Not a valid date format");
		this.dateFormat = dateFormat;
	}
	/**
	 * validate String
	 */
	public boolean validate(Object i) {
		SimpleDateFormat f = new SimpleDateFormat(dateFormat);
		f.setLenient(false);
		try {
			 Date date = f.parse((String) i);
		} catch (ParseException e) {
			
			e.printStackTrace();
			return false;
		}
 
		return true;
	}

}
