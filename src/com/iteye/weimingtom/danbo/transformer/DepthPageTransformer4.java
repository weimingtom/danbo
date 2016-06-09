package com.iteye.weimingtom.danbo.transformer;

import android.view.View;

import com.iteye.weimingtom.danbo.view.ViewPagerCompat.PageTransformer;
import com.nineoldandroids.view.ViewHelper;

public class DepthPageTransformer4 implements PageTransformer {  
    private static final float MIN_SCALE = 0.9f;  
  
    public void transformPage(View view, float position) {  
    	int pageWidth = view.getWidth(); 
    	if (position < -1) {  
        	//ViewCompat.setAlpha(view, 0);
        	view.setVisibility(View.INVISIBLE);
        	ViewHelper.setTranslationX(view, 0);
        	ViewHelper.setScaleX(view, 1);
        	ViewHelper.setScaleY(view, 1);
        } else if (position <= 0) {
        	//ViewCompat.setAlpha(view, 1 + position);  
        	view.setVisibility(View.VISIBLE);
        	ViewHelper.setTranslationX(view, pageWidth * -position);
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
            ViewHelper.setScaleX(view, scaleFactor);
            ViewHelper.setScaleY(view, scaleFactor);
        } else if (position <= 1) {
        	//ViewCompat.setAlpha(view, 1);  
        	view.setVisibility(View.VISIBLE);
        	ViewHelper.setTranslationX(view, 0);
        	ViewHelper.setScaleX(view, 1);
        	ViewHelper.setScaleY(view, 1);
        } else {
        	//ViewCompat.setAlpha(view, 1);  
        	view.setVisibility(View.INVISIBLE);
        	ViewHelper.setTranslationX(view, 0);
        	ViewHelper.setScaleX(view, 1);
        	ViewHelper.setScaleY(view, 1);
        }
        //Log.e("DepthPageTransformer2", "view == " + view + ",position == " + position);
    }  
}
