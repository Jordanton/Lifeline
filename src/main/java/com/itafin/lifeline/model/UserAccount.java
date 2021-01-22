package com.itafin.lifeline.model;

import java.io.Serializable;

public class UserAccount implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String mUsername;
	private String mPassword;
	private String mRole;
	private String mStatus;
	private String mEmployeeId;
	
	public UserAccount() {
		mUsername = null;
		mPassword = null;
		mRole = null;
		mStatus = null;
		mEmployeeId = null;
	}

	public UserAccount(String username, String password, String role, String status) {
		mUsername = username;
		mPassword = password;
		mRole = role;
		mStatus = status;
		mEmployeeId = null;
	}
	
	public UserAccount(String username, String password) {
		mUsername = username;
		mPassword = password;
		mRole = null;
		mStatus = null;
		mEmployeeId = null;
	}

	public String getUsername() {
		return mUsername;
	}

	public void setUsername(String username) {
		mUsername = username;
	}

	public String getPassword() {
		return mPassword;
	}

	public void setPassword(String password) {
		mPassword = password;
	}
	
	public String getRole() {
		return mRole;
	}
	
	public void setRole(String role) {
		mRole = role;
	}
	
	public String getStatus() {
		return mStatus;
	}
	
	public void setStatus(String status) {
		mStatus = status;
	}
	
	public String getEmployeeId() {
		return mEmployeeId;
	}
	
	public void setEmployeeId(String employeeId) {
		mEmployeeId = employeeId;
	}
}
