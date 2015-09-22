package com.ideal.framework.utils.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ideal.framework.utils.string.EmptyUtil;


/** 
* 2015-4-8 下午05:59:35
* author:himo
* mail:zhangyao0905@gmail.com
* descript:日期格式化工具类
*/ 
public class DateFormatter {
	
	protected static Logger logger = LoggerFactory.getLogger(DateFormatter.class);
	
	/**
	 * yyyy-M-d HH:mm:ss
	 * */
	public static final String SIMPLE_LONG_DATE = "yyyy-M-d HH:mm:ss";
	/**
	 * yyyy/MM/dd
	 * */
	public static final String SIMPLE_LONG_DATE2 = "yyyy/MM/dd";
	/**
	 * MM/dd
	 * */
	public static final String SIMPLE_LONG_DATE3 = "MM/dd";
	/**
	 * yyyy年M月d日
	 * */
	public static final String CHINESE_SHORT_DATE = "yyyy年M月d日";

	/**
	 * yyyy-M-d
	 * */
	public static final String SIMPLE_SHORT_DATE = "yyyy-M-d";

	/**
	 * yyyy-MM-dd
	 * */
	public static final String SIMPLE_SHORT_DATE_SHOW = "yyyy-MM-dd";
	/**
	 * yyyy-MM-dd HH:mm:ss
	 * */
	public static final String SIMPLE_LONG_DATE_SHOW = "yyyy-MM-dd HH:mm:ss";

	/**
	 * EEE MMM dd yyyy HH:mm:ss 'GMT'Z
	 * */
	public static final String SIMPLE_LONG_DATE_DOJO = "EEE MMM dd yyyy HH:mm:ss 'GMT'Z";

	private static final String[] weekDays = { "", "星期日", "星期一", "星期二", "星期三",
			"星期四", "星期五", "星期六" };

	/**
	 * 格式化今日中文短日期
	 * 
	 * @return
	 */
	public static final String formatChineseShortToday() {
		return formatDate(new Date(), CHINESE_SHORT_DATE);
	}

	/**
	 * 格式化日期，返回格式化后的字符串
	 * 
	 * @param date
	 * @param patter
	 * @return
	 */
	public static final String formatDate(Date date) {
		if(date ==null)return "";
		try {
				SimpleDateFormat sdf = new SimpleDateFormat(
						SIMPLE_SHORT_DATE_SHOW, Locale.ENGLISH);
				return sdf.format(date);
		} catch (Exception e) {
			logger.error("格式化日期出错", e);
			return null;
		}

	}
	
	/**
	 * 格式化日期，返回格式化后的字符串
	 * 
	 * @param date
	 * @param patter
	 * @return
	 */
	public static final String formatDate(Date date, String pattern) {
		if(date ==null)return "";
		try {
			if (SIMPLE_SHORT_DATE.equals(pattern)) {
				SimpleDateFormat sdf = new SimpleDateFormat(
						SIMPLE_SHORT_DATE_SHOW, Locale.ENGLISH);
				return sdf.format(date);
			}
			SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
			return sdf.format(date);
		} catch (Exception e) {
			logger.error("格式化日期出错", e);
			return null;
		}

	}

	/**
	 * @param sDate
	 * @param pattern
	 * @return
	 */
	public static final Date formatDate(String sDate, String pattern) {
		if(EmptyUtil.isEmpty(sDate))return null;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
		try {
			return sdf.parse(sDate);
		} catch (Exception e) {
			logger.error("格式化日期出错", e);
			return null;
		}
	}

