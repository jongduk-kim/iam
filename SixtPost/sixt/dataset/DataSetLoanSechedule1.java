package sixt.dataset;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class DataSetLoanSechedule1 {

	private String busnRegNo;
	private String busiNm;
	private String carMgmtNo;
	private String carNo;
	
	
	private String monthlyRate;
	private String monthlyPrice;
	private String princialPrice;
	private String monthlyTerm;
	private String geoTerm;
	private String zanPrice;
	private String monthlyPayGb;
	private String monthlyStartDay;
	private String monthlyEndDay;
	private String state;
	private String returnHalfDay;
	
	
	public HashMap getHashMap (String message[]) {
		
		HashMap hm = new HashMap();
		
		hm.put("busnRegNo"		, message[0]);
		hm.put("busiNm"			, message[1]);
		hm.put("carMgmtNo"		, message[2]);
		hm.put("carNo"			, message[3]);
		hm.put("monthlyRate"	, message[4]);
		hm.put("monthlyPrice"	, message[5]);
		hm.put("princialPrice"	, message[6]);
		hm.put("monthlyTerm"	, message[7]);
		hm.put("geoTerm"		, message[8]);
		hm.put("zanPrice"		, message[9]);
		hm.put("monthlyPayGb"	, message[10]);
		hm.put("monthlyStartDay", message[11]);
		hm.put("monthlyEndDay"	, message[12]);
		hm.put("state"			, message[13]);
		hm.put("returnHalfDay"	, message[14]);
		
		System.out.println("message[0]:" + message[0]);
		System.out.println("message[1]:" + message[1]);
		System.out.println("message[2]:" + message[2]);
		System.out.println("message[3]:" + message[3]);
		System.out.println("message[4]:" + message[4]);
		System.out.println("message[5]:" + message[5]);
		System.out.println("message[6]:" + message[6]);
		System.out.println("message[7]:" + message[7]);
		System.out.println("message[8]:" + message[8]);
		System.out.println("message[9]:" + message[9]);
		System.out.println("message[10]:" + message[10]);
		System.out.println("message[11]:" + message[11]);
		System.out.println("message[12]:" + message[12]);
		System.out.println("message[13]:" + message[13]);
		System.out.println("message[14]:" + message[14]);
		
		return hm;
	}


	public PreparedStatement setPreparedStatement(HashMap map, PreparedStatement pstmt) throws SQLException, Exception {
		pstmt.setString(1 , map.get("busnRegNo").toString().trim());
		pstmt.setString(2 , map.get("busiNm").toString().trim());
		pstmt.setString(3 , map.get("carMgmtNo").toString().trim());
		pstmt.setString(4 , map.get("carNo").toString().trim());
		pstmt.setString(5 , map.get("monthlyRate").toString().trim());
		pstmt.setString(6 , map.get("monthlyPrice").toString().trim());
		pstmt.setString(7 , map.get("princialPrice").toString().trim());
		pstmt.setString(8 , map.get("monthlyTerm").toString().trim());
		pstmt.setString(9 , map.get("geoTerm").toString().trim());
		pstmt.setString(10 , map.get("zanPrice").toString().trim());
		pstmt.setString(11, map.get("monthlyPayGb").toString().trim());
		pstmt.setString(12 , map.get("monthlyStartDay").toString().trim());
		pstmt.setString(13 , map.get("monthlyEndDay").toString().trim());
		pstmt.setString(14 , map.get("state").toString().trim());
		pstmt.setString(15 , map.get("returnHalfDay").toString().trim());
		
		return pstmt;
	}
}
