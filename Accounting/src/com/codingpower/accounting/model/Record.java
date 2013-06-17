package com.codingpower.accounting.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Record {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	private int bank;//隶属于哪个银行
	private String account;//帐号
	private Calendar dateTime;//记录时间
	private int moneyType;//收支类型
	private BigDecimal money;//正为收入，负为支出
	private String detail;//详细信息
	private int year;
	private int month;
	
	public int getBank() {
		return bank;
	}
	public void setBank(int bank) {
		this.bank = bank;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Calendar getDateTime() {
		return dateTime;
	}
	public void setDateTime(Calendar calendar) {
		this.dateTime = calendar;
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public int getYear() {
		return year;
	}
	public int getMonth() {
		return month;
	}
	public int getMoneyType() {
		return moneyType;
	}
	public void setMoneyType(int moneyType) {
		this.moneyType = moneyType;
	}
	@Override
	public String toString()
	{
		return bank +"\n" + account+"\n" + sdf.format(dateTime.getTime())+"\n" + money+"\n" + detail;
	}
}
