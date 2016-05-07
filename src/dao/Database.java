package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
	public Connection getConnection() throws Exception {
		
		String url = "jdbc:mysql://127.0.0.1:3306/blackjack";
		String user = "root";
		String password = "root";

		// Load the Connector/J driver
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		// Establish connection to MySQL
		Connection conn = DriverManager.getConnection(url, user, password);
		
		return conn;

	}

}
