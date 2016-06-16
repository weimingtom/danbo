package com.iteye.weimingtom.danbo.view;

import us.donmai.danbooru.danbo.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class SwipeCommonView extends LinearLayout {
	public SwipeCommonView(Context context) {
		super(context);
		init(context);
	}

	public SwipeCommonView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	private void init(Context context) {
		View view = View.inflate(context, 
			R.layout.common_listview_with_title, null);
		this.addView(view, new LinearLayout.LayoutParams(
    			LinearLayout.LayoutParams.MATCH_PARENT, 
    			LinearLayout.LayoutParams.MATCH_PARENT));
		this.setBackgroundResource(R.color.white);
		view.setBackgroundResource(R.color.white);
    }
}
