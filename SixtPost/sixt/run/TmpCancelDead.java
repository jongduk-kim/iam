package sixt.run;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import sixt.db.DBManager;

public class TmpCancelDead {
	
	public static String getWorkMonthList() {
		StringBuffer sb = new StringBuffer();
		
		/*
		 * BETWEEN '20171101' AND '20171130' 지점마감 일자 범위 세팅 EX:) 2017.12월 지점정산시 11월기준 기간설정은 '20171101' 부터  '20171130' 까지 설정 
		 * 기간범위 입력시 ''으로 묶어주어야함 
		 */
		//sb.append("	SELECT REPLACE(DATE, '-', '') FROM TB_WOLDO  WHERE REPLACE(DATE, '-', '') BETWEEN '20180601' AND '20180628' ORDER BY REPLACE(DATE, '-', '') DESC	");		// (1) 마감취소
		
		sb.append("	SELECT REPLACE(DATE, '-', '') FROM TB_WOLDO  WHERE REPLACE(DATE, '-', '') BETWEEN '20180607' AND '20180630' ORDER BY REPLACE(DATE, '-', '') ASC	");		// (2) 마감

		return sb.toString();
	}

	private static final String setUpdateSql() {
		StringBuffer sb = new StringBuffer();
		
		return sb.toString();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	CallableStatement csmt;
		
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
    		pstmt = conn.prepareStatement(getWorkMonthList());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				/*
				 * 작업순서
				 * 1. 영업마감
				 * 2. 회계마감
				 * 마감시 : 영업마감이 완료된 이후 회계마감 진행
				 * 취소시 : 순서 상관없음 ( 회계마감취소 -> 영업마감취소 혹은 영업마감취소 -> 회계마감취소 )
				 */
				//csmt = conn.prepareCall(" { call usp_DEADLN_CAR( ?, ?, ? ) } ");	// 차량마감 ( 지점정산과 무관 )
				//csmt = conn.prepareCall(" { call usp_DEADLN_SALES( ?, ?, ? ) } ");	// 영업마감 or 영업마감 취소시
				csmt = conn.prepareCall(" { call usp_DEADLN_ACCNT( ?, ?, ? ) } ");	// 회계마감 or 회계마감 취소시
				
				
				csmt.setString(1, rs.getString(1));
				csmt.setString(2, "Y");		// 마감시 Y, 취소시 : N
				csmt.setInt(3, 0);
				
				csmt.registerOutParameter(3, java.sql.Types.INTEGER);
				csmt.executeUpdate();
				
				if (csmt.getInt(3) != 0 ) {
					conn.rollback();
					throw new Exception("회계마감 처리중 오류가 발생하였습니다.\n전산팀 문의 내선 : 1970\n마감일:" + rs.getString(1));
				} else {
					conn.commit();
					System.out.println(rs.getString(1));
				}
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			try { if (conn != null) conn.close(); } catch (Exception e) {}
		}
	}
}
