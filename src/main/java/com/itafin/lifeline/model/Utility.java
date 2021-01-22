package com.itafin.lifeline.model;

import org.springframework.web.multipart.MultipartFile;

public class Utility {
	private Supplier mSupplier;
	
	private String mPhoneNum;
	private String mLastName;
	private String mFirstName;
	private String mMiddleInitial;
	private String mAccountNum;
	private String mIncludedInRent;	
	private String mDateReceived;
	private String mDateApproved;
	private String mDateSubmitted;
	private String mServiceRequested;
	private String mUtilityID;
	
	private int mHouseholdCount;
	
	private MultipartFile mDocument;
	
	public Utility() {
		mSupplier = null;
	}
	
	public Utility(Supplier supplier) {
		mSupplier = supplier;
	}
	
	public String getUtilityID() {
		return mUtilityID;
	}
	
	public int getHouseholdCount() {
		return mHouseholdCount;
	}
	
	public String getServiceRequested() {
		return mServiceRequested;
	}
	
	public String getDateApproved() {
		return mDateApproved;
	}
	
	public String getDateSubmitted() {
		return mDateSubmitted;
	}

	public String getDateReceived() {
		return mDateReceived;
	}
	
	public String getAccountNum() {
		return mAccountNum;
	}
	
	public String getIncludedInRent() {
		return mIncludedInRent;
	}
	
	public String getSupplierCode() {
		if(null != mSupplier)
			return mSupplier.getSupplierCode();
		else return null;
	}
	
	public String getPhoneNum() {
		return mPhoneNum;
	}
	
	public String getLastName() {
		return mLastName;
	}
	
	public String getFirstName() {
		return mFirstName;
	}
	
	public String getMiddleInitial() {
		return mMiddleInitial;
	}
	
	public Supplier getSupplier() {
		return mSupplier;
	}
	
	public void setUtilityID(String utilityID) {
		mUtilityID = utilityID;
	}
	
	public void setSupplier(Supplier s) {
		mSupplier = s;
	}
	
	public void setHouseholdCount(int count) {
		mHouseholdCount = count;
	}
	
	public void setServiceRequested(String serviceRequested) {
		mServiceRequested = serviceRequested;
	}
	
	public void setDateApproved(String dateApproved) {
		mDateApproved = dateApproved;
	}

	public void setDateReceived(String dateReceived) {
		mDateReceived = dateReceived;
	}
	
	public void setDateSubmitted(String dateSubmitted) {
		mDateSubmitted = dateSubmitted;
	}
	
	public void setAccountNum(String accountNum) {
		mAccountNum = accountNum;
	}
	
	public void setIncludedInRent(String includedInRent) {
		mIncludedInRent = includedInRent;
	}
	
	public void setSupplierCode(String supplierCode) {
		mSupplier.setSupplierCode(supplierCode);
	}
	
	public void setPhoneNum(String phoneNum) {
		mPhoneNum = phoneNum;
	}
	
	public void setLastName(String lastName) {
		mLastName = lastName;
	}
	
	public void setFirstName(String firstName) {
		mFirstName = firstName;
	}
	
	public void setMiddleInitial(String middleInitial) {
		mMiddleInitial = middleInitial;
	}

	public MultipartFile getDocument() {
		return mDocument;
	}

	public void setDocument(MultipartFile document) {
		this.mDocument = document;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		
		if(obj == null || obj.getClass() != this.getClass())
			return false;
		
		Utility utility = (Utility) obj;
		
		boolean ret = 
		(this.mFirstName == utility.getFirstName() || ( this.mFirstName != null && this.mFirstName.equals(utility.getFirstName()) )) &&
		(this.mLastName == utility.getLastName() || ( this.mLastName != null && this.mLastName.equals(utility.getLastName()) )) &&
		(this.mMiddleInitial == utility.getMiddleInitial() || ( this.mMiddleInitial != null && this.mMiddleInitial.equals(utility.getMiddleInitial()) )) &&
		(this.mAccountNum == utility.getAccountNum() || ( this.mAccountNum != null && this.mAccountNum.equals(utility.getAccountNum()) )) &&
		(this.mIncludedInRent == utility.getIncludedInRent() || ( this.mIncludedInRent != null && this.mIncludedInRent.equals(utility.getIncludedInRent()) )) &&
		(this.mServiceRequested == utility.getServiceRequested() || ( this.mServiceRequested != null && this.mServiceRequested.equals(utility.getServiceRequested()) )) &&
		(this.mSupplier == utility.getSupplier() || this.getSupplierCode() == utility.getSupplierCode() || 
			( this.mSupplier.getSupplierCode() != null && this.getSupplierCode().equals(utility.getSupplierCode())) ) &&
		(this.mDocument == utility.getDocument() || ( this.mDocument != null && this.mDocument.equals(utility.getDocument()) ));
		
		return ret;
	}
	
	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + ((this.mFirstName == null) ? 0 : mFirstName.hashCode());
		result = prime * result + ((this.mLastName == null) ? 0 : mLastName.hashCode());
		result = prime * result + ((this.mMiddleInitial == null) ? 0 : mMiddleInitial.hashCode());
		result = prime * result + ((this.mAccountNum == null) ? 0 : mAccountNum.hashCode());
		result = prime * result + ((this.mIncludedInRent == null) ? 0 : mIncludedInRent.hashCode());
		result = prime * result + ((this.mServiceRequested == null) ? 0 : mServiceRequested.hashCode());
		result = prime * result + ((this.mSupplier == null) ? 0 : getSupplierCode().hashCode());
		result = prime * result + ((this.mDocument == null) ? 0 : (int) mDocument.getSize());
		
		return result;
	}
}
