package com.itafin.lifeline.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class LifelineProperties {
	
	private static Logger logger = Logger.getLogger(LifelineProperties.class);
	
	private Properties props;
	private static final String PROPERTIES_PATH = "/app/latax/properties/vendor.properties";
	
	private String mVersionNumber = "0.0";
	
	private long mMaxFileSize = 1048576;
	private String mDocumentUploadFilePath;
	private List<String> mDocumentAllowedFileTypes;
	
	public LifelineProperties() {
		this.props = loadProperties();
		setVersionNumber(props.getProperty("app.versionNumber"));
		setMaxUploadFileSize(Long.parseLong(props.getProperty("app.maxUploadFileSize")));
		setDocumentUploadFilePath(props.getProperty("app.documentUploadFilePath"));
		setDocumentAllowedFileTypes(props.getProperty("app.documentAllowedFileTypes"));
	}
	
	private Properties loadProperties() {
		
		Properties newProps = new Properties();
		try {
			InputStream input = getClass().getResourceAsStream("/lifeline.properties");
			newProps.load(input);
			return newProps;
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Could not load properties file");
		}
		return null;	
	}
	
	public Properties getProperties() {
		return this.props;
	}

	public String getVersionNumber() {
		return this.mVersionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.mVersionNumber = versionNumber;
	}

	public long getMaxUploadFileSize() {
		return mMaxFileSize;
	}

	public void setMaxUploadFileSize(long mMaxFileSize) {
		this.mMaxFileSize = mMaxFileSize;
	}
	
	public String getDocumentUploadFilePath() {
		return mDocumentUploadFilePath;
	}

	public void setDocumentUploadFilePath(String documentUploadFilePath) {
		this.mDocumentUploadFilePath = documentUploadFilePath;
	}

	public List<String> getDocumentAllowedFileTypes() {
		return mDocumentAllowedFileTypes;
	}

	public void setDocumentAllowedFileTypes(String documentAllowedFileTypes) {
		List<String> list_types = new ArrayList<String>();
		String[] str_arr_types = documentAllowedFileTypes.split(",");
		for(int i = 0; i < str_arr_types.length; i++) {
			list_types.add(str_arr_types[i].trim().toUpperCase());
		}
		if(null == this.mDocumentAllowedFileTypes) {
			this.mDocumentAllowedFileTypes = new ArrayList<String>();
		}
		this.mDocumentAllowedFileTypes.addAll(list_types);
	}
	
	// unused
//	public boolean setSCERSProperties(ServletContext context) {
//		boolean flag = false;
//		try {
//	    InputStream SCERSConf = context.getResourceAsStream(File.separator+"WEB-INF"+File.separator+"SCERS.properties");
//	    Properties SCERSProperties = new Properties();
//	    SCERSProperties.load(SCERSConf);
//	    setEnableLDAPLogin(Boolean.valueOf(SCERSProperties.getProperty("enable_ldap_login", "true")));
//	    setEnableNormalLogin(Boolean.valueOf(SCERSProperties.getProperty("enable_normal_backup_login", "true")));
//	    setVersionNumber(SCERSProperties.getProperty("version_number", "0.0"));
//
//	    logger.debug("SCERS properties loaded.");
//	    flag = true;
//  	} catch (Exception e) {
//      logger.error("SCERSPropertiesLoader :init(): Exception " + e);
//      System.out.println(e);
//      flag = false;
//  	}
//
//		return flag;
//	}
//	

}
