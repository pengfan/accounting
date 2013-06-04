package com.codingpower.accounting.analysis;

import java.util.HashMap;
import java.util.Map;

public class Constants {

	public static int BankType_cmb = 1;
	public static Map<Integer, String> bankMap = new HashMap<Integer, String>();
	
	static
	{
		bankMap.put(BankType_cmb, "招商银行");
	}
}
