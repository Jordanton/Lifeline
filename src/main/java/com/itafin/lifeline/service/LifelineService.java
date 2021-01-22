package com.itafin.lifeline.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.itafin.lifeline.controller.BaseController;
import com.itafin.lifeline.dao.LifelineDaoImpl;
import com.itafin.lifeline.dao.LifelineDao;
import com.itafin.lifeline.model.Applicant;
import com.itafin.lifeline.model.Supplier;
import com.itafin.lifeline.model.UserAccount;
import com.itafin.lifeline.model.Utility;
import com.itafin.lifeline.utils.Encrypter;
import com.itafin.lifeline.utils.LifelineProperties;

@Service("lifelineService")
public class LifelineService extends BaseController{
	
	private static final Logger logger = Logger.getLogger(LifelineService.class);
	
	@Autowired LifelineProperties lifelineProps;
	@Autowired LifelineDaoImpl lifelineDao;
	
	public String insertApplicant(Applicant applicant) {
		
		String serial = null;
		// Outdated fields carried over from rewrite 
		// These will not change but still entered 
		// in the DB
		String sequenceNum = "100";
		String moveInDate = "01-JAN-17";
		String grossIncome = "0";
		String statusCode = "A";
		String dateOfBirth = formatDate(applicant.getDateOfBirth());
		applicant.setSequenceNum(sequenceNum);
		applicant.setMoveInDate(moveInDate);
		applicant.setGrossIncome(grossIncome);
		applicant.setStatusCode(statusCode);
		applicant.setDateOfBirth(dateOfBirth);
		applicant.setSystemUser("LIFELINE");
		
		Date todayDate = new Date();
		String todayDate_dd_MMM_yy = new SimpleDateFormat("dd-MMM-yy").format(todayDate);
		applicant.setDateReceived(todayDate_dd_MMM_yy);
		String timestamp = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SSS a").format(todayDate);
		applicant.setTimestamp(timestamp);
		
		// remove empty utilities. empty utilities must be removed before setting other fields, othw they would no longer be empty
		int utilIndex = 0;
		while(utilIndex < applicant.getUtility().size()) {
			if(utilityIsNotEmpty(applicant.getUtility().get(utilIndex))) {
				utilIndex++;	// util not empty, go to next utility
			} else {
				applicant.getUtility().remove(utilIndex);
				// removing something from the list shifts the rest of the list to the left, decreasing their index
				// so keep utilIndex the same for next loop
			}
		}
		
		for(int i = 0; i < applicant.getUtility().size(); i++) {
			applicant.getUtility().get(i).setDateReceived(todayDate_dd_MMM_yy);
		}
		
		try {
			serial = lifelineDao.insertApplicant(applicant);
		} catch (Exception e) {
			logger.error("Error: Could not add applicant [ " + applicant.getFirstName() + " " + applicant.getLastName() +  " ].", e);
			e.printStackTrace();
		}
		return serial;
	}

	public Applicant getApplicantFromAccountNum(String accountNum) {
		Applicant applicant = null;
		
		try {
			applicant = lifelineDao.getApplicantFromAccountNum(accountNum);
		} catch (SQLException e) {
			logger.error("SQL Error: Could not find applicant by accountNum [ " + accountNum + " ].", e);
			e.printStackTrace();
		}
		
		return applicant;
	}

	public List<Utility> getUtilitiesFromAccountNum(String accountNum) {
		List<Utility> utilities = new ArrayList<Utility>();
		
		try {
			utilities = lifelineDao.getUtilitiesFromAccountNum(accountNum);
		} catch (SQLException e) {
			logger.error("SQL Error: Could not find utilities for accountNum [ " + accountNum + " ].", e);
			e.printStackTrace();
		}
		
		return utilities;
	}

	public boolean doesSupplierExist(String supplierCode) {
		boolean exists = false;
		try {
			exists = lifelineDao.doesSupplierExist(supplierCode);
		} catch (SQLException e) {
			logger.error("SQL Error: doesSupplierExist [ " + supplierCode + " ].", e);
			e.printStackTrace();
		}
		return exists;
	}

