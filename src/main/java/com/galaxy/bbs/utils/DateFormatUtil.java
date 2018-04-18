/**
 *
 * Create on 2018年3月3日 下午12:34:44
 *
 * author user
 * 
 */
package com.galaxy.bbs.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
    yyyy：年  
	MM：月  
	dd：日  
	hh：1~12小时制(1-12)  
	HH：24小时制(0-23)  
	mm：分  
	ss：秒  
	S：毫秒  
	E：星期几  
	D：一年中的第几天  
	F：一月中的第几个星期(会把这个月总共过的天数除以7)  
	w：一年中的第几个星期  
	W：一月中的第几星期(会根据实际情况来算)  
	a：上下午标识  
	k：和HH差不多，表示一天24小时制(1-24)。  
	K：和hh差不多，表示一天12小时制(0-11)。  
	z：表示时区   
 */
public class DateFormatUtil {

	public final static String SIMPLE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public final static SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
	
	public static String getSimpleDateFormat(Date date) {
		return sdf.format(date);
	}
	
	/**
	 * 获取格式化的当前系统时间
	 * @return
	 */
	public static String getSimpleDateFormat() {
		return sdf.format(new Date());
	}
}
