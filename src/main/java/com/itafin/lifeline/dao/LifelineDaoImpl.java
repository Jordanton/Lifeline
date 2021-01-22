package com.itafin.lifeline.dao;

import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.itafin.lifeline.model.Address;
import com.itafin.lifeline.model.Applicant;
import com.itafin.lifeline.model.Supplier;
import com.itafin.lifeline.model.UserAccount;
import com.itafin.lifeline.model.Utility;
import com.itafin.lifeline.service.LifelineService;
import com.itafin.lifeline.utils.Encrypter;
import com.itafin.lifeline.utils.LifelineUtils;

import oracle.jdbc.OracleTypes;

@Repository("lifelineDao")
public class LifelineDaoImpl implements LifelineDao 
{
	private static final Logger logger = Logger.getLogger(LifelineDaoImpl.class);
	
	@Autowired DataSource datasource;
	@Autowired JdbcTemplate appDataSourceTemplate;
	@Autowired LifelineService lifelineService;
	
	//unused
	public int deleteApplicant(String applicantAccountNum) throws SQLException {
		
		int rowsDeleted = 0;
		
		Connection conn = datasource.getConnection();
		PreparedStatement deleteApplicant = conn.prepareStatement("DELETE FROM web_exemp_aplcnt WHERE scer_acct_nbr = ?");

		deleteApplicant.setString(1, applicantAccountNum);
		
		rowsDeleted = deleteApplicant.executeUpdate();
		deleteApplicant.close();
		
		return rowsDeleted;
	}
	
