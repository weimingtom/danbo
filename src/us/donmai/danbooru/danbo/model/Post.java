package us.donmai.danbooru.danbo.model;

import java.io.Serializable;

/**
 * An image posted on the danbooru-based site
 */
public class Post implements Serializable {
	private static final long serialVersionUID = 1793362629392152009L;

	private int _id;
	private String _previewUrl;
	private String _sampleUrl;
	private String _fileUrl;
	private int _fileSize;

	/**
	 * Empty constructor
	 */
	public Post() {
	}

	/**
	 * @param id
	 * @param previewUrl
	 * @param sampleUrl
	 * @param fileUrl
	 * @param fileSize
	 */
	public Post(int id, String previewUrl, String sampleUrl, String fileUrl,
			int fileSize) {
		_id = id;
		_previewUrl = previewUrl;
		_sampleUrl = sampleUrl;
		_fileUrl = fileUrl;
		_fileSize = fileSize;
	}

	/**
	 * @return the post ID
	 */
	public int getId() {
		return _id;
	}

	/**
	 * @return the _previewUrl
	 */
	public String getPreviewUrl() {
		return _previewUrl;
	}

	/**
	 * @param previewUrl
	 *            the _previewUrl to set
	 */
	public void setPreviewUrl(String previewUrl) {
		_previewUrl = previewUrl;
	}

	/**
	 * @return the _sampleUrl
	 */
	public String getSampleUrl() {
		return _sampleUrl;
	}

	/**
	 * @param sampleUrl
	 *            the _sampleUrl to set
	 */
	public void setSampleUrl(String sampleUrl) {
		_sampleUrl = sampleUrl;
	}

	/**
	 * @return the _fileUrl
	 */
	public String getFileUrl() {
		return _fileUrl;
	}

	/**
	 * @param fileUrl
	 *            the _fileUrl to set
	 */
	public void setFileUrl(String fileUrl) {
		_fileUrl = fileUrl;
	}

	public int get_fileSize() {
		return _fileSize;
	}

	public void set_fileSize(int _fileSize) {
		this._fileSize = _fileSize;
	}

	/**
	 * @return The image name as a String
	 */
	public String getImageName() {
		return this.getFileUrl().substring(this.getFileUrl().lastIndexOf('/'));
	}

	/**
	 * If both objects are posts and they have the same id, they are equal
	 */
	@Override
	public boolean equals(Object o) {
		Post p = (Post) o;
		if (p != null && p.getId() == this.getId()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * The unique HashCode for a post is its id
	 */
	@Override
	public int hashCode() {
		return this.getId();
	}
}
