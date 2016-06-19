package com.iteye.weimingtom.danbo.adapter;

import java.util.List;

import us.donmai.danbooru.danbo.R;
import us.donmai.danbooru.danbo.model.Post;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class GridAdapter extends BaseAdapter{
	private final static boolean D = false;
	private final static String TAG = "GridAdapter";
	
	private Context mContext;
	private List<Post> mList;
	private ImageLoader mImageLoader;
	private LayoutInflater mInflater;
	
	public GridAdapter(Context mContext, List<Post> mList, ImageLoader mImageLoader){
		this.mContext = mContext;
		this.mList = mList;
		this.mImageLoader = mImageLoader;
		this.mInflater = LayoutInflater.from(mContext);
		if (D) {
			Log.e(TAG, "GridAdapter mList.size == " + mList.size());
		}
	}
	
	@Override
	public int getCount() {
		return mList == null ? 0 : mList.size();
	}

	@Override
	public Post getItem(int position) {
		return mList == null ? null : mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder mHolder;
		Post videoitem = getItem(position);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.homepage_item, null);
			mHolder = new ViewHolder();
			mHolder.icon = (ImageView) convertView.findViewById(R.id.list_item_image);
			mHolder.msg = (TextView) convertView.findViewById(R.id.list_item_title);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		
		mImageLoader.displayImage(videoitem.getPreviewUrl(), mHolder.icon);
		mHolder.msg.setText(videoitem.getImageName() != null ? videoitem.getImageName() : "");

		return convertView;
	}
	
	private void onclick() {
		/*
		mHolder.icon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Post item = (Post) mList.get(position);
				
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putSerializable(VideoInfoActivity.EXTRA_VIDEO_ITEM_DATA, item);
				intent.setClass(mContext, VideoInfoActivity.class);
				intent.putExtras(bundle);
				mContext.startActivity(intent);
				if (D) {
					Log.e(TAG, ">>>>>>>>>>>>>>>GridAdapter onclick");
				}
			}
		});
		*/
	}
	
	private class ViewHolder{
		private ImageView icon;
		private TextView msg;
	}
	
	public void clear() {
		mList.clear();
	}
	
	public void add(Post p) {
		mList.add(p);
	}
}
