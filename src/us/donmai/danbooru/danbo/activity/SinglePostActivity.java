package us.donmai.danbooru.danbo.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import us.donmai.danbooru.danbo.R;
import us.donmai.danbooru.danbo.model.Post;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Activity called when the user wants to see a single
 * {@link us.donmai.danbooru.danbo.model.Post}
 */
public class SinglePostActivity extends Activity {

	private Post _post;
	private Bitmap _postBitmap;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_post);

		this._post = (Post) this.getIntent().getExtras().get("post");
		try {
			URL postFileUrl = new URL(_post.getFileUrl());
			URL postSampleUrl = new URL(_post.getSampleUrl());
			GetPostBitmapTask task = new GetPostBitmapTask();
			if (_post.get_fileSize() > 1024 * 1024) {
				task.execute(postSampleUrl);
			} else {
				task.execute(postFileUrl);
			}
		} catch (Exception e) {
			Log.e("post", e.toString());
		}

	}

	private class GetPostBitmapTask extends AsyncTask<URL, Void, Void> {

		ProgressDialog _progress;

		@Override
		protected void onPreExecute() {
			_progress = ProgressDialog.show(SinglePostActivity.this,
					"Loading image",
					"Please wait while the post image is downloaded", true);
		}

		@Override
		protected Void doInBackground(URL... urls) {
			try {
				SinglePostActivity.this._postBitmap = BitmapFactory
						.decodeStream(urls[0].openStream());
			} catch (Exception e) {
				Log.e("post", "Error while downloading post bitmap:\n"
						+ e.toString());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			RelativeLayout rl = (RelativeLayout) findViewById(R.id.RelativeLayout01);
			rl.removeAllViews();
			ImageView postImage = new ImageView(SinglePostActivity.this);
			postImage.setImageBitmap(SinglePostActivity.this._postBitmap);
			rl.addView(postImage);
			_progress.dismiss();
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.post_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.set_as_wallpaper:

		case R.id.save_image:
			SaveImageToSDTask saveTask = new SaveImageToSDTask();
			saveTask.execute(_post);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	/**
	 * An asynchronous task to download and save an image to the SD card
	 */
	private class SaveImageToSDTask extends AsyncTask<Post, Void, Boolean> {

		ProgressDialog _savingDialog;

		@Override
		protected Boolean doInBackground(Post... posts) {
			String state = Environment.getExternalStorageState();
			Boolean result = false;
			if (Environment.MEDIA_MOUNTED.equals(state)) {
				// We can read and write the media

				File danboDir = new File(Environment
						.getExternalStorageDirectory()
						+ "/Danbo");

				File imageFile;
				if (danboDir.mkdir() || danboDir.exists()) {
					imageFile = new File(Environment
							.getExternalStorageDirectory()
							+ "/Danbo/" + posts[0].getImageName());
				} else {
					imageFile = new File(Environment
							.getExternalStorageDirectory()
							+ "/" + posts[0].getImageName());
				}
				try {
					if (imageFile.createNewFile() || imageFile.exists()) {
						FileOutputStream fos = new FileOutputStream(imageFile);
						int png = posts[0].getImageName().lastIndexOf(".png");
						if (png == -1) {
							SinglePostActivity.this._postBitmap.compress(
									CompressFormat.JPEG, 90, fos);
						} else {
							SinglePostActivity.this._postBitmap.compress(
									CompressFormat.PNG, 90, fos);
						}
					}
				} catch (Exception e) {
					Log.e("danbo", e.toString());
				}
			}
			return result;
		}

		@Override
		protected void onPreExecute() {
			_savingDialog = ProgressDialog.show(SinglePostActivity.this,
					"Saving Image", "Saving image, please wait ...");
		}

		@Override
		protected void onPostExecute(Boolean result) {
			_savingDialog.dismiss();
		}
	}

	private class SetWallpaperTask extends AsyncTask<Bitmap, Void, Boolean> {

		ProgressDialog _progress;

		@Override
		protected void onPreExecute() {

			_progress = new ProgressDialog(SinglePostActivity.this);
			_progress.setMessage("Setting wallpaper, please wait ...");
			_progress.show();
		}

		@Override
		protected Boolean doInBackground(Bitmap... params) {
			boolean result = false;
			if (params[0] != null) {
				try {
					File wallpaperFile = new File(Environment
							.getExternalStorageDirectory()
							+ "/Danbo/wallpaper.png");
					FileInputStream fis = new FileInputStream(wallpaperFile);
					SinglePostActivity.this.setWallpaper(fis);
					result = true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return result;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (result) {
				Toast msg = Toast.makeText(SinglePostActivity.this,
						"Your new wallpaper is set", Toast.LENGTH_SHORT);
				msg.show();
			} else {
				Toast msg = Toast.makeText(SinglePostActivity.this,
						"An error occured while setting the wallpaper",
						Toast.LENGTH_LONG);
				msg.show();
			}
			_progress.dismiss();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.post_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.set_as_wallpaper:
			File danboDir = new File(Environment.getExternalStorageDirectory()
					+ "/Danbo");

			File wallpaperFile;
			if (danboDir.mkdir() || danboDir.exists()) {
				wallpaperFile = new File(Environment
						.getExternalStorageDirectory()
						+ "/Danbo/wallpaper.png");
			} else {
				wallpaperFile = new File(Environment
						.getExternalStorageDirectory()
						+ "/wallpaper.png");
			}
			try {
				if (wallpaperFile.createNewFile() || wallpaperFile.exists()) {
					FileOutputStream fos = new FileOutputStream(wallpaperFile);
					SinglePostActivity.this._postBitmap.compress(
							CompressFormat.PNG, 90, fos);
				}
			} catch (Exception e) {
				Log.e("danbo", e.toString());
			}

			// image cropping

			Intent i = new Intent(SinglePostActivity.this,
					us.donmai.danbooru.danbo.cropimage.CropImage.class);

			i.putExtra("image-path", wallpaperFile.getPath());
			i.putExtra("scale", true);
			startActivityForResult(i, 42);

			return true;
		case R.id.save_image:
			SaveImageToSDTask saveTask = new SaveImageToSDTask();
			saveTask.execute(_post);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 42) {
			SetWallpaperTask wallpaperTask = new SetWallpaperTask();
			wallpaperTask.execute(_postBitmap);
		}
	}
}
