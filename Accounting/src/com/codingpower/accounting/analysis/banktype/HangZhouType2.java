package com.codingpower.accounting.analysis.banktype;

import java.util.HashMap;
import java.util.Map;

import com.codingpower.accounting.analysis.Constants;
import com.codingpower.accounting.analysis.Meta;
import com.codingpower.accounting.analysis.SMSStringType;

/**
 * 杭州银行
 * @author fortransit
 *
 */
public class HangZhouType2 implements SMSStringType {

	@Override
	public int bank() {
		return Constants.BankType_hangzhou;
	}
	
	@Override
	public String phone() {
		return "1065905710008296523";
	}

	@Override
	public String pattern() {
		return "您尾号(\\d+).*卡(\\d+月\\d+日\\d+:\\d+)([^\\d]+?)([\\d\\.]+).*";
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
