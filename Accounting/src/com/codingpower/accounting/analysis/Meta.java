package com.codingpower.accounting.analysis;

import java.util.Date;

/**
 * 分析元数据
 * @author fortransit
 *
 */
public class Meta {
	
	public static final String DATETIME = "datetime";
	public static final String TYPE = "type";
	public static final String SUM = "sum";
	public static final String ACCOUNT = "account";
	
	private int bank;
	private String datetime;
	private String type;
	private String sum;
	private String account;
	private Date smsDate;
	
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSum() {
		return sum;
	}
	public void setSum(String sum) {
		this.sum = sum;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public int getBank() {
		return bank;
	}
	public void setBank(int bank) {
		this.bank = bank;
	}
	public Date getSmsDate() {
		return smsDate;
	}
	public void setSmsDate(Date smsDate) {
		this.smsDate = smsDate;
	}
	@Override
	public String toString() {
		return datetime +"\n" + account+"\n"+ sum + "\n" + type + "\n" + smsDate.getTime();
	}
}
