package com.codingpower.accounting.analysis.adapter;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 分析时间字符串，转换为时间类型
 * 
 * @author fortransit
 * 
 */
public class DateAdapter {

	private static String patterns[];

	static {
		patterns = new String[4];
		patterns[0] = "(\\d+)月(\\d+)日(\\d+):(\\d+)";
		patterns[1] = "(\\d+)月(\\d+)日(\\d+)时(\\d+)分";
		patterns[2] = "\\d+年(\\d+)月(\\d+)日";
	}

	/**
	 * 分析时间数据，如果不符合格式则返回null
	 * 
	 * @param year
	 * @param content
	 * @return
	 */
	public Calendar analysis(int year, String content) {
		try {
			for (String patternStr : patterns) {
				Pattern pattern = Pattern.compile(patternStr);
				Matcher matcher = pattern.matcher(content);
				if (matcher.matches()) {
					Calendar calendar = Calendar.getInstance();
					calendar.set(Calendar.YEAR, year);
					calendar.set(Calendar.MONTH,getFieldInt(matcher, 1));
					calendar.set(Calendar.DAY_OF_MONTH,getFieldInt(matcher, 2));
					if(matcher.groupCount() >= 4)
					{
						calendar.set(Calendar.HOUR_OF_DAY,getFieldInt(matcher, 3));
						calendar.set(Calendar.MINUTE,getFieldInt(matcher, 4));
					}
					return calendar;
				}
			}
			return null;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private int getFieldInt(Matcher matcher, int index) {
		return Integer.parseInt(matcher.group(index));
	}
}
