package com.run;

import org.apache.log4j.Level;

import com.property.CobraProperty;
import com.property.CobraValue;
import com.util.DateUtility;

public class CobraRunTest {

	public static void main(String[] args) throws Exception {
		if(CobraProperty.getSystemProperty()) {}
		
		while ( true ) {
			int time = 60 * 60 * 1;
				
			CobraRun_Backup cRun = new CobraRun_Backup();
				
			boolean isTrue = cRun.requestReservaionNoList(time);
				
			cRun.getInboundDetl();
		}

	}
}
