package com.furen.listener;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.spi.LoggingEvent;

/**
 * @author xiajj
 *
 */
public class FileAppender extends RollingFileAppender {
	private static SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
	private static String initFileName = null;
	public void append(LoggingEvent event) {  
		synchronized(this) {  
			if(initFileName == null){
				initFileName = this.getFile();
			}
			String currentDay = dtFormat.format(new Date());
			String tempfileName = initFileName + "_" + currentDay;
			if(!this.getFile().contains(tempfileName)){
				this.closeFile();
				setFile(tempfileName+".log");
				activateOptions();
			}
			super.append(event);
		}
    }  
}
