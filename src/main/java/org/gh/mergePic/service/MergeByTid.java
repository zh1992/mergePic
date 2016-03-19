package org.gh.mergePic.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.gh.mergePic.bean.URL;
import org.gh.mergePic.dao.PosDao;
import org.gh.mergePic.dao.URLDao;
import org.gh.mergePic.utils.MergeTools;

public class MergeByTid {

	private static int countPerTid;// 总图片数
	private static int countDimen;// 正方形边长
	private static String sourcePath; // 图片源地址
	private static String destPath; // 图片目的地址
	private static String currentPath;//当前位置
	
	public static String getCurrentPath() {
		return currentPath;
	}
 
	public static void setSourcePath(String sourcePath) {
		MergeByTid.sourcePath = sourcePath;
	}

	/**
	 * 需要arguments 第一个为 图片源地址 ； 第二个为 图片目的地址
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String args[]) throws IOException {

		/**
		 * 先获取当前目录
		 */
		File directory = new File("");//参数为空 
		currentPath = directory.getCanonicalPath(); 
		System.out.println("当前路径为:"+currentPath);
		
		sourcePath = args[0];
		destPath = args[1];
		if (sourcePath != null && destPath != null) {

			URLDao dao_url = new URLDao();
			PosDao dao_pos = new PosDao();
			List<Integer> list_root = dao_url.queryTidList(0);

			// 先清空三个pos字段
			if (list_root.size() > 0) {
				dao_pos.clearPos();
			}
			
			for (int j = 0; j < list_root.size(); j++) {
				int pid = list_root.get(j);
				List<Integer> list_leaf = dao_url.queryTidList(pid);
				String sql = "select * from website_url where ico>0 and tid in (";
				String tid_str = "";
				for (int i = 0; i < list_leaf.size(); i++) {
					if (i != list_leaf.size() - 1) {
						String tmp = list_leaf.get(i) + ",";
						tid_str += tmp;
					} else {
						String tmp = list_leaf.get(i) + "";
						tid_str += tmp;
					}
				}
				sql += tid_str;
				sql += ") order by `order` asc";
				List<String> list_path = new ArrayList<String>();
				list_path.add(sourcePath + "0.png");
				List<URL> list_url = dao_url.queryList(sql);
				countPerTid = (int) Double.parseDouble("" + list_url.size());
				countPerTid++;
				countDimen = (int) Math.ceil(Math.sqrt(countPerTid));

				for (int k = 0; k < list_url.size(); k++) {
					int ico = list_url.get(k).getIco();
					String url_path = sourcePath + ico + ".png";
					list_path.add(url_path);
				}

				boolean isMergeSuccessful = false;
				// 拼接图片
				isMergeSuccessful = MergeTools.merge(list_path, countDimen, countDimen, destPath + j + ".png");
				// 拼接图片成功才定位坐标
				if (true == isMergeSuccessful) {
					System.out.println("图片拼接成功");
					// 更新(index_w,index,y)
					dao_pos.PosByList(j, list_url, countDimen);
					// 更新index_pic 从而形成(index_pic,index_w,index_h)
					dao_pos.updateRangeTid(j, tid_str);
				} else {
					System.out.println("图片拼接失败");
				}
			}
		}
	}

}
