package com.itafin.lifeline.validation;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.itafin.lifeline.model.Applicant;
import com.itafin.lifeline.model.Utility;
import com.itafin.lifeline.utils.LifelineProperties;

@Component
public class ApplicantFormValidator implements Validator {

	@Autowired LifelineProperties lifelineProps;
	
	@Autowired EmailValidator emailValidator;
	@Autowired PhoneValidator phoneValidator;
	
	private static final int SENIOR_AGE_LIMIT = 62;
	
	private boolean validateDocuments = false;
	
	public ApplicantFormValidator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Applicant.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Applicant applicant = (Applicant) target;
		
		// Validate name fields
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.applicantForm.lastName");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.applicantForm.firstName");
		if(applicant.getMiddleName().length() > 1) {
			errors.rejectValue("middleName", "Format.applicantForm.middleName");
		}
		
		// Validate email address
		// OOF do not want to require email address
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAddress", "NotEmpty.applicantForm.emailAddress");
		if((null != applicant.getEmailAddress() && !applicant.getEmailAddress().isEmpty()) ){
			if(!emailValidator.valid(applicant.getEmailAddress())) {
				errors.rejectValue("emailAddress", "Format.applicantForm.emailAddress");
			}
		}
		
		if(!("S".equals(applicant.getClassCode()) || "D".equals(applicant.getClassCode()) )) {
			errors.rejectValue("classCode", "Valid.applicantForm.generic");
		}
		
		// Validate dob
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateOfBirth", "NotEmpty.applicantForm.dayOfBirth");
		if("S".equalsIgnoreCase(applicant.getClassCode())) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				formatter.setLenient(false);
				String dob = applicant.getDateOfBirth();
				Date date = formatter.parse(dob);
				SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
				int birthYear = Integer.valueOf(yearFormat.format(date));
				int currentYear = Integer.valueOf(yearFormat.format(new Date()));
				if(currentYear - birthYear < SENIOR_AGE_LIMIT) {
					errors.rejectValue("dateOfBirth", "Valid.applicantForm.dayOfBirth");
				}
			} catch (ParseException e) {
				errors.rejectValue("dateOfBirth", "Format.applicantForm.dayOfBirth");
				e.printStackTrace();
			}
		}
		
		// Validate phone number
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dayPhone", "NotEmpty.applicantForm.phone");
		if(null != applicant.getDayPhone() && !applicant.getDayPhone().trim().isEmpty()) {
			String originalDayPhone = applicant.getDayPhone();
			String cleanDayPhone = originalDayPhone.replaceAll("[^0-9]","");
			if(!phoneValidator.valid(cleanDayPhone)) {
				errors.rejectValue("dayPhone", "Format.applicantForm.phone");
			}
		}
		
		if(validateDocuments) {
			// Validate status document file size (senior or disability status)
			if(documentSizeIsValid(applicant.getStatusDocument())) {	// check if file is selected and is not too large
				if(!documentExtensionIsValid(applicant.getStatusDocument())) {
					errors.rejectValue("statusDocument", "FileExtension.applicantForm.generic");
				}
			} else {
				if(applicant.getStatusDocument().getSize() == 0) {
					errors.rejectValue("statusDocument", "NotEmpty.applicantForm.document");
				} else {
					errors.rejectValue("statusDocument", "FileSize.applicantForm.generic");
				}
			}
			
			// Validate income document
			if(documentSizeIsValid(applicant.getIncomeDocument())) {	// check if file is selected and is not too large
				if(!documentExtensionIsValid(applicant.getIncomeDocument())) {
					errors.rejectValue("incomeDocument", "FileExtension.applicantForm.generic");
				}
			} else {
				if(applicant.getIncomeDocument().getSize() == 0) {
					errors.rejectValue("incomeDocument", "NotEmpty.applicantForm.document");
				} else {
					errors.rejectValue("incomeDocument", "FileSize.applicantForm.generic");
				}
			}
		}
		
		// note: consider implementing rest calls to AddressValidation api at later date, and consolidating fields on the form
		// into just [address line 1, address line 2, city, state, zip] then parsing out the fields later
		// Validate service address
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serviceAddress.streetNum", "NotEmpty.applicantForm.generic");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serviceAddress.streetName", "NotEmpty.applicantForm.generic");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serviceAddress.streetType", "NotEmpty.applicantForm.generic");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serviceAddress.city", "NotEmpty.applicantForm.generic");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serviceAddress.state", "NotEmpty.applicantForm.generic");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serviceAddress.zipCode", "NotEmpty.applicantForm.generic");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serviceAddress.residenceType", "NotEmpty.applicantForm.generic");
		if(!"S".equals(applicant.getServiceAddress().getResidenceType()) && !"M".equals(applicant.getServiceAddress().getResidenceType()) ) {
			errors.rejectValue("serviceAddress.residenceType", "Valid.applicantForm.generic");
		}
		if("M".equals(applicant.getServiceAddress().getResidenceType())) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serviceAddress.mobileParkName", "NotEmpty.applicantForm.generic");
		}
		// Validate mailing address
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mailingAddress.streetNum", "NotEmpty.applicantForm.generic");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mailingAddress.streetName", "NotEmpty.applicantForm.generic");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mailingAddress.streetType", "NotEmpty.applicantForm.generic");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mailingAddress.city", "NotEmpty.applicantForm.generic");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mailingAddress.state", "NotEmpty.applicantForm.generic");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mailingAddress.zipCode", "NotEmpty.applicantForm.generic");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mailingAddress.residenceType", "NotEmpty.applicantForm.generic");
		if(!"S".equals(applicant.getMailingAddress().getResidenceType()) && !"M".equals(applicant.getMailingAddress().getResidenceType()) ) {
			errors.rejectValue("mailingAddress.residenceType", "Valid.applicantForm.generic");
		}
		if("M".equals(applicant.getMailingAddress().getResidenceType())) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mailingAddress.mobileParkName", "NotEmpty.applicantForm.generic");
		}
		
		Utility blankUtil = new Utility();	// compare to blank utility to check if empty
		int emptyUtilCount = 0;
		boolean dwpUtilFilled = false;
		if(null != applicant.getUtility()) { 
			for(int i = 0; i < applicant.getUtility().size(); i++) {
				String prefix = "utility[" + i;
				String fieldName;
				Utility util = applicant.getUtility().get(i);
				if(!util.equals(blankUtil)) { // if util isn't empty
					// Validate name fields
					fieldName = prefix + "].lastName";
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, "NotEmpty.applicantForm.lastName");
					fieldName = prefix + "].firstName";
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, "NotEmpty.applicantForm.firstName");
					fieldName = prefix + "].middleInitial";
					if(util.getMiddleInitial().length() > 1) {
						errors.rejectValue(fieldName, "Format.applicantForm.middleName");
					}
					
					// Check if DWP utility is present. OOF wants to require only DWP
					if("DWP".equals(util.getSupplierCode())) {
						dwpUtilFilled = true;
					}
					
					// Validate account number and included in rent for DWP and SCG
					if("DWP".equals(util.getSupplierCode()) || "SCG".equals(util.getSupplierCode()) ) {
						fieldName = prefix + "].accountNum";
						ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, "NotEmpty.applicantForm.accountNum");
						
						// SCG Account number limited to 11 characters
						if("SCG".equals(util.getSupplierCode())) {
							fieldName = prefix + "].accountNum";
							if(util.getAccountNum().length() > 11) {
								errors.rejectValue(fieldName, "CharLimit.applicantForm.utilities.SCG");
							}
						}
						
						fieldName = prefix + "].includedInRent";
						if(!("Y".equals(util.getIncludedInRent()) || "N".equals(util.getIncludedInRent()) )) {
							errors.rejectValue(fieldName, "Valid.applicantForm.generic");
						}
						ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, "NotEmpty.applicantForm.generic");
						// Validate number of people in household and service requested for DWP
						if("DWP".equals(util.getSupplierCode())) {
							fieldName = prefix + "].householdCount";
							ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, "NotEmpty.applicantForm.generic");
							if(util.getHouseholdCount() <= 0) {
								errors.rejectValue(fieldName, "Valid.applicantForm.householdCount");
							}
							
							fieldName = prefix + "].serviceRequested";
							ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, "NotEmpty.applicantForm.generic");
							if(!("E".equals(util.getServiceRequested()) || "W".equals(util.getServiceRequested())
									|| "B".equals(util.getServiceRequested()) || "L".equals(util.getServiceRequested() ))) {
								errors.rejectValue(fieldName, "Valid.applicantForm.generic");
							}
						}
					} else {
						// Validate that supplier code and phone number are not empty for landline and cellular
						fieldName = prefix + "].supplier.supplierCode";
						ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, "NotEmpty.applicantForm.generic");
						
						fieldName = prefix +"].phoneNum";
						ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, "NotEmpty.applicantForm.phone");
						if(!phoneValidator.valid(util.getPhoneNum())) {
							errors.rejectValue(fieldName, "Format.applicantForm.phone");
						}
						
					}
					
					if(validateDocuments) {
						// Validate document
						fieldName = prefix + "].document";
						MultipartFile document = util.getDocument();
						if(documentSizeIsValid(document)) {	// check if file is selected and is not too large
							if(!documentExtensionIsValid(document)) {
								errors.rejectValue(fieldName, "FileExtension.applicantForm.generic");
							}
						} else {
							if(document.getSize() == 0) {
								errors.rejectValue(fieldName, "NotEmpty.applicantForm.document");
							} else {
								errors.rejectValue(fieldName, "FileSize.applicantForm.generic");
							}
						}
					}
				} else {
					emptyUtilCount++;
				}
			}
		} else {	// no utilities, reject utilities?
			errors.rejectValue("utility", "NotEmpty.applicantForm.utilities");
		}
		
		// had to implement this because a change to controller made the form supply "ghost" utilities that are blank but still create utility object
	 // also check if DWP is filled out
		if(emptyUtilCount >= 4 || !dwpUtilFilled) {
			errors.rejectValue("utility", "NotEmpty.applicantForm.utilities");
		}
		
	}
	
	private boolean documentSizeIsValid(MultipartFile doc) {

		if(null != doc && doc.getSize() > 0 && doc.getSize() <= lifelineProps.getMaxUploadFileSize()) {
			return true;
		} 
		return false;
	}
	
	// this method won't validate against someone renaming their file to have a correct extension, unfortunately
	// TO-DO looking into MIMEtype validation
	private boolean documentExtensionIsValid(MultipartFile doc) {
		String filename = doc.getOriginalFilename();
		String extension = FilenameUtils.getExtension(filename).toUpperCase();
		if(null != doc && lifelineProps.getDocumentAllowedFileTypes().contains(extension)) {
			
			// also checking content type
			String mimeType = doc.getContentType();
			if(mimeType.contains("pdf") || mimeType.contains("image"))
				return true;
		}
		return false;
	}
	
	public void setValidateDocuments(boolean set) {
		this.validateDocuments = set;
	}
	
	public boolean isValidateDocuments() {
		return this.validateDocuments;
	}

}
