package us.donmai.danbooru.danbo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * A quick way to access the preferences
 */
public class SharedPreferencesInstance {
	static private SharedPreferences _pref;

	/**
	 * @return an instance of the shared preferences
	 */
	static public SharedPreferences getInstance() {
		return _pref;
	}

	/**
	 * Instantiate the shared preferences using a valid context
	 * @param c
	 * A valid context
	 */
	static public void initialize(Context c) {
		_pref = PreferenceManager.getDefaultSharedPreferences(c);
	}
}
