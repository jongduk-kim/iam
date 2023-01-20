package com.run;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.db.DBConnection;
import com.property.CobraDataSet;
import com.property.CobraProperty;
import com.property.CobraReservationDetl;
import com.property.CobraValue;
import com.util.DateUtility;

public class CobraRun_Backup implements Job {
	
	private static Logger logger = Logger.getLogger(CobraRun_Backup.class);
	
	private static String DIR_LOG;			// log 파일 디렉토리 정보
	private static String WORK_TIME;		// 작업시간(send, recv 작업 시간)
	private static String JDBC_DRIVER;
	private static String JDBC_URL;
	private static String DB_LOGIN_ID;
	private static String DB_LOGIN_PASSWORD;
	
	private static String curDate;
	
	
	private String setLoaderSqlInbound() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("	MERGE TB_GENER_RENT_RESRV_INBOUND AS T1 											").append("\n");
		sb.append("	USING (SELECT ? AS RESRV_NO ) AS T2 												").append("\n");
		sb.append("    ON T1.RESRV_NO = T2.RESRV_NO														").append("\n");
		sb.append("  WHEN MATCHED THEN 																	").append("\n");
		sb.append("		  UPDATE 																		").append("");
		sb.append("			 SET T1.RESRV_RECEIP_TYP			= ?		,	-- 접수유형 04 : 코브라			").append("\n");
		sb.append("          	 T1.RESRV_BRAN_CD				= ?		,								").append("\n");
		sb.append(" 	         T1.RESRV_RECEIP_USER			= ?		,								").append("\n");
		sb.append(" 	         T1.CLNT_RENT_DIV				= ?		,								").append("\n");
		sb.append(" 	         T1.RESRV_STAT_CD				= ?		,								").append("\n");
		sb.append(" 	         T1.CLNT_NM						= ?		,								").append("\n");
		sb.append(" 	         T1.CLNT_MOBIL_NO				= ?		,								").append("\n");
		sb.append(" 	         T1.CLNT_TEL_NO					= ?		,								").append("\n");
		sb.append(" 	         T1.RENT_BRAN_CD				= ?		,								").append("\n");
		sb.append(" 	         T1.CARRY_BRAN_CD				= ?		,								").append("\n");
		sb.append(" 	         T1.RENT_DAY					= ?		,								").append("\n");
		sb.append(" 	         T1.RENT_HMIN					= ?		,								").append("\n");
		sb.append(" 	         T1.CARRY_DAY					= ?		,								").append("\n");
		sb.append(" 	         T1.CARRY_HMIN					= ?		,								").append("\n");
		sb.append("				 T1.BOO_EXT_CODE_T				= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_NAME_T				= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_BOOKED_T			= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_CHARGED_T			= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_PRICE_NETAMOUNT_T	= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_CODE_OW				= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_NAME_OW				= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_BOOKED_OW			= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_CHARGED_OW			= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_PRICE_NETAMOUNT_OW	= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_CODE_AD				= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_NAME_AD				= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_BOOKED_AD			= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_CHARGED_AD			= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_PRICE_NETAMOUNT_AD	= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_CODE_NV				= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_NAME_NV				= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_BOOKED_NV			= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_CHARGED_NV			= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_PRICE_NETAMOUNT_NV	= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_CODE_CS				= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_NAME_CS				= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_BOOKED_CS			= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_CHARGED_CS			= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_PRICE_NETAMOUNT_CS	= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_CODE_X				= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_NAME_X				= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_BOOKED_X			= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_CHARGED_X			= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_PRICE_NETAMOUNT_X	= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_CODE_O2				= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_NAME_O2				= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_BOOKED_O2			= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_CHARGED_O2			= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_PRICE_NETAMOUNT_O2	= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_CODE_Y				= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_NAME_Y				= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_BOOKED_Y			= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_CHARGED_Y			= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_PRICE_NETAMOUNT_Y	= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_CODE_PF				= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_NAME_PF				= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_BOOKED_PF			= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_CHARGED_PF			= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_PRICE_NETAMOUNT_PF	= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_CODE_SC				= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_NAME_SC				= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_BOOKED_SC			= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_CHARGED_SC			= ?		,								").append("\n");
		sb.append(" 	         T1.BOO_EXT_PRICE_NETAMOUNT_SC	= ?		,								").append("\n");
		sb.append(" 	         T1.RENT_SFCAR_INSUR_KIND		= ?		,								").append("\n");
		sb.append(" 	         T1.RENT_SFCAR_INSUR_AMOUNT		= ?		,								").append("\n");
		sb.append(" 	         T1.TOTL_AMOUNT					= ?		,								").append("\n");
		sb.append(" 	         T1.TOTL_NET_AMOUNT				= ?		,								").append("\n");
		sb.append(" 	         T1.TOTL_VAT_AMOUNT				= ?		,								").append("\n");
		sb.append(" 	         T1.PREPAID						= ?		,								").append("\n");
		sb.append(" 	         T1.UPD_USER					= ?		,								").append("\n");
		sb.append(" 	         T1.UPD_DT						= GETDATE()								").append("\n");
		sb.append("	 WHEN NOT MATCHED THEN  															").append("\n");
		sb.append("		  INSERT (  																	").append("\n");
		sb.append("				 RESRV_NO					,											").append("\n");
		sb.append(" 	         RESRV_RECEIP_TYP			,											").append("\n");
		sb.append(" 	         RESRV_BRAN_CD				,											").append("\n");
		sb.append(" 	         RESRV_RECEIP_USER			,											").append("\n");
		sb.append(" 	         CLNT_RENT_DIV				,											").append("\n");
		sb.append(" 	         RESRV_STAT_CD				,											").append("\n");
		sb.append(" 	         RESRV_DAY					,											").append("\n");
		sb.append(" 	         RESRV_HMIN					,											").append("\n");
		sb.append(" 	         CLNT_NM					,											").append("\n");
		sb.append(" 	         CLNT_DIV					,											").append("\n");
		sb.append(" 	         CLNT_MOBIL_NO				,											").append("\n");
		sb.append(" 	         CLNT_TEL_NO				,											").append("\n");
		sb.append(" 	         RENT_BRAN_CD				,											").append("\n");
		sb.append(" 	         CARRY_BRAN_CD				,											").append("\n");
		sb.append(" 	         RENT_DAY					,											").append("\n");
		sb.append(" 	         RENT_HMIN					,											").append("\n");
		sb.append(" 	         CARRY_DAY					,											").append("\n");
		sb.append(" 	         CARRY_HMIN					,											").append("\n");
		sb.append(" 	         BOO_EXT_CODE_T				,											").append("\n");
		sb.append(" 	         BOO_EXT_NAME_T				,											").append("\n");
		sb.append(" 	         BOO_EXT_BOOKED_T			,											").append("\n");
		sb.append(" 	         BOO_EXT_CHARGED_T			,											").append("\n");
		sb.append(" 	         BOO_EXT_PRICE_NETAMOUNT_T	,											").append("\n");
		sb.append(" 	         BOO_EXT_CODE_OW			,											").append("\n");
		sb.append(" 	         BOO_EXT_NAME_OW			,											").append("\n");
		sb.append(" 	         BOO_EXT_BOOKED_OW			,											").append("\n");
		sb.append(" 	         BOO_EXT_CHARGED_OW			,											").append("\n");
		sb.append(" 	         BOO_EXT_PRICE_NETAMOUNT_OW	,											").append("\n");
		sb.append(" 	         BOO_EXT_CODE_AD			,											").append("\n");
		sb.append(" 	         BOO_EXT_NAME_AD			,											").append("\n");
		sb.append(" 	         BOO_EXT_BOOKED_AD			,											").append("\n");
		sb.append(" 	         BOO_EXT_CHARGED_AD			,											").append("\n");
		sb.append(" 	         BOO_EXT_PRICE_NETAMOUNT_AD	,											").append("\n");
		sb.append(" 	         BOO_EXT_CODE_NV			,											").append("\n");
		sb.append(" 	         BOO_EXT_NAME_NV			,											").append("\n");
		sb.append(" 	         BOO_EXT_BOOKED_NV			,											").append("\n");
		sb.append(" 	         BOO_EXT_CHARGED_NV			,											").append("\n");
		sb.append(" 	         BOO_EXT_PRICE_NETAMOUNT_NV	,											").append("\n");
		sb.append(" 	         BOO_EXT_CODE_CS			,											").append("\n");
		sb.append(" 	         BOO_EXT_NAME_CS			,											").append("\n");
		sb.append(" 	         BOO_EXT_BOOKED_CS			,											").append("\n");
		sb.append(" 	         BOO_EXT_CHARGED_CS			,											").append("\n");
		sb.append(" 	         BOO_EXT_PRICE_NETAMOUNT_CS	,											").append("\n");
		sb.append(" 	         BOO_EXT_CODE_X				,											").append("\n");
		sb.append(" 	         BOO_EXT_NAME_X				,											").append("\n");
		sb.append(" 	         BOO_EXT_BOOKED_X			,											").append("\n");
		sb.append(" 	         BOO_EXT_CHARGED_X			,											").append("\n");
		sb.append(" 	         BOO_EXT_PRICE_NETAMOUNT_X	,											").append("\n");
		sb.append(" 	         BOO_EXT_CODE_O2			,											").append("\n");
		sb.append(" 	         BOO_EXT_NAME_O2			,											").append("\n");
		sb.append(" 	         BOO_EXT_BOOKED_O2			,											").append("\n");
		sb.append(" 	         BOO_EXT_CHARGED_O2			,											").append("\n");
		sb.append(" 	         BOO_EXT_PRICE_NETAMOUNT_O2	,											").append("\n");
		sb.append(" 	         BOO_EXT_CODE_Y				,											").append("\n");
		sb.append(" 	         BOO_EXT_NAME_Y				,											").append("\n");
		sb.append(" 	         BOO_EXT_BOOKED_Y			,											").append("\n");
		sb.append(" 	         BOO_EXT_CHARGED_Y			,											").append("\n");
		sb.append(" 	         BOO_EXT_PRICE_NETAMOUNT_Y	,											").append("\n");
		sb.append(" 	         BOO_EXT_CODE_PF			,											").append("\n");
		sb.append(" 	         BOO_EXT_NAME_PF			,											").append("\n");
		sb.append(" 	         BOO_EXT_BOOKED_PF			,											").append("\n");
		sb.append(" 	         BOO_EXT_CHARGED_PF			,											").append("\n");
		sb.append(" 	         BOO_EXT_PRICE_NETAMOUNT_PF	,											").append("\n");
		sb.append(" 	         BOO_EXT_CODE_SC			,											").append("\n");
		sb.append(" 	         BOO_EXT_NAME_SC			,											").append("\n");
		sb.append(" 	         BOO_EXT_BOOKED_SC			,											").append("\n");
		sb.append(" 	         BOO_EXT_CHARGED_SC			,											").append("\n");
		sb.append(" 	         BOO_EXT_PRICE_NETAMOUNT_SC	,											").append("\n");
		sb.append(" 	         RENT_SFCAR_INSUR_KIND		,											").append("\n");
		sb.append(" 	         RENT_SFCAR_INSUR_AMOUNT	,											").append("\n");
		sb.append(" 	         TOTL_NET_AMOUNT			,											").append("\n");
		sb.append(" 	         TOTL_VAT_AMOUNT			,											").append("\n");
		sb.append(" 	         TOTL_AMOUNT				,											").append("\n");
		sb.append(" 	         PREPAID					,											").append("\n");
		sb.append(" 	         INS_USER					,											").append("\n");
		sb.append(" 	         INS_DT																	").append("\n");
		sb.append("			) VALUES ( 																	").append("\n");
		sb.append("				?		,									").append("\n");
		sb.append(" 			?	,	-- 접수유형 						").append("\n");
		sb.append(" 			?		,									").append("\n");
		sb.append(" 			?		,									").append("\n");
		sb.append(" 			?	,									").append("\n");
		sb.append(" 			?		,									").append("\n");
		sb.append(" 			?		,									").append("\n");
		sb.append(" 			?		,									").append("\n");
		sb.append(" 			? 		, -- name							").append("\n");
		sb.append(" 			?		,									").append("\n");
		sb.append(" 			?		,									").append("\n");
		sb.append(" 			?		,									").append("\n");
		sb.append(" 			?		,									").append("\n");
		sb.append(" 			?		,									").append("\n");
		sb.append(" 			?		,									").append("\n");
		sb.append(" 			?		,									").append("\n");
		sb.append(" 			?		,									").append("\n");
		sb.append(" 			?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		, 									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	  		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		?		,									").append("\n");
		sb.append(" 	   		GETDATE()									").append("\n");
		sb.append("		);													").append("\n");
		
		
		
