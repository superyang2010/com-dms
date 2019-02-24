package com.dms.pub.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * <b>Summary: </b><br/>
 *      &nbsp;&nbsp;&nbsp;&nbsp;日期工具类
 *      <br/>
 * <b>Remarks: </b><br/>
 *        &nbsp;&nbsp;&nbsp;&nbsp;日期工具类
 *	@author yangchao
 */
public class DateUtil {
	/**
	 * 默认日期格式<br/> 
	 * &nbsp;&nbsp;&nbsp;&nbsp;{@value}
	 */
	public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	
	/**
	 * 默认时间格式<br/> 
	 * &nbsp;&nbsp;&nbsp;&nbsp;{@value}
	 */
	public final static String DEFAULT_TIME_FORMAT = "HH:mm:ss";
	
	/**
	 * 默认日期时间格式<br/> 
	 * &nbsp;&nbsp;&nbsp;&nbsp;{@value}
	 */
	public final static String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 默认时间戳格式<br/> 
	 * &nbsp;&nbsp;&nbsp;&nbsp;{@value}
	 */
	public final static String DEFAULT_DATESTAMP_FORMAT = "yyyyMMddHHmmssSSS";
	
	private DateUtil(){}
	
	/**
	 * 获取当前日期
	 * @return 当前日期对象
	 */
	public static Date getNow(){
		return Calendar.getInstance().getTime();
	}
	
	/**
	 * 获取当前日期
	 * @param format		日期格式
	 * @return 					指定格式的当前日期字符串
	 * @see #dateToStr(Date, String)
	 */
	public static String getNow(String format){
		return dateToStr(getNow(), format);
	}
	
	/**
	 * 获取当前日期(不带时间)
	 * @return 当前日期字符串格式<br/>
	 * 默认格式 : {@value #DEFAULT_DATE_FORMAT} 
	 * @see #getNow(String)
	 */
	public static String getCurrentDate(){
		return getNow(DEFAULT_DATE_FORMAT);
	}
	
	/**
	 * 获取当前时间
	 * @return 当前时间字符串格式<br/>
	 * 默认格式 : {@value #DEFAULT_TIME_FORMAT} 
	 * @see #getNow(String)
	 */
	public static String getCurrentTime(){
		return getNow(DEFAULT_TIME_FORMAT);
	}
	
	/**
	 * 获取当前日期时间(带时间)
	 * @return 当前日期字符串格式<br/>
	 * 默认格式 : {@value #DEFAULT_DATE_TIME_FORMAT} 
	 * @see #getNow(String)
	 */
	public static String getCurrentDateTime(){
		return getNow(DEFAULT_DATE_TIME_FORMAT);
	}
	
	
	/**
	 * 获取明天的日期
	 * @return 明天的日期对象
	 */
	public static Date getTomorrow(){
		return getEndDateByDay(getNow(), 1);
	}
	
	/**
	 * 获取明天的日期,指定了日期格式
	 * @param format 日期格式
	 * @return 指定格式的明天的日期串
	 */
	public static String getTomorrow(String format){
		return dateToStr(getTomorrow(), format);
	}
	
	/**
	 * 获取昨天的日期
	 * @return 昨天的日期对象
	 */
	public static Date getYesterday(){
		return getEndDateByDay(getNow(), -1);
	}
	
	/**
	 * 获取昨天的日期,指定了日期格式
	 * @param format 日期格式
	 * @return 指定格式的昨天的日期串
	 */
	public static String getYesterday(String format){
		return dateToStr(getYesterday(), format);
	}
	
	/**
	 * 取得指定年月日的日期对象.
	 * @param year 年
	 * @param month 月,注意是从1到12
	 * @param day 日
	 * @return 一个java.util.Date()类型的对象
	 */
	public static Date getDate(int year, int month, int day) {
	    Calendar c = new GregorianCalendar();
	    c.set(year, month - 1, day);
	    return c.getTime();
	}
	
