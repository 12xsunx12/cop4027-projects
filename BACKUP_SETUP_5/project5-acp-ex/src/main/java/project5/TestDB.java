package project5;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Scanner;
import java.lang.reflect.*;

/**
   Tests a database installation by creating and querying
   a sample table.
*/
public class TestDB 
{
	private Connection conn;
	private Statement stat;
	
	
	// Constructor initializes the DB
	public TestDB() throws Exception {
	    SimpleDataSource.init("database.properties");
	    this.conn = SimpleDataSource.getConnection();
	    this.stat = conn.createStatement();
	    System.out.println("DB connection initialized");
	}
	
	public ResultSet createInstruments() throws Exception
	  {
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

	  public ResultSet createLocations() throws Exception
	  {
	         stat.execute("CREATE TABLE Locations (locName CHAR(12),locNumber INTEGER,address CHAR(50))");
	         stat.execute("INSERT INTO Locations VALUES ('PNS',1,'Pensacola Florida')");
	         stat.execute("INSERT INTO Locations VALUES ('CLT',2,'Charlotte North Carolina')");
	         stat.execute("INSERT INTO Locations VALUES ('DFW',3,'Dallas Fort Worth Texas')");
	         ResultSet result = stat.executeQuery("SELECT * FROM Locations");
	         return result;
	  }

	  public ResultSet createInventory() throws Exception
	  {
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
	  
	  public void printResultSet(ResultSet resultSet) throws SQLException {
		    ResultSetMetaData metaData = resultSet.getMetaData();
		    int columnCount = metaData.getColumnCount();

		    // Print column names
		    for (int i = 1; i <= columnCount; i++) {
		        System.out.print(metaData.getColumnName(i) + "\t");
		    }
		    System.out.println();

		    // Print rows
		    while (resultSet.next()) {
		        for (int i = 1; i <= columnCount; i++) {
		            System.out.print(resultSet.getString(i) + "\t");
		        }
		        System.out.println();
		    }
		}
	  
	  public void printInstruments() throws Exception {
		    ResultSet resultSet = createInstruments();
		    printResultSet(resultSet);
		}

		public void printLocations() throws Exception {
		    ResultSet resultSet = createLocations();
		    printResultSet(resultSet);
		}

		public void printInventory() throws Exception {
		    ResultSet resultSet = createInventory();
		    printResultSet(resultSet);
		}

	
	// Main sets a few String variable names and then calls functions
	public static void main(String[] args) throws Exception {
		TestDB testDB = new TestDB();
//		testDB.createInstruments();
//		testDB.createLocations();
//		testDB.createInventory();
		testDB.printInstruments();
	    testDB.printLocations();
	    testDB.printInventory();
	}
}
