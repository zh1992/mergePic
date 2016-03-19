package org.gh.mergePic.db;

import java.sql.Connection;
import java.sql.DriverManager;

import org.gh.mergePic.service.ManagePNG;
import org.gh.mergePic.service.MergeByTid;
import org.gh.mergePic.utils.PropertyUtil;

public class Conn {

	private static Connection conn;
	private static String path = MergeByTid.getCurrentPath() + "/jdbc.properties";
	private static PropertyUtil property;

	public static Connection getConnection() {
		if (null == conn) {
			try {
				property = new PropertyUtil(path);
				Class.forName(property.getValue("jdbc.driverClassName"));
				conn = DriverManager.getConnection(property.getValue("jdbc.url"), property.getValue("jdbc.username"),
						property.getValue("jdbc.password"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return conn;
	}

}
