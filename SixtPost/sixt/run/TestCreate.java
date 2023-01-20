package sixt.run;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import sixt.db.DBManager;

public class TestCreate {

	private static final String setSelectSql() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("		SELECT  GENER_RENT_NO, CLNT_NM, CLNT_MOBIL_NO,MEMBER_SEQ FROM TEMPVIEW ORDER BY CLNT_NM, CLNT_MOBIL_NO, MEMBER_SEQ");
		return sb.toString();
		
	}
	
	private static final String setSelectSql2() {
		StringBuffer sb = new StringBuffer();
		
		
		sb.append("	SELECT							").append("\n"); 
		sb.append("		COUNT(1) 					").append("\n");
		sb.append("	  FROM TEMPVIEW 				").append("\n");
		sb.append("	 WHERE CLNT_NM 		= ? 		").append("\n");
		sb.append("	   AND CLNT_MOBIL_NO= ? 		").append("\n");
		sb.append("	   and MEMBER_SEQ 	<> ? 		").append("\n");
		
		return sb.toString();
		
	}
	
	private static final String setSelectSql3() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("	SELECT							").append("\n"); 
		sb.append("		MEMBER_SEQ				").append("\n");
		sb.append("	  FROM TEMPVIEW 				").append("\n");
		sb.append("	 WHERE CLNT_NM 		= ? 		").append("\n");
		sb.append("	   AND CLNT_MOBIL_NO= ? 		").append("\n");
		sb.append("	 ORDER BY MEMBER_SEQ			");
		
		return sb.toString();
		
	}
	
	private static final String setUpdateSql() {
		StringBuffer sb = new StringBuffer();
		
		/*
		sb.append("	UPDATE TEMPVIEW							").append("\n"); 
		sb.append("	   SET NEW_MEMBER_SEQ	= ? 	,		").append("\n");
		sb.append("	       MEMBER_SEQ	= ? 			").append("\n");
		sb.append("	 WHERE GENER_RENT_NO = ?  				").append("\n");
		*/
		
		sb.append("	UPDATE TB_GENER_RENT_CONTRCT							").append("\n"); 
		sb.append("	   SET MEMBER_SEQ	= ? 			").append("\n");
		sb.append("	 WHERE GENER_RENT_NO = ?  				").append("\n");
		
		return sb.toString();
		
	}
	
	
	private static final void createSMS(){
		Connection conn = null;
    	PreparedStatement pstmt1 = null;
    	PreparedStatement pstmt2 = null;
    	PreparedStatement pstmt3 = null;
    	PreparedStatement pstmt4 = null;
    	
    	ResultSet rs = null;
    	ResultSet rs2 = null;
    	ResultSet rs3 = null;
    	
    	String tmpSeq = "";

    	try {
    		conn = DBManager.getConnection();
	
    		pstmt1 = conn.prepareStatement(setSelectSql());
    		
    		rs = pstmt1.executeQuery();
    		int i = 0;
    		
			while ( rs.next() ) {
				//System.out.println(rs.getString(1) + "==" + rs.getString(2) + "==" +rs.getString(3) + "==" +rs.getString(4)  );
				
				

				pstmt2 =  conn.prepareStatement(setSelectSql2());

				pstmt2.setString(1, rs.getString(2));
				pstmt2.setString(2, rs.getString(3));
				pstmt2.setString(3, rs.getString(4));

				rs2 = pstmt2.executeQuery();
				
				if (rs2.next()) {
					i = rs2.getInt(1);
					
					if ( i > 0) {
						pstmt4 = conn.prepareStatement(setSelectSql3());
						pstmt4.setString(1, rs.getString(2));
						pstmt4.setString(2, rs.getString(3));
						rs3 = pstmt4.executeQuery();
						
						if (rs3.next()) {
							tmpSeq = rs3.getString(1);
							System.out.println("tmpSeq:" + tmpSeq);
						}
						pstmt3 = conn.prepareStatement(setUpdateSql());
						pstmt3.setString(1, tmpSeq  );
						pstmt3.setString(2, rs.getString(1)  );
						pstmt3.execute();
						
					}
				}
				
				/*
				pstmt2 = conn.prepareStatement(setInsertMMSSql());
				pstmt2.setString(1, rs.getString("CONTENTS"));
				pstmt2.setString(2, rs.getString("TITLE"));
				
				
				pstmt3 = conn.prepareStatement(setInsertMSGSql());
				
				pstmt3.setString(1, rs.getString("TO_CALL").replaceAll("-", ""));	// 수신번호
				pstmt3.setString(2, rs.getString("FROM_CALL").replaceAll("-", ""));	// 발신번호
				
				
				pstmt3.setString(3, rs.getString("TITLE"));
				
				pstmt2.execute();
				pstmt3.execute();
				*/
				
				
			}
    	} catch (Exception e) {
    		System.out.println(e.toString());
    	}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestCreate.createSMS();
	}

}
