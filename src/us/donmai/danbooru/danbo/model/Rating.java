package us.donmai.danbooru.danbo.model;

import java.io.Serializable;

/**
 * Constants specifying the "safety" of a post
 */
public class Rating implements Serializable {
	private static final long serialVersionUID = 7308857740495590656L;
	
	/**
	 * Safe for everyone
	 */
	static public final int SAFE = 0;
	/**
	 * Might not be safe for children
	 */
	static public final int QUESTIONNABLE = 1;
	/**
	 * Adult only
	 */
	static public final int EXPLICIT = 2;
}
