package com.itafin.lifeline.model;

public class Supplier {
	private String mSupplierName;
	private String mSupplierCode;
	private String mUser; // The user who added this supplier
	private String mTimestamp;
	private String mDescription;
	private String mUtilityType;
	private String mUtilityStatusCode;
	private String mSupplierStatusCode;
	private String mSerial;

	@Override
	public String toString() {
		String supplier = "Supplier Name: " + mSupplierName + "\nSupplier Code: "
			+ mSupplierCode + "\nSupplier Description: " + mDescription
			+ "\nUtility Type: " + mUtilityType + "\nUtility Status Code: "
			+ mUtilityStatusCode + "\nTimestamp: " + mTimestamp;

		return supplier;
	}
	
	public Supplier() {
		mSupplierCode = null;
	}

	public Supplier(String name, String code) {
		mSupplierName = name;
		mSupplierCode = code;
	}

	public Supplier(String serial) {
		mSerial = serial;
	}

	public void setSupplierName(String name) {
		mSupplierName = name;
	}

	public String getSupplierName() {
		return mSupplierName;
	}

	public void setSupplierCode(String code) {
		mSupplierCode = code.toUpperCase();
	}

	public String getSupplierCode() {
		return mSupplierCode;
	}

	public void setUser(String user) {
		mUser = user;
	}

	public String getUser() {
		return mUser;
	}

	public void setTimestamp(String timestamp) {
		mTimestamp = timestamp;
	}

	public String getTimestamp() {
		return mTimestamp;
	}

	public void setSupplierStatusCode(String supplierStatusCode) {
		mSupplierStatusCode = supplierStatusCode;
	}

	public String getSupplierStatusCode() {
		return mSupplierStatusCode;
	}

	public void setDescription(String description) {
		mDescription = description;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setUtilityType(String utilityType) {
		mUtilityType = utilityType;
	}

	public String getUtilityType() {
		return mUtilityType;
	}

	public void setUtilityStatusCode(String utilityStatusCode) {
		mUtilityStatusCode = utilityStatusCode;
	}

	public String getUtilityStatusCode() {
		return mUtilityStatusCode;
	}

	public String getSerial() {
		return mSerial;
	}

	public void setSerial(String serial) {
		mSerial = serial;
	}
}
