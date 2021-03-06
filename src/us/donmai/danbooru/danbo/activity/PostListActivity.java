package us.donmai.danbooru.danbo.activity;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import us.donmai.danbooru.danbo.R;
import us.donmai.danbooru.danbo.adapter.PostGridAdapter;
import us.donmai.danbooru.danbo.model.Post;
import us.donmai.danbooru.danbo.model.PostBitmap;
import us.donmai.danbooru.danbo.model.Tag;
import us.donmai.danbooru.danbo.rest.PostRequest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

/**
 * This activity displays a list of posts using a grid
 */
public class PostListActivity extends Activity {

	private PostRequest _request;

	private class GetPostListTask extends
			AsyncTask<PostRequest, Integer, ArrayList<PostBitmap>> {
		private ProgressDialog _progressDialog;

		public GetPostListTask() {
			Resources res = getResources();
			_progressDialog = new ProgressDialog(PostListActivity.this);
			_progressDialog.setIndeterminate(false);
			_progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			_progressDialog.setMessage(res.getString(R.string.generic_loading));
		}

		@Override
		protected ArrayList<PostBitmap> doInBackground(PostRequest... params) {
			ArrayList<PostBitmap> data = new ArrayList<PostBitmap>();
			try {
				PostRequest req = params[0];
				_progressDialog.setMax(req.getLimit());
				List<Post> posts = req.execute();

				for (Post p : posts) {
					URL bitmapURL = new URL(p.getPreviewUrl());
					Bitmap postPreview = BitmapFactory.decodeStream(bitmapURL
							.openStream());
					data.add(new PostBitmap(p, postPreview));
					this.publishProgress(data.size());
				}
			} catch (Exception e) {
				e.printStackTrace();
				Log.e("Exception", e.toString());
			}

			return data;
		}

		@Override
		protected void onPostExecute(ArrayList<PostBitmap> result) {
			try {
				this._progressDialog.dismiss();
			} catch (Exception e) {
			}
			if (result != null) {
				GridView gv = (GridView) findViewById(R.id.PostGrid);
				PostGridAdapter adapter = new PostGridAdapter(result,
						PostListActivity.this);
				gv.setAdapter(adapter);
			} else {
				Toast msg = Toast.makeText(PostListActivity.this,
						R.string.error_post_load_message, Toast.LENGTH_LONG);
				msg.show();
			}
		}

		@Override
		protected void onPreExecute() {
			_progressDialog.show();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			int progress = values[0];
			Log.d("DEBUG", "progression chargement :" + progress);
			_progressDialog.setProgress(progress);
		}
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.posts);

		Bundle extras = this.getIntent().getExtras();
		if (extras != null && extras.containsKey("tag")) {
			Log.d("danbo", "Tags were set when calling PostListActivity");
			Object tagObject = extras.getSerializable("tag");
			Tag t = (Tag) tagObject;
			ArrayList<Tag> tags = new ArrayList<Tag>();
			tags.add(t);
			_request = new PostRequest(this, tags);
		} else {
			_request = new PostRequest(this);
		}
		GetPostListTask task = new GetPostListTask();
		task.execute(_request);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.post_list_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.previous_page:
			if (this._request.getPage() > 1) {
				_request.previousPage();
				GetPostListTask t = new GetPostListTask();
				t.execute(_request);
			} else {
				Toast.makeText(this, R.string.no_previous_page, Toast.LENGTH_SHORT).show();
			}
			return true;
		case R.id.next_page:
			_request.nextPage();
			GetPostListTask t = new GetPostListTask();
			t.execute(_request);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
