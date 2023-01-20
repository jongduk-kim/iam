package sixt.run;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import sixt.db.DBManager;

public class CreateSMS {
	
	private static final String setSelectSql() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("	SELECT 								").append("\n");
		sb.append("		BRAN_NM		AS BRAN_NM		,	").append("\n");
		sb.append("		FROM_CALL	AS FROM_CALL	,	").append("\n");
		sb.append("		USER_NM		AS USER_NM		,	").append("\n");
		sb.append("		TO_CALL		AS TO_CALL		,	").append("\n");
		sb.append("		TITLE		AS TITLE		,	").append("\n");
		sb.append("		CONTENTS	AS CONTENTS			").append("\n");
		sb.append("   FROM TB_TMP_SMS 					").append("\n");
		sb.append("  WHERE 1 = 2	 					").append("\n");
		
		return sb.toString();
	}
	
	
	private static final String setInsertMMSSql() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("	 INSERT INTO MMS_CONTENTS_INFO (	").append("\n");
		sb.append("	 	FILE_CNT	,					").append("\n");
		sb.append("	 	MMS_BODY	,					").append("\n");
		sb.append("	 	MMS_SUBJECT						").append("\n");
		sb.append("	 ) VALUES (							").append("\n");
		sb.append("	 	0			,					").append("\n");
		sb.append("	 	?			,					").append("\n");
		sb.append("	 	?								").append("\n");
		sb.append("	 )									").append("\n");
		
		
		return sb.toString();
	}
	
	
	private static final String setInsertMSGSql() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("	 INSERT INTO MSG_DATA (							").append("\n");
		sb.append("	 	CUR_STATE	,								").append("\n");
		sb.append("	 	CALL_TO		, -- 수신번호						").append("\n");
		sb.append("	 	CALL_FROM	, -- 발신번호						").append("\n");
		sb.append("	 	SMS_TXT		,								").append("\n");
		sb.append("	 	MSG_TYPE	,								").append("\n");
		sb.append("	 	CONT_SEQ									").append("\n");
		sb.append("	 ) VALUES (										").append("\n");
		sb.append("	 	0			,								").append("\n");
		sb.append("	 	?			,								").append("\n");
		sb.append("	 	?			,								").append("\n");
		sb.append("	 	?			,								").append("\n");
		sb.append("	 	'6'			,								").append("\n");
		sb.append("		(SELECT IDENT_CURRENT('MMS_CONTENTS_INFO'))	").append("\n");	
		sb.append("	 )												").append("\n");
		
		
		return sb.toString();
	}
	
	private static final void createSMS(){
		Connection conn = null;
    	PreparedStatement pstmt1 = null;
    	PreparedStatement pstmt2 = null;
    	PreparedStatement pstmt3 = null;
    	
    	ResultSet rs = null;
    	
    	try {
    		conn = DBManager.getConnection();
	
    		pstmt1 = conn.prepareStatement(setSelectSql());
    		
    		rs = pstmt1.executeQuery();
    		int i = 0;
    		
			while ( rs.next() ) {
				pstmt2 = conn.prepareStatement(setInsertMMSSql());
				pstmt2.setString(1, rs.getString("CONTENTS"));
				pstmt2.setString(2, rs.getString("TITLE"));
				
				
				pstmt3 = conn.prepareStatement(setInsertMSGSql());
				
				pstmt3.setString(1, rs.getString("TO_CALL").replaceAll("-", ""));	// 수신번호
				pstmt3.setString(2, rs.getString("FROM_CALL").replaceAll("-", ""));	// 발신번호
				
				
				pstmt3.setString(3, rs.getString("TITLE"));
				
				pstmt2.execute();
				pstmt3.execute();
				
				
			}
    	} catch (Exception e) {
    		System.out.println(e.toString());
    	}
	}
	
	public static void main(String[] args) {
		CreateSMS.createSMS();
	}
}
