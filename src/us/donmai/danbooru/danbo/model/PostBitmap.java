package us.donmai.danbooru.danbo.model;

import android.graphics.Bitmap;

public class PostBitmap {
	private Post _post;
	private Bitmap _bitmap;

	public Post getPost() {
		return _post;
	}

	public Bitmap getBitmap() {
		return _bitmap;
	}

	public PostBitmap(Post p, Bitmap b) {
		this._post = p;
		this._bitmap = b;
	}
}
