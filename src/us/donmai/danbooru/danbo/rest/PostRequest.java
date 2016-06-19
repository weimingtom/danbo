package us.donmai.danbooru.danbo.rest;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
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
import us.donmai.danbooru.danbo.util.PrefUtils;
import android.content.Context;
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

	private void init(Context context) {
		PrefUtils pref = new PrefUtils(context);
		this._host = pref.getHost();
		this._rating = pref.getRating();
		this._resource = "/post/index.json";
		String tmpLimit = pref.getPostsPerPage();
		try {
			this._limit = Integer.parseInt(tmpLimit);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			Log.e("danbo", tmpLimit + " is not a number.");
			this._limit = 12;
		}
		this._tags = new ArrayList<Tag>();
		this._page = 1;
	}

	/**
	 * A request returning a json file with a filtered post list
	 */
	public PostRequest(Context context) {
		init(context);
	}

	public PostRequest(Context context, List<Tag> tags) {
		init(context);
		this._tags = tags;
	}

	/**
	 * @return A json array containing all the posts
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws JSONException
	 */
	public List<Post> execute() {
		String url = _host + _resource + "?limit=" + _limit
				+ "&page=" + this._page + "&tags=";
		if (!(_rating.equalsIgnoreCase("All"))) {
			url += "rating:" + _rating;
		}
		for (Tag t : _tags) {
			url = url + "%20" + t.getName();
		}

		ArrayList<Post> posts = new ArrayList<Post>();

		try {
			//url = "https://yande.re/post/index.json?limit=12&page=1&tags=rating:Safe";
			//http://stackoverflow.com/questions/3163693/java-urlconnection-timeout
			URL jsonUrl = new URL(url);
			//URLConnection connection = jsonUrl.openConnection();
			
			HttpURLConnection connection = (HttpURLConnection) jsonUrl.openConnection();
			HttpURLConnection.setFollowRedirects(false);
			connection.setConnectTimeout(15 * 1000);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");
			connection.connect();
			
			//connection.setUseCaches(false);
			//connection.setReadTimeout(5000);
			InputStream jsonStream = connection.getInputStream();
			String jsonString = IOHelpers.convertStreamToString(jsonStream);
			System.out.println("url==" + url);
			System.out.println("jsonString==" + jsonString);
			_response = new JSONArray(jsonString);

			int i = 0;
			int length = _response.length();

			// for each entry we create a post object and populate its
			// fields with the values obtained from the json file
			for (i = 0; i < length; i++) {
				JSONObject entry = _response.getJSONObject(i);
				int id = entry.getInt("id");

				String previewUrl = entry.getString("preview_url");
				if (previewUrl != null && !previewUrl.startsWith("http")) {
					previewUrl = _host + previewUrl;
				}
				String sampleUrl = null;
				if (entry.has("sample_url") && entry.getString("sample_url") != null) {
					sampleUrl = entry.getString("sample_url"); //"";//entry.getString("sample_url");
				} else {
					sampleUrl = entry.getString("file_url"); //"";//entry.getString("sample_url");
				}
				if (sampleUrl != null && !sampleUrl.startsWith("http")) {
					sampleUrl = _host + sampleUrl;
				}
				String fileUrl = entry.getString("file_url");
				if (fileUrl != null && !fileUrl.startsWith("http")) {
					fileUrl = _host + fileUrl;
				}
				System.out.println("previewUrl == " + previewUrl);
				
				int fileSize = entry.getInt("file_size");

				Post p = new Post(id, previewUrl, sampleUrl, fileUrl, fileSize);

				// add the constructed post to the list
				posts.add(p);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Request Exception", e.toString());
		}
		return posts;
	}

	public int getLimit() {
		return _limit;
	}

	public void setPage(int page) {
		this._page = page;
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
