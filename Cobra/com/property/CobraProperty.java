package com.property;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class CobraProperty {
	private static Logger logger = Logger.getLogger(CobraProperty.class);
	
	//NTS system변수값을 가져온다.
	public static boolean getSystemProperty() {
		
		if(CobraValue.alreadyBinding) {
			return true;
		}
		
		InputStream	is = CobraValue.class.getResourceAsStream("/cobra.properties");
		Properties	properties = new Properties();
		
		try {
			properties.load(is);
		} catch (IOException ioe) {
			if(logger.isEnabledFor(Level.ERROR)) {
				logger.error("Can not read System property file. ("+ioe.getMessage()+")",ioe);
			}
			return false;
		}		
		
		CobraValue.DIR_LOG 				= properties.getProperty("DIR_LOG");
		CobraValue.WORK_TIME 			= properties.getProperty("WORK_TIME");
		
		CobraValue.JDBC_DRIVER 			= properties.getProperty("JDBC_DRIVER");
		CobraValue.JDBC_URL 			= properties.getProperty("JDBC_URL");
		CobraValue.DB_LOGIN_ID 			= properties.getProperty("DB_LOGIN_ID");
		CobraValue.DB_LOGIN_PASSWORD 	= properties.getProperty("DB_LOGIN_PASSWORD");
		
		// property 정보 바인딩여부
		CobraValue.alreadyBinding = true;
		
		return true;
	}
}
