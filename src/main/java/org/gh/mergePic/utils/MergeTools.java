package org.gh.mergePic.utils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * @author gh
 */
public class MergeTools {

	/**
	 * 拼接图片
	 * 
	 * @param files
	 *            文件路径列表
	 * @param height
	 *            高度
	 * @param width
	 *            宽度
	 * @param fileName
	 *            生成的文件全名(路径+全名)
	 */
	public static boolean merge(List<String> files, int height, int width, String fileName) {

		int len = files.size();
		if (len < 1) {
			System.out.println("图片数量小于1");
			return false;
		}

		/*
		 * 读取file的RGB
		 */
		File[] src = new File[len];
		BufferedImage[] images = new BufferedImage[len];
		int[][] ImageArrays = new int[len][];
		for (int i = 0; i < len; i++) {
			try {
				src[i] = new File(files.get(i));
				images[i] = ImageIO.read(src[i]);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			int file_width = images[i].getWidth();
			int file_height = images[i].getHeight();
			ImageArrays[i] = new int[file_width * file_height];// 从图片中读取RGB
			ImageArrays[i] = images[i].getRGB(0, 0, file_width, file_height, ImageArrays[i], 0, file_width);
		}

		/*
		 * 计算长宽
		 */
		int newHeight = 16 * height;
		int newWidth = 16 * width;
		// System.out.println("拼接后图像宽度：" + newWidth + " , 拼接后图像高度：" +
		// newHeight);

		/**
		 * 生成新图片 通过BufferedImage的setRGB进行绘图
		 */
		try {
			BufferedImage ImageNew = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_4BYTE_ABGR);
			int height_i = 0;
			int width_i = 0;
			for (int i = 0; i < height; i++) {// 高度
				for (int j = 0; j < width; j++) {// 宽度
					/*
					 * 因为不一定能填充满整个正方形，所以通过下标判断是否应该退出
					 */
					if (i * width + j >= len) {
						break;
					}
					ImageNew.setRGB(width_i, height_i, 16, 16, ImageArrays[i * width + j], 0, 16);
					width_i += 16;
				}
				width_i = 0;
				height_i += 16;
			}

			File outFile = new File(fileName);
			ImageIO.write(ImageNew, "png", outFile);
			// writeImg(path, 16*width, 16*height, ImageNew);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 生成新图片文件
	 * 
	 * @param path
	 * @param width
	 * @param height
	 * @param img
	 */
	public static void writeImg(String path, int width, int height, BufferedImage img) {
		File outFile = new File(path);
		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = newImage.getGraphics();
		g.drawImage(img, 0, 0, width, height, null);
		g.dispose();
		try {
			ImageIO.write(newImage, "jpg", outFile);// 写图片
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//	
//	public static void writeImg(String path,BufferedImage img){
//		int width
//	}

	/**
	 * 按比例压缩
	 * 
	 * @param originalImage
	 * @param times
	 * @return
	 */
	public static BufferedImage zoomOutImage(BufferedImage originalImage, Integer times) {

		int width = originalImage.getWidth() / times;
		int height = originalImage.getHeight() / times;
		BufferedImage newImage = new BufferedImage(width, height, originalImage.getType());
		Graphics g = newImage.getGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
		return newImage;

	}

	/**
	 * 强制按照大小压缩
	 * 
	 * @param originalImage
	 * @param width
	 * @param height
	 * @return
	 */
	public static BufferedImage zoomOutImage(BufferedImage originalImage, int width, int height) {

		if (originalImage == null || width < 1 || height < 1) {
			System.out.println("error");
			return null;
		}
		BufferedImage newImage = new BufferedImage(width, height, 6);
		Graphics g = newImage.getGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
		return newImage;
	}

	

}
