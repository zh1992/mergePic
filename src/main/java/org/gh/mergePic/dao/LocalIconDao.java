package org.gh.mergePic.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.gh.mergePic.db.Conn;

/**
 * @author gh 处理本地png文件夹中的png文件，将文件名信息导入数据库 、 local_icon表中
 */
public class LocalIconDao extends BaseDao<Integer> {

	Connection conn;

	@Override
	public List<Integer> queryList(String sql) {
		return null;
	}

	public void insertList(List<Integer> list) {
		conn = Conn.getConnection();
		int count_validInsertion = 0;
		try {
			// 先清空local_icon表
			clear(conn);
			Statement stmt = conn.createStatement();
			for (int i = 0; i < list.size(); i++) {
				int j = 1 + i;
				String sql = "insert into local_icon values(" + j + "," + list.get(i) + ")";
				stmt.execute(sql);
				count_validInsertion++;
			}
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error:插入到local_icon出错");
		} finally {
			System.out.println("插入 "+count_validInsertion+" 记录到local_icon");
		}

	}

	private void clear(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			stmt.execute("truncate table local_icon");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error: truncate local_icon出错");
		}
	}

	public void updateWebsiteUrl() {
		conn = Conn.getConnection();
		try {
			Statement stmt = conn.createStatement();
			String sql_update = "update website_url,local_icon SET website_url.ico = local_icon.ico where `website_url`.id = `local_icon`.ico and website_url.ico = 0";
			stmt.execute(sql_update);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error:根据local_icon更新website_url出错");
		}

	}

}
