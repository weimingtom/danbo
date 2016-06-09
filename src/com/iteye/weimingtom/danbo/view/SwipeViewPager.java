package com.iteye.weimingtom.danbo.view;

import android.content.Context;
import android.util.AttributeSet;

public class SwipeViewPager extends ViewPagerCompat {
	public SwipeViewPager(Context context) {
		super(context);
	}
	
	public SwipeViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }
}
