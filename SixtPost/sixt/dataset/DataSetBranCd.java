package sixt.dataset;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class DataSetBranCd {
	private int size_d_SigunCd		= 11;
	private int size_d_SigunNm		= 31;
	private int size_d_Name			= 31;
	private int size_d_NameDetail	= 62;
	

	private int end_d_SigunCd		= size_d_SigunCd;
	private int end_d_SigunNm		= end_d_SigunCd					+ size_d_SigunNm; 
	private int end_d_Name			= end_d_SigunNm							+ size_d_Name; 
	private int end_d_NameDetail	= end_d_Name					+ size_d_NameDetail; 
	
	
	
	
	
	public HashMap getHashMap(String message) {
		
		/*
		byte[] messageByte = message.getBytes();

		HashMap hm = new HashMap();

		
		hm.put("SigunCd"	, new String(messageByte, 0, size_d_SigunCd						));
		hm.put("SigunNm"	, new String(messageByte, end_d_SigunCd		, size_d_SigunNm	));
		hm.put("Name"		, new String(messageByte, end_d_SigunNm		, size_d_Name		));
		hm.put("NameDetail"	, new String(messageByte, end_d_Name	, size_d_NameDetail	));
		
		
		System.out.println("|" + hm.get("SigunCd") + "|");
		System.out.println("|" + hm.get("SigunNm") + "|");
		System.out.println("|" + hm.get("Name") + "|");
		System.out.println("|" + hm.get("NameDetail") + "|");
	
		*/
		
		HashMap hm = new HashMap();
		
		String[] tmpValue = message.split("\t");
		
		if (tmpValue.length == 3) {
			hm.put("ADMNR_CD"		, tmpValue[0]);
			if ("00000000".equals(tmpValue[0].substring(2, 10)) ) {
				if ("존재".equals(tmpValue[2].substring(0, 2)) ) {
					hm.put("ADMNR_LEVL"		, "1");
					
					if ("서울".equals(tmpValue[1].substring(0, 2))) { hm.put("ADMNR_NM"		, "서울");hm.put("ADMNR_WHOL_NM"		, "서울");
					} else if ("부산".equals(tmpValue[1].substring(0, 2))) {	hm.put("ADMNR_NM"		, "부산");hm.put("ADMNR_WHOL_NM"		, "부산");
					} else if ("대구".equals(tmpValue[1].substring(0, 2))) {	hm.put("ADMNR_NM"		, "대구");hm.put("ADMNR_WHOL_NM"		, "대구");
					} else if ("인천".equals(tmpValue[1].substring(0, 2))) {	hm.put("ADMNR_NM"		, "인천");hm.put("ADMNR_WHOL_NM"		, "인천");
					} else if ("광주".equals(tmpValue[1].substring(0, 2))) {	hm.put("ADMNR_NM"		, "광주");hm.put("ADMNR_WHOL_NM"		, "광주");
					} else if ("대전".equals(tmpValue[1].substring(0, 2))) {	hm.put("ADMNR_NM"		, "대전");hm.put("ADMNR_WHOL_NM"		, "대전");
					} else if ("울산".equals(tmpValue[1].substring(0, 2))) {	hm.put("ADMNR_NM"		, "울산");hm.put("ADMNR_WHOL_NM"		, "울산");
					} else if ("세종".equals(tmpValue[1].substring(0, 2))) { hm.put("ADMNR_NM"		, "세종");hm.put("ADMNR_WHOL_NM"		, "세종");
					} else if ("경기".equals(tmpValue[1].substring(0, 2))) {	hm.put("ADMNR_NM"		, "경기");hm.put("ADMNR_WHOL_NM"		, "경기");
					} else if ("강원".equals(tmpValue[1].substring(0, 2))) { hm.put("ADMNR_NM"		, "강원");hm.put("ADMNR_WHOL_NM"		, "강원");
					} else if ("충청북도".equals(tmpValue[1].substring(0, 4))) { hm.put("ADMNR_NM"	, "충북");hm.put("ADMNR_WHOL_NM"		, "충북");
					} else if ("충청남도".equals(tmpValue[1].substring(0, 4))) { hm.put("ADMNR_NM"	, "충남");hm.put("ADMNR_WHOL_NM"		, "충남");
					} else if ("전라북도".equals(tmpValue[1].substring(0, 4))) { hm.put("ADMNR_NM"	, "전북");hm.put("ADMNR_WHOL_NM"		, "전북");
					} else if ("전라남도".equals(tmpValue[1].substring(0, 4))) { hm.put("ADMNR_NM"	, "전남");hm.put("ADMNR_WHOL_NM"		, "전남");
					} else if ("경상북도".equals(tmpValue[1].substring(0, 4))) { hm.put("ADMNR_NM"	, "경북");hm.put("ADMNR_WHOL_NM"		, "경북");
					} else if ("경상남도".equals(tmpValue[1].substring(0, 4))) { hm.put("ADMNR_NM"	, "경남");hm.put("ADMNR_WHOL_NM"		, "경남");
					} else if ("제주".equals(tmpValue[1].substring(0, 2))) {	hm.put("ADMNR_NM"		, "제주");hm.put("ADMNR_WHOL_NM"		, "제주");
					}
				}
			} else {
				String tmpValue2[] = tmpValue[1].split(" ");
				
				if ("존재".equals(tmpValue[2].substring(0, 2)) ) {
					if ("3611000000".equals(tmpValue[0])) {
						hm.put("ADMNR_LEVL"		, "2");
						
						if ("서울".equals(tmpValue[1].substring(0, 2))) {hm.put("ADMNR_NM"		, "서울");
						} else if ("부산".equals(tmpValue[1].substring(0, 2))) {hm.put("ADMNR_NM"		, "부산");
						} else if ("대구".equals(tmpValue[1].substring(0, 2))) {hm.put("ADMNR_NM"		, "대구");
						} else if ("인천".equals(tmpValue[1].substring(0, 2))) {hm.put("ADMNR_NM"		, "인천");
						} else if ("광주".equals(tmpValue[1].substring(0, 2))) {hm.put("ADMNR_NM"		, "광주");
						} else if ("대전".equals(tmpValue[1].substring(0, 2))) {hm.put("ADMNR_NM"		, "대전");
						} else if ("울산".equals(tmpValue[1].substring(0, 2))) {hm.put("ADMNR_NM"		, "울산");
						} else if ("세종".equals(tmpValue[1].substring(0, 2))) {hm.put("ADMNR_NM"		, "세종");
						} else if ("경기".equals(tmpValue[1].substring(0, 2))) {hm.put("ADMNR_NM"		, "경기");
						} else if ("강원".equals(tmpValue[1].substring(0, 2))) {hm.put("ADMNR_NM"		, "강원");
						} else if ("충청북도".equals(tmpValue[1].substring(0, 4))) { hm.put("ADMNR_NM"		, "충북");
						} else if ("충청남도".equals(tmpValue[1].substring(0, 4))) { hm.put("ADMNR_NM"		, "충남");
						} else if ("전라북도".equals(tmpValue[1].substring(0, 4))) { hm.put("ADMNR_NM"		, "전북");
						} else if ("전라남도".equals(tmpValue[1].substring(0, 4))) { hm.put("ADMNR_NM"		, "전남");
						} else if ("경상북도".equals(tmpValue[1].substring(0, 4))) { hm.put("ADMNR_NM"		, "경북");
						} else if ("경상남도".equals(tmpValue[1].substring(0, 4))) {hm.put("ADMNR_NM"		, "경남");
						} else if ("제주".equals(tmpValue[1].substring(0, 2))) {	hm.put("ADMNR_NM"		, "제주");
						}
						
						
						//hm.put("ADMNR_NM"		, tmpValue2[0]);
						
						
						
						hm.put("ADMNR_WHOL_NM"	, "");
					} else {
					
						if (tmpValue2.length == 2) {
							hm.put("ADMNR_LEVL"		, "2");
							hm.put("ADMNR_NM"			, tmpValue2[1]);
						
							
							
							hm.put("ADMNR_WHOL_NM"		, tmpValue2[1]);
						} else {
							hm.put("ADMNR_LEVL"		, "3");
							//hm.put("ADMNR_NM"			, tmpValue2[0]);
							
							if (tmpValue2.length == 3) {
								hm.put("ADMNR_NM"			, tmpValue2[2]);
								hm.put("ADMNR_WHOL_NM"		, tmpValue2[1] + " " + tmpValue2[2]);
							} else if (tmpValue2.length == 4) {
								hm.put("ADMNR_NM"			, tmpValue2[2] + " " + tmpValue2[3]);
								hm.put("ADMNR_WHOL_NM"		, tmpValue2[1] + " " + tmpValue2[2] + " " + tmpValue2[3]);
							} else if (tmpValue2.length == 5) {
								hm.put("ADMNR_NM"			, tmpValue2[2] + " " + tmpValue2[3] + " " + tmpValue2[4]);
								hm.put("ADMNR_WHOL_NM"		, tmpValue2[1] + " " + tmpValue2[2] + " " + tmpValue2[3] + " " + tmpValue2[4]);
							} else {
								hm.put("ADMNR_NM"			, tmpValue2[2]);
								hm.put("ADMNR_WHOL_NM"		, tmpValue2[1] + " " + tmpValue2[2] + " " + tmpValue2[3]);
							}
						}
					}
					
				}
			}
		}

		return hm;
	}
	
	public PreparedStatement setPreparedStatement(HashMap hm, PreparedStatement pstmt) throws SQLException, Exception {
	
		
		pstmt.setString(1 , hm.get("ADMNR_CD").toString().trim());
		pstmt.setString(2 , hm.get("ADMNR_LEVL").toString().trim());
		pstmt.setString(3 , hm.get("ADMNR_NM").toString().trim());
		pstmt.setString(4 , hm.get("ADMNR_WHOL_NM").toString().trim());
		
		return pstmt;
	}
	
	public PreparedStatement setPreparedStatementTmp(String code, String level, String name, String detlName, PreparedStatement pstmt) throws SQLException, Exception {
	
		
		pstmt.setString(1 , code);
		pstmt.setString(2 , level);
		pstmt.setString(3 , name);
		pstmt.setString(4 , detlName);
		
		return pstmt;
	}
}
