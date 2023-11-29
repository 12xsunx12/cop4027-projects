package project5;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Model {
	public Connection conn;
	public Statement stat;
	
	public Model() {
		initDB();
		createDatabase();
	}
	
	public void initDB() {
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
	
	public void dropTable(String tableName) {
		try {  
			System.out.println("Dropping Table...");
			stat.execute("DROP TABLE " + tableName); 
		}
		catch (Exception e) { 
			System.out.println("\nDrop Failed\n"); 
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
	
	public ResultSet queryResultSet(String command) throws SQLException {
		ResultSet result = stat.executeQuery(command);
		
		return result;
	}
	
	public void executeSQLCommand(String command) {
		try {
			stat.execute(command);
		} 
		catch (SQLException e) {
			System.out.println(e.toString());
		}
	}
	
	public void createDatabase() {
		dropTable("Instruments");
		dropTable("Locations");
		dropTable("Inventory");
		try {
			createInstruments();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			createLocations();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			createInventory();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	
	public ResultSet searchDB(String instrumentType, String brand, String maxCost, String warehouseLocation) {
	    StringBuilder query = new StringBuilder("SELECT * FROM Instruments JOIN Inventory ON Instruments.instNumber = Inventory.iNumber JOIN Locations ON Inventory.lNumber = Locations.locNumber");
	    ArrayList<String> conditions = new ArrayList<>();

	    if (instrumentType != null && !instrumentType.isEmpty() && !instrumentType.equals("all")) {
	        conditions.add("Instruments.instName = '" + instrumentType + "'");
	    }
	    if (brand != null && !brand.isEmpty() && !brand.equals("all")) {
	        conditions.add("Instruments.descrip = '" + brand + "'");
	    }
	    if (maxCost != null && !maxCost.isEmpty() && !maxCost.equals("all")) {
	        conditions.add("Instruments.cost <= " + maxCost);
	    }
	    if (warehouseLocation != null && !warehouseLocation.isEmpty() && !warehouseLocation.equals("all")) {
	        warehouseLocation = warehouseLocation.replace(",", "");
	        conditions.add("Locations.address = '" + warehouseLocation + "'");
	    }

	    if (!conditions.isEmpty()) {
	        query.append(" WHERE ").append(String.join(" AND ", conditions));
	    }

	    try {
	        System.out.println(query.toString());
	        return queryResultSet(query.toString());
	    } catch (Exception e) {
	        System.out.println("Error executing search query: " + e.getMessage());
	        return null;
	    }
	}



	
	private ResultSet createInstruments() throws Exception {
		createTable("CREATE TABLE Instruments (instName CHAR(12),instNumber INTEGER,cost DOUBLE,descrip CHAR(20))");
		executeSQLCommand("INSERT INTO Instruments VALUES ('guitar',1,100.0,'yamaha')");
		executeSQLCommand("INSERT INTO Instruments VALUES ('guitar',2,500.0,'gibson')");
		executeSQLCommand("INSERT INTO Instruments VALUES ('bass',3,250.0,'fender')");
		executeSQLCommand("INSERT INTO Instruments VALUES ('keyboard',4,600.0,'roland')");
		executeSQLCommand("INSERT INTO Instruments VALUES ('keyboard',5,500.0,'alesis')");
		executeSQLCommand("INSERT INTO Instruments VALUES ('drums',6,1500.0,'ludwig')");
		executeSQLCommand("INSERT INTO Instruments VALUES ('drums',7,400.0,'yamaha')");
		ResultSet result = queryResultSet("SELECT * FROM Instruments");
		return result;
	}

	private ResultSet createLocations() throws Exception {
		createTable("CREATE TABLE Locations (locName CHAR(12),locNumber INTEGER,address CHAR(50))");
		executeSQLCommand("INSERT INTO Locations VALUES ('PNS',1,'Pensacola Florida')");
		executeSQLCommand("INSERT INTO Locations VALUES ('CLT',2,'Charlotte North Carolina')");
		executeSQLCommand("INSERT INTO Locations VALUES ('DFW',3,'Dallas Fort Worth Texas')");
		ResultSet result = queryResultSet("SELECT * FROM Locations");
		return result;
	}

	private ResultSet createInventory() throws Exception {
		createTable("CREATE TABLE Inventory (iNumber INTEGER,lNumber INTEGER,quantity INTEGER)");
		executeSQLCommand("INSERT INTO Inventory VALUES (1,1,15)");
		executeSQLCommand("INSERT INTO Inventory VALUES (1,2,27)");
		executeSQLCommand("INSERT INTO Inventory VALUES (1,3,20)");
		executeSQLCommand("INSERT INTO Inventory VALUES (2,1,10)");
		executeSQLCommand("INSERT INTO Inventory VALUES (2,2,10)");
		executeSQLCommand("INSERT INTO Inventory VALUES (2,3,35)");
		executeSQLCommand("INSERT INTO Inventory VALUES (3,1,45)");
		executeSQLCommand("INSERT INTO Inventory VALUES (3,2,10)");
		executeSQLCommand("INSERT INTO Inventory VALUES (3,3,17)");
		executeSQLCommand("INSERT INTO Inventory VALUES (4,1,28)");
		executeSQLCommand("INSERT INTO Inventory VALUES (4,2,10)");
		executeSQLCommand("INSERT INTO Inventory VALUES (4,3,16)");
		executeSQLCommand("INSERT INTO Inventory VALUES (5,1,28)");
		executeSQLCommand("INSERT INTO Inventory VALUES (5,2,10)");
		executeSQLCommand("INSERT INTO Inventory VALUES (5,3,1)");
		executeSQLCommand("INSERT INTO Inventory VALUES (6,1,2)");
		executeSQLCommand("INSERT INTO Inventory VALUES (6,2,10)");
		executeSQLCommand("INSERT INTO Inventory VALUES (6,3,16)");
		executeSQLCommand("INSERT INTO Inventory VALUES (7,1,16)");
		executeSQLCommand("INSERT INTO Inventory VALUES (7,2,4)");
		executeSQLCommand("INSERT INTO Inventory VALUES (7,3,12)");     
		ResultSet result = queryResultSet("SELECT * FROM Inventory");
		return result;
	}
	
}