	public int updateApplicant(Applicant applicant) {
		int rowsUpdated = 0;
		
		try {
			rowsUpdated = lifelineDao.updateApplicant(applicant);
		} catch (SQLException e) {
			logger.error("SQL Error: Could not update applicant [ " + applicant.getAccountNum() + " ].", e);
			e.printStackTrace();
		}
		
		return rowsUpdated;
	}

	public int deleteApplicant(String accountNum) {
		
		int rowsDeleted = 0;
		
		try {
			rowsDeleted = lifelineDao.deleteApplicant(accountNum);
		} catch (SQLException e) {
			logger.error("SQL Error: Could not delete applicant [ " + accountNum + " ].", e);
			e.printStackTrace();
		}
		
		return rowsDeleted;
	}
	
	/**
	 * Saves document to /{filepath}/LL_[acct_nbr]_[util_id]
	 * Default filepath = /app/latax/shared/webapp_documents/[MM-dd-YYYY]/
	 * 
	 * file_desc can be utility id or "identification" or "income" or anything
	 */
	public Path saveDocumentToDisk(MultipartFile document, String scer_acct_nbr, String file_desc) {
		String filepath = lifelineProps.getDocumentUploadFilePath();
		String appPrefix = "LL_";
		Path pathToReturn = null;
		try {
		  
			Date today = new Date();
		  SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-YYYY");
		  String todayStr = sdf.format(today);
		  String datedFilepath = filepath + todayStr + File.separator;
		  
		  File directory = new File(datedFilepath);
		  if(!directory.exists()) {
		    directory.mkdirs();
	    }
		  
		  String fileExt = FilenameUtils.getExtension(document.getOriginalFilename());
		  String newFilename = null;
		  newFilename = appPrefix + scer_acct_nbr + "_";
		  newFilename += file_desc + "." + fileExt;
//		  if(null != scer_dtl_id) {	// for utility documents
//		  	newFilename = appPrefix + scer_acct_nbr + "_";
//		  	newFilename += suplr_cd + "_" + scer_dtl_id + "." + fileExt;
//		  } else {	// for senior/disability identification
//		  	newFilename = appPrefix + scer_acct_nbr + "_";
//		  	newFilename += "identification" + "." + fileExt;
//		  }
		  
		  byte[] bytes = document.getBytes();
		  pathToReturn = Paths.get(datedFilepath + newFilename);
		  //String fullPath = pathToReturn.toString();
		  pathToReturn = Files.write(pathToReturn, bytes);
		  // need access to the File object before writing in order to set writable
//		  File newFile = new File(fullPath);
//		  newFile.setWritable(true);
//		  newFile.createNewFile();
		  

		} catch (Exception e) {
			logger.error("saveDocumentToDisk - there was a problem saving the file \"" + document.getOriginalFilename() + "\" to disk.", e);
			pathToReturn = null;
		}
		
		return pathToReturn;
	}
	
	public String formatDate(String date) {
		// User enters birthday as MM/DD/YYYY, gets passed to servlet as YYYY-MM-DD
		// This block of code converts it into dd-MMM-yyyy
		// Because that is how it is originally stored in DB

		String year = date.substring(0, 4);
		String day = date.substring(8, 10);
		int monthNum = Integer.parseInt(date.substring(5, 7));
		String month = Month.of(monthNum).name().substring(0, 3); // i.e. "January" to "JAN"

		return day + "-" + month + "-" + year; // formats to dd-MMM-yyyy
	}
	
	public boolean utilityIsNotEmpty(Utility u) {
		boolean ret = false;
		if((null != u.getFirstName() && !u.getFirstName().trim().isEmpty() ) 
			|| ( null != u.getLastName() && !u.getLastName().trim().isEmpty() ) 
			|| ( null != u.getMiddleInitial() && !u.getMiddleInitial().trim().isEmpty() )
			|| ( null != u.getAccountNum() && !u.getAccountNum().trim().isEmpty() )
			|| ( null != u.getSupplier() && null != u.getSupplierCode() && !u.getSupplierCode().trim().isEmpty() )
			|| ( null != u.getPhoneNum() && !u.getPhoneNum().trim().isEmpty() )
			|| (u.getHouseholdCount() > 0) 
			|| ( null != u.getDocument() ) ) {
			ret = true;
		}
		return ret;
	}

}
