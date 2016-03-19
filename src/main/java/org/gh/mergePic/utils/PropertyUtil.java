package org.gh.mergePic.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class PropertyUtil {

	private Properties property = new Properties();

	public PropertyUtil(String path) {
		try {
//			File f = new File(path);
//			System.out.println(f.exists()+" , "+path);
//			InputStream in = PropertyUtil.class.getResourceAsStream(path);
			InputStream in = new FileInputStream(new File(path));
			if (in != null) {
				property.load(in);
			}
		} catch (IOException e) {
			System.out.println(path + ":error");
		}
	}

	public String getValue(String key) {
		if (property.containsKey(key)) {
			String value = property.getProperty(key);
			try {
				return new String(value.getBytes("Latin1"), "utf8");
			} catch (UnsupportedEncodingException e) {
				return value;
			}
		} else {
			return null;
		}
	}
}
