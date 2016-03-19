package org.gh.mergePic.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.gh.mergePic.db.Conn;
import org.gh.mergePic.bean.URL;

/**
 * @author gh URLDao
 */
public class URLDao extends BaseDao<URL> {
	private Connection conn;

	@Override
	public List<URL> queryList(String sql) {
		conn = Conn.getConnection();
		List<URL> list = new ArrayList<URL>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String url = rs.getString(3);
				int tid = rs.getInt(4);
				int order = rs.getInt(5);
				String domain = rs.getString(6);
				int ico = rs.getInt(7);
				list.add(new URL(id, name, url, tid, order, domain, ico));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Integer> queryTidList(int tid) {
		List<Integer> list = new ArrayList<Integer>();
		conn = Conn.getConnection();
		try {
			Statement stmt = conn.createStatement();
			String sql = "select * from website_dir where pid = " + tid + " and shown=1";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int tid_queried = rs.getInt(2);
				list.add(tid_queried);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public String queryById(String sql) {
		conn = Conn.getConnection();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String domain = rs.getString(6);
				return domain;
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

}
