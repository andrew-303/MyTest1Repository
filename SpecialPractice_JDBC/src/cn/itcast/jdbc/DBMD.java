package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class DBMD {

	public static void main(String[] args) throws Exception {
		Connection conn = JdbcUtils.getConnection();
		DatabaseMetaData metaData = conn.getMetaData();
		System.out.println(metaData.getDatabaseProductName());
		System.out.println(metaData.supportsTransactions());
		conn.close();
	}
}
