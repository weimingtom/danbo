package com.iteye.weimingtom.danbo.activity;

import us.donmai.danbooru.danbo.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;

import com.grumoon.pulllistview.PullListView;
import com.grumoon.pulllistview.sample.DataUtil;
import com.iteye.weimingtom.danbo.adapter.FragmentPagerAdapterCompat;
import com.iteye.weimingtom.danbo.adapter.SwipeFragmentAdapter;
import com.iteye.weimingtom.danbo.transformer.DepthPageTransformer4;
import com.iteye.weimingtom.danbo.view.SwipeViewPager;

public class SwipeListActivity extends SwipeBaseActivity {
	private FragmentPagerAdapterCompat mAdapter;
	private SwipeViewPager mPager;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_swipe);
		
        mAdapter = new SwipeFragmentAdapter(getSupportFragmentManager(), 5);
        mPager = (SwipeViewPager) findViewById(R.id.pager);
        mPager.setPageTransformerCompat(false, new DepthPageTransformer4());
        mPager.setAdapter(mAdapter);
	}
}