	public String insertApplicant(Applicant applicant) {
		int errors = 0;
		String serial = null;
		Connection conn = null;
		
		try {
			conn = appDataSourceTemplate.getDataSource().getConnection();
			// autocommit on by default, but this inserts on multiple tables so we want to be able to rollback if an error occurs
			conn.setAutoCommit(false);	
//  		String sql = 
//  			"INSERT INTO WEB_EXEMP_APLCNT(SCER_ACCT_NBR, SCER_ACCT_SEQ_NBR, APLCNT_SSN, APLCNT_LST_NM, "
//  				+ "APLCNT_FRST_NM, APLCNT_MI_NM, DWP_ACCT_NBR, SYS_USER_ID, TIMESTMP, APLCNT_CLASS_CD, MSTR_STTS_CD, MSTR_STTS_DT, "
//  				+ "BRTH_DT, GRS_INC_AMT, DAYTIME_TEL_NBR, SERV_STR_NBR, SERV_STR_FRAC_NBR, SERV_STR_DIR_CD, SERV_STR_NM, SERV_STR_TYP_CD, "
//  				+ "SERV_UNIT_NBR, SERV_CITY_NM, SERV_ZIP_CD, SERV_MBL_PK_NM, APLCNT_MOVE_IN_DT, RES_TYP_CD, MAIL_ADDR_IND, MAIL_STR_NBR, "
//  				+ "MAIL_STR_FRAC_NBR, MAIL_STR_DIR_CD, MAIL_STR_NM, MAIL_STR_TYP_CD, MAIL_UNIT_NBR, MAIL_CITY_NM, MAIL_ST_CD, MAIL_ZIP_CD, "
//  				+ "MAIL_MBL_PK_NM,"
//  				+ "EMAIL_ADDRESS"
//  				+ ") values ("
//  				+ ":SCER_ACCT_NBR, "
//  				+ ":SCER_ACCT_SEQ_NBR, "
//  				+ ":APLCNT_SSN, "
//  				+ ":APLCNT_LST_NM, "
//  				+ ":APLCNT_FRST_NM, "
//  				+ ":APLCNT_MI_NM, "
//  				+ ":DWP_ACCT_NBR, "
//  				+ ":SYS_USER_ID, "
//  				+ ":TIMESTMP, "
//  				+ ":APLCNT_CLASS_CD ,"
//  				+ ":MSTR_STTS_CD, "
//  				+ ":MSTR_STTS_DT, "
//  				+ ":BRTH_DT, "
//  				+ ":GRS_INC_AMT, "
//  				+ ":DAYTIME_TEL_NBR, "
//  				+ ":SERV_STR_NBR, "
//  				+ ":SERV_STR_FRAC_NBR, "
//  				+ ":SERV_STR_DIR_CD, "
//  				+ ":SERV_STR_NM, "
//  				+ ":SERV_STR_TYP_CD, "
//  				+ ":SERV_UNIT_NBR, "
//  				+ ":SERV_CITY_NM,  "
//  				+ ":SERV_ZIP_CD, "
//  				+ ":SERV_MBL_PK_NM, "
//  				+ ":APLCNT_MOVE_IN_DT, "
//  				+ ":RES_TYP_CD, "
//  				+ ":MAIL_ADDR_IND, "
//  				+ ":MAIL_STR_NBR, " 
//  				+ ":MAIL_STR_FRAC_NBR, "
//  				+ ":MAIL_STR_DIR_CD, "
//  				+ ":MAIL_STR_NM, "
//  				+ ":MAIL_STR_TYP_CD, "
//  				+ ":MAIL_UNIT_NBR, "
//  				+ ":MAIL_CITY_NM, "
//  				+ ":MAIL_ST_CD, "
//  				+ ":MAIL_ZIP_CD," 
//  				+ ":MAIL_MBL_PK_NM, "
//  				+ ":EMAIL_ADDRESS "
//  				+ ")";
  		
  		MapSqlParameterSource mapSqlParam = new MapSqlParameterSource();
  		mapSqlParam = getSqlParameterByModel(applicant);
  		
  		// 38 parameters
  		CallableStatement query = conn.prepareCall(
  			"BEGIN INSERT INTO WEB_EXEMP_APLCNT(SCER_ACCT_NBR, SCER_ACCT_SEQ_NBR, APLCNT_SSN, APLCNT_LST_NM, "
  				+ "APLCNT_FRST_NM, APLCNT_MI_NM, DWP_ACCT_NBR, SYS_USER_ID, TIMESTMP, APLCNT_CLASS_CD, MSTR_STTS_CD, MSTR_STTS_DT, "
  				+ "BRTH_DT, GRS_INC_AMT, DAYTIME_TEL_NBR, SERV_STR_NBR, SERV_STR_FRAC_NBR, SERV_STR_DIR_CD, SERV_STR_NM, SERV_STR_TYP_CD, "
  				+ "SERV_UNIT_NBR, SERV_CITY_NM, SERV_ZIP_CD, SERV_MBL_PK_NM, APLCNT_MOVE_IN_DT, RES_TYP_CD, MAIL_ADDR_IND, MAIL_STR_NBR, "
  				+ "MAIL_STR_FRAC_NBR, MAIL_STR_DIR_CD, MAIL_STR_NM, MAIL_STR_TYP_CD, MAIL_UNIT_NBR, MAIL_CITY_NM, MAIL_ST_CD, MAIL_ZIP_CD, "
  				+ "MAIL_MBL_PK_NM, EMAIL_ADDRESS) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) RETURNING SCER_ACCT_NBR INTO ?; END;");
  		
  		
  		logger.debug("INSERT into web_exemp_aplcnt("
  			+ "SCER_ACCT_NBR, SCER_ACCT_SEQ_NBR, APLCNT_SSN, APLCNT_LST_NM, "
  			+ "APLCNT_FRST_NM, APLCNT_MI_NM, DWP_ACCT_NBR, SYS_USER_ID, TIMESTMP, APLCNT_CLASS_CD, MSTR_STTS_CD, MSTR_STTS_DT, "
  			+ "BRTH_DT, GRS_INC_AMT, DAYTIME_TEL_NBR, SERV_STR_NBR, SERV_STR_FRAC_NBR, SERV_STR_DIR_CD, SERV_STR_NM, SERV_STR_TYP_CD, "
  			+ "SERV_UNIT_NBR, SERV_CITY_NM, SERV_ZIP_CD, SERV_MBL_PK_NM, APLCNT_MOVE_IN_DT, RES_TYP_CD, MAIL_ADDR_IND, MAIL_STR_NBR, "
  			+ "MAIL_STR_FRAC_NBR, MAIL_STR_DIR_CD, MAIL_STR_NM, MAIL_STR_TYP_CD, MAIL_UNIT_NBR, MAIL_CITY_NM, MAIL_ST_CD, MAIL_ZIP_CD, "
  			+ "MAIL_MBL_PK_NM, "
  			+ "EMAIL_ADDRESS) values ("
  			+ mapSqlParam.getValue("SCER_ACCT_NBR") 
  			+ ", " + mapSqlParam.getValue("SCER_ACCT_SEQ_NBR")
  			+ ", " + null
  			+ ", " + mapSqlParam.getValue("APLCNT_LST_NM")
  			+ ", " + mapSqlParam.getValue("APLCNT_FRST_NM")
  			+ ", " + mapSqlParam.getValue("APLCNT_MI_NM")
  			+ ", " + mapSqlParam.getValue("DWP_ACCT_NBR")
  			+ ", " + mapSqlParam.getValue("SYS_USER_ID")
  			+ ", " + mapSqlParam.getValue("TIMESTMP")
  			+ ", " + mapSqlParam.getValue("APLCNT_CLASS_CD")
  			+ ", " + mapSqlParam.getValue("MSTR_STTS_CD")
  			+ ", " + mapSqlParam.getValue("MSTR_STTS_DT")
  			+ ", " + mapSqlParam.getValue("BRTH_DT")
  			+ ", " + mapSqlParam.getValue("GRS_INC_AMT")
  			+ ", " + mapSqlParam.getValue("DAYTIME_TEL_NBR")
  			+ ", " + mapSqlParam.getValue("SERV_STR_NBR")
  			+ ", " + mapSqlParam.getValue("SERV_STR_FRAC_NBR")
  			+ ", " + mapSqlParam.getValue("SERV_STR_DIR_CD")
  			+ ", " + mapSqlParam.getValue("SERV_STR_NM")
  			+ ", " + mapSqlParam.getValue("SERV_STR_TYP_CD")
  			+ ", " + mapSqlParam.getValue("SERV_UNIT_NBR")
  			+ ", " + mapSqlParam.getValue("SERV_CITY_NM")
  			+ ", " + mapSqlParam.getValue("SERV_ZIP_CD")
  			+ ", " + mapSqlParam.getValue("SERV_MBL_PK_NM")
  			+ ", " + mapSqlParam.getValue("APLCNT_MOVE_IN_DT")
  			+ ", " + mapSqlParam.getValue("RES_TYP_CD")
  			+ ", " + mapSqlParam.getValue("MAIL_ADDR_IND")
  			+ ", " + mapSqlParam.getValue("MAIL_STR_NBR")
  			+ ", " + mapSqlParam.getValue("MAIL_STR_FRAC_NBR")
  			+ ", " + mapSqlParam.getValue("MAIL_STR_DIR_CD")
  			+ ", " + mapSqlParam.getValue("MAIL_STR_NM")
  			+ ", " + mapSqlParam.getValue("MAIL_STR_TYP_CD")
  			+ ", " + mapSqlParam.getValue("MAIL_UNIT_NBR")
  			+ ", " + mapSqlParam.getValue("MAIL_CITY_NM")
  			+ ", " + mapSqlParam.getValue("MAIL_ST_CD")
  			+ ", " + mapSqlParam.getValue("MAIL_ZIP_CD")
  			+ ", " + mapSqlParam.getValue("MAIL_MBL_PK_NM")
  			+ ", " + mapSqlParam.getValue("EMAIL_ADDRESS") + ")");
  		
//  		NamedParameterJdbcTemplate namedParamJdbcTempl = new NamedParameterJdbcTemplate(appDataSourceTemplate);
//  		KeyHolder keyHolder = new GeneratedKeyHolder();
//  		
//  		rowsInserted = namedParamJdbcTempl.update(sql, mapSqlParam, keyHolder, new String[]{"SCER_ACCT_NBR"});
//  		
//  		serial = keyHolder.getKey().toString();
//  		String testSerial = Integer.toString(keyHolder.getKey().intValue());
  		
  		query.setString(1, applicant.getAccountNum());
  		query.setString(2, applicant.getSequenceNum());
  		query.setString(3, applicant.getSocialSecurity());
  		query.setString(4, applicant.getLastName().toUpperCase());
  		query.setString(5, applicant.getFirstName().toUpperCase());
  		query.setString(6, applicant.getMiddleName().toUpperCase());
  		query.setString(7, applicant.getDWPAccountNum());
  		query.setString(8, applicant.getSystemUser());
  		query.setString(9, applicant.getTimestamp());
  		query.setString(10, applicant.getClassCode());
  		query.setString(11, applicant.getStatusCode());
  		query.setString(12, applicant.getStatusDate());
  		query.setString(13, applicant.getDateOfBirth());
  		query.setString(14, applicant.getGrossIncome());
  		query.setString(15, applicant.getDayPhone());

  		// Service Address
  		Address serviceAddress = applicant.getServiceAddress();
  		Address mailingAddress = applicant.getMailingAddress();
  		query.setString(16, serviceAddress.getStreetNum());
  		query.setString(17, serviceAddress.getFracNum());
  		query.setString(18, serviceAddress.getStreetDir());
  		query.setString(19, serviceAddress.getStreetName().toUpperCase());
  		query.setString(20, serviceAddress.getStreetType().toUpperCase());
  		query.setString(21, serviceAddress.getUnitNum());
  		query.setString(22, serviceAddress.getCity().toUpperCase());
  		query.setString(23, serviceAddress.getZipCode());
  		query.setString(24, serviceAddress.getMobileParkName().toUpperCase());
  		query.setString(25, null); // movein date
  		query.setString(26, serviceAddress.getResidenceType().toUpperCase());

  		// Mailing address
  		query.setString(27, mailingAddress.getAddressType());
  		query.setString(28, mailingAddress.getStreetNum());
  		query.setString(29, mailingAddress.getFracNum());
  		query.setString(30, mailingAddress.getStreetDir());
  		query.setString(31, mailingAddress.getStreetName().toUpperCase());
  		query.setString(32, mailingAddress.getStreetType().toUpperCase());
  		query.setString(33, mailingAddress.getUnitNum());
  		query.setString(34, mailingAddress.getCity().toUpperCase());
  		query.setString(35, mailingAddress.getState().toUpperCase());
  		query.setString(36, mailingAddress.getZipCode());
  		query.setString(37, mailingAddress.getMobileParkName().toUpperCase());
  		query.registerOutParameter(39, OracleTypes.VARCHAR);
  		
  		query.setString(38, applicant.getEmailAddress());
  		
  		query.executeUpdate();
  		serial = query.getString(39);
  		query.close();
  		
//  		if(null != serial) {
//  			int doc_success = insertDocument(conn, serial, null, null, applicant.getStatusDocument());
//  			if(doc_success < 0) {
//  				throw new Exception("Could not insert status document for applicant [" + applicant.getLastName() + ", " + applicant.getFirstName() + "]");
//  			}
//  		}
  		if(null == serial) {
  			errors++;
  			throw new Exception("Could not insert record for applicant [" + applicant.getLastName() + ", " + applicant.getFirstName() + "]");
  		}
  		
  		// Adds applicant to APLC table 
  		// Mainly to store comments
  		PreparedStatement addApplicantToAPLC = conn.prepareStatement("INSERT INTO WEB_EXEMP_APLC ("
  			+ "SCER_ACCT_NBR, SCER_ACCT_SEQ_NBR, APLC_TYP_CD, SYS_USER_ID, TIMESTMP, ACTN_PRCS_CD, "
  			+ "APLC_STTS_CD, APLC_STTS_DT, APLC_RCV_DT, APLC_CMT_TXT, ORI_APLC_RCV_DT, UPDT_STTS_SRCE_CD, "
  			+ "APLC_MAIL_DT, APLC_CORE_STTS_CD, APLC_CORE_RCV_DT) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
  		addApplicantToAPLC.setString(1, serial);
  		addApplicantToAPLC.setString(2, applicant.getSequenceNum());
  		
  		// This field is migrated from the old mainframe application
  		// Refunds were stopped in 2012, so all entries are exemptions ("E")
  		addApplicantToAPLC.setString(3, "E"); // APLC_TYP_CD
  		
  		addApplicantToAPLC.setString(4, applicant.getSystemUser().toUpperCase());
  		addApplicantToAPLC.setString(5, applicant.getTimestamp());	
  		addApplicantToAPLC.setString(6, null); // ACTN_PRCS_CD
  		addApplicantToAPLC.setString(7, "RC"); // APLC_STTS_CD
  		addApplicantToAPLC.setString(8, applicant.getStatusDate());
  		addApplicantToAPLC.setString(9, applicant.getDateReceived());
  		addApplicantToAPLC.setString(10, applicant.getComments());		
  		addApplicantToAPLC.setString(11, new SimpleDateFormat("dd-MMM-yy").format(new Date()));
  		addApplicantToAPLC.setString(12, null);
  		addApplicantToAPLC.setString(13, applicant.getMailingDate());
  		addApplicantToAPLC.setString(14, "RC"); // APLC_CORE_STTS_CD
  		addApplicantToAPLC.setString(15, new SimpleDateFormat("dd-MMM-yy").format(new Date()));
  		
  		addApplicantToAPLC.executeUpdate();
  		addApplicantToAPLC.close();
  		
  		/* Serial number was generated after previous execute
  		 * Now use the serial number to add the utilities table (WEB_TAPLC_DTL_CURR) */
  		
  		/* Loop through applicant's utilities and add new row for each one */
  		for (int i = 0; i < applicant.getUtility().size(); i++) {
  			String scer_dtl_id = null;
  			CallableStatement queryUtil = conn.prepareCall(
  				"BEGIN INSERT INTO WEB_TAPLC_DTL_CURR (SCER_ACCT_NBR, UTIL_SUPLR_CD, UTIL_BILL_LST_NM, "
  				+ "UTIL_BILL_FRST_NM, UTIL_BILL_MI_NM, APLC_TYP_CD, TIMESTMP, UTIL_APPROVED_DT, "
  				+ "ACCT_INFO_TXT, RENT_TAX_CD, SCER_ACCT_SEQ_NBR, UTIL_RCV_DT, PERSN_OCCP_CNT, "
  				+ "UTIL_TEL_FUND_NBR, LIFELINE_SERV_IND, SYS_USER_ID) "
  				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
  				+ "RETURNING SCER_DTL_ID INTO ?; END;");
  			
  			queryUtil.setString(1, serial);
  			queryUtil.setString(2, applicant.getUtility().get(i).getSupplierCode().toUpperCase());
  			queryUtil.setString(3, applicant.getUtility().get(i).getLastName().toUpperCase());
  			queryUtil.setString(4, applicant.getUtility().get(i).getFirstName().toUpperCase());
  			queryUtil.setString(5, applicant.getUtility().get(i).getMiddleInitial().toUpperCase());
  			queryUtil.setString(6, "E");
  			queryUtil.setString(7, new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SSS a").format(new Date()));
  			queryUtil.setString(8, new SimpleDateFormat("dd-MMM-yy").format(new Date()));
  			queryUtil.setString(9, applicant.getUtility().get(i).getAccountNum());
  			queryUtil.setString(10, applicant.getUtility().get(i).getIncludedInRent());
  			queryUtil.setString(11, "100");
  			queryUtil.setString(12, applicant.getUtility().get(i).getDateReceived());
  			queryUtil.setInt(13, applicant.getUtility().get(i).getHouseholdCount());
  			queryUtil.setString(14, applicant.getUtility().get(i).getPhoneNum());
  			queryUtil.setString(15, applicant.getUtility().get(i).getServiceRequested());
  			queryUtil.setString(16, applicant.getSystemUser().toUpperCase());
  			queryUtil.registerOutParameter(17, OracleTypes.VARCHAR);					
  			
  			queryUtil.execute();
  			scer_dtl_id = queryUtil.getString(17);
  			queryUtil.close();
  			
  			String suplr_cd = applicant.getUtility().get(i).getSupplierCode().toUpperCase();
  			
  			if(null != scer_dtl_id) {
  				// insert documents now
//  				int doc_success = insertDocument(conn, serial, scer_dtl_id, suplr_cd, applicant.getUtility().get(i).getDocument());
//  				if(doc_success != 1) {
//  					throw new Exception("Could not insert document for utility [" + applicant.getUtility().get(i).getSupplierCode() + "]");
//  				}
  				applicant.getUtility().get(i).setUtilityID(scer_dtl_id);
  			} else {
  				errors++;
  				throw new Exception("Could not insert utility [" + applicant.getUtility().get(i).getSupplierCode() + "] into table.");
  			}
  			
  		}
  		
  		// save documents to disk now
  		if(errors == 0) {
    		if(null != serial) {	// serial should never be null at this point or exception would have been thrown earlier
    			String docDescription = "identification";
    			int doc_success = insertDocument(conn, serial, docDescription, applicant.getStatusDocument());
    			if(doc_success < 0) {
    				throw new Exception("Could not save status document for applicant [" + applicant.getLastName() + ", " + applicant.getFirstName() + "]");
    			}
    			
    			docDescription = "income";
    			doc_success = insertDocument(conn, serial, docDescription, applicant.getIncomeDocument());
    			if(doc_success < 0) {
    				throw new Exception("Could not save income document for applicant [" + applicant.getLastName() + ", " + applicant.getFirstName() + "]");
    			}
    		}
    		
    		for (int i = 0; i < applicant.getUtility().size(); i++) {
    			String scer_dtl_id = applicant.getUtility().get(i).getUtilityID();
    			String suplr_cd = applicant.getUtility().get(i).getSupplierCode();
    			String docDescription = suplr_cd + "-" + scer_dtl_id;
    			int doc_success = insertDocument(conn, serial, docDescription, applicant.getUtility().get(i).getDocument());
  				if(doc_success != 1) {
  					throw new Exception("Could not save document for utility [" + applicant.getUtility().get(i).getSupplierCode() + "]");
  				}
    			
    		}
  		}
  		
  		conn.commit();
  		conn.close();
		} catch(SQLTimeoutException e) {
			logger.error("insertApplicant - unable to get database connection.", e);
			e.printStackTrace();
			serial = null;
		} catch(SQLException e) {
			logger.error("insertApplicant - SQLException occurred. Attempting rollback...", e);
			e.printStackTrace();
			if(null != conn) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					logger.error("insertApplicant - Error occurred while attempting rollback.", e);
					e1.printStackTrace();
				}
			}
			serial = null;
		} catch(Exception e) {
			logger.error("insertApplicant - Error occured. Attempting rollback...", e);
			e.printStackTrace();
			if(null != conn) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					logger.error("insertApplicant - Error occurred while attempting rollback.", e);
					e1.printStackTrace();
				}
			}
			serial = null;
		}
		
		return serial;
	}
	
	private int insertDocument(Connection conn, String scer_acct_nbr, String file_desc, MultipartFile doc) throws SQLException {
		
		Path filepath = lifelineService.saveDocumentToDisk(doc, scer_acct_nbr, file_desc);
		int retCode = -1;
		if(null != filepath) {
			retCode = 1;
		}
//		if(null != filepath) {
//				String sql = "INSERT INTO WEB_TAPLC_DOC(SCER_ACCT_NBR, SCER_DTL_ID, DOC_ID, DOC_FILEPATH, TIMESTMP) "
//					+ " values ("
//					+ " :SCER_ACCT_NBR, "
//					+ " :SCER_DTL_ID, "
//					+ " :DOC_ID, "
//					+ " :DOC_FILEPATH, "
//					+ " :TIMESTMP )";
//				
//				PreparedStatement insertDoc = conn.prepareStatement("INSERT INTO WEB_TAPLC_DOC "
//					+ " (SCER_ACCT_NBR, SCER_DTL_ID, DOC_ID, DOC_FILEPATH, TIMESTMP) "
//					+ " values (?, ?, ?, ?, ?)");
//				
//				insertDoc.setString(1, scer_acct_nbr);
//				insertDoc.setString(2, scer_dtl_id);
//				insertDoc.setString(3, null);
//				insertDoc.setString(4, filepath.toString());
//				insertDoc.setString(5, new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SSS a").format(new Date()));
//				
//				retCode = insertDoc.executeUpdate();
//		}
//		
		return retCode;
	}
	
	private MapSqlParameterSource getSqlParameterByModel(Applicant applicant) {
		
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("SCER_ACCT_NBR", applicant.getAccountNum());
		map.addValue("SCER_ACCT_SEQ_NBR", applicant.getSequenceNum());
		map.addValue("APLCNT_SSN", applicant.getSocialSecurity());
		map.addValue("APLCNT_LST_NM", applicant.getLastName().toUpperCase());
		map.addValue("APLCNT_FRST_NM", applicant.getFirstName().toUpperCase());
		map.addValue("APLCNT_MI_NM", applicant.getMiddleName().toUpperCase());
		map.addValue("DWP_ACCT_NBR", applicant.getDWPAccountNum());
		map.addValue("SYS_USER_ID", applicant.getSystemUser().toUpperCase());
		map.addValue("TIMESTMP", applicant.getTimestamp());
		map.addValue("APLCNT_CLASS_CD", applicant.getClassCode());
		map.addValue("MSTR_STTS_CD", applicant.getStatusCode());
		map.addValue("MSTR_STTS_DT", applicant.getStatusDate());
		map.addValue("BRTH_DT", applicant.getDateOfBirth());
		map.addValue("GRS_INC_AMT", applicant.getGrossIncome());
		map.addValue("DAYTIME_TEL_NBR", applicant.getDayPhone());
		
		Address serviceAddress = applicant.getServiceAddress();
		map.addValue("SERV_STR_NBR", serviceAddress.getStreetNum());
		map.addValue("SERV_STR_FRAC_NBR", serviceAddress.getFracNum());
		map.addValue("SERV_STR_DIR_CD", serviceAddress.getStreetDir());
		map.addValue("SERV_STR_NM", serviceAddress.getStreetName().toUpperCase());
		map.addValue("SERV_STR_TYP_CD", serviceAddress.getStreetType().toUpperCase());
		map.addValue("SERV_UNIT_NBR", serviceAddress.getUnitNum());
		map.addValue("SERV_CITY_NM", serviceAddress.getCity().toUpperCase());
		map.addValue("SERV_ZIP_CD", serviceAddress.getZipCode());
		map.addValue("SERV_MBL_PK_NM", serviceAddress.getMobileParkName().toUpperCase());
		map.addValue("APLCNT_MOVE_IN_DT", null);
		map.addValue("RES_TYP_CD", serviceAddress.getResidenceType().toUpperCase());
		
		Address mailingAddress = applicant.getServiceAddress();
		map.addValue("MAIL_ADDR_IND", mailingAddress.getAddressType());
		map.addValue("MAIL_STR_NBR", mailingAddress.getStreetNum());
		map.addValue("MAIL_STR_FRAC_NBR", mailingAddress.getFracNum());
		map.addValue("MAIL_STR_DIR_CD", mailingAddress.getStreetDir());
		map.addValue("MAIL_STR_NM", mailingAddress.getStreetName().toUpperCase());
		map.addValue("MAIL_STR_TYP_CD", mailingAddress.getStreetType().toUpperCase());
		map.addValue("MAIL_UNIT_NBR", mailingAddress.getUnitNum());
		map.addValue("MAIL_CITY_NM", mailingAddress.getCity().toUpperCase());
		map.addValue("MAIL_ST_CD", mailingAddress.getState().toUpperCase());
		map.addValue("MAIL_ZIP_CD", mailingAddress.getZipCode());
		map.addValue("MAIL_MBL_PK_NM", mailingAddress.getMobileParkName().toUpperCase());
		
		map.addValue("EMAIL_ADDRESS", applicant.getEmailAddress());
		
		return map;
	}

	// unusued
	public boolean doesSupplierExist(String supplierCode) throws SQLException {
		Connection conn = datasource.getConnection();
		PreparedStatement query = conn.prepareStatement("SELECT * FROM TUTIL_SUPLR WHERE UTIL_SUPLR_CD = ?");
		
		query.setString(1, supplierCode.toUpperCase());
		
		ResultSet rs = query.executeQuery();
		
		if (rs.next()) {
			return true;
		}
		return false;
	}
	
	public List<Utility> getUtilitiesFromAccountNum(String accountNum) throws SQLException {
		List<Utility> utils = new ArrayList<Utility>();
		Connection conn = datasource.getConnection();
		PreparedStatement query = conn.prepareStatement("SELECT * FROM WEB_TAPLC_DTL_CURR WHERE scer_acct_nbr = ?");
		query.setString(1, accountNum);
		
		ResultSet rs = query.executeQuery();

		while(rs.next()) {
			Utility u = new Utility();
			Supplier s = new Supplier();
			
			s.setSupplierCode(rs.getString("UTIL_SUPLR_CD").toUpperCase());
			u.setSupplier(s);
			
			u.setUtilityID(rs.getString("SCER_DTL_ID"));
			u.setLastName(rs.getString("UTIL_BILL_LST_NM"));
			u.setFirstName(rs.getString("UTIL_BILL_FRST_NM"));
			u.setMiddleInitial(rs.getString("UTIL_BILL_MI_NM"));
			u.setHouseholdCount(rs.getInt("PERSN_OCCP_CNT"));
			u.setAccountNum(rs.getString("ACCT_INFO_TXT"));
			u.setPhoneNum(rs.getString("UTIL_TEL_FUND_NBR"));
			u.setUtilityID(rs.getString("SCER_DTL_ID")); // PK
			
			if (rs.getString("UTIL_RCV_DT") != null) {
				u.setDateReceived(rs.getString("UTIL_RCV_DT").substring(0,10));
			}
			if (rs.getString("UTIL_APPROVED_DT") != null) {
				u.setDateApproved(rs.getString("UTIL_APPROVED_DT").substring(0,10));
			}
			
			if (rs.getString("UTIL_FACS_SUBM_DT") != null) {
				u.setDateSubmitted(rs.getString("UTIL_FACS_SUBM_DT").substring(0,10));
			}

			u.setIncludedInRent(rs.getString("RENT_TAX_CD"));
			u.setServiceRequested(rs.getString("LIFELINE_SERV_IND"));
			
			utils.add(u);
		}
		
		conn.close();
		return utils;
	}
	
	public Applicant getApplicantFromAccountNum(String accountNum) throws SQLException {
		Connection conn = datasource.getConnection();
		PreparedStatement query = conn.prepareStatement("SELECT * FROM WEB_EXEMP_APLCNT WHERE scer_acct_nbr = ?");
		query.setString(1, accountNum);
		
		ResultSet rs = query.executeQuery();
		
		Applicant a = new Applicant();
		if(rs.next()) {
		 /* Substring is necessary to format date as YYYY-MM-DD
		  * Otherwise, we can't populate 'type="date"' fields in the view
		 	* Because the format must be YYYY-MM-DD to populate
		 	* But is retrieved from DB as YYYY-MM-DD HH:MM:SS....
		  * So only substring the dates if they exist (otherwise you will 
		  * get an error trying to call a function on a null value) */
			if (rs.getString("BRTH_DT") != null) {
				a.setDateOfBirth(rs.getString("BRTH_DT").substring(0,10));
			}
			if (rs.getString("MSTR_STTS_DT") != null) {
				a.setStatusDate(rs.getString("MSTR_STTS_DT").substring(0,10));
			}			
			
  		a.setAccountNum(rs.getString("SCER_ACCT_NBR"));
  		a.setSequenceNum(rs.getString("SCER_ACCT_SEQ_NBR"));
  		a.setLastName(rs.getString("APLCNT_LST_NM"));
  		a.setFirstName(rs.getString("APLCNT_FRST_NM"));
  		a.setMiddleName(rs.getString("APLCNT_MI_NM"));  		  		
  		a.setDayPhone(rs.getString("DAYTIME_TEL_NBR"));
  		a.setClassCode(rs.getString("APLCNT_CLASS_CD"));
  		a.setSystemUser(rs.getString("SYS_USER_ID"));
  				
  		Address serv = new Address();
  		serv.setStreetNum(rs.getString("SERV_STR_NBR"));
  		serv.setFracNum(rs.getString("SERV_STR_FRAC_NBR"));
  		serv.setStreetDir(rs.getString("SERV_STR_DIR_CD"));
  		serv.setStreetName(rs.getString("SERV_STR_NM"));
  		serv.setStreetType(rs.getString("SERV_STR_TYP_CD"));
  		serv.setUnitNum(rs.getString("SERV_UNIT_NBR"));
  		serv.setCity(rs.getString("SERV_CITY_NM"));
  		serv.setState("CA");
  		serv.setZipCode(rs.getString("SERV_ZIP_CD"));
  		serv.setMobileParkName(rs.getString("SERV_MBL_PK_NM"));
  		serv.setResidenceType(rs.getString("RES_TYP_CD"));
  		
  		Address mail = new Address();
  		mail.setStreetNum(rs.getString("MAIL_STR_NBR"));
  		mail.setFracNum(rs.getString("MAIL_STR_FRAC_NBR"));
  		mail.setStreetDir(rs.getString("MAIL_STR_DIR_CD"));
  		mail.setStreetName(rs.getString("MAIL_STR_NM"));
  		mail.setStreetType(rs.getString("MAIL_STR_TYP_CD"));
  		mail.setUnitNum(rs.getString("MAIL_UNIT_NBR"));
  		mail.setCity(rs.getString("MAIL_CITY_NM"));
  		mail.setState(rs.getString("MAIL_ST_CD"));
  		mail.setZipCode(rs.getString("MAIL_ZIP_CD"));
  		mail.setMobileParkName(rs.getString("MAIL_MBL_PK_NM"));
  		
  		a.setServiceAddress(serv);
  		a.setMailingAddress(mail);
		}
		rs.close();
		
		PreparedStatement queryAPLC = conn.prepareStatement("SELECT * FROM WEB_EXEMP_APLC WHERE scer_acct_nbr = ?");
		queryAPLC.setString(1, accountNum);
		
		ResultSet rsAPLC = queryAPLC.executeQuery();
		
		if (rsAPLC.next()) {
			if (rsAPLC.getString("APLC_RCV_DT") != null) {
				a.setDateReceived(rsAPLC.getString("APLC_RCV_DT").substring(0,10));
			}
			
			/* This checks if the applicant has a mail date entered
			 * If so, it will enter the date in the DB, and any 
			 * comments that were entered. */
			if (rsAPLC.getString("APLC_MAIL_DT") != null) {
				a.setMailingDate(rsAPLC.getString("APLC_MAIL_DT").substring(0,10));				
			}
			
			a.setComments(rsAPLC.getString("APLC_CMT_TXT"));
		}		
		queryAPLC.close();
		conn.close();
		return a;
	}

	// unused
	/* This method retrieves the serial number for the supplier
	 * Then passes the serial number back as a String 
	 * The serial number is the primary key that is auto-generated after insert */
	public String getSupplierSerial(Supplier supplier)
		throws SQLException {		
		String ret = "";
		String sql = "SELECT WEB_TUTIL_SUPLR.UTIL_SUPLR_ID FROM WEB_TUTIL_SUPLR WHERE UTIL_SUPLR_CD = :supplierCodeParam";
		String supplierCode = supplier.getSupplierCode();
		MapSqlParameterSource mapSqlParamSrc = new MapSqlParameterSource();
		mapSqlParamSrc.addValue("supplierCodeParam", supplierCode);
		
		NamedParameterJdbcTemplate namedParamJdbcTempl = new NamedParameterJdbcTemplate(datasource);
		
		List<String> serials = namedParamJdbcTempl.query(sql, mapSqlParamSrc,
			new RowMapper<String>() {
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					String serial = rs.getString("UTIL_SUPLR_ID");
					return serial;
				}
			});
		
		if(null != serials && serials.size() > 0)
			ret = serials.get(0);

		return ret;
	}
	
	// unusued
	public int updateApplicant(Applicant applicant)
		throws SQLException {
		int rowsUpdated = 0;
		Connection conn = datasource.getConnection();
		PreparedStatement query = conn.prepareStatement(
			"UPDATE WEB_EXEMP_APLCNT SET APLCNT_SSN = ?, APLCNT_LST_NM = ?, "
				+ "APLCNT_FRST_NM = ?, APLCNT_MI_NM = ?, DWP_ACCT_NBR = ?, "
				+ "TIMESTMP = ?, APLCNT_CLASS_CD = ?, MSTR_STTS_CD = ?, MSTR_STTS_DT = ?, "
				+ "BRTH_DT = ?, GRS_INC_AMT = ?, DAYTIME_TEL_NBR = ?, SERV_STR_NBR = ?, "
				+ "SERV_STR_FRAC_NBR = ?, SERV_STR_DIR_CD = ?, SERV_STR_NM = ?, SERV_STR_TYP_CD = ?, "
				+ "SERV_UNIT_NBR = ?, SERV_CITY_NM = ?, SERV_ZIP_CD = ?, SERV_MBL_PK_NM = ?, "
				+ "APLCNT_MOVE_IN_DT = ?, RES_TYP_CD = ?, MAIL_ADDR_IND = ?, MAIL_STR_NBR = ?, "
				+ "MAIL_STR_FRAC_NBR = ?, MAIL_STR_DIR_CD = ?, MAIL_STR_NM = ?, MAIL_STR_TYP_CD = ?, "
				+ "MAIL_UNIT_NBR = ?, MAIL_CITY_NM = ?, MAIL_ST_CD = ?, MAIL_ZIP_CD = ?, "
				+ "MAIL_MBL_PK_NM = ?, SYS_USER_ID = ? WHERE SCER_ACCT_NBR = ?");

		/* Applicant's information */
		query.setString(1, applicant.getSocialSecurity());
		query.setString(2, applicant.getLastName());
		query.setString(3, applicant.getFirstName());
		query.setString(4, applicant.getMiddleName());
		query.setString(5, applicant.getDWPAccountNum());
		query.setString(6, applicant.getTimestamp());
		query.setString(7, applicant.getClassCode());
		query.setString(8, applicant.getStatusCode());
		query.setString(9, applicant.getStatusDate());
		query.setString(10, applicant.getDateOfBirth());
		query.setString(11, applicant.getGrossIncome());
		query.setString(12, applicant.getDayPhone());

		/* Applicant's service address */
		Address serviceAddress = applicant.getServiceAddress();
		Address mailingAddress = applicant.getMailingAddress();
		query.setString(13, serviceAddress.getStreetNum());
		query.setString(14, serviceAddress.getFracNum());
		query.setString(15, serviceAddress.getStreetDir());
		query.setString(16, serviceAddress.getStreetName());
		query.setString(17, serviceAddress.getStreetType());
		query.setString(18, serviceAddress.getUnitNum());
		query.setString(19, serviceAddress.getCity());
		query.setString(20, serviceAddress.getZipCode());
		query.setString(21, serviceAddress.getMobileParkName());
		query.setString(22, null); // Move-in date, not used anymore
		query.setString(23, serviceAddress.getResidenceType());

		/* Applicant's mailing address */
		query.setString(24, mailingAddress.getAddressType());
		query.setString(25, mailingAddress.getStreetNum());
		query.setString(26, mailingAddress.getFracNum());
		query.setString(27, mailingAddress.getStreetDir());
		query.setString(28, mailingAddress.getStreetName());
		query.setString(29, mailingAddress.getStreetType());
		query.setString(30, mailingAddress.getUnitNum());
		query.setString(31, mailingAddress.getCity());
		query.setString(32, mailingAddress.getState());
		query.setString(33, mailingAddress.getZipCode());
		query.setString(34, mailingAddress.getMobileParkName());
		query.setString(35, applicant.getSystemUser());
		query.setString(36, applicant.getAccountNum());

		rowsUpdated = query.executeUpdate();		
		query.close();						

		/* This query is to add applicants to the "...APLC" table
		 * This table is similar to the "...APLCNT" table
		 * But the main reason we need it is to store comments, if any
		 */
		PreparedStatement addApplicantToAPLC = conn.prepareStatement(
			"UPDATE WEB_EXEMP_APLC SET APLC_TYP_CD = ?, TIMESTMP = ?, "
			+ "ACTN_PRCS_CD = ?, APLC_STTS_CD = ?, APLC_STTS_DT = ?, "
			+ "APLC_RCV_DT = ?, APLC_CMT_TXT = ?, ORI_APLC_RCV_DT = ?, "
			+ "UPDT_STTS_SRCE_CD = ?, APLC_MAIL_DT = ?, APLC_CORE_STTS_CD = ?, "
			+ "APLC_CORE_RCV_DT = ? WHERE SCER_ACCT_NBR = ?");

		/* Note: Any null or hardcoded values
		 * are to match up with the old system's requirements
		 * since we need to enter data for every column in the table */
		addApplicantToAPLC.setString(1, "E");
		addApplicantToAPLC.setString(2, applicant.getTimestamp());		
		addApplicantToAPLC.setString(3, null);
		addApplicantToAPLC.setString(4, "RC");
		addApplicantToAPLC.setString(5, applicant.getStatusDate());
		addApplicantToAPLC.setString(6, applicant.getDateReceived());
		addApplicantToAPLC.setString(7, applicant.getComments());		
		addApplicantToAPLC.setString(8, new SimpleDateFormat("dd-MMM-yy").format(new Date()));
		addApplicantToAPLC.setString(9, null);
		addApplicantToAPLC.setString(10, applicant.getMailingDate());
		addApplicantToAPLC.setString(11, "RC");
		addApplicantToAPLC.setString(12, new SimpleDateFormat("dd-MMM-yy").format(new Date()));
		addApplicantToAPLC.setString(13, applicant.getAccountNum());
		
		addApplicantToAPLC.executeUpdate();
		addApplicantToAPLC.close();
		
		/* Now use the serial number to add the utilities 
		 * to DTL_APLC_CUR. This is done by deleting the existing utilities
		 * and then inserting the updated utilities */				
		
		PreparedStatement deleteUtils = conn.prepareStatement("DELETE FROM WEB_TAPLC_DTL_CURR WHERE SCER_ACCT_NBR = ?");
		deleteUtils.setString(1, applicant.getAccountNum());
		deleteUtils.executeUpdate();
		deleteUtils.close();
				
		for (int i = 0; i < applicant.getUtility().size(); i++) {
			PreparedStatement updateUtils = conn.prepareStatement("INSERT INTO WEB_TAPLC_DTL_CURR "
				+ "(SCER_ACCT_NBR, UTIL_SUPLR_CD, UTIL_BILL_LST_NM, UTIL_BILL_FRST_NM, UTIL_BILL_MI_NM, "
				+ "APLC_TYP_CD, TIMESTMP, UTIL_APPROVED_DT, ACCT_INFO_TXT, RENT_TAX_CD, SCER_ACCT_SEQ_NBR, UTIL_RCV_DT, "
				+ "PERSN_OCCP_CNT, UTIL_TEL_FUND_NBR, LIFELINE_SERV_IND, SYS_USER_ID) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			updateUtils.setString(1, applicant.getAccountNum());
			updateUtils.setString(2, applicant.getUtility().get(i).getSupplierCode());
			updateUtils.setString(3, applicant.getUtility().get(i).getLastName());
			updateUtils.setString(4, applicant.getUtility().get(i).getFirstName());
			updateUtils.setString(5, applicant.getUtility().get(i).getMiddleInitial());
			updateUtils.setString(6, "E");
			updateUtils.setString(7, new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SSS a").format(new Date()));			
			updateUtils.setString(8, new SimpleDateFormat("dd-MMM-yy").format(new Date()));			
			updateUtils.setString(9, applicant.getUtility().get(i).getAccountNum());
			updateUtils.setString(10, applicant.getUtility().get(i).getIncludedInRent());
			updateUtils.setString(11, "100");
			updateUtils.setString(12, applicant.getUtility().get(i).getDateReceived());
			updateUtils.setInt(13, applicant.getUtility().get(i).getHouseholdCount());
			updateUtils.setString(14, applicant.getUtility().get(i).getPhoneNum());
			updateUtils.setString(15, applicant.getUtility().get(i).getServiceRequested());
			updateUtils.setString(16, applicant.getSystemUser());
			
			updateUtils.executeUpdate();
			updateUtils.close();
		}
		
		conn.close();
		return rowsUpdated;
	}

}