package sixt.run;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import sixt.dataset.DataSetPost;
import sixt.db.DBManager;


public class RunCreateNewPost {
	private static Logger logger = Logger.getLogger(RunCreateNewPost.class);
	
	private static final String FILE_EXT	= ".txt";
	
	private static final String FILE_DIR	= "D:\\문서\\99.기타\\01.신우편번호\\";
	private static final String FILE_NM		= "20140217_19";
	
	private static final String COLUMNS 	= "ZIP_NO		, ZIP_NO_SEQ	, SIDO_NM	, SIDO_ENG	, SI_GUN_NM	, SI_GUN_ENG	,"
											+ "UP_NM		, UP_ENG		, ROAD_CD	, ROAD_NM	, ROAD_ENG	, JIHA_YN		,"
											+ "GUN_MUL1		, GUN_MUL2		, GUN_MUL3	, DARANG_NM	, SIGUNBUNM	, BUB_CD		,"
											+ "BUB_NM		, LI_NM			, SAY_YN	, JI_CD1	, UP_SEQ	, JI_CD2		";
	
	private static final String COLUMNS_PARAM 	= "? , ? , ? , ? , ? , ? ,	"
												+ "? , ? , ? , ? , ? , ? ,	"
												+ "? , ? , ? , ? , ? , ? ,  "
												+ "? , ? , ? , ? , ? , ?";
	
	public static void start() throws Exception {
		int i = 19;
		//for (int i = 1; i < 19; i++) {
			loaderData(FILE_DIR, FILE_NM, i);	
		//}
	}
	
//	 LODAER SQL 문 작성
	public static String setLoaderSql(String TABLE_NM, String COLUMNS, String COLUMNS_PARAM) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" INSERT INTO 	").append(TABLE_NM).append("\n");
		sb.append(" ( 				").append("\n");
		sb.append(COLUMNS			 ).append("\n");
		sb.append(" ) 				").append("\n");
		sb.append(" VALUES 			").append("\n");
		sb.append(" ( 				").append("\n");
		sb.append(COLUMNS_PARAM		 ).append("\n");
		sb.append(" ) 				").append("\n");
		
		return sb.toString();
	}
	
	
    public static boolean loaderData(String fileDir, String fileName, int i) {
    	Connection conn = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	
    	StringBuffer sb = new StringBuffer();
    	String message = "";
    	HashMap   hm = null;
    	
    	DataSetPost dPost = new DataSetPost();
    	boolean isTrue = true;
    
    	try {
    		conn = DBManager.getConnection();
    			
    		pstmt = conn.prepareStatement(setLoaderSql("TB_NEW_ZIP_NO", COLUMNS, COLUMNS_PARAM));
    		
    		File loaderFile = new File(fileDir, fileName + i + FILE_EXT);
    		    		
    		BufferedReader br = new BufferedReader(new FileReader(loaderFile));
			
			String temp 	= "";
			
			int index = 0;
			
			while((temp = br.readLine()) != null){
				message = temp;
				hm = dPost.getHashMap(message);
				
				if (index != 0) {
					pstmt = dPost.setPreparedStatement(hm, pstmt);
					pstmt.addBatch();	
				}
				
				if(index % 10000 == 0) {	//10000
					System.out.println("FileName " + fileName + i + FILE_EXT + "Count : " + index + "건 처리중");
				}

				if(index % 10000 == 0) {	// 1000
					pstmt.executeBatch();
					conn.commit();			
				}
				index++;
			}
			
			System.out.println("총완료 Count : " + index);
			pstmt.executeBatch();
			conn.commit();
			
			
			br.close();
			conn.commit();
    	} catch (SQLException sqle) {
    		try {
				conn.rollback();
			} catch (SQLException e) {
				logger.debug(e.toString());
			}
    		logger.info(sqle.toString());
    	} catch (Exception e) {
    		try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.debug(e.toString());
			}
    		logger.info(e.toString());
    	} finally {
    		try {
    			if (rs != null) 	{ rs.close(); }
    			if (pstmt != null) 	{ pstmt.close(); }
    			if (conn != null) 	{ conn.close(); }
    		} catch (Exception e) {
    			
    		}
    	}
		
    	return isTrue;
    }
    
}
