package com.khar.isframework.validator;

/**
 * basic validator class that always return true
 * @author kharisma
 *
 */
public class BasicValidator implements Validator {
	
	String attrName;
	String erroMessage;
	public BasicValidator(String attributeName,String errorMessage) {
		this.attrName = attributeName;
		this.erroMessage = errorMessage;
	}

	@Override
	public boolean validate(Object i) {
		return true;
	}

	@Override
	public String getErrorMesage() {
		return erroMessage;
	}

}
