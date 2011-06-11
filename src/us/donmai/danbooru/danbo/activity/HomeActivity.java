package us.donmai.danbooru.danbo.activity;

import us.donmai.danbooru.danbo.R;
import us.donmai.danbooru.danbo.SharedPreferencesInstance;
import us.donmai.danbooru.danbo.util.NetworkState;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * The activity launched when the application starts up
 */
public class HomeActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		/* Is a connection available ? */
		NetworkState ns = new NetworkState(this);

		if (!ns.connectionAvailable()) {

			/**
			 * No network available, we display an error message and exit
			 */
			Log.d("danbo", "No connection available");
			AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
			String errorMessage = "You need internet connectivity to use Danbo.";
			alertBuilder.setMessage(errorMessage);
			alertBuilder.setNegativeButton("Exit",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface arg0, int arg1) {
							HomeActivity.this.finish();

						}
					});
			AlertDialog networkAlert = alertBuilder.create();
			networkAlert.show();
		} else {

			// a connection is available
			SharedPreferencesInstance.initialize(this);

			setContentView(R.layout.home);

			String[] menuItems = { "Posts", "Tags" };
			ListView menuItemsListView = (ListView) findViewById(R.id.MenuItems);
			menuItemsListView.setAdapter(new ArrayAdapter<String>(this,
					R.layout.menu_items, menuItems));
			menuItemsListView.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View v,
						int position, long id) {
					switch (position) {
					case 0:
						Intent postsIntent = new Intent(HomeActivity.this,
								PostListActivity.class);
						HomeActivity.this.startActivity(postsIntent);
						break;
					case 1:
						Intent tagsIntent = new Intent(HomeActivity.this,
								TagListActivity.class);
						HomeActivity.this.startActivity(tagsIntent);
						break;
					default:
						break;
					}

				}
			});
		}
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