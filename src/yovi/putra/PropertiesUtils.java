package yovi.putra;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/***
 * @author yovi.putra
 */
public class PropertiesUtils {
	/***
	 * @param context
	 * @param propertiesFile
	 * @param key
	 * @return
	 * 
	 *         propertiesFile : direktori file config. ex : properitesFile =
	 *         config/config.properties
	 */
	public static String getProperties(Class<?> context, String propertiesFile,
			String key) {
		String _return = "";
		Properties prop = new Properties();
		InputStream is = context.getResourceAsStream(propertiesFile);
		try {
			prop.load(is);
			_return = prop.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return _return;
	}

	/***
	 * @param context
	 * @param propertiesFile
	 * @return
	 * 
	 *         propertiesFile : direktori file config. ex : properitesFile =
	 *         config/config.properties
	 */
	public static Properties getProperties(Class<?> context,
			String propertiesFile) {
		Properties prop = new Properties();
		InputStream is = context.getResourceAsStream(propertiesFile);
		try {
			prop.load(is);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					
				}
		}
		return prop;
	}
}
