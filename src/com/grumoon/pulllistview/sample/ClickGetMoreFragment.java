package com.grumoon.pulllistview.sample;

import us.donmai.danbooru.danbo.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.grumoon.pulllistview.PullListView;

public class ClickGetMoreFragment extends Fragment {
    private PullListView plvData;
    private ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pull_list_view_fragment_click_get_more, container, false);
        initView(v);
        return v;
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
