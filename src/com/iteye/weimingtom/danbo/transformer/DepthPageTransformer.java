package com.iteye.weimingtom.danbo.transformer;

import android.view.View;

import com.iteye.weimingtom.danbo.view.ViewPagerCompat.PageTransformer;
import com.nineoldandroids.view.ViewHelper;

public class DepthPageTransformer implements PageTransformer {  
    private static final float MIN_SCALE = 0.75f;  
  
    public void transformPage(View view, float position) {  
        int pageWidth = view.getWidth();  
  
        if (position < -1) {  
            // view.setAlpha(0);  
            ViewHelper.setAlpha(view, 0);  
        } else if (position <= 0) {  
            // view.setAlpha(1);  
            ViewHelper.setAlpha(view, 1);  
            // view.setTranslationX(0);  
            ViewHelper.setTranslationX(view, 0);  
            // view.setScaleX(1);  
            ViewHelper.setScaleX(view, 1);  
            // view.setScaleY(1);  
            ViewHelper.setScaleY(view, 1);  
        } else if (position <= 1) {  
            // view.setAlpha(1 - position);  
            ViewHelper.setAlpha(view, 1 - position);  
            // view.setTranslationX(pageWidth * -position);  
            ViewHelper.setTranslationX(view, pageWidth * -position);  
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));  
            // view.setScaleX(scaleFactor);  
            ViewHelper.setScaleX(view, scaleFactor);  
            // view.setScaleY(scaleFactor);  
            ViewHelper.setScaleY(view, scaleFactor);  
        } else {  
            // view.setAlpha(0);  
            ViewHelper.setAlpha(view, 0);  
        }  
    }  
}
