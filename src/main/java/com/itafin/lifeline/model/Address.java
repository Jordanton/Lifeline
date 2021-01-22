package com.itafin.lifeline.model;

public class Address {
	private String mStreetNum;
	private String mStreetName;
	private String mStreetType;
	private String mStreetDir;
	private String mFracNum;
	private String mCity;
	private String mZipCode;
	private String mMobileParkName;
	private String mResidenceType;
	private String mUnitNum;
	private String mState;
	private String mAddressType;
	
	public Address() {
	}
	
	// Constructor for service address 
	public Address(String streetNum, String fracNum, String streetDir, String streetName,
		String streetType, String city, String zipCode, String mobileParkName, String residenceType, String unitNum) {
		mStreetNum = streetNum;
		mFracNum = fracNum;
		mStreetDir = streetDir;
		mStreetName = streetName;
		mStreetType = streetType;
		mCity = city;
		mZipCode = zipCode;
		mMobileParkName = mobileParkName;
		mResidenceType = residenceType;
		mUnitNum = unitNum;
	}
	
	// Constructor for mailing addresses (has a state, no move in date)
	public Address(String streetNum, String fracNum, String streetDir, String streetName,
		String streetType, String city, String zipCode, String mobileParkName, String residenceType, String unitNum, String state, String addressIndicator) {
		mStreetNum = streetNum;
		mFracNum = fracNum;
		mStreetDir = streetDir;
		mStreetName = streetName;
		mStreetType = streetType;
		mCity = city;
		mZipCode = zipCode;
		mMobileParkName = mobileParkName;
		mResidenceType = residenceType;
		mUnitNum = unitNum;
		mState = state;	
		mAddressType = addressIndicator;
	}
	
	public String getAddressType() {
		return mAddressType;
	}
	
	public String getState() {
		return mState;
	}
	
	public String getUnitNum() {
		return mUnitNum;
	}
	
	public String getStreetNum() {
		return mStreetNum;
	} 
	
	public String getFracNum() {
		return mFracNum;
	}
	
	public String getStreetDir() {
		return mStreetDir;
	}
	
	public String getStreetName() {
		return mStreetName;
	}
	
	public String getStreetType() {
		return mStreetType;
	}
	
	public String getCity() {
		return mCity;
	}
	
	public String getZipCode() {
		return mZipCode;
	}
	
	public String getMobileParkName() {
		return mMobileParkName;
	}
	
	public String getResidenceType() {
		return mResidenceType;
	}
	
	public void setStreetDir(String direction) {
		mStreetDir = direction;
	}
	public void setAddressType(String addressType) {
		mAddressType = addressType;
	}
	
	public void setState(String state) {
		mState = state;
	}
	
	public void setUnitNum(String unitNum) {
		mUnitNum = unitNum;
	}
	
	public void setStreetNum(String streetNum) {
		mStreetNum = streetNum;
	}
	
	public void setFracNum(String fracNum) {
		mFracNum = fracNum;
	}
	
	public void setStreetName(String streetName) {
		mStreetName = streetName;
	}
	
	public void setStreetType(String streetType) {
		mStreetType = streetType;
	}
	
	public void setCity(String city) {
		mCity = city;
	}
	
	public void setZipCode(String zipCode) {
		mZipCode = zipCode;
	}
	
	public void setMobileParkName(String mobileParkName) {
		mMobileParkName = mobileParkName;
	}
	
	public void setResidenceType(String residenceType) {
		mResidenceType = residenceType;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		
		if(obj == null || obj.getClass() != this.getClass())
			return false;
		
		Address addr = (Address) obj;
		
		boolean ret = 
			(this.mStreetNum == addr.getStreetNum() || ( this.mStreetNum != null && this.mStreetNum.equals(addr.getStreetNum()) )) &&
			(this.mFracNum == addr.getFracNum() || ( this.mFracNum != null && this.mFracNum.equals(addr.getFracNum()) )) &&
			(this.mStreetDir == addr.getStreetDir() || ( this.mStreetDir != null && this.mStreetDir.equals(addr.getStreetDir()) )) &&
			(this.mStreetName == addr.getStreetName() || ( this.mStreetName != null && this.mStreetName.equals(addr.getStreetName()) )) &&
			(this.mStreetType == addr.getStreetType() || ( this.mStreetType != null && this.mStreetType.equals(addr.getStreetType()) )) &&
			(this.mUnitNum == addr.getUnitNum() || ( this.mUnitNum != null && this.mUnitNum.equals(addr.getUnitNum()) )) &&
			(this.mCity == addr.getCity() || ( this.mCity != null && this.mCity.equals(addr.getCity()) )) &&
			(this.mZipCode == addr.getZipCode() || ( this.mZipCode != null && this.mZipCode.equals(addr.getZipCode()) )) &&
			(this.mMobileParkName == addr.getMobileParkName() || ( this.mMobileParkName != null && this.mMobileParkName.equals(addr.getMobileParkName()) ));
			
		return ret;
	}
	
	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + ((this.mStreetNum == null) ? 0 : mStreetNum.hashCode());
		result = prime * result + ((this.mFracNum == null) ? 0 : mFracNum.hashCode());
		result = prime * result + ((this.mStreetDir == null) ? 0 : mStreetDir.hashCode());
		result = prime * result + ((this.mStreetName == null) ? 0 : mStreetName.hashCode());
		result = prime * result + ((this.mStreetType == null) ? 0 : mStreetType.hashCode());
		result = prime * result + ((this.mUnitNum == null) ? 0 : mUnitNum.hashCode());
		result = prime * result + ((this.mCity == null) ? 0 : mCity.hashCode());
		result = prime * result + ((this.mZipCode == null) ? 0 : mZipCode.hashCode());
		result = prime * result + ((this.mMobileParkName == null) ? 0 : mMobileParkName.hashCode());
		
		return result;
	}
}
