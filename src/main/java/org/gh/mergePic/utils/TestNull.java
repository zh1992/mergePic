package org.gh.mergePic.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class TestNull {

	public static void main(String[] args) throws Exception {
		String path_dir = "/Users/gh/png/";
		File f = new File(path_dir);
		File[] paths = f.listFiles();
		List<String> list = new ArrayList<String>();
		for (File path : paths) {
			BufferedImage img_tmp = ImageIO.read(path);
			if (img_tmp != null) {
			} else {
				list.add(path.getName());
				System.out.println("null :"+path.getName());
			}
		}
		
		System.out.println("null count: " + list.size());
	}

}
