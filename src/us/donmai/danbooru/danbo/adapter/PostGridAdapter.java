package us.donmai.danbooru.danbo.adapter;

import java.util.ArrayList;

import us.donmai.danbooru.danbo.R;
import us.donmai.danbooru.danbo.activity.SinglePostActivity;
import us.donmai.danbooru.danbo.model.Post;
import us.donmai.danbooru.danbo.model.PostBitmap;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Backend class of a grid of posts
 */
public class PostGridAdapter extends BaseAdapter {

	private ArrayList<PostBitmap> _posts;
	private Context _context;

	/**
	 * @param posts
	 * @param context
	 */
	public PostGridAdapter(ArrayList<PostBitmap> posts, Context context) {
		super();
		_posts = posts;
		_context = context;
	}

	public int getCount() {
		return _posts.size();
	}

	public Object getItem(int arg0) {
		return null;
	}

	public long getItemId(int arg0) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		Resources res = _context.getResources();

		if (convertView == null) {
			imageView = new ImageView(_context);
			// Image display
			// 
			imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,  (int)res.getDimension(R.dimen.gridview_imageview_height)));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			int padding = (int)res.getDimension(R.dimen.gridview_imageview_padding);
			imageView.setPadding(padding, padding, padding, padding);
		} else {
			imageView = (ImageView) convertView;
		}
		imageView.setOnClickListener(new ImageClicked(_posts.get(position)
				.getPost()));
		// Get the preview bitmap from the post object
		// Bitmap bm = _posts.get(position).getPreviewImage();

		// is the image in portrait or landscape ? make it square for the grid

		// apply the bitmap to the imageview
		// imageView.setImageBitmap(bm);
		// imageView.setOnClickListener(new ImageClicked(_posts.get(position)));
		try {
			Bitmap bm = _posts.get(position).getBitmap();

			if (bm.getWidth() > bm.getHeight()) {
				bm = Bitmap.createBitmap(bm, 0, 0, bm.getHeight(), bm
						.getHeight());
			} else {
				bm = Bitmap
						.createBitmap(bm, 0, 0, bm.getWidth(), bm.getWidth());
			}

			imageView.setImageBitmap(bm);
		} catch (Exception e) {

		}
		return imageView;
	}

	private class ImageClicked implements OnClickListener {

		private Post _post;

		public ImageClicked(Post post) {
			super();
			_post = post;
		}

		public void onClick(View v) {
			Intent i = new Intent(v.getContext(), SinglePostActivity.class);
			i.putExtra("post", _post);
			v.getContext().startActivity(i);
		}

	}

}
