import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.lang.reflect.*;

/**
   Tests a database installation by creating and querying
   a sample table.
*/
public class TestDB {
	
   public static void main(String[] args) throws Exception {  
	  Class vehicle = Class.forName("Vehicle");
	  
	  System.out.println("Starting...");
      DataBaseController dbc = new DataBaseController();
      
      // Create a connection to the SQL server. 
      dbc.Connect();
      
      // Drop the table if it's pre-existing.
      dbc.DropTable();
      
      // Write to CSV File
      dbc.WriteToCsv("Chevy,1500,7829.20,500\r\n"
      		+ "Ford,2000,2000.87,600\r\n"
      		+ "Toyota,2500,3210.94,700\r\n"
      		+ "Nissan,1999,6000.2,200\r\n"
      		+ "Hyundai,2499,999999,450\r\n"
      		+ "Chevy,3999,5000.43,375\r\n"
      		+ "Ford,1750,1500,290\r\n"
      		+ "Toyota,2250,800.5,150\r\n"
      		+ "Nissan,3250,350.43,475\r\n"
      		+ "Hyundai,1900,643.49,600");
      
      // Obtain metadata from "vehicle class" using reflection
      Field f[] = vehicle.getDeclaredFields();
      
      // Create table for each field
      String tableResult = "CREATE TABLE Test2 (";
      String token[];
      
      // Parsing metadata into a string; SQL command which will create the database
      for (int i = 0; i < f.length; i++) {
    	  token = f[i].toString().split("Vehicle.");
    	  
    	  if (f[i].toString().contains("String")) {
    		  tableResult += token[1] + " VARCHAR(20), ";
    	  } else if (f[i].toString().contains("int")) {
    		  tableResult += token[1] + " INTEGER, ";
    	  } else if (f[i].toString().contains("double")) {
    		  tableResult += token[1] + " FLOAT, ";
    	  }
      }
      
      // Deleting the last comma and ending with a parentheses
      tableResult = tableResult.substring(0, tableResult.length() - 2);
      tableResult += ")";
      
      // Create Table
      dbc.CreateTable(tableResult);
      
      // Input CVS Data into Database
      dbc.InputValuesFromCsv(dbc.GetCsv().split("\n"));
      
      // Query the data      
      System.out.println("\n~~ Result Set 1 - Entire Database ~~");
      System.out.println(dbc.Query("SELECT * FROM Test2"));
      
      System.out.println("~~ Result Set 2 - Only chevy and toyotas ~~");
      System.out.println(dbc.Query("SELECT * FROM Test2 WHERE (make = \'Chevy\') OR (make = \'Toyota\')"));
      
      System.out.println("~~ Result Set 3 - All vehicles that weigh more than 2500lbs ~~");
      System.out.println(dbc.Query("SELECT * FROM Test2 WHERE weight >= 2500"));

   }
}
