package project5;

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
	
	/*
	 * Constructor
	 */
	public Model() {
		createDatabase();
	}
	
	public void createDatabase() {
		SimpleDataSource simpleDataSource = new SimpleDataSource();
		Connection connection = null;
		Statement statement = null;
		
		try {
			connection = simpleDataSource.getConnection();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			createInstruments(statement);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			createLocations(statement);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			createInventory(statement);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private ResultSet createInstruments(Statement stat) throws Exception {
	         stat.execute("CREATE TABLE Instruments (instName CHAR(12),instNumber INTEGER,cost DOUBLE,descrip CHAR(20))");
	         stat.execute("INSERT INTO Instruments VALUES ('guitar',1,100.0,'yamaha')");
	         stat.execute("INSERT INTO Instruments VALUES ('guitar',2,500.0,'gibson')");
	         stat.execute("INSERT INTO Instruments VALUES ('bass',3,250.0,'fender')");
	         stat.execute("INSERT INTO Instruments VALUES ('keyboard',4,600.0,'roland')");
	         stat.execute("INSERT INTO Instruments VALUES ('keyboard',5,500.0,'alesis')");
	         stat.execute("INSERT INTO Instruments VALUES ('drums',6,1500.0,'ludwig')");
	         stat.execute("INSERT INTO Instruments VALUES ('drums',7,400.0,'yamaha')");
	         ResultSet result = stat.executeQuery("SELECT * FROM Instruments");
	         return result;
	  }

	  private ResultSet createLocations(Statement stat) throws Exception {
	         stat.execute("CREATE TABLE Locations (locName CHAR(12),locNumber INTEGER,address CHAR(50))");
	         stat.execute("INSERT INTO Locations VALUES ('PNS',1,'Pensacola Florida')");
	         stat.execute("INSERT INTO Locations VALUES ('CLT',2,'Charlotte North Carolina')");
	         stat.execute("INSERT INTO Locations VALUES ('DFW',3,'Dallas Fort Worth Texas')");
	         ResultSet result = stat.executeQuery("SELECT * FROM Locations");
	         return result;
	  }

	  private ResultSet createInventory(Statement stat) throws Exception {
	         stat.execute("CREATE TABLE Inventory (iNumber INTEGER,lNumber INTEGER,quantity INTEGER)");
	         stat.execute("INSERT INTO Inventory VALUES (1,1,15)");
	         stat.execute("INSERT INTO Inventory VALUES (1,2,27)");
	         stat.execute("INSERT INTO Inventory VALUES (1,3,20)");
	         stat.execute("INSERT INTO Inventory VALUES (2,1,10)");
	         stat.execute("INSERT INTO Inventory VALUES (2,2,10)");
	         stat.execute("INSERT INTO Inventory VALUES (2,3,35)");
	         stat.execute("INSERT INTO Inventory VALUES (3,1,45)");
	         stat.execute("INSERT INTO Inventory VALUES (3,2,10)");
	         stat.execute("INSERT INTO Inventory VALUES (3,3,17)");
	         stat.execute("INSERT INTO Inventory VALUES (4,1,28)");
	         stat.execute("INSERT INTO Inventory VALUES (4,2,10)");
	         stat.execute("INSERT INTO Inventory VALUES (4,3,16)");
	         stat.execute("INSERT INTO Inventory VALUES (5,1,28)");
	         stat.execute("INSERT INTO Inventory VALUES (5,2,10)");
	         stat.execute("INSERT INTO Inventory VALUES (5,3,1)");
	         stat.execute("INSERT INTO Inventory VALUES (6,1,2)");
	         stat.execute("INSERT INTO Inventory VALUES (6,2,10)");
	         stat.execute("INSERT INTO Inventory VALUES (6,3,16)");
	         stat.execute("INSERT INTO Inventory VALUES (7,1,16)");
	         stat.execute("INSERT INTO Inventory VALUES (7,2,4)");
	         stat.execute("INSERT INTO Inventory VALUES (7,3,12)");     
	         ResultSet result = stat.executeQuery("SELECT * FROM Inventory");
	         return result;
	  }
}
