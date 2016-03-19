package org.gh.mergePic.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.gh.mergePic.dao.LocalIconDao;
import org.gh.mergePic.utils.MergeTools;


public class ManagePNG {
	
	private static String sourcePath; // 图片源地址
	private static String destPath; // 图片目的地址
	public static String currentPath;

	public static String getCurrentPath() {
		return currentPath;
	}

	public static void main(String args[]) throws IOException {

		sourcePath = args[0];
		destPath = args[1];
		int count_validPNG = 0;
		
		if (sourcePath != null && destPath != null) {
			
			File f = new File(sourcePath);
			File[] files = f.listFiles();
			// System.out.println("length: " + files.length);

			for (int i = 0; i < files.length; i++) {
				BufferedImage img = null;
				try {
					BufferedImage img_tmp = ImageIO.read(files[i]);
					if (img_tmp == null) {
						continue;
					}
					img = MergeTools.zoomOutImage(img_tmp, 120, 150);
					count_validPNG++;
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
				if (img != null) {
					MergeTools.writeImg(destPath + files[i].getName(), 120, 150, img);
				}
			}
			System.out.println("新增png图标数量为: " + count_validPNG);

			// 如果有新图片更新，则更新数据库
			if (count_validPNG > 0) {
				File directory = new File("");// 参数为空
				currentPath = directory.getCanonicalPath();// 获取当前路径
				File f_dest = new File(destPath); // 遍历目标路径
				File files_dest[] = f_dest.listFiles();
				List<Integer> list = new ArrayList<Integer>();
				for (File file : files_dest) {
					String name = file.getName();
					// 只支持png格式
					if (name.indexOf(".PNG") == -1 && name.indexOf(".png") == -1) {
						continue;
					}
					int index = file.getName().lastIndexOf('.');
					int ico = Integer.parseInt(file.getName().substring(0, index));
					list.add(ico);
				}
				System.out.println("目标目录包含png文件个数为: " + list.size());
				LocalIconDao dao = new LocalIconDao();
				// 更新local_icon表
				dao.insertList(list);
				// 更新website_url表
				dao.updateWebsiteUrl();
			}
		}
	}
}