	/**
	 * 取得指定年 月 日 时 分 秒的日期对象.
	 * @param year 年
	 * @param month 月 1-12
	 * @param day 日
	 * @param hour 小时 0-23
	 * @param minute  分钟 0-59
	 * @param second  秒 0-59
	 * @return 一个java.util.Date()类型的对象
	 */
	public static Date getDate(int year, int month, int day, int hour, int minute, int second) {
	    Calendar c = new GregorianCalendar();
	    c.set(year, month - 1, day, hour, minute, second);
	    return c.getTime();
	}
	
	/**  
	  * 取得当前日期的年份，以yyyy格式返回.  
	  * @return 当年 yyyy  
	  */ 
	 public static String getCurrentYear() {  
	      return getNow("yyyy");  
	 }

	 /**
	  * 自动返回上一年。例如当前年份是2007年，那么就自动返回2006
	  * @return 返回结果的格式为 yyyy
	  */
	 public static String getPreYear() {  
	     return dateToStr(getEndDateByYear(getNow(), -1),"yyyy");
	 }
	 
	 /**
	  * 自动返回下一年。例如当前年份是2007年，那么就自动返回2008
	  * @return 返回结果的格式为 yyyy
	  */
	 public static String getNextYear() {  
		 return dateToStr(getEndDateByYear(getNow(), 1),"yyyy");
	 }
	 
	 /**
	  * 取得当前日期的月份，以MM格式返回.
	  * @return 当前月份的格式为 MM
	  */
	 public static String getCurrentMonth() {
	     return getNow("MM");
	 }
	 	 
	 /**
	  * 取得当前日期的上个月份，以MM格式返回.<br/>
	  * 例如当前月份是06年，那么就自动返回05
	  * @return 上个月份的格式为 MM
	  */
	 public static String getPreMonth() {
		 return dateToStr(getEndDateByMonth(getNow(), -1),"MM");
	 }
	 
	 /**
	  * 取得当前日期的下个月份，以MM格式返回.<br/>
	  * 例如当前月份是06年，那么就自动返回07
	  * @return 下个月份的格式为 MM
	  */
	 public static String getNextMonth() {
		 return dateToStr(getEndDateByMonth(getNow(), 1),"MM");
	 }
	 
	 /**
	  * 取得当前日期的号数，以dd格式返回.
	  * @return 当前日期的号数的格式为 dd
	  */
	 public static String getCurrentDay() {
	     return getNow("dd");
	 }
	 
	 /**
	  * 取得当前日期的昨天，以dd格式返回.<br/>
	  * 例如当前日期是05号，那么就自动返回04
	  * @return 当前日期的昨天,格式为 MM
	  */
	 public static String getPreDay() {
		 return getYesterday("dd");
	 }
	 
	 /**
	  * 取得当前日期的明天，以dd格式返回.<br/>
	  * 例如当前日期是05号，那么就自动返回06
	  * @return 当前日期的明天,格式为 MM
	  */
	 public static String getNextDay() {
	     return getTomorrow("dd");
	 }
	 
