package us.donmai.danbooru.danbo.activity;

import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import us.donmai.danbooru.danbo.R;
import us.donmai.danbooru.danbo.adapter.TagListAdapter;
import us.donmai.danbooru.danbo.model.Tag;
import us.donmai.danbooru.danbo.util.IOHelpers;
import us.donmai.danbooru.danbo.util.PrefUtils;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;

/**
 * This activity displays a list of tags
 */
public class TagListActivity extends Activity {

	private TagListAdapter _adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tags);

		GetTagListTask task = new GetTagListTask(this);
		task.execute();

	}

	private class GetTagListTask extends AsyncTask<Void, Void, Void> {
		private ProgressDialog _progress;
		private Context _context;
		
		public GetTagListTask(Context context) {
			this._context = context;
		}
		
		@Override
		protected void onPreExecute() {
			Resources res = getResources();
			_progress = ProgressDialog.show(TagListActivity.this, "",
					res.getString(R.string.generic_loading), true);
		}

		@Override
		protected void onPostExecute(Void r) {
			ListView lv = (ListView) findViewById(R.id.TagListView);
			lv.setAdapter(_adapter);
			try {
				_progress.dismiss();
			} catch (Exception e) {
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				PrefUtils pref = new PrefUtils(_context);
				String host = pref.getHost();
				URL tagListUrl = new URL(host + "/tag/index.json?order=count&limit=100");
				String jsonString = IOHelpers.convertStreamToString(tagListUrl
						.openStream());
				JSONArray jsonTags = new JSONArray(jsonString);

				ArrayList<Tag> tags = new ArrayList<Tag>();
				for (int i = 0; i < jsonTags.length(); i++) {
					JSONObject jso = jsonTags.getJSONObject(i);
					Tag t = new Tag(jso.getString("name"));
					tags.add(t);
				}
				_adapter = new TagListAdapter(tags, TagListActivity.this);
			} catch (Exception e) {
				// TODO: handle exception
			}
			return null;
		}
	}
}
