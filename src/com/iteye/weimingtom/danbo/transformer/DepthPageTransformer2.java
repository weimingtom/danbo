package com.iteye.weimingtom.danbo.transformer;

import android.view.View;

import com.iteye.weimingtom.danbo.view.ViewPagerCompat.PageTransformer;
import com.nineoldandroids.view.ViewHelper;

public class DepthPageTransformer2 implements PageTransformer {  
    private static final float MIN_SCALE = 0.95f;  
  
    public void transformPage(View view, float position) {  
    	int pageWidth = view.getWidth(); 
        int pageHeight = view.getHeight();
        if (position < -1) {  
            ViewHelper.setAlpha(view, 0);  
        } else if (position <= 0) {
            ViewHelper.setAlpha(view, 1 + position);  
            ViewHelper.setTranslationX(view, pageWidth * -position);
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
            ViewHelper.setScaleX(view, scaleFactor);
            ViewHelper.setScaleY(view, scaleFactor);
        } else if (position <= 1) {
        	//ViewHelper.setAlpha(view, 1 - position);  
        	ViewHelper.setAlpha(view, 1);  
        	ViewHelper.setTranslationX(view, 0);
        	ViewHelper.setScaleX(view, 1);
        	ViewHelper.setScaleY(view, 1);
        } else {
            ViewHelper.setAlpha(view, 0);  
        }
        //Log.e("DepthPageTransformer2", "view == " + view + ",position == " + position);
    }  
}
