package com.codingpower.accounting.analysis.banktype;

import java.util.HashMap;
import java.util.Map;

import com.codingpower.accounting.analysis.Constants;
import com.codingpower.accounting.analysis.Meta;
import com.codingpower.accounting.analysis.SMSStringType;

/**
 * 交通银行
 * @author fortransit
 *
 */
public class TranType implements SMSStringType {

	@Override
	public int bank() {
		return Constants.BankType_tran;
	}
	
	@Override
	public String phone() {
		return "95559";
	}

	@Override
	public String pattern() {
		return "您尾号(\\d+)[^\\d]+(.*?)在([^\\d]+)([\\d\\.]+)元.*";
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
