package com.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtility {
	// 월일시분초 데이타를 얻는다.
	public synchronized final static String getMMddHHmmss() {
		SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss", java.util.Locale.KOREA);
		return sdf.format(new Date());
	}
	
	// 년월일 데이타를 얻는다.
	public synchronized final static String getyyyyMMdd() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", java.util.Locale.KOREA);
		return sdf.format(new Date());
	}	
	
	// 년월일시분초 데이타를 얻는다.
	public synchronized final static String getyyyyMMddHHmmss() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", java.util.Locale.KOREA);
		return sdf.format(new Date());
	}
	
	//	 시분초
	public synchronized final static String getHHmmss() {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss", java.util.Locale.KOREA);
		return sdf.format(new Date());
	}
	
	public static String getDateString(String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.KOREA);
		return formatter.format(new Date()); 
	}
	
	public static String getShortTimeString() {
		SimpleDateFormat formatter = new SimpleDateFormat("HHmmss", Locale.KOREA);
		return formatter.format(new Date()); 
	}
}
