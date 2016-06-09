package com.iteye.weimingtom.danbo.fragment;

import us.donmai.danbooru.danbo.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * CANNOT BE private final static class
 * @author Administrator
 *
 */
public class ImageFragment extends Fragment {
	private static final String KEY_PAGE = "Image.page";

    public int page;
    private ImageView image;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_PAGE)) {
            this.page = savedInstanceState.getInt(KEY_PAGE);
        }

        //Log.e("BitmapFragment", "onCreate " + this.page);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	image = new ImageView(getActivity());
        image.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        image.setTag("page" + page);
        
        //Log.e("BitmapFragment", "onCreateView " + this.page);
        switch (page) {
        case 0:
        	image.setImageResource(R.drawable.test001);
            break;
            
        case 1:
        	image.setImageResource(R.drawable.test002);
            break;
        	
        case 2:
        	image.setImageResource(R.drawable.test003);
            break;
        
        case 3:
        	image.setImageResource(R.drawable.test001);
            break;
            
        case 4:
        	image.setImageResource(R.drawable.test002);
            break;
        }
        
        
        LinearLayout layout = new LinearLayout(getActivity());
        layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        layout.addView(image);
        
        return layout;
    }

	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_PAGE, this.page);
    }
}
