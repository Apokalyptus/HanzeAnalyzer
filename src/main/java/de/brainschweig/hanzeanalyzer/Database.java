package de.brainschweig.hanzeanalyzer;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

class Database {

	private static Connection conn = null;

	static void connect(String connectionString) {
		try {
			conn = DriverManager.getConnection(connectionString);
		} catch (SQLException ex) {

			System.exit(-1);
		}
	}

	static ArrayList<String> fetchResults() {
		Statement stmt;
		ResultSet rs;
		ArrayList<String> values = new ArrayList<String>();

		String selectStatement = "SELECT hanze from results;";

		try {
			conn.setAutoCommit(false);

			stmt = conn.createStatement();

			rs = stmt.executeQuery(selectStatement);
			String[] zips = null;
			while (rs.next()) {
				values.add(rs.getString("hanze"));
			}

		
			rs.close();

		} catch (SQLException e) {
			System.out.println("Executing Query went wrong:");
			e.printStackTrace();

		}
		return values;

	}

	static void insertHanze(String hanze) {
		PreparedStatement insertUrl = null;
		String insertStatement = "INSERT INTO `crawler`.`top_hanze` ( `hanze`, `count`) VALUES (?, 1) ON DUPLICATE KEY UPDATE count = count + 1;";
		try {

			conn.setAutoCommit(true);

			insertUrl = conn.prepareStatement(insertStatement);
			insertUrl.setString(1, hanze);
			insertUrl.executeUpdate();
			insertUrl.close();
			
		} catch (SQLIntegrityConstraintViolationException ice){
			System.out.println("SQLIntegrityConstraintViolationException: ");
			ice.printStackTrace();			
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
		}
	}
}
