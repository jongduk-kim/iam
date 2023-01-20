package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.property.CobraValue;

public class DBConnection {
	private static Logger logger = Logger.getLogger(DBConnection.class);

	// ITAS DB Connection 을 얻는다.
	public synchronized final Connection getDBConnection(String JDBC_DRIVER, String JDBC_URL, String DB_LOGIN_ID, String DB_LOGIN_PASSWORD) {
		if("".equals( JDBC_DRIVER )) {			
			if(logger.isEnabledFor(Level.WARN)) {
				System.out.println("JDBC DRIVER is not available.");
				logger.warn("JDBC DRIVER is not available.");
			}
			return null;
		}
		
		if("".equals( JDBC_URL )) {			
			if(logger.isEnabledFor(Level.WARN)) {
				System.out.println("JDBC URL is not available.");
				logger.warn("JDBC URL is not available.");
			}
			return null;
		}
		
		if("".equals( DB_LOGIN_ID )) {			
			if(logger.isEnabledFor(Level.WARN)) {
				System.out.println("JDBC LOGIN ID is not available.");
				logger.warn("JDBC LOGIN ID is not available.");
			}
			return null;
		}	
		
		if("".equals( DB_LOGIN_PASSWORD )) {			
			if(logger.isEnabledFor(Level.WARN)) {
				System.out.println("JDBC LOGIN PASSWORD is not available.");
				logger.warn("JDBC LOGIN PASSWORD is not available.");
			}
			return null;
		}

		Connection conn = null;
		
		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(JDBC_URL, DB_LOGIN_ID, DB_LOGIN_PASSWORD);
		}catch(Exception e){
			logger.warn("Connection Faled :: " + e.toString());
			logger.warn("Connection Faled :: " + e.getMessage());
		}
		
		return conn;
		
	}
	// DB Connection 을 얻는다.
	public synchronized final Connection getDBConnection() throws Exception {
		return getDBConnection(	CobraValue.JDBC_DRIVER, CobraValue.JDBC_URL, CobraValue.DB_LOGIN_ID, CobraValue.DB_LOGIN_PASSWORD);
	}	
}
