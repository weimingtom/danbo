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
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
			Resources res = getResources();
			_progress = ProgressDialog.show(SinglePostActivity.this,
					res.getString(R.string.singlepost_loading_title),
					res.getString(R.string.singlepost_loading_message), true);
		}

		@Override
		protected Void doInBackground(URL... urls) {
			try {
				SinglePostActivity.this._postBitmap = BitmapFactory
						.decodeStream(urls[0].openStream());
			} catch (Exception e) {
				Log.e("post",
						"Error while downloading post bitmap:\n" + e.toString());
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
			try {
				_progress.dismiss();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * An asynchronous task to download and save an image to the SD card
	 */
	private class SaveImageToSDTask extends AsyncTask<Void, Void, Void> {

		ProgressDialog _savingDialog;

		@Override
		protected void onPreExecute() {
			Resources res = getResources();
			_savingDialog = ProgressDialog.show(SinglePostActivity.this,
					res.getString(R.string.singlepost_saving_image_title),
					res.getString(R.string.singlepost_saving_image_message));
		}

		@Override
		protected Void doInBackground(Void... params) {
			String state = Environment.getExternalStorageState();
			if (Environment.MEDIA_MOUNTED.equals(state)) {
				// We can read and write the media
				File rootDirectory = Environment.getExternalStorageDirectory();
				try {
					File danboDirectory = new File(rootDirectory + "/danbo");
					if (!danboDirectory.exists()) {
						danboDirectory.mkdir();
					}

					File imageFile = new File(danboDirectory
							+ _post.getImageName());
					if (!imageFile.exists()) {
						imageFile.createNewFile();
					}
					FileOutputStream fos = new FileOutputStream(imageFile);
					int png = _post.getImageName().lastIndexOf(".png");
					if (png == -1) {
						SinglePostActivity.this._postBitmap.compress(
								CompressFormat.JPEG, 90, fos);
					} else {
						SinglePostActivity.this._postBitmap.compress(
								CompressFormat.PNG, 90, fos);
					}
				} catch (Exception e) {
					Log.e("danbo", e.toString());
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void res) {
			try {
				_savingDialog.dismiss();
			} catch (Exception e) {
			}
		}
	}

	private class SetWallpaperTask extends AsyncTask<Void, Void, Void> {

		ProgressDialog _progress;

		@Override
		protected void onPreExecute() {
			Resources res = getResources();
			_progress = new ProgressDialog(SinglePostActivity.this);
			_progress.setMessage(res
					.getString(R.string.singlepost_setting_wallpaper));
			_progress.show();
		}

		@Override
		protected Void doInBackground(Void... params) {

			String state = Environment.getExternalStorageState();

			if (Environment.MEDIA_MOUNTED.equals(state)) {
				// We can read and write the media
				File rootDirectory = Environment.getExternalStorageDirectory();
				try {
					File danboDirectory = new File(rootDirectory + "/danbo");
					if (!danboDirectory.exists())
						danboDirectory.mkdir();

					File wallpaperFile = new File(danboDirectory
							+ "/wallpaper.jpg");

					if (!wallpaperFile.exists())
						wallpaperFile.createNewFile();

					FileOutputStream fos = new FileOutputStream(wallpaperFile);
					SinglePostActivity.this._postBitmap.compress(
							CompressFormat.JPEG, 90, fos);

				} catch (Exception e) {
					Log.e("danbo", e.toString());
				}
			}
			try {
				File root = Environment.getExternalStorageDirectory();
				File wallpaperFile = new File(root + "/danbo/wallpaper.jpg");
				FileInputStream fis = new FileInputStream(wallpaperFile);
				SinglePostActivity.this.setWallpaper(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Resources res = getResources();
			Toast msg = Toast.makeText(SinglePostActivity.this,
					res.getString(R.string.singlepost_wallpaper_is_set),
					Toast.LENGTH_SHORT);
			msg.show();
			try {
				_progress.dismiss();
			} catch (Exception e) {
			}
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

			// image cropping

			// Intent i = new Intent(SinglePostActivity.this,
			// us.donmai.danbooru.danbo.cropimage.CropImage.class);
			//
			// i.putExtra("image-path", wallpaperFile.getPath());
			// i.putExtra("scale", true);
			// startActivityForResult(i, 42);

			SetWallpaperTask wallpaperTask = new SetWallpaperTask();
			wallpaperTask.execute();

			return true;
		case R.id.save_image:
			SaveImageToSDTask saveTask = new SaveImageToSDTask();
			saveTask.execute();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 42) {
			SetWallpaperTask wallpaperTask = new SetWallpaperTask();
			wallpaperTask.execute();
		}
	}
}
