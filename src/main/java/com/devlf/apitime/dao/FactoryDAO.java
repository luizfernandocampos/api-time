package com.devlf.apitime.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class FactoryDAO {

	public static String url = "jdbc:mysql://127.0.0.1/committedtime";
	public static String user = "root";
	public static String pwd = "time";
	public static String classe = "com.mysql.jdbc.Driver";

	public static Connection getConnection() throws Exception {

		Class.forName(classe);
		Connection conn = null;
		conn = DriverManager.getConnection(url, user, pwd);

		return conn;
	}

}
