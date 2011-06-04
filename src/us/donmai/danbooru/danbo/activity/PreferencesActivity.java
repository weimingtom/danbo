package us.donmai.danbooru.danbo.activity;

import us.donmai.danbooru.danbo.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * The Preferences activity allows the user to edit his preferences for the
 * application
 */
public class PreferencesActivity extends PreferenceActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
}
