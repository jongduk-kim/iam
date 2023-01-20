package sixt.poi;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import sixt.dataset.DataSetLoanSechedule1;
import sixt.db.DBManager;

public class XlsxRead1 {
	
	private static final String FILE_EXT	= ".xlsx";
	
	private static final String FILE_DIR	= "D:\\����\\31.���̷�Ʈī\\�����ϰ���\\05.�ʱ��ڷ�\\";
	private static final String FILE_NM		= "schedule1";
	
	private static final String COLUMNS 	= "BUSN_REG_NO		, BUSI_NM			, CAR_MGMT_NO		, CAR_NO			, MONTHLY_RATE		,"
											+ "MONTHLY_PRICE	, PRINCIPAL_PRICE	, MONTHLY_TERM		, GEO_TERM			, ZAN_PRICE			,"
											+ "MONTHLY_PAY_GB	, MONTHLY_START_DAY	, MONTHLY_END_DAY	, STATE				, RETURN_HALF_DAY";
	
	private static final String COLUMNS_PARAM 	= "? , ? , ? , ? , ? ,	"
												+ "? , ? , ? , ? , ? ,	"
												+ "? , ? , ? , ? , ? ";

	// LODAER SQL �� �ۼ�
	public static String setLoaderSql(String tableNm) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" INSERT INTO 	").append(tableNm).append("\n");
		sb.append(" ( 				").append("\n");
		sb.append(COLUMNS			 ).append("\n");
		sb.append(" ) 				").append("\n");
		sb.append(" VALUES 			").append("\n");
		sb.append(" ( 				").append("\n");
		sb.append(COLUMNS_PARAM		 ).append("\n");
		sb.append(" ) 				").append("\n");
		
		return sb.toString();
	}
	
	
	public static void main(String[] args) throws Exception {
		
		Connection conn = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	
    	StringBuffer sb = new StringBuffer();
    	String message[] = new String[19];
    	HashMap hm = null;
    	
    	Vector vector = new Vector(); 
    	
    	DataSetLoanSechedule1 dLoan = new DataSetLoanSechedule1();
    	boolean isTrue = true;
    	
		conn = DBManager.getConnection();
		
		pstmt = conn.prepareStatement(setLoaderSql("TB_LOAN_MASTER_DATA"));
		
		FileInputStream fis = new FileInputStream("D:\\����\\31.���̷�Ʈī\\�����ϰ���\\05.�ʱ��ڷ�\\schedule1.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		
		int rowindex = 0;
		int columnindex = 0;
		
//		��Ʈ �� (ù��°���� �����ϹǷ� 0�� �ش�)
//		���� �� ��Ʈ�� �б����ؼ��� FOR���� �ѹ��� �����ش�
		XSSFSheet sheet = workbook.getSheetAt(0);
		
//		���� ��
		int rows = sheet.getPhysicalNumberOfRows();
		int index = 0;
		
		for(rowindex = 1; rowindex < rows; rowindex++) {
			
			 //�����д´�
			XSSFRow row = sheet.getRow(rowindex);
			
			if(row != null){
//				���� ��
		        int cells = row.getPhysicalNumberOfCells();
		        for(columnindex = 0; columnindex <= cells; columnindex++){
		        	 //������ �д´�
		            XSSFCell cell = row.getCell(columnindex);
		            String value = "";
		            //���� ���ϰ�츦 ���� ��üũ
		            if(cell == null){
		                continue;
		            } else {
		                //Ÿ�Ժ��� ���� �б�
		                switch (cell.getCellType()){
			                case XSSFCell.CELL_TYPE_FORMULA:
			                    value = cell.getCellFormula();
			                    break;
			                case XSSFCell.CELL_TYPE_NUMERIC:
			                    value = cell.getNumericCellValue()+"";
			                    break;
			                case XSSFCell.CELL_TYPE_STRING:
			                    value = cell.getStringCellValue()+"";
			                    break;
			                case XSSFCell.CELL_TYPE_BLANK:
			                    value = cell.getBooleanCellValue()+"";
			                    break;
			                case XSSFCell.CELL_TYPE_ERROR:
			                    value = cell.getErrorCellValue() + "";
			                    break;
		                }
		            }
		            message[columnindex] = value;
		            System.out.println("�� �� ���� :"+value);
		           
		        } // for end
			}
			
			hm = dLoan.getHashMap(message);
			
			
			pstmt = dLoan.setPreparedStatement(hm, pstmt);
			pstmt.addBatch();	
			
			
			if(index % 100 == 0) {	//10000
				System.out.println("Count : " + index + "�� ó����");
			}
			
			if(index % 100 == 0) {	// 1000
				pstmt.executeBatch();
				conn.commit();			
			}
			
			
			index++;
		} // for end
		
		System.out.println("�ѿϷ� Count : " + index);
		pstmt.executeBatch();
		conn.commit();
	}
}