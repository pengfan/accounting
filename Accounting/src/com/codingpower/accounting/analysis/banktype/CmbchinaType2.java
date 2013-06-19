package com.codingpower.accounting.analysis.banktype;

import java.util.HashMap;
import java.util.Map;

import com.codingpower.accounting.analysis.Constants;
import com.codingpower.accounting.analysis.Meta;
import com.codingpower.accounting.analysis.SMSStringType;

/**
 * 招商银行 ATM 记录
 * @author fortransit
 *
 */
public class CmbchinaType2 implements SMSStringType {

	@Override
	public int bank() {
		return Constants.BankType_cmb;
	}
	
	@Override
	public String phone() {
		return "95555";
	}

	@Override
	public String pattern() {
		return "贵卡(\\d+)于(\\d+月\\d+日\\d+时\\d+分)([^\\d]+)([\\d\\.]+).*";
	}

	@Override
	public Map<Integer, String> meaningMap() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, Meta.ACCOUNT);
		map.put(2, Meta.DATETIME);
		map.put(3, Meta.TYPE);
		map.put(4, Meta.SUM);
		return map;
	}
}
