package com.codingpower.accounting.analysis.adapter;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.codingpower.accounting.analysis.Meta;
import com.codingpower.accounting.analysis.SMSStringType;
import com.codingpower.accounting.analysis.banktype.CmbchinaType;
import com.codingpower.accounting.analysis.banktype.CmbchinaType2;
import com.codingpower.accounting.analysis.banktype.HangZhouType1;
import com.codingpower.accounting.analysis.banktype.HangZhouType2;
import com.codingpower.accounting.analysis.banktype.TranType;
import com.codingpower.accounting.model.Record;

public class SMSAdapter {
	
	private String TAG = "SMSAdapter";
	private Map<String, Method> metaMethodMap = new HashMap<String, Method>();
	private ContentAdapter contentAdapter = new ContentAdapter();
	private DateAdapter dateAdapter = new DateAdapter();
	
	private Calendar smsDateCalendar = Calendar.getInstance();
	private Set<SMSStringType> typeSet = new HashSet<SMSStringType>();
	private Map<String, Pattern> patternCache = new HashMap<String, Pattern>();

	public SMSAdapter()
	{
		typeSet.add(new CmbchinaType());
		typeSet.add(new CmbchinaType2());
		typeSet.add(new HangZhouType1());
		typeSet.add(new HangZhouType2());
		typeSet.add(new TranType());
	}
	
	/**
	 * 从短信中获取信息，转换为meta列表
	 * @param context
	 * @return
	 */
	public List<Record> analysis(Context context)
	{
		init();
		Uri uriSMSURI = Uri.parse("content://sms/inbox");
		Cursor cur = context.getContentResolver().query(uriSMSURI, null, null, null,
				null);
		String[] test = cur.getColumnNames();
		for(int i = 0 ; i < test.length; i++)
		{
			Log.i(TAG, i+"," +test[i]);
		}
		Date date = new Date();
		List<Record> metaList = new ArrayList<Record>();
		while (cur.moveToNext()) {
			date.setTime(cur.getLong(4));
			String phoneNum = cur.getString(2);
			String content = cur.getString(12);
			
			Meta meta = generateMeta(date, phoneNum, content);
			if(meta != null)
			{
				if("1065905710008396523".equals(phoneNum) 
						|| "1065905710008296523".equals(phoneNum) ){
					Log.i(TAG, content);
				}
				
				metaList.add(changeToRecord(meta));
			}
		}
		return metaList;
	}
	
	private Meta generateMeta(Date date, String phoneNum, String content)
	{
		for(SMSStringType smsType: typeSet)
		{
			if(phoneNum.equals(smsType.phone()))
			{
				//从缓存中取出Pattern
				Pattern pattern = patternCache.get(smsType.pattern());
				if(pattern == null)
				{
					pattern = Pattern.compile(smsType.pattern());
					patternCache.put(smsType.pattern(), pattern);
				}
				Meta meta = patternToMeta(date, smsType.bank(), content, pattern, smsType.meaningMap());
				if(meta != null)
				{
					return meta;
				}
			}
		}
		return null;
	}
	
	private void init()
	{
		for(Method m: Meta.class.getMethods())
		{
			if(m.getName().startsWith("set"))
			{
				String name = m.getName().substring(3).toLowerCase();
				metaMethodMap.put(name, m);
			}
		}
	}
	/**
	 * 将正则表达式中代表的group匹配项和meta中的属性对应赋值
	 * 
	 * @param metaList
	 * @param content
	 * @param pattern
	 * @param map
	 */
	private Meta patternToMeta(Date smsDate, int bank, String content,Pattern pattern, Map<Integer,String> map)
	{
		Matcher matcher = pattern.matcher(content);
		if(matcher.matches())
		{
			Meta meta = new Meta();
			meta.setSmsDate(smsDate);
			meta.setBank(bank);
			for(int i = 1; i <= matcher.groupCount(); i++)
			{
				String propName = map.get(i);
				String propVal = matcher.group(i);
				setVal(meta, propName, propVal);
			}
			return meta;
		}
		return null;
	}
	
	private void setVal(Meta meta, String propName, String propVal)
	{
		Method m = metaMethodMap.get(propName);
		Set set = metaMethodMap.keySet();
		//Log.w(TAG, propName + "," + String.valueOf(m));
		if(metaMethodMap.containsKey(propName))
		{
			try {
				metaMethodMap.get(propName).invoke(meta, propVal);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private Record changeToRecord(Meta meta)
	{
		Record record = new Record();
		record.setBank(meta.getBank());
		//TODO:要增加一个帐号管理，这里只是帐号的尾号
		record.setAccount(meta.getAccount());
		smsDateCalendar.setTime(meta.getSmsDate());
		record.setDateTime(dateAdapter.analysis(
				smsDateCalendar.get(Calendar.YEAR),
				meta.getDatetime()));
		String content = meta.getType();
		record.setDetail(content);
		BigDecimal money = new BigDecimal(meta.getSum());
		record.setMoney(money);
		record.setMoneyType(contentAdapter.analysis(content));
		return record;
	}
}

