package sixt.run;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;

import sixt.dataset.DataSetBranCd;
import sixt.db.DBManager;

public class CreateBranCd {
	
	// LODAER SQL 문 작성
	public static String setLoaderSql() {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" INSERT INTO TB_ADMNR_CD_KJD ( 		").append("\n");
		sb.append("		ADMNR_CD		,			 	").append("\n");
		sb.append("		ADMNR_LEVL		,		 		").append("\n");
		sb.append("		ADMNR_NM		,		 		").append("\n");
		sb.append("		ADMNR_WHOL_NM	,		 		").append("\n");
		sb.append("		USE_CHK			,		 		").append("\n");
		sb.append("		INS_USER		,		 		").append("\n");
		sb.append("		INS_DT					 		").append("\n");
		sb.append(" ) VALUES (							").append("\n");
		sb.append(" 	? 				,				").append("\n");
		sb.append(" 	? 				,				").append("\n");
		sb.append(" 	? 				,				").append("\n");
		sb.append(" 	? 				,				").append("\n");
		sb.append(" 	'Y' 			,				").append("\n");
		sb.append(" 	'00000000'		,				").append("\n");
		sb.append(" 	GETDATE()  						").append("\n");
		sb.append(" ) 									").append("\n");
		
		return sb.toString();
	}
	
	public static boolean dbCardInsert(File rpInFile){
		Connection conn = null;
		String message = "";
		
		FileChannel inFileChannel;

		int insertCnt = 0;
		
		DataSetBranCd dDataSet = new DataSetBranCd();
		
		try{
			inFileChannel = (new FileInputStream(rpInFile)).getChannel();
			
			//ByteBuffer inBB = ByteBuffer.allocateDirect(529);
			ByteBuffer inBB = ByteBuffer.allocateDirect(300);
	
			Charset charset = Charset.forName("KSC5601");
			
			conn = DBManager.getConnection();
			
			PreparedStatement pstmt = null;
			PreparedStatement pstmtTruncate = null;
	
			conn = DBManager.getConnection();
	
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(setLoaderSql());
			
			while(inFileChannel.read(inBB) > 0){

				inBB.flip();
				
				message = "";
				message = String.valueOf(charset.decode(inBB));
				
				
				boolean isTrue = false;
				
				
				
				if(!"".equals(message) && message != null) {
					isTrue = true;
				}
				
				if(isTrue) {

					HashMap hm = dDataSet.getHashMap(message);

					if(hm != null) {
						pstmt = dDataSet.setPreparedStatement(hm, pstmt);
						pstmt.addBatch();
						
						if(insertCnt % 10000 == 0) {	//10000
							System.out.println("Count = " + insertCnt + "건 처리중");
						}

						if(insertCnt % 1000 == 0) {	// 1000
							pstmt.executeBatch();
							conn.commit();			
						}
						
						insertCnt++;
					}
				}
				inBB.clear();
			}
			
			System.out.println("총완료 Count : " + insertCnt);
			pstmt.executeBatch();
			conn.commit();
			
			//setDBInsert(this.TABLE_NM, rpWorkDate);
			
	
			inFileChannel.close();
		}catch(Exception e){
			System.out.println("RunFileRead.readFile() ::" + e.toString());
			System.out.println("RunFileRead.readFile() ::" + e.getMessage());
		}
		
		
		return false;
	}
	
	
	public static void main(String[] args) throws Exception {
		Connection conn = null;
		String message = "";
		int insertCnt = 0;
		FileChannel inFileChannel;
		
		//File file = new File("C:\\Users\\Administrator\\Downloads\\jscode20150514\\KIKmix.20150514");
		//File file = new File("C:\\Users\\Administrator\\Downloads\\jscode20150514\\KIKcd_B.20150514");
		File file = new File("C:\\Users\\Administrator\\Downloads\\BranCd.txt");
		
		int index = 0;
//		if (file.exists()) {
//			dbCardInsert(file);
//		}
		
		conn = DBManager.getConnection();
		
		conn.setAutoCommit(false);
		
		PreparedStatement pstmt = null;
		
		pstmt = conn.prepareStatement(setLoaderSql());
		
		FileReader fr = new FileReader(file);
		
		BufferedReader br1 = new BufferedReader(fr);
		
		DataSetBranCd dDataSet = new DataSetBranCd();
		
		while(true) {
			message = br1.readLine();
			if(message == null) {
				break;
			}
			String[] tmpValue = message.split("\t");
			
			if (tmpValue.length == 3) {
				//System.out.println("tmpValue[2]|" + tmpValue[2] + "|");
				if ("존재".equals(tmpValue[2])) {
					HashMap hm = dDataSet.getHashMap(message);
					
					if (hm != null ) {
						pstmt = dDataSet.setPreparedStatement(hm, pstmt);
						pstmt.addBatch();
								
						if(insertCnt % 50 == 0) {	// 1000
							pstmt.executeBatch();
							conn.commit();
						}
					
						insertCnt++;
					}
				}
			}
		}
		
		pstmt.executeBatch();
		conn.commit();	
		br1.close();
		
		String[] tmpBran = {"6100000000", "6200000000", "6300000000", "6400000000", "6500000000", "6600000000"};
		
		String[] tmpBran1 = {"6111000000", "6211000000", "6311000000", "6411000000", "6511000000", "6611000000"};
		
		String[] tmpBranNm = {"인천공항", "김해공항", "김포공항", "서울역KTX", "광명역KTX", "부산역KTX"};
		
		
		for (int i = 0; i < tmpBran.length; i ++ ) {
			pstmt = dDataSet.setPreparedStatementTmp(tmpBran[i], "1", tmpBranNm[i], tmpBranNm[i], pstmt);
			pstmt.addBatch();
		}
		
		for (int i = 0; i < tmpBran.length; i ++ ) {
			pstmt = dDataSet.setPreparedStatementTmp(tmpBran1[i], "2", tmpBranNm[i], tmpBranNm[i], pstmt);
			pstmt.addBatch();
		}
		
		pstmt.executeBatch();
		conn.commit();
	}
}
