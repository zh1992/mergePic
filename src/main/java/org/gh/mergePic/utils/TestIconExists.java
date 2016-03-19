package org.gh.mergePic.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.gh.mergePic.dao.LocalIconDao;

/**
 * @author gh 插入记录到local_icon表中，表示本地哪些png 记录png的文件名
 */
public class TestIconExists {

	public static String currentPath;

	public static String getCurrentPath() {
		return currentPath;
	}

	public static void main(String[] args) throws IOException {

		File directory = new File("");// 参数为空
		currentPath = directory.getCanonicalPath();
		System.out.println("当前路径为:" + currentPath);

		File f = new File("/Users/gh/png/");
		File files[] = f.listFiles();
		List<Integer> list = new ArrayList<Integer>();
		for (File file : files) {
			String name = file.getName();
			//只支持png
			if (name.indexOf(".PNG")==-1 && name.indexOf(".png")==-1) {
				continue;
			}
			int index = file.getName().lastIndexOf('.');
			int ico = Integer.parseInt(file.getName().substring(0, index));
			list.add(ico);
		}
		System.out.println("size: " + list.size());
		LocalIconDao dao = new LocalIconDao();
		dao.insertList(list);
	}

}