		sb.append("		MERGE TB_GENER_RENT_RESRV_INBOUND2 AS T1 						").append("\n");
		sb.append("		USING (SELECT ?	AS RESRV_NO ) AS T2 							").append("\n");
		sb.append("        ON T1.GENER_RESRV_NO = T2.RESRV_NO 							").append("\n");
		sb.append("      WHEN MATCHED THEN 												").append("\n");
		sb.append("			  UPDATE 													").append("\n");
		sb.append("				 SET RESRV_RECEIP_TYP 			= ?		,				").append("\n");
		sb.append(" 			 	 RESRV_BRAN_CD				= ? 	,				").append("\n");
		sb.append("					 RESRV_RECEIP_USER    		= ?		,				").append("\n");
		sb.append("					 CLNT_RENT_DIV    			= ?		,				").append("\n");
		sb.append("					 RESRV_STAT_CD    			= ?		,				").append("\n");
		sb.append("					 CLNT_NM    				= ?		,	-- name 	").append("\n");
		sb.append("					 CLNT_DIV    				= ?		,				").append("\n");
		sb.append("					 MEMBR_CLASS    			= ?		,				").append("\n");
		sb.append("					 CLNT_MOBIL_NO    			= ?		,				").append("\n");
		sb.append("					 CLNT_TEL_NO    			= ?		,				").append("\n");
		sb.append("					 RENT_BRAN_CD    			= ?		,				").append("\n");
		sb.append("					 CARRY_BRAN_CD    			= ?		,				").append("\n");
		sb.append("					 RENT_DAY    				= ?		,				").append("\n");
		sb.append("					 RENT_HMIN    				= ?		,				").append("\n");
		sb.append("					 CARRY_DAY    				= ?		,				").append("\n");
		sb.append("					 CARRY_HMIN    				= ?		,				").append("\n");
		sb.append("					 UPD_USER    				= ?		,				").append("\n");
		sb.append("					 UPD_DT    					= GETDATE()				").append("\n");
		sb.append("	  	 WHEN NOT MATCHED THEN  										").append("\n");
		sb.append("		   	INSERT (  													").append("\n");
		sb.append("				GENER_RESRV_NO				,							").append("\n");
		sb.append("				RESRV_RECEIP_TYP			,							").append("\n");
		sb.append("			 	RESRV_BRAN_CD   			,							").append("\n");
		sb.append("			 	RESRV_RECEIP_USER			,							").append("\n");
		sb.append("			 	CLNT_RENT_DIV    			,							").append("\n");
		sb.append("			 	RESRV_STAT_CD    			,							").append("\n");
		sb.append("				CLNT_NM    					,							").append("\n");
		sb.append("				CLNT_DIV    				,							").append("\n");
		sb.append("				MEMBR_CLASS    				,							").append("\n");
		sb.append("				CLNT_MOBIL_NO    			,							").append("\n");
		sb.append("				CLNT_TEL_NO    				,							").append("\n");
		sb.append("				RENT_BRAN_CD    			,							").append("\n");
		sb.append("				CARRY_BRAN_CD    			,							").append("\n");
		sb.append("				RENT_DAY    				,							").append("\n");
		sb.append("				RENT_HMIN    				,							").append("\n");
		sb.append("				CARRY_DAY    				,							").append("\n");
		sb.append("				CARRY_HMIN    				,							").append("\n");
		sb.append("				INS_USER    				,							").append("\n");
		sb.append("				INS_DT    												").append("\n");
		sb.append("		   ) VALUES (													").append("\n");
		sb.append("				?							,							").append("\n");
		sb.append("				?							,							").append("\n");
		sb.append(" 			?							,							").append("\n");
		sb.append("			 	?							,							").append("\n");
		sb.append("				?							,							").append("\n");
		sb.append("				?							,							").append("\n");
		sb.append("				?							,				-- name		").append("\n");
		sb.append("				?							,							").append("\n");
		sb.append("				?							,							").append("\n");
		sb.append("				?							,							").append("\n");
		sb.append("				?							,							").append("\n");
		sb.append("				?							,							").append("\n");
		sb.append("				?							,							").append("\n");
		sb.append("				?							,							").append("\n");
		sb.append("				?							,							").append("\n");
		sb.append("				?							,							").append("\n");
		sb.append("				?							,							").append("\n");
		sb.append("				?							,							").append("\n");
		sb.append("				GETDATE()												").append("\n");
		sb.append("		   );															").append("\n");
		
		
		return sb.toString();
	}
	private String setLoaderSqlMember() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("	INSERT INTO TB_PERSNL_MEMBR_OFFLINE (			").append("\n");
		sb.append(" 	MEMBR_NM				,					").append("\n");
		sb.append("		USE_CHK					,					").append("\n");
		sb.append("		JOIN_DATE				,					").append("\n");
		sb.append("		REMNDR_POINT_SCOR		,					").append("\n");
		sb.append("		INS_DT					,					").append("\n");
		sb.append("		INS_USER									").append("\n");
		sb.append("	) VALUES ( 										").append("\n");
		sb.append("		?										,	").append("\n");
		sb.append("		'Y'										,	").append("\n");
		sb.append("		CONVERT(VARCHAR(8), GETDATE(), 112)		,	").append("\n");
		sb.append("		0										,	").append("\n");
		sb.append("		GETDATE()								,	").append("\n");
		sb.append("		'00000000'									").append("\n");
		sb.append("	)												").append("\n");
		
		return sb.toString();
	}
	
	private String setLoaderSqlRequest() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("	INSERT INTO TB_SIXT_RESERVATION_DETL_REQUEST (  				").append("\n");
		sb.append("		RESRV_NO					,								").append("\n");
		sb.append("		RESRV_SEQ					,								").append("\n");
		sb.append("		GENER_RESRV_NO				,								").append("\n");
		sb.append("		STATUS						,								").append("\n");
		sb.append("		GROUP1						,								").append("\n");
		sb.append("		DURATION					,								").append("\n");
		sb.append("		STATIONREMARK				,								").append("\n");
		sb.append("		FLIGHTNO					,								").append("\n");
		sb.append("		BONUSPROGRAMNO				,								").append("\n");
		sb.append("		ORIGINAGENCYNO				,								").append("\n");
		sb.append("		ORIGINAME1					,								").append("\n");
		sb.append("		RATECODE					,								").append("\n");
		sb.append("		PICKUPDATETIME				,								").append("\n");
		sb.append("		PICKUPSTATIONID				,								").append("\n");
		sb.append("		PICKUPNAME					,								").append("\n");
		sb.append("		RETURNDATETIME				,								").append("\n");
		sb.append("		RETURNSTATIONID				,								").append("\n");
		sb.append("		RETURNNAME					,								").append("\n");
		sb.append("		FIRSTDRIVERPHONE			,								").append("\n");
		sb.append("		FIRSTDRIVERMOBILE			,								").append("\n");
		sb.append("		FIRSTDRIVEREMAIL			,								").append("\n");
		sb.append("		FIRSTDRIVERNAME1			,								").append("\n");
		sb.append("		FIRSTDRIVERNAME2			,								").append("\n");
		sb.append("		FIRSTDRIVERCOUNTRY			,								").append("\n");
		sb.append("		FORBIINSCODE				,								").append("\n");
		sb.append("		FORBIINSNAME				,								").append("\n");
		sb.append("		FORBIINSBOOKED				,								").append("\n");
		sb.append("		FORBIINSCHARGED				,								").append("\n");
		sb.append("		FORBIINSPRICE				,								").append("\n");
		sb.append("		FORBIEXTCODE				,								").append("\n");
		sb.append("		FORBIEXTNAME				,								").append("\n");
		sb.append("		FORBIEXTBOOKED				,								").append("\n");
		sb.append("		FORBIEXTCHARGED				,								").append("\n");
		sb.append("		FORBIEXTPRICE				,								").append("\n");
		sb.append("		INCINSCODE					,								").append("\n");
		sb.append("		INCINSNAME					,								").append("\n");
		sb.append("		INCINSBOOKED				,								").append("\n");
		sb.append("		INCINSCHARGED				,								").append("\n");
		sb.append("		INCINSPRICE					,								").append("\n");
		sb.append("		INCEXTCODE					,								").append("\n");
		sb.append("		INCEXTNAME					,								").append("\n");
		sb.append("		INCEXTBOOKED				,								").append("\n");
		sb.append("		INCEXTCHARGED				,								").append("\n");
		sb.append("		INCEXTPRICE					,								").append("\n");
		sb.append("		BOOEXTCODE_T				,								").append("\n");
		sb.append("		BOOEXTNAME_T				,								").append("\n");
		sb.append("		BOOEXTBOOKED_T				,								").append("\n");
		sb.append("		BOOEXTCHARGED_T				,								").append("\n");
		sb.append("		BOOEXTPRICECURRENCY_T		,								").append("\n");
		sb.append("		BOOEXTPRICENETAMOUNT_T		,								").append("\n");
		sb.append("		BOOEXTPRICEVATPERCENT_T		,								").append("\n");
		sb.append("		BOOEXTCODE_OW				,								").append("\n");
		sb.append("		BOOEXTNAME_OW				,								").append("\n");
		sb.append("		BOOEXTBOOKED_OW				,								").append("\n");
		sb.append("		BOOEXTCHARGED_OW			,								").append("\n");
		sb.append("		BOOEXTPRICECURRENCY_OW		,								").append("\n");
		sb.append("		BOOEXTPRICENETAMOUNT_OW		,								").append("\n");
		sb.append("		BOOEXTPRICEVATPERCENT_OW	,								").append("\n");
		sb.append("		BOOEXTCODE_AD				,								").append("\n");
		sb.append("		BOOEXTNAME_AD				,								").append("\n");
		sb.append("		BOOEXTBOOKED_AD				,								").append("\n");
		sb.append("		BOOEXTCHARGED_AD			,								").append("\n");
		sb.append("		BOOEXTPRICECURRENCY_AD		,								").append("\n");
		sb.append("		BOOEXTPRICENETAMOUNT_AD		,								").append("\n");
		sb.append("		BOOEXTPRICEVATPERCENT_AD	,								").append("\n");
		sb.append("		BOOEXTCODE_NV				,								").append("\n");
		sb.append("		BOOEXTNAME_NV				,								").append("\n");
		sb.append("		BOOEXTBOOKED_NV				,								").append("\n");
		sb.append("		BOOEXTCHARGED_NV			,								").append("\n");
		sb.append("		BOOEXTPRICECURRENCY_NV		,								").append("\n");
		sb.append("		BOOEXTPRICENETAMOUNT_NV		,								").append("\n");
		sb.append("		BOOEXTPRICEVATPERCENT_NV	,								").append("\n");
		sb.append("		BOOEXTCODE_CS				,								").append("\n");
		sb.append("		BOOEXTNAME_CS				,								").append("\n");
		sb.append("		BOOEXTBOOKED_CS				,								").append("\n");
		sb.append("		BOOEXTCHARGED_CS			,								").append("\n");
		sb.append("		BOOEXTPRICECURRENCY_CS		,								").append("\n");
		sb.append("		BOOEXTPRICENETAMOUNT_CS		,								").append("\n");
		sb.append("		BOOEXTPRICEVATPERCENT_CS	,								").append("\n");
		sb.append("		BOOEXTCODE_X				,								").append("\n");
		sb.append("		BOOEXTNAME_X				,								").append("\n");
		sb.append("		BOOEXTBOOKED_X				,								").append("\n");
		sb.append("		BOOEXTCHARGED_X				,								").append("\n");
		sb.append("		BOOEXTPRICECURRENCY_X		,								").append("\n");
		sb.append("		BOOEXTPRICENETAMOUNT_X		,								").append("\n");
		sb.append("		BOOEXTPRICEVATPERCENT_X		,								").append("\n");
		sb.append("		BOOEXTCODE_O2				,								").append("\n");
		sb.append("		BOOEXTNAME_O2				,								").append("\n");
		sb.append("		BOOEXTBOOKED_O2				,								").append("\n");
		sb.append("		BOOEXTCHARGED_O2			,								").append("\n");
		sb.append("		BOOEXTPRICECURRENCY_O2		,								").append("\n");
		sb.append("		BOOEXTPRICENETAMOUNT_O2		,								").append("\n");
		sb.append("		BOOEXTPRICEVATPERCENT_O2	,								").append("\n");
		sb.append("		BOOEXTCODE_Y				,								").append("\n");
		sb.append("		BOOEXTNAME_Y				,								").append("\n");
		sb.append("		BOOEXTBOOKED_Y				,								").append("\n");
		sb.append("		BOOEXTCHARGED_Y				,								").append("\n");
		sb.append("		BOOEXTPRICECURRENCY_Y		,								").append("\n");
		sb.append("		BOOEXTPRICENETAMOUNT_Y		,								").append("\n");
		sb.append("		BOOEXTPRICEVATPERCENT_Y		,								").append("\n");
		sb.append("		BOOEXTCODE_PF				,								").append("\n");
		sb.append("		BOOEXTNAME_PF				,								").append("\n");
		sb.append("		BOOEXTBOOKED_PF				,								").append("\n");
		sb.append("		BOOEXTCHARGED_PF			,								").append("\n");
		sb.append("		BOOEXTPRICECURRENCY_PF		,								").append("\n");
		sb.append("		BOOEXTPRICENETAMOUNT_PF		,								").append("\n");
		sb.append("		BOOEXTPRICEVATPERCENT_PF	,								").append("\n");
		sb.append("		BOOEXTCODE_SC				,								").append("\n");
		sb.append("		BOOEXTNAME_SC				,								").append("\n");
		sb.append("		BOOEXTBOOKED_SC				,								").append("\n");
		sb.append("		BOOEXTCHARGED_SC			,								").append("\n");
		sb.append("		BOOEXTPRICECURRENCY_SC		,								").append("\n");
		sb.append("		BOOEXTPRICENETAMOUNT_SC		,								").append("\n");
		sb.append("		BOOEXTPRICEVATPERCENT_SC	,								").append("\n");
		sb.append("		BOOINSCODE					,								").append("\n");
		sb.append("		BOOINSNAME					,								").append("\n");
		sb.append("		BOOINSBOOKED				,								").append("\n");
		sb.append("		BOOINSCHARGED				,								").append("\n");
		sb.append("		BOOINSPRICECURRENCY			,								").append("\n");
		sb.append("		BOOINSPRICENETAMOUNT		,								").append("\n");
		sb.append("		BOOINSPRICEVATPERCENT		,								").append("\n");
		sb.append("		PAYMENTTYPE					,								").append("\n");
		sb.append("		TOTLDUEAMOUNTNODE			,								").append("\n");
		sb.append("		TOTLGROSSAMOUNTNODE			,								").append("\n");
		sb.append("		TOTLVATAMOUNTNODE			,								").append("\n");
		sb.append("		TOTLCURRENCYNODE			,								").append("\n");
		sb.append("		TOTLNETAMOUNTNODE			,								").append("\n");
		sb.append("		TOTLVATPERCENTNODE			,								").append("\n");
		sb.append("		REQUEST_DETL_DT				,								").append("\n");
		sb.append("		REQUEST_DETL_ID				,								").append("\n");
		sb.append("		PREPAID						,								").append("\n");
		sb.append("		INS_DT						,								").append("\n");
		sb.append("		INS_USER													").append("\n");
		sb.append("	) VALUES (														").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		(SELECT ISNULL(MAX(RESRV_SEQ), 0) + 1						").append("\n");
		sb.append("        FROM TB_SIXT_RESERVATION_DETL_REQUEST					").append("\n");
		sb.append("       WHERE RESRV_NO = ? )	,	").append("\n");
		sb.append("		?		,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,		").append("\n");
		
		
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		GETDATE()									,				").append("\n");
		sb.append("		'00000000'									,				").append("\n");
		sb.append("		?	,				").append("\n");
		sb.append("		GETDATE()									,				").append("\n");
		sb.append("		'00000000'													").append("\n");
		sb.append(" )																").append("\n");
		
		return sb.toString();
	}
	
	private String setLoaderSql() {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("MERGE TB_SIXT_RESERVATION_DETL AS T1									").append("\n");
		sb.append("	USING (SELECT ? AS RESRV_NO ) AS T2									").append("\n");
		sb.append("	   ON T1.RESRV_NO = T2.RESRV_NO										").append("\n");
		sb.append("	  WHEN MATCHED THEN													").append("\n");
		sb.append("		   UPDATE														").append("\n");
		sb.append("			  SET T1.GENER_RESRV_NO				= 	?	,				").append("\n");
		sb.append("			      T1.STATUS 					= 	?	,				").append("\n");
		sb.append("			      T1.GROUP1 					= 	?	,				").append("\n");
		sb.append("			      T1.DURATION 					=	?	,				").append("\n");
		sb.append("			      T1.STATIONREMARK 				=	?	,				").append("\n");
		sb.append("			      T1.FLIGHTNO 					= 	?	,				").append("\n");
		sb.append("			      T1.BONUSPROGRAMNO 			= 	?	,				").append("\n");
		sb.append("			      T1.ORIGINAGENCYNO 			= 	?	,				").append("\n");
		sb.append("			      T1.ORIGINAME1 				= 	?	,				").append("\n");
		sb.append("			      T1.RATECODE 					= 	?	,				").append("\n");
		sb.append("			      T1.PICKUPDATETIME 			= 	?	,				").append("\n");
		sb.append("			      T1.PICKUPSTATIONID 			= 	?	,				").append("\n");
		sb.append("			      T1.PICKUPNAME 				= 	?	,				").append("\n");
		sb.append("			      T1.RETURNDATETIME 			= 	?	,				").append("\n");
		sb.append("			      T1.RETURNSTATIONID 			= 	?	,				").append("\n");
		sb.append("			      T1.RETURNNAME 				= 	?	,				").append("\n");
		sb.append("			      T1.FIRSTDRIVERPHONE 			= 	?	,				").append("\n");
		sb.append("			      T1.FIRSTDRIVERMOBILE 			= 	?	,				").append("\n");
		sb.append("			      T1.FIRSTDRIVEREMAIL 			= 	?	,				").append("\n");
		sb.append("			      T1.FIRSTDRIVERNAME1 			= 	?	,				").append("\n");
		sb.append("			      T1.FIRSTDRIVERNAME2 			= 	?	,				").append("\n");
		sb.append("			      T1.FIRSTDRIVERCOUNTRY		 	=	 ?	,				").append("\n");
		sb.append("			      T1.FORBIINSCODE 				= 	?	,				").append("\n");
		sb.append("			      T1.FORBIINSNAME 				= 	?	,				").append("\n");
		sb.append("			      T1.FORBIINSBOOKED 			= 	?	,				").append("\n");
		sb.append("			      T1.FORBIINSCHARGED 			= 	?	,				").append("\n");
		sb.append("			      T1.FORBIINSPRICE 				= 	?	,				").append("\n");
		sb.append("			      T1.FORBIEXTCODE 				= 	?	,				").append("\n");
		sb.append("			      T1.FORBIEXTNAME 				= 	?	,				").append("\n");
		sb.append("			      T1.FORBIEXTBOOKED 			= 	?	,				").append("\n");
		sb.append("			      T1.FORBIEXTCHARGED 			= 	?	,				").append("\n");
		sb.append("			      T1.FORBIEXTPRICE 				= 	?	,				").append("\n");
		sb.append("			      T1.INCINSCODE 				= 	?	,				").append("\n");
		sb.append("			      T1.INCINSNAME 				= 	?	,				").append("\n");
		sb.append("			      T1.INCINSBOOKED 				= 	?	,				").append("\n");
		sb.append("			      T1.INCINSCHARGED 				= 	?	,				").append("\n");
		sb.append("			      T1.INCINSPRICE 				= 	?	,				").append("\n");
		sb.append("			      T1.INCEXTCODE 				= 	?	,				").append("\n");
		sb.append("			      T1.INCEXTNAME 				= 	?	,				").append("\n");
		sb.append("			      T1.INCEXTBOOKED 				= 	?	,				").append("\n");
		sb.append("			      T1.INCEXTCHARGED 				= 	?	,				").append("\n");
		sb.append("			      T1.INCEXTPRICE 				= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTCODE_T 				= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTNAME_T 				= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTBOOKED_T 			= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTCHARGED_T 			= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTPRICECURRENCY_T 		= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTPRICENETAMOUNT_T 	= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTPRICEVATPERCENT_T 	= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTCODE_OW 				= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTNAME_OW 				= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTBOOKED_OW 			= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTCHARGED_OW 			= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTPRICECURRENCY_OW 	= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTPRICENETAMOUNT_OW 	= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTPRICEVATPERCENT_OW 	= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTCODE_AD 				= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTNAME_AD 				= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTBOOKED_AD 			= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTCHARGED_AD 			=	?	,				").append("\n");
		sb.append("			      T1.BOOEXTPRICECURRENCY_AD 	=	?	,				").append("\n");
		sb.append("			      T1.BOOEXTPRICENETAMOUNT_AD 	= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTPRICEVATPERCENT_AD 	= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTCODE_NV 				= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTNAME_NV 				= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTBOOKED_NV 			= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTCHARGED_NV 			= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTPRICECURRENCY_NV 	= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTPRICENETAMOUNT_NV 	= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTPRICEVATPERCENT_NV 	=	 ?	,				").append("\n");
		sb.append("			      T1.BOOEXTCODE_CS 				= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTNAME_CS 				= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTBOOKED_CS 			= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTCHARGED_CS 			= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTPRICECURRENCY_CS 	= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTPRICENETAMOUNT_CS 	= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTPRICEVATPERCENT_CS 	= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTCODE_X 				= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTNAME_X 				= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTBOOKED_X 			= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTCHARGED_X 			= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTPRICECURRENCY_X 		= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTPRICENETAMOUNT_X 	= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTPRICEVATPERCENT_X 	= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTCODE_O2 				= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTNAME_O2 				= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTBOOKED_O2 			= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTCHARGED_O2 			= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTPRICECURRENCY_O2 	= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTPRICENETAMOUNT_O2 	= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTPRICEVATPERCENT_O2 	= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTCODE_Y 				= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTNAME_Y 				=	?	,				").append("\n");
		sb.append("			      T1.BOOEXTBOOKED_Y 			= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTCHARGED_Y 			= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTPRICECURRENCY_Y 		= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTPRICENETAMOUNT_Y 	= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTPRICEVATPERCENT_Y 	= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTCODE_PF 				= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTNAME_PF 				= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTBOOKED_PF 			= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTCHARGED_PF 			= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTPRICECURRENCY_PF 	=	?	,				").append("\n");
		sb.append("			      T1.BOOEXTPRICENETAMOUNT_PF 	= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTPRICEVATPERCENT_PF 	= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTCODE_SC 				= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTNAME_SC 				= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTBOOKED_SC 			= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTCHARGED_SC 			= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTPRICECURRENCY_SC 	= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTPRICENETAMOUNT_SC 	= 	?	,				").append("\n");
		sb.append("			      T1.BOOEXTPRICEVATPERCENT_SC 	= 	?	,				").append("\n");
		sb.append("			      T1.BOOINSCODE 				= 	?	,				").append("\n");
		sb.append("			      T1.BOOINSNAME 				= 	?	,				").append("\n");
		sb.append("			      T1.BOOINSBOOKED 				= 	?	,				").append("\n");
		sb.append("			      T1.BOOINSCHARGED 				= 	?	,				").append("\n");
		sb.append("			      T1.BOOINSPRICECURRENCY	 	= 	?	,				").append("\n");
		sb.append("			      T1.BOOINSPRICENETAMOUNT 		= 	?	,				").append("\n");
		sb.append("			      T1.BOOINSPRICEVATPERCENT 		= 	?	,				").append("\n");
		sb.append("			      T1.PAYMENTTYPE 				= 	?	,				").append("\n");
		sb.append("				  T1.TOTLDUEAMOUNTNODE			= 	?	,				").append("\n");
		sb.append("				  T1.TOTLGROSSAMOUNTNODE		= 	?	,				").append("\n");
		sb.append("				  T1.TOTLVATAMOUNTNODE			= 	?	,				").append("\n");
		sb.append("				  T1.TOTLCURRENCYNODE			= 	?	,				").append("\n");
		sb.append("				  T1.TOTLNETAMOUNTNODE			= 	?	,				").append("\n");
		sb.append("				  T1.TOTLVATPERCENTNODE			= 	?	,				").append("\n");
		sb.append("				  T1.PREPAID					= 	?	,				").append("\n");
		sb.append("				  T1.REQUEST_DETL_DT			= GETDATE()	,			").append("\n");
		sb.append("				  T1.REQUEST_DETL_ID			= '00000000',			").append("\n");
		sb.append("			      T1.UPD_DT 					= GETDATE()	,			").append("\n");
		sb.append("			      T1.UPD_USER 					= '00000000'																	").append("\n");
		sb.append("	  WHEN NOT MATCHED THEN  																						").append("\n");
		sb.append("		   INSERT (  																								").append("\n");
		sb.append("				RESRV_NO					,																		").append("\n");
		sb.append("				GENER_RESRV_NO				,																		").append("\n");
		sb.append("				STATUS						,																		").append("\n");
		sb.append("				GROUP1						,																		").append("\n");
		sb.append("				DURATION					,																		").append("\n");
		sb.append("				STATIONREMARK				,																		").append("\n");
		sb.append("				FLIGHTNO					,																		").append("\n");
		sb.append("				BONUSPROGRAMNO				,																		").append("\n");
		sb.append("				ORIGINAGENCYNO				,																		").append("\n");
		sb.append("				ORIGINAME1					,																		").append("\n");
		sb.append("				RATECODE					,																		").append("\n");
		sb.append("				PICKUPDATETIME				,																		").append("\n");
		sb.append("				PICKUPSTATIONID				,																		").append("\n");
		sb.append("				PICKUPNAME					,																		").append("\n");
		sb.append("				RETURNDATETIME				,																		").append("\n");
		sb.append("				RETURNSTATIONID				,																		").append("\n");
		sb.append("				RETURNNAME					,																		").append("\n");
		sb.append("				FIRSTDRIVERPHONE			,																		").append("\n");
		sb.append("				FIRSTDRIVERMOBILE			,																		").append("\n");
		sb.append("				FIRSTDRIVEREMAIL			,																		").append("\n");
		sb.append("				FIRSTDRIVERNAME1			,																		").append("\n");
		sb.append("				FIRSTDRIVERNAME2			,																		").append("\n");
		sb.append("				FIRSTDRIVERCOUNTRY			,																		").append("\n");
		sb.append("				FORBIINSCODE				,																		").append("\n");
		sb.append("				FORBIINSNAME				,																		").append("\n");
		sb.append("				FORBIINSBOOKED				,																		").append("\n");
		sb.append("				FORBIINSCHARGED				,																		").append("\n");
		sb.append("				FORBIINSPRICE				,																		").append("\n");
		sb.append("				FORBIEXTCODE				,																		").append("\n");
		sb.append("				FORBIEXTNAME				,																		").append("\n");
		sb.append("				FORBIEXTBOOKED				,																		").append("\n");
		sb.append("				FORBIEXTCHARGED				,																		").append("\n");
		sb.append("				FORBIEXTPRICE				,																		").append("\n");
		sb.append("				INCINSCODE					,																		").append("\n");
		sb.append("				INCINSNAME					,																		").append("\n");
		sb.append("				INCINSBOOKED				,																		").append("\n");
		sb.append("				INCINSCHARGED				,																		").append("\n");
		sb.append("				INCINSPRICE					,																		").append("\n");
		sb.append("				INCEXTCODE					,																		").append("\n");
		sb.append("				INCEXTNAME					,																		").append("\n");
		sb.append("				INCEXTBOOKED				,																		").append("\n");
		sb.append("				INCEXTCHARGED				,																		").append("\n");
		sb.append("				INCEXTPRICE					,																		").append("\n");
		sb.append("				BOOEXTCODE_T				,																		").append("\n");
		sb.append("				BOOEXTNAME_T				,																		").append("\n");
		sb.append("				BOOEXTBOOKED_T				,																		").append("\n");
		sb.append("				BOOEXTCHARGED_T				,																		").append("\n");
		sb.append("				BOOEXTPRICECURRENCY_T		,																		").append("\n");
		sb.append("				BOOEXTPRICENETAMOUNT_T		,																		").append("\n");
		sb.append("				BOOEXTPRICEVATPERCENT_T		,																		").append("\n");
		sb.append("				BOOEXTCODE_OW				,																		").append("\n");
		sb.append("				BOOEXTNAME_OW				,																		").append("\n");
		sb.append("				BOOEXTBOOKED_OW				,																		").append("\n");
		sb.append("				BOOEXTCHARGED_OW			,																		").append("\n");
		sb.append("				BOOEXTPRICECURRENCY_OW		,																		").append("\n");
		sb.append("				BOOEXTPRICENETAMOUNT_OW		,																		").append("\n");
		sb.append("				BOOEXTPRICEVATPERCENT_OW	,																		").append("\n");
		sb.append("				BOOEXTCODE_AD				,																		").append("\n");
		sb.append("				BOOEXTNAME_AD				,																		").append("\n");
		sb.append("				BOOEXTBOOKED_AD				,																		").append("\n");
		sb.append("				BOOEXTCHARGED_AD			,																		").append("\n");
		sb.append("				BOOEXTPRICECURRENCY_AD		,																		").append("\n");
		sb.append("				BOOEXTPRICENETAMOUNT_AD		,																		").append("\n");
		sb.append("				BOOEXTPRICEVATPERCENT_AD	,																		").append("\n");
		sb.append("				BOOEXTCODE_NV				,																		").append("\n");
		sb.append("				BOOEXTNAME_NV				,																		").append("\n");
		sb.append("				BOOEXTBOOKED_NV				,																		").append("\n");
		sb.append("				BOOEXTCHARGED_NV			,																		").append("\n");
		sb.append("				BOOEXTPRICECURRENCY_NV		,																		").append("\n");
		sb.append("				BOOEXTPRICENETAMOUNT_NV		,																		").append("\n");
		sb.append("				BOOEXTPRICEVATPERCENT_NV	,																		").append("\n");
		sb.append("				BOOEXTCODE_CS				,																		").append("\n");
		sb.append("				BOOEXTNAME_CS				,																		").append("\n");
		sb.append("				BOOEXTBOOKED_CS				,																		").append("\n");
		sb.append("				BOOEXTCHARGED_CS			,																		").append("\n");
		sb.append("				BOOEXTPRICECURRENCY_CS		,																		").append("\n");
		sb.append("				BOOEXTPRICENETAMOUNT_CS		,																		").append("\n");
		sb.append("				BOOEXTPRICEVATPERCENT_CS	,																		").append("\n");
		sb.append("				BOOEXTCODE_X				,																		").append("\n");
		sb.append("				BOOEXTNAME_X				,																		").append("\n");
		sb.append("				BOOEXTBOOKED_X				,																		").append("\n");
		sb.append("				BOOEXTCHARGED_X				,																		").append("\n");
		sb.append("				BOOEXTPRICECURRENCY_X		,																		").append("\n");
		sb.append("				BOOEXTPRICENETAMOUNT_X		,																		").append("\n");
		sb.append("				BOOEXTPRICEVATPERCENT_X		,																		").append("\n");
		sb.append("				BOOEXTCODE_O2				,																		").append("\n");
		sb.append("				BOOEXTNAME_O2				,																		").append("\n");
		sb.append("				BOOEXTBOOKED_O2				,																		").append("\n");
		sb.append("				BOOEXTCHARGED_O2			,																		").append("\n");
		sb.append("				BOOEXTPRICECURRENCY_O2		,																		").append("\n");
		sb.append("				BOOEXTPRICENETAMOUNT_O2		,																		").append("\n");
		sb.append("				BOOEXTPRICEVATPERCENT_O2	,																		").append("\n");
		sb.append("				BOOEXTCODE_Y				,																		").append("\n");
		sb.append("				BOOEXTNAME_Y				,																		").append("\n");
		sb.append("				BOOEXTBOOKED_Y				,																		").append("\n");
		sb.append("				BOOEXTCHARGED_Y				,																		").append("\n");
		sb.append("				BOOEXTPRICECURRENCY_Y		,																		").append("\n");
		sb.append("				BOOEXTPRICENETAMOUNT_Y		,																		").append("\n");
		sb.append("				BOOEXTPRICEVATPERCENT_Y		,																		").append("\n");
		sb.append("				BOOEXTCODE_PF				,																		").append("\n");
		sb.append("				BOOEXTNAME_PF				,																		").append("\n");
		sb.append("				BOOEXTBOOKED_PF				,																		").append("\n");
		sb.append("				BOOEXTCHARGED_PF			,																		").append("\n");
		sb.append("				BOOEXTPRICECURRENCY_PF		,																		").append("\n");
		sb.append("				BOOEXTPRICENETAMOUNT_PF		,																		").append("\n");
		sb.append("				BOOEXTPRICEVATPERCENT_PF	,																		").append("\n");
		sb.append("				BOOEXTCODE_SC				,																		").append("\n");
		sb.append("				BOOEXTNAME_SC				,																		").append("\n");
		sb.append("				BOOEXTBOOKED_SC				,																		").append("\n");
		sb.append("				BOOEXTCHARGED_SC			,																		").append("\n");
		sb.append("				BOOEXTPRICECURRENCY_SC		,																		").append("\n");
		sb.append("				BOOEXTPRICENETAMOUNT_SC		,																		").append("\n");
		sb.append("				BOOEXTPRICEVATPERCENT_SC	,																		").append("\n");
		sb.append("				BOOINSCODE					,																		").append("\n");
		sb.append("				BOOINSNAME					,																		").append("\n");
		sb.append("				BOOINSBOOKED				,																		").append("\n");
		sb.append("				BOOINSCHARGED				,																		").append("\n");
		sb.append("				BOOINSPRICECURRENCY			,																		").append("\n");
		sb.append("				BOOINSPRICENETAMOUNT		,																		").append("\n");
		sb.append("				BOOINSPRICEVATPERCENT		,																		").append("\n");
		sb.append("				PAYMENTTYPE					,																		").append("\n");
		sb.append("				TOTLDUEAMOUNTNODE			,																		").append("\n");
		sb.append("				TOTLGROSSAMOUNTNODE			,																		").append("\n");
		sb.append("				TOTLVATAMOUNTNODE			,																		").append("\n");
		sb.append("				TOTLCURRENCYNODE			,																		").append("\n");
		sb.append("				TOTLNETAMOUNTNODE			,																		").append("\n");
		sb.append("				TOTLVATPERCENTNODE			,																		").append("\n");
		sb.append("				PREPAID						,																		").append("\n");
		sb.append("				REQUEST_DETL_DT				,																		").append("\n");
		sb.append("				REQUEST_DETL_ID				,																		").append("\n");
		sb.append("				INS_DT						,																		").append("\n");
		sb.append("				INS_USER																							").append("\n");
		sb.append("		   ) VALUES (																								").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,													").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,														").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			?	,															").append("\n");
		sb.append("			GETDATE()									,															").append("\n");
		sb.append("			'00000000'									,															").append("\n");
		sb.append("			GETDATE()									,															").append("\n");
		sb.append("			'00000000'																								").append("\n");
		sb.append("		   );																										").append("\n");
		
		sb.append("	UPDATE TB_SIXT_RESERVATION																						").append("\n");
		sb.append("    SET REQUEST_DETL_YN 	= 'Y'		,																			").append("\n");
		sb.append("        STATION_CD	   	= ?													").append("\n");
		sb.append("  WHERE RESRV_NO 		= ?													").append("\n");
		
		return sb.toString();
	}
	
	private String queryInbound() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("	SELECT	TOP 20																													").append("\n");
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
	
	private String InboundInsert() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("MERGE TB_SIXT_RESERVATION AS T1							").append("\n");
		sb.append("	USING (SELECT ? 				AS RESRV_NO ) AS T2		").append("\n");
		sb.append("	   ON T1.RESRV_NO = T2.RESRV_NO							").append("\n");
		sb.append("	 WHEN MATCHED THEN										").append("\n");
		sb.append("	   	  UPDATE											").append("\n");
		sb.append("			 SET T1.REQUEST_DT		= GETDATE()		,		").append("\n");
		sb.append("			     T1.REQUEST_USER 	= '00000000'	,		").append("\n");
		sb.append("			     T1.UPD_DT 			= GETDATE()		,		").append("\n");
		sb.append("			     T1.UPD_USER 		= '00000000'			").append("\n");
		sb.append("	 WHEN NOT MATCHED THEN  								").append("\n");
		sb.append("		  INSERT (  										").append("\n");
		sb.append("		  		RESRV_NO			,						").append("\n");
		sb.append("				STATION_CD			,						").append("\n");
		sb.append("				REQUEST_DT			,						").append("\n");
		sb.append("				REQUEST_USER		,						").append("\n");
		sb.append("				REQUEST_DETL_YN		,						").append("\n");
		sb.append("				INS_DT				,						").append("\n");
		sb.append("				INS_USER									").append("\n");
		sb.append("		   ) VALUES (										").append("\n");
		sb.append("			?						,						").append("\n");
		sb.append("			NULL					,						").append("\n");
		sb.append("			GETDATE()				,						").append("\n");
		sb.append("			'00000000'				,						").append("\n");
		sb.append("			'N'						,						").append("\n");
		sb.append("			GETDATE()				,						").append("\n");
		sb.append("			'00000000'										").append("\n");
		sb.append("		   );												").append("\n");
		
		return sb.toString();
	}
	
	public String getTextContent(Node node) throws Exception {
		String textContent = "";

		if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
			textContent = node.getNodeValue(); 
		} else {
			Node child = node.getFirstChild();
	    
			if (child != null) {
				Node sibling = child.getNextSibling();
				if (sibling != null) {
					StringBuffer sb = new StringBuffer();
					getTextContent(node, sb);
					textContent = sb.toString();
				} else { 
					if (child.getNodeType() == Node.TEXT_NODE) {
						textContent = child.getNodeValue();
					} else {
						textContent = getTextContent(child);
					}
				}
			}
		}
		return textContent;
	}
	
	private void getTextContent(Node node, StringBuffer sb) throws Exception {
		Node child = node.getFirstChild();
	  
		while (child != null) {
			if (child.getNodeType() == Node.TEXT_NODE) {
			} else {
				getTextContent(child, sb);
			}
			
			child = child.getNextSibling();
		}
	}
	
	/**
	 * 예약번호 요청 XML
	 * @return
	 */
	public String getXMLReservaion(int termDay) {
		StringBuffer sb = new StringBuffer();

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>																												").append("\n");
		sb.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"http://webservices.sixt.de/webservices/franchise/1.03\">		").append("\n");
		sb.append("	<SOAP-ENV:Header>																																		").append("\n");
		sb.append("		<ns1:authentication>																																").append("\n");
		sb.append("			<ns1:companyNo>252</ns1:companyNo>																												").append("\n");
		sb.append("			<ns1:password>sfk72kerio299cva</ns1:password>																									").append("\n");
		sb.append("		</ns1:authentication>																																").append("\n");
		sb.append("	</SOAP-ENV:Header>																																		").append("\n");
		sb.append("	<SOAP-ENV:Body>																																			").append("\n");
		sb.append("		<ns1:reservationListRequest>																														").append("\n");
		sb.append("			<ns1:seconds>" + termDay + "</ns1:seconds>																										").append("\n");
		sb.append("		</ns1:reservationListRequest>																														").append("\n");
		sb.append("	</SOAP-ENV:Body>																																		").append("\n");
		sb.append("	</SOAP-ENV:Envelope>																																	").append("\n");

		return sb.toString();	
	}
	

	public String getXMLReservationDetail(String resrvNo){
		StringBuffer sb = new StringBuffer();

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>																												").append("\n");
		sb.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"http://webservices.sixt.de/webservices/franchise/1.03\">		").append("\n");
		sb.append("	<SOAP-ENV:Header>																																		").append("\n");
		sb.append("		<ns1:authentication>																																").append("\n");
		sb.append("			<ns1:companyNo>252</ns1:companyNo>																												").append("\n");
		sb.append("			<ns1:password>sfk72kerio299cva</ns1:password>																									").append("\n");
		sb.append("		</ns1:authentication>																																").append("\n");
		sb.append("	</SOAP-ENV:Header>																																		").append("\n");
		sb.append("	<SOAP-ENV:Body>																																			").append("\n");
		sb.append("		<ns1:reservationDetailsRequest>																														").append("\n");
		sb.append("			<ns1:reservationNo>" + resrvNo + "</ns1:reservationNo>																							").append("\n");
		sb.append("		</ns1:reservationDetailsRequest>																													").append("\n");
		sb.append("	</SOAP-ENV:Body>																																		").append("\n");
		sb.append("	</SOAP-ENV:Envelope>																																	").append("\n");

		return sb.toString();
	}
	
	/**
	 * 예약번호 저장
	 *
	 * <pre>
	 * 참조테이블 : TB_SIXT_RESERVATION
	 * </pre>
	 * 
	 * @return boolean
	 * @history  [2015-03-19] [김종덕] 최초작성
	 */	
	public boolean insertInbound(String reserNo) throws Exception {
		Connection conn = null;
		DBConnection db = new DBConnection();
		
		PreparedStatement pstmt = null;
		
		conn = db.getDBConnection();
		
		pstmt = conn.prepareStatement(InboundInsert());
		
		pstmt.setString(1, reserNo);
		pstmt.setString(2, reserNo);
		
		pstmt.addBatch();
		
		if(pstmt.executeUpdate()>0) return true;
		else return false;
	}
	
	/**
	 * 예약번호상세정보 수신
	 * @return
	 * @throws Exception
	 */
	public CobraReservationDetl requestReservaionDetl(String tmpResrvNo) throws Exception {
		HttpURLConnection httpConn 		= null; // 연결용 커넥션
		URL url 						= null;
		
		CobraReservationDetl oVO 	= new CobraReservationDetl();
		
		String sUrl = "https://webservices.sixt.de/webservices/franchise/prod/soap_1.03";
		
		String inputLine = null;
		String tmpLine = "";
		
		try {
			url = new URL(sUrl);
			httpConn = (HttpURLConnection) url.openConnection();
				
			httpConn.setDoOutput(true);
				
			httpConn.setRequestProperty("Authorization", "Basic " + "ZnJhbmNoaXNlOkVwdXdhNFViZXMzMA");
					
			OutputStream wr = httpConn.getOutputStream();
				
			wr.write(getXMLReservationDetail(tmpResrvNo).getBytes("utf-8"));
				
			wr.flush();
			wr.close();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
				
			while ((inputLine = in.readLine()) != null) {
				tmpLine = tmpLine + inputLine;
			}
			
			in.close();
			
			System.out.println("인바운드상세|" + tmpLine + "|");
			
			if (httpConn.getResponseCode() == 200) {
				
				DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
				domFactory.setNamespaceAware(true);
				
				DocumentBuilder builder = domFactory.newDocumentBuilder();
				
				StringReader is = new StringReader(tmpLine);
				
				Document document = builder.parse(new org.xml.sax.InputSource(is));
	
				XPath xpath = XPathFactory.newInstance().newXPath();
				
				xpath.setNamespaceContext(new PersonalNamespaceContext());
	
				XPathExpression xReservationNo 			= xpath.compile("//ns1:reservationNo/text()");
				XPathExpression xStatus					= xpath.compile("//ns1:status/text()");
				XPathExpression xGroup					= xpath.compile("//ns1:group/text()");
				XPathExpression xDuration				= xpath.compile("//ns1:duration/text()");
				XPathExpression xStationRemark			= xpath.compile("//ns1:stationRemark/text()");
				XPathExpression xFlightNo				= xpath.compile("//ns1:flightNo/text()");
				XPathExpression xBonusProgramNo			= xpath.compile("//ns1:bonusProgramNo/text()");
				XPathExpression xOriginAgencyNo			= xpath.compile("//ns1:agency/ns1:agencyNo/text()");
				XPathExpression xOrigiName1				= xpath.compile("//ns1:agency/ns1:name1/text()");
				XPathExpression xRateCode				= xpath.compile("//ns1:rate/ns1:code/text()");
				XPathExpression xPickupDatetime			= xpath.compile("//ns1:pickup/ns1:dateTime/text()");
				XPathExpression xPickupStationID		= xpath.compile("//ns1:pickup/ns1:station/ns1:stationID/text()");
				XPathExpression xPickupName				= xpath.compile("//ns1:pickup/ns1:station/ns1:name/text()");
				XPathExpression xReturnDatetime			= xpath.compile("//ns1:return/ns1:dateTime/text()");
				XPathExpression xReturnStationID		= xpath.compile("//ns1:return/ns1:station/ns1:stationID/text()");
				XPathExpression xReturnName				= xpath.compile("//ns1:return/ns1:station/ns1:name/text()");
				XPathExpression xFirstDriverPhone		= xpath.compile("//ns1:firstDriver/ns1:contact/ns1:phone/text()");
				XPathExpression xFirstDriverMobile		= xpath.compile("//ns1:firstDriver/ns1:contact/ns1:mobile/text()");
				XPathExpression xFirstDriverEmail		= xpath.compile("//ns1:firstDriver/ns1:contact/ns1:email/text()");
				XPathExpression xFirstDriverName1		= xpath.compile("//ns1:firstDriver/ns1:name1/text()");
				XPathExpression xFirstDriverName2		= xpath.compile("//ns1:firstDriver/ns1:name2/text()");
				XPathExpression xFirstDriverCountry		= xpath.compile("//ns1:firstDriver/ns1:address/ns1:country/text()");
				XPathExpression xForbiInsCode			= xpath.compile("//ns1:forbidden/ns1:insurance/ns1:code/text()");
				XPathExpression xForbiInsName			= xpath.compile("//ns1:forbidden/ns1:insurance/ns1:name/text()");
				XPathExpression xForbiInsBooked			= xpath.compile("//ns1:forbidden/ns1:insurance/ns1:booked/text()");
				XPathExpression xForbiInsCharged		= xpath.compile("//ns1:forbidden/ns1:insurance/ns1:charged/text()");
				XPathExpression xForbiInsPrice			= xpath.compile("//ns1:forbidden/ns1:insurance/ns1:price/text()");
				XPathExpression xForbiExtCode			= xpath.compile("//ns1:forbidden/ns1:extra/ns1:code/text()");
				XPathExpression xForbiExtName			= xpath.compile("//ns1:forbidden/ns1:extra/ns1:name/text()");
				XPathExpression xForbiExtBooked			= xpath.compile("//ns1:forbidden/ns1:extra/ns1:booked/text()");
				XPathExpression xForbiExtCharged		= xpath.compile("//ns1:forbidden/ns1:extra/ns1:charged/text()");
				XPathExpression xForbiExtPrice			= xpath.compile("//ns1:forbidden/ns1:extra/ns1:price/text()");
				XPathExpression xIncInsCode				= xpath.compile("//ns1:included/ns1:insurance/ns1:code/text()");
				XPathExpression xIncInsName				= xpath.compile("//ns1:included/ns1:insurance/ns1:name/text()");
				XPathExpression xIncInsBooked			= xpath.compile("//ns1:included/ns1:insurance/ns1:booked/text()");
				XPathExpression xIncInsCharged			= xpath.compile("//ns1:included/ns1:insurance/ns1:charged/text()");
				
				XPathExpression xIncInsPrice			= xpath.compile("//ns1:included/ns1:insurance/ns1:price/text()");
				
				XPathExpression xIncExtCode				= xpath.compile("//ns1:included/ns1:extra/ns1:code/text()");
				XPathExpression xIncExtName				= xpath.compile("//ns1:included/ns1:extra/ns1:name/text()");
				XPathExpression xIncExtBooked			= xpath.compile("//ns1:included/ns1:extra/ns1:booked/text()");
				XPathExpression xIncExtCharged			= xpath.compile("//ns1:included/ns1:extra/ns1:charged/text()");
				XPathExpression xIncExtPrice			= xpath.compile("//ns1:included/ns1:extra/ns1:price/text()");
				XPathExpression xBooExtCode				= xpath.compile("//ns1:booked/ns1:extra/ns1:code/text()");
				
				
				XPathExpression xBooExtName				= xpath.compile("//ns1:booked/ns1:extra/ns1:name/text()");
				XPathExpression xBooExtBooked			= xpath.compile("//ns1:booked/ns1:extra/ns1:booked/text()");
				XPathExpression xBooExtCharged			= xpath.compile("//ns1:booked/ns1:extra/ns1:charged/text()");
				XPathExpression xBooExtPriceCurrency	= xpath.compile("//ns1:booked/ns1:extra/ns1:price/ns1:currency/text()");
				XPathExpression xBooExtPriceNetAmount	= xpath.compile("//ns1:booked/ns1:extra/ns1:price/ns1:netAmount/text()");
				XPathExpression xBooExtPriceVatPercent	= xpath.compile("//ns1:booked/ns1:extra/ns1:price/ns1:vatPercent/text()");
				XPathExpression xBooInsCode				= xpath.compile("//ns1:booked/ns1:insurance/ns1:code/text()");///////////////
				XPathExpression xBooInsName				= xpath.compile("//ns1:booked/ns1:insurance/ns1:name/text()");
				XPathExpression xBooInsBooked			= xpath.compile("//ns1:booked/ns1:insurance/ns1:booked/text()");
				XPathExpression xBooInsCharged			= xpath.compile("//ns1:booked/ns1:insurance/ns1:charged/text()");
				XPathExpression xBooInsPriceCurrency	= xpath.compile("//ns1:booked/ns1:insurance/ns1:price/ns1:currency/text()");
				XPathExpression xBooInsPriceNetAmount	= xpath.compile("//ns1:booked/ns1:insurance/ns1:price/ns1:netAmount/text()");
				XPathExpression xBooInsPriceVatPercent	= xpath.compile("//ns1:booked/ns1:insurance/ns1:price/ns1:vatPercent/text()");
				XPathExpression xPaymentType			= xpath.compile("//ns1:payment/ns1:type/text()");
				XPathExpression xTotlDueAmount			= xpath.compile("//ns1:total/ns1:dueAmount/text()");
				XPathExpression xTotlGrossAmount		= xpath.compile("//ns1:total/ns1:grossAmount/text()");
				XPathExpression xTotlVatAmount			= xpath.compile("//ns1:total/ns1:vatAmount/text()");
				XPathExpression xTotlCurrency			= xpath.compile("//ns1:total/ns1:currency/text()");
				XPathExpression xTotlNetAmount			= xpath.compile("//ns1:total/ns1:netAmount/text()");
				XPathExpression xTotlVatPercent			= xpath.compile("//ns1:total/ns1:vatPercent/text()");
				XPathExpression xRateIsPrepaid			= xpath.compile("//ns1:rate/ns1:isPrepaid/text()");	// Prepaid 여부
				
				Object resultReservationNo 			 	= xReservationNo.evaluate(document, XPathConstants.NODESET);
				Object resultStatus					 	= xStatus.evaluate(document, XPathConstants.NODESET);
				Object resultGroup					 	= xGroup.evaluate(document, XPathConstants.NODESET);
				Object resultDuration				 	= xDuration.evaluate(document, XPathConstants.NODESET);
				Object resultStationRemark			 	= xStationRemark.evaluate(document, XPathConstants.NODESET);
				Object resultFlightNo				 	= xFlightNo.evaluate(document, XPathConstants.NODESET);
				Object resultBonusProgramNo			 	= xBonusProgramNo.evaluate(document, XPathConstants.NODESET);
				Object resultOriginAgencyNo			 	= xOriginAgencyNo.evaluate(document, XPathConstants.NODESET);
				Object resultOrigiName1					= xOrigiName1.evaluate(document, XPathConstants.NODESET);
				Object resultRateCode				 	= xRateCode.evaluate(document, XPathConstants.NODESET);
				Object resultPickupDatetime			 	= xPickupDatetime.evaluate(document, XPathConstants.NODESET);
				Object resultPickupStationID		 	= xPickupStationID.evaluate(document, XPathConstants.NODESET);
				Object resultPickupName				 	= xPickupName.evaluate(document, XPathConstants.NODESET);
				Object resultReturnDatetime				= xReturnDatetime.evaluate(document, XPathConstants.NODESET);
				Object resultReturnStationID		 	= xReturnStationID.evaluate(document, XPathConstants.NODESET);
				Object resultReturnName				 	= xReturnName.evaluate(document, XPathConstants.NODESET);
				Object resultFirstDriverPhone		 	= xFirstDriverPhone.evaluate(document, XPathConstants.NODESET);
				Object resultFirstDriverMobile		 	= xFirstDriverMobile.evaluate(document, XPathConstants.NODESET);
				Object resultFirstDriverEmail		 	= xFirstDriverEmail.evaluate(document, XPathConstants.NODESET);
				Object resultFirstDriverName1			= xFirstDriverName1.evaluate(document, XPathConstants.NODESET);
				Object resultFirstDriverName2		 	= xFirstDriverName2.evaluate(document, XPathConstants.NODESET);
				Object resultFirstDriverCountry			= xFirstDriverCountry.evaluate(document, XPathConstants.NODESET);
				Object resultForbiInsCode			 	= xForbiInsCode.evaluate(document, XPathConstants.NODESET);
				Object resultForbiInsName			 	= xForbiInsName.evaluate(document, XPathConstants.NODESET);
				Object resultForbiInsBooked			 	= xForbiInsBooked.evaluate(document, XPathConstants.NODESET);
				Object resultForbiInsCharged		 	= xForbiInsCharged.evaluate(document, XPathConstants.NODESET);
				Object resultForbiInsPrice			 	= xForbiInsPrice.evaluate(document, XPathConstants.NODESET);
				Object resultForbiExtCode			 	= xForbiExtCode.evaluate(document, XPathConstants.NODESET);
				Object resultForbiExtName			 	= xForbiExtName.evaluate(document, XPathConstants.NODESET);
				Object resultForbiExtBooked			 	= xForbiExtBooked.evaluate(document, XPathConstants.NODESET);
				Object resultForbiExtCharged		 	= xForbiExtCharged.evaluate(document, XPathConstants.NODESET);
				Object resultForbiExtPrice			 	= xForbiExtPrice.evaluate(document, XPathConstants.NODESET);
				
				
				Object resultIncInsCode				 	= xIncInsCode.evaluate(document, XPathConstants.NODESET);
				Object resultIncInsName				 	= xIncInsName.evaluate(document, XPathConstants.NODESET);
				Object resultIncInsBooked			 	= xIncInsBooked.evaluate(document, XPathConstants.NODESET);
				Object resultIncInsCharged				= xIncInsCharged.evaluate(document, XPathConstants.NODESET);
				Object resultIncInsPrice			 	= xIncInsPrice.evaluate(document, XPathConstants.NODESET);
				Object resultIncExtCode				 	= xIncExtCode.evaluate(document, XPathConstants.NODESET);
				Object resultIncExtName				 	= xIncExtName.evaluate(document, XPathConstants.NODESET);
				Object resultIncExtBooked			 	= xIncExtBooked.evaluate(document, XPathConstants.NODESET);
				Object resultIncExtCharged			 	= xIncExtCharged.evaluate(document, XPathConstants.NODESET);
				Object resultIncExtPrice			 	= xIncExtPrice.evaluate(document, XPathConstants.NODESET);
				Object resultBooExtCode				 	= xBooExtCode.evaluate(document, XPathConstants.NODESET);
				Object resultBooExtName				 	= xBooExtName.evaluate(document, XPathConstants.NODESET);
				Object resultBooExtBooked			 	= xBooExtBooked.evaluate(document, XPathConstants.NODESET);
				Object resultBooExtCharged			 	= xBooExtCharged.evaluate(document, XPathConstants.NODESET);
				Object resultBooExtPriceCurrency	 	= xBooExtPriceCurrency.evaluate(document, XPathConstants.NODESET);
				Object resultBooExtPriceNetAmount	 	= xBooExtPriceNetAmount.evaluate(document, XPathConstants.NODESET);
				Object resultBooExtPriceVatPercent	 	= xBooExtPriceVatPercent.evaluate(document, XPathConstants.NODESET);
				Object resultBooInsCode				 	= xBooInsCode.evaluate(document, XPathConstants.NODESET);
				Object resultBooInsName				 	= xBooInsName.evaluate(document, XPathConstants.NODESET);
				Object resultBooInsBooked			 	= xBooInsBooked.evaluate(document, XPathConstants.NODESET);
				Object resultBooInsCharged			 	= xBooInsCharged.evaluate(document, XPathConstants.NODESET);
				Object resultBooInsPriceCurrency	 	= xBooInsPriceCurrency.evaluate(document, XPathConstants.NODESET);
				Object resultBooInsPriceNetAmount	 	= xBooInsPriceNetAmount.evaluate(document, XPathConstants.NODESET);
				Object resultBooInsPriceVatPercent	 	= xBooInsPriceVatPercent.evaluate(document, XPathConstants.NODESET);
				Object resultPaymentType				= xPaymentType.evaluate(document, XPathConstants.NODESET);
				Object resultTotlDueAmount			 	= xTotlDueAmount.evaluate(document, XPathConstants.NODESET);
				Object resultTotlGrossAmount		 	= xTotlGrossAmount.evaluate(document, XPathConstants.NODESET);
				Object resultTotlVatAmount			 	= xTotlVatAmount.evaluate(document, XPathConstants.NODESET);
				Object resultTotlCurrency			 	= xTotlCurrency.evaluate(document, XPathConstants.NODESET);
				Object resultTotlNetAmount			 	= xTotlNetAmount.evaluate(document, XPathConstants.NODESET);
				Object resultTotlVatPercent			 	= xTotlVatPercent.evaluate(document, XPathConstants.NODESET);
				Object resultRatePrepaid			 	= xRateIsPrepaid.evaluate(document, XPathConstants.NODESET);
				
				NodeList xReservationNoNode 		 	= (NodeList) resultReservationNo;
				NodeList xStatusNode 				 	= (NodeList) resultStatus;
				NodeList xGroupNode 				 	= (NodeList) resultGroup;
				NodeList xDurationNode 				 	= (NodeList) resultDuration;
				NodeList xStationRemarkNode 		 	= (NodeList) resultStationRemark;
				NodeList xFlightNoNode 				 	= (NodeList) resultFlightNo;
				NodeList xBonusProgramNoNode 		 	= (NodeList) resultBonusProgramNo;
				NodeList xOriginAgencyNoNode 		 	= (NodeList) resultOriginAgencyNo;
				NodeList xOrigiName1Node 				= (NodeList) resultOrigiName1;
				NodeList xRateCodeNode 				 	= (NodeList) resultRateCode;
				NodeList xPickupDatetimeNode 		 	= (NodeList) resultPickupDatetime;
				NodeList xPickupStationIDNode 		 	= (NodeList) resultPickupStationID;
				NodeList xPickupNameNode 			 	= (NodeList) resultPickupName;
				NodeList xReturnDatetimeNode 		 	= (NodeList) resultReturnDatetime;
				NodeList xReturnStationIDNode 			= (NodeList) resultReturnStationID;
				NodeList xReturnNameNode 			 	= (NodeList) resultReturnName;
				NodeList xFirstDriverPhoneNode 		 	= (NodeList) resultFirstDriverPhone;
				NodeList xFirstDriverMobileNode 	 	= (NodeList) resultFirstDriverMobile;
				NodeList xFirstDriverEmailNode 		 	= (NodeList) resultFirstDriverEmail;
				NodeList xFirstDriverName1Node 		 	= (NodeList) resultFirstDriverName1;
				NodeList xFirstDriverName2Node 		 	= (NodeList) resultFirstDriverName2;
				NodeList xFirstDriverCountryNode 	 	= (NodeList) resultFirstDriverCountry;
				NodeList xForbiInsCodeNode 			 	= (NodeList) resultForbiInsCode;
				NodeList xForbiInsNameNode 			 	= (NodeList) resultForbiInsName;
				NodeList xForbiInsBookedNode 			= (NodeList) resultForbiInsBooked;
				NodeList xForbiInsChargedNode 			= (NodeList) resultForbiInsCharged;
				NodeList xForbiInsPriceNode 			= (NodeList) resultForbiInsPrice;
				NodeList xForbiExtCodeNode 				= (NodeList) resultForbiExtCode;
				NodeList xForbiExtNameNode 				= (NodeList) resultForbiExtName;
				NodeList xForbiExtBookedNode 			= (NodeList) resultForbiExtBooked;
				NodeList xForbiExtChargedNode 			= (NodeList) resultForbiExtCharged;
				NodeList xForbiExtPriceNode 			= (NodeList) resultForbiExtPrice;
				NodeList xIncInsCodeNode 				= (NodeList) resultIncInsCode;
				NodeList xIncInsNameNode 				= (NodeList) resultIncInsName;
				NodeList xIncInsBookedNode				= (NodeList) resultIncInsBooked;
				NodeList xIncInsChargedNode				= (NodeList) resultIncInsCharged;
				NodeList xIncInsPriceNode 				= (NodeList) resultIncInsPrice;
				NodeList xIncExtCodeNode 				= (NodeList) resultIncExtCode;
				NodeList xIncExtNameNode 				= (NodeList) resultIncExtName;
				NodeList xIncExtBookedNode 				= (NodeList) resultIncExtBooked;
				NodeList xIncExtChargedNode 			= (NodeList) resultIncExtCharged;
				NodeList xIncExtPriceNode 				= (NodeList) resultIncExtPrice;
				NodeList xBooExtCodeNode 				= (NodeList) resultBooExtCode;
				NodeList xBooExtNameNode 				= (NodeList) resultBooExtName;
				NodeList xBooExtBookedNode 				= (NodeList) resultBooExtBooked;
				NodeList xBooExtChargedNode 			= (NodeList) resultBooExtCharged;
				NodeList xBooExtPriceCurrencyNode 		= (NodeList) resultBooExtPriceCurrency;
				NodeList xBooExtPriceNetAmountNode 		= (NodeList) resultBooExtPriceNetAmount;
				NodeList xBooExtPriceVatPercentNode		= (NodeList) resultBooExtPriceVatPercent;
				NodeList xBooInsCodeNode 				= (NodeList) resultBooInsCode;
				NodeList xBooInsNameNode 				= (NodeList) resultBooInsName;
				NodeList xBooInsBookedNode 				= (NodeList) resultBooInsBooked;
				NodeList xBooInsChargedNode 			= (NodeList) resultBooInsCharged;
				NodeList xBooInsPriceCurrencyNode 		= (NodeList) resultBooInsPriceCurrency;
				NodeList xBooInsPriceNetAmountNode		= (NodeList) resultBooInsPriceNetAmount;
				NodeList xBooInsPriceVatPercentNode 	= (NodeList) resultBooInsPriceVatPercent;
				NodeList xPaymentTypeNode 				= (NodeList) resultPaymentType;
				NodeList xTotlDueAmountNode				= (NodeList) resultTotlDueAmount;
				NodeList xTotlGrossAmountNode			= (NodeList) resultTotlGrossAmount;
				NodeList xTotlVatAmountNode				= (NodeList) resultTotlVatAmount;
				NodeList xTotlCurrencyNode				= (NodeList) resultTotlCurrency;
				NodeList xTotlNetAmountNode				= (NodeList) resultTotlNetAmount;
				NodeList xTotlVatPercentNode			= (NodeList) resultTotlVatPercent;
				NodeList xRatePrepaidNode				= (NodeList) resultRatePrepaid;
				
				String sReservationNo			= "";
				String sStatus					= "";
				String sGroup					= "";
				String sDuration				= "";
				String sStationRemark			= "";
				String sFlightNo				= "";
				String sBonusProgramNo			= "";
				String sOriginAgencyNo			= "";
				String sOrigiName1				= "";
				String sRateCode				= "";
				String sPickupDatetime			= "";
				String sPickupStationID			= "";
				String sPickupName				= "";
				String sReturnDatetime			= "";
				String sReturnStationID			= "";
				String sReturnName				= "";
				String sFirstDriverPhone		= "";
				String sFirstDriverMobile		= "";
				String sFirstDriverEmail		= "";
				String sFirstDriverName1		= "";
				String sFirstDriverName2		= "";
				String sFirstDriverCountry		= "";
				String sForbiInsCode			= "";
				String sForbiInsName			= "";
				String sForbiInsBooked			= "";
				String sForbiInsCharged			= "";
				String sForbiInsPrice			= "";
				String sForbiExtCode			= "";
				String sForbiExtName			= "";
				String sForbiExtBooked			= "";
				String sForbiExtCharged			= "";
				String sForbiExtPrice			= "";
				String sIncInsCode				= "";
				String sIncInsName				= "";
				String sIncInsBooked			= "";
				String sIncInsCharged			= "";
				String sIncInsPrice				= "";
				String sIncExtCode				= "";	
				String sIncExtName				= "";	
				String sIncExtBooked			= "";	
				String sIncExtCharged			= "";	
				String sIncExtPrice				= "";
				String sBooExtCode				= "";	
				String sBooExtName				= "";	
				String sBooExtBooked			= "";	
				String sBooExtCharged			= "";	
				String sBooExtPriceCurrency		= "";
				String sBooExtPriceNetAmount	= "";	
				String sBooExtPriceVatPercent	= "";
				String sBooInsCode				= "";
				String sBooInsName				= "";
				String sBooInsBooked			= "";
				String sBooInsCharged			= "";
				String sBooInsPriceCurrency		= "";
				String sBooInsPriceNetAmount	= "";
				String sBooInsPriceVatPercent	= "";
				String sPaymentType				= "";
				
				String sTotlDueAmountNode		= "";
				String sTotlGrossAmountNode		= "";
				String sTotlVatAmountNode		= "";
				String sTotlCurrencyNode		= "";
				String sTotlNetAmountNode		= "";
				String sTotlVatPercentNode		= "";
				String sRatePrepaid				= "";
				
				for( int i = 0; i< xReservationNoNode.getLength();i++) {
					if ( i > 0) { sReservationNo = sReservationNo + "→" + xStatusNode.item(i).getNodeValue();} else {sReservationNo = sReservationNo + xReservationNoNode.item(i).getNodeValue();}
				}
				
				for( int i = 0; i< xStatusNode.getLength();i++) {
					if ( i > 0) { sStatus = sStatus + "→" + xStatusNode.item(i).getNodeValue(); } else { sStatus = sStatus + xStatusNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i< xGroupNode.getLength();i++) {
					if ( i > 0) { sGroup = sGroup + "→" + xGroupNode.item(i).getNodeValue(); } else { sGroup = sGroup + xGroupNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i< xDurationNode.getLength();i++) {
					if ( i > 0) { sDuration = sDuration + "→" + xDurationNode.item(i).getNodeValue(); } else { sDuration = sDuration + xDurationNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i< xStationRemarkNode.getLength();i++) {
					if ( i > 0) { sStationRemark = sStationRemark + "→" + xStationRemarkNode.item(i).getNodeValue(); } else { sStationRemark = sStationRemark + xStationRemarkNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i< xFlightNoNode.getLength();i++) {
					if ( i > 0) { sFlightNo = sFlightNo + "→" + xFlightNoNode.item(i).getNodeValue(); } else { sFlightNo = sFlightNo + xFlightNoNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i< xBonusProgramNoNode.getLength();i++) {
					if ( i > 0) { sBonusProgramNo = sBonusProgramNo + "→" + xBonusProgramNoNode.item(i).getNodeValue(); } else { sBonusProgramNo = sBonusProgramNo + xBonusProgramNoNode.item(i).getNodeValue(); }
				}
			
				for( int i = 0; i< xOriginAgencyNoNode.getLength();i++) {
					if ( i > 0) { sOriginAgencyNo = sOriginAgencyNo + "→" + xOriginAgencyNoNode.item(i).getNodeValue(); } else { sOriginAgencyNo = sOriginAgencyNo + xOriginAgencyNoNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i< xOrigiName1Node.getLength();i++) {
					if ( i > 0) { sOrigiName1 = sOrigiName1 + "→" + xOrigiName1Node.item(i).getNodeValue(); } else { sOrigiName1 = sOrigiName1 + xOrigiName1Node.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i< xRateCodeNode.getLength();i++) {
					if ( i > 0) { sRateCode = sRateCode + "→" + xRateCodeNode.item(i).getNodeValue(); } else { sRateCode = sRateCode + xRateCodeNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i< xPickupDatetimeNode.getLength();i++) {
					if ( i > 0) { sPickupDatetime = sPickupDatetime + "→" + xPickupDatetimeNode.item(i).getNodeValue(); } else { sPickupDatetime = sPickupDatetime + xPickupDatetimeNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i< xPickupStationIDNode.getLength();i++) {
					if ( i > 0) { sPickupStationID = sPickupStationID + "→" + xPickupStationIDNode.item(i).getNodeValue(); } else { sPickupStationID = sPickupStationID + xPickupStationIDNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i< xPickupNameNode.getLength();i++) {
					if ( i > 0) { sPickupName = sPickupName + "→" + xPickupNameNode.item(i).getNodeValue(); } else { sPickupName = sPickupName + xPickupNameNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i< xReturnDatetimeNode.getLength();i++) {
					if ( i > 0) { sReturnDatetime = sReturnDatetime + "→" + xReturnDatetimeNode.item(i).getNodeValue(); } else { sReturnDatetime = sReturnDatetime + xReturnDatetimeNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i< xReturnStationIDNode.getLength();i++) {
					if ( i > 0) { sReturnStationID = sReturnStationID + "→" + xReturnStationIDNode.item(i).getNodeValue(); } else { sReturnStationID = sReturnStationID + xReturnStationIDNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xReturnNameNode.getLength();i++) {	
					if ( i > 0) { sReturnName = sReturnName	 + "→" + xReturnNameNode.item(i).getNodeValue(); } else { sReturnName = sReturnName + xReturnNameNode.item(i).getNodeValue();}
				}
				for( int i = 0; i < xFirstDriverPhoneNode.getLength();i++) {
					if ( i > 0) { sFirstDriverPhone = sFirstDriverPhone + "→" + xFirstDriverPhoneNode.item(i).getNodeValue();} else {sFirstDriverPhone = sFirstDriverPhone + xFirstDriverPhoneNode.item(i).getNodeValue();}
				}
				
				for( int i = 0; i < xFirstDriverMobileNode.getLength();i++) {	
					if ( i > 0) { sFirstDriverMobile = sFirstDriverMobile + "→" + xFirstDriverMobileNode.item(i).getNodeValue(); } else { sFirstDriverMobile = sFirstDriverMobile + xFirstDriverMobileNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xFirstDriverEmailNode.getLength();i++) {
					if ( i > 0) { sFirstDriverEmail	= sFirstDriverEmail + "→" + xFirstDriverEmailNode.item(i).getNodeValue(); } else { sFirstDriverEmail = sFirstDriverEmail + xFirstDriverEmailNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xFirstDriverName1Node.getLength();i++) {	
					if ( i > 0) { sFirstDriverName1	= sFirstDriverName1 + "→" + xFirstDriverName1Node.item(i).getNodeValue(); } else { sFirstDriverName1 = sFirstDriverName1 + xFirstDriverName1Node.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xFirstDriverName2Node.getLength();i++) {	
					if ( i > 0) { sFirstDriverName2	= sFirstDriverName2	+ "→" + xFirstDriverName2Node.item(i).getNodeValue(); } else { sFirstDriverName2 = sFirstDriverName2 + xFirstDriverName2Node.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xFirstDriverCountryNode.getLength();i++) {	
					if ( i > 0) { sFirstDriverCountry = sFirstDriverCountry	+ "→" + xFirstDriverCountryNode.item(i).getNodeValue(); } else { sFirstDriverCountry = sFirstDriverCountry + xFirstDriverCountryNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xForbiInsCodeNode.getLength();i++) {	
					if ( i > 0) { sForbiInsCode	= sForbiInsCode	+ "→" + xForbiInsCodeNode.item(i).getNodeValue(); } else { sForbiInsCode = sForbiInsCode + xForbiInsCodeNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xForbiInsNameNode.getLength();i++) {	
					if ( i > 0) { sForbiInsName	= sForbiInsName + "→" + xForbiInsNameNode.item(i).getNodeValue(); } else { sForbiInsName = sForbiInsName + xForbiInsNameNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xForbiInsBookedNode.getLength();i++) {	
					if ( i > 0) { sForbiInsBooked = sForbiInsBooked	+ "→" + xForbiInsBookedNode.item(i).getNodeValue(); } else { sForbiInsBooked	= sForbiInsBooked + xForbiInsBookedNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xForbiInsChargedNode.getLength();i++) {	
					if ( i > 0) { sForbiInsCharged = sForbiInsCharged + "→" + xForbiInsChargedNode.item(i).getNodeValue(); } else { sForbiInsCharged = sForbiInsCharged + xForbiInsChargedNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xForbiInsPriceNode.getLength();i++) {	
					if ( i > 0) { sForbiInsPrice = sForbiInsPrice + "→" + xForbiInsPriceNode.item(i).getNodeValue(); } else { sForbiInsPrice = sForbiInsPrice + xForbiInsPriceNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xForbiExtCodeNode.getLength();i++) {	
					if ( i > 0) { sForbiExtCode	= sForbiExtCode + "→" + xForbiExtCodeNode.item(i).getNodeValue(); } else { sForbiExtCode = sForbiExtCode + xForbiExtCodeNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xForbiExtNameNode.getLength();i++) {	
					if ( i > 0) { sForbiExtName	= sForbiExtName + "→" + xForbiExtNameNode.item(i).getNodeValue(); } else { sForbiExtName = sForbiExtName + xForbiExtNameNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xForbiExtBookedNode.getLength();i++) {	
					if ( i > 0) { sForbiExtBooked = sForbiExtBooked + "→" + xForbiExtBookedNode.item(i).getNodeValue(); } else { sForbiExtBooked = sForbiExtBooked + xForbiExtBookedNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xForbiExtChargedNode.getLength();i++) {	
					if ( i > 0) { sForbiExtCharged = sForbiExtCharged + "→" + xForbiExtChargedNode.item(i).getNodeValue(); } else { sForbiExtCharged = sForbiExtCharged + xForbiExtChargedNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xForbiExtPriceNode.getLength();i++) {	
					if ( i > 0) { sForbiExtPrice = sForbiExtPrice + "→" + xForbiExtPriceNode.item(i).getNodeValue(); } else { sForbiExtPrice = sForbiExtPrice + xForbiExtPriceNode.item(i).getNodeValue();}
				}
				
				for( int i = 0; i < xIncInsCodeNode.getLength();i++) {	
					if ( i > 0) { sIncInsCode = sIncInsCode	+ "→" + xIncInsCodeNode.item(i).getNodeValue(); } else { sIncInsCode	= sIncInsCode + xIncInsCodeNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xIncInsNameNode.getLength();i++) {	
					if ( i > 0) { sIncInsName = sIncInsName + "→" + xIncInsNameNode.item(i).getNodeValue(); } else { sIncInsName	= sIncInsName + xIncInsNameNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xIncInsBookedNode.getLength();i++) {	
					if ( i > 0) { sIncInsBooked = sIncInsBooked + "→" + xIncInsBookedNode.item(i).getNodeValue(); } else { sIncInsBooked = sIncInsBooked + xIncInsBookedNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xIncInsChargedNode.getLength();i++) {	
					if ( i > 0) { sIncInsCharged = sIncInsCharged + "→" + xIncInsChargedNode.item(i).getNodeValue(); } else { sIncInsCharged = sIncInsCharged + xIncInsChargedNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xIncInsPriceNode.getLength();i++) {	
					if ( i > 0) { sIncInsPrice = sIncInsPrice + "→" + xIncInsPriceNode.item(i).getNodeValue(); } else { sIncInsPrice = sIncInsPrice + xIncInsPriceNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xIncExtCodeNode.getLength();i++) {	
					if ( i > 0) { sIncExtCode = sIncExtCode + "→" + xIncExtCodeNode.item(i).getNodeValue(); } else { sIncExtCode	= sIncExtCode + xIncExtCodeNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xIncExtNameNode.getLength();i++) {	
					if ( i > 0) { sIncExtName = sIncExtName + "→" + xIncExtNameNode.item(i).getNodeValue(); } else { sIncExtName	= sIncExtName + xIncExtNameNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xIncExtBookedNode.getLength();i++) {	
					if ( i > 0) { sIncExtBooked	= sIncExtBooked	+ "→" + xIncExtBookedNode.item(i).getNodeValue(); } else { sIncExtBooked = sIncExtBooked + xIncExtBookedNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xIncExtChargedNode.getLength();i++) {	
					if ( i > 0) { sIncExtCharged = sIncExtCharged + "→" + xIncExtChargedNode.item(i).getNodeValue(); } else { sIncExtCharged = sIncExtCharged + xIncExtChargedNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xIncExtPriceNode.getLength();i++) {	
					if ( i > 0) { sIncExtPrice = sIncExtPrice + "→" + xIncExtPriceNode.item(i).getNodeValue(); } else { sIncExtPrice = sIncExtPrice + xIncExtPriceNode.item(i).getNodeValue(); }
				}
				
				// 추가 옵션
				for( int i = 0; i < xBooExtCodeNode.getLength();i++) {	
					if ( i > 0) { sBooExtCode = sBooExtCode	+ "→" + xBooExtCodeNode.item(i).getNodeValue();	} else { sBooExtCode	= sBooExtCode + xBooExtCodeNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xBooExtNameNode.getLength();i++) {	
					if ( i > 0) { sBooExtName = sBooExtName + "→" + xBooExtNameNode.item(i).getNodeValue(); } else { sBooExtName = sBooExtName + xBooExtNameNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xBooExtBookedNode.getLength();i++) {	
					if ( i > 0) { sBooExtBooked = sBooExtBooked + "→" + xBooExtBookedNode.item(i).getNodeValue(); } else { sBooExtBooked = sBooExtBooked + xBooExtBookedNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xBooExtChargedNode.getLength();i++) {	
					if ( i > 0) { sBooExtCharged = sBooExtCharged + "→" + xBooExtChargedNode.item(i).getNodeValue(); } else { sBooExtCharged = sBooExtCharged + xBooExtChargedNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xBooExtPriceCurrencyNode.getLength();i++) {	
					if ( i > 0) { sBooExtPriceCurrency = sBooExtPriceCurrency + "→" + xBooExtPriceCurrencyNode.item(i).getNodeValue(); } else { sBooExtPriceCurrency = sBooExtPriceCurrency + xBooExtPriceCurrencyNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xBooExtPriceNetAmountNode.getLength();i++) {	
					if ( i > 0) { sBooExtPriceNetAmount = sBooExtPriceNetAmount + "→" + xBooExtPriceNetAmountNode.item(i).getNodeValue();} else {sBooExtPriceNetAmount = sBooExtPriceNetAmount + xBooExtPriceNetAmountNode.item(i).getNodeValue();}
				}
				
				for( int i = 0; i < xBooExtPriceVatPercentNode.getLength();i++) {	
					if ( i > 0) { sBooExtPriceVatPercent = sBooExtPriceVatPercent + "→" + xBooExtPriceVatPercentNode.item(i).getNodeValue(); } else { sBooExtPriceVatPercent = sBooExtPriceVatPercent + xBooExtPriceVatPercentNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xBooInsCodeNode.getLength();i++) {	
					if ( i > 0) { sBooInsCode = sBooInsCode + "→" + xBooInsCodeNode.item(i).getNodeValue(); } else { sBooInsCode	= sBooInsCode + xBooInsCodeNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xBooInsNameNode.getLength();i++) {	
					if ( i > 0) { sBooInsName = sBooInsName	+ "→" + xBooInsNameNode.item(i).getNodeValue(); } else { sBooInsName	= sBooInsName + xBooInsNameNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xBooInsBookedNode.getLength();i++) {	
					if ( i > 0) { sBooInsBooked = sBooInsBooked	+ "→" + xBooInsBookedNode.item(i).getNodeValue(); } else { sBooInsBooked = sBooInsBooked+ xBooInsBookedNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xBooInsChargedNode.getLength();i++) {	
					if ( i > 0) { sBooInsCharged = sBooInsCharged + "→" + xBooInsChargedNode.item(i).getNodeValue(); } else { sBooInsCharged = sBooInsCharged + xBooInsChargedNode.item(i).getNodeValue(); }
				}				
				
				for( int i = 0; i < xBooInsPriceCurrencyNode.getLength();i++) {	
					if ( i > 0) { sBooInsPriceCurrency = sBooInsPriceCurrency + "→" + xBooInsPriceCurrencyNode.item(i).getNodeValue(); } else { sBooInsPriceCurrency = sBooInsPriceCurrency + xBooInsPriceCurrencyNode.item(i).getNodeValue(); }
				}
			
				for( int i = 0; i < xBooInsPriceNetAmountNode.getLength();i++) {	
					if ( i > 0) { sBooInsPriceNetAmount = sBooInsPriceNetAmount + "→" + xBooInsPriceNetAmountNode.item(i).getNodeValue(); } else { sBooInsPriceNetAmount = sBooInsPriceNetAmount + xBooInsPriceNetAmountNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xBooInsPriceVatPercentNode.getLength();i++) {	
					if ( i > 0) { sBooInsPriceVatPercent = sBooInsPriceVatPercent + "→" + xBooInsPriceVatPercentNode.item(i).getNodeValue(); } else { sBooInsPriceVatPercent = sBooInsPriceVatPercent + xBooInsPriceVatPercentNode.item(i).getNodeValue(); }
				}
				
				for( int i = 0; i < xPaymentTypeNode.getLength();i++) {	
					if ( i > 0) { sPaymentType = sPaymentType + "→" + xPaymentTypeNode.item(i).getNodeValue(); } else { sPaymentType = sPaymentType+ xPaymentTypeNode.item(i).getNodeValue(); }
				}
				
				//////////////////////////////
	
				for( int i = 0; i < xRatePrepaidNode.getLength();i++) {	
					if ( i > 0) { sRatePrepaid = sRatePrepaid + "→" + xRatePrepaidNode.item(i).getNodeValue(); } else { sRatePrepaid = sRatePrepaid+ xRatePrepaidNode.item(i).getNodeValue(); }
				}
				
				
				for( int i = 0; i < xTotlDueAmountNode.getLength();i++) {	
					if ( i > 0) { sTotlDueAmountNode = sTotlDueAmountNode + "→" + xTotlDueAmountNode.item(i).getNodeValue(); } else { sTotlDueAmountNode = sTotlDueAmountNode + xTotlDueAmountNode.item(i).getNodeValue(); }
				}
				
				
				
				String dTotlGrossAmountNode = "";
				String dTotlVatAmountNode 	= "";
				String dTotlNetAmountNode 	= "";
				
				String tmpDTotlGrossAmountNode 	= "";
				String tmpDTotlVatAmountNode 	= "";
				String tmpDTotlNetAmountNode 	= "";
				
				
				NumberFormat nf = NumberFormat.getInstance(); //NumberFormat 객체 생성 
				nf.setGroupingUsed(false); //지수 표현 제거
				
				
				double dTotlGrossAmountNodeAmount = (double)0.0;
				double dTotlVatAmountNodeAmount = (double)0.0;
				double dTotlNetAmountNodeAmount = (double)0.0;
				
				/////////////////////////////////////////////////////
				for( int i = 0; i < xTotlGrossAmountNode.getLength(); i++) {
					if ( xTotlGrossAmountNode.getLength() > 1 ) {
						tmpDTotlGrossAmountNode = nf.format(Double.parseDouble(xTotlGrossAmountNode.item(i).getNodeValue()));
						dTotlGrossAmountNodeAmount = dTotlGrossAmountNodeAmount + Double.parseDouble(tmpDTotlGrossAmountNode);
					} else {
						tmpDTotlGrossAmountNode = tmpDTotlGrossAmountNode + nf.format(Double.parseDouble(xTotlGrossAmountNode.item(i).getNodeValue()));
					}
				}
				
				if (xTotlGrossAmountNode.getLength() > 1) {
					dTotlGrossAmountNode = Double.toString(dTotlGrossAmountNodeAmount);
 				} else {
 					dTotlGrossAmountNode = tmpDTotlGrossAmountNode;
 				}
				////////////////////////////////
				
				
				/////////////////////////////////////////
				for( int i = 0; i < xTotlVatAmountNode.getLength(); i++) {
					if (xTotlVatAmountNode.getLength() > 1) {
						tmpDTotlVatAmountNode = nf.format(Double.parseDouble(xTotlVatAmountNode.item(i).getNodeValue()));
						dTotlVatAmountNodeAmount = dTotlVatAmountNodeAmount + Double.parseDouble(tmpDTotlVatAmountNode);
					} else {
						tmpDTotlVatAmountNode = tmpDTotlVatAmountNode + nf.format(Double.parseDouble(xTotlVatAmountNode.item(i).getNodeValue()));
					}
				}
				
				if ( xTotlVatAmountNode.getLength() > 1) {
					dTotlVatAmountNode = Double.toString(dTotlVatAmountNodeAmount);
				} else {
					dTotlVatAmountNode = tmpDTotlVatAmountNode;
				}
				//////////////////////////////////////////////
				
				for( int i = 0; i < xTotlCurrencyNode.getLength();i++) {
					if ( i > 0) { sTotlCurrencyNode = sTotlCurrencyNode + "→" + xTotlCurrencyNode.item(i).getNodeValue(); } else { sTotlCurrencyNode = sTotlCurrencyNode + xTotlCurrencyNode.item(i).getNodeValue(); }
				}

				///////////////////////////////////////////////////////////////////
				for( int i = 0; i < xTotlNetAmountNode.getLength(); i++) {
					if ( xTotlNetAmountNode.getLength() > 1) {
						tmpDTotlNetAmountNode = nf.format(Double.parseDouble(xTotlNetAmountNode.item(i).getNodeValue()));
						dTotlNetAmountNodeAmount = dTotlNetAmountNodeAmount + Double.parseDouble(tmpDTotlNetAmountNode);
					} else {
						tmpDTotlNetAmountNode = tmpDTotlNetAmountNode + nf.format(Double.parseDouble(xTotlNetAmountNode.item(i).getNodeValue()));
					}
				}
				
				if ( xTotlNetAmountNode.getLength() > 1) {
					dTotlNetAmountNode = Double.toString(dTotlNetAmountNodeAmount);
				} else {
					dTotlNetAmountNode = tmpDTotlNetAmountNode;
				}
				
				///////////////////////////////////////////////////////////////////
				
				for( int i = 0; i < xTotlVatPercentNode.getLength();i++) {	
					if ( i > 0) { sTotlVatPercentNode = sTotlVatPercentNode + "→" + xTotlVatPercentNode.item(i).getNodeValue(); } else { sTotlVatPercentNode = sTotlVatPercentNode + xTotlVatPercentNode.item(i).getNodeValue(); }
				}
				
				oVO = setDataSet(
					sReservationNo			,  sStatus								,  sGroup								,
					sDuration				,  sStationRemark						,  sFlightNo							,
					sBonusProgramNo			,  sOriginAgencyNo						,  sOrigiName1							,
					sRateCode				,  sPickupDatetime						,  sPickupStationID						,
					sPickupName				,  sReturnDatetime						,  sReturnStationID						,
					sReturnName				,  sFirstDriverPhone					,  sFirstDriverMobile					,	
					sFirstDriverEmail		,  sFirstDriverName1					,  sFirstDriverName2					,	
					sFirstDriverCountry		,  sForbiInsCode						,  sForbiInsName						,	
					sForbiInsBooked			,  sForbiInsCharged						,  sForbiInsPrice						,	
					sForbiExtCode			,  sForbiExtName						,  sForbiExtBooked						,
					sForbiExtCharged		,  sForbiExtPrice						,  sIncInsCode							,
					sIncInsName				,  sIncInsBooked						,  sIncInsCharged						,	
					sIncInsPrice			,  sIncExtCode							,  sIncExtName							,
					sIncExtBooked			,  sIncExtCharged						,  sIncExtPrice							,
					sBooExtCode				,  sBooExtName							,  sBooExtBooked						,	
					sBooExtCharged			,  sBooExtPriceCurrency					,  sBooExtPriceNetAmount				,	
					sBooExtPriceVatPercent	,  sBooInsCode							,  sBooInsName							,
					sBooInsBooked			,  sBooInsCharged						,  sBooInsPriceCurrency					,
					sBooInsPriceNetAmount	,  sBooInsPriceVatPercent				,  sPaymentType							,
					sTotlDueAmountNode		,  dTotlGrossAmountNode					,  dTotlVatAmountNode					,
					sTotlCurrencyNode		,  dTotlNetAmountNode					,  sTotlVatPercentNode					,
					sRatePrepaid
				);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			httpConn.disconnect();
		}
		return oVO;
	}
	
	/**
	 * 예약번호 수신
	 * @param strStationId
	 * @param strTermDay
	 * @param strUserID
	 * @return
	 * @throws Exception
	 */
	public boolean requestReservaionNoList(int strTermDay)  {
		HttpURLConnection httpConn 	= null; // 연결용 커넥션
		URL url 					= null;
		boolean isTrue				= true;
		Connection conn 			= null;
		DBConnection dbConn			= new DBConnection();
		
		PreparedStatement pstmt 	= null;
		
		try {
			conn = dbConn.getDBConnection();
			pstmt = conn.prepareStatement(InboundInsert());
			
			String sUrl = "https://webservices.sixt.de/webservices/franchise/prod/soap_1.03";
			String uri  = "http://webservices.sixt.de/webservices/franchise/1.03";
			
			url = new URL(sUrl);
			
			httpConn = (HttpURLConnection) url.openConnection();
			
			httpConn.setDoOutput(true);
			
			httpConn.setRequestProperty("Authorization", "Basic " + "ZnJhbmNoaXNlOkVwdXdhNFViZXMzMA");
				
			OutputStream wr = httpConn.getOutputStream();
			
			wr.write(getXMLReservaion(strTermDay).getBytes("utf-8"));
			
			wr.flush();
			wr.close();
				
			String inputLine = null;
			String tmpLine = "";
			BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
			
			while ((inputLine = in.readLine()) != null) {
				tmpLine = tmpLine + inputLine;
			}
			
			in.close();
			
			if (httpConn.getResponseCode() == 200) {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				
				dbf.setNamespaceAware(true);
				    
			    DocumentBuilder db = dbf.newDocumentBuilder();
				    
			    StringReader is = new StringReader(tmpLine);
				    
			    Document doc = db.parse(new org.xml.sax.InputSource(is));
				    
			    NodeList nodeList = doc.getElementsByTagNameNS(uri, "reservationNo");
			    
			    for (int i = 0; i < nodeList.getLength(); i++) {
			    	Node node = nodeList.item(i);
			    	
			    	String xmlReserNo = getTextContent(node);
			    	
			    	if (!"".equals(xmlReserNo) && xmlReserNo != null) {
			    		try {
			    			
			    			pstmt.setString(1, xmlReserNo);
			    			pstmt.setString(2, xmlReserNo);
			    			
			    			pstmt.addBatch();
			    			
			    			if( i % 100 == 0) {	// 1000
								pstmt.executeBatch();
								conn.commit();
							}
						} catch (Exception e) {
							isTrue = false;
							e.printStackTrace();
						} finally {
						}
			    	}
			    }	// for end
			    
			    pstmt.executeBatch();
				conn.commit();
			} else {
				isTrue = false;
			}
		} catch (Exception e) {
			
		} finally {
			
		}
		return isTrue;
	}

	/**
	 *	인바운드 예약번호 상세정보 수신 
	 *	kjd
	 */
	public void getInboundDetl() {
		Connection conn 			= null;
		DBConnection dbConn			= new DBConnection();
		
		PreparedStatement pstmt 			= null;
		PreparedStatement pstmtInboundDetl 	= null;
		PreparedStatement pstmtRequest		= null;
		PreparedStatement pstmtMember		= null;
		PreparedStatement pstmtGenerResrv	= null;
	
		
		ResultSet rs						= null;
		CobraDataSet cDataSet 				= null;

		int detlCnt 		= 0;
		int requestCnt 		= 0;
		int memberCnt		= 0;
		int generResrvCnt 	= 0;
		
		try {
			conn 				= dbConn.getDBConnection();
			
			pstmt 				= conn.prepareStatement(queryInbound());
			pstmtInboundDetl 	= conn.prepareStatement(setLoaderSql());
			pstmtRequest 		= conn.prepareStatement(setLoaderSqlRequest());
			pstmtMember 		= conn.prepareStatement(setLoaderSqlMember());
			pstmtGenerResrv 	= conn.prepareStatement(setLoaderSqlInbound());
			
			rs = pstmt.executeQuery();
			
			while ( rs.next() ) {
				String rsResrvNo = rs.getString("RESRV_NO");
				
				if ( !"".equals(rsResrvNo) && rsResrvNo != null ) {
					System.out.println("인바운드예약번호|" + rsResrvNo + "|");
					// Inbound 상세예약정보 수신
					CobraReservationDetl cobraVO = requestReservaionDetl(rsResrvNo);
					
					if ( cobraVO != null ) {
						cDataSet = setCobraDataSet(cobraVO);
						
						pstmtInboundDetl = setPreparedStatement(cobraVO, pstmtInboundDetl, rsResrvNo, cDataSet);
						pstmtInboundDetl.addBatch();
						
						if ( (detlCnt > 0 ) && (detlCnt % 50 == 0 ) ) {
							logger.info(detlCnt + " 건 처리중" );
							pstmtInboundDetl.executeBatch();
							conn.commit();
						}
						
						pstmtGenerResrv = setPreparedStatementGenerResrv(cobraVO, pstmtGenerResrv, rsResrvNo, cDataSet, generResrvCnt);
						pstmtGenerResrv.addBatch();
						
						if ( (generResrvCnt > 0) && (generResrvCnt % 50 == 0) ) {
							logger.info("내륙 + 인바운드" + generResrvCnt + " 건 처리중" );
							pstmtGenerResrv.executeBatch();
							conn.commit();
						}
						
						
						if ("RQ".equals(cobraVO.getStatus()) || "ARQ".equals(cobraVO.getStatus())) {
							pstmtRequest = setPreparedStatementRequest(cobraVO, pstmtRequest, rsResrvNo, cDataSet);
							pstmtRequest.addBatch();
							
							if ( (requestCnt > 0 ) && (requestCnt % 50 == 0 ) ) {
								logger.info(requestCnt + " 건 처리중" );
								pstmtRequest.executeBatch();
								conn.commit();
							}
						}
						
						
						if ( mSearchUser(cobraVO.getFirstDriverName1(), cobraVO.getFirstDriverName2()) <= 0) {
							pstmtMember = setPreparedStatementMember(pstmtMember, cobraVO.getFirstDriverName1() + " " + cobraVO.getFirstDriverName2());
							
							pstmtMember.addBatch();
							
							if ( (memberCnt > 0 ) && (memberCnt % 50 == 0 ) ) {
								logger.info(memberCnt + " 건 처리중" );
								pstmtMember.executeBatch();
								conn.commit();
							}
						}
					}
				}
				detlCnt++;
				requestCnt++;
				memberCnt++;
				generResrvCnt++;
				
			} // while end
			
			logger.info(detlCnt + " 건 처리중");
			logger.info("내륙 + 인바운드:" + generResrvCnt + " 건 처리중");
			logger.info(requestCnt + " 건 처리중");
			logger.info(memberCnt + " 건 처리중");
			
			pstmtInboundDetl.executeBatch();
			pstmtGenerResrv.executeBatch();
			pstmtRequest.executeBatch();
			pstmtMember.executeBatch();
			
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) { pstmt.close(); }
				if (pstmtInboundDetl != null) { pstmtInboundDetl.close(); }
				if (pstmtRequest != null) { pstmtRequest.close(); }
				if (pstmtMember != null) { pstmtMember.close(); }
				if (pstmtGenerResrv != null) { pstmtGenerResrv.close(); }
				if (conn  != null) { conn.close(); }
			} catch (Exception e) {
				
			}
		}
	}
	
	public CobraDataSet setCobraDataSet(CobraReservationDetl oVO) {
		CobraDataSet cDataSet = new CobraDataSet();
		
		cDataSet.setTmpArrayBooExtCode(oVO.getBooExtCode().split("→"));
		cDataSet.setTmpArrayBooExtName(oVO.getBooExtName().split("→"));
		cDataSet.setTmpArrayBooExtBooked(oVO.getBooExtBooked().split("→"));
		cDataSet.setTmpArrayBooExtCharged(oVO.getBooExtCharged().split("→"));
		cDataSet.setTmpArrayBooExtPriceCurrency(oVO.getBooExtPriceCurrency().split("→"));
		cDataSet.setTmpArrayBooExtPriceNetAmount(oVO.getBooExtPriceNetAmount().split("→"));
		cDataSet.setTmpArrayBooExtPriceVatPercent(oVO.getBooExtPriceVatPercent().split("→"));
		
		for (int i = 0; i < cDataSet.getTmpArrayBooExtCode().length; i++ ) {
			
			if ("OW".equals(cDataSet.getTmpArrayBooExtCode()[i]) ) {
				cDataSet.setTmpCodeOW(cDataSet.getTmpArrayBooExtCode()[i]);
				cDataSet.setTmpNameOW(cDataSet.getTmpArrayBooExtName()[i]);
				cDataSet.setTmpBookedOW(cDataSet.getTmpArrayBooExtBooked()[i]);
				cDataSet.setTmpChargedOW(cDataSet.getTmpArrayBooExtCharged()[i]);
				cDataSet.setTmpBooExtPriceCurrencyOW(cDataSet.getTmpArrayBooExtPriceCurrency()[i]);
				cDataSet.setTmpBooExtPriceNetAmountOW(cDataSet.getTmpArrayBooExtPriceNetAmount()[i]);
				cDataSet.setTmpBooExtPriceVatPercentOW(cDataSet.getTmpArrayBooExtPriceVatPercent()[i]);

				if ( ("".equals(cDataSet.getTmpBookedOW().trim())) || (cDataSet.getTmpBookedOW() == null) ) { cDataSet.setTmpBookedOW("0"); }
				if ( ("".equals(cDataSet.getTmpChargedOW())) || (cDataSet.getTmpChargedOW() == null) ) { cDataSet.setTmpChargedOW("0"); }
				if ( ("".equals(cDataSet.getTmpBooExtPriceCurrencyOW())) || (cDataSet.getTmpBooExtPriceCurrencyOW() == null) ) { cDataSet.setTmpBooExtPriceCurrencyOW("0"); }
				if ( ("".equals(cDataSet.getTmpBooExtPriceNetAmountOW())) || (cDataSet.getTmpBooExtPriceNetAmountOW() == null) ) { cDataSet.setTmpBooExtPriceNetAmountOW("0");}
				if ( ("".equals(cDataSet.getTmpBooExtPriceVatPercentOW())) || (cDataSet.getTmpBooExtPriceVatPercentOW() == null) ) { cDataSet.setTmpBooExtPriceVatPercentOW("0");}				
			} else if ("O2".equals(cDataSet.getTmpArrayBooExtCode()[i])) {
				cDataSet.setTmpCodeO2(cDataSet.getTmpArrayBooExtCode()[i]);
				cDataSet.setTmpNameO2(cDataSet.getTmpArrayBooExtName()[i]);
				cDataSet.setTmpBookedO2(cDataSet.getTmpArrayBooExtBooked()[i]);
				cDataSet.setTmpChargedO2(cDataSet.getTmpArrayBooExtCharged()[i]);
				cDataSet.setTmpBooExtPriceCurrencyO2(cDataSet.getTmpArrayBooExtPriceCurrency()[i]);
				cDataSet.setTmpBooExtPriceNetAmountO2(cDataSet.getTmpArrayBooExtPriceNetAmount()[i]);
				cDataSet.setTmpBooExtPriceVatPercentO2(cDataSet.getTmpArrayBooExtPriceVatPercent()[i]);

				if ( ("".equals(cDataSet.getTmpBookedO2().trim())) || (cDataSet.getTmpBookedO2() == null) ) { cDataSet.setTmpBookedO2("0"); }
				if ( ("".equals(cDataSet.getTmpChargedO2())) || (cDataSet.getTmpChargedO2() == null) ) { cDataSet.setTmpChargedO2("0"); }
				if ( ("".equals(cDataSet.getTmpBooExtPriceCurrencyO2())) || (cDataSet.getTmpBooExtPriceCurrencyO2() == null) ) { cDataSet.setTmpBooExtPriceCurrencyO2("0"); }
				if ( ("".equals(cDataSet.getTmpBooExtPriceNetAmountO2())) || (cDataSet.getTmpBooExtPriceNetAmountO2() == null) ) { cDataSet.setTmpBooExtPriceNetAmountO2("0"); }
				if ( ("".equals(cDataSet.getTmpBooExtPriceVatPercentO2())) || (cDataSet.getTmpBooExtPriceVatPercentO2() == null) ) { cDataSet.setTmpBooExtPriceVatPercentO2("0");}
			} else if ("T".equals(cDataSet.getTmpArrayBooExtCode()[i])) {
				cDataSet.setTmpCodeT(cDataSet.getTmpArrayBooExtCode()[i]);
				cDataSet.setTmpNameT(cDataSet.getTmpArrayBooExtName()[i]);
				cDataSet.setTmpBookedT(cDataSet.getTmpArrayBooExtBooked()[i]);
				cDataSet.setTmpChargedT(cDataSet.getTmpArrayBooExtCharged()[i]);
				cDataSet.setTmpBooExtPriceCurrencyT(cDataSet.getTmpArrayBooExtPriceCurrency()[i]);
				cDataSet.setTmpBooExtPriceNetAmountT(cDataSet.getTmpArrayBooExtPriceNetAmount()[i]);
				cDataSet.setTmpBooExtPriceVatPercentT(cDataSet.getTmpArrayBooExtPriceVatPercent()[i]);

				if ( ("".equals(cDataSet.getTmpBookedT().trim())) || (cDataSet.getTmpBookedT() == null) ) { cDataSet.setTmpBookedT("0"); }
				if ( ("".equals(cDataSet.getTmpChargedT())) || (cDataSet.getTmpChargedT() == null) ) { cDataSet.setTmpChargedT("0"); }
				if ( ("".equals(cDataSet.getTmpBooExtPriceCurrencyT())) || (cDataSet.getTmpBooExtPriceCurrencyT() == null) ) { cDataSet.setTmpBooExtPriceCurrencyT("0"); }
				if ( ("".equals(cDataSet.getTmpBooExtPriceNetAmountT())) || (cDataSet.getTmpBooExtPriceNetAmountT() == null) ) { cDataSet.setTmpBooExtPriceNetAmountT("0");	}
				if ( ("".equals(cDataSet.getTmpBooExtPriceVatPercentT())) || (cDataSet.getTmpBooExtPriceVatPercentT() == null) ) { cDataSet.setTmpBooExtPriceVatPercentT("0"); }
			} else if ("NV".equals(cDataSet.getTmpArrayBooExtCode()[i])) {
				cDataSet.setTmpCodeNV(cDataSet.getTmpArrayBooExtCode()[i]);
				cDataSet.setTmpNameNV(cDataSet.getTmpArrayBooExtName()[i]);
				cDataSet.setTmpBookedNV(cDataSet.getTmpArrayBooExtBooked()[i]);
				cDataSet.setTmpChargedNV(cDataSet.getTmpArrayBooExtCharged()[i]);
				cDataSet.setTmpBooExtPriceCurrencyNV(cDataSet.getTmpArrayBooExtPriceCurrency()[i]);
				cDataSet.setTmpBooExtPriceNetAmountNV(cDataSet.getTmpArrayBooExtPriceNetAmount()[i]);
				cDataSet.setTmpBooExtPriceVatPercentNV(cDataSet.getTmpArrayBooExtPriceVatPercent()[i]);

				if ( ("".equals(cDataSet.getTmpBookedNV().trim())) || (cDataSet.getTmpBookedNV() == null) ) { cDataSet.setTmpBookedNV("0"); }
				if ( ("".equals(cDataSet.getTmpChargedNV())) || (cDataSet.getTmpChargedNV() == null) ) { cDataSet.setTmpChargedNV("0"); }
				if ( ("".equals(cDataSet.getTmpBooExtPriceCurrencyNV())) || (cDataSet.getTmpBooExtPriceCurrencyNV() == null) ) { cDataSet.setTmpBooExtPriceCurrencyNV("0"); }
				if ( ("".equals(cDataSet.getTmpBooExtPriceNetAmountNV())) || (cDataSet.getTmpBooExtPriceNetAmountNV() == null) ) { cDataSet.setTmpBooExtPriceNetAmountNV("0"); }
				if ( ("".equals(cDataSet.getTmpBooExtPriceVatPercentNV())) || (cDataSet.getTmpBooExtPriceVatPercentNV() == null) ) { cDataSet.setTmpBooExtPriceVatPercentNV("0");}
			} else if ("AD".equals(cDataSet.getTmpArrayBooExtCode()[i])) {
				cDataSet.setTmpCodeAD(cDataSet.getTmpArrayBooExtCode()[i]);
				cDataSet.setTmpNameAD(cDataSet.getTmpArrayBooExtName()[i]);
				cDataSet.setTmpBookedAD(cDataSet.getTmpArrayBooExtBooked()[i]);
				cDataSet.setTmpChargedAD(cDataSet.getTmpArrayBooExtCharged()[i]);
				cDataSet.setTmpBooExtPriceCurrencyAD(cDataSet.getTmpArrayBooExtPriceCurrency()[i]);
				cDataSet.setTmpBooExtPriceNetAmountAD(cDataSet.getTmpArrayBooExtPriceNetAmount()[i]);
				cDataSet.setTmpBooExtPriceVatPercentAD(cDataSet.getTmpArrayBooExtPriceVatPercent()[i]);

				if ( ("".equals(cDataSet.getTmpBookedAD().trim())) || (cDataSet.getTmpBookedAD() == null) ) { cDataSet.setTmpBookedAD("0"); }
				if ( ("".equals(cDataSet.getTmpChargedAD())) || (cDataSet.getTmpChargedAD() == null) ) { cDataSet.setTmpChargedAD("0"); }
				if ( ("".equals(cDataSet.getTmpBooExtPriceCurrencyAD())) || (cDataSet.getTmpBooExtPriceCurrencyAD() == null) ) { cDataSet.setTmpBooExtPriceCurrencyAD("0"); }
				if ( ("".equals(cDataSet.getTmpBooExtPriceNetAmountAD())) || (cDataSet.getTmpBooExtPriceNetAmountAD() == null) ) { cDataSet.setTmpBooExtPriceNetAmountAD("0"); }
				if ( ("".equals(cDataSet.getTmpBooExtPriceVatPercentAD())) || (cDataSet.getTmpBooExtPriceVatPercentAD() == null) ) { cDataSet.setTmpBooExtPriceVatPercentAD("0");}
			} else if ("X".equals(cDataSet.getTmpArrayBooExtCode()[i])) {
				cDataSet.setTmpCodeX(cDataSet.getTmpArrayBooExtCode()[i]);
				cDataSet.setTmpNameX(cDataSet.getTmpArrayBooExtName()[i]);
				cDataSet.setTmpBookedX(cDataSet.getTmpArrayBooExtBooked()[i]);
				cDataSet.setTmpChargedX(cDataSet.getTmpArrayBooExtCharged()[i]);
				cDataSet.setTmpBooExtPriceCurrencyX(cDataSet.getTmpArrayBooExtPriceCurrency()[i]);
				cDataSet.setTmpBooExtPriceNetAmountX(cDataSet.getTmpArrayBooExtPriceNetAmount()[i]);
				cDataSet.setTmpBooExtPriceVatPercentX(cDataSet.getTmpArrayBooExtPriceVatPercent()[i]);

				if ( ("".equals(cDataSet.getTmpBookedX().trim())) || (cDataSet.getTmpBookedX() == null) ) { cDataSet.setTmpBookedX("0"); }
				if ( ("".equals(cDataSet.getTmpChargedX())) || (cDataSet.getTmpChargedX() == null) ) { cDataSet.setTmpChargedX("0"); }
				if ( ("".equals(cDataSet.getTmpBooExtPriceCurrencyX())) || (cDataSet.getTmpBooExtPriceCurrencyX() == null) ) { cDataSet.setTmpBooExtPriceCurrencyX("0"); }
				if ( ("".equals(cDataSet.getTmpBooExtPriceNetAmountX())) || (cDataSet.getTmpBooExtPriceNetAmountX() == null) ) { cDataSet.setTmpBooExtPriceNetAmountX("0"); }
				if ( ("".equals(cDataSet.getTmpBooExtPriceVatPercentX())) || (cDataSet.getTmpBooExtPriceVatPercentX() == null) ) { cDataSet.setTmpBooExtPriceVatPercentX("0");}
			} else if ("Y".equals(cDataSet.getTmpArrayBooExtCode()[i])) {
				cDataSet.setTmpCodeY(cDataSet.getTmpArrayBooExtCode()[i]);
				cDataSet.setTmpNameY(cDataSet.getTmpArrayBooExtName()[i]);
				cDataSet.setTmpBookedY(cDataSet.getTmpArrayBooExtBooked()[i]);
				cDataSet.setTmpChargedY(cDataSet.getTmpArrayBooExtCharged()[i]);
				cDataSet.setTmpBooExtPriceCurrencyY(cDataSet.getTmpArrayBooExtPriceCurrency()[i]);
				cDataSet.setTmpBooExtPriceNetAmountY(cDataSet.getTmpArrayBooExtPriceNetAmount()[i]);
				cDataSet.setTmpBooExtPriceVatPercentY(cDataSet.getTmpArrayBooExtPriceVatPercent()[i]);

				if ( ("".equals(cDataSet.getTmpBookedY().trim())) || (cDataSet.getTmpBookedY() == null) ) { cDataSet.setTmpBookedY("0"); }
				if ( ("".equals(cDataSet.getTmpChargedY())) || (cDataSet.getTmpChargedY() == null) ) { cDataSet.setTmpChargedY("0"); }
				if ( ("".equals(cDataSet.getTmpBooExtPriceCurrencyY())) || (cDataSet.getTmpBooExtPriceCurrencyY() == null) ) { cDataSet.setTmpBooExtPriceCurrencyY("0"); }
				if ( ("".equals(cDataSet.getTmpBooExtPriceNetAmountY())) || (cDataSet.getTmpBooExtPriceNetAmountY() == null) ) { cDataSet.setTmpBooExtPriceNetAmountY("0"); }
				if ( ("".equals(cDataSet.getTmpBooExtPriceVatPercentY())) || (cDataSet.getTmpBooExtPriceVatPercentY() == null) ) { cDataSet.setTmpBooExtPriceVatPercentY("0");}
			} else if ("CS".equals(cDataSet.getTmpArrayBooExtCode()[i])) {
				cDataSet.setTmpCodeCS(cDataSet.getTmpArrayBooExtCode()[i]);
				cDataSet.setTmpNameCS(cDataSet.getTmpArrayBooExtName()[i]);
				cDataSet.setTmpBookedCS(cDataSet.getTmpArrayBooExtBooked()[i]);
				cDataSet.setTmpChargedCS(cDataSet.getTmpArrayBooExtCharged()[i]);
				cDataSet.setTmpBooExtPriceCurrencyCS(cDataSet.getTmpArrayBooExtPriceCurrency()[i]);
				cDataSet.setTmpBooExtPriceNetAmountCS(cDataSet.getTmpArrayBooExtPriceNetAmount()[i]);
				cDataSet.setTmpBooExtPriceVatPercentCS(cDataSet.getTmpArrayBooExtPriceVatPercent()[i]);

				if ( ("".equals(cDataSet.getTmpBookedCS().trim())) || (cDataSet.getTmpBookedCS() == null) ) { cDataSet.setTmpBookedCS("0"); }
				if ( ("".equals(cDataSet.getTmpChargedCS())) || (cDataSet.getTmpChargedCS() == null) ) { cDataSet.setTmpChargedCS("0"); }
				if ( ("".equals(cDataSet.getTmpBooExtPriceCurrencyCS())) || (cDataSet.getTmpBooExtPriceCurrencyCS() == null) ) { cDataSet.setTmpBooExtPriceCurrencyCS("0"); }
				if ( ("".equals(cDataSet.getTmpBooExtPriceNetAmountCS())) || (cDataSet.getTmpBooExtPriceNetAmountCS() == null) ) { cDataSet.setTmpBooExtPriceNetAmountCS("0"); }
				if ( ("".equals(cDataSet.getTmpBooExtPriceVatPercentCS())) || (cDataSet.getTmpBooExtPriceVatPercentCS() == null) ) { cDataSet.setTmpBooExtPriceVatPercentCS("0");}
			} else if ("PF".equals(cDataSet.getTmpArrayBooExtCode()[i])) {
				cDataSet.setTmpCodePF(cDataSet.getTmpArrayBooExtCode()[i]);
				cDataSet.setTmpNamePF(cDataSet.getTmpArrayBooExtName()[i]);
				cDataSet.setTmpBookedPF(cDataSet.getTmpArrayBooExtBooked()[i]);
				cDataSet.setTmpChargedPF(cDataSet.getTmpArrayBooExtCharged()[i]);
				cDataSet.setTmpBooExtPriceCurrencyPF(cDataSet.getTmpArrayBooExtPriceCurrency()[i]);
				cDataSet.setTmpBooExtPriceNetAmountPF(cDataSet.getTmpArrayBooExtPriceNetAmount()[i]);
				cDataSet.setTmpBooExtPriceVatPercentPF(cDataSet.getTmpArrayBooExtPriceVatPercent()[i]);

				if ( ("".equals(cDataSet.getTmpBookedPF().trim())) || (cDataSet.getTmpBookedPF() == null) ) { cDataSet.setTmpBookedPF("0"); }
				if ( ("".equals(cDataSet.getTmpChargedPF())) || (cDataSet.getTmpChargedPF() == null) ) { cDataSet.setTmpChargedPF("0"); }
				if ( ("".equals(cDataSet.getTmpBooExtPriceCurrencyPF())) || (cDataSet.getTmpBooExtPriceCurrencyPF() == null) ) { cDataSet.setTmpBooExtPriceCurrencyPF("0"); }
				if ( ("".equals(cDataSet.getTmpBooExtPriceNetAmountPF())) || (cDataSet.getTmpBooExtPriceNetAmountPF() == null) ) { cDataSet.setTmpBooExtPriceNetAmountPF("0"); }
				if ( ("".equals(cDataSet.getTmpBooExtPriceVatPercentPF())) || (cDataSet.getTmpBooExtPriceVatPercentPF() == null) ) { cDataSet.setTmpBooExtPriceVatPercentPF("0");}
			} else if ("SC".equals(cDataSet.getTmpArrayBooExtCode()[i])) {
				
				cDataSet.setTmpCodeSC(cDataSet.getTmpArrayBooExtCode()[i]);
				cDataSet.setTmpNameSC(cDataSet.getTmpArrayBooExtName()[i]);
				cDataSet.setTmpBookedSC(cDataSet.getTmpArrayBooExtBooked()[i]);
				cDataSet.setTmpChargedSC(cDataSet.getTmpArrayBooExtCharged()[i]);
				cDataSet.setTmpBooExtPriceCurrencySC(cDataSet.getTmpArrayBooExtPriceCurrency()[i]);
				cDataSet.setTmpBooExtPriceNetAmountSC(cDataSet.getTmpArrayBooExtPriceNetAmount()[i]);
				cDataSet.setTmpBooExtPriceVatPercentSC(cDataSet.getTmpArrayBooExtPriceVatPercent()[i]);

				if ( ("".equals(cDataSet.getTmpBookedSC().trim())) || (cDataSet.getTmpBookedSC() == null) ) { cDataSet.setTmpBookedSC("0"); }
				if ( ("".equals(cDataSet.getTmpChargedSC())) || (cDataSet.getTmpChargedSC() == null) ) { cDataSet.setTmpChargedSC("0"); }
				if ( ("".equals(cDataSet.getTmpBooExtPriceCurrencySC())) || (cDataSet.getTmpBooExtPriceCurrencySC() == null) ) { cDataSet.setTmpBooExtPriceCurrencySC("0"); }
				if ( ("".equals(cDataSet.getTmpBooExtPriceNetAmountSC())) || (cDataSet.getTmpBooExtPriceNetAmountSC() == null) ) { cDataSet.setTmpBooExtPriceNetAmountSC("0"); }
				if ( ("".equals(cDataSet.getTmpBooExtPriceVatPercentSC())) || (cDataSet.getTmpBooExtPriceVatPercentSC() == null) ) { cDataSet.setTmpBooExtPriceVatPercentSC("0");}
			}
		} // for end 
		return cDataSet;
	}
	
	
	public PreparedStatement setPreparedStatementGenerResrv(CobraReservationDetl oVO, PreparedStatement pstmt, String tmpResrvNo, CobraDataSet cDataSet, int idex) {
		String tmpRentBranCd 	= getInboundBranCd(oVO.getPickupStationID());
		String tmpCarryBranCd	= getInboundBranCd(oVO.getReturnStationID());
		String tmpStatus 		= "";
		
		String tmpRentDay		= oVO.getPickupDatetime().replaceAll("-", "").substring(0, 8);
		String tmpRentHmin		= oVO.getPickupDatetime().replaceAll("-", "").replaceAll(":", "").substring(9, 13);
		String tmpCarryDay		= oVO.getReturnDatetime().replaceAll("-", "").substring(0, 8);
		String tmpCarryHmin		= oVO.getReturnDatetime().replaceAll("-", "").replaceAll(":", "").substring(9, 13);
				
		String tmpInsurCd = "";
		double dInsurAmount = 0.0;
		
		if ("RS".equals(oVO.getStatus())) {
			tmpStatus = "02";
		} else if ("RA".equals(oVO.getStatus())) {
			tmpStatus = "05";
		} else {
			tmpStatus = "04";
		}
		
		try {
			
			//pstmt.setString(1, oVO.getReservationNo());
			pstmt.setString(1, tmpResrvNo);
			
			pstmt.setString(2, "04");
			
			if (!"2099".equals(oVO.getPickupStationID()) ) {
				pstmt.setString(3, tmpRentBranCd);
			} else {
				pstmt.setString(3, oVO.getPickupStationID());
			}
			
			pstmt.setString(4, "00000001");
			pstmt.setString(5, "01");
			pstmt.setString(6, tmpStatus);
			pstmt.setString(7, oVO.getFirstDriverName1() + " " + oVO.getFirstDriverName2());
			pstmt.setString(8, oVO.getFirstDriverMobile());
			pstmt.setString(9, oVO.getFirstDriverPhone());
			// kjdkjd
			
			
			pstmt.setString(10, tmpRentBranCd);
			pstmt.setString(11, tmpCarryBranCd);
			
			pstmt.setString(12, tmpRentDay);
			pstmt.setString(13, tmpRentHmin);
			pstmt.setString(14, tmpCarryDay);
			pstmt.setString(15, tmpCarryHmin);
			
			pstmt.setString(16, cDataSet.getTmpCodeT());
			pstmt.setString(17, cDataSet.getTmpNameT());
			pstmt.setString(18, cDataSet.getTmpBookedT());
			pstmt.setString(19, cDataSet.getTmpChargedT());
			
			if ("".equals(cDataSet.getTmpBooExtPriceNetAmountT())) { cDataSet.setTmpBooExtPriceNetAmountT("0"); }
			pstmt.setInt(20, (int)Math.floor(Double.parseDouble(cDataSet.getTmpBooExtPriceNetAmountT())));
			
			
			
			pstmt.setString(21, cDataSet.getTmpCodeOW());
			pstmt.setString(22, cDataSet.getTmpNameOW());
			pstmt.setString(23, cDataSet.getTmpBookedOW());
			pstmt.setString(24, cDataSet.getTmpChargedOW());
			if ("".equals(cDataSet.getTmpBooExtPriceNetAmountOW())) { cDataSet.setTmpBooExtPriceNetAmountOW("0"); }
			pstmt.setInt(25, (int)Math.floor(Double.parseDouble(cDataSet.getTmpBooExtPriceNetAmountOW())));
			
			pstmt.setString(26, cDataSet.getTmpCodeAD());
			pstmt.setString(27, cDataSet.getTmpNameAD().replaceAll("'", ""));
			pstmt.setString(28, cDataSet.getTmpBookedAD());
			pstmt.setString(29, cDataSet.getTmpChargedAD());
			if ("".equals(cDataSet.getTmpBooExtPriceNetAmountAD())) { cDataSet.setTmpBooExtPriceNetAmountAD("0"); }
			pstmt.setInt(30, (int)Math.floor(Double.parseDouble(cDataSet.getTmpBooExtPriceNetAmountAD())));
			
			
			pstmt.setString(31, cDataSet.getTmpCodeNV());
			pstmt.setString(32, cDataSet.getTmpNameNV());
			pstmt.setString(33, cDataSet.getTmpBookedNV());
			pstmt.setString(34, cDataSet.getTmpChargedNV());
			if ("".equals(cDataSet.getTmpBooExtPriceNetAmountNV())) { cDataSet.setTmpBooExtPriceNetAmountNV("0"); }
			pstmt.setInt(35, (int)Math.floor(Double.parseDouble(cDataSet.getTmpBooExtPriceNetAmountNV())));
			
			pstmt.setString(36, cDataSet.getTmpCodeCS());
			pstmt.setString(37, cDataSet.getTmpNameCS());
			pstmt.setString(38, cDataSet.getTmpBookedCS());
			pstmt.setString(39, cDataSet.getTmpChargedCS());
			if ("".equals(cDataSet.getTmpBooExtPriceNetAmountCS())) { cDataSet.setTmpBooExtPriceNetAmountCS("0"); }
			pstmt.setInt(40, (int)Math.floor(Double.parseDouble(cDataSet.getTmpBooExtPriceNetAmountCS())));
			
			
			pstmt.setString(41, cDataSet.getTmpCodeX());
			pstmt.setString(42, cDataSet.getTmpNameX());
			pstmt.setString(43, cDataSet.getTmpBookedX());
			pstmt.setString(44, cDataSet.getTmpChargedX());
			if ("".equals(cDataSet.getTmpBooExtPriceNetAmountX())) { cDataSet.setTmpBooExtPriceNetAmountX("0"); }
			pstmt.setInt(45, (int)Math.floor(Double.parseDouble(cDataSet.getTmpBooExtPriceNetAmountX())));
			
			pstmt.setString(46, cDataSet.getTmpCodeO2());
			pstmt.setString(47, cDataSet.getTmpNameO2());
			pstmt.setString(48, cDataSet.getTmpBookedO2());
			pstmt.setString(49, cDataSet.getTmpChargedO2());
			if ("".equals(cDataSet.getTmpBooExtPriceNetAmountO2())) { cDataSet.setTmpBooExtPriceNetAmountO2("0"); }
			pstmt.setInt(50, (int)Math.floor(Double.parseDouble(cDataSet.getTmpBooExtPriceNetAmountO2())));
			
			pstmt.setString(51, cDataSet.getTmpCodeY());
			pstmt.setString(52, cDataSet.getTmpNameY());
			pstmt.setString(53, cDataSet.getTmpBookedY());
			pstmt.setString(54, cDataSet.getTmpChargedY());
			if ("".equals(cDataSet.getTmpBooExtPriceNetAmountY())) { cDataSet.setTmpBooExtPriceNetAmountY("0"); }
			pstmt.setInt(55, (int)Math.floor(Double.parseDouble(cDataSet.getTmpBooExtPriceNetAmountY())));
			
			pstmt.setString(56, cDataSet.getTmpCodePF());
			pstmt.setString(57, cDataSet.getTmpNamePF());
			pstmt.setString(58, cDataSet.getTmpBookedPF());
			pstmt.setString(59, cDataSet.getTmpChargedPF());
			if ("".equals(cDataSet.getTmpBooExtPriceNetAmountPF())) { cDataSet.setTmpBooExtPriceNetAmountPF("0"); }
			pstmt.setInt(60, (int)Math.floor(Double.parseDouble(cDataSet.getTmpBooExtPriceNetAmountPF())));
			
			pstmt.setString(61, cDataSet.getTmpCodeSC());
			pstmt.setString(62, cDataSet.getTmpNameSC());
			pstmt.setString(63, cDataSet.getTmpBookedSC());
			pstmt.setString(64, cDataSet.getTmpChargedSC());
			if ("".equals(cDataSet.getTmpBooExtPriceNetAmountSC())) { cDataSet.setTmpBooExtPriceNetAmountSC("0"); }
			pstmt.setInt(65, (int)Math.floor(Double.parseDouble(cDataSet.getTmpBooExtPriceNetAmountSC())));
			
			
			if ("".equals(oVO.getBooInsCode()) ){
				tmpInsurCd = "";
				dInsurAmount = 0.0;
			} else {
				tmpInsurCd = "30";
				dInsurAmount = Double.parseDouble(oVO.getBooInsCharged()) * Double.parseDouble(oVO.getBooInsPriceNetAmount());
			}
			
			pstmt.setString(66, tmpInsurCd);
			pstmt.setDouble(67, dInsurAmount);
			
			
			pstmt.setInt(68, (int)Math.floor(Double.parseDouble(oVO.getTotlGrossAmountNode())));
			pstmt.setInt(69, (int)Math.floor(Double.parseDouble(oVO.getTotlNetAmountNode())) );
			pstmt.setInt(70, (int)Math.floor(Double.parseDouble(oVO.getTotlVatAmountNode())));
			pstmt.setString(71, oVO.getRatePrepaid());
			pstmt.setString(72, "00000001");
			//////////////////////////// update end
			
			
			
			
			
			
			//pstmt.setString(73, oVO.getReservationNo());
			pstmt.setString(73, tmpResrvNo);
			
			pstmt.setString(74, "04");
			
			
			if (!"2099".equals(oVO.getPickupStationID()) ) {
				pstmt.setString(75, tmpRentBranCd);
			} else {
				pstmt.setString(75, oVO.getPickupStationID());
			}
			
			pstmt.setString(76, "00000001");
			pstmt.setString(77, "01");
			pstmt.setString(78, tmpStatus);
			pstmt.setString(79, getDateString("yyyymmdd") );
			pstmt.setString(80, getShortTimeString().substring(0, 4));
			
			pstmt.setString(81, oVO.getFirstDriverName1() + " " + oVO.getFirstDriverName2());
			pstmt.setString(82, "2");
			
			pstmt.setString(83, oVO.getFirstDriverMobile());
			pstmt.setString(84, oVO.getFirstDriverPhone());
			
			pstmt.setString(85, tmpRentBranCd);
			pstmt.setString(86, tmpCarryBranCd);
			pstmt.setString(87, tmpRentDay);
			pstmt.setString(88, tmpRentHmin);
			pstmt.setString(89, tmpCarryDay);
			pstmt.setString(90, tmpCarryHmin);
		
			
			
			pstmt.setString(91, cDataSet.getTmpCodeT());
			pstmt.setString(92, cDataSet.getTmpNameT());
			pstmt.setString(93, cDataSet.getTmpBookedT());
			pstmt.setString(94, cDataSet.getTmpChargedT());
			pstmt.setInt(95, (int)Math.floor(Double.parseDouble(cDataSet.getTmpBooExtPriceNetAmountT())));
			

			pstmt.setString(96, cDataSet.getTmpCodeOW());
			pstmt.setString(97, cDataSet.getTmpNameOW().replaceAll("'", ""));
			pstmt.setString(98, cDataSet.getTmpBookedOW());
			pstmt.setString(99, cDataSet.getTmpChargedOW());
			pstmt.setInt(100, (int)Math.floor(Double.parseDouble(cDataSet.getTmpBooExtPriceNetAmountOW())));
			
			
			pstmt.setString(101, cDataSet.getTmpCodeAD());
			pstmt.setString(102, cDataSet.getTmpNameAD().replaceAll("'", ""));
			pstmt.setString(103, cDataSet.getTmpBookedAD());
			pstmt.setString(104, cDataSet.getTmpChargedAD());
			pstmt.setInt(105, (int)Math.floor(Double.parseDouble(cDataSet.getTmpBooExtPriceNetAmountAD())));
			
			pstmt.setString(106, cDataSet.getTmpCodeNV());
			pstmt.setString(107, cDataSet.getTmpNameNV());
			pstmt.setString(108, cDataSet.getTmpBookedNV());
			pstmt.setString(109, cDataSet.getTmpChargedNV());
			pstmt.setInt(110, (int)Math.floor(Double.parseDouble(cDataSet.getTmpBooExtPriceNetAmountNV())));
			
			pstmt.setString(111, cDataSet.getTmpCodeCS());
			pstmt.setString(112, cDataSet.getTmpNameCS());
			pstmt.setString(113, cDataSet.getTmpBookedCS());
			pstmt.setString(114, cDataSet.getTmpChargedCS());
			pstmt.setInt(115, (int)Math.floor(Double.parseDouble(cDataSet.getTmpBooExtPriceNetAmountCS())));
			
			pstmt.setString(116, cDataSet.getTmpCodeX());
			pstmt.setString(117, cDataSet.getTmpNameX());
			pstmt.setString(118, cDataSet.getTmpBookedX());
			pstmt.setString(119, cDataSet.getTmpChargedX());
			pstmt.setInt(120, (int)Math.floor(Double.parseDouble(cDataSet.getTmpBooExtPriceNetAmountX())));
			
			pstmt.setString(121, cDataSet.getTmpCodeO2());
			pstmt.setString(122, cDataSet.getTmpNameO2());
			pstmt.setString(123, cDataSet.getTmpBookedO2());
			pstmt.setString(124, cDataSet.getTmpChargedO2());
			pstmt.setInt(125, (int)Math.floor(Double.parseDouble(cDataSet.getTmpBooExtPriceNetAmountO2())));
			
			pstmt.setString(126, cDataSet.getTmpCodeY());
			pstmt.setString(127, cDataSet.getTmpNameY());
			pstmt.setString(128, cDataSet.getTmpBookedY());
			pstmt.setString(129, cDataSet.getTmpChargedY());
			pstmt.setInt(130, (int)Math.floor(Double.parseDouble(cDataSet.getTmpBooExtPriceNetAmountY())));
			
			pstmt.setString(131, cDataSet.getTmpCodePF());
			pstmt.setString(132, cDataSet.getTmpNamePF());
			pstmt.setString(133, cDataSet.getTmpBookedPF());
			pstmt.setString(134, cDataSet.getTmpChargedPF());
			pstmt.setInt(135, (int)Math.floor(Double.parseDouble(cDataSet.getTmpBooExtPriceNetAmountPF())));
			
			pstmt.setString(136, cDataSet.getTmpCodeSC());
			pstmt.setString(137, cDataSet.getTmpNameSC());
			pstmt.setString(138, cDataSet.getTmpBookedSC());
			pstmt.setString(139, cDataSet.getTmpChargedSC());
			pstmt.setInt(140, (int)Math.floor(Double.parseDouble(cDataSet.getTmpBooExtPriceNetAmountSC())));
			
			
			pstmt.setString(141, tmpInsurCd);
			pstmt.setDouble(142, dInsurAmount);
			
			
			pstmt.setInt(143, (int)Math.floor(Double.parseDouble(oVO.getTotlGrossAmountNode())));
			pstmt.setInt(144, (int)Math.floor(Double.parseDouble(oVO.getTotlNetAmountNode())) );
			pstmt.setInt(145, (int)Math.floor(Double.parseDouble(oVO.getTotlVatAmountNode())));
			pstmt.setString(146, oVO.getRatePrepaid());
			pstmt.setString(147, "00000001");
			
			
			
			
			
			///////////////////////// insert 끝
			
			pstmt.setString(148, oVO.getReservationNo());
			
			pstmt.setString(149, "04");
			
			if (!"2099".equals(oVO.getPickupStationID()) ) {
				pstmt.setString(150, tmpRentBranCd);
			} else {
				pstmt.setString(150, oVO.getPickupStationID());
			}
			
			pstmt.setString(151, "00000000");
			pstmt.setString(152, "01");
			pstmt.setString(153, tmpStatus);
			
			pstmt.setString(154, oVO.getFirstDriverName1() + " " + oVO.getFirstDriverName2());
			pstmt.setString(155, "2");
			pstmt.setString(156, "01");
			
			pstmt.setString(157, oVO.getFirstDriverMobile());
			pstmt.setString(158, oVO.getFirstDriverPhone());
			

			pstmt.setString(159, tmpRentBranCd);
			pstmt.setString(160, tmpCarryBranCd);

			pstmt.setString(161, tmpRentDay);
			pstmt.setString(162, tmpRentHmin);
			pstmt.setString(163, tmpCarryDay);
			pstmt.setString(164, tmpCarryHmin);
			
			pstmt.setString(165, "00000001");
			////////////////// update 끝
				
			
			//pstmt.setString(166, oVO.getReservationNo());
			pstmt.setString(166, tmpResrvNo);
			
			pstmt.setString(167, "04");
			
			if (!"2099".equals(oVO.getPickupStationID()) ) {
				pstmt.setString(168, tmpRentBranCd);
			} else {
				pstmt.setString(168, oVO.getPickupStationID());
			}
			
			pstmt.setString(169, "00000000");
			pstmt.setString(170, "01");
			pstmt.setString(171, tmpStatus);
			
			
			pstmt.setString(172, oVO.getFirstDriverName1() + " " + oVO.getFirstDriverName2());
			pstmt.setString(173, "2");
			pstmt.setString(174, "01");
			
			pstmt.setString(175, oVO.getFirstDriverMobile());
			pstmt.setString(176, oVO.getFirstDriverPhone());
			
			
			
			pstmt.setString(177, tmpRentBranCd);
			pstmt.setString(178, tmpCarryBranCd);
			
			pstmt.setString(179, tmpRentDay);
			pstmt.setString(180, tmpRentHmin);
			pstmt.setString(181, tmpCarryDay);
			pstmt.setString(182, tmpCarryHmin);
			pstmt.setString(183, "00000001");
			
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString());
		}
		
		System.out.println("idex:" + idex);
		return pstmt;
	}
	
	
	
	public PreparedStatement setPreparedStatement(CobraReservationDetl oVO, PreparedStatement pstmt, String tmpResrvNo, CobraDataSet cDataSet) {
		try {
			pstmt.setString(1, oVO.getReservationNo());
			pstmt.setString(2, oVO.getReservationNo());
			pstmt.setString(3, oVO.getStatus());
			pstmt.setString(4, oVO.getGroup());
			pstmt.setString(5, oVO.getDuration());
			pstmt.setString(6, oVO.getStationRemark().replace("'", ""));
			pstmt.setString(7, oVO.getFlightNo());
			pstmt.setString(8, oVO.getBonusProgramNo());
			pstmt.setString(9, oVO.getOriginAgencyNo());
			pstmt.setString(10, oVO.getOrigiName1().replace("'", "")	);
			pstmt.setString(11, oVO.getRateCode() );
			pstmt.setString(12, oVO.getPickupDatetime());
			pstmt.setString(13, oVO.getPickupStationID());
			pstmt.setString(14, oVO.getPickupName());
			pstmt.setString(15, oVO.getReturnDatetime());
			pstmt.setString(16, oVO.getReturnStationID());
			pstmt.setString(17, oVO.getReturnName());
			pstmt.setString(18, oVO.getFirstDriverPhone());
			pstmt.setString(19, oVO.getFirstDriverMobile());
			pstmt.setString(20, oVO.getFirstDriverEmail());
			
			pstmt.setString(21, oVO.getFirstDriverName1());
			pstmt.setString(22, oVO.getFirstDriverName2());
			pstmt.setString(23, oVO.getFirstDriverCountry());
			pstmt.setString(24, oVO.getForbiInsCode());
			pstmt.setString(25, oVO.getForbiInsName());
			pstmt.setString(26, oVO.getForbiInsBooked());
			pstmt.setString(27, oVO.getForbiInsCharged());
			pstmt.setString(28, oVO.getForbiInsPrice());
			pstmt.setString(29, oVO.getForbiExtCode());
			pstmt.setString(30, oVO.getForbiExtName());
			pstmt.setString(31, oVO.getForbiExtBooked());
			pstmt.setString(32, oVO.getForbiExtCharged());
			pstmt.setString(33, oVO.getForbiExtPrice());
			pstmt.setString(34, oVO.getIncInsCode());
			pstmt.setString(35, oVO.getIncInsName());
			pstmt.setString(36, oVO.getIncInsBooked());
			pstmt.setString(37, oVO.getIncInsCharged());
			pstmt.setString(38, oVO.getIncInsPrice());
			pstmt.setString(39, oVO.getIncExtCode());
			pstmt.setString(40, oVO.getIncExtName());
			
			
			pstmt.setString(41, oVO.getIncExtBooked());
			pstmt.setString(42, oVO.getIncExtCharged());
			pstmt.setString(43, oVO.getIncExtPrice());
			
			
			pstmt.setString(44, cDataSet.getTmpCodeT());
			pstmt.setString(45, cDataSet.getTmpNameT());
			pstmt.setString(46, cDataSet.getTmpBookedT());
			pstmt.setString(47, cDataSet.getTmpChargedT());
			pstmt.setString(48, cDataSet.getTmpBooExtPriceCurrencyT());
			pstmt.setString(49, cDataSet.getTmpBooExtPriceNetAmountT());
			pstmt.setString(50, cDataSet.getTmpBooExtPriceVatPercentT());
			pstmt.setString(51, cDataSet.getTmpCodeOW());
			pstmt.setString(52, cDataSet.getTmpNameOW());
			pstmt.setString(53, cDataSet.getTmpBookedOW());
			pstmt.setString(54, cDataSet.getTmpChargedOW());
			pstmt.setString(55, cDataSet.getTmpBooExtPriceCurrencyOW());
			pstmt.setString(56, cDataSet.getTmpBooExtPriceNetAmountOW());
			pstmt.setString(57, cDataSet.getTmpBooExtPriceVatPercentOW());
			pstmt.setString(58, cDataSet.getTmpCodeAD());
			pstmt.setString(59, cDataSet.getTmpNameAD().replace("'", ""));
			
			pstmt.setString(60, cDataSet.getTmpBookedAD());
			pstmt.setString(61, cDataSet.getTmpChargedAD());
			pstmt.setString(62, cDataSet.getTmpBooExtPriceCurrencyAD());
			pstmt.setString(63, cDataSet.getTmpBooExtPriceNetAmountAD());
			pstmt.setString(64, cDataSet.getTmpBooExtPriceVatPercentAD());
			pstmt.setString(65, cDataSet.getTmpCodeNV());
			pstmt.setString(66, cDataSet.getTmpNameNV());
			pstmt.setString(67, cDataSet.getTmpBookedNV());
			pstmt.setString(68, cDataSet.getTmpChargedNV());
			pstmt.setString(69, cDataSet.getTmpBooExtPriceCurrencyNV());
			pstmt.setString(70, cDataSet.getTmpBooExtPriceNetAmountNV());
			pstmt.setString(71, cDataSet.getTmpBooExtPriceVatPercentNV());
			pstmt.setString(72, cDataSet.getTmpCodeCS());
			pstmt.setString(73, cDataSet.getTmpNameCS());
			pstmt.setString(74, cDataSet.getTmpBookedCS());
			pstmt.setString(75, cDataSet.getTmpChargedCS());
			pstmt.setString(76, cDataSet.getTmpBooExtPriceCurrencyCS());
			pstmt.setString(77, cDataSet.getTmpBooExtPriceNetAmountCS());
			pstmt.setString(78, cDataSet.getTmpBooExtPriceVatPercentCS());
			pstmt.setString(79, cDataSet.getTmpCodeX());
			pstmt.setString(80, cDataSet.getTmpNameX());
			pstmt.setString(81, cDataSet.getTmpBookedX());
			pstmt.setString(82, cDataSet.getTmpChargedX());
			pstmt.setString(83, cDataSet.getTmpBooExtPriceCurrencyX());
			pstmt.setString(84, cDataSet.getTmpBooExtPriceNetAmountX());
			pstmt.setString(85, cDataSet.getTmpBooExtPriceVatPercentX());
			pstmt.setString(86, cDataSet.getTmpCodeO2());
			
			pstmt.setString(87, cDataSet.getTmpNameO2());
			pstmt.setString(88, cDataSet.getTmpBookedO2());
			pstmt.setString(89, cDataSet.getTmpChargedO2());
			
			pstmt.setString(90, cDataSet.getTmpBooExtPriceCurrencyO2());
			pstmt.setString(91, cDataSet.getTmpBooExtPriceNetAmountO2());
			pstmt.setString(92, cDataSet.getTmpBooExtPriceVatPercentO2());
			pstmt.setString(93, cDataSet.getTmpCodeY());
			pstmt.setString(94, cDataSet.getTmpNameY());
			pstmt.setString(95, cDataSet.getTmpBookedY());
			pstmt.setString(96, cDataSet.getTmpChargedY());
			pstmt.setString(97, cDataSet.getTmpBooExtPriceCurrencyY());
			pstmt.setString(98, cDataSet.getTmpBooExtPriceNetAmountY());
			pstmt.setString(99, cDataSet.getTmpBooExtPriceVatPercentY());
			pstmt.setString(100, cDataSet.getTmpCodePF());
			pstmt.setString(101, cDataSet.getTmpNamePF());
			pstmt.setString(102, cDataSet.getTmpBookedPF());
			pstmt.setString(103, cDataSet.getTmpChargedPF());
			pstmt.setString(104, cDataSet.getTmpBooExtPriceCurrencyPF());
			pstmt.setString(105, cDataSet.getTmpBooExtPriceNetAmountPF());
			pstmt.setString(106, cDataSet.getTmpBooExtPriceVatPercentPF());
			pstmt.setString(107, cDataSet.getTmpCodeSC());
			pstmt.setString(108, cDataSet.getTmpNameSC());
			pstmt.setString(109, cDataSet.getTmpBookedSC());
			pstmt.setString(110, cDataSet.getTmpChargedSC());
			pstmt.setString(111, cDataSet.getTmpBooExtPriceCurrencySC());
			pstmt.setString(112, cDataSet.getTmpBooExtPriceNetAmountSC());
			pstmt.setString(113, cDataSet.getTmpBooExtPriceVatPercentSC());
			pstmt.setString(114, oVO.getBooInsCode());
			pstmt.setString(115, oVO.getBooInsName());
			pstmt.setString(116, oVO.getBooInsBooked());
			pstmt.setString(117, oVO.getBooInsCharged());
			pstmt.setString(118, oVO.getBooInsPriceCurrency());
			pstmt.setString(119, oVO.getBooInsPriceNetAmount());
			pstmt.setString(120, oVO.getBooInsPriceVatPercent());
			pstmt.setString(121, oVO.getPaymentType());
			pstmt.setString(122, oVO.getTotlDueAmountNode());
			pstmt.setString(123, oVO.getTotlGrossAmountNode());
			pstmt.setString(124, oVO.getTotlVatAmountNode());
			pstmt.setString(125, oVO.getTotlCurrencyNode());
			pstmt.setString(126, oVO.getTotlNetAmountNode());
			pstmt.setString(127, oVO.getTotlVatPercentNode());
			pstmt.setString(128, oVO.getRatePrepaid());
		
			
			pstmt.setString(129, oVO.getReservationNo());
			pstmt.setString(130, oVO.getReservationNo());
			pstmt.setString(131, oVO.getStatus());
			pstmt.setString(132, oVO.getGroup());
			pstmt.setString(133, oVO.getDuration());
			pstmt.setString(134, oVO.getStationRemark().replace("'", ""	));
			pstmt.setString(135, oVO.getFlightNo());
			pstmt.setString(136, oVO.getBonusProgramNo());
			pstmt.setString(137, oVO.getOriginAgencyNo());
			pstmt.setString(138, oVO.getOrigiName1().replace("'", ""));
			pstmt.setString(139, oVO.getRateCode());
			pstmt.setString(140, oVO.getPickupDatetime());
			pstmt.setString(141, oVO.getPickupStationID());
			pstmt.setString(142, oVO.getPickupName());
			pstmt.setString(143, oVO.getReturnDatetime());
			pstmt.setString(144, oVO.getReturnStationID());
			pstmt.setString(145, oVO.getReturnName());
			pstmt.setString(146, oVO.getFirstDriverPhone());
			pstmt.setString(147, oVO.getFirstDriverMobile());
			pstmt.setString(148, oVO.getFirstDriverEmail());
			pstmt.setString(149, oVO.getFirstDriverName1());
			pstmt.setString(150, oVO.getFirstDriverName2());
			pstmt.setString(151, oVO.getFirstDriverCountry());
			pstmt.setString(152, oVO.getForbiInsCode());
			pstmt.setString(153, oVO.getForbiInsName());
			pstmt.setString(154, oVO.getForbiInsBooked());
			pstmt.setString(155, oVO.getForbiInsCharged());
			pstmt.setString(156, oVO.getForbiInsPrice());
			pstmt.setString(157, oVO.getForbiExtCode());
			pstmt.setString(158, oVO.getForbiExtName());
			pstmt.setString(159, oVO.getForbiExtBooked());
			pstmt.setString(160, oVO.getForbiExtCharged());
			pstmt.setString(161, oVO.getForbiExtPrice());
			pstmt.setString(162, oVO.getIncInsCode());
			pstmt.setString(163, oVO.getIncInsName());
			pstmt.setString(164, oVO.getIncInsBooked());
			pstmt.setString(165, oVO.getIncInsCharged());
			pstmt.setString(166, oVO.getIncInsPrice());
			pstmt.setString(167, oVO.getIncExtCode());
			pstmt.setString(168, oVO.getIncExtName());
			pstmt.setString(169, oVO.getIncExtBooked());
			pstmt.setString(170, oVO.getIncExtCharged());
			pstmt.setString(171, oVO.getIncExtPrice());
			
			pstmt.setString(172, cDataSet.getTmpCodeT());
			pstmt.setString(173, cDataSet.getTmpNameT());
			pstmt.setString(174, cDataSet.getTmpBookedT());
			pstmt.setString(175, cDataSet.getTmpChargedT());
			pstmt.setString(176, cDataSet.getTmpBooExtPriceCurrencyT());
			pstmt.setString(177, cDataSet.getTmpBooExtPriceNetAmountT());
			pstmt.setString(178, cDataSet.getTmpBooExtPriceVatPercentT());
			pstmt.setString(179, cDataSet.getTmpCodeOW());
			pstmt.setString(180, cDataSet.getTmpNameOW());
			pstmt.setString(181, cDataSet.getTmpBookedOW());
			pstmt.setString(182, cDataSet.getTmpChargedOW());
			pstmt.setString(183, cDataSet.getTmpBooExtPriceCurrencyOW());
			pstmt.setString(184, cDataSet.getTmpBooExtPriceNetAmountOW());
			pstmt.setString(185, cDataSet.getTmpBooExtPriceVatPercentOW());
			pstmt.setString(186, cDataSet.getTmpCodeAD());
			pstmt.setString(187, cDataSet.getTmpNameAD().replace("'", ""));
			pstmt.setString(188, cDataSet.getTmpBookedAD());
			pstmt.setString(189, cDataSet.getTmpChargedAD());
			pstmt.setString(190, cDataSet.getTmpBooExtPriceCurrencyAD());
			pstmt.setString(191, cDataSet.getTmpBooExtPriceNetAmountAD());
			pstmt.setString(192, cDataSet.getTmpBooExtPriceVatPercentAD());
			pstmt.setString(193, cDataSet.getTmpCodeNV());
			pstmt.setString(194, cDataSet.getTmpNameNV());
			pstmt.setString(195, cDataSet.getTmpBookedNV());
			pstmt.setString(196, cDataSet.getTmpChargedNV());
			pstmt.setString(197, cDataSet.getTmpBooExtPriceCurrencyNV());
			pstmt.setString(198, cDataSet.getTmpBooExtPriceNetAmountNV());
			pstmt.setString(199, cDataSet.getTmpBooExtPriceVatPercentNV());
			pstmt.setString(200, cDataSet.getTmpCodeCS());
			pstmt.setString(201, cDataSet.getTmpNameCS());
			pstmt.setString(202, cDataSet.getTmpBookedCS());
			pstmt.setString(203, cDataSet.getTmpChargedCS());
			pstmt.setString(204, cDataSet.getTmpBooExtPriceCurrencyCS());
			pstmt.setString(205, cDataSet.getTmpBooExtPriceNetAmountCS());
			pstmt.setString(206, cDataSet.getTmpBooExtPriceVatPercentCS());
			pstmt.setString(207, cDataSet.getTmpCodeX());
			pstmt.setString(208, cDataSet.getTmpNameX());
			pstmt.setString(209, cDataSet.getTmpBookedX());
			pstmt.setString(210, cDataSet.getTmpChargedX());
			pstmt.setString(211, cDataSet.getTmpBooExtPriceCurrencyX());
			pstmt.setString(212, cDataSet.getTmpBooExtPriceNetAmountX());
			pstmt.setString(213, cDataSet.getTmpBooExtPriceVatPercentX());
			pstmt.setString(214, cDataSet.getTmpCodeO2());
			pstmt.setString(215, cDataSet.getTmpNameO2());
			pstmt.setString(216, cDataSet.getTmpBookedO2());
			pstmt.setString(217, cDataSet.getTmpChargedO2());
			pstmt.setString(218, cDataSet.getTmpBooExtPriceCurrencyO2());
			pstmt.setString(219, cDataSet.getTmpBooExtPriceNetAmountO2());
			pstmt.setString(220, cDataSet.getTmpBooExtPriceVatPercentO2());
			pstmt.setString(221, cDataSet.getTmpCodeY());
			pstmt.setString(222, cDataSet.getTmpNameY());
			pstmt.setString(223, cDataSet.getTmpBookedY());
			pstmt.setString(224, cDataSet.getTmpChargedY());
			pstmt.setString(225, cDataSet.getTmpBooExtPriceCurrencyY());
			pstmt.setString(226, cDataSet.getTmpBooExtPriceNetAmountY());
			pstmt.setString(227, cDataSet.getTmpBooExtPriceVatPercentY());
			pstmt.setString(228, cDataSet.getTmpCodePF());
			pstmt.setString(229, cDataSet.getTmpNamePF());
			pstmt.setString(230, cDataSet.getTmpBookedPF());
			pstmt.setString(231, cDataSet.getTmpChargedPF());
			pstmt.setString(232, cDataSet.getTmpBooExtPriceCurrencyPF());
			pstmt.setString(233, cDataSet.getTmpBooExtPriceNetAmountPF());
			pstmt.setString(234, cDataSet.getTmpBooExtPriceVatPercentPF());
			pstmt.setString(235, cDataSet.getTmpCodeSC());
			pstmt.setString(236, cDataSet.getTmpNameSC());
			pstmt.setString(237, cDataSet.getTmpBookedSC());
			pstmt.setString(238, cDataSet.getTmpChargedSC());
			pstmt.setString(239, cDataSet.getTmpBooExtPriceCurrencySC());
			pstmt.setString(240, cDataSet.getTmpBooExtPriceNetAmountSC());
			pstmt.setString(241, cDataSet.getTmpBooExtPriceVatPercentSC());
			pstmt.setString(242, oVO.getBooInsCode());
			pstmt.setString(243, oVO.getBooInsName());
			pstmt.setString(244, oVO.getBooInsBooked());
			pstmt.setString(245, oVO.getBooInsCharged());
			pstmt.setString(246, oVO.getBooInsPriceCurrency());
			pstmt.setString(247, oVO.getBooInsPriceNetAmount());
			pstmt.setString(248, oVO.getBooInsPriceVatPercent());
			pstmt.setString(249, oVO.getPaymentType());
			pstmt.setString(250, oVO.getTotlDueAmountNode());
			pstmt.setString(251, oVO.getTotlGrossAmountNode());
			pstmt.setString(252, oVO.getTotlVatAmountNode());
			pstmt.setString(253, oVO.getTotlCurrencyNode());
			pstmt.setString(254, oVO.getTotlNetAmountNode());
			pstmt.setString(255, oVO.getTotlVatPercentNode());
			pstmt.setString(256, oVO.getRatePrepaid());
			
			pstmt.setString(257,  oVO.getPickupStationID());
			pstmt.setString(258,  oVO.getReservationNo());
			
		} catch (Exception e) {
			logger.error(e.toString());
		}
		
		return pstmt;
	}
	
	
	public PreparedStatement setPreparedStatementRequest(CobraReservationDetl oVO, PreparedStatement pstmt, String tmpResrvNo, CobraDataSet cDataSet) {
				
		try {
			pstmt.setString(1, oVO.getReservationNo());
			pstmt.setString(2, oVO.getReservationNo());
			pstmt.setString(3, oVO.getReservationNo());
			
			pstmt.setString(4, oVO.getStatus());
			pstmt.setString(5, oVO.getGroup() );
			pstmt.setString(6, oVO.getDuration());
			pstmt.setString(7, oVO.getStationRemark().replace("'", ""));
			pstmt.setString(8, oVO.getFlightNo());
			pstmt.setString(9, oVO.getBonusProgramNo());
			pstmt.setString(10, oVO.getOriginAgencyNo());
			pstmt.setString(11, oVO.getOrigiName1());
			pstmt.setString(12, oVO.getRateCode());
			pstmt.setString(13, oVO.getPickupDatetime());
			pstmt.setString(14, oVO.getPickupStationID());
			pstmt.setString(15, oVO.getPickupName());
			pstmt.setString(16, oVO.getReturnDatetime());
			pstmt.setString(17, oVO.getReturnStationID());
			pstmt.setString(18, oVO.getReturnName());
			pstmt.setString(19, oVO.getFirstDriverPhone() );
			pstmt.setString(20, oVO.getFirstDriverMobile());
			
			pstmt.setString(21, oVO.getFirstDriverEmail() 		);
			pstmt.setString(22, oVO.getFirstDriverName1() 		);
			pstmt.setString(23, oVO.getFirstDriverName2() 		);
			pstmt.setString(24, oVO.getFirstDriverCountry() 	);
			pstmt.setString(25, oVO.getForbiInsCode() 			);
			pstmt.setString(26, oVO.getForbiInsName() 			);
			pstmt.setString(27, oVO.getForbiInsBooked() 		);
			pstmt.setString(28, oVO.getForbiInsCharged() 		);
			pstmt.setString(29, oVO.getForbiInsPrice() 		);
			pstmt.setString(30, oVO.getForbiExtCode() 			);
			pstmt.setString(31, oVO.getForbiExtName() 			);
			pstmt.setString(32, oVO.getForbiExtBooked() 		);
			pstmt.setString(33, oVO.getForbiExtCharged() 		);
			pstmt.setString(34, oVO.getForbiExtPrice() 		);
			pstmt.setString(35, oVO.getIncInsCode() 			);
			pstmt.setString(36, oVO.getIncInsName() 			);
			pstmt.setString(37, oVO.getIncInsBooked() 			);
			pstmt.setString(38, oVO.getIncInsCharged() 		);
			pstmt.setString(39, oVO.getIncInsPrice() 			);
			pstmt.setString(40, oVO.getIncExtCode() 			);
			pstmt.setString(41, oVO.getIncExtName() 			);
			pstmt.setString(42, oVO.getIncExtBooked() 			);
			pstmt.setString(43, oVO.getIncExtCharged() 		);
			pstmt.setString(44, oVO.getIncExtPrice() 			);
			pstmt.setString(45, cDataSet.getTmpCodeT() 						);
			pstmt.setString(46, cDataSet.getTmpNameT() 						);
			pstmt.setString(47, cDataSet.getTmpBookedT() 					);
			pstmt.setString(48, cDataSet.getTmpChargedT()					);
			pstmt.setString(49, cDataSet.getTmpBooExtPriceCurrencyT() 		);
			pstmt.setString(50, cDataSet.getTmpBooExtPriceNetAmountT()	 	);
			pstmt.setString(51, cDataSet.getTmpBooExtPriceVatPercentT()		);
			pstmt.setString(52, cDataSet.getTmpCodeOW() 						);
			pstmt.setString(53, cDataSet.getTmpNameOW()						);
			pstmt.setString(54, cDataSet.getTmpBookedOW() 					);
			pstmt.setString(55, cDataSet.getTmpChargedOW()					);
			pstmt.setString(56, cDataSet.getTmpBooExtPriceCurrencyOW() 		);
			pstmt.setString(57, cDataSet.getTmpBooExtPriceNetAmountOW() 		);
			pstmt.setString(58, cDataSet.getTmpBooExtPriceVatPercentOW()		);
			pstmt.setString(59, cDataSet.getTmpCodeAD() 						);
			pstmt.setString(60, cDataSet.getTmpNameAD()						);
			pstmt.setString(61, cDataSet.getTmpBookedAD() 					);
			pstmt.setString(62, cDataSet.getTmpChargedAD()					);
			pstmt.setString(63, cDataSet.getTmpBooExtPriceCurrencyAD()	 	);
			pstmt.setString(64, cDataSet.getTmpBooExtPriceNetAmountAD() 		);
			pstmt.setString(65, cDataSet.getTmpBooExtPriceVatPercentAD()		);
			pstmt.setString(66, cDataSet.getTmpCodeNV() 						);
			pstmt.setString(67, cDataSet.getTmpNameNV()						);
			pstmt.setString(68, cDataSet.getTmpBookedNV() 					);
			pstmt.setString(69, cDataSet.getTmpChargedNV()					);
			pstmt.setString(70, cDataSet.getTmpBooExtPriceCurrencyNV() 		);
			pstmt.setString(71, cDataSet.getTmpBooExtPriceNetAmountNV() 		);
			pstmt.setString(72, cDataSet.getTmpBooExtPriceVatPercentNV()		);
			pstmt.setString(73, cDataSet.getTmpCodeCS() 						);
			pstmt.setString(74, cDataSet.getTmpNameCS()						);
			pstmt.setString(75, cDataSet.getTmpBookedCS() 					);
			pstmt.setString(76, cDataSet.getTmpChargedCS()					);
			pstmt.setString(77, cDataSet.getTmpBooExtPriceCurrencyCS() 		);
			pstmt.setString(78, cDataSet.getTmpBooExtPriceNetAmountCS() 		);
			pstmt.setString(79, cDataSet.getTmpBooExtPriceVatPercentCS()		);
			pstmt.setString(80, cDataSet.getTmpCodeX() 						);
			pstmt.setString(81, cDataSet.getTmpNameX()						);
			pstmt.setString(82, cDataSet.getTmpBookedX() 					);
			pstmt.setString(83, cDataSet.getTmpChargedX()					);
			pstmt.setString(84, cDataSet.getTmpBooExtPriceCurrencyX() 		);
			pstmt.setString(85, cDataSet.getTmpBooExtPriceNetAmountX() 		);
			pstmt.setString(86, cDataSet.getTmpBooExtPriceVatPercentX()		);
			pstmt.setString(87, cDataSet.getTmpCodeO2() 						);
			pstmt.setString(88, cDataSet.getTmpNameO2()						);
			pstmt.setString(89, cDataSet.getTmpBookedO2() 					);
			pstmt.setString(90, cDataSet.getTmpChargedO2()					);
			pstmt.setString(91, cDataSet.getTmpBooExtPriceCurrencyO2() 		);
			pstmt.setString(92, cDataSet.getTmpBooExtPriceNetAmountO2() 		);
			pstmt.setString(93, cDataSet.getTmpBooExtPriceVatPercentO2()		);
			pstmt.setString(94, cDataSet.getTmpCodeY() 						);
			pstmt.setString(95, cDataSet.getTmpNameY()						);
			pstmt.setString(96, cDataSet.getTmpBookedY() 					);
			pstmt.setString(97, cDataSet.getTmpChargedY()					);
			pstmt.setString(98, cDataSet.getTmpBooExtPriceCurrencyY() 		);
			pstmt.setString(99, cDataSet.getTmpBooExtPriceNetAmountY() 		);
			pstmt.setString(100, cDataSet.getTmpBooExtPriceVatPercentY()		);
			pstmt.setString(101, cDataSet.getTmpCodePF() 						);
			pstmt.setString(102, cDataSet.getTmpNamePF()						);
			pstmt.setString(103, cDataSet.getTmpBookedPF() 					);
			pstmt.setString(104, cDataSet.getTmpChargedPF()					);
			pstmt.setString(105, cDataSet.getTmpBooExtPriceCurrencyPF() 		);
			pstmt.setString(106, cDataSet.getTmpBooExtPriceNetAmountPF() 		);
			pstmt.setString(107, cDataSet.getTmpBooExtPriceVatPercentPF()		);
			pstmt.setString(108, cDataSet.getTmpCodeSC() 						);
			pstmt.setString(109, cDataSet.getTmpNameSC()						);
			pstmt.setString(110, cDataSet.getTmpBookedSC() 					);
			pstmt.setString(111, cDataSet.getTmpChargedSC()					);
			pstmt.setString(112, cDataSet.getTmpBooExtPriceCurrencySC() 		);
			pstmt.setString(113, cDataSet.getTmpBooExtPriceNetAmountSC() 		);
			pstmt.setString(114, cDataSet.getTmpBooExtPriceVatPercentSC()		);
			pstmt.setString(115, oVO.getBooInsCode() 			);
			pstmt.setString(116, oVO.getBooInsName() 			);
			pstmt.setString(117, oVO.getBooInsBooked()		 	);
			pstmt.setString(118, oVO.getBooInsCharged() 		);
			pstmt.setString(119, oVO.getBooInsPriceCurrency() 	);
			pstmt.setString(120, oVO.getBooInsPriceNetAmount() 	);
			pstmt.setString(121, oVO.getBooInsPriceVatPercent() );
			pstmt.setString(122, oVO.getPaymentType() 			);
			pstmt.setString(123, oVO.getTotlDueAmountNode() 	);
			pstmt.setString(124, oVO.getTotlGrossAmountNode() 	);
			pstmt.setString(125, oVO.getTotlVatAmountNode() 	);
			pstmt.setString(126, oVO.getTotlCurrencyNode() 		);
			pstmt.setString(127, oVO.getTotlNetAmountNode() 	);
			pstmt.setString(128, oVO.getTotlVatPercentNode() 	);
			pstmt.setString(129, oVO.getRatePrepaid() 	);

		
			
		} catch (Exception e) {
			logger.error(e.toString());
		}
		
		return pstmt;
	}
	
	
	public PreparedStatement setPreparedStatementMember(PreparedStatement pstmt, String userNm) {
		try {
			pstmt.setString(1, userNm);
		} catch (Exception e) {
			logger.error(e.toString());
		}
		
		return pstmt;
	}

	public CobraReservationDetl setDataSet(	String sReservationNo			, String sStatus				, String sGroup					,			
											String sDuration				, String sStationRemark			, String sFlightNo				,			
											String sBonusProgramNo			, String sOriginAgencyNo		, String sOrigiName1			,
											String sRateCode				, String sPickupDatetime		, String sPickupStationID		,
											String sPickupName				, String sReturnDatetime		, String sReturnStationID		,
											String sReturnName				, String sFirstDriverPhone		, String sFirstDriverMobile		,	
											String sFirstDriverEmail		, String sFirstDriverName1		, String sFirstDriverName2		,	
											String sFirstDriverCountry		, String sForbiInsCode			, String sForbiInsName			,	
											String sForbiInsBooked			, String sForbiInsCharged		, String sForbiInsPrice			,	
											String sForbiExtCode			, String sForbiExtName			, String sForbiExtBooked		,
											String sForbiExtCharged			, String sForbiExtPrice			, String sIncInsCode			,
											String sIncInsName				, String sIncInsBooked			, String sIncInsCharged			,	
											String sIncInsPrice				, String sIncExtCode			, String sIncExtName			,
											String sIncExtBooked			, String sIncExtCharged			, String sIncExtPrice			,
											String sBooExtCode				, String sBooExtName			, String sBooExtBooked			,	
											String sBooExtCharged			, String sBooExtPriceCurrency	, String sBooExtPriceNetAmount	,	
											String sBooExtPriceVatPercent	, String sBooInsCode			, String sBooInsName			,
											String sBooInsBooked			, String sBooInsCharged			, String sBooInsPriceCurrency	,
											String sBooInsPriceNetAmount	, String sBooInsPriceVatPercent	, String sPaymentType 			,
											String sTotlDueAmountNode		, String sTotlGrossAmountNode	, String sTotlVatAmountNode		,
											String sTotlCurrencyNode		, String sTotlNetAmountNode		, String sTotlVatPercentNode	,
											String sRatePrepaid ) {
		
		CobraReservationDetl oVO = new CobraReservationDetl();

		oVO.setReservationNo(sReservationNo);
		oVO.setStatus(sStatus);
		oVO.setGroup(sGroup);
		oVO.setDuration(sDuration);
		oVO.setStationRemark(sStationRemark);
		oVO.setFlightNo(sFlightNo);
		oVO.setBonusProgramNo(sBonusProgramNo);
		oVO.setOriginAgencyNo(sOriginAgencyNo);
		oVO.setOrigiName1(sOrigiName1);
		oVO.setRateCode(sRateCode);
		oVO.setPickupDatetime(sPickupDatetime);
		oVO.setPickupStationID(sPickupStationID);
		oVO.setPickupName(sPickupName);
		oVO.setReturnDatetime(sReturnDatetime);
		oVO.setReturnStationID(sReturnStationID);
		oVO.setReturnName(sReturnName);
		oVO.setFirstDriverPhone(sFirstDriverPhone);
		oVO.setFirstDriverMobile(sFirstDriverMobile);
		oVO.setFirstDriverEmail(sFirstDriverEmail);
		oVO.setFirstDriverName1(sFirstDriverName1);
		oVO.setFirstDriverName2(sFirstDriverName2);
		oVO.setFirstDriverCountry(sFirstDriverCountry);
		oVO.setForbiInsCode(sForbiInsCode);
		oVO.setForbiInsName(sForbiInsName);
		oVO.setForbiInsBooked(sForbiInsBooked);
		oVO.setForbiInsCharged(sForbiInsCharged);
		oVO.setForbiInsPrice(sForbiInsPrice);
		oVO.setForbiExtCode(sForbiExtCode);
		oVO.setForbiExtName(sForbiExtName);
		oVO.setForbiExtBooked(sForbiExtBooked);
		oVO.setForbiExtCharged(sForbiExtCharged);
		oVO.setForbiExtPrice(sForbiExtPrice);
		oVO.setIncInsCode(sIncInsCode);
		oVO.setIncInsName(sIncInsName);
		oVO.setIncInsBooked(sIncInsBooked);
		oVO.setIncInsCharged(sIncInsCharged);
		oVO.setIncInsPrice(sIncInsPrice);
		oVO.setIncExtCode(sIncExtCode);
		oVO.setIncExtName(sIncExtName);
		oVO.setIncExtBooked(sIncExtBooked);
		oVO.setIncExtCharged(sIncExtCharged);
		oVO.setIncExtPrice(sIncExtPrice);
		oVO.setBooExtCode(sBooExtCode);
		oVO.setBooExtName(sBooExtName);
		oVO.setBooExtBooked(sBooExtBooked);
		oVO.setBooExtCharged(sBooExtCharged);
		oVO.setBooExtPriceCurrency(sBooExtPriceCurrency);
		oVO.setBooExtPriceNetAmount(sBooExtPriceNetAmount);
		oVO.setBooExtPriceVatPercent(sBooExtPriceVatPercent);
		oVO.setBooInsCode(sBooInsCode);
		oVO.setBooInsName(sBooInsName);
		oVO.setBooInsBooked(sBooInsBooked);
		oVO.setBooInsCharged(sBooInsCharged);
		oVO.setBooInsPriceCurrency(sBooInsPriceCurrency);
		oVO.setBooInsPriceNetAmount(sBooInsPriceNetAmount);
		oVO.setBooInsPriceVatPercent(sBooInsPriceVatPercent);
		oVO.setPaymentType(sPaymentType);
		oVO.setTotlDueAmountNode(sTotlDueAmountNode);
		oVO.setTotlGrossAmountNode(sTotlGrossAmountNode);
		oVO.setTotlVatAmountNode(sTotlVatAmountNode);
		oVO.setTotlCurrencyNode(sTotlCurrencyNode);
		oVO.setTotlNetAmountNode(sTotlNetAmountNode);
		oVO.setTotlVatPercentNode(sTotlVatPercentNode);
		
		if ("true".equals(sRatePrepaid) ) {
			oVO.setRatePrepaid("PP");
		} else if ("false".equals(sRatePrepaid) ) {
			oVO.setRatePrepaid("POA");
		} else {
			oVO.setRatePrepaid("");
		}

		return oVO;
	}
	
	/**
	 * 인바운드 지점코드 매핑.
	 *
	 * <pre>
	 * 참조테이블 : TB_GENER_RENT_RESRV
	 * </pre>
	 * 
	 * @return 예약번호
	 * @history [2015-03-25]  김종덕  최초작성
	 */	
	public String getInboundBranCd(String strStationID) {
		DBConnection db = new DBConnection();
		Connection conn = null;
		ResultSet rs    = null;
		
		String rsInboundBranCd = "";
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(" SELECT REFRN_CD_1			").append("\n");
		sb.append("   FROM TB_CMCD 				").append("\n");
		sb.append("  WHERE CMCD_DIV = 'A90'		").append("\n");
		sb.append("    AND CMCD_CD  = ?			").append("\n");
		
		PreparedStatement pstmt = null;
		
		try {
			conn = db.getDBConnection();
		
			pstmt = conn.prepareStatement(sb.toString());
		
			pstmt.setString(1, strStationID);
		
		
			rs = pstmt.executeQuery();
		
			while ( rs.next() ) {
				rsInboundBranCd = rs.getString("REFRN_CD_1");
			}
		} catch ( Exception e) {
			logger.error(e.toString());
		}
		
		return rsInboundBranCd;
	}
	
	/**
	 * 개인고객을 검색한다.[예약/대여공통]
	 *
	 * <pre>
	 * 참조테이블 : TB_PERSNL_MEMBR_OFFLINE
	 * </pre>
	 * 
	 * @return 개인고객검색[예약/대여공통]
	 */	
	public int mSearchUser(String firstDriverName1, String firstDriverName2) {
	
		DBConnection db = new DBConnection();
		Connection conn = null;
		ResultSet rs    = null;
		
		StringBuffer sb = new StringBuffer();
		int memberSeq = 0;
		
		sb.append(" SELECT											").append("\n");
		sb.append(" 	ISNULL(MAX(MEMBER_SEQ), 0) AS MEMBER_SEQ	").append("\n");
		sb.append("   FROM TB_PERSNL_MEMBR_OFFLINE   				").append("\n");
		sb.append("  WHERE MEMBR_NM =  ?							").append("\n");
		
		PreparedStatement pstmt = null;
		
		try {
			conn = db.getDBConnection();
		
			pstmt = conn.prepareStatement(sb.toString());
		
			pstmt.setString(1, firstDriverName1 + " " + firstDriverName2);
		
		
			rs = pstmt.executeQuery();
		
			while ( rs.next() ) {
				memberSeq = rs.getInt("MEMBER_SEQ");
			}
		} catch ( Exception e) {
			logger.error(e.toString());
		}
		
		return memberSeq;
	}
	
	public static String getDateString(String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.KOREA);
		return formatter.format(new Date()); 
	}
	
	public static String getShortTimeString() {
		SimpleDateFormat formatter = new SimpleDateFormat("HHmmss", Locale.KOREA);
		return formatter.format(new Date()); 
	}
	
	//public static void main(String[] args) throws Exception {
	public static void start() throws Exception {
		if(CobraProperty.getSystemProperty()) {
			DIR_LOG 			= CobraValue.DIR_LOG;
			WORK_TIME 			= CobraValue.WORK_TIME;
		}
		
		curDate = DateUtility.getHHmmss().substring(0, 2);
		
		while ( true ) {
			if ( WORK_TIME.equals(curDate)) {
				logger.info("============================ Inbound 예약정보 수신 모듈 Start ============================================");
				logger.info("시작시간 : " + DateUtility.getyyyyMMddHHmmss());
				//int time = 60 * 60 * 96;
				int time = 60 * 60 * 1;
				
				CobraRun_Backup cRun = new CobraRun_Backup();
				
				boolean isTrue = cRun.requestReservaionNoList(time);
				
				cRun.getInboundDetl();
				logger.info("종료시간 : " + DateUtility.getyyyyMMddHHmmss());
				logger.info("============================ Inbound 예약정보 수신 모듈 End   ============================================");
			}
			
			try {
	           	Thread.sleep( 1000 * 60 * 30 );		// 30분  단위.
            } catch (InterruptedException ie) {
                if(logger.isEnabledFor(Level.ERROR)) {
                	logger.error(ie.toString());
                }
            }
		}
	}
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		
		if(CobraProperty.getSystemProperty()) {}
		
		CobraRun_Backup cRun = new CobraRun_Backup();
		
		cRun.requestReservaionNoList(60 * 60 * 96);
		logger.info("시작시간 : " + DateUtility.getyyyyMMddHHmmss());
		cRun.getInboundDetl();
		logger.info("종료시간 : " + DateUtility.getyyyyMMddHHmmss());
	}
}
