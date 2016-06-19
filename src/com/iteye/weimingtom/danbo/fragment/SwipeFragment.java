package com.iteye.weimingtom.danbo.fragment;

import java.util.ArrayList;
import java.util.List;

import us.donmai.danbooru.danbo.R;
import us.donmai.danbooru.danbo.activity.SinglePostActivity;
import us.donmai.danbooru.danbo.model.Post;
import us.donmai.danbooru.danbo.rest.PostRequest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.grumoon.pulllistview.PullListView;
import com.iteye.weimingtom.danbo.adapter.GridAdapter;
import com.iteye.weimingtom.danbo.view.SwipeCommonView;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * CANNOT BE private final static class
 * @author Administrator
 *
 */
public class SwipeFragment extends Fragment {
	private static final boolean D = true;
	private static final String TAG = "SwipeFragment";
	
	private static final String KEY_PAGE = "SwipeFragment.page";
    public int page;
    private SwipeCommonView swipeCommonView;
    private PullListView plvData;
    private GridAdapter adapter;
    private ImageButton btnBack;
    private List<Post> mData;
    
    private PostRequest _request;

	private ImageLoader mImageLoader;
	private DisplayImageOptions options;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null && 
        	savedInstanceState.containsKey(KEY_PAGE)) {
            this.page = savedInstanceState.getInt(KEY_PAGE);
        }

		initImageLoader();
		mImageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.top_banner_android)
			.showImageForEmptyUri(R.drawable.top_banner_android)
			.showImageOnFail(R.drawable.top_banner_android)
			.cacheInMemory(true)
			.cacheOnDisk(true)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.imageScaleType(ImageScaleType.EXACTLY)
			.build();
        
        //Log.e("BitmapFragment", "onCreate " + this.page);
		_request = new PostRequest(getActivity());
		_request.setPage(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	swipeCommonView = new SwipeCommonView(getActivity());
    	swipeCommonView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        
    	swipeCommonView.setTag("page" + page);
        
    	initHeaderView(swipeCommonView);
        initView(swipeCommonView);
    	
        return swipeCommonView;
    }

	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_PAGE, this.page);
    }

	private void initHeaderView(View root) {
		btnBack = (ImageButton) root.findViewById(R.id.back);
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				getActivity().finish();
			}
		});
	}
	
    private void initView(View v) {
        plvData = (PullListView) v.findViewById(R.id.plv_data);
        plvData.setOnRefreshListener(new PullListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(true, adapter, plvData);
            }
        });
        plvData.setOnGetMoreListener(new PullListView.OnGetMoreListener() {
            @Override
            public void onGetMore() {
                getData(false, adapter, plvData);
            }
        });
        mData = new ArrayList<Post>();
        adapter = new GridAdapter(getActivity(), mData, mImageLoader);
        plvData.setAdapter(adapter);
        plvData.performRefresh();
        plvData.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view1, int position,
					long id) {
				if (D) {
					Log.e(TAG, ">>>>>>>>SwipeFragment onItemClick");
				}
				
				if (mData != null && 
					position - 1 >= 0 && 
					position - 1 < mData.size()) {
					Post item = (Post) mData.get(position - 1);
					Intent intent = new Intent(getActivity(), SinglePostActivity.class);
					intent.putExtra("post", item);
					getActivity().startActivity(intent);
				}
			}
        });
    }
    
	private void initImageLoader() {
		//File cacheDir = StorageUtils.getOwnCacheDirectory(getActivity().getApplicationContext(), Constant.IMAGE_CACHE_PATH);

		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this.getActivity()).defaultDisplayImageOptions(defaultOptions)
				.memoryCache(new LruMemoryCache(12 * 1024 * 1024))
				.memoryCacheSize(12 * 1024 * 1024)
				/*
				.diskCacheSize(32 * 1024 * 1024)
				.diskCacheFileCount(100)
				.diskCache(new UnlimitedDiskCache(cacheDir))
				*/
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();

		ImageLoader.getInstance().init(config);
	}
	
    public void getData(final boolean isRefresh, 
    	final GridAdapter aa, 
    	final PullListView plv) {
    	if (isRefresh) {
    		_request.setPage(0);
    	}
    	_request.nextPage();
		new GetPostListTask(isRefresh, aa, plv).execute(_request);
    }

	private class GetPostListTask extends
			AsyncTask<PostRequest, Integer, List<Post>> {
		private boolean mIsRefresh;
		private GridAdapter mAdapter;
		private PullListView mPlv;
		
		public GetPostListTask(final boolean isRefresh, 
			final GridAdapter aa, 
			final PullListView plv) {
			this.mIsRefresh = isRefresh;
			this.mAdapter = aa;
			this.mPlv = plv;
		}

		@Override
		protected List<Post> doInBackground(PostRequest... params) {
			List<Post> data = new ArrayList<Post>();
			try {
				PostRequest req = params[0];
				data = req.execute();
			} catch (Exception e) {
				e.printStackTrace();
				Log.e("Exception", e.toString());
			}
			return data;
		}

		@Override
		protected void onPostExecute(List<Post> result) {
            if (result != null) {
            	if (mIsRefresh) {
                    mAdapter.clear();
                }
    			if (D) {
            		Log.e(TAG, "getData");
            	}
                for (Post p : result) {
                	mAdapter.add(p);
                }
			} else {
				Toast msg = Toast.makeText(getActivity(),
						R.string.error_post_load_message, Toast.LENGTH_LONG);
				msg.show();
			}
			
            mAdapter.notifyDataSetChanged();
            mPlv.refreshComplete();
            mPlv.getMoreComplete();
		}

		@Override
		protected void onPreExecute() {
		
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			
		}
	}
}
