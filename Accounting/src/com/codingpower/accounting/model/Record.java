package com.codingpower.accounting.model;

import java.math.BigDecimal;
import java.util.Date;

public class Record {

	private int bank;//隶属于哪个银行
	private String account;//帐号
	private Date dateTime;//记录时间
	private BigDecimal money;//正为收入，负为支出
	private String detail;//详细信息
	
}
