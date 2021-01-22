package com.itafin.lifeline.dao;

import java.sql.SQLException;

import com.itafin.lifeline.model.Applicant;

public interface LifelineDao {
	
	public String insertApplicant(Applicant applicant) throws SQLException;
	public int updateApplicant(Applicant applicant) throws SQLException;
	
}
