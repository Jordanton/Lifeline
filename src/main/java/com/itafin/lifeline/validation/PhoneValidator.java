package com.itafin.lifeline.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component("phoneValidator")
public class PhoneValidator {
	
	private Pattern pattern;
	private Matcher matcher;
	
	//US phone number, of the form ANNNNNNNNN, where A is between 2 and 9 and N is between 0 and 9
	private static final String PHONE_PATTERN_US = "^[2-9]\\d{2}\\d{3}\\d{4}$";
	
	public PhoneValidator() {
		pattern = Pattern.compile(PHONE_PATTERN_US);
	}
	
	public boolean valid(final String phone) {
		matcher = pattern.matcher(phone);
		return matcher.matches();
	}
}
