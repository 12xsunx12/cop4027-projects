package project5;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * MODEL - handles all the back-end, database code, and data-containers
 * 
 * - Code Formatting (in this order)
 * 		- variable declarations
 * 		- public constructor();
 * 		- private methods();
 * 		- public methods();
 * 		- public toString();
 */
public class Model {
	/*
	 * variable declarations
	 */
	private DatabaseManager databaseManager;
	
	/*
	 * Constructor
	 */
	public Model() {
		databaseManager = new DatabaseManager();
		createDatabase();
	}
	
	public void createDatabase() {
		databaseManager.dropTable("Instruments");
		databaseManager.dropTable("Locations");
		databaseManager.dropTable("Inventory");
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
	
	public void select(String instrumentType, String brand, String maxCost, String warehouseLocation) {
		String query = "SELECT DISTINCT *"
				+ " FROM Instruments"
				+ " JOIN Inventory ON Instruments.instNumber = Inventory.iNumber"
				+ " JOIN Locations ON Inventory.lNumber = Locations.locNumber";
		try {
			System.out.println(databaseManager.query(query));
		} catch (SQLException e) {
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
	        return databaseManager.queryResultSet(query.toString());
	    } catch (Exception e) {
	        System.out.println("Error executing search query: " + e.getMessage());
	        return null;
	    }
	}



	
	private ResultSet createInstruments() throws Exception {
		databaseManager.createTable("CREATE TABLE Instruments (instName CHAR(12),instNumber INTEGER,cost DOUBLE,descrip CHAR(20))");
		databaseManager.executeSQLCommand("INSERT INTO Instruments VALUES ('guitar',1,100.0,'yamaha')");
		databaseManager.executeSQLCommand("INSERT INTO Instruments VALUES ('guitar',2,500.0,'gibson')");
		databaseManager.executeSQLCommand("INSERT INTO Instruments VALUES ('bass',3,250.0,'fender')");
		databaseManager.executeSQLCommand("INSERT INTO Instruments VALUES ('keyboard',4,600.0,'roland')");
		databaseManager.executeSQLCommand("INSERT INTO Instruments VALUES ('keyboard',5,500.0,'alesis')");
		databaseManager.executeSQLCommand("INSERT INTO Instruments VALUES ('drums',6,1500.0,'ludwig')");
		databaseManager.executeSQLCommand("INSERT INTO Instruments VALUES ('drums',7,400.0,'yamaha')");
		ResultSet result = databaseManager.queryResultSet("SELECT * FROM Instruments");
		return result;
	}

	private ResultSet createLocations() throws Exception {
		databaseManager.createTable("CREATE TABLE Locations (locName CHAR(12),locNumber INTEGER,address CHAR(50))");
		databaseManager.executeSQLCommand("INSERT INTO Locations VALUES ('PNS',1,'Pensacola Florida')");
		databaseManager.executeSQLCommand("INSERT INTO Locations VALUES ('CLT',2,'Charlotte North Carolina')");
		databaseManager.executeSQLCommand("INSERT INTO Locations VALUES ('DFW',3,'Dallas Fort Worth Texas')");
		ResultSet result = databaseManager.queryResultSet("SELECT * FROM Locations");
		return result;
	}

	private ResultSet createInventory() throws Exception {
		databaseManager.createTable("CREATE TABLE Inventory (iNumber INTEGER,lNumber INTEGER,quantity INTEGER)");
		databaseManager.executeSQLCommand("INSERT INTO Inventory VALUES (1,1,15)");
		databaseManager.executeSQLCommand("INSERT INTO Inventory VALUES (1,2,27)");
		databaseManager.executeSQLCommand("INSERT INTO Inventory VALUES (1,3,20)");
		databaseManager.executeSQLCommand("INSERT INTO Inventory VALUES (2,1,10)");
		databaseManager.executeSQLCommand("INSERT INTO Inventory VALUES (2,2,10)");
		databaseManager.executeSQLCommand("INSERT INTO Inventory VALUES (2,3,35)");
		databaseManager.executeSQLCommand("INSERT INTO Inventory VALUES (3,1,45)");
		databaseManager.executeSQLCommand("INSERT INTO Inventory VALUES (3,2,10)");
		databaseManager.executeSQLCommand("INSERT INTO Inventory VALUES (3,3,17)");
		databaseManager.executeSQLCommand("INSERT INTO Inventory VALUES (4,1,28)");
		databaseManager.executeSQLCommand("INSERT INTO Inventory VALUES (4,2,10)");
		databaseManager.executeSQLCommand("INSERT INTO Inventory VALUES (4,3,16)");
		databaseManager.executeSQLCommand("INSERT INTO Inventory VALUES (5,1,28)");
		databaseManager.executeSQLCommand("INSERT INTO Inventory VALUES (5,2,10)");
		databaseManager.executeSQLCommand("INSERT INTO Inventory VALUES (5,3,1)");
		databaseManager.executeSQLCommand("INSERT INTO Inventory VALUES (6,1,2)");
		databaseManager.executeSQLCommand("INSERT INTO Inventory VALUES (6,2,10)");
		databaseManager.executeSQLCommand("INSERT INTO Inventory VALUES (6,3,16)");
		databaseManager.executeSQLCommand("INSERT INTO Inventory VALUES (7,1,16)");
		databaseManager.executeSQLCommand("INSERT INTO Inventory VALUES (7,2,4)");
		databaseManager.executeSQLCommand("INSERT INTO Inventory VALUES (7,3,12)");     
		ResultSet result = databaseManager.queryResultSet("SELECT * FROM Inventory");
		return result;
	}
	
}
