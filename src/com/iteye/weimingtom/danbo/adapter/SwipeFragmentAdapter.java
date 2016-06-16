package com.iteye.weimingtom.danbo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.iteye.weimingtom.danbo.fragment.SwipeFragment;

public class SwipeFragmentAdapter extends FragmentPagerAdapterCompat {	
	private int pageCount;
    
	public SwipeFragmentAdapter(FragmentManager fm, int count) {
        super(fm);
        this.pageCount = count;
    }

    @Override
    public Fragment getItem(int position) {
    	SwipeFragment result = new SwipeFragment();
    	result.page = position;
        return result;
    }

    @Override
    public int getCount() {
        return this.pageCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
    	return "";
    }
}
