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

import sixt.dataset.DataSetLoanSechedule2;
import sixt.db.DBManager;

public class XlsxRead2 {
	
	private static final String FILE_EXT	= ".xlsx";
	
	private static final String FILE_DIR	= "D:\\문서\\31.하이렌트카\\최형록과장\\05.초기자료\\";
	private static final String FILE_NM		= "data2";
	
	private static final String COLUMNS 	= "BUSN_REG_NO	, BUSI_NM		, CAR_MGMT_NO	, CAR_NO			, SEQ		,"
											+ "PAYM_DAY		, PAYM_MONTH	, ORIGINAL_PRICE, INTEREST_PRICE	, TOTL_PRICE, "
											+ "ZANGA_PRICE	, BANK_BALANCE";
	
	private static final String COLUMNS_PARAM 	= "? , ? , ? , ? , ? ,	"
												+ "? , ? , ? , ? , ? ,	"
												+ "? , ?  ";

	// LODAER SQL 문 작성
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
    	String message[] = new String[18];
    	HashMap hm = null;
    	
    	Vector vector = new Vector(); 
    	
    	DataSetLoanSechedule2 dLoan = new DataSetLoanSechedule2();
    	boolean isTrue = true;
    	
		conn = DBManager.getConnection();
		
		pstmt = conn.prepareStatement(setLoaderSql("TB_LOAN_CAR_SCHEDULE_DATA"));
		
		FileInputStream fis = new FileInputStream("D:\\문서\\31.하이렌트카\\최형록과장\\05.초기자료\\data3.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		
		int rowindex = 0;
		int columnindex = 0;
		
//		시트 수 (첫번째에만 존재하므로 0을 준다)
//		만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
		XSSFSheet sheet = workbook.getSheetAt(0);
		
//		행의 수
		int rows = sheet.getPhysicalNumberOfRows();
		int index = 0;
		
		for(rowindex = 1; rowindex < rows; rowindex++){
			
			 //행을읽는다
			XSSFRow row = sheet.getRow(rowindex);
			
			if(row != null){
//				셀의 수
		        int cells = row.getPhysicalNumberOfCells();
		        for(columnindex = 0; columnindex <= cells; columnindex++){
		        	 //셀값을 읽는다
		            XSSFCell cell = row.getCell(columnindex);
		            String value = "";
		            //셀이 빈값일경우를 위한 널체크
		            if(cell == null){
		                continue;
		            } else {
		                //타입별로 내용 읽기
		                switch (cell.getCellType()){
			                case XSSFCell.CELL_TYPE_FORMULA:
			                    value = cell.getCellFormula() + "";
			                    break;
			                case XSSFCell.CELL_TYPE_NUMERIC:
			                	//value = cell.getNumericCellValue()+"";
			                	value = "" + (long) cell.getNumericCellValue();
			                    break;
			                case XSSFCell.CELL_TYPE_STRING:
			                    value = cell.getStringCellValue()+"";
			                    break;
			                case XSSFCell.CELL_TYPE_BLANK:
			                    //value = cell.getBooleanCellValue()+"";
			                	value = "";
			                    break;
			                case XSSFCell.CELL_TYPE_ERROR:
			                    value = cell.getErrorCellValue() + "";
			                    break;
		                }
		            }
		            
		            message[columnindex] = value;
		            
		        } // for end
			}
			
			hm = dLoan.getHashMap(message);
			
			//if (index != 0) {
				pstmt = dLoan.setPreparedStatement(hm, pstmt);
				pstmt.addBatch();	
//			}
			
			if(index % 100 == 0) {	//10000
				System.out.println("Count : " + index + "건 처리중");
			}
			
			if(index % 100 == 0) {	// 1000
				pstmt.executeBatch();
				conn.commit();			
			}
			
			index++;
		} // for end
		
		System.out.println("총완료 Count : " + index);
		pstmt.executeBatch();
		conn.commit();
	}
}