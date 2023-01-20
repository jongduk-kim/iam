package sixt.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DataSourceConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDriver;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;


/**
 * <table><tr><td>
 * 자카르차의 커먼스 프로젝트이 commons-dbcp 를 이용한 풀이다.
 * 디비세팅값은 dbpool.properties 에 들어가야 하며
 * 파일의 위치는 클래스 패스내에 있어야 하며 팩키지 않에 들어가지 않아야 한다.
 * DBManager.java
 * </td></tr></table>
 */

public class DBManager {
	
	private static Logger 	logger = Logger.getLogger(DBManager.class);
	private static DBManager instance = null;

	/**
	 * 커넥션을 얻어 온다. 
	 * getConnection
	 * @param connName
	 * @return
	 * @throws SQLException Connection
	 */
	public static Connection getConnection() throws SQLException{
	    String connName = "oracle";
		if (instance == null) {
			
			if (!initDrivers(connName)) return null;
		}
		Connection conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:" + connName);
		return conn;
	}
	
	/**
	 * 커넥션의 세팅값이 있는지 조사한다. 
	 * initDrivers
	 * @return boolean
	 */
	public static boolean initDrivers(String connName){
		if (instance == null){
			DBManager  connPool = new DBManager();
			
			InputStream	is = DBManager.class.getResourceAsStream("/sixt.properties");
			
			Properties  props  = new Properties();
			
			try {
				props.load(is);
				props.put("db" , connName);
				connPool.loadDrivers(props);
			} catch(Exception e) {
				return false;
			}
			instance = connPool;
		}
		return true;
	}
	
	/**
	 * BasicDataSource 에 디비 세팅값을 세팅한다. 
	 * loadDrivers
	 * @param props void
	 */
	private void loadDrivers(Properties props){

		BasicDataSource  bds 			= null;
		String driver  					= null;
		String url   					= null;
		String db   					= null;
		String user  					= null;
		String password 				= null;
		String valiDationquery			= null;
		
		int   maxActive = 30;
		int   maxIdle  = 0;
		int   maxWait  = 15000;
		boolean  defaultAutoCommit = false;
		boolean  defaultReadOnly   = false;

		try {
			db							= props.getProperty("db");
			driver     					= props.getProperty(db + ".driver");
			url      					= props.getProperty(db + ".url");
			user     					= props.getProperty(db + ".user");
			password    				= props.getProperty(db + ".password");
			maxActive    				= Integer.parseInt(props.getProperty(db + ".maxActive"));
			maxIdle     				= Integer.parseInt(props.getProperty(db + ".maxIdle"));
			maxWait     				= Integer.parseInt(props.getProperty(db + ".maxWait"));
			defaultAutoCommit 			= props.getProperty(db + ".defaultAutoCommit").equals("true");
			defaultReadOnly 	  		= props.getProperty(db + ".defaultReadOnly").equals("true");
			valiDationquery				= props.getProperty(db + ".validationquery");
			
			bds = new BasicDataSource();
			bds.setDriverClassName(driver);
			bds.setUrl(url);
			bds.setUsername(user);
			bds.setPassword(password);
			bds.setMaxActive(maxActive);
			bds.setMaxIdle(maxIdle);
			bds.setMaxWait(maxWait);
			bds.setDefaultAutoCommit(defaultAutoCommit);
			bds.setDefaultReadOnly(defaultReadOnly);
			bds.setValidationQuery(valiDationquery);
			
			createPools(db, bds);
		} catch(Exception e) {
			logger.error("Can't initialize pool : " + db);
		}
	}

	/**
	 * 실제로 디비커넥션을 생성 한다. 
	 * createPools
	 * @param pool
	 * @param bds
	 * @throws Exception void
	 */
	private void createPools(String pool, BasicDataSource bds) throws Exception {
		try {
			ObjectPool connectionPool = new GenericObjectPool(null);
			ConnectionFactory  connectionFactory = new DataSourceConnectionFactory(bds);
			new PoolableConnectionFactory(connectionFactory, connectionPool, null, null, false, true);
			PoolingDriver driver = new PoolingDriver();
			driver.registerPool(pool, connectionPool);
		} catch(Exception e) {
			logger.error(e.toString());
		}
	}
}
