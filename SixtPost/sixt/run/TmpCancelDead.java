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
		 * BETWEEN '20171101' AND '20171130' �������� ���� ���� ���� EX:) 2017.12�� ��������� 11������ �Ⱓ������ '20171101' ����  '20171130' ���� ���� 
		 * �Ⱓ���� �Է½� ''���� �����־���� 
		 */
		//sb.append("	SELECT REPLACE(DATE, '-', '') FROM TB_WOLDO  WHERE REPLACE(DATE, '-', '') BETWEEN '20180601' AND '20180628' ORDER BY REPLACE(DATE, '-', '') DESC	");		// (1) �������
		
		sb.append("	SELECT REPLACE(DATE, '-', '') FROM TB_WOLDO  WHERE REPLACE(DATE, '-', '') BETWEEN '20180607' AND '20180630' ORDER BY REPLACE(DATE, '-', '') ASC	");		// (2) ����

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
				 * �۾�����
				 * 1. ��������
				 * 2. ȸ�踶��
				 * ������ : ���������� �Ϸ�� ���� ȸ�踶�� ����
				 * ��ҽ� : ���� ������� ( ȸ�踶����� -> ����������� Ȥ�� ����������� -> ȸ�踶����� )
				 */
				//csmt = conn.prepareCall(" { call usp_DEADLN_CAR( ?, ?, ? ) } ");	// �������� ( ��������� ���� )
				//csmt = conn.prepareCall(" { call usp_DEADLN_SALES( ?, ?, ? ) } ");	// �������� or �������� ��ҽ�
				csmt = conn.prepareCall(" { call usp_DEADLN_ACCNT( ?, ?, ? ) } ");	// ȸ�踶�� or ȸ�踶�� ��ҽ�
				
				
				csmt.setString(1, rs.getString(1));
				csmt.setString(2, "Y");		// ������ Y, ��ҽ� : N
				csmt.setInt(3, 0);
				
				csmt.registerOutParameter(3, java.sql.Types.INTEGER);
				csmt.executeUpdate();
				
				if (csmt.getInt(3) != 0 ) {
					conn.rollback();
					throw new Exception("ȸ�踶�� ó���� ������ �߻��Ͽ����ϴ�.\n������ ���� ���� : 1970\n������:" + rs.getString(1));
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
