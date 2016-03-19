package org.gh.mergePic.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.gh.mergePic.utils.MergeTools;

/**
 * yunnengtai那个项目迁移到as之后，总是说png文件sRGB区的问题； 
 * 于是用这个类将所有的png文件转换为了jpg文件
 * @author gh
 *
 */
public class CutsRGB {

	public static void main(String[] args) {
		File f = new File("/Users/gh/pic/");
		File[] files = f.listFiles();

		for (File file : files) {
			BufferedImage img = null;
			try {
				System.out.println(file.getName());
				BufferedImage img_tmp = ImageIO.read(file);
				if (img_tmp == null) {
					continue;
				}
				int width = img_tmp.getWidth();
				int height = img_tmp.getHeight();
				int index = file.getName().lastIndexOf('.');
				String name = file.getName().substring(0, index);
				MergeTools.writeImg("/Users/gh/result_pic/" + name + ".jpg", width, height, img_tmp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
