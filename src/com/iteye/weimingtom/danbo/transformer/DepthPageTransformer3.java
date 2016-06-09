package com.iteye.weimingtom.danbo.transformer;

import android.support.v4.view.ViewCompat;
import android.view.View;

import com.iteye.weimingtom.danbo.view.ViewPagerCompat.PageTransformer;

public class DepthPageTransformer3 implements PageTransformer {  
    private static final float MIN_SCALE = 0.9f;  
  
    public void transformPage(View view, float position) {  
    	int pageWidth = view.getWidth(); 
        if (position < -1) {  
        	//ViewCompat.setAlpha(view, 0);
        	view.setVisibility(View.INVISIBLE);
        } else if (position < 0) {
        	//ViewCompat.setAlpha(view, 1 + position);  
        	view.setVisibility(View.VISIBLE);
        	ViewCompat.setTranslationX(view, pageWidth * -position);
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
            ViewCompat.setScaleX(view, scaleFactor);
            ViewCompat.setScaleY(view, scaleFactor);
        } else if (position < 1) {
        	//ViewCompat.setAlpha(view, 1);  
        	view.setVisibility(View.VISIBLE);
        	ViewCompat.setTranslationX(view, 0);
        	ViewCompat.setScaleX(view, 1);
        	ViewCompat.setScaleY(view, 1);
        } else {
        	//ViewCompat.setAlpha(view, 1);  
        	view.setVisibility(View.VISIBLE);
        }
        //Log.e("DepthPageTransformer2", "view == " + view + ",position == " + position);
        ViewCompat.postInvalidateOnAnimation(view);
    }  
}
