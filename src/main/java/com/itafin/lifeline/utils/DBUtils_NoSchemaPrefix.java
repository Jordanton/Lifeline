package com.itafin.lifeline.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import com.itafin.lifeline.model.Address;
import com.itafin.lifeline.model.Applicant;
import com.itafin.lifeline.model.Supplier;
import com.itafin.lifeline.model.UserAccount;
import com.itafin.lifeline.model.Utility;

import oracle.jdbc.OracleTypes;

public class DBUtils_NoSchemaPrefix {
	public static void insertApplicant(Connection conn, Applicant applicant) throws SQLException {
		CallableStatement query = conn.prepareCall(
			"BEGIN INSERT INTO TUTIL_EXEMP_APLCNT(SCER_ACCT_NBR, SCER_ACCT_SEQ_NBR, APLCNT_SSN, APLCNT_LST_NM, "
				+ "APLCNT_FRST_NM, APLCNT_MI_NM, DWP_ACCT_NBR, SYS_USER_ID, TIMESTMP, APLCNT_CLASS_CD, MSTR_STTS_CD, MSTR_STTS_DT, "
				+ "BRTH_DT, GRS_INC_AMT, DAYTIME_TEL_NBR, SERV_STR_NBR, SERV_STR_FRAC_NBR, SERV_STR_DIR_CD, SERV_STR_NM, SERV_STR_TYP_CD, "
				+ "SERV_UNIT_NBR, SERV_CITY_NM, SERV_ZIP_CD, SERV_MBL_PK_NM, APLCNT_MOVE_IN_DT, RES_TYP_CD, MAIL_ADDR_IND, MAIL_STR_NBR, "
				+ "MAIL_STR_FRAC_NBR, MAIL_STR_DIR_CD, MAIL_STR_NM, MAIL_STR_TYP_CD, MAIL_UNIT_NBR, MAIL_CITY_NM, MAIL_ST_CD, MAIL_ZIP_CD, "
				+ "MAIL_MBL_PK_NM) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) RETURNING SCER_ACCT_NBR INTO ?; END;");

		// Applicant personal information
		query.setString(1, applicant.getAccountNum());
		query.setString(2, applicant.getSequenceNum());
		query.setString(3, applicant.getSocialSecurity());
		query.setString(4, applicant.getLastName());
		query.setString(5, applicant.getFirstName());
		query.setString(6, applicant.getMiddleName());
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
		query.setString(19, serviceAddress.getStreetName());
		query.setString(20, serviceAddress.getStreetType());
		query.setString(21, serviceAddress.getUnitNum());
		query.setString(22, serviceAddress.getCity());
		query.setString(23, serviceAddress.getZipCode());
		query.setString(24, serviceAddress.getMobileParkName());
		query.setString(25, applicant.getMoveInDate());
		query.setString(26, serviceAddress.getResidenceType());

		// Mailing address
		query.setString(27, mailingAddress.getAddressType());
		query.setString(28, mailingAddress.getStreetNum());
		query.setString(29, mailingAddress.getFracNum());
		query.setString(30, mailingAddress.getStreetDir());
		query.setString(31, mailingAddress.getStreetName());
		query.setString(32, mailingAddress.getStreetType());
		query.setString(33, mailingAddress.getUnitNum());
		query.setString(34, mailingAddress.getCity());
		query.setString(35, mailingAddress.getState());
		query.setString(36, mailingAddress.getZipCode());
		query.setString(37, mailingAddress.getMobileParkName());
		query.registerOutParameter(38, OracleTypes.VARCHAR);

		query.execute();
		
		String serial = query.getString(38);
		
		query.close();		
		
		System.out.println("SERIAL NUMBER PK IS: " + serial);
		System.out.println("SERIAL NUMBER PK IS: " + serial);
		System.out.println("SERIAL NUMBER PK IS: " + serial);		
		
		// Adds applicant to APLC table 
		// Mainly to store comments
		PreparedStatement addApplicantToAPLC = conn.prepareStatement("INSERT INTO TUTIL_EXEMP_APLC (SCER_ACCT_NBR, SCER_ACCT_SEQ_NBR, APLC_TYP_CD, SYS_USER_ID, TIMESTMP, ACTN_PRCS_CD, APLC_STTS_CD, APLC_STTS_DT, APLC_RCV_DT, APLC_CMT_TXT, ORI_APLC_RCV_DT, UPDT_STTS_SRCE_CD, APLC_MAIL_DT, APLC_CORE_STTS_CD, APLC_CORE_RCV_DT) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		addApplicantToAPLC.setString(1, serial);
		addApplicantToAPLC.setString(2, applicant.getSequenceNum());
		addApplicantToAPLC.setString(3, "E"); // exemptions only
		addApplicantToAPLC.setString(4, applicant.getSystemUser());
		addApplicantToAPLC.setString(5, applicant.getTimestamp());
		
		addApplicantToAPLC.setString(6, null); // ACTN_PRCS_CD all empty
		addApplicantToAPLC.setString(7, "RC"); // Change this?
		addApplicantToAPLC.setString(8, new SimpleDateFormat("dd-MMM-yy").format(new Date()));
		addApplicantToAPLC.setString(9, new SimpleDateFormat("dd-MMM-yy").format(new Date()));
		addApplicantToAPLC.setString(10, applicant.getComments());
		
		addApplicantToAPLC.setString(11, new SimpleDateFormat("dd-MMM-yy").format(new Date()));
		addApplicantToAPLC.setString(12, null);
		addApplicantToAPLC.setString(13, new SimpleDateFormat("dd-MMM-yy").format(new Date()));
		addApplicantToAPLC.setString(14, "RC");
		addApplicantToAPLC.setString(15, new SimpleDateFormat("dd-MMM-yy").format(new Date()));
		
		addApplicantToAPLC.executeUpdate();
		addApplicantToAPLC.close();
		
		/*
		 * Now use the serial number to add the utilities 
		 * to DTL_APLCT_CUR
		 */				

		for (int i = 0; i < applicant.getUtility().size(); i++) {
			// loop through applicant utilities and add new row for each util
			// Applicant personal information			
			PreparedStatement queryUtil = conn.prepareStatement("INSERT INTO TAPLC_DTL_CURR (SCER_ACCT_NBR, UTIL_SUPLR_CD, UTIL_BILL_LST_NM, UTIL_BILL_FRST_NM, UTIL_BILL_MI_NM, APLC_TYP_CD, TIMESTMP, APLC_ENT_DT, ACCT_INFO_TXT, RENT_TAX_CD, SCER_ACCT_SEQ_NBR, APLC_RCV_DT, PERSN_OCCP_CNT, UTIL_TEL_FUND_NBR, LIFELINE_SERV_IND) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			queryUtil.setString(1, serial);
			queryUtil.setString(2, applicant.getUtility().get(i).getSupplierCode());
			queryUtil.setString(3, applicant.getUtility().get(i).getLastName());
			queryUtil.setString(4, applicant.getUtility().get(i).getFirstName());
			queryUtil.setString(5, applicant.getUtility().get(i).getMiddleInitial());
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
			queryUtil.executeUpdate();
			queryUtil.close();
		}		
	}
	
	public static ArrayList<Utility> getUtilitiesFromAccountNum(Connection conn, String accountNum) throws SQLException {
		ArrayList utils = new ArrayList<Utility>();
		
		PreparedStatement query = conn.prepareStatement("SELECT * FROM taplc_dtl_curr WHERE scer_acct_nbr = ?");
		query.setString(1, accountNum);
		
		ResultSet rs = query.executeQuery();
				
		while(rs.next()) {
			Utility u = new Utility();
			Supplier s = new Supplier();
			
			s.setSupplierCode(rs.getString("UTIL_SUPLR_CD"));
			u.setSupplier(s);
			
			u.setLastName(rs.getString("UTIL_BILL_LST_NM"));
			u.setFirstName(rs.getString("UTIL_BILL_FRST_NM"));
			u.setMiddleInitial(rs.getString("UTIL_BILL_MI_NM"));
			u.setHouseholdCount(rs.getInt("PERSN_OCCP_CNT"));
			u.setAccountNum(rs.getString("ACCT_INFO_TXT"));
			
			u.setDateReceived(rs.getString("APLC_RCV_DT"));
			u.setDateApproved(rs.getString("APLC_ENT_DT"));
			u.setDateSubmitted(rs.getString("APLC_FACS_SUBM_DT"));
			
			utils.add(u);
		}
		
		return utils;
	}
	
	public static Applicant getApplicantFromAccountNum(Connection conn, String accountNum) throws SQLException {
		PreparedStatement query = conn.prepareStatement("SELECT * FROM tutil_exemp_aplcnt WHERE scer_acct_nbr = ?");
		query.setString(1, accountNum);
		
		ResultSet rs = query.executeQuery();
		
		Applicant a = new Applicant();
		if(rs.next()) {
  		a.setAccountNum(rs.getString("SCER_ACCT_NBR"));
  		a.setLastName(rs.getString("APLCNT_LST_NM"));
  		a.setFirstName(rs.getString("APLCNT_FRST_NM"));
  		a.setMiddleName(rs.getString("APLCNT_MI_NM"));
  		a.setStatusDate(new SimpleDateFormat("dd-MMM-yy").format(rs.getDate("MSTR_STTS_DT")));
  		a.setDateOfBirth(new SimpleDateFormat("dd-MMM-yy").format(rs.getDate("BRTH_DT")));
  		a.setDayPhone(rs.getString("DAYTIME_TEL_NBR"));
  		a.setClassCode(rs.getString("APLCNT_CLASS_CD"));
  				
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
		
		return a;
	}

	public static String getSupplierSerial(Connection conn, Supplier supplier)
		throws SQLException {
		// Retrieves the SERIAL from the supplier added above
		// SERIAL is auto generated as an identity column in SQL Developer
		// Stores the serial in a variable to reference in next query
		PreparedStatement query = conn.prepareStatement(
			"SELECT TUTIL_SUPLR.UTIL_SUPLR_ID FROM TUTIL_SUPLR WHERE UTIL_SUPLR_CD = ?");
		query.setString(1, supplier.getSupplierCode());
		ResultSet rs = query.executeQuery();
		String serial = "";
		if (rs.next()) {
			serial = rs.getString("UTIL_SUPLR_ID");
		}
		query.executeUpdate();
		query.close();

		return serial;
	}

	/*
	 * This method will update the information of a supplier
	 */
	public static void updateSupplier(Connection conn, Supplier supplierToUpdate)
		throws SQLException {
		// Format the timestamp to match previous DB2 timestamp
		// NOTE: Timestamp is declared once here to prevent differing timestamps
		// Since we are performing two queries to two tables
		String timestamp = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SSS a")
			.format(new Date());

		PreparedStatement query = conn.prepareStatement("UPDATE TUTIL_SUPLR "
			+ "SET UTIL_SUPLR_NM = ?, UTIL_SUPLR_CD = ?, UTIL_SUPLR_STTS_CD = ?, "
			+ "TIMESTMP = ? WHERE TUTIL_SUPLR.UTIL_SUPLR_ID = ?");
		query.setString(1, supplierToUpdate.getSupplierName());
		query.setString(2, supplierToUpdate.getSupplierCode());
		query.setString(3, supplierToUpdate.getSupplierStatusCode());
		query.setString(4, timestamp);
		query.setString(5, supplierToUpdate.getSerial());

		query.executeUpdate();
		query.close();

		query = conn.prepareStatement("UPDATE TUTIL_TYP "
			+ "SET UTIL_SUPLR_CD = ?, UTIL_TYP_CD = ?, UTIL_TYP_DESC = ?, UTIL_TYP_STTS_CD = ?,"
			+ " TIMESTMP = ? WHERE TUTIL_TYP.UTIL_SUPLR_ID = ?");
		query.setString(1, supplierToUpdate.getSupplierCode());
		query.setString(2, supplierToUpdate.getUtilityType());
		query.setString(3, supplierToUpdate.getDescription());
		query.setString(4, supplierToUpdate.getUtilityStatusCode());
		query.setString(5, timestamp);
		query.setString(6, supplierToUpdate.getSerial());

		query.executeUpdate();
		query.close();
	}

	/*
	 * This method will send a SQL query from the database to return a list of
	 * suppliers
	 */
	public static List<Supplier> listSuppliers(Connection conn)
		throws SQLException {
		String preparedStatement = "SELECT * FROM TUTIL_SUPLR INNER JOIN TUTIL_TYP "
			+ "ON TUTIL_SUPLR.UTIL_SUPLR_ID = TUTIL_TYP.UTIL_SUPLR_ID ORDER BY TUTIL_SUPLR.UTIL_SUPLR_CD";

		PreparedStatement pstm = conn.prepareStatement(preparedStatement);

		// Execute the SQL query on the database
		// Store each result in the list to display in a table
		// Actual display of table occurs in VIEWs
		ResultSet rs = pstm.executeQuery();
		List<Supplier> list = new ArrayList<Supplier>();

		while (rs.next()) {
			Supplier supplier = new Supplier(rs.getString("UTIL_SUPLR_NM"),
				rs.getString("UTIL_SUPLR_CD"));
			supplier.setUser(rs.getString("SYS_USER_ID"));
			supplier.setSupplierStatusCode(rs.getString("UTIL_SUPLR_STTS_CD"));
			supplier.setDescription(rs.getString("UTIL_TYP_DESC"));
			supplier.setUtilityType(rs.getString("UTIL_TYP_CD"));
			supplier.setUtilityStatusCode(rs.getString("UTIL_TYP_STTS_CD"));
			supplier.setSerial(rs.getString("UTIL_SUPLR_ID"));
			// Note: Timestamp is retrieved from DB as SQL Timestamp type
			// java.sql.Timestamp extends java.util.Date, so it can be formatted as
			// SimpleDateFormat
			supplier.setTimestamp(new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SSS a")
				.format(rs.getTimestamp("TIMESTMP")));

			list.add(supplier);
		}
		return list;
	}

	public static ArrayList<Applicant> searchApplicants(Connection conn,
		ArrayList<String> keywordList) throws SQLException {
		
		PreparedStatement preparedStatement;
		
		preparedStatement = conn
			.prepareStatement("SELECT * FROM tutil_exemp_aplcnt "
				+ "WHERE (aplcnt_lst_nm LIKE UPPER(?) or aplcnt_lst_nm is null) "
				+ "AND (aplcnt_frst_nm LIKE UPPER(?) or aplcnt_frst_nm is null) "
				+ "AND (aplcnt_mi_nm LIKE UPPER(?) or aplcnt_mi_nm is null)");
		
		preparedStatement.setString(1, keywordList.get(0) + "%");
		preparedStatement.setString(2, keywordList.get(1) + "%");
		preparedStatement.setString(3, keywordList.get(2) + "%");

		ResultSet rs = preparedStatement.executeQuery();

		ArrayList<Applicant> results = new ArrayList<Applicant>();

		while (rs.next()) {
			Applicant applicant = new Applicant();
			applicant.setAccountNum(rs.getString("SCER_ACCT_NBR"));

			System.out.println("SCER_ACCT_NBR: " + rs.getString("SCER_ACCT_NBR"));

			Address serviceAddress = new Address(rs.getString("SERV_STR_NBR"),
				rs.getString("SERV_STR_FRAC_NBR"), rs.getString("SERV_STR_DIR_CD"),
				rs.getString("SERV_STR_NM"), rs.getString("SERV_STR_TYP_CD"),
				rs.getString("SERV_CITY_NM"), rs.getString("SERV_ZIP_CD"),
				rs.getString("SERV_MBL_PK_NM"), rs.getString("RES_TYP_CD"),
				rs.getString("SERV_UNIT_NBR"));
			
			Address mailingAddress = new Address(rs.getString("MAIL_STR_NBR"),
				rs.getString("MAIL_STR_FRAC_NBR"), rs.getString("MAIL_STR_DIR_CD"),
				rs.getString("MAIL_STR_NM"), rs.getString("MAIL_STR_TYP_CD"),
				rs.getString("MAIL_CITY_NM"), rs.getString("MAIL_ZIP_CD"),
				rs.getString("MAIL_MBL_PK_NM"), rs.getString("MAIL_ST_CD"),
				rs.getString("MAIL_UNIT_NBR"));

			applicant.setServiceAddress(serviceAddress);
			applicant.setMailingAddress(mailingAddress);
			
			applicant.setLastName(rs.getString("APLCNT_LST_NM"));
			applicant.setFirstName(rs.getString("APLCNT_FRST_NM"));
			applicant.setMiddleName(rs.getString("APLCNT_MI_NM"));
			applicant.setDayPhone(rs.getString("DAYTIME_TEL_NBR"));			
			applicant.setSystemUser(rs.getString("SYS_USER_ID"));
			
			Date masterStatusDate = rs.getDate("MSTR_STTS_DT");
			if (masterStatusDate != null) {
				applicant.setStatusDate(new SimpleDateFormat("dd-MMM-yy").format(rs.getDate("MSTR_STTS_DT")));
			}

			Date dateOfBirth = rs.getDate("BRTH_DT");
			if (dateOfBirth != null) {
				applicant.setDateOfBirth(
					new SimpleDateFormat("dd-MMM-yy").format(rs.getDate("BRTH_DT")));
			}

			applicant.setTimestamp(new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SSS a")
				.format(rs.getTimestamp("TIMESTMP")));

			results.add(applicant);
		}

		return results;
	}
		
	// ANYTHING DONE HERE MUST BE DONE TO THE ABOVE METHOD (searchApplicant)
	public static ArrayList<Applicant> searchApplicantsByAddress(Connection conn,
		ArrayList<String> keywordList) throws SQLException {
		
		PreparedStatement preparedStatement;
		
		preparedStatement = conn
			.prepareStatement("SELECT * FROM tutil_exemp_aplcnt "
				+ "WHERE (serv_str_nbr LIKE UPPER(?) or serv_str_nbr is null) "
				+ "AND (serv_str_frac_nbr LIKE UPPER(?) or serv_str_frac_nbr is null) "
				+ "AND (serv_str_dir_cd LIKE UPPER(?) or serv_str_dir_cd is null) "
				+ "AND (serv_str_nm LIKE UPPER(?) or serv_str_nm is null) "
				+ "AND (serv_str_typ_cd LIKE UPPER(?) or serv_str_typ_cd is null) "
				+ "AND (serv_unit_nbr LIKE UPPER(?) or serv_unit_nbr is null) "
				+ "AND (serv_city_nm LIKE UPPER(?) or serv_city_nm is null) "
				+ "AND (serv_zip_cd LIKE UPPER(?) or serv_zip_cd is null) "
				+ "AND (serv_mbl_pk_nm LIKE UPPER(?) or serv_mbl_pk_nm is null)");
		
		preparedStatement.setString(1, keywordList.get(0) + "%");
		preparedStatement.setString(2, keywordList.get(1) + "%");
		preparedStatement.setString(3, keywordList.get(2) + "%");
		preparedStatement.setString(4, keywordList.get(3) + "%");
		preparedStatement.setString(5, keywordList.get(4) + "%");
		preparedStatement.setString(6, keywordList.get(5) + "%");
		preparedStatement.setString(7, keywordList.get(6) + "%");
		preparedStatement.setString(8, keywordList.get(7) + "%");
		preparedStatement.setString(9, keywordList.get(8) + "%");

		ResultSet rs = preparedStatement.executeQuery();

		ArrayList<Applicant> results = new ArrayList<Applicant>();

		for (int i = 0; i < keywordList.size(); i++) {
			System.out.println("KeywordList " + i + ": " + keywordList.get(i));
		}
		
		while (rs.next()) {
			Applicant applicant = new Applicant();
			applicant.setAccountNum(rs.getString("SCER_ACCT_NBR"));

			System.out.println("SCER_ACCT_NBR: " + rs.getString("SCER_ACCT_NBR"));

			Address serviceAddress = new Address(rs.getString("SERV_STR_NBR"),
				rs.getString("SERV_STR_FRAC_NBR"), rs.getString("SERV_STR_DIR_CD"),
				rs.getString("SERV_STR_NM"), rs.getString("SERV_STR_TYP_CD"),
				rs.getString("SERV_CITY_NM"), rs.getString("SERV_ZIP_CD"),
				rs.getString("SERV_MBL_PK_NM"), rs.getString("RES_TYP_CD"),
				rs.getString("SERV_UNIT_NBR"));
			
			Address mailingAddress = new Address(rs.getString("MAIL_STR_NBR"),
				rs.getString("MAIL_STR_FRAC_NBR"), rs.getString("MAIL_STR_DIR_CD"),
				rs.getString("MAIL_STR_NM"), rs.getString("MAIL_STR_TYP_CD"),
				rs.getString("MAIL_CITY_NM"), rs.getString("MAIL_ZIP_CD"),
				rs.getString("MAIL_MBL_PK_NM"), rs.getString("MAIL_ST_CD"),
				rs.getString("MAIL_UNIT_NBR"));
					
			applicant.setServiceAddress(serviceAddress);
			applicant.setMailingAddress(mailingAddress);
			
			applicant.setLastName(rs.getString("APLCNT_LST_NM"));
			applicant.setFirstName(rs.getString("APLCNT_FRST_NM"));
			applicant.setMiddleName(rs.getString("APLCNT_MI_NM"));
			applicant.setDayPhone(rs.getString("DAYTIME_TEL_NBR"));			
			applicant.setSystemUser(rs.getString("SYS_USER_ID"));
			
			Date masterStatusDate = rs.getDate("MSTR_STTS_DT");
			if (masterStatusDate != null) {
				applicant.setStatusDate(new SimpleDateFormat("dd-MMM-yy").format(rs.getDate("MSTR_STTS_DT")));
			}

			Date dateOfBirth = rs.getDate("BRTH_DT");
			if (dateOfBirth != null) {
				applicant.setDateOfBirth(
					new SimpleDateFormat("dd-MMM-yy").format(rs.getDate("BRTH_DT")));
			}

			applicant.setTimestamp(new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SSS a")
				.format(rs.getTimestamp("TIMESTMP")));

			results.add(applicant);
		}

		return results;
	}

	public static List<Supplier> searchSuppliers(Connection conn, String keyword)
		throws SQLException {
		PreparedStatement pstm = conn.prepareStatement(
			"SELECT * FROM TUTIL_SUPLR INNER JOIN TUTIL_TYP ON TUTIL_SUPLR.UTIL_SUPLR_ID = TUTIL_TYP.UTIL_SUPLR_ID "
				+ "WHERE TUTIL_SUPLR.UTIL_SUPLR_NM LIKE ? OR TUTIL_SUPLR.UTIL_SUPLR_CD LIKE ? OR TUTIL_TYP.UTIL_TYP_DESC LIKE ? ORDER BY TUTIL_SUPLR.UTIL_SUPLR_CD");

		// This allows the search to only search upper case
		// Also trims any whitespace that can cause inaccurate searches
		keyword = keyword.toUpperCase().trim();
		pstm.setString(1, "%" + keyword + "%");
		pstm.setString(2, "%" + keyword + "%");
		pstm.setString(3, "%" + keyword + "%");

		ResultSet rs = pstm.executeQuery();
		List<Supplier> list = new ArrayList<Supplier>();

		while (rs.next()) {
			Supplier supplier = new Supplier(rs.getString("UTIL_SUPLR_NM"),
				rs.getString("UTIL_SUPLR_CD"));
			supplier.setUser(rs.getString("SYS_USER_ID"));
			supplier.setSupplierStatusCode(rs.getString("UTIL_SUPLR_STTS_CD"));
			supplier.setDescription(rs.getString("UTIL_TYP_DESC"));
			supplier.setUtilityType(rs.getString("UTIL_TYP_CD"));
			supplier.setUtilityStatusCode(rs.getString("UTIL_TYP_STTS_CD"));
			supplier.setSerial(rs.getString("UTIL_SUPLR_ID"));
			// Note: Timestamp is retrieved from DB as SQL Timestamp type
			// java.sql.Timestamp extends java.util.Date, so it can be formatted as
			// SimpleDateFormatAsk Question

			supplier.setTimestamp(new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SSS a")
				.format(rs.getTimestamp("TIMESTMP")));

			list.add(supplier);
		}
		return list;
	}

	/*
	 * This method will send a SQL query to add a supplier to the list NOTE: Due
	 * to required support for the original mainframe application Each query must
	 * be done on both tables
	 */
	public static void addSupplier(Connection conn, Supplier supplierToAdd)
		throws SQLException {
		// Prepare SQL statement and insert new supplier into first table
		// SCERS_TUTIL_SUPLR
		PreparedStatement query = conn.prepareStatement(
			"INSERT INTO TUTIL_SUPLR (UTIL_SUPLR_CD, UTIL_SUPLR_NM, UTIL_SUPLR_STTS_CD, SYS_USER_ID, TIMESTMP) VALUES (?, ?, ?, ?, ?)");
		query.setString(1, supplierToAdd.getSupplierCode());
		query.setString(2, supplierToAdd.getSupplierName());
		query.setString(3, supplierToAdd.getSupplierStatusCode());
		query.setString(4, supplierToAdd.getUser());
		query.setString(5, supplierToAdd.getTimestamp());

		query.executeUpdate();
		query.close();

		String serial = getSupplierSerial(conn, supplierToAdd);
		supplierToAdd.setSerial(serial);

		// Prepare SQL statement and insert new supplier into second table,
		// SCERS_TUTIL_TYP
		// Inserts information based on serial number of previous insert
		query = conn.prepareStatement(
			"INSERT INTO TUTIL_TYP (UTIL_SUPLR_CD, UTIL_TYP_CD, UTIL_TYP_DESC, UTIL_TYP_STTS_CD, SYS_USER_ID, TIMESTMP, UTIL_SUPLR_ID) VALUES (?, ?, ?, ?, ?, ?, ?)");
		query.setString(1, supplierToAdd.getSupplierCode());
		query.setString(2, supplierToAdd.getUtilityType());
		query.setString(3, supplierToAdd.getDescription());
		query.setString(4, supplierToAdd.getUtilityStatusCode());
		query.setString(5, supplierToAdd.getUser());
		query.setString(6, supplierToAdd.getTimestamp());
		query.setString(7, supplierToAdd.getSerial());

		query.executeUpdate();
		query.close();
	}

	public static boolean findUser(Connection conn, String usernameToValidate,
		String passwordToValidate) throws SQLException {
		String sql = "Select a.User_Name, a.Password, a.Gender from SCERS.User_Account a "
			+ " where UPPER(a.User_Name) = ? and a.password= ?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, usernameToValidate.toUpperCase());
		pstm.setString(2, passwordToValidate);
		ResultSet rs = pstm.executeQuery();

		if (rs.next()) {
			return true;	
		}
		return false;
	}

	public static UserAccount findUser(Connection conn, String usernameToFind)
		throws SQLException {
		String sql = "Select a.User_Name, a.Password, a.Gender from SCERS.User_Account a "
			+ " where a.User_Name = ? ";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, usernameToFind);

		ResultSet rs = pstm.executeQuery();

		if (rs.next()) {
			String password = rs.getString("password");
			String username = rs.getString("username");
			UserAccount user = new UserAccount(username, password);
			return user;
		}
		return null;
	}
	
	/*
	 * Method to delete a supplier from the DB Note: Deletes PK from table
	 * SCERS_TUTIL_SUPLR and cascade deletes from the other table
	
	public static void deleteSupplier(Connection conn, Supplier supplierToDelete)
		throws SQLException {
		PreparedStatement query = conn
			.prepareStatement("DELETE FROM SCERS_TUTIL_TYP WHERE UTIL_SUPLR_ID = ?");

		query.setString(1, supplierToDelete.getSerial());
		query.executeUpdate();
		query.close();
	}	
	**/
}