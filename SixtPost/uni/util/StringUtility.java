package uni.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class StringUtility {

	// null À» °ø¹éÀ¸·Î ¹Ù²Û´Ù.
	public synchronized final static String convertNull(String str) {
		if(str == null) str = "";
		
		return str;
	}
	
	public synchronized final static String convertNullHM(HashMap hm, String hmKey) {
		String rsStr = "";
		
		if (hm.get(hmKey).toString() == null) {
			return rsStr;
		} else {
			return hm.get(hmKey).toString().trim();
		}
	}
	
	// ¹®ÀÚ¿­À» ByteBuffer ¿¡ ³Ö´Â´Ù.
	public synchronized final static ByteBuffer getByteBuffer(String str) {
		String tmpStr = str;
		byte[] bytes = tmpStr.getBytes();
		ByteBuffer bb = ByteBuffer.allocate(bytes.length);		
		bb.put(bytes,0,bytes.length);		
		return bb;
	}
	
	// °íÁ¤±æÀÌ ¼ýÀÚÇüÅÂÀÇ ¹®ÀÚ¿­À» ¾ò´Â´Ù. ³²´ÂÀÚ¸®´Â '0'
	public synchronized final static String getFixedNumber(String number, int len) {
		String str = number;
		
		if(str == null) {
			str = "";
		} else if(str.length() > len) {
			str = str.substring(0, len);
		}
		
		StringBuffer sbString = new StringBuffer();
		
		for(int i=str.length(); i<len; i++) {
			sbString.append("0");
		}
		
		sbString.append(str);
		
		return sbString.toString();
	}

	public synchronized final static String getFixedOneStr(String one_str, int len) {
		String str = one_str;
		
		if(str == null) {
			str = "";
		} else if(str.length() > len) {
			str = str.substring(0, len);
		}
		
		StringBuffer sbString = new StringBuffer();
		
		for(int i=str.length(); i<len; i++) {
			sbString.append(one_str);
		}
		
		sbString.append(str);
		
		return sbString.toString();
	}
	
	public synchronized final static String getFixedNumber(int number, int len) {
		String str = String.valueOf(number);
		
		if(str == null) {
			str = "";
		} else if(str.length() > len) {
			str = str.substring(0, len);
		}
		
		StringBuffer sbString = new StringBuffer();
		
		for(int i=str.length(); i<len; i++) {
			sbString.append("0");
		}
		
		sbString.append(str);
		
		return sbString.toString();
	}
	
	// °íÁ¤±æÀÌ byte ·Î ÀÚ¸¥ ¹®ÀÚ¿­ ¹è¿­À» ¾ò´Â´Ù.

	public synchronized final static String[] getFixedByteArray(String str, int byteLength) {
		
		List aList = new ArrayList();
		String tmpStr = str;
		if(tmpStr == null) {
			tmpStr = "";
		}
		if(tmpStr.getBytes().length < byteLength) {
			aList.add(getFixedByte(tmpStr, byteLength));
		} else {
			byte[] byteString = tmpStr.getBytes();
			int startPos = 0;
			int endPos = 0+byteLength;
			
			while(byteString.length>startPos) {
				String tmp = "";
				if(byteString.length >= (startPos+byteLength)) {
					tmp = new String(byteString,startPos,byteLength);
				} else {
					tmp = new String(byteString,startPos,byteString.length-startPos);
				}
				
				aList.add(getFixedByte(tmp,byteLength));
				startPos = startPos + byteLength;
				endPos = endPos + byteLength;
			}
		}
				
		String[] strArray = new String[aList.size()];
		aList.toArray(strArray);
		aList.clear();
		return strArray;
	}
	
	// °íÁ¤±æÀÌ byte ·Î ÀÚ¸¥ ¹®ÀÚ¿­À» ¾ò´Â´Ù.
	public synchronized final static String getFullFixedByte(String str, int byteLength) {
		
		str = getFullString(str);
		str = getFixedByte(str, byteLength-2);
		str = str + "  ";
		return str;
	}
	
	// °íÁ¤±æÀÌ byte ·Î ÀÚ¸¥ ¹®ÀÚ¿­À» ¾ò´Â´Ù.
	public synchronized final static String getFixedByte(String str, int byteLength) {
		StringBuffer sbString = new StringBuffer();
		String tmpStr = str;
		if(tmpStr == null) {
			tmpStr = "";
		}
		
		sbString.append(tmpStr);
		
		for(int i = tmpStr.getBytes().length; i < byteLength; i++) {
			sbString.append(" ");
		}
		tmpStr = sbString.toString();
		byte[] byteString = tmpStr.getBytes();
		
		return new String(byteString, 0, byteLength);
	}

	// °íÁ¤±æÀÌ byte ·Î ÀÚ¸¥ ¾ÏÈ£È­µÈ ¹®ÀÚ¿­À» ¾ò´Â´Ù.
	public synchronized final static String X_getEncryptedFixedByte(String str, int byteLength) throws 	InvalidKeyException, 
																										NoSuchAlgorithmException, 
																										NoSuchPaddingException, 
																										InvalidKeySpecException, 
																										UnsupportedEncodingException, 
																										IllegalBlockSizeException, 
																										BadPaddingException {
		StringBuffer sbString = new StringBuffer();
		String tmpStr = str;
		if(tmpStr == null) {
			tmpStr = "";
		}
		
		//sbString.append(EncryptionUtility.setEncrypt(tmpStr));
		
		for(int i=sbString.toString().getBytes().length; i<byteLength; i++) {
			sbString.append(" ");
		}
		tmpStr = sbString.toString();
		byte[] byteString = tmpStr.getBytes();
		
		return new String(byteString,0,byteLength);
	}
	
	// ½ÃÀÛÀ§Ä¡·Î ºÎÅÍ Æ¯Á¤ ±æÀÌÀÇ  °íÁ¤±æÀÌ ¹®ÀÚ¿­À» ¾ò´Â´Ù.
	public synchronized final static String getFixedByte(String str, int startPos, int endPos) {
		String tmpStr = str;
		tmpStr = getFixedByte(tmpStr, endPos);
		return new String(tmpStr.getBytes(), startPos, endPos-startPos);
	}
	
	// ¹ÝÀÚ¸¦ ÀüÀÚ·Î º¯È¯ÇÑ´Ù.
	public synchronized final static String getFullString(String strHalf) {
		
		if(strHalf == null) {
			return strHalf;
		}
				
		char[] charArray = strHalf.toCharArray();
		
		for(int i=0; i<charArray.length; i++) {			
			if(charArray[i] >= 0x21 && charArray[i] <= 0x7E) {
				// ¹ÝÀÚ ¿µ¹® ¿µ¼ýÀÚ/¹®ÀÚºÎÈ£¸¦ °ñ¶ó, 0xFEE0 ¸¸Å­ ´õÇÑ´Ù.
				charArray[i] += 0xFEE0;
			} else if(charArray[i] == 0x20) {
				// ¹ÝÀÚ °ø¹éÀ» ÀüÀÚ °ø¹éÀ¸·Î ¹Ù²Û´Ù.
				charArray[i] = 0x3000;
			}
		}
		
		String strFull = new String(charArray);
		
		return strFull;
	}
	
	// ÀüÀÚ¸¦ ¹ÝÀÚ·Î º¯È¯ÇÑ´Ù.
	public synchronized final static String getHalfString(String strFull) {
		
		if(strFull == null) {
			return strFull;
		}
						
		char[] charArray = strFull.toCharArray();
		
		for(int i=0; i<charArray.length; i++) {			
			if(charArray[i] >= 0xFF01 && charArray[i] <= 0xFF5E) {
				// ÀüÀÚ ¿µ¹® ¿µ¼ýÀÚ/¹®ÀÚºÎÈ£¸¦ °ñ¶ó, 0xFEE0 ¸¸Å­ »«´Ù.
				charArray[i] -= 0xFEE0;
			} else if(charArray[i] == 0x3000) {
				// ÀüÀÚ °ø¹éÀ» ¹ÝÀÚ °ø¹éÀ¸·Î ¹Ù²Û´Ù.
				charArray[i] = 0x20;
			}
		}
		
		String strHalf = new String(charArray);
		return strHalf;
	}	
	
	// ÇÑ±ÛÀ» ¿µ¹®À¸·Î º¯È¯ÇÑ´Ù.
	public synchronized final static String convertHangul_English(String str) {
		
		String tmpStr = str;
		if(tmpStr.indexOf("»ç¿ëÀÚ") >= 0) {
			tmpStr = tmpStr.replaceAll("»ç¿ëÀÚ","User");
		} else if(tmpStr.indexOf("ÇÁ·Î±×·¥") >= 0) {
			tmpStr = tmpStr.replaceAll("ÇÁ·Î±×·¥","Pgm");
		} else if(tmpStr.indexOf("Àç¹«ÀçÇ¥") >= 0) {
			tmpStr = tmpStr.replaceAll("Àç¹«ÀçÇ¥","BSPL");
		} else if(tmpStr.indexOf("ºÎ°¡°¡Ä¡¼¼") >= 0) {
			tmpStr = tmpStr.replaceAll("ºÎ°¡°¡Ä¡¼¼","VAT");
		} else if(tmpStr.indexOf("»ý¼º") >= 0) {
			tmpStr = tmpStr.replaceAll("»ý¼º","Crt");
		} else if(tmpStr.indexOf("ÄÚµå") >= 0) {
			tmpStr = tmpStr.replaceAll("ÄÚµå","Cd");
		} else if(tmpStr.indexOf("¹®¼­") >= 0) {
			tmpStr = tmpStr.replaceAll("¹®¼­","Doc");
		} else if(tmpStr.indexOf("º¸°í¼­") >= 0) {
			tmpStr = tmpStr.replaceAll("º¸°í¼­","Rpt");
		} else if(tmpStr.indexOf("°øÁö»çÇ×") >= 0) {
			tmpStr = tmpStr.replaceAll("°øÁö»çÇ×","Notice");
		} else if(tmpStr.indexOf("°øÁö") >= 0) {
			tmpStr = tmpStr.replaceAll("°øÁö","Notice");
		} else if(tmpStr.indexOf("Ä«µå") >= 0) {
			tmpStr = tmpStr.replaceAll("Ä«µå","Card");
		}
		
		tmpStr = tmpStr.replaceAll( "°¡", "Ga");    
		tmpStr = tmpStr.replaceAll( "°¢", "Gak");   
		tmpStr = tmpStr.replaceAll( "°£", "Gan");   
		tmpStr = tmpStr.replaceAll( "°¥", "Gal");   
		tmpStr = tmpStr.replaceAll( "°¨", "Gam");   
		tmpStr = tmpStr.replaceAll( "°©", "Gap");   
		tmpStr = tmpStr.replaceAll( "°«", "Gat");   
		tmpStr = tmpStr.replaceAll( "°­", "Gang");  
		tmpStr = tmpStr.replaceAll( "°³", "Gae");   
		tmpStr = tmpStr.replaceAll( "°´", "Gaek");  
		tmpStr = tmpStr.replaceAll( "°Å", "Geo");   
		tmpStr = tmpStr.replaceAll( "°Ç", "Geon");  
		tmpStr = tmpStr.replaceAll( "°É", "Geol");  
		tmpStr = tmpStr.replaceAll( "°Ë", "Geom");  
		tmpStr = tmpStr.replaceAll( "°Ì", "Geop");  
		tmpStr = tmpStr.replaceAll( "°Ô", "Ge");    
		tmpStr = tmpStr.replaceAll( "°Ü", "Gyeo");  
		tmpStr = tmpStr.replaceAll( "°Ý", "Gyeok"); 
		tmpStr = tmpStr.replaceAll( "°ß", "Gyeon"); 
		tmpStr = tmpStr.replaceAll( "°á", "Gyeol"); 
		tmpStr = tmpStr.replaceAll( "°â", "Gyeom"); 
		tmpStr = tmpStr.replaceAll( "°ã", "Gyeop"); 
		tmpStr = tmpStr.replaceAll( "°æ", "Gyeong");
		tmpStr = tmpStr.replaceAll( "°è", "Gye");   
		tmpStr = tmpStr.replaceAll( "°í", "Go");    
		tmpStr = tmpStr.replaceAll( "°î", "Gok");   
		tmpStr = tmpStr.replaceAll( "°ï", "Gon");   
		tmpStr = tmpStr.replaceAll( "°ñ", "Gol");   
		tmpStr = tmpStr.replaceAll( "°÷", "Got");   
		tmpStr = tmpStr.replaceAll( "°ø", "Gong");  
		tmpStr = tmpStr.replaceAll( "°ù", "Got");   
		tmpStr = tmpStr.replaceAll( "°ú", "Gwa");   
		tmpStr = tmpStr.replaceAll( "°û", "Gwak");  
		tmpStr = tmpStr.replaceAll( "°ü", "Gwan");  
		tmpStr = tmpStr.replaceAll( "°ý", "Gwal");  
		tmpStr = tmpStr.replaceAll( "±¤", "Gwang"); 
		tmpStr = tmpStr.replaceAll( "±¥", "Gwae");  
		tmpStr = tmpStr.replaceAll( "±«", "Goe");   
		tmpStr = tmpStr.replaceAll( "±²", "Goeng"); 
		tmpStr = tmpStr.replaceAll( "±³", "Gyo");   
		tmpStr = tmpStr.replaceAll( "±¸", "Gu");    
		tmpStr = tmpStr.replaceAll( "±¹", "Guk");   
		tmpStr = tmpStr.replaceAll( "±º", "Gun");   
		tmpStr = tmpStr.replaceAll( "±¼", "Gul");   
		tmpStr = tmpStr.replaceAll( "±Â", "Gut");   
		tmpStr = tmpStr.replaceAll( "±Ã", "Gung");  
		tmpStr = tmpStr.replaceAll( "±Ç", "Gwon");  
		tmpStr = tmpStr.replaceAll( "±È", "Gwol");  
		tmpStr = tmpStr.replaceAll( "±Í", "Gwi");   
		tmpStr = tmpStr.replaceAll( "±Ô", "Gyu");   
		tmpStr = tmpStr.replaceAll( "±Õ", "Gyun");  
		tmpStr = tmpStr.replaceAll( "±Ö", "Gyul");  
		tmpStr = tmpStr.replaceAll( "±×", "Geu");   
		tmpStr = tmpStr.replaceAll( "±Ø", "Geuk");  
		tmpStr = tmpStr.replaceAll( "±Ù", "Geun");  
		tmpStr = tmpStr.replaceAll( "±Û", "Geul");  
		tmpStr = tmpStr.replaceAll( "±Ý", "Geum");  
		tmpStr = tmpStr.replaceAll( "±Þ", "Geup");  
		tmpStr = tmpStr.replaceAll( "±à", "Geung"); 
		tmpStr = tmpStr.replaceAll( "±â", "Gi");    
		tmpStr = tmpStr.replaceAll( "±ä", "Gin");   
		tmpStr = tmpStr.replaceAll( "±æ", "Gil");   
		tmpStr = tmpStr.replaceAll( "±è", "Gim");   
		tmpStr = tmpStr.replaceAll( "±î", "Kka");   
		tmpStr = tmpStr.replaceAll( "±ú", "Kkae");  
		tmpStr = tmpStr.replaceAll( "²¿", "Kko");   
		tmpStr = tmpStr.replaceAll( "²À", "Kkok");  
		tmpStr = tmpStr.replaceAll( "²É", "Kkot");  
		tmpStr = tmpStr.replaceAll( "²Ò", "Kkoe");  
		tmpStr = tmpStr.replaceAll( "²Ù", "Kku");   
		tmpStr = tmpStr.replaceAll( "²Þ", "Kkum");  
		tmpStr = tmpStr.replaceAll( "³¡", "Kkeut"); 
		tmpStr = tmpStr.replaceAll( "³¢", "Kki");   
		tmpStr = tmpStr.replaceAll( "³ª", "Na");    
		tmpStr = tmpStr.replaceAll( "³«", "Nak");   
		tmpStr = tmpStr.replaceAll( "³­", "Nan");   
		tmpStr = tmpStr.replaceAll( "³¯", "Nal");   
		tmpStr = tmpStr.replaceAll( "³²", "Nam");   
		tmpStr = tmpStr.replaceAll( "³³", "Nap");   
		tmpStr = tmpStr.replaceAll( "³¶", "Nang");  
		tmpStr = tmpStr.replaceAll( "³»", "Nae");   
		tmpStr = tmpStr.replaceAll( "³Ã", "Naeng"); 
		tmpStr = tmpStr.replaceAll( "³Ê", "Neo");   
		tmpStr = tmpStr.replaceAll( "³Î", "Neol");  
		tmpStr = tmpStr.replaceAll( "³×", "Ne");    
		tmpStr = tmpStr.replaceAll( "³à", "Nyeo");  
		tmpStr = tmpStr.replaceAll( "³á", "Nyeok"); 
		tmpStr = tmpStr.replaceAll( "³â", "Nyeon"); 
		tmpStr = tmpStr.replaceAll( "³ä", "Nyeom"); 
		tmpStr = tmpStr.replaceAll( "³ç", "Nyeong");
		tmpStr = tmpStr.replaceAll( "³ë", "No");    
		tmpStr = tmpStr.replaceAll( "³ì", "Nok");   
		tmpStr = tmpStr.replaceAll( "³í", "Non");   
		tmpStr = tmpStr.replaceAll( "³î", "Nol");   
		tmpStr = tmpStr.replaceAll( "³ó", "Nong");  
		tmpStr = tmpStr.replaceAll( "³ú", "Noe");   
		tmpStr = tmpStr.replaceAll( "´©", "Nu");    
		tmpStr = tmpStr.replaceAll( "´«", "Nun");   
		tmpStr = tmpStr.replaceAll( "´­", "Nul");   
		tmpStr = tmpStr.replaceAll( "´À", "Neu");   
		tmpStr = tmpStr.replaceAll( "´Á", "Neuk");  
		tmpStr = tmpStr.replaceAll( "´Æ", "Neum");  
		tmpStr = tmpStr.replaceAll( "´É", "Neung"); 
		tmpStr = tmpStr.replaceAll( "´Ì", "Nui");   
		tmpStr = tmpStr.replaceAll( "´Ï", "Ni");    
		tmpStr = tmpStr.replaceAll( "´Ð", "Nik");   
		tmpStr = tmpStr.replaceAll( "´Ñ", "Nin");   
		tmpStr = tmpStr.replaceAll( "´Ò", "Nil");   
		tmpStr = tmpStr.replaceAll( "´Ô", "Nim");   
		tmpStr = tmpStr.replaceAll( "´Ù", "Da");    
		tmpStr = tmpStr.replaceAll( "´Ü", "Dan");   
		tmpStr = tmpStr.replaceAll( "´Þ", "Dal");   
		tmpStr = tmpStr.replaceAll( "´ã", "Dam");   
		tmpStr = tmpStr.replaceAll( "´ä", "Dap");   
		tmpStr = tmpStr.replaceAll( "´ç", "Dang");  
		tmpStr = tmpStr.replaceAll( "´ë", "Dae");   
		tmpStr = tmpStr.replaceAll( "´ì", "Daek");  
		tmpStr = tmpStr.replaceAll( "´õ", "Deo");   
		tmpStr = tmpStr.replaceAll( "´ö", "Deok");  
		tmpStr = tmpStr.replaceAll( "µµ", "Do");    
		tmpStr = tmpStr.replaceAll( "µ¶", "Dok");   
		tmpStr = tmpStr.replaceAll( "µ·", "Don");   
		tmpStr = tmpStr.replaceAll( "µ¹", "Dol");   
		tmpStr = tmpStr.replaceAll( "µ¿", "Dong");  
		tmpStr = tmpStr.replaceAll( "µÅ", "Dwae");  
		tmpStr = tmpStr.replaceAll( "µÇ", "Doe");   
		tmpStr = tmpStr.replaceAll( "µÈ", "Doen");  
		tmpStr = tmpStr.replaceAll( "µÎ", "Du");    
		tmpStr = tmpStr.replaceAll( "µÏ", "Duk");   
		tmpStr = tmpStr.replaceAll( "µÐ", "Dun");   
		tmpStr = tmpStr.replaceAll( "µÚ", "Dwi");   
		tmpStr = tmpStr.replaceAll( "µå", "Deu");   
		tmpStr = tmpStr.replaceAll( "µæ", "Deuk");  
		tmpStr = tmpStr.replaceAll( "µé", "Deul");  
		tmpStr = tmpStr.replaceAll( "µî", "Deung"); 
		tmpStr = tmpStr.replaceAll( "µð", "Di");    
		tmpStr = tmpStr.replaceAll( "µû", "Tta");   
		tmpStr = tmpStr.replaceAll( "¶¥", "Ttang"); 
		tmpStr = tmpStr.replaceAll( "¶§", "Ttae");  
		tmpStr = tmpStr.replaceAll( "¶Ç", "Tto");   
		tmpStr = tmpStr.replaceAll( "¶Ñ", "Ttu");   
		tmpStr = tmpStr.replaceAll( "¶Ò", "Ttuk");  
		tmpStr = tmpStr.replaceAll( "¶ß", "Tteu");  
		tmpStr = tmpStr.replaceAll( "¶ì", "Tti");   
		tmpStr = tmpStr.replaceAll( "¶ó", "Ra");    
		tmpStr = tmpStr.replaceAll( "¶ô", "Rak");   
		tmpStr = tmpStr.replaceAll( "¶õ", "Ran");   
		tmpStr = tmpStr.replaceAll( "¶÷", "Ram");   
		tmpStr = tmpStr.replaceAll( "¶û", "Rang");  
		tmpStr = tmpStr.replaceAll( "·¡", "Rae");   
		tmpStr = tmpStr.replaceAll( "·©", "Raeng"); 
		tmpStr = tmpStr.replaceAll( "·®", "Ryang"); 
		tmpStr = tmpStr.replaceAll( "··", "Reong"); 
		tmpStr = tmpStr.replaceAll( "·¹", "Re");    
		tmpStr = tmpStr.replaceAll( "·Á", "Ryeo");  
		tmpStr = tmpStr.replaceAll( "·Â", "Ryeok"); 
		tmpStr = tmpStr.replaceAll( "·Ã", "Ryeon"); 
		tmpStr = tmpStr.replaceAll( "·Ä", "Ryeol"); 
		tmpStr = tmpStr.replaceAll( "·Å", "Ryeom"); 
		tmpStr = tmpStr.replaceAll( "·Æ", "Ryeop"); 
		tmpStr = tmpStr.replaceAll( "·É", "Ryeong");
		tmpStr = tmpStr.replaceAll( "·Ê", "Rye");   
		tmpStr = tmpStr.replaceAll( "·Î", "Ro");    
		tmpStr = tmpStr.replaceAll( "·Ï", "Rok");   
		tmpStr = tmpStr.replaceAll( "·Ð", "Ron");   
		tmpStr = tmpStr.replaceAll( "·Õ", "Rong");  
		tmpStr = tmpStr.replaceAll( "·Ú", "Roe");   
		tmpStr = tmpStr.replaceAll( "·á", "Ryo");   
		tmpStr = tmpStr.replaceAll( "·æ", "Ryong"); 
		tmpStr = tmpStr.replaceAll( "·ç", "Ru");    
		tmpStr = tmpStr.replaceAll( "·ù", "Ryu");   
		tmpStr = tmpStr.replaceAll( "·ú", "Ryuk");  
		tmpStr = tmpStr.replaceAll( "·û", "Ryun");  
		tmpStr = tmpStr.replaceAll( "·ü", "Ryul");  
		tmpStr = tmpStr.replaceAll( "¸¢", "Ryung"); 
		tmpStr = tmpStr.replaceAll( "¸£", "Reu");   
		tmpStr = tmpStr.replaceAll( "¸¤", "Reuk");  
		tmpStr = tmpStr.replaceAll( "¸¥", "Reun");  
		tmpStr = tmpStr.replaceAll( "¸§", "Reum");  
		tmpStr = tmpStr.replaceAll( "¸ª", "Reung"); 
		tmpStr = tmpStr.replaceAll( "¸®", "Ri");    
		tmpStr = tmpStr.replaceAll( "¸°", "Rin");   
		tmpStr = tmpStr.replaceAll( "¸²", "Rim");   
		tmpStr = tmpStr.replaceAll( "¸³", "Rip");   
		tmpStr = tmpStr.replaceAll( "¸¶", "Ma");    
		tmpStr = tmpStr.replaceAll( "¸·", "Mak");   
		tmpStr = tmpStr.replaceAll( "¸¸", "Man");   
		tmpStr = tmpStr.replaceAll( "¸»", "Mal");   
		tmpStr = tmpStr.replaceAll( "¸Á", "Mang");  
		tmpStr = tmpStr.replaceAll( "¸Å", "Mae");   
		tmpStr = tmpStr.replaceAll( "¸Æ", "Maek");  
		tmpStr = tmpStr.replaceAll( "¸Ç", "Maen");  
		tmpStr = tmpStr.replaceAll( "¸Í", "Maeng"); 
		tmpStr = tmpStr.replaceAll( "¸Ó", "Meo");   
		tmpStr = tmpStr.replaceAll( "¸Ô", "Meok");  
		tmpStr = tmpStr.replaceAll( "¸Þ", "Me");    
		tmpStr = tmpStr.replaceAll( "¸ç", "Myeo");  
		tmpStr = tmpStr.replaceAll( "¸è", "Myeok"); 
		tmpStr = tmpStr.replaceAll( "¸é", "Myeon"); 
		tmpStr = tmpStr.replaceAll( "¸ê", "Myeol"); 
		tmpStr = tmpStr.replaceAll( "¸í", "Myeong");
		tmpStr = tmpStr.replaceAll( "¸ð", "Mo");    
		tmpStr = tmpStr.replaceAll( "¸ñ", "Mok");   
		tmpStr = tmpStr.replaceAll( "¸ô", "Mol");   
		tmpStr = tmpStr.replaceAll( "¸ø", "Mot");   
		tmpStr = tmpStr.replaceAll( "¸ù", "Mong");  
		tmpStr = tmpStr.replaceAll( "¸þ", "Moe");   
		tmpStr = tmpStr.replaceAll( "¹¦", "Myo");   
		tmpStr = tmpStr.replaceAll( "¹«", "Mu");    
		tmpStr = tmpStr.replaceAll( "¹¬", "Muk");   
		tmpStr = tmpStr.replaceAll( "¹®", "Mun");   
		tmpStr = tmpStr.replaceAll( "¹°", "Mul");   
		tmpStr = tmpStr.replaceAll( "¹Ç", "Meu");   
		tmpStr = tmpStr.replaceAll( "¹Ì", "Mi");    
		tmpStr = tmpStr.replaceAll( "¹Î", "Min");   
		tmpStr = tmpStr.replaceAll( "¹Ð", "Mil");   
		tmpStr = tmpStr.replaceAll( "¹Ù", "Ba");    
		tmpStr = tmpStr.replaceAll( "¹Ú", "Bak");   
		tmpStr = tmpStr.replaceAll( "¹Ý", "Ban");   
		tmpStr = tmpStr.replaceAll( "¹ß", "Bal");   
		tmpStr = tmpStr.replaceAll( "¹ä", "Bap");   
		tmpStr = tmpStr.replaceAll( "¹æ", "Bang");  
		tmpStr = tmpStr.replaceAll( "¹è", "Bae");   
		tmpStr = tmpStr.replaceAll( "¹é", "Baek");  
		tmpStr = tmpStr.replaceAll( "¹ì", "Baem");  
		tmpStr = tmpStr.replaceAll( "¹ö", "Beo");   
		tmpStr = tmpStr.replaceAll( "¹ø", "Beon");  
		tmpStr = tmpStr.replaceAll( "¹ú", "Beol");  
		tmpStr = tmpStr.replaceAll( "¹ü", "Beom");  
		tmpStr = tmpStr.replaceAll( "¹ý", "Beop");  
		tmpStr = tmpStr.replaceAll( "º­", "Byeo");  
		tmpStr = tmpStr.replaceAll( "º®", "Byeok"); 
		tmpStr = tmpStr.replaceAll( "º¯", "Byeon"); 
		tmpStr = tmpStr.replaceAll( "º°", "Byeol"); 
		tmpStr = tmpStr.replaceAll( "º´", "Byeong");
		tmpStr = tmpStr.replaceAll( "º¸", "Bo");    
		tmpStr = tmpStr.replaceAll( "º¹", "Bok");   
		tmpStr = tmpStr.replaceAll( "º»", "Bon");   
		tmpStr = tmpStr.replaceAll( "ºÀ", "Bong");  
		tmpStr = tmpStr.replaceAll( "ºÎ", "Bu");    
		tmpStr = tmpStr.replaceAll( "ºÏ", "Buk");   
		tmpStr = tmpStr.replaceAll( "ºÐ", "Bun");   
		tmpStr = tmpStr.replaceAll( "ºÒ", "Bul");   
		tmpStr = tmpStr.replaceAll( "ºØ", "Bung");  
		tmpStr = tmpStr.replaceAll( "ºñ", "Bi");    
		tmpStr = tmpStr.replaceAll( "ºó", "Bin");   
		tmpStr = tmpStr.replaceAll( "ºô", "Bil");   
		tmpStr = tmpStr.replaceAll( "ºö", "Bim");   
		tmpStr = tmpStr.replaceAll( "ºù", "Bing");  
		tmpStr = tmpStr.replaceAll( "ºü", "Ppa");   
		tmpStr = tmpStr.replaceAll( "»©", "Ppae");  
		tmpStr = tmpStr.replaceAll( "»µ", "Ppeo");  
		tmpStr = tmpStr.replaceAll( "»Ç", "Ppo");   
		tmpStr = tmpStr.replaceAll( "»Ñ", "Ppu");   
		tmpStr = tmpStr.replaceAll( "»Ú", "Ppeu");  
		tmpStr = tmpStr.replaceAll( "»ß", "Ppi");   
		tmpStr = tmpStr.replaceAll( "»ç", "Sa");    
		tmpStr = tmpStr.replaceAll( "»è", "Sak");   
		tmpStr = tmpStr.replaceAll( "»ê", "San");   
		tmpStr = tmpStr.replaceAll( "»ì", "Sal");   
		tmpStr = tmpStr.replaceAll( "»ï", "Sam");   
		tmpStr = tmpStr.replaceAll( "»ð", "Sap");   
		tmpStr = tmpStr.replaceAll( "»ó", "Sang");  
		tmpStr = tmpStr.replaceAll( "»ô", "Sat");   
		tmpStr = tmpStr.replaceAll( "»õ", "Sae");   
		tmpStr = tmpStr.replaceAll( "»ö", "Saek");  
		tmpStr = tmpStr.replaceAll( "»ý", "Saeng"); 
		tmpStr = tmpStr.replaceAll( "¼­", "Seo");   
		tmpStr = tmpStr.replaceAll( "¼®", "Seok");  
		tmpStr = tmpStr.replaceAll( "¼±", "Seon");  
		tmpStr = tmpStr.replaceAll( "¼³", "Seol");  
		tmpStr = tmpStr.replaceAll( "¼¶", "Seom");  
		tmpStr = tmpStr.replaceAll( "¼·", "Seop");  
		tmpStr = tmpStr.replaceAll( "¼º", "Seong"); 
		tmpStr = tmpStr.replaceAll( "¼¼", "Se");    
		tmpStr = tmpStr.replaceAll( "¼Å", "Syeo");  
		tmpStr = tmpStr.replaceAll( "¼Ò", "So");    
		tmpStr = tmpStr.replaceAll( "¼Ó", "Sok");   
		tmpStr = tmpStr.replaceAll( "¼Õ", "Son");   
		tmpStr = tmpStr.replaceAll( "¼Ö", "Sol");   
		tmpStr = tmpStr.replaceAll( "¼Ú", "Sot");   
		tmpStr = tmpStr.replaceAll( "¼Û", "Song");  
		tmpStr = tmpStr.replaceAll( "¼â", "Swae");  
		tmpStr = tmpStr.replaceAll( "¼è", "Soe");   
		tmpStr = tmpStr.replaceAll( "¼ö", "Su");    
		tmpStr = tmpStr.replaceAll( "¼÷", "Suk");   
		tmpStr = tmpStr.replaceAll( "¼ø", "Sun");   
		tmpStr = tmpStr.replaceAll( "¼ú", "Sul");   
		tmpStr = tmpStr.replaceAll( "¼û", "Sum");   
		tmpStr = tmpStr.replaceAll( "¼þ", "Sung");  
		tmpStr = tmpStr.replaceAll( "½¬", "Swi");   
		tmpStr = tmpStr.replaceAll( "½º", "Seu");   
		tmpStr = tmpStr.replaceAll( "½½", "Seul");  
		tmpStr = tmpStr.replaceAll( "½¿", "Seum");  
		tmpStr = tmpStr.replaceAll( "½À", "Seup");  
		tmpStr = tmpStr.replaceAll( "½Â", "Seung"); 
		tmpStr = tmpStr.replaceAll( "½Ã", "Si");    
		tmpStr = tmpStr.replaceAll( "½Ä", "Sik");   
		tmpStr = tmpStr.replaceAll( "½Å", "Sin");   
		tmpStr = tmpStr.replaceAll( "½Ç", "Sil");   
		tmpStr = tmpStr.replaceAll( "½É", "Sim");   
		tmpStr = tmpStr.replaceAll( "½Ê", "Sip");   
		tmpStr = tmpStr.replaceAll( "½Ì", "Sing");  
		tmpStr = tmpStr.replaceAll( "½Î", "Ssa");   
		tmpStr = tmpStr.replaceAll( "½Ö", "Ssang"); 
		tmpStr = tmpStr.replaceAll( "½Ø", "Ssae");  
		tmpStr = tmpStr.replaceAll( "½î", "Sso");   
		tmpStr = tmpStr.replaceAll( "¾¦", "Ssuk");  
		tmpStr = tmpStr.replaceAll( "¾¾", "Ssi");   
		tmpStr = tmpStr.replaceAll( "¾Æ", "A");     
		tmpStr = tmpStr.replaceAll( "¾Ç", "Ak");    
		tmpStr = tmpStr.replaceAll( "¾È", "An");    
		tmpStr = tmpStr.replaceAll( "¾Ë", "Al");    
		tmpStr = tmpStr.replaceAll( "¾Ï", "Am");    
		tmpStr = tmpStr.replaceAll( "¾Ð", "Ap");    
		tmpStr = tmpStr.replaceAll( "¾Ó", "Ang");   
		tmpStr = tmpStr.replaceAll( "¾Õ", "Ap");    
		tmpStr = tmpStr.replaceAll( "¾Ö", "Ae");    
		tmpStr = tmpStr.replaceAll( "¾×", "Aek");   
		tmpStr = tmpStr.replaceAll( "¾Þ", "Aeng");  
		tmpStr = tmpStr.replaceAll( "¾ß", "Ya");    
		tmpStr = tmpStr.replaceAll( "¾à", "Yak");   
		tmpStr = tmpStr.replaceAll( "¾á", "Yan");   
		tmpStr = tmpStr.replaceAll( "¾ç", "Yang");  
		tmpStr = tmpStr.replaceAll( "¾î", "Eo");    
		tmpStr = tmpStr.replaceAll( "¾ï", "Eok");   
		tmpStr = tmpStr.replaceAll( "¾ð", "Eon");   
		tmpStr = tmpStr.replaceAll( "¾ó", "Eol");   
		tmpStr = tmpStr.replaceAll( "¾ö", "Eom");   
		tmpStr = tmpStr.replaceAll( "¾÷", "Eop");   
		tmpStr = tmpStr.replaceAll( "¿¡", "E");     
		tmpStr = tmpStr.replaceAll( "¿©", "Yeo");   
		tmpStr = tmpStr.replaceAll( "¿ª", "Yeok");  
		tmpStr = tmpStr.replaceAll( "¿¬", "Yeon");  
		tmpStr = tmpStr.replaceAll( "¿­", "Yeol");  
		tmpStr = tmpStr.replaceAll( "¿°", "Yeom");  
		tmpStr = tmpStr.replaceAll( "¿±", "Yeop");  
		tmpStr = tmpStr.replaceAll( "¿µ", "Yeong"); 
		tmpStr = tmpStr.replaceAll( "¿¹", "Ye");    
		tmpStr = tmpStr.replaceAll( "¿À", "O");     
		tmpStr = tmpStr.replaceAll( "¿Á", "Ok");    
		tmpStr = tmpStr.replaceAll( "¿Â", "On");    
		tmpStr = tmpStr.replaceAll( "¿Ã", "Ol");    
		tmpStr = tmpStr.replaceAll( "¿È", "Om");    
		tmpStr = tmpStr.replaceAll( "¿Ë", "Ong");   
		tmpStr = tmpStr.replaceAll( "¿Í", "Wa");    
		tmpStr = tmpStr.replaceAll( "¿Ï", "Wan");   
		tmpStr = tmpStr.replaceAll( "¿Ð", "Wal");   
		tmpStr = tmpStr.replaceAll( "¿Õ", "Wang");  
		tmpStr = tmpStr.replaceAll( "¿Ö", "Wae");   
		tmpStr = tmpStr.replaceAll( "¿Ü", "Oe");    
		tmpStr = tmpStr.replaceAll( "¿Þ", "Oen");   
		tmpStr = tmpStr.replaceAll( "¿ä", "Yo");    
		tmpStr = tmpStr.replaceAll( "¿å", "Yok");   
		tmpStr = tmpStr.replaceAll( "¿ë", "Yong");  
		tmpStr = tmpStr.replaceAll( "¿ì", "U");     
		tmpStr = tmpStr.replaceAll( "¿í", "Uk");    
		tmpStr = tmpStr.replaceAll( "¿î", "Un");    
		tmpStr = tmpStr.replaceAll( "¿ï", "Ul");    
		tmpStr = tmpStr.replaceAll( "¿ò", "Um");    
		tmpStr = tmpStr.replaceAll( "¿õ", "Ung");   
		tmpStr = tmpStr.replaceAll( "¿ö", "Wo");    
		tmpStr = tmpStr.replaceAll( "¿ø", "Won");   
		tmpStr = tmpStr.replaceAll( "¿ù", "Wol");   
		tmpStr = tmpStr.replaceAll( "À§", "Wi");    
		tmpStr = tmpStr.replaceAll( "À¯", "Yu");    
		tmpStr = tmpStr.replaceAll( "À°", "Yuk");   
		tmpStr = tmpStr.replaceAll( "À±", "Yun");   
		tmpStr = tmpStr.replaceAll( "À²", "Yul");   
		tmpStr = tmpStr.replaceAll( "À¶", "Yung");  
		tmpStr = tmpStr.replaceAll( "À·", "Yut");   
		tmpStr = tmpStr.replaceAll( "À¸", "Eu");    
		tmpStr = tmpStr.replaceAll( "Àº", "Eun");   
		tmpStr = tmpStr.replaceAll( "À»", "Eul");   
		tmpStr = tmpStr.replaceAll( "À½", "Eum");   
		tmpStr = tmpStr.replaceAll( "À¾", "Eup");   
		tmpStr = tmpStr.replaceAll( "ÀÀ", "Eung");  
		tmpStr = tmpStr.replaceAll( "ÀÇ", "Ui");    
		tmpStr = tmpStr.replaceAll( "ÀÌ", "I");     
		tmpStr = tmpStr.replaceAll( "ÀÍ", "Ik");    
		tmpStr = tmpStr.replaceAll( "ÀÎ", "In");    
		tmpStr = tmpStr.replaceAll( "ÀÏ", "Il");    
		tmpStr = tmpStr.replaceAll( "ÀÓ", "Im");    
		tmpStr = tmpStr.replaceAll( "ÀÔ", "Ip");    
		tmpStr = tmpStr.replaceAll( "À×", "Ing");   
		tmpStr = tmpStr.replaceAll( "ÀÚ", "Ja");    
		tmpStr = tmpStr.replaceAll( "ÀÛ", "Jak");   
		tmpStr = tmpStr.replaceAll( "ÀÜ", "Jan");   
		tmpStr = tmpStr.replaceAll( "Àá", "Jam");   
		tmpStr = tmpStr.replaceAll( "Àâ", "Jap");   
		tmpStr = tmpStr.replaceAll( "Àå", "Jang");  
		tmpStr = tmpStr.replaceAll( "Àç", "Jae");   
		tmpStr = tmpStr.replaceAll( "Àï", "Jaeng"); 
		tmpStr = tmpStr.replaceAll( "Àú", "Jeo");   
		tmpStr = tmpStr.replaceAll( "Àû", "Jeok");  
		tmpStr = tmpStr.replaceAll( "Àü", "Jeon");  
		tmpStr = tmpStr.replaceAll( "Àý", "Jeol");  
		tmpStr = tmpStr.replaceAll( "Á¡", "Jeom");  
		tmpStr = tmpStr.replaceAll( "Á¢", "Jeop");  
		tmpStr = tmpStr.replaceAll( "Á¤", "Jeong"); 
		tmpStr = tmpStr.replaceAll( "Á¦", "Je");    
		tmpStr = tmpStr.replaceAll( "Á¶", "Jo");    
		tmpStr = tmpStr.replaceAll( "Á·", "Jok");   
		tmpStr = tmpStr.replaceAll( "Á¸", "Jon");   
		tmpStr = tmpStr.replaceAll( "Á¹", "Jol");   
		tmpStr = tmpStr.replaceAll( "Á¾", "Jong");  
		tmpStr = tmpStr.replaceAll( "ÁÂ", "Jwa");   
		tmpStr = tmpStr.replaceAll( "ÁË", "Joe");   
		tmpStr = tmpStr.replaceAll( "ÁÖ", "Ju");    
		tmpStr = tmpStr.replaceAll( "Á×", "Juk");   
		tmpStr = tmpStr.replaceAll( "ÁØ", "Jun");   
		tmpStr = tmpStr.replaceAll( "ÁÙ", "Jul");   
		tmpStr = tmpStr.replaceAll( "Áß", "Jung");  
		tmpStr = tmpStr.replaceAll( "Áã", "Jwi");   
		tmpStr = tmpStr.replaceAll( "Áî", "Jeu");   
		tmpStr = tmpStr.replaceAll( "Áï", "Jeuk");  
		tmpStr = tmpStr.replaceAll( "Áñ", "Jeul");  
		tmpStr = tmpStr.replaceAll( "Áò", "Jeum");  
		tmpStr = tmpStr.replaceAll( "Áó", "Jeup");  
		tmpStr = tmpStr.replaceAll( "Áõ", "Jeung"); 
		tmpStr = tmpStr.replaceAll( "Áö", "Ji");    
		tmpStr = tmpStr.replaceAll( "Á÷", "Jik");   
		tmpStr = tmpStr.replaceAll( "Áø", "Jin");   
		tmpStr = tmpStr.replaceAll( "Áú", "Jil");   
		tmpStr = tmpStr.replaceAll( "Áü", "Jim");   
		tmpStr = tmpStr.replaceAll( "Áý", "Jip");   
		tmpStr = tmpStr.replaceAll( "Â¡", "Jing");  
		tmpStr = tmpStr.replaceAll( "Â¥", "Jja");   
		tmpStr = tmpStr.replaceAll( "Â°", "Jjae");  
		tmpStr = tmpStr.replaceAll( "ÂÉ", "Jjo");   
		tmpStr = tmpStr.replaceAll( "Âî", "Jji");   
		tmpStr = tmpStr.replaceAll( "Â÷", "Cha");   
		tmpStr = tmpStr.replaceAll( "Âø", "Chak");  
		tmpStr = tmpStr.replaceAll( "Âù", "Chan");  
		tmpStr = tmpStr.replaceAll( "Âû", "Chal");  
		tmpStr = tmpStr.replaceAll( "Âü", "Cham");  
		tmpStr = tmpStr.replaceAll( "Ã¢", "Chang"); 
		tmpStr = tmpStr.replaceAll( "Ã¤", "Chae");  
		tmpStr = tmpStr.replaceAll( "Ã¥", "Chaek"); 
		tmpStr = tmpStr.replaceAll( "Ã³", "Cheo");  
		tmpStr = tmpStr.replaceAll( "Ã´", "Cheok"); 
		tmpStr = tmpStr.replaceAll( "Ãµ", "Cheon"); 
		tmpStr = tmpStr.replaceAll( "Ã¶", "Cheol"); 
		tmpStr = tmpStr.replaceAll( "Ã·", "Cheom"); 
		tmpStr = tmpStr.replaceAll( "Ã¸", "Cheop"); 
		tmpStr = tmpStr.replaceAll( "Ã»", "Cheong");
		tmpStr = tmpStr.replaceAll( "Ã¼", "Che");   
		tmpStr = tmpStr.replaceAll( "ÃÊ", "Cho");   
		tmpStr = tmpStr.replaceAll( "ÃË", "Chok");  
		tmpStr = tmpStr.replaceAll( "ÃÌ", "Chon");  
		tmpStr = tmpStr.replaceAll( "ÃÑ", "Chong"); 
		tmpStr = tmpStr.replaceAll( "ÃÖ", "Choe");  
		tmpStr = tmpStr.replaceAll( "Ãß", "Chu");
		tmpStr = tmpStr.replaceAll( "Ãë", "Chwi");
		tmpStr = tmpStr.replaceAll( "Ãà", "Chuk");  
		tmpStr = tmpStr.replaceAll( "Ãá", "Chun");  
		tmpStr = tmpStr.replaceAll( "Ãâ", "Chul");  
		tmpStr = tmpStr.replaceAll( "Ãã", "Chum");  
		tmpStr = tmpStr.replaceAll( "Ãæ", "Chung"); 
		tmpStr = tmpStr.replaceAll( "Ãø", "Cheuk"); 
		tmpStr = tmpStr.replaceAll( "Ãþ", "Cheung");
		tmpStr = tmpStr.replaceAll( "Ä¡", "Chi");   
		tmpStr = tmpStr.replaceAll( "Ä¢", "Chik");  
		tmpStr = tmpStr.replaceAll( "Ä£", "Chin");  
		tmpStr = tmpStr.replaceAll( "Ä¥", "Chil");  
		tmpStr = tmpStr.replaceAll( "Ä§", "Chim");  
		tmpStr = tmpStr.replaceAll( "Ä¨", "Chip");  
		tmpStr = tmpStr.replaceAll( "Äª", "Ching"); 
		tmpStr = tmpStr.replaceAll( "Ä«", "Ka");   
		tmpStr = tmpStr.replaceAll( "ÄÚ", "Ko");    
		tmpStr = tmpStr.replaceAll( "Äè", "Kwae");  
		tmpStr = tmpStr.replaceAll( "Å©", "Keu");   
		tmpStr = tmpStr.replaceAll( "Å«", "Keun");  
		tmpStr = tmpStr.replaceAll( "Å°", "Ki");    
		tmpStr = tmpStr.replaceAll( "Å¸", "Ta");    
		tmpStr = tmpStr.replaceAll( "Å¹", "Tak");   
		tmpStr = tmpStr.replaceAll( "Åº", "Tan");   
		tmpStr = tmpStr.replaceAll( "Å»", "Tal");   
		tmpStr = tmpStr.replaceAll( "Å½", "Tam");   
		tmpStr = tmpStr.replaceAll( "Å¾", "Tap");   
		tmpStr = tmpStr.replaceAll( "ÅÁ", "Tang");  
		tmpStr = tmpStr.replaceAll( "ÅÂ", "Tae");   
		tmpStr = tmpStr.replaceAll( "ÅÃ", "Taek");  
		tmpStr = tmpStr.replaceAll( "ÅÆ", "Taem");
		tmpStr = tmpStr.replaceAll( "ÅÊ", "Taeng"); 
		tmpStr = tmpStr.replaceAll( "ÅÍ", "Teo");   
		tmpStr = tmpStr.replaceAll( "Å×", "Te");   
		tmpStr = tmpStr.replaceAll( "ÅÛ", "Tem");
		tmpStr = tmpStr.replaceAll( "Åä", "To");    
		tmpStr = tmpStr.replaceAll( "Åæ", "Ton");   
		tmpStr = tmpStr.replaceAll( "Åç", "Tol");   
		tmpStr = tmpStr.replaceAll( "Åë", "Tong");  
		tmpStr = tmpStr.replaceAll( "Åð", "Toe");   
		tmpStr = tmpStr.replaceAll( "Åõ", "Tu");    
		tmpStr = tmpStr.replaceAll( "Åü", "Tung");  
		tmpStr = tmpStr.replaceAll( "Æ¢", "Twi");   
		tmpStr = tmpStr.replaceAll( "Æ®", "Teu");   
		tmpStr = tmpStr.replaceAll( "Æ¯", "Teuk");  
		tmpStr = tmpStr.replaceAll( "Æ´", "Teum");  
		tmpStr = tmpStr.replaceAll( "Æ¼", "Ti");    
		tmpStr = tmpStr.replaceAll( "ÆÄ", "Pa");    
		tmpStr = tmpStr.replaceAll( "ÆÇ", "Pan");   
		tmpStr = tmpStr.replaceAll( "ÆÈ", "Pal");   
		tmpStr = tmpStr.replaceAll( "ÆÐ", "Pae");   
		tmpStr = tmpStr.replaceAll( "ÆØ", "Paeng"); 
		tmpStr = tmpStr.replaceAll( "ÆÛ", "Peo");   
		tmpStr = tmpStr.replaceAll( "Æä", "Pe");    
		tmpStr = tmpStr.replaceAll( "Æì", "Pyeo");  
		tmpStr = tmpStr.replaceAll( "Æí", "Pyeon"); 
		tmpStr = tmpStr.replaceAll( "Æï", "Pyeom"); 
		tmpStr = tmpStr.replaceAll( "Æò", "Pyeong");
		tmpStr = tmpStr.replaceAll( "Æó", "Pye");   
		tmpStr = tmpStr.replaceAll( "Æ÷", "Po");    
		tmpStr = tmpStr.replaceAll( "Æø", "Pok");   
		tmpStr = tmpStr.replaceAll( "Ç¥", "Pyo");   
		tmpStr = tmpStr.replaceAll( "Çª", "Pu");    
		tmpStr = tmpStr.replaceAll( "Ç°", "Pum");   
		tmpStr = tmpStr.replaceAll( "Ç³", "Pung");  
		tmpStr = tmpStr.replaceAll( "ÇÁ", "Peu");   
		tmpStr = tmpStr.replaceAll( "ÇÇ", "Pi");    
		tmpStr = tmpStr.replaceAll( "ÇÈ", "Pik");   
		tmpStr = tmpStr.replaceAll( "ÇÊ", "Pil");   
		tmpStr = tmpStr.replaceAll( "ÇÌ", "Pip");   
		tmpStr = tmpStr.replaceAll( "ÇÏ", "Ha");    
		tmpStr = tmpStr.replaceAll( "ÇÐ", "Hak");   
		tmpStr = tmpStr.replaceAll( "ÇÑ", "Han");   
		tmpStr = tmpStr.replaceAll( "ÇÒ", "Hal");   
		tmpStr = tmpStr.replaceAll( "ÇÔ", "Ham");   
		tmpStr = tmpStr.replaceAll( "ÇÕ", "Hap");   
		tmpStr = tmpStr.replaceAll( "Ç×", "Hang");  
		tmpStr = tmpStr.replaceAll( "ÇØ", "Hae");   
		tmpStr = tmpStr.replaceAll( "ÇÙ", "Haek");  
		tmpStr = tmpStr.replaceAll( "Çà", "Haeng"); 
		tmpStr = tmpStr.replaceAll( "Çâ", "Hyang"); 
		tmpStr = tmpStr.replaceAll( "Çã", "Heo");   
		tmpStr = tmpStr.replaceAll( "Çå", "Heon");  
		tmpStr = tmpStr.replaceAll( "Çè", "Heom");  
		tmpStr = tmpStr.replaceAll( "Çì", "He");    
		tmpStr = tmpStr.replaceAll( "Çô", "Hyeo");  
		tmpStr = tmpStr.replaceAll( "Çõ", "Hyeok"); 
		tmpStr = tmpStr.replaceAll( "Çö", "Hyeon"); 
		tmpStr = tmpStr.replaceAll( "Ç÷", "Hyeol"); 
		tmpStr = tmpStr.replaceAll( "Çø", "Hyeom"); 
		tmpStr = tmpStr.replaceAll( "Çù", "Hyeop"); 
		tmpStr = tmpStr.replaceAll( "Çü", "Hyeong");
		tmpStr = tmpStr.replaceAll( "Çý", "Hye");   
		tmpStr = tmpStr.replaceAll( "È£", "Ho");    
		tmpStr = tmpStr.replaceAll( "È¤", "Hok");   
		tmpStr = tmpStr.replaceAll( "È¥", "Hon");   
		tmpStr = tmpStr.replaceAll( "È¦", "Hol");   
		tmpStr = tmpStr.replaceAll( "È©", "Hop");   
		tmpStr = tmpStr.replaceAll( "È«", "Hong");  
		tmpStr = tmpStr.replaceAll( "È­", "Hwa");   
		tmpStr = tmpStr.replaceAll( "È®", "Hwak");  
		tmpStr = tmpStr.replaceAll( "È¯", "Hwan");  
		tmpStr = tmpStr.replaceAll( "È°", "Hwal");  
		tmpStr = tmpStr.replaceAll( "È²", "Hwang"); 
		tmpStr = tmpStr.replaceAll( "È³", "Hwae");  
		tmpStr = tmpStr.replaceAll( "È¶", "Hwaet"); 
		tmpStr = tmpStr.replaceAll( "È¸", "Hoe");   
		tmpStr = tmpStr.replaceAll( "È¹", "Hoek");  
		tmpStr = tmpStr.replaceAll( "È¾", "Hoeng"); 
		tmpStr = tmpStr.replaceAll( "È¿", "Hyo");   
		tmpStr = tmpStr.replaceAll( "ÈÄ", "Hu");    
		tmpStr = tmpStr.replaceAll( "ÈÆ", "Hun");   
		tmpStr = tmpStr.replaceAll( "ÈÍ", "Hwon");  
		tmpStr = tmpStr.replaceAll( "ÈÑ", "Hwe");   
		tmpStr = tmpStr.replaceAll( "ÈÖ", "Hwi");   
		tmpStr = tmpStr.replaceAll( "ÈÞ", "Hyu");   
		tmpStr = tmpStr.replaceAll( "Èá", "Hyul");  
		tmpStr = tmpStr.replaceAll( "Èä", "Hyung"); 
		tmpStr = tmpStr.replaceAll( "Èå", "Heu");   
		tmpStr = tmpStr.replaceAll( "Èæ", "Heuk");  
		tmpStr = tmpStr.replaceAll( "Èç", "Heun");  
		tmpStr = tmpStr.replaceAll( "Èê", "Heul");  
		tmpStr = tmpStr.replaceAll( "Èì", "Heum");  
		tmpStr = tmpStr.replaceAll( "Èí", "Heup");  
		tmpStr = tmpStr.replaceAll( "Èï", "Heung"); 
		tmpStr = tmpStr.replaceAll( "Èñ", "Hui");   
		tmpStr = tmpStr.replaceAll( "Èò", "Huin");  
		tmpStr = tmpStr.replaceAll( "È÷", "Hi");    
		tmpStr = tmpStr.replaceAll( "Èû", "Him");   
		tmpStr = tmpStr.replaceAll( "  ", " ");
		tmpStr = tmpStr.replaceAll( " ", "_");
		
		
		return tmpStr;	
	}
	
	/**
	 * int °ªÀ» ¹ÝÈ¯ÇÑ´Ù.
	 * 
	 * @param str
	 * @return
	 */
    public static int getParseInt( String str ) {
    	int num = 0;
    	if ((str == null) || ("".equals(str.trim())) ) {
    		str = "0";
    	}
    	
    	num = Integer.parseInt(str.trim());
    	return num;
    } 
}
