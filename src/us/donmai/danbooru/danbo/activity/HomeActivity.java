package us.donmai.danbooru.danbo.activity;

import us.donmai.danbooru.danbo.R;
import us.donmai.danbooru.danbo.util.NetworkState;
import us.donmai.danbooru.danbo.util.PrefUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.iteye.weimingtom.danbo.activity.SwipeActivity;
import com.iteye.weimingtom.danbo.activity.SwipeListActivity;

/**
 * The activity launched when the application starts up
 */
public class HomeActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		Resources res = getResources();
		String[] menuItems = res.getStringArray(R.array.main_menu);
		ListView menuItemsListView = (ListView) findViewById(R.id.MenuItems);
		menuItemsListView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, menuItems));
		
		menuItemsListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v,
					int position, long id) {
				switch (position) {
				case 0:
					startActivity(new Intent(HomeActivity.this,
							PostListActivity.class));
					break;
				
				case 1:
					startActivity(new Intent(HomeActivity.this,
						TagListActivity.class));
					break;
				
				case 2:
					onSearchRequested();
					break;
					
				case 3:
					startActivity(new Intent(HomeActivity.this,
						SwipeActivity.class));
					break;

				case 4:
					startActivity(new Intent(HomeActivity.this,
						SwipeListActivity.class));
					break;
					
				case 5:
					startActivity(new Intent(HomeActivity.this, 
						PreferencesActivity.class));
					break;
					
				default:
					break;
				}
			}
		});
	}

	private void checkFirstLaunch() {
		PrefUtils pref = new PrefUtils(this);
		boolean firstLaunch = pref.getFirstLaunch();
		if (firstLaunch) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.welcome_message);
			builder.setNeutralButton(R.string.accept_welcome_message,
					new OnClickListener() {
						public void onClick(DialogInterface dialog,
								int which) {
						}
					});
			AlertDialog firstLaunchAlert = builder.create();
			firstLaunchAlert.show();
			pref.setFirstLaunch(false);
		}
	}
	
	private boolean checkPhoneStatus() {
		/* Is a connection available ? */
		NetworkState ns = new NetworkState(this);

		if (!ns.connectionAvailable()) {

			/**
			 * No network available, we display an error message and exit
			 */
			Log.d("danbo", "No connection available");
			AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
			alertBuilder.setMessage(R.string.error_no_connection_available);
			alertBuilder.setNegativeButton(R.string.button_exit,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							HomeActivity.this.finish();
						}
					});
			AlertDialog networkAlert = alertBuilder.create();
			networkAlert.show();
			return false;
		}

		/* Check if the SD Card is available for writing */
		String state = Environment.getExternalStorageState();

		if (!(Environment.MEDIA_MOUNTED.equals(state))) {
			AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
			alertBuilder.setMessage(R.string.error_sd_not_available);
			alertBuilder.setNegativeButton(R.string.button_exit,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							HomeActivity.this.finish();
						}
					});
			AlertDialog alert = alertBuilder.create();
			alert.show();
			return false;
		}

		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		/**
		 * Called when the activity creates the associated menu
		 */
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		return true;
	};

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/**
		 * Called when the user touch an option in the menu
		 */
		switch (item.getItemId()) {
		case R.id.preferences_menu_item:
			/**
			 * User selected the preferences option, we launch the preferences
			 * activity
			 */
			Intent i = new Intent(this, PreferencesActivity.class);
			startActivity(i);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
}