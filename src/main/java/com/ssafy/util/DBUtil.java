package com.ssafy.util;

import java.sql.*;
import java.util.Iterator;

public class DBUtil {
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/ssafytrip?serverTimezone=UTC";
	private static final String DB_ID = "ssafy";
	private static final String DB_PWD = "ssafy";
	
	private static DBUtil instance = null;
	
	static {
		try {
			new DBUtil();
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private DBUtil() {
		instance = this;
	}
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, DB_ID, DB_PWD);
	}
	
	public static DBUtil getInstance() {
		if(instance == null) new DBUtil();
		return instance;
	}
	
	public static void close(AutoCloseable... autoCloseables) {
		for (AutoCloseable autoCloseable : autoCloseables) {
			if (autoCloseable != null) {
				try {
					autoCloseable.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
