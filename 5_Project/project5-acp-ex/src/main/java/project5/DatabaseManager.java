package project5;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
	public Connection conn;
	public Statement stat;
	
	public DatabaseManager() {
		try {
			System.out.println("Establishing Connection...");
			SimpleDataSource.init("database.properties");
			conn = SimpleDataSource.getConnection();
		    stat = conn.createStatement();  
		} 
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public void createTable(String input) {
		System.out.println("Creating Table...");
		try {
			stat.execute(input);
		} 
		catch (SQLException e) {
			System.out.println(e.toString());
		}
	}
	
	public void dropTable(String tableName) {
		try {  
			System.out.println("Dropping Table...");
			stat.execute("DROP TABLE " + tableName); 
		}
		catch (Exception e) { 
			System.out.println("\nDrop Failed\n"); 
		} 
	}
	
	public void executeSQLCommand(String command) {
		try {
			stat.execute(command);
		} 
		catch (SQLException e) {
			System.out.println(e.toString());
		}
	}
	
	public String query(String command) throws SQLException {
		ResultSet result = stat.executeQuery(command);
		ResultSetMetaData rsm = result.getMetaData();
		int col = rsm.getColumnCount();
		String query = "";
		
		while(result.next()) {
			for (int i = 1; i <= col; i++) {
				query += result.getString(i) + " ";
			}
			query += "\n";
		}
		
		return query;
	}
	
	public String query(String command, ResultSet result) throws SQLException {
		ResultSetMetaData rsm = result.getMetaData();
		int col = rsm.getColumnCount();
		String query = "";
		
		while(result.next()) {
			for (int i = 1; i <= col; i++) {
				query += result.getString(i) + " ";
			}
			query += "\n";
		}
		
		return query;
	}
	
	public ResultSet queryResultSet(String command) throws SQLException {
		ResultSet result = stat.executeQuery(command);
		
		return result;
	}
}