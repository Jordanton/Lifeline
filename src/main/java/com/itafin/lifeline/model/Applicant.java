package com.itafin.lifeline.model;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

public class Applicant {
	private String mAccountNum;
	private String mSequenceNum;
	private String mSocialSecurityNum;
	private String mLastName;
	private String mMiddleName;
	private String mFirstName;
	private String mDWPAccountNum;
	private String mSystemUser;
	private String mTimestamp;
	private String mClassCode;
	private String mStatusCode;
	private String mStatusDate;
	private String mDateOfBirth;
	private String mGrossIncome;
	private String mDayPhone;
	private String mMoveInDate;
	private String mComments;
	private String mDateReceived;
	private String mMailingDate;
	private String mEmailAddress;
	
	private Address mMailingAddress;
	private Address mServiceAddress;
	
	private ArrayList<Utility> mUtilities;
	
	private MultipartFile statusDocument;
	private MultipartFile incomeDocument;
	
	public Applicant() {
		/* Compiler automatically provides a default constructor ONLY IF 
		 * you have defined no other constructors
		 * If you define a detailed constructor with arguments, like above
		 * You must explicitly define a default constructor */
	}
	
	public Applicant(String accountNum, String sequenceNum, String socialSecurityNum, 
		String lastName, String middleName, String firstName, String dwpAccountNum, String systemUser, 
		String timestamp, String classCode, String statusCode, String statusDate, String dateOfBirth, 
		String grossIncome, String dayPhone, String moveInDate, Address mailingAddress, Address serviceAddress, ArrayList<Utility> utilities) {
		mAccountNum = accountNum;
		mSequenceNum = sequenceNum;
		mSocialSecurityNum = socialSecurityNum;
		mLastName = lastName;
		mMiddleName = middleName;
		mFirstName = firstName;
		mDWPAccountNum = dwpAccountNum;
		mSystemUser = systemUser;
		mTimestamp = timestamp;
		mClassCode = classCode;
		mStatusCode = statusCode;
		mStatusDate = statusDate;
		mDateOfBirth = dateOfBirth;
		mGrossIncome = grossIncome;
		mDayPhone = dayPhone;
		mMoveInDate = moveInDate;
		mMailingAddress = mailingAddress;
		mServiceAddress = serviceAddress;
		mUtilities = utilities;
	}
	
	// Constructor without address parameters for testing
	public Applicant(String accountNum, String sequenceNum, String socialSecurityNum, 
		String lastName, String middleName, String firstName, String dwpAccountNum, String systemUser, 
		String timestamp, String classCode, String statusCode, String statusDate, String dateOfBirth, 
		String grossIncome, String dayPhone) {
		mAccountNum = accountNum;
		mSequenceNum = sequenceNum;
		mSocialSecurityNum = socialSecurityNum;
		mLastName = lastName;
		mMiddleName = middleName;
		mFirstName = firstName;
		mDWPAccountNum = dwpAccountNum;
		mSystemUser = systemUser;
		mTimestamp = timestamp;
		mClassCode = classCode;
		mStatusCode = statusCode;
		mStatusDate = statusDate;
		mDateOfBirth = dateOfBirth;
		mGrossIncome = grossIncome;
		mDayPhone = dayPhone;
	}	

	/* Accessors (getter methods) */
	public String getMailingDate() {
		return mMailingDate;
	}
	
	public ArrayList<Utility> getUtility() {
		return mUtilities;
	}
	
	public String getComments() {
		return mComments;
	}
	
	public String getDateReceived() {
		return mDateReceived;
	}
	
	public String getAccountNum() {
		return mAccountNum;
	}
	
	public String getSequenceNum() {
		return mSequenceNum;
	}
	
	public String getSocialSecurity() {
		return mSocialSecurityNum;
	}
	
	public String getLastName() {
		return mLastName;
	}
	
	public String getMiddleName() {
		return mMiddleName;
	}
	
	public String getFirstName() {
		return mFirstName;
	}
	
	public String getDWPAccountNum() {
		return mDWPAccountNum;
	}
	
	public String getSystemUser() {
		return mSystemUser;
	}
	
	public String getTimestamp() {
		return mTimestamp;
	}
	
	public String getClassCode() {
		return mClassCode;
	}
	
	public String getStatusCode() {
		return mStatusCode;
	}
	
	public String getStatusDate() {
		return mStatusDate;
	}
	
	public String getDateOfBirth() {
		return mDateOfBirth;
	}
	
	public String getGrossIncome() {
		return mGrossIncome;
	}
	
	public String getDayPhone() {
		return mDayPhone;
	}
	
	public Address getServiceAddress() {
		return mServiceAddress;
	}
	
