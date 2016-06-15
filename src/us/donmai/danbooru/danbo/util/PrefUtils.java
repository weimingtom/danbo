package us.donmai.danbooru.danbo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefUtils {
	public final static String PREF_FIRST_LAUNCH_KEY = "first-launch";
	public final static String PREF_HOST_KEY = "host";
	public final static String PREF_RATING_KEY = "rating";
	public final static String PREF_POSTS_PER_PAGE_KEY = "posts_per_page";
	
	private SharedPreferences sp;
	
	public PrefUtils(Context context) {
		this.sp = PreferenceManager
				.getDefaultSharedPreferences(context);
	}
	
	public boolean getFirstLaunch() {
		return sp.getBoolean(PREF_FIRST_LAUNCH_KEY, true);
	}
	
	public void setFirstLaunch(boolean val) {
		sp.edit().putBoolean(PREF_FIRST_LAUNCH_KEY, val).commit();
	}
	
	public String getHost() {
		//return sp.getString(PREF_HOST_KEY, "https://danbooru.donmai.us");
		return sp.getString(PREF_HOST_KEY, "https://yande.re");
	}
	
	public void setHost(String val) {
		sp.edit().putString(PREF_HOST_KEY, val).commit();
	}
	
	public String getRating() {
		return sp.getString(PREF_RATING_KEY, "Safe");
	}
	
	public void setRating(String val) {
		sp.edit().putString(PREF_RATING_KEY, val).commit();
	}
	
	public String getPostsPerPage() {
		return sp.getString(PREF_POSTS_PER_PAGE_KEY, "12");
	}
	
	public void setPostsPerPage(String val) {
		sp.edit().putString(PREF_POSTS_PER_PAGE_KEY, val).commit();
	}
}
