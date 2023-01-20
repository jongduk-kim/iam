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
				if ("����".equals(tmpValue[2].substring(0, 2)) ) {
					hm.put("ADMNR_LEVL"		, "1");
					
					if ("����".equals(tmpValue[1].substring(0, 2))) { hm.put("ADMNR_NM"		, "����");hm.put("ADMNR_WHOL_NM"		, "����");
					} else if ("�λ�".equals(tmpValue[1].substring(0, 2))) {	hm.put("ADMNR_NM"		, "�λ�");hm.put("ADMNR_WHOL_NM"		, "�λ�");
					} else if ("�뱸".equals(tmpValue[1].substring(0, 2))) {	hm.put("ADMNR_NM"		, "�뱸");hm.put("ADMNR_WHOL_NM"		, "�뱸");
					} else if ("��õ".equals(tmpValue[1].substring(0, 2))) {	hm.put("ADMNR_NM"		, "��õ");hm.put("ADMNR_WHOL_NM"		, "��õ");
					} else if ("����".equals(tmpValue[1].substring(0, 2))) {	hm.put("ADMNR_NM"		, "����");hm.put("ADMNR_WHOL_NM"		, "����");
					} else if ("����".equals(tmpValue[1].substring(0, 2))) {	hm.put("ADMNR_NM"		, "����");hm.put("ADMNR_WHOL_NM"		, "����");
					} else if ("���".equals(tmpValue[1].substring(0, 2))) {	hm.put("ADMNR_NM"		, "���");hm.put("ADMNR_WHOL_NM"		, "���");
					} else if ("����".equals(tmpValue[1].substring(0, 2))) { hm.put("ADMNR_NM"		, "����");hm.put("ADMNR_WHOL_NM"		, "����");
					} else if ("���".equals(tmpValue[1].substring(0, 2))) {	hm.put("ADMNR_NM"		, "���");hm.put("ADMNR_WHOL_NM"		, "���");
					} else if ("����".equals(tmpValue[1].substring(0, 2))) { hm.put("ADMNR_NM"		, "����");hm.put("ADMNR_WHOL_NM"		, "����");
					} else if ("��û�ϵ�".equals(tmpValue[1].substring(0, 4))) { hm.put("ADMNR_NM"	, "���");hm.put("ADMNR_WHOL_NM"		, "���");
					} else if ("��û����".equals(tmpValue[1].substring(0, 4))) { hm.put("ADMNR_NM"	, "�泲");hm.put("ADMNR_WHOL_NM"		, "�泲");
					} else if ("����ϵ�".equals(tmpValue[1].substring(0, 4))) { hm.put("ADMNR_NM"	, "����");hm.put("ADMNR_WHOL_NM"		, "����");
					} else if ("���󳲵�".equals(tmpValue[1].substring(0, 4))) { hm.put("ADMNR_NM"	, "����");hm.put("ADMNR_WHOL_NM"		, "����");
					} else if ("���ϵ�".equals(tmpValue[1].substring(0, 4))) { hm.put("ADMNR_NM"	, "���");hm.put("ADMNR_WHOL_NM"		, "���");
					} else if ("��󳲵�".equals(tmpValue[1].substring(0, 4))) { hm.put("ADMNR_NM"	, "�泲");hm.put("ADMNR_WHOL_NM"		, "�泲");
					} else if ("����".equals(tmpValue[1].substring(0, 2))) {	hm.put("ADMNR_NM"		, "����");hm.put("ADMNR_WHOL_NM"		, "����");
					}
				}
			} else {
				String tmpValue2[] = tmpValue[1].split(" ");
				
				if ("����".equals(tmpValue[2].substring(0, 2)) ) {
					if ("3611000000".equals(tmpValue[0])) {
						hm.put("ADMNR_LEVL"		, "2");
						
						if ("����".equals(tmpValue[1].substring(0, 2))) {hm.put("ADMNR_NM"		, "����");
						} else if ("�λ�".equals(tmpValue[1].substring(0, 2))) {hm.put("ADMNR_NM"		, "�λ�");
						} else if ("�뱸".equals(tmpValue[1].substring(0, 2))) {hm.put("ADMNR_NM"		, "�뱸");
						} else if ("��õ".equals(tmpValue[1].substring(0, 2))) {hm.put("ADMNR_NM"		, "��õ");
						} else if ("����".equals(tmpValue[1].substring(0, 2))) {hm.put("ADMNR_NM"		, "����");
						} else if ("����".equals(tmpValue[1].substring(0, 2))) {hm.put("ADMNR_NM"		, "����");
						} else if ("���".equals(tmpValue[1].substring(0, 2))) {hm.put("ADMNR_NM"		, "���");
						} else if ("����".equals(tmpValue[1].substring(0, 2))) {hm.put("ADMNR_NM"		, "����");
						} else if ("���".equals(tmpValue[1].substring(0, 2))) {hm.put("ADMNR_NM"		, "���");
						} else if ("����".equals(tmpValue[1].substring(0, 2))) {hm.put("ADMNR_NM"		, "����");
						} else if ("��û�ϵ�".equals(tmpValue[1].substring(0, 4))) { hm.put("ADMNR_NM"		, "���");
						} else if ("��û����".equals(tmpValue[1].substring(0, 4))) { hm.put("ADMNR_NM"		, "�泲");
						} else if ("����ϵ�".equals(tmpValue[1].substring(0, 4))) { hm.put("ADMNR_NM"		, "����");
						} else if ("���󳲵�".equals(tmpValue[1].substring(0, 4))) { hm.put("ADMNR_NM"		, "����");
						} else if ("���ϵ�".equals(tmpValue[1].substring(0, 4))) { hm.put("ADMNR_NM"		, "���");
						} else if ("��󳲵�".equals(tmpValue[1].substring(0, 4))) {hm.put("ADMNR_NM"		, "�泲");
						} else if ("����".equals(tmpValue[1].substring(0, 2))) {	hm.put("ADMNR_NM"		, "����");
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
