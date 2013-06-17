package com.codingpower.accounting;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.IconPagerAdapter;

class MouthStatsFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
	private String[] mMonthArray;
    private int mCount;

    public MouthStatsFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        mMonthArray = context.getResources().getStringArray(R.array.month);
        mCount = mMonthArray.length;
    }

    @Override
    public Fragment getItem(int position) {
        return MouthStatsFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return mMonthArray[position % mMonthArray.length];
    }

    @Override
    public int getIconResId(int index) {
      return R.drawable.ic_launcher;
    }

}