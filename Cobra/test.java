import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.db.DBConnection;
import com.property.CobraProperty;

public class test {
	
	private String insert(String rsNumber) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("	INSERT INTO TB_SIXT_RESERVATION_TEST (	").append("\n");
		sb.append("		RESRV_NO		,		").append("\n");
		sb.append("		STATION_CD				").append("\n");
		//sb.append("		REQUEST_DT				").append("\n");
		sb.append("	) VALUES ( 					").append("\n");
		sb.append("		'" + rsNumber+ "'			,	").append("\n");
		sb.append("		''						").append("\n");
		//sb.append("		GETDATE()				").append("\n");
		sb.append("	)							").append("\n");
		
		return sb.toString();
	}
	
	private String queryInbound() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("	SELECT 																														").append("\n");
		sb.append("		CONVERT(VARCHAR(19) , B.REQUEST_DETL_DT, 112)									AS REQUEST_DETL_DAY				,		").append("\n");
		sb.append("		REPLACE(SUBSTRING(CONVERT(CHAR(8) , B.REQUEST_DETL_DT, 8), 1, 5), ':', '')		AS REQUEST_DETL_HMIN			,		").append("\n");
		sb.append("		A.RESRV_NO																		AS RESRV_NO						,		").append("\n");
		sb.append("		B.GENER_RESRV_NO																AS GENER_RESRV_NO				,		").append("\n");
		sb.append("		(SELECT USER_NM FROM TB_USER_INFO WHERE USER_ID = B.REQUEST_DETL_ID)			AS REQUEST_DETL_ID				,		").append("\n");
		sb.append("		CASE																													").append("\n");
		sb.append("			WHEN ISNULL(A.STATION_CD, '') = '' THEN '전체'																		").append("\n");
		sb.append("			ELSE (SELECT CMCD_NM FROM TB_CMCD WHERE CMCD_DIV = 'A90' AND CMCD_CD = A.STATION_CD )								").append("\n");
		sb.append("		END																				AS STATION_CD					,		").append("\n");
		sb.append("		'0'																				AS REQUEST_DETL_YN				,		").append("\n");
		sb.append("		CASE																													").append("\n");
		sb.append("			WHEN A.REQUEST_DETL_YN = 'N' THEN '미요청'																			").append("\n");
		sb.append("			ELSE                              '요청완료'																			").append("\n");
		sb.append("		END																				AS REQUEST_DETL_NM				,		").append("\n");
		sb.append("		B.STATUS																		AS STATUS						,		").append("\n");
		sb.append("		B.FIRSTDRIVERNAME1 + ' ' + FIRSTDRIVERNAME2										AS FIRSTDRIVERNAME				,		").append("\n");
		sb.append("		B.RATECODE																		AS RATE							,		").append("\n");
		sb.append("		B.GROUP1																		AS GROUP1						,		").append("\n");
		sb.append("		B.FIRSTDRIVERPHONE																AS PHONE						,		").append("\n");
		sb.append("		B.FIRSTDRIVERMOBILE																AS MOBILE						,		").append("\n");
		sb.append("		B.FLIGHTNO																		AS FLIGHTNO						,		").append("\n");
		sb.append("		B.FIRSTDRIVEREMAIL																AS EMAIL						,		").append("\n");
		sb.append("		B.STATIONREMARK																	AS STATION						,		").append("\n");
		sb.append("		REPLACE(SUBSTRING(PICKUPDATETIME, 1, 10), '-','')								AS PICKUP_DAY					,		").append("\n");
		sb.append("		REPLACE(SUBSTRING(B.PICKUPDATETIME, 12, 5), ':', '')							AS PICKUP_HMIN					,		").append("\n");
		sb.append("		REPLACE(SUBSTRING(RETURNDATETIME, 1, 10), '-','')								AS RETURN_DAY					,		").append("\n");
		sb.append("		REPLACE(SUBSTRING(B.RETURNDATETIME, 12, 5), ':', '')							AS RETURN_HMIN					,		").append("\n");
		sb.append("		B.PICKUPSTATIONID																AS PICKUP_STATIONID				,		").append("\n");
		sb.append("		B.RETURNSTATIONID																AS RETURN_STATIONID				,		").append("\n");
		sb.append("		B.BOOEXTCODE_OW																	AS CODE_OW						,		").append("\n");
		sb.append("		B.BOOEXTNAME_OW																	AS NAME_OW						,		").append("\n");
		sb.append("		B.BOOEXTBOOKED_OW																AS BOOKED_OW					,		").append("\n");
		sb.append("		B.BOOEXTCHARGED_OW																AS CHARGED_OW					,		").append("\n");
		sb.append("		B.BOOEXTPRICECURRENCY_OW														AS BOOEXTPRICECURRENCY_OW		,		").append("\n");
		sb.append("		B.BOOEXTPRICENETAMOUNT_OW														AS BOOEXTPRICENETAMOUNT_OW		,		").append("\n");
		sb.append("		B.BOOEXTPRICEVATPERCENT_OW														AS BOOEXTPRICEVATPERCENT_OW		,		").append("\n");
		sb.append("		B.BOOEXTCODE_O2																	AS CODE_O2						,		").append("\n");
		sb.append("		B.BOOEXTNAME_O2																	AS NAME_O2						,		").append("\n");
		sb.append("		B.BOOEXTBOOKED_O2																AS BOOKED_O2					,		").append("\n");
		sb.append("		B.BOOEXTCHARGED_O2																AS CHARGED_O2					,		").append("\n");
		sb.append("		B.BOOEXTPRICECURRENCY_O2														AS BOOEXTPRICECURRENCY_O2		,		").append("\n");
		sb.append("		B.BOOEXTPRICENETAMOUNT_O2														AS BOOEXTPRICENETAMOUNT_O2		,		").append("\n");
		sb.append("		B.BOOEXTPRICEVATPERCENT_O2														AS BOOEXTPRICEVATPERCENT_O2		,		").append("\n");
		sb.append("		B.BOOEXTCODE_T																	AS CODE_T						,		").append("\n");
		sb.append("		B.BOOEXTNAME_T																	AS NAME_T						,		").append("\n");
		sb.append("		B.BOOEXTBOOKED_T																AS BOOKED_T						,		").append("\n");
		sb.append("		B.BOOEXTCHARGED_T																AS CHARGED_T					,		").append("\n");
		sb.append("		B.BOOEXTPRICECURRENCY_T															AS BOOEXTPRICECURRENCY_T		,		").append("\n");
		sb.append("		B.BOOEXTPRICENETAMOUNT_T														AS BOOEXTPRICENETAMOUNT_T		,		").append("\n");
		sb.append("		B.BOOEXTPRICEVATPERCENT_T														AS BOOEXTPRICEVATPERCENT_T		,		").append("\n");
		sb.append("		B.BOOEXTCODE_NV																	AS CODE_NV						,		").append("\n");
		sb.append("		B.BOOEXTNAME_NV																	AS NAME_NV						,		").append("\n");
		sb.append("		B.BOOEXTBOOKED_NV																AS BOOKED_NV					,		").append("\n");
		sb.append("		B.BOOEXTCHARGED_NV																AS CHARGED_NV					,		").append("\n");
		sb.append("		B.BOOEXTPRICECURRENCY_NV														AS BOOEXTPRICECURRENCY_NV		,		").append("\n");
		sb.append("		B.BOOEXTPRICENETAMOUNT_NV														AS BOOEXTPRICENETAMOUNT_NV		,		").append("\n");
		sb.append("		B.BOOEXTPRICEVATPERCENT_NV														AS BOOEXTPRICEVATPERCENT_NV		,		").append("\n");
		sb.append("		B.BOOEXTCODE_AD																	AS CODE_AD						,		").append("\n");
		sb.append("		B.BOOEXTNAME_AD																	AS NAME_AD						,		").append("\n");
		sb.append("		B.BOOEXTBOOKED_AD																AS BOOKED_AD					,		").append("\n");
		sb.append("		B.BOOEXTCHARGED_AD																AS CHARGED_AD					,		").append("\n");
		sb.append("		B.BOOEXTPRICECURRENCY_AD														AS BOOEXTPRICECURRENCY_AD		,		").append("\n");
		sb.append("		B.BOOEXTPRICENETAMOUNT_AD														AS BOOEXTPRICENETAMOUNT_AD		,		").append("\n");
		sb.append("		B.BOOEXTPRICEVATPERCENT_AD														AS BOOEXTPRICEVATPERCENT_AD		,		").append("\n");
		sb.append("		B.BOOEXTCODE_X																	AS CODE_X						,		").append("\n");
		sb.append("		B.BOOEXTNAME_X																	AS NAME_X						,		").append("\n");
		sb.append("		B.BOOEXTBOOKED_X																AS BOOKED_X						,		").append("\n");
		sb.append("		B.BOOEXTCHARGED_X																AS CHARGED_X					,		").append("\n");
		sb.append("		B.BOOEXTPRICECURRENCY_X															AS BOOEXTPRICECURRENCY_X		,		").append("\n");
		sb.append("		B.BOOEXTPRICENETAMOUNT_X														AS BOOEXTPRICENETAMOUNT_X		,		").append("\n");
		sb.append("		B.BOOEXTPRICEVATPERCENT_X														AS BOOEXTPRICEVATPERCENT_X		,		").append("\n");
		sb.append("		B.BOOEXTCODE_Y																	AS CODE_Y						,		").append("\n");
		sb.append("		B.BOOEXTNAME_Y																	AS NAME_Y						,		").append("\n");
		sb.append("		B.BOOEXTBOOKED_Y																AS BOOKED_Y						,		").append("\n");
		sb.append("		B.BOOEXTCHARGED_Y																AS CHARGED_Y					,		").append("\n");
		sb.append("		B.BOOEXTPRICECURRENCY_Y															AS BOOEXTPRICECURRENCY_Y		,		").append("\n");
		sb.append("		B.BOOEXTPRICENETAMOUNT_Y														AS BOOEXTPRICENETAMOUNT_Y		,		").append("\n");
		sb.append("		B.BOOEXTPRICEVATPERCENT_Y														AS BOOEXTPRICEVATPERCENT_Y		,		").append("\n");
		sb.append("		B.BOOEXTCODE_CS																	AS CODE_CS						,		").append("\n");
		sb.append("		B.BOOEXTNAME_CS																	AS NAME_CS						,		").append("\n");
		sb.append("		B.BOOEXTBOOKED_CS																AS BOOKED_CS					,		").append("\n");
		sb.append("		B.BOOEXTCHARGED_CS																AS CHARGED_CS					,		").append("\n");
		sb.append("		B.BOOEXTPRICECURRENCY_CS														AS BOOEXTPRICECURRENCY_CS		,		").append("\n");
		sb.append("		B.BOOEXTPRICENETAMOUNT_CS														AS BOOEXTPRICENETAMOUNT_CS		,		").append("\n");
		sb.append("		B.BOOEXTPRICEVATPERCENT_CS														AS BOOEXTPRICEVATPERCENT_CS		,		").append("\n");
		sb.append("		B.BOOEXTCODE_PF																	AS CODE_PF						,		").append("\n");
		sb.append("		B.BOOEXTNAME_PF																	AS NAME_PF						,		").append("\n");
		sb.append("		B.BOOEXTBOOKED_PF																AS BOOKED_PF					,		").append("\n");
		sb.append("		B.BOOEXTCHARGED_PF																AS CHARGED_PF					,		").append("\n");
		sb.append("		B.BOOEXTPRICECURRENCY_PF														AS BOOEXTPRICECURRENCY_PF		,		").append("\n");
		sb.append("		B.BOOEXTPRICENETAMOUNT_PF														AS BOOEXTPRICENETAMOUNT_PF		,		").append("\n");
		sb.append("		B.BOOEXTPRICEVATPERCENT_PF														AS BOOEXTPRICEVATPERCENT_PF		,		").append("\n");
		sb.append("		B.BOOEXTCODE_SC																	AS CODE_SC						,		").append("\n");
		sb.append("		B.BOOEXTNAME_SC																	AS NAME_SC						,		").append("\n");
		sb.append("		B.BOOEXTBOOKED_SC																AS BOOKED_SC					,		").append("\n");
		sb.append("		B.BOOEXTCHARGED_SC																AS CHARGED_SC					,		").append("\n");
		sb.append("		B.BOOEXTPRICECURRENCY_SC														AS BOOEXTPRICECURRENCY_SC		,		").append("\n");
		sb.append("		B.BOOEXTPRICENETAMOUNT_SC														AS BOOEXTPRICENETAMOUNT_SC		,		").append("\n");
		sb.append("		B.BOOEXTPRICEVATPERCENT_SC														AS BOOEXTPRICEVATPERCENT_SC		,		").append("\n");
		sb.append("		B.BOOINSCODE																	AS BOOINSCODE					,		").append("\n");
		sb.append("		B.BOOINSNAME																	AS BOOINSNAME					,		").append("\n");
		sb.append("		B.BOOINSBOOKED																	AS BOOINSBOOKED					,		").append("\n");
		sb.append("		B.BOOINSCHARGED																	AS BOOINSCHARGED				,		").append("\n");
		sb.append("		B.BOOINSPRICECURRENCY															AS BOOINSPRICECURRENCY			,		").append("\n");
		sb.append("		B.BOOINSPRICENETAMOUNT															AS BOOINSPRICENETAMOUNT			,		").append("\n");
		sb.append("		B.BOOINSPRICEVATPERCENT															AS BOOINSPRICEVATPERCENT		,		").append("\n");
		sb.append("		B.TOTLNETAMOUNTNODE																AS TOTLNETAMOUNTNODE			,		").append("\n");
		sb.append("		B.TOTLVATAMOUNTNODE																AS TOTLVATAMOUNTNODE			,		").append("\n");
		sb.append("		B.TOTLGROSSAMOUNTNODE															AS TOTLGROSSAMOUNTNODE			,		").append("\n");
		sb.append("		B.INCINSCODE																	AS INCINSCODE					,		").append("\n");
		sb.append("		B.INCINSNAME																	AS INCINSNAME					,		").append("\n");
		sb.append("		B.INCINSBOOKED																	AS INCINSBOOKED					,		").append("\n");
		sb.append("		B.INCINSCHARGED																	AS INCINSCHARGED				,		").append("\n");
		sb.append("		B.INCINSPRICE																	AS INCINSPRICE					,		").append("\n");
		sb.append("		CASE																													").append("\n");
		sb.append("			WHEN B.PREPAID = 'PP'	 THEN 'PP'																					").append("\n");
		sb.append("			WHEN B.PREPAID = 'POA'	 THEN 'POA'																					").append("\n");
		sb.append("			ELSE						  '기타'																					").append("\n");
		sb.append("		END 																			AS PREPAID						,		").append("\n");
		sb.append("		B.ORIGINAME1																	AS ORIGINAME1					,		").append("\n");
		sb.append("		B.ORIGINAGENCYNO																AS ORIGINAGENCYNO				,		").append("\n");
		sb.append("		CONVERT(CHAR(19), B.INS_DT, 20) 												AS INS_DT						,		").append("\n");
		sb.append("		(SELECT USER_NM FROM TB_USER_INFO WHERE USER_ID = B.INS_USER)					AS INS_USER						,		").append("\n");
		sb.append("		A.REQUEST_DT																	AS REQUEST_DT							").append("\n");					
		sb.append("	  FROM TB_SIXT_RESERVATION A LEFT OUTER JOIN TB_SIXT_RESERVATION_DETL B ON A.RESRV_NO = B.RESRV_NO							").append("\n");
		sb.append("	 WHERE 1 = 1																												").append("\n");
		sb.append("	   AND ISNULL(B.STATUS, '') NOT IN ('CNL', 'DEN', 'NS', 'RA')																").append("\n");
		
		
		return sb.toString();
	}
	
	
	public void runTest() throws Exception {
		Connection conn 			= null;
		DBConnection dbConn			= new DBConnection();
		PreparedStatement pstmt 		= null;
		PreparedStatement pstmt2 		= null;
		
		ResultSet rs				= null;
		
		conn 				= dbConn.getDBConnection();
		pstmt 				= conn.prepareStatement(queryInbound());
		
		rs = pstmt.executeQuery();
		
		while (rs.next()) {
			String strTest = rs.getString("RESRV_NO");
			pstmt2 = conn.prepareStatement(insert(strTest));
			
			pstmt2.executeUpdate();
			
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		/*
		if(CobraProperty.getSystemProperty()) {}
		test t = new test();
		t.runTest();
		*/
		
		String str = "12'3456--abcd";
		
		System.out.println(str.replaceAll("'", ""));
		System.out.println(str.replaceAll("'", "").replaceAll("-", ""));
	}
}
