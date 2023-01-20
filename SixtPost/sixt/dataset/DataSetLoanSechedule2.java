package sixt.dataset;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class DataSetLoanSechedule2 {

	private String busnRegNo;
	private String busiNm;
	private String carMgmtNo;
	private String carNo;
	private int seq;
	private String paymDay;
	private String paymMonth;
	private String originalPrice;
	private String interestPrice;
	private String totlPrice;
	private String zangaPrice;
	private String bankBalancePrice;
	
	public HashMap getHashMap (String message[]) {
		
		HashMap hm = new HashMap();
		
		hm.put("busnRegNo"			, message[0]);
		hm.put("busiNm"				, message[1]);
		hm.put("carMgmtNo"			, message[2]);
		hm.put("carNo"				, message[3]);
		hm.put("seq"				, message[4]);
		hm.put("paymDay"			, message[5]);
		hm.put("paymMonth"			, message[6]);
		hm.put("originalPrice"		, message[7]);
		hm.put("interestPrice"		, message[8]);
		hm.put("totlPrice"			, message[9]);
		//hm.put("zangaPrice"			, message[10]);
		hm.put("zangaPrice"			, 0);
		hm.put("bankBalancePrice"	, message[11]);
		/*
		System.out.println("busnRegNo:" +  message[0]);
		System.out.println("busiNm:" +  message[1]);
		System.out.println("carMgmtNo:" +  message[2]);
		System.out.println("carNo:" +  message[3]);
		System.out.println("seq:" +  message[4]);
		System.out.println("paymDay:" +  message[5]);
		System.out.println("paymMonth:" +  message[6]);
		System.out.println("originalPrice:" +  message[7]);
		System.out.println("interestPrice:" +  message[8]);
		System.out.println("totlPrice:" +  message[9]);
		System.out.println("zangaPrice:" +  message[10]);
		System.out.println("bankBalancePrice:" +  message[11]);
*/
		
		return hm;
	}


	public PreparedStatement setPreparedStatement(HashMap map, PreparedStatement pstmt) throws SQLException, Exception {
		pstmt.setString(1 , map.get("busnRegNo").toString().trim());
		pstmt.setString(2 , map.get("busiNm").toString().trim());
		pstmt.setString(3 , map.get("carMgmtNo").toString().trim());
		pstmt.setString(4 , map.get("carNo").toString().trim());
		pstmt.setString(5 , map.get("seq").toString().trim());
		pstmt.setString(6 , map.get("paymDay").toString().trim());
		pstmt.setString(7 , map.get("paymMonth").toString().trim());
		pstmt.setString(8 , map.get("originalPrice").toString().trim());
		pstmt.setString(9 , map.get("interestPrice").toString().trim());
		pstmt.setString(10 , map.get("totlPrice").toString().trim());
		pstmt.setString(11 , map.get("zangaPrice").toString().trim());
		pstmt.setString(12 , map.get("bankBalancePrice").toString().trim());
		
		return pstmt;
	}


	public String getBankBalancePrice() {
		return bankBalancePrice;
	}


	public void setBankBalancePrice(String bankBalancePrice) {
		this.bankBalancePrice = bankBalancePrice;
	}


	public String getBusiNm() {
		return busiNm;
	}


	public void setBusiNm(String busiNm) {
		this.busiNm = busiNm;
	}


	public String getBusnRegNo() {
		return busnRegNo;
	}


	public void setBusnRegNo(String busnRegNo) {
		this.busnRegNo = busnRegNo;
	}


	public String getCarMgmtNo() {
		return carMgmtNo;
	}


	public void setCarMgmtNo(String carMgmtNo) {
		this.carMgmtNo = carMgmtNo;
	}


	public String getCarNo() {
		return carNo;
	}


	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}


	public String getInterestPrice() {
		return interestPrice;
	}


	public void setInterestPrice(String interestPrice) {
		this.interestPrice = interestPrice;
	}


	public String getOriginalPrice() {
		return originalPrice;
	}


	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}


	public String getPaymDay() {
		return paymDay;
	}


	public void setPaymDay(String paymDay) {
		this.paymDay = paymDay;
	}


	public String getPaymMonth() {
		return paymMonth;
	}


	public void setPaymMonth(String paymMonth) {
		this.paymMonth = paymMonth;
	}


	public int getSeq() {
		return seq;
	}


	public void setSeq(int seq) {
		this.seq = seq;
	}


	public String getTotlPrice() {
		return totlPrice;
	}


	public void setTotlPrice(String totlPrice) {
		this.totlPrice = totlPrice;
	}


	public String getZangaPrice() {
		return zangaPrice;
	}


	public void setZangaPrice(String zangaPrice) {
		this.zangaPrice = zangaPrice;
	}

	
}
