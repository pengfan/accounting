package com.codingpower.accounting.analysis;

import java.util.HashMap;
import java.util.Map;

public class Constants {

	public static int BankType_cmb = 1;
	public static int BankType_tran = 2;
	public static int BankType_hangzhou = 3;
	public static Map<Integer, String> bankMap = new HashMap<Integer, String>();
	
	static
	{
		bankMap.put(BankType_cmb, "招商银行");
		bankMap.put(BankType_tran, "交通银行");
		bankMap.put(BankType_hangzhou, "杭州银行");
	}
	
	public static String getBankName(int type)
	{
		return bankMap.get(type);
	}
}
