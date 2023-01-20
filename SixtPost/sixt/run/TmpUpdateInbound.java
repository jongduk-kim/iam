package sixt.run;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import sixt.db.DBManager;

public class TmpUpdateInbound {
	
	public static String getInboundList() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("	SELECT																																			").append("\n"); 																								
		sb.append("		GENER_RENT_NO			,																													").append("\n");
		sb.append("		CLNT_RENT_DIV			,																													").append("\n");
		sb.append("		GENER_RESRV_NO			,																													").append("\n");
		sb.append("		BUSN_REG_NO				,																													").append("\n");
		sb.append("		INS_DT																																		").append("\n");																				
		sb.append("	  FROM TB_GENER_RENT_CONTRCT																													").append("\n");
		sb.append("	 WHERE RENT_DAY BETWEEN '20170101' AND '20170427'																								").append("\n");
		sb.append("	   AND ((SUBSTRING(GENER_RESRV_NO, 1, 2 ) = '98' ) OR BUSN_REG_NO IN ('9999908005', '9999908006', '9999924005', '9999924006', '9999913005',		").append("\n");
		sb.append("																		 '9999913006', '9999922005', '9999922006', '9999919005', '9999919006',		").append("\n");
		sb.append("																	     '9999903005', '9999902005', '9999901006', '9999917005', '9999914005',		").append("\n");
		sb.append("																		 '9999914006', '9999912005', '9999912006', '9999906005', '9999906006',		").append("\n");
		sb.append("																		 '9999902006', '9999903006', '9999917006',									").append("\n");
		sb.append("																		 '9999904005', '9999904006', '9999918005', '9999918006', '9999901005',		").append("\n");
		sb.append("																		 '9999901006', '9999924005', '9999924006', '9999921005', '9999921006',		").append("\n");
		sb.append("																		 '9999901005', '9999901006', '9999915005', '9999915006', '9999907005',		").append("\n");
		sb.append("																		 '9999907006', '9999904005', '9999904006', '9999926005', '9999926006'		").append("\n");
		sb.append("	))																																				").append("\n");														
		sb.append("	UNION ALL																																		").append("\n");
		sb.append("	SELECT																																			").append("\n");																																							
		sb.append("		GENER_RENT_NO			,																													").append("\n");								
		sb.append("		CLNT_RENT_DIV			,																													").append("\n");
		sb.append("		''			AS GENER_RESRV_NO			,																									").append("\n");
		sb.append("		BUSN_REG_NO								,																									").append("\n");
		sb.append("		INS_DT																																		").append("\n");		
		sb.append("	  FROM TB_GENER_RENT_CONTRCT_ADD																												").append("\n");					
		sb.append("	 WHERE RENT_DAY BETWEEN '20170101' AND '20170427'																								").append("\n");						
		sb.append("	   AND BUSN_REG_NO IN ('9999908005', '9999908006', '9999924005', '9999924006', '9999913005',													").append("\n");
		sb.append("										'9999913006', '9999922005', '9999922006', '9999919005', '9999919006',										").append("\n");
		sb.append("	'9999903005', '9999902005', '9999901006', '9999917005', '9999914005',																			").append("\n");
		sb.append("	'9999914006', '9999912005', '9999912006', '9999906005', '9999906006',																			").append("\n");
		sb.append("	'9999902006', '9999903006', '9999917006',																										").append("\n");
		sb.append("	'9999904005', '9999904006', '9999918005', '9999918006', '9999901005',																			").append("\n");
		sb.append("	'9999901006', '9999924005', '9999924006', '9999921005', '9999921006',																			").append("\n");
		sb.append("	'9999901005', '9999901006', '9999915005', '9999915006', '9999907005',																			").append("\n");
		sb.append("	'9999907006', '9999904005', '9999904006', '9999926005', '9999926006'																			").append("\n");
		sb.append("	)																																				").append("\n");							
		
		return sb.toString();
	}
	
	private static final String setUpdateSql(String strGubun) {
		StringBuffer sb = new StringBuffer();
		
		
		if ("A".equals(strGubun)) {
			sb.append("	UPDATE TB_GENER_RENT_CONTRCT_ADD	").append("\n");
		} else {
			sb.append("	UPDATE TB_GENER_RENT_CONTRCT		").append("\n");
		}
		
		sb.append("	   SET CLNT_RENT_DIV	= '05' 			").append("\n");
		sb.append("	 WHERE GENER_RENT_NO = ?  				").append("\n");
		
		return sb.toString();
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
    	ResultSet rs = null;
		
		String rsGenerRentNo = "";
		String rsClntRentDiv = "";
		
		try {
			conn = DBManager.getConnection();
			
    		pstmt = conn.prepareStatement(getInboundList());
			
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				rsGenerRentNo = rs.getString("GENER_RENT_NO");
				
				if ("A".equals(rsGenerRentNo.substring(0, 1))) {
					System.out.println("추가:" + rsGenerRentNo);
				} else {
					System.out.println("일반" + rsGenerRentNo);
				}
			
				pstmt2 = conn.prepareStatement(setUpdateSql(rsGenerRentNo.substring(0, 1)));
				pstmt2.setString(1, rsGenerRentNo);
				
				pstmt2.execute();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			try { if (conn != null) conn.close(); } catch (Exception e) {}
		}
	}

}
