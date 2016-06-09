package com.iteye.weimingtom.danbo.activity;

import us.donmai.danbooru.danbo.R;
import android.os.Bundle;
import android.view.Window;

import com.iteye.weimingtom.danbo.adapter.ImageFragmentAdapter;
import com.iteye.weimingtom.danbo.transformer.DepthPageTransformer4;
import com.iteye.weimingtom.danbo.view.SwipeViewPager;

public class SwipeActivity extends SwipeBaseActivity	{
	private ImageFragmentAdapter mAdapter;
	private SwipeViewPager mPager;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_swipe);
		
        mAdapter = new ImageFragmentAdapter(getSupportFragmentManager(), 5);
        mPager = (SwipeViewPager) findViewById(R.id.pager);
        mPager.setPageTransformerCompat(false, new DepthPageTransformer4());
        mPager.setAdapter(mAdapter);
	}
}
