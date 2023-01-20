package sixt.run;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import sixt.db.DBManager;
import uni.util.DateUtility;

public class TestRun {
	
	public static void main(String[] args ) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CallableStatement csmt;
		
		try {
			conn = DBManager.getConnection();
			
			//pstmt =  conn.prepareStatement("	SELECT REPLACE(DATE, '-', '') FROM TB_WOLDO  WHERE REPLACE(DATE, '-', '') BETWEEN '20100105' AND '20150512' ORDER BY DATE	");
			pstmt =  conn.prepareStatement("	SELECT REPLACE(DATE, '-', '') FROM TB_WOLDO  WHERE REPLACE(DATE, '-', '') BETWEEN '20130212' AND '20150512' ORDER BY DATE	");
			
			rs = pstmt.executeQuery();
			System.out.println("Start Time :" + DateUtility.getyyyyMMddHHmmss());
			while( rs.next() ) {
				
				System.out.println(rs.getString(1));

				csmt = conn.prepareCall(" { call usp_DEADLN_ACCNT( ?, ?, ? ) } ");
				
				csmt.setString(1, rs.getString(1));
				csmt.setString(2, "N");
				csmt.setInt(3, 0);
				
				csmt.executeUpdate();
				conn.commit();

			}
			System.out.println("End Time :" + DateUtility.getyyyyMMddHHmmss());
		} catch (Exception e) {
			System.out.println("System.out.println(rs.getString(1));:" + e.toString());
		}
	}
}
