package com.codingpower.accounting.analysis.adapter;

import java.util.HashSet;
import java.util.Set;

/**
 * 内容分析器，将内容进行分析，目前只是区分是消费还是收入
 * @author fortransit
 *
 */
public class ContentAdapter {
	public static final int EXPENSES = -1;//支出
	public static final int INCOME = 1;//收入
	public static final int UNKNOWN = 0;//未知
	
	private static Set<String> expenseskeyWordSet = new HashSet<String>();
	private static Set<String> incomekeyWordSet = new HashSet<String>();
	
	static 
	{
		expenseskeyWordSet.add("支付");
		expenseskeyWordSet.add("消费");
		expenseskeyWordSet.add("取现");
		incomekeyWordSet.add("工资");
	}
	
	/**
	 * 如果是支出的话返回"-"，否则返回""
	 * @param content
	 * @return
	 */
	public int analysis(String content)
	{
		for(String keyword: expenseskeyWordSet)
		{
			if(content.contains(keyword))
				return EXPENSES;
		}
		for(String keyword: incomekeyWordSet)
		{
			if(content.contains(keyword))
				return INCOME;
		}
		return UNKNOWN;
	}
}
