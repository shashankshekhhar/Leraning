package db;

import java.sql.DriverManager;

public class SqlConnection {

	public static java.sql.Connection connector() {

		String driverName = "com.mysql.jdbc.Driver";
		// properties.getProperty("driverName");
		String jdbcUrl = "jdbc:mysql://";
		// properties.getProperty("jdbcUrl");
		String user = "root";// properties.getProperty("user");
		String password = "root";// properties.getProperty("password");
		String serverName = "localhost";// properties.getProperty("serverName");
		String port = "3306";// properties.getProperty("port");
		String databaseName = "";
		try {
			Class.forName(driverName);
			java.sql.Connection con = DriverManager
					.getConnection(jdbcUrl + serverName + ":" + port + "/" + databaseName, user, password);
			return con;
		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return null;
		}

	}
}
