package us.donmai.danbooru.danbo;

import java.util.List;

import us.donmai.danbooru.danbo.activity.PostListActivity;
import us.donmai.danbooru.danbo.model.Tag;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * Backend class for a list of tags
 */
public class TagListAdapter implements ListAdapter {
	
	private List<Tag> _tags;
	private Context _context;

	/**
	 * @param tags
	 * @param c
	 */
	public TagListAdapter(List<Tag> tags, Context c) {
		this._tags = tags;
		this._context = c;
	}


	public boolean areAllItemsEnabled() {
		return true;
	}


	public boolean isEnabled(int position) {
		return true;
	}


	public int getCount() {
		return _tags.size();
	}


	public Object getItem(int position) {
		return _tags.get(position);
	}


	public long getItemId(int position) {
		return 0;
	}


	public int getItemViewType(int position) {
		return 0;
	}


	public View getView(int position, View convertView, ViewGroup parent) {
		TextView tv = null;
		Tag t = _tags.get(position);
		if (convertView == null) {
			tv = new TextView(this._context);
			//tv.setWidth(LayoutParams.FILL_PARENT);
			tv.setTextSize(TypedValue.COMPLEX_UNIT_PT, 18);
			
		} else {
			tv = (TextView) convertView;
		}
		tv.setText(t.getName());
		tv.setOnClickListener(new TagClick(t));
		return tv;
	}


	public int getViewTypeCount() {
		return 1;
	}


	public boolean hasStableIds() {
		return false;
	}


	public boolean isEmpty() {
		return (_tags.size() == 0);
	}


	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}


	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

	private class TagClick implements OnClickListener {
		
		private Tag _tag;
		
		public TagClick(Tag t) {
			_tag = t;
		}
		
		public void onClick(View v) {
			Intent i = new Intent(v.getContext(),PostListActivity.class);
			i.putExtra("tag", _tag);
			v.getContext().startActivity(i);
		}
	}

}
