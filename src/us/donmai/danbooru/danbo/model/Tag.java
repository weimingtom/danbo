package us.donmai.danbooru.danbo.model;

import java.io.Serializable;

/**
 * Tags are metadata associated with a post : anime, character ...
 */
public class Tag implements Serializable {
	
	private static final long serialVersionUID = 8965414316154455353L;
	private String _name;
	private int _type;
	
	/**
	 * A general tag
	 */
	public final int TYPE_GENERAL = 0;
	
	/**
	 * Set this type when the tag is an artist name
	 */
	public final int TYPE_ARTIST = 1;
	
	/**
	 * 
	 */
	public final int TYPE_COPYRIGHT = 3;
	
	/**
	 * When the type is an anime/manga character
	 */
	public final int TYPE_CHARACTER = 4;

	
	/**
	 * @param name
	 */
	public Tag(String name) {
		super();
		_name = name;
	}

	/**
	 * @param name
	 * @param type
	 */
	public Tag(String name, int type) {
		super();
		_name = name;
		_type = type;
	}

	/**
	 * @return the tag name
	 */
	public String getName() {
		return _name;
	}

	/**
	 * @param name
	 *            Set the tag name
	 */
	public void setName(String name) {
		this._name = name;
	}

	/**
	 * @param type 
	 */
	public void setType(int type) {
		this._type = type;
	}

	/**
	 * @return the _type
	 */
	public int getType() {
		return _type;
	}
}
