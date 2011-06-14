package us.donmai.danbooru.danbo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import us.donmai.danbooru.danbo.model.Post;
import us.donmai.danbooru.danbo.model.Tag;
import us.donmai.danbooru.danbo.util.IOHelpers;
import android.util.Log;

/**
 * Sends a request to get a list of post
 */
public class PostRequest {

	private JSONArray _response;
	private String _host;
	private String _resource;
	private int _limit;
	private String _rating;
	private int _page;
	private List<Tag> _tags;

	private void init() {
		this._host = SharedPreferencesInstance.getInstance().getString("host",
				"danbooru.donmai.us");
		this._rating = SharedPreferencesInstance.getInstance().getString(
				"rating", "Safe");
		this._resource = "/post/index.json";
		String tmpLimit = SharedPreferencesInstance.getInstance().getString(
				"posts_per_page", "12");
		try {
			this._limit = Integer.parseInt(tmpLimit);
		}
		catch (NumberFormatException e) {
			Log.e("danbo",tmpLimit + " is not a number.");
			this._limit = 12;
		}
		
		this._tags = new ArrayList<Tag>();
		this._page = 1;
	}

	/**
	 * A request returning a json file with a filtered post list
	 */
	public PostRequest() {
		init();
	}

	public PostRequest(List<Tag> tags) {
		init();
		this._tags = tags;
	}

	/**
	 * @return A json array containing all the posts
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws JSONException
	 */
	public List<Post> execute() {
		String url = "http://" + _host + _resource + "?limit=" + _limit
				+ "&page=" + this._page + "&tags=";
		if(!(_rating.equalsIgnoreCase("All"))) {
			url += "rating:" + _rating;
		}
		for (Tag t : _tags) {
			url = url + "%20" + t.getName();
		}

		ArrayList<Post> posts = new ArrayList<Post>();

		try {
			URL jsonUrl = new URL(url);
			InputStream jsonStream = jsonUrl.openStream();
			String jsonString = IOHelpers.convertStreamToString(jsonStream);
			_response = new JSONArray(jsonString);

			int i = 0;
			int length = _response.length();

			// for each entry we create a post object and populate its
			// fields with the values obtained from the json file
			for (i = 0; i < length; i++) {
				JSONObject entry = _response.getJSONObject(i);
				int id = entry.getInt("id");

				String previewUrl = entry.getString("preview_url");
				String sampleUrl = entry.getString("sample_url");
				String fileUrl = entry.getString("file_url");

				Post p = new Post(id, previewUrl, sampleUrl, fileUrl);

				// add the constructed post to the list
				posts.add(p);
			}

		} catch (Exception e) {
			Log.e("Request Exception", e.toString());
		}
		return posts;
	}

	public int getLimit() {
		return _limit;
	}

	public int getPage() {
		return _page;
	}
	
	public void nextPage() {
		_page++;
	}
	
	public void previousPage() {
		_page--;
	}
}
