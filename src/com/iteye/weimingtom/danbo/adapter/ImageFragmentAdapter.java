package com.iteye.weimingtom.danbo.adapter;

import com.iteye.weimingtom.danbo.fragment.ImageFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class ImageFragmentAdapter extends FragmentPagerAdapterCompat {	
	private int pageCount;
    
	public ImageFragmentAdapter(FragmentManager fm, int count) {
        super(fm);
        this.pageCount = count;
    }

    @Override
    public Fragment getItem(int position) {
    	ImageFragment result = new ImageFragment();
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
