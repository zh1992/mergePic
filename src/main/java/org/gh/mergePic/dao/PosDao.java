package org.gh.mergePic.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.gh.mergePic.bean.URL;
import org.gh.mergePic.db.Conn;

public class PosDao {

	Connection conn;

	/**
	 * 处理index_w,index_y
	 * 
	 * @param index_pic
	 * @param list
	 * @param length
	 */
	public void PosByList(int index_pic, List<URL> list, int length) {
		if (list != null && length > 0) {
			/*
			 * 先处理坐标 横向为index_w,纵向为index_h 某点坐标为(index_w,index_h)
			 */
			int index_p = index_pic;
			for (int i = 0; i < list.size(); i++) {
				int index_w = (i + 1) % length;
				int index_h = (i + 1) / length;
				list.get(i).setIndex_p(index_p);
				list.get(i).setIndex_w(index_w);
				list.get(i).setIndex_h(index_h);
			}
			/*
			 * 更新数据库
			 */
			conn = Conn.getConnection();
			try {
				// 更新
				Statement stmt = conn.createStatement();
				for (int i = 0; i < list.size(); i++) {
					String sql = "update website_url set index_pic=" + index_p + " , index_w="
							+ list.get(i).getIndex_w() + " , index_h=" + list.get(i).getIndex_h() + " where id="
							+ list.get(i).getId();
					stmt.execute(sql);
				}
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				System.out.println("更新数据库中坐标值index_w,index_h");
			}
		}
	}

	public void clearPos() {
		conn = Conn.getConnection();
		Statement stmt_clear;
		try {
			stmt_clear = conn.createStatement();
			String sql_clear = "update website_url set index_pic=0,index_w=0,index_h=0";
			stmt_clear.execute(sql_clear);
			stmt_clear.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			System.out.println("清空数据库中原坐标值");
		}

	}

	/**
	 * 处理index_pic
	 * 
	 * @param index_pic
	 * @param start
	 * @param end
	 */
	public void updateRangeTid(int index_pic, int start, int end) {
		if (start >= end || index_pic < 0) {
			return;
		}
		conn = Conn.getConnection();
		try {
			Statement stmt = conn.createStatement();
			String sql = "update website_url set index_pic= " + index_pic + " where tid> " + start + " and tid<" + end;
			stmt.execute(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			System.out.println("更新数据库中坐标值index_pic");
		}
	}

	/**
	 * 
	 * @param index_pic
	 * @param str
	 *            tid集合 形如：10,11,12
	 */
	public void updateRangeTid(int index_pic, String str) {
		if (str == null || index_pic < 0) {
			return;
		}
		conn = Conn.getConnection();
		try {
			Statement stmt = conn.createStatement();
			String sql = "update website_url set index_pic= " + index_pic + " where tid in (" + str + ")";
//			System.out.println(sql);
			stmt.execute(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