	public static final Date formatDate3(String sDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(sDate);
		} catch (Exception e) {
			logger.error("格式化日期出错", e);
			return null;
		}
	}

	/**
	 * @param sDate
	 * @param pattern
	 * @return
	 */
	public static final String formatDate2(Date sDate, String pattern)
			throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
		return sdf.format(sDate);

	}

	/**
	 * 得到中文星期
	 * 
	 * @return
	 */
	public static final String getChineseWeekDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return weekDays[c.get(Calendar.DAY_OF_WEEK)];
	}

	/**
	 * 得到例如 200803 格式的日期
	 * @return
	 * @throws Exception
	 */
	public static String getCurrentYearMonth() throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		return df.format(new Date());
	}

	/**
	 * return yyyy-MM-dd
	 * */
	public static String getCurrentYearMonthDay() throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(new Date());
	}
	
	public static String getCurrentYearMonth(String pattern) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(new Date());
	}

	/**
	 * 得到当前年份例如 2008 格式的日期
	 * @return
	 * @throws Exception
	 */
	public static String getCurrentYear() throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		return df.format(new Date());
	}

	/**
	 * 得到当前月份例如 04 格式的日期
	 * @return
	 * @throws Exception
	 */
	public static String getCurrentMonth() throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("MM");
		return df.format(new Date());
	}

	public static synchronized java.util.Calendar getNextWeek(
			java.util.Calendar gc) {
		/**
		 * 详细设计： 1.指定日期加7天
		 */
		gc.add(Calendar.DATE, 7);
		return gc;
	}

	public static synchronized java.util.Date getNextWeek(java.util.Date date) {
		/**
		 * 详细设计： 1.指定日期加7天
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, 7);
		return gc.getTime();
	}

	public static String getNextWeek(String date, int day) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		/**
		 * 详细设计： 1.指定日期累加
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(sdf.parse(date));
		gc.add(Calendar.DATE, day);
		return sdf.format(gc.getTime());

	}

	public static synchronized java.util.Date getLastWeek(java.util.Date date) {
		/**
		 * 详细设计： 1.指定日期加7天
		 * 
		 * 
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, -7);
		return gc.getTime();
	}

	public static synchronized java.util.Date getFirstDayOfWeek(
			java.util.Date date) {
		/**
		 * 详细设计： 1.如果date是星期日，则减0天 2.如果date是星期一，则减1天 3.如果date是星期二，则减2天
		 * 
		 * 
		 * 4.如果date是星期三，则减3天 5.如果date是星期四，则减4天 6.如果date是星期五，则减5天
		 * 
		 * 
		 * 7.如果date是星期六，则减6天
		 * 
		 * 
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.SUNDAY):
			gc.add(Calendar.DATE, -6);
			break;
		case (Calendar.MONDAY):
			gc.add(Calendar.DATE, 0);
			break;
		case (Calendar.TUESDAY):
			gc.add(Calendar.DATE, -1);
			break;
		case (Calendar.WEDNESDAY):
			gc.add(Calendar.DATE, -2);
			break;
		case (Calendar.THURSDAY):
			gc.add(Calendar.DATE, -3);
			break;
		case (Calendar.FRIDAY):
			gc.add(Calendar.DATE, -4);
			break;
		case (Calendar.SATURDAY):
			gc.add(Calendar.DATE, -5);
			break;
		}
		return gc.getTime();
	}

	/**
	 * 获取指定日期所在一周内的星期一的日期
	 * 
	 * 
	 * @param date
	 * @return
	 */
	public static synchronized java.util.Date getFirstDayOfNextWeek(
			java.util.Date date) {
		/**
		 * 详细设计： 1.调用getNextWeek设置当前时间 2.以1为基础，调用getFirstDayOfWeek
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.setTime(getNextWeek(gc.getTime()));
		gc.setTime(getFirstDayOfWeek(gc.getTime()));
		return gc.getTime();
	}

	/**
	 * 获取指定日期所在一周内的星期一的日期
	 * 
	 * 
	 * @param date
	 * @return
	 */
	public static synchronized java.util.Date getFirstDayOfLastWeek(
			java.util.Date date) {
		/**
		 * 详细设计： 1.调用getNextWeek设置当前时间 2.以1为基础，调用getFirstDayOfWeek
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		// gc.setTime(date);
		// gc.setTime(getLastWeek(gc.getTime()));
		gc.setTime(getFirstDayOfWeek(date));
		return gc.getTime();
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static synchronized java.util.Date getLastDayOfWeek(
			java.util.Date date) {
		/**
		 * 详细设计： 1.如果date是星期日，则加6天 2.如果date是星期一，则加5天 3.如果date是星期二，则加4天
		 * 
		 * 
		 * 4.如果date是星期三，则加3天 5.如果date是星期四，则加2天 6.如果date是星期五，则加1天
		 * 
		 * 
		 * 7.如果date是星期六，则加0天
		 * 
		 * 
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.SUNDAY):
			gc.add(Calendar.DATE, 0);
			break;
		case (Calendar.MONDAY):
			gc.add(Calendar.DATE, 6);
			break;
		case (Calendar.TUESDAY):
			gc.add(Calendar.DATE, 5);
			break;
		case (Calendar.WEDNESDAY):
			gc.add(Calendar.DATE, 4);
			break;
		case (Calendar.THURSDAY):
			gc.add(Calendar.DATE, 3);
			break;
		case (Calendar.FRIDAY):
			gc.add(Calendar.DATE, 2);
			break;
		case (Calendar.SATURDAY):
			gc.add(Calendar.DATE, 1);
			break;
		}
		return gc.getTime();
	}

	/**
	 * 根据日期获取周一日期
	 * 
	 * @param date
	 * @return
	 */
	public static String getMonday(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = format.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		// 关于DAY_OF_WEEK的说明
		// Field number for get and set indicating
		// the day of the week. This field takes values
		// SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY,
		// and SATURDAY
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return format.format(cal.getTime());
	}

	/**
	 * 根据日期获取周日日期
	 * 
	 * @param date
	 * @return
	 */
	public static String getSunday(String date) throws Exception {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
		Date d = format.parse(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, 6);
		return format.format(cal.getTime());

	}

	/**
	 * 根据当前日期 得到对应的星期和 日期
	 */
	public static String getDateByWeek(String datetime, int weekNum)
			throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date2 = sdf.parse(datetime);
		Calendar c = Calendar.getInstance();
		c.setTime(date2);
		c.setFirstDayOfWeek(Calendar.MONDAY);

		if (weekNum == 1) {
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		}
		if (weekNum == 2) {
			c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		}
		if (weekNum == 3) {
			c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		}
		if (weekNum == 4) {
			c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		}
		if (weekNum == 5) {
			c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		}
		if (weekNum == 6) {
			c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		}
		if (weekNum == 7) {
			c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		}
		return sdf.format(c.getTime());

	}

	public static void main(String[] args) {

		int i = compare_date("1995-11-12 15:21", "1999-12-11 09:59");
		System.out.println("i==" + i);

	}

	public static String getOneWeekDate(String date) throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getMonday(date)).append("|");
		buffer.append(getNextWeek(getMonday(date), 1)).append("|");
		buffer.append(getNextWeek(getMonday(date), 2)).append("|");
		buffer.append(getNextWeek(getMonday(date), 3)).append("|");
		buffer.append(getNextWeek(getMonday(date), 4)).append("|");
		buffer.append(getNextWeek(getMonday(date), 5)).append("|");
		buffer.append(getNextWeek(getMonday(date), 6)).append("|");
		return buffer.toString().substring(0, buffer.toString().length() - 1);
	}

	public static String changeDate(String date, int flag) {
		if (flag == 1) {
			return date.replaceAll("-", ".");
		} else {
			return date.substring(5, date.length()).replaceAll("-", ".");
		}

	}

	/**
	 * 比较两个日期的大小
	 * 
	 * @param DATE1
	 * @param DATE2
	 * @return 1 date1>date2 -1 date1<date2 0 date1=date2
	 */
	public static int compare_date(String DATE1, String DATE2) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				System.out.println("dt1 在dt2前");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				System.out.println("dt1在dt2后");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}
	
	 /**
	 * 按年月日生成文件夹名
	 * @return String like: 2015/4/3/
	 * */
    public static String getDatePath(){
    	Calendar date = Calendar.getInstance();
    	int day = date.get(Calendar.DAY_OF_MONTH);
    	int month = date.get(Calendar.MONTH) + 1;
    	int year = date.get(Calendar.YEAR);
    	String datePath = String.valueOf(year).trim()+"/"+String.valueOf(month).trim()+"/"+String.valueOf(day).trim()+"/";
    	return datePath;
    }
    
    /**
     * yyyy-M-d HH:mm:ss
     * */
	public static final String formatSimpleLongDate(Date date) {
		if(date == null){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_LONG_DATE,Locale.ENGLISH);
		return sdf.format(date);
	}
	
	/**
	 * yyyy-M-d HH:mm:ss
	 * */
	public static final String formatSimpleShortDate(Date date) {
		if(date == null){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_SHORT_DATE,Locale.ENGLISH);
		return sdf.format(date);
	}
}