	/**
	 * 格式化日期
	 * @param date			需要格式化的日期对象
	 * @param format		日期格式
	 * @return					指定格式的日期字符串
	 */
	public static  String dateToStr(Date date,String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);	
		String dateString = dateFormat.format(date);
		return dateString;
	}

	/**
	 * 格式化日期<br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;默认格式 : {@value #DEFAULT_DATE_TIME_FORMAT} 
	 * @param date	需要格式化的日期对象
	 * @return			默认格式的日期字符串
	 * @see #dateToStr(Date, String)
	 */
	public static  String dateToStr(Date date) {
		return dateToStr(date,DEFAULT_DATE_TIME_FORMAT);
	}
	
	/**
	 * 获取当前时间戳
	 * <br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;默认格式 : {@value #DEFAULT_DATESTAMP_FORMAT} 
	 * @return 时间戳字符串
	 * @see #dateToStr(Date, String)
	 */
	public static String getTimestamp(){
		return dateToStr(getNow(),DEFAULT_DATESTAMP_FORMAT);
	}
	
	/**
	 * 获取当前时间戳
	 * @param format	时间戳格式
	 * @return 指定格式的时间戳字符串
	 * @see #dateToStr(Date, String)
	 */
	public static String getTimestamp(String format){
		return dateToStr(getNow(),format);
	}
	
	/**
	 * 将字符串形式的日期, 转换成日期对象.
	 * @param date		转换对象
	 * @param format	转换格式
	 * @return				指定格式的日期字符串
	 */
	public static Date strToDate(String date,String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format); 
        try {
        	return dateFormat.parse(date) ;
        } catch(ParseException e) {
        	throw new RuntimeException("Can not convert String to Date, cause: " + e.getMessage()) ;
        }
	}	
	
	/**
	 * 将字符串形式的日期, 转换成日期对象<br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;默认格式 : {@value #DEFAULT_DATE_TIME_FORMAT} 	
	 * @param date	转换对象
	 * @return			转换结果
	 * @see #strToDate(String, String)
	 */
	public static Date strToDate(String date) {
        return strToDate(date,DEFAULT_DATE_TIME_FORMAT);
	}
	
	/**
	 * 根据起始日期和间隔天计算结束日期
	 * @param sDate 	开始时间
	 * @param type 		计算单位(Calendar.YEAR，Calendar.MONTH，Calendar.DAY 等等，参照 Calendar 中的常量)
	 * @param interval	间隔时间(与计算单位对应)
	 * @return 				结束时间
	 * */
	public static Date getEndDate(Date sDate, int type, int interval){
		Calendar sCalendar = Calendar.getInstance();
		sCalendar.setTime(sDate);
		sCalendar.add(type, interval);
		return sCalendar.getTime();
	}
	
	/**
	 * 根据起始日期和间隔毫秒数计算结束日期
	 * @param sDate 			开始时间
	 * @param milliSecond	间隔时间(毫秒)
	 * @return 						结束时间
	 * */
	public static Date getEndDateByMilliSecond(Date sDate, int milliSecond){
		return getEndDate(sDate, Calendar.MILLISECOND, milliSecond);
	}
	
	/**
	 * 根据起始日期和间隔秒数计算结束日期
	 * @param sDate 	开始时间
	 * @param second	间隔时间(秒)
	 * @return 				结束时间
	 * */
	public static Date getEndDateBySecond(Date sDate, int second){
		return getEndDate(sDate, Calendar.SECOND, second);
	}
	
	/**
	 * 根据起始日期和间隔分钟计算结束日期
	 * @param sDate 	开始时间
	 * @param minute	间隔时间(分钟)
	 * @return 				结束时间
	 * */
	public static Date getEndDateByMinute(Date sDate, int minute){
		return getEndDate(sDate, Calendar.MINUTE, minute);
	}
	
	/**
	 * 根据起始日期和间隔小时计算结束日期
	 * @param sDate 	开始时间
	 * @param hour		间隔时间(小时)
	 * @return 				结束时间
	 * */
	public static Date getEndDateByHour(Date sDate, int hour){
		return getEndDate(sDate, Calendar.HOUR, hour);
	}
	
	/**
	 * 根据起始日期和间隔天计算结束日期
	 * @param sDate 	开始时间
	 * @param days 		间隔时间(天)
	 * @return 				结束时间
	 * */
	public static Date getEndDateByDay(Date sDate, int days){
		return getEndDate(sDate, Calendar.DATE, days);
	}
	
	/**
	 * 根据起始日期和间隔月计算结束日期
	 * @param sDate 		开始时间
	 * @param monthes	间隔时间(月)
	 * @return 					结束时间
	 * */
	public static Date getEndDateByMonth(Date sDate, int monthes){
		return getEndDate(sDate, Calendar.MONTH, monthes);
	}
	
	/**
	 * 根据起始日期和间隔年计算结束日期
	 * @param sDate 	开始时间
	 * @param years		间隔时间(年)
	 * @return 				结束时间
	 * */
	public static Date getEndDateByYear(Date sDate, int years){
		return getEndDate(sDate, Calendar.YEAR, years);
	}
	
	/**  
     * 计算两个日期的时间间隔(毫秒)  
     * @param sDate 		开始时间  
     * @param eDate 		结束时间  
     * @return interval 		时间间隔(毫秒)
     * */  
    public static long getDiffByMilliSecond(Date sDate, Date eDate)  {  
        return eDate.getTime()-sDate.getTime();  
    } 
    
    /**  
     * 计算两个日期的时间间隔(秒)  
     * @param sDate 		开始时间  
     * @param eDate 		结束时间  
     * @return interval 		时间间隔(秒)
     * */  
    public static long getDiffBySecond(Date sDate, Date eDate)  {  
        return getDiffByMilliSecond(sDate, eDate)/1000;  
    }
    
    /**  
     * 计算两个日期的时间间隔(分钟)  
     * @param sDate 		开始时间  
     * @param eDate 		结束时间  
     * @return interval 		时间间隔(分钟)
     * */  
    public static long getDiffByMinute(Date sDate, Date eDate)  {  
        return getDiffBySecond(sDate, eDate)/60;  
    }
    
    /**  
     * 计算两个日期的时间间隔(小时)  
     * @param sDate 		开始时间  
     * @param eDate 		结束时间  
     * @return interval 		时间间隔(小时)
     * */  
    public static long getDiffByHour(Date sDate, Date eDate)  {  
        return getDiffByMinute(sDate, eDate)/60;
    }
    
    /**  
     * 计算两个日期的时间间隔(天)  
     * @param sDate 		开始时间  
     * @param eDate 		结束时间  
     * @return interval 		时间间隔(天)
     * */  
    public static long getDiffByDay(Date sDate, Date eDate)  {  
        return getDiffByHour(sDate, eDate)/24;
    }
    
    /**
     * 获取指定日期是星期几
     * @param date	日期
     * @return	一个代表当期日期是星期几的数字。1表示星期天、2表示星期一、7表示星期六。
     */
    public static int getDayOfWeek(Date date){
    	Calendar cal = new GregorianCalendar();
    	cal.setTime(date);
    	return cal.get(Calendar.DAY_OF_WEEK);
    }
    
    /**
     * 获取指定日期是当月第几天
     * @param date	日期
     * @return	一个代表当前日期是当月第几天的数字
     */
    public static int getDayOfMonth(Date date){
    	Calendar cal = new GregorianCalendar();
    	cal.setTime(date);
    	return cal.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 获取指定日期的当月有多少天
     * @param date	日期
     * @return	当月的天数
     */
    public static int getDaysOfMonth(Date date){
    	int mArray[] = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30,31};
    	if(isLeapYear(date)){
    		mArray[1] = 29;
    	}
    	return mArray[getMonthOfYear(date)-1];
    }

    /**
     * 获取指定日期是当年第几天
     * @param date	日期
     * @return	一个代表当前日期是当年第几天的数字
     */
    public static int getDayOfYear(Date date){
    	Calendar cal = new GregorianCalendar();
    	cal.setTime(date);
    	return cal.get(Calendar.DAY_OF_YEAR);
    }
    
    /**
     * 获取指定日期是当月中的第几周
     * @param date	日期
     * @return	返回一个表示第几周的数字
     */
    public static int getWeekOfMonth(Date date){
    	Calendar cal = new GregorianCalendar();
    	cal.setTime(date);
    	return cal.get(Calendar.WEEK_OF_MONTH);
    }
    
    /**
     * 获取指定日期是当年中的第几周
     * @param date	日期
     * @return	返回一个表示第几周的数字
     */
    public static int getWeekOfYear(Date date){
    	Calendar cal = new GregorianCalendar();
    	cal.setTime(date);
    	return cal.get(Calendar.WEEK_OF_YEAR);
    }
    
    /**
     * 获取指定日期是当年中的第几个月, 1-12
     * @param date	日期
     * @return	返回一个表示第几个月的数字
     */
    public static int getMonthOfYear(Date date){
    	Calendar cal = new GregorianCalendar();
    	cal.setTime(date);
    	return cal.get(Calendar.MONTH)+1;
    }
    
    /**
     * 获取指定开始和结束年的年份列表
     * @param startYear	开始年份
     * @param endYear		结束年份
     * @return					年份列表
     */
    public static String[] getSelectYear(int startYear, int endYear){
    	if(startYear>endYear||startYear<0||endYear<0){
    		return new String[0];
    	}
    	String[] yearList = new String[endYear-startYear+1] ;
    	for (int i = startYear; i <= endYear; i++) {
    		yearList[i-startYear] = String.valueOf(i);
		}
    	return yearList;
    }
    
    /**
     * 获取指定开始和结束月的月份列表
     * @param startMonth	开始月份 1-12 超出范围取1
     * @param endMonth		结束月份 1-12 超出范围取12
     * @return					月份列表
     */
    public static String[] getSelectMonth(int startMonth, int endMonth){
    	if(startMonth>endMonth){
    		return new String[0];
    	}
    	startMonth = (startMonth<0||startMonth>12)?1:startMonth;
    	endMonth = (endMonth<0||endMonth>12)?12:endMonth;
    	String[] monthList = new String[endMonth-startMonth+1] ;
    	for (int i = startMonth; i <= endMonth; i++) {
    		monthList[i-startMonth] = String.valueOf(i);
		}
    	return monthList;
    }
    
    /**
     * 判断日期是否在指定范围内
     * @param date	判断对象
     * @param start	开始日期
     * @param end	结束日期
     * @return	判断结果, true/false 
     */
    public static boolean isInRange(Date date, Date start, Date end){
    	if(compareDate(start, date) <=0 && compareDate(end, date) >=0){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    /**
     * 比较两个日期
     * @param sDate 日期1
     * @param eDate 日期2
     * @return 
     *     0 ： 两个日期相等 <br/>
     *     -1：前者小于后者 <br/>
     *      1： 前者大于后者 <br/>
     */
    public static int compareDate(Date sDate,Date eDate){
    	Calendar sCalendar = Calendar.getInstance();
		sCalendar.setTime(sDate);
		Calendar eCalendar = Calendar.getInstance();
		eCalendar.setTime(eDate);
		return sCalendar.compareTo(eCalendar);
    }
    
    /**
     * 时区之间日期转换
     * @param date						待转换日期(日期对象)
     * @param sourceTimeZone	源时区
     * @param targetTimeZone		目标时区
     * @return								转换后目标时区的时间
     */
    public static Date transformDateBetweenTimeZone(Date date, TimeZone sourceTimeZone, TimeZone targetTimeZone) {   
        Long targetTime = date.getTime() - sourceTimeZone.getRawOffset() + targetTimeZone.getRawOffset();   
        return new Date(targetTime);   
    }
    
    /**
     * 时区之间日期转换
     * @param dateStr					待转换日期(字符串格式)
     * @param dateFormat			与之匹配的日期格式
     * @param sourceTimeZone	源时区
     * @param targetTimeZone		目标时区
     * @return								转换后目标时区的时间
     * @see #strToDate(String, String)
     * @see #transformDateBetweenTimeZone(Date, TimeZone, TimeZone)
     */
    public static Date transformDateBetweenTimeZone(String dateStr, String dateFormat, TimeZone sourceTimeZone, TimeZone targetTimeZone) {   
    	Date date = strToDate(dateStr,dateFormat);
    	return transformDateBetweenTimeZone(date, sourceTimeZone, targetTimeZone);
    }   
    
    /**
     * 获取所有时区
     * @return 所有时区的数组列表
     */
    public static String[] getAllTimeZone() {   
        List<String> v = new  ArrayList<String>(); 
        String[] timeZones = TimeZone.getAvailableIDs();   
        for  ( int  i =  0 ; i < timeZones.length; i++) {   
            v.add(timeZones[i]);   
        }   
        Collections.sort(v, String.CASE_INSENSITIVE_ORDER);   
        timeZones = v.toArray(timeZones);
        v = null ;   
        return  timeZones;   
    }
    
    /**
     * 判断某个日期是否是闰年
     * @param date	判断日期
     * @return	判断结果 true/false
     */
    private static boolean isLeapYear(Date date){
    	int year = Integer.valueOf(dateToStr(date, "yyyy"));
    	return  (year%4 == 0 && year%100 != 0) || (year%400 == 0);
    }
}
