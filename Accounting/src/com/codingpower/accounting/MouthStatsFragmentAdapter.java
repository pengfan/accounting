package com.codingpower.accounting;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codingpower.accounting.model.Record;
import com.viewpagerindicator.IconPagerAdapter;

class MouthStatsFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
	private List<DateTitle> DateTitleList;

    public MouthStatsFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        DateTitleList = new ArrayList<DateTitle>();
    }
    
    public void extractFrom(List<Record> recordList)
    {
    	DateTitle last = null;
    	for(Record rec : recordList)
    	{
    		if(last == null)
    		{
    			last = new DateTitle(rec);
    			DateTitleList.add(last);
    		}
    		else if(!last.includes(rec))
    		{
    			last = new DateTitle(rec);
    			DateTitleList.add(last);
    		}
    	}
    	notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return MouthStatsFragment.newInstance(DateTitleList.get(position));
    }

    @Override
    public int getCount() {
        return DateTitleList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return DateTitleList.get(position).toString();
    }

    @Override
    public int getIconResId(int index) {
      return R.drawable.ic_launcher;
    }
    
    public static class DateTitle
    {
    	private int year;
    	private int month;
    	
    	public DateTitle(Record rec)
    	{
    		year = rec.getYear();
    		month = rec.getMonth();
    	}
    	
    	public boolean includes(Record rec)
    	{
    		if(year == rec.getYear() && month == rec.getMonth())
    			return true;
    		return false;
    	}
    	
    	public String toString()
    	{
    		return year + "年" + month + "月";
    	}
    }

}