package uni.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;

public class FileUtility {
	private static Logger logger = Logger.getLogger(FileUtility.class);
	// 파일 읽기
	public synchronized final String fileRead(String fileInDirectory, String fileInName, int capacity) throws FileNotFoundException, IOException {
		File inFile = new File(fileInDirectory, fileInName);
		FileChannel inFileChannel = (new FileInputStream(inFile)).getChannel();
		ByteBuffer inBB = ByteBuffer.allocateDirect(capacity);
		Charset charset = Charset.forName("KSC5601");
		
		if(inFile.exists()==false) {
			throw new FileNotFoundException();
		}
		if(inFile.canRead()==false) {
			throw new IOException(inFile.getName() + "파일을 읽을 수 없습니다.");
		}
		
		String data = "";
		if(inFileChannel.read(inBB)>0) {
			inBB.flip();
			data = String.valueOf(charset.decode(inBB));
			inBB.clear();
		}
		inFileChannel.close();
		return data;
	}
	
	// 파일 읽기
	public synchronized final String fileRead(String fileInDirectory, String fileInName, int capacity, int limit) throws FileNotFoundException, IOException {
		File inFile = new File(fileInDirectory, fileInName);
		FileChannel inFileChannel = (new FileInputStream(inFile)).getChannel();
		ByteBuffer inBB = ByteBuffer.allocateDirect(capacity);
		Charset charset = Charset.forName("KSC5601");

		if(inFile.exists()==false) {
			throw new FileNotFoundException();
		}
		if(inFile.canRead()==false) {
			throw new IOException(inFile.getName() + "파일을 읽을 수 없습니다.");
		}
		
		String data = "";
		if(inFileChannel.read(inBB)>0) {
			inBB.flip();
			data = String.valueOf(charset.decode(inBB)).substring(0,limit);
			inBB.clear();
		}
		inFileChannel.close();
		return data;
	}

	// 파일 특정 위치 읽기
	public synchronized final String fileReadWithPosition(String fileInDirectory, String fileInName, int capacity, long position) throws FileNotFoundException, IOException {
		File inFile = new File(fileInDirectory, fileInName);
		FileChannel inFileChannel = (new FileInputStream(inFile)).getChannel();
		ByteBuffer inBB = ByteBuffer.allocateDirect(capacity);
		Charset charset = Charset.forName("KSC5601");
		
		if(inFile.exists()==false) {
			throw new FileNotFoundException();
		}
		if(inFile.canRead()==false) {
			throw new IOException(inFile.getName() + "파일을 읽을 수 없습니다.");
		}
		
		String data = "";
		if(inFileChannel.read(inBB, position)>0) {
			inBB.flip();
			data = String.valueOf(charset.decode(inBB));
			inBB.clear();
		}
		inFileChannel.close();
		
		return data;
	}
	
	// 파일 쓰기
	public synchronized final void fileWrite(String fileInDirectory, String fileInName, String data) throws FileNotFoundException, IOException {
		File inFile = new File(fileInDirectory, fileInName);
		
		if(inFile.exists()==false) {
			inFile.createNewFile();
		}
		
		if(inFile.canWrite()==false) {
			throw new IOException(inFile.getName() + "파일에 쓸 수 없습니다.");
		}
		
		ByteBuffer bb = StringUtility.getByteBuffer(data);
		bb.flip();
		FileChannel inFileChannel = (new FileOutputStream(inFile)).getChannel();
		inFileChannel.write(bb);
		inFileChannel.close();
	}
	
	// 파일에 추가하여 쓰기
	public synchronized final void fileWriteAppend(String fileInDirectory, String fileInName, String data) throws FileNotFoundException, IOException {
		File inFile= new File(fileInDirectory, fileInName);
		
		if(inFile.exists()==false) {
			inFile.createNewFile();
		} 
		
		if(inFile.canWrite()==false) {
			throw new IOException(inFile.getName() + "파일에 쓸 수 없습니다.");
		}
		
		ByteBuffer bb = StringUtility.getByteBuffer(data);
		bb.flip();
		FileChannel inFileChannel = (new FileOutputStream(inFile,true)).getChannel();
		inFileChannel.write(bb);
		inFileChannel.close();
	}
	
	// 파일 복사하기
	public synchronized final void fileCopy(String fileInDirectory, String fileInName, String fileOutDirectory, String fileOutName) throws FileNotFoundException, IOException {
		File inFile = new File(fileInDirectory, fileInName);
		FileChannel inFileChannel = (new FileInputStream(inFile)).getChannel();
		
		if(inFile.exists() == false) {
			throw new FileNotFoundException();
		}
		if(inFile.canRead()==false) {
			throw new IOException(inFile.getName() + "파일을 읽을 수 없습니다.");
		}
		
		File outFile = new File(fileOutDirectory, fileOutName);
        
		FileChannel outFileChannel = (new FileOutputStream(outFile)).getChannel();
		
		inFileChannel.transferTo(0,inFileChannel.size(),outFileChannel);
		
		inFileChannel.close();
		outFileChannel.close();
	}
	
	/**
	 * 파일 이동하기. 파일 이동후 원본파일은 삭제한다.
	 * @param fileInDirectory		: 원본파일 디렉토리 정보
	 * @param fileInName			: 원본 파일명
	 * @param fileOutDirectory		: 복사될 디렉토리 정보
	 * @param fileOutName			: 복사후 파일명
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	
	public synchronized final void fileMove(String fileInDirectory, String fileInName, String fileOutDirectory, String fileOutName) throws FileNotFoundException, IOException {
		File inFile = new File(fileInDirectory, fileInName);
		FileChannel inFileChannel = (new FileInputStream(inFile)).getChannel();
		
		if(inFile.exists() == false) {
			throw new FileNotFoundException();
		}
		if(inFile.canRead() == false) {
			throw new IOException(inFile.getName() + "파일을 읽을 수 없습니다.");
		}
		
		File outFile = new File(fileOutDirectory, fileOutName);
		FileChannel outFileChannel = (new FileOutputStream(outFile)).getChannel();
		
		inFileChannel.transferTo(0,inFileChannel.size(),outFileChannel);
		
		inFileChannel.close();
		outFileChannel.close();
		
		if(inFile.exists()) {
            inFile.delete();
		}
	}
	
	
	// 파일 삭제하기
	public synchronized final void fileDeleteIfExist(String fileInDirectory, String fileInName) throws IOException {
		File inFile = new File(fileInDirectory, fileInName);
		
		if(inFile.exists()) {
			System.out.println(inFile.delete());
		}
	}	
	
}
