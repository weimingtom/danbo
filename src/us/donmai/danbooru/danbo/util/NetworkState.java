package us.donmai.danbooru.danbo.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Gives information about the network state of the phone
 */
public class NetworkState {
	private ConnectivityManager cm;

	/**
	 * @param c
	 *            A valid context
	 */
	public NetworkState(Context c) {
		this.cm = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);

	}

	/**
	 * @return Returns true if a connection is available for passing data
	 */
	public boolean connectionAvailable() {
		boolean networkAvailable = false;
		NetworkInfo[] networks = cm.getAllNetworkInfo();
		for (int i = 0; i < networks.length; i++) {
			if (networks[i].isConnected()) {
				networkAvailable = true;
			} else {
				continue;
			}
		}
		return networkAvailable;
	}
}
