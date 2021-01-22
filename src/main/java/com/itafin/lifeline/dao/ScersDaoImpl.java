package com.itafin.lifeline.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.itafin.lifeline.model.Supplier;

@Repository("scersDao")
public class ScersDaoImpl implements ScersDao {

	private static final Logger logger = Logger.getLogger(ScersDaoImpl.class);
	@Autowired
	private JdbcTemplate appDataSourceTemplate;
	
	public void setAppDataSource(DataSource dataSource) {
		this.appDataSourceTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Supplier> retrieve_activeSupplierList() {
		List<Supplier> retList = null;
		
		try {
			String sql = "SELECT * FROM TUTIL_SUPLR INNER JOIN TUTIL_TYP "
				+ " ON TUTIL_SUPLR.UTIL_SUPLR_ID = TUTIL_TYP.UTIL_SUPLR_ID "
				+ " WHERE TUTIL_TYP.UTIL_TYP_STTS_CD = 'A' AND "
				+ " TUTIL_TYP.UTIL_TYP_CD is not NULL "
				+ " ORDER BY TUTIL_SUPLR.UTIL_SUPLR_CD ";
			
			MapSqlParameterSource mapSqlParamSrc = new MapSqlParameterSource();
			
			NamedParameterJdbcTemplate namedParamJdbcTempl = new NamedParameterJdbcTemplate(appDataSourceTemplate);
			retList = namedParamJdbcTempl.query(sql, mapSqlParamSrc,
  				new RowMapper<Supplier>() {
  					@Override
  					public Supplier mapRow(ResultSet rs, int rowNum) throws SQLException {
  
  						Supplier supplier = new Supplier(rs.getString("UTIL_SUPLR_NM"),
  							rs.getString("UTIL_SUPLR_CD"));
  						supplier.setUser(rs.getString("SYS_USER_ID"));
  						supplier.setSupplierStatusCode(rs.getString("UTIL_SUPLR_STTS_CD"));
  						supplier.setDescription(rs.getString("UTIL_TYP_DESC"));
  						supplier.setUtilityType(rs.getString("UTIL_TYP_CD"));
  						supplier.setUtilityStatusCode(rs.getString("UTIL_TYP_STTS_CD"));
  						supplier.setSerial(rs.getString("UTIL_SUPLR_ID"));
  						
  						/* Note: Timestamp is retrieved from DB as SQL Timestamp type
  						 * java.sql.Timestamp extends java.util.Date, so it can be formatted as
  						 * SimpleDateFormat */
  						supplier.setTimestamp(new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SSS a")
  							.format(rs.getTimestamp("TIMESTMP")));
  
  						return supplier;
  					}
  				});
		} catch(DataAccessException e) {
			logger.error("Exception occurred in retrieve_activeSupplierList() - ", e);
		}
		
		return retList;
	}

	@Override
	public Supplier getSupplierByCode(String supplierCode) {
		Supplier supplier = null;
		
		try {
			String sql = "SELECT * FROM TUTIL_SUPLR INNER JOIN TUTIL_TYP "
				+ " ON TUTIL_SUPLR.UTIL_SUPLR_ID = TUTIL_TYP.UTIL_SUPLR_ID "
				+ " WHERE TUTIL_TYP.UTIL_TYP_STTS_CD = 'A' AND "
				+ " TUTIL_TYP.UTIL_TYP_CD is not NULL AND "
				+ " upper(TUTIL_SUPLR.UTIL_SUPLR_CD) = upper(:util_suplr_cd) ";
			
			MapSqlParameterSource mapSqlParamSrc = new MapSqlParameterSource();
			mapSqlParamSrc.addValue("util_suplr_cd", supplierCode);
			
			NamedParameterJdbcTemplate namedParamJdbcTempl = new NamedParameterJdbcTemplate(appDataSourceTemplate);
			
			List<Supplier> suppliers = null;
			suppliers = namedParamJdbcTempl.query(sql, mapSqlParamSrc,
  				new RowMapper<Supplier>() {
  					@Override
  					public Supplier mapRow(ResultSet rs, int rowNum) throws SQLException {
  
  						Supplier supplier = new Supplier(rs.getString("UTIL_SUPLR_NM"),
  							rs.getString("UTIL_SUPLR_CD"));
  						supplier.setUser(rs.getString("SYS_USER_ID"));
  						supplier.setSupplierStatusCode(rs.getString("UTIL_SUPLR_STTS_CD"));
  						supplier.setDescription(rs.getString("UTIL_TYP_DESC"));
  						supplier.setUtilityType(rs.getString("UTIL_TYP_CD"));
  						supplier.setUtilityStatusCode(rs.getString("UTIL_TYP_STTS_CD"));
  						supplier.setSerial(rs.getString("UTIL_SUPLR_ID"));
  						
  						/* Note: Timestamp is retrieved from DB as SQL Timestamp type
  						 * java.sql.Timestamp extends java.util.Date, so it can be formatted as
  						 * SimpleDateFormat */
  						supplier.setTimestamp(new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SSS a")
  							.format(rs.getTimestamp("TIMESTMP")));
  
  						return supplier;
  					}
  				});
			if(null != suppliers && !suppliers.isEmpty()) {
				supplier = suppliers.get(0);
			}
		} catch(DataAccessException e) {
			logger.error("Exception occurred in retrieve_activeSupplierList() - ", e);
		}
		
		return supplier;
	}

}
