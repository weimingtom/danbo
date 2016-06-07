/**
 * 
 */
package us.donmai.danbooru.danbo.activity;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import us.donmai.danbooru.danbo.R;
import us.donmai.danbooru.danbo.SharedPreferencesInstance;
import us.donmai.danbooru.danbo.TagListAdapter;
import us.donmai.danbooru.danbo.model.Tag;
import us.donmai.danbooru.danbo.util.IOHelpers;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

/**
 *
 */
public class TagSearchActivity extends Activity {

	private class SearchTask extends AsyncTask<String, Integer, List<Tag>> {

		ProgressDialog _progress;

		@Override
		protected void onPreExecute() {
			Resources res = getResources();
			_progress = new ProgressDialog(TagSearchActivity.this);
			_progress.setIndeterminate(true);
			_progress.setMessage(res.getString(R.string.searching));
			_progress.show();
		}

		@Override
		protected List<Tag> doInBackground(String... params) {

			ArrayList<Tag> tags = new ArrayList<Tag>();
			try {
				String host = SharedPreferencesInstance.getInstance()
						.getString("host", "https://danbooru.donmai.us");
				URL tagsUrl = new URL(host
						+ "/tag/index.json?name=" + params[0] + "*");
				InputStream responseStream = tagsUrl.openStream();
				String jsonString = IOHelpers
						.convertStreamToString(responseStream);

				JSONArray jsonArray = new JSONArray(jsonString);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject obj = jsonArray.getJSONObject(i);
					Tag t = new Tag(obj.getString("name"), obj.getInt("type"));
					tags.add(t);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Log.e("Search", e.toString());
				Toast message = Toast.makeText(TagSearchActivity.this,
						"Search error", Toast.LENGTH_LONG);
				message.show();
			}
			return tags;
		}

		@Override
		protected void onPostExecute(List<Tag> result) {
			TagListAdapter adapter = new TagListAdapter(result,
					TagSearchActivity.this);
			ListView lv = (ListView) findViewById(R.id.TagListView);
			lv.setAdapter(adapter);
			try {
				_progress.dismiss();
			} catch (Exception e) {
			}
		}
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.tags);

		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			SearchTask task = new SearchTask();
			task.execute(query);
		}
	}

}
