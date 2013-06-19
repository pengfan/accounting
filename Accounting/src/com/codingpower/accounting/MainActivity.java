package com.codingpower.accounting;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.codingpower.accounting.MouthStatsFragmentAdapter.DateTitle;
import com.codingpower.accounting.analysis.adapter.SMSAdapter;
import com.codingpower.accounting.model.Record;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

public class MainActivity extends SherlockFragmentActivity {

	MouthStatsFragmentAdapter mAdapter;
    ViewPager mPager;
    PageIndicator mIndicator;
    
    private List<Record> recordList;
    private SMSAdapter smsAdapter;
    
    protected void onCreate(Bundle savedInstanceState) {
    	setTheme(R.style.Theme_Sherlock_Light);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mAdapter = new MouthStatsFragmentAdapter(this, getSupportFragmentManager());

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        mIndicator = (TitlePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        
        smsAdapter = new SMSAdapter();
        init();
    }
    
    private void init()
    {
    	recordList = smsAdapter.analysis(this);
    	mAdapter.extractFrom(recordList);
    }
    
    public List<Record> getMonthRecord(DateTitle dateTitle)
    {
    	List<Record> monthList = new ArrayList<Record>();
    	boolean started = false;
    	for(Record rec : recordList)
    	{
    		if(dateTitle.includes(rec))
    		{
    			monthList.add(rec);
    			started = true;
    		}
    		else if(started)
    		{
    			break;
    		}
    			
    		/*else if(rec.getMonth() > month)
    		{
    			break;
    		}*/
    	}
    	return monthList;
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        }
        return super.onOptionsItemSelected(item);
    }
}
