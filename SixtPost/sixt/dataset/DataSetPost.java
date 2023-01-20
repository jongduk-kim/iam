package sixt.dataset;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class DataSetPost {
	
	private String code1;
	private String code2;
	private String code3;
	private String code4;
	private String code5;
	private String code6;
	private String code7;
	private String code8;
	private String code9;
	private String code10;
	private String code11;
	private String code12;
	private String code13;	// 건물번호본번
	private String code14;	// 건물번후부번
	private String code15;	// 건물관리번호
	private String code16;	// 다량배달처명
	private String code17;	// 시군구용건물명
	private String code18;	// 법정동코드
	private String code19;	// 법정동명
	private String code20;	// 리
	private String code21;	// 산여부
	private String code22;	// ㅇ지번본번
	private String code23;	// 읍면동일련번호
	private String code24;	// 지번부번
	
	public HashMap getHashMap (String message) {
		
		String arrPost[] = null; 
		
		arrPost = message.split("\\|");
		
		HashMap hm = new HashMap();
		
		hm.put("code1"		, arrPost[0]);
		hm.put("code2"		, arrPost[1]);
		hm.put("code3"		, arrPost[2]);
		hm.put("code4"		, arrPost[3]);
		hm.put("code5"		, arrPost[4]);
		hm.put("code6"		, arrPost[5]);
		hm.put("code7"		, arrPost[6]);
		hm.put("code8"		, arrPost[7]);
		hm.put("code9"		, arrPost[8]);
		hm.put("code10"		, arrPost[9]);
		hm.put("code11"		, arrPost[10]);
		hm.put("code12"		, arrPost[11]);
		hm.put("code13"		, arrPost[12]);
		hm.put("code14"		, arrPost[13]);
		hm.put("code15"		, arrPost[14]);
		hm.put("code16"		, arrPost[15]);
		hm.put("code17"		, arrPost[16]);
		hm.put("code18"		, arrPost[17]);
		hm.put("code19"		, arrPost[18]);
		hm.put("code20"		, arrPost[19]);
		hm.put("code21"		, arrPost[20]);
		hm.put("code22"		, arrPost[21]);
		hm.put("code23"		, arrPost[22]);
		hm.put("code24"		, arrPost[23]);

		return hm;
	}
	
	public PreparedStatement setPreparedStatement(HashMap map, PreparedStatement pstmt) throws SQLException, Exception {
		pstmt.setString(1 , map.get("code1").toString().trim());
		pstmt.setString(2 , map.get("code2").toString().trim());
		pstmt.setString(3 , map.get("code3").toString().trim());
		pstmt.setString(4 , map.get("code4").toString().trim());
		pstmt.setString(5 , map.get("code5").toString().trim());
		pstmt.setString(6 , map.get("code6").toString().trim());
		pstmt.setString(7 , map.get("code7").toString().trim());
		pstmt.setString(8 , map.get("code8").toString().trim());
		pstmt.setString(9 , map.get("code9").toString().trim());
		pstmt.setString(10 , map.get("code10").toString().trim());
		pstmt.setString(11 , map.get("code11").toString().trim());
		pstmt.setString(12 , map.get("code12").toString().trim());
		pstmt.setString(13 , map.get("code13").toString().trim());
		pstmt.setString(14 , map.get("code14").toString().trim());
		pstmt.setString(15 , map.get("code15").toString().trim());
		pstmt.setString(16 , map.get("code16").toString().trim());
		pstmt.setString(17 , map.get("code17").toString().trim());
		pstmt.setString(18 , map.get("code18").toString().trim());
		pstmt.setString(19 , map.get("code19").toString().trim());
		pstmt.setString(20 , map.get("code20").toString().trim());
		pstmt.setString(21 , map.get("code21").toString().trim());
		pstmt.setString(22 , map.get("code22").toString().trim());
		pstmt.setString(23 , map.get("code23").toString().trim());
		pstmt.setString(24 , map.get("code24").toString().trim());
		return pstmt;
	}
	
	
	public String getCode1() {
		return code1;
	}

	public void setCode1(String code1) {
		this.code1 = code1;
	}

	public String getCode10() {
		return code10;
	}

	public void setCode10(String code10) {
		this.code10 = code10;
	}

	public String getCode11() {
		return code11;
	}

	public void setCode11(String code11) {
		this.code11 = code11;
	}

	public String getCode12() {
		return code12;
	}

	public void setCode12(String code12) {
		this.code12 = code12;
	}

	public String getCode13() {
		return code13;
	}

	public void setCode13(String code13) {
		this.code13 = code13;
	}

	public String getCode14() {
		return code14;
	}

	public void setCode14(String code14) {
		this.code14 = code14;
	}

	public String getCode15() {
		return code15;
	}

	public void setCode15(String code15) {
		this.code15 = code15;
	}

	public String getCode16() {
		return code16;
	}

	public void setCode16(String code16) {
		this.code16 = code16;
	}

	public String getCode17() {
		return code17;
	}

	public void setCode17(String code17) {
		this.code17 = code17;
	}

	public String getCode18() {
		return code18;
	}

	public void setCode18(String code18) {
		this.code18 = code18;
	}

	public String getCode19() {
		return code19;
	}

	public void setCode19(String code19) {
		this.code19 = code19;
	}

	public String getCode2() {
		return code2;
	}

	public void setCode2(String code2) {
		this.code2 = code2;
	}

	public String getCode20() {
		return code20;
	}

	public void setCode20(String code20) {
		this.code20 = code20;
	}

	public String getCode21() {
		return code21;
	}

	public void setCode21(String code21) {
		this.code21 = code21;
	}

	public String getCode22() {
		return code22;
	}

	public void setCode22(String code22) {
		this.code22 = code22;
	}

	public String getCode23() {
		return code23;
	}

	public void setCode23(String code23) {
		this.code23 = code23;
	}

	public String getCode24() {
		return code24;
	}

	public void setCode24(String code24) {
		this.code24 = code24;
	}

	public String getCode3() {
		return code3;
	}

	public void setCode3(String code3) {
		this.code3 = code3;
	}

	public String getCode4() {
		return code4;
	}

	public void setCode4(String code4) {
		this.code4 = code4;
	}

	public String getCode5() {
		return code5;
	}

	public void setCode5(String code5) {
		this.code5 = code5;
	}

	public String getCode6() {
		return code6;
	}

	public void setCode6(String code6) {
		this.code6 = code6;
	}

	public String getCode7() {
		return code7;
	}

	public void setCode7(String code7) {
		this.code7 = code7;
	}

	public String getCode8() {
		return code8;
	}

	public void setCode8(String code8) {
		this.code8 = code8;
	}

	public String getCode9() {
		return code9;
	}

	public void setCode9(String code9) {
		this.code9 = code9;
	}
}