package com.iteye.weimingtom.danbo.fragment;

import us.donmai.danbooru.danbo.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout.LayoutParams;

import com.grumoon.pulllistview.PullListView;
import com.grumoon.pulllistview.sample.DataUtil;
import com.iteye.weimingtom.danbo.view.SwipeCommonView;

/**
 * CANNOT BE private final static class
 * @author Administrator
 *
 */
public class SwipeFragment extends Fragment {
	private static final String KEY_PAGE = "SwipeFragment.page";
    public int page;
    private SwipeCommonView swipeCommonView;
    private PullListView plvData;
    private ArrayAdapter<String> adapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null && 
        	savedInstanceState.containsKey(KEY_PAGE)) {
            this.page = savedInstanceState.getInt(KEY_PAGE);
        }

        //Log.e("BitmapFragment", "onCreate " + this.page);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	swipeCommonView = new SwipeCommonView(getActivity());
    	swipeCommonView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        
    	swipeCommonView.setTag("page" + page);
        
        initView(swipeCommonView);
    	
        return swipeCommonView;
    }

	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_PAGE, this.page);
    }

    private void initView(View v) {
        plvData = (PullListView) v.findViewById(R.id.plv_data);
        plvData.setOnRefreshListener(new PullListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DataUtil.getData(true, adapter, plvData);
            }
        });
        plvData.setOnGetMoreListener(new PullListView.OnGetMoreListener() {
            @Override
            public void onGetMore() {
                DataUtil.getData(false, adapter, plvData);
            }
        });
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.pull_list_view_list_item);
        plvData.setAdapter(adapter);
        plvData.performRefresh();
    }
}
