package com.codingpower.accounting;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.codingpower.accounting.MouthStatsFragmentAdapter.DateTitle;
import com.codingpower.accounting.analysis.Constants;
import com.codingpower.accounting.analysis.adapter.ContentAdapter;
import com.codingpower.accounting.model.Record;

/**
 * 月统计界面
 * 
 * @author fortransit
 *
 */
public final class MouthStatsFragment extends Fragment {
    private static final String KEY_CONTENT = "MouthStatsFragment:month";
    private static final SimpleDateFormat ItemDateFormat = new SimpleDateFormat("MM-dd");
    
    private DateTitle dateTitle;
    private List<Record> monthRecordList = new ArrayList<Record>();
    private RecordListAdapter recordListAdapter;
    
    private View totalStatusBar;
    private TextView totalIncomeView, totalExpenseView;

    public static MouthStatsFragment newInstance(DateTitle dateTitle) {
        MouthStatsFragment fragment = new MouthStatsFragment();
        fragment.dateTitle = dateTitle;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
        	month = savedInstanceState.getInt(KEY_CONTENT);
        }*/
        recordListAdapter = new RecordListAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.month_record_list, null);
    	totalStatusBar = view.findViewById(R.id.totalStatusBar);
    	totalIncomeView = (TextView) view.findViewById(R.id.totalIncomeView);
    	totalExpenseView = (TextView) view.findViewById(R.id.totalExpensesView);
    	ListView listView = findView(view, R.id.recordList);
    	listView.setAdapter(recordListAdapter);
    	getMonthList();
        return view;
    }
    
    public void getMonthList()
    {
    	monthRecordList = ((MainActivity)getActivity()).getMonthRecord(dateTitle);
    	recordListAdapter.notifyDataSetChanged();
    	if(!monthRecordList.isEmpty())
    	{
    		totalStatusBar.setVisibility(View.VISIBLE);
        	BigDecimal totalIncome = new BigDecimal(0);
        	BigDecimal totalExpense = new BigDecimal(0);
        	for(Record rec : monthRecordList)
        	{
        		if(rec.getMoneyType() == ContentAdapter.INCOME)
        		{
        			totalIncome = totalIncome.add(rec.getMoney());
        		}
        		else if(rec.getMoneyType() == ContentAdapter.EXPENSES)
        		{
        			totalExpense = totalExpense.add(rec.getMoney());
        		}
        	}
        	totalIncomeView.setText(totalIncome.toString());
        	totalExpenseView.setText(totalExpense.toString());
    	}
    	
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putInt(KEY_CONTENT, month);
    }
    
    public <T extends View>T findView(View view, int resId)
    {
    	return (T)view.findViewById(resId);
    }
    
    private class RecordListAdapter extends BaseAdapter
    {

		@Override
		public int getCount() {
			return monthRecordList.size();
		}

		@Override
		public Object getItem(int position) {
			return monthRecordList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null)
			{
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.record_list_item, null);
				RecordItemHolder holder = new RecordItemHolder();
				holder.bankImgView = findView(convertView, R.id.bankImgView);
				holder.bankAccountView = findView(convertView, R.id.bankAccountView);
				holder.recordTypeView = findView(convertView, R.id.recordTypeView);
				holder.recordDetailView = findView(convertView, R.id.recordDetailView);
				holder.sumView = findView(convertView, R.id.sumView);
				holder.dateView = findView(convertView, R.id.dateView);
				convertView.setTag(holder);
			}
			RecordItemHolder holder = (RecordItemHolder)convertView.getTag();
			Record rec = monthRecordList.get(position);
			holder.bankAccountView.setText(Constants.getBankName(rec.getBank()));
			holder.recordTypeView.setText(rec.getDetail());
			holder.recordDetailView.setVisibility(View.GONE);
			String moneyStr = rec.getMoney().toString();
			switch(rec.getMoneyType())
			{
				case ContentAdapter.EXPENSES:
				{
					moneyStr = "-" + moneyStr;
					holder.sumView.setTextColor(getResources().getColor(R.color.sum_type_expenses));
					break;
				}
				case ContentAdapter.INCOME:
				{
					moneyStr = "+" + moneyStr;
					holder.sumView.setTextColor(getResources().getColor(R.color.sum_type_income));
					break;
				}
				case ContentAdapter.UNKNOWN:
				{
					holder.sumView.setTextColor(getResources().getColor(R.color.sum_type_unknown));
					break;
				}
			}
			holder.sumView.setText(moneyStr);
			holder.dateView.setText(ItemDateFormat.format(rec.getDateTime().getTime()));
			return convertView;
		}
    }
    private static class RecordItemHolder
    {
    	public ImageView bankImgView;
    	public TextView bankAccountView;
    	public TextView recordTypeView;
    	public TextView recordDetailView;
    	public TextView sumView;
    	public TextView dateView;
    }
}

