package us.donmai.danbooru.danbo.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author edouard Helper method related to IO operations
 */
public class IOHelpers {
	/**
	 * @param is
	 * @return a string
	 * @throws IOException
	 */
	static public String convertStreamToString(InputStream is)
			throws IOException {
		final char[] buffer = new char[0x10000];
		StringBuilder out = new StringBuilder();
		Reader in = new InputStreamReader(is, "UTF-8");
		int read;
		do {
			read = in.read(buffer, 0, buffer.length);
			if (read > 0) {
				out.append(buffer, 0, read);
			}
		} while (read >= 0);

		return out.toString();
	}
}
