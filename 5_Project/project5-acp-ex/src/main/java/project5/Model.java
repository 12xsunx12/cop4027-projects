package project5;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