	public String getMoveInDate() {
		return mMoveInDate;
	}
	
	public Address getMailingAddress() {
		return mMailingAddress;
	}
	
	public String getEmailAddress() {
		return mEmailAddress;
	}
	
	/* Mutators (setter methods) */
	public void setMailingDate(String mailingDate) {
		mMailingDate = mailingDate;
	}
	
	public void setUtility(ArrayList<Utility> utilities) {
		mUtilities = utilities;
	}
	
	public void setComments(String comments) {
		mComments = comments;
	}
	
	public void setDateReceived(String dateReceived) {
		mDateReceived = dateReceived;
	}
	
	public void setAccountNum(String accountNum) {
		mAccountNum = accountNum;
	}
	
	public void setSequenceNum(String sequenceNum) {
		mSequenceNum = sequenceNum;
	}
	
	public void setSocialSecurityNum(String socialSecurityNum) {
		mSocialSecurityNum = socialSecurityNum;
	}
	
	public void setLastName(String lastName) {
		mLastName = lastName;
	}
	
	public void setMiddleName(String middleName) {
		mMiddleName = middleName;
	}
	
	public void setFirstName(String firstName) {
		mFirstName = firstName;
	}
	
	public void setDWPAccountNum(String dwpAccountNum) {
		mDWPAccountNum = dwpAccountNum;
	}
	
	public void setSystemUser(String systemUser) {
		mSystemUser = systemUser;
	}
	
	public void setTimestamp(String timestamp) {
		mTimestamp = timestamp;
	}
	
	public void setClassCode(String classCode) {
		mClassCode = classCode;
	}
	
	public void setStatusCode(String statusCode) {
		mStatusCode = statusCode;
	}
	
	public void setStatusDate(String statusDate) {
		mStatusDate = statusDate;
	}
	
	public void setDateOfBirth(String dateOfBirth) {
		mDateOfBirth = dateOfBirth;
	}
	
	public void setGrossIncome(String grossIncome) {
		mGrossIncome = grossIncome;
	}
	
	public void setDayPhone(String dayPhone) {
		mDayPhone = dayPhone;
	}
	
	public void setServiceAddress(Address serviceAddress) {
		mServiceAddress = serviceAddress;
	}
	
	public void setMailingAddress(Address mailingAddress) {
		mMailingAddress = mailingAddress;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.mEmailAddress = emailAddress;
	}
	
	public void setMoveInDate(String moveInDate) {
		mMoveInDate = moveInDate;
	}
	
	public MultipartFile getStatusDocument() {
		return statusDocument;
	}

	public void setStatusDocument(MultipartFile statusDocument) {
		this.statusDocument = statusDocument;
	}
	
	public MultipartFile getIncomeDocument() {
		return incomeDocument;
	}

	public void setIncomeDocument(MultipartFile incomeDocument) {
		this.incomeDocument = incomeDocument;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		
		if(obj == null || obj.getClass() != this.getClass())
			return false;
		
		Applicant applicant = (Applicant) obj;
		if(this.mAccountNum != null && this.mAccountNum.equals(applicant.getAccountNum())) 
			return true;
		
		boolean ret = 
			(this.mFirstName == applicant.getFirstName() || ( this.mFirstName != null && this.mFirstName.equals(applicant.getFirstName()) )) &&
			(this.mLastName == applicant.getLastName() || ( this.mLastName != null && this.mLastName.equals(applicant.getLastName()) )) &&
			(this.mMiddleName == applicant.getFirstName() || ( this.mMiddleName != null && this.mMiddleName.equals(applicant.getMiddleName()) )) &&
			(this.mDateOfBirth == applicant.getDateOfBirth() || ( this.mDateOfBirth != null && this.mDateOfBirth.equals(applicant.getDateOfBirth()) )) &&
			(this.mSequenceNum == applicant.getSequenceNum() || ( this.mSequenceNum != null && this.mSequenceNum.equals(applicant.getSequenceNum()) )) &&
			(this.mServiceAddress == applicant.getServiceAddress() || ( applicant.getServiceAddress().equals(this.getServiceAddress()) ));
		
		return ret;
	}
	
	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + ((this.mFirstName == null) ? 0 : mFirstName.hashCode());
		result = prime * result + ((this.mLastName == null) ? 0 : mLastName.hashCode());
		result = prime * result + ((this.mMiddleName == null) ? 0 : mMiddleName.hashCode());
		result = prime * result + ((this.mAccountNum == null) ? 0 : mAccountNum.hashCode());
		result = prime * result + ((this.mDateOfBirth == null) ? 0 : mDateOfBirth.hashCode());
		return result;
	}
}