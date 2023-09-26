import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DataBaseController {
	public Connection conn;
	public Statement stat;
	public FileOutputStream ofs;
	public FileOutputStream ofsSql;
	public Scanner ifs;
	public File file;
	public File outputFile;
	public File outputFileSQL;
	
	public DataBaseController() {
		conn = null;
		stat = null;
		
		file = new File("Vehicles.csv");
		outputFileSQL = new File("SQLCommandLog.txt");
		try {
			ofsSql = new FileOutputStream(outputFileSQL);
		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}
		
		try {
			ifs = new Scanner(file);
		} 
		catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}
	}
	
	public Connection Connect() {
		try {
			System.out.println("Establishing Connection...");
			SimpleDataSource.init("database.properties");
			conn = SimpleDataSource.getConnection();
		    stat = conn.createStatement();  
		} 
		catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return conn;
	}
	
	public void DropTable() {
		try {  
			System.out.println("Dropping Table...");
			stat.execute("DROP TABLE Test2"); 
		}
		catch (Exception e) { 
			System.out.println("\nDrop Failed\n"); 
		} 
	}
	
	public void CreateTable(String input) {
		System.out.println("Creating Table...");
		try {
			stat.execute(input);
		} 
		catch (SQLException e) {
			System.out.println(e.toString());
		}
		
		LogSQLCommand(input);
	}
	
	public void ExecuteSQLCommand(String command) {
		try {
			stat.execute(command);
		} 
		catch (SQLException e) {
			System.out.println(e.toString());
		}
		
		LogSQLCommand(command);
	}
	
	public String Query(String command) throws SQLException {
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
		
		LogSQLCommand(command);
		return query;
	}
	
	public String Query(String command, ResultSet result) throws SQLException {
		ResultSetMetaData rsm = result.getMetaData();
		int col = rsm.getColumnCount();
		String query = "";
		
		while(result.next()) {
			for (int i = 1; i <= col; i++) {
				query += result.getString(i) + " ";
			}
			query += "\n";
		}
		
		LogSQLCommand(command);
		return query;
	}
	
	public void WriteToCsv(String input) {
		try {
			ofs = new FileOutputStream(file);
		} 
		catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}
		
		for (int i = 0; i < input.length(); i++) {
			try {
				ofs.write(input.charAt(i));
			} 
			catch (IOException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	public String GetCsv() {
		try {
			ifs = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}
		
		String resultString = "";
		
		while(ifs.hasNext()) {
			resultString += ifs.nextLine() + '\n';
		}
		
		return resultString;
	}
	
	public void InputValuesFromCsv(String csvData[]) {
		String token[];
		String insertResult = "";
		
		for (int i = 0; i < csvData.length; i++) {
			token = csvData[i].split(",");
			insertResult = "INSERT INTO Test2 VALUES (" + "\'" + token[0] + "\',"
			+ token[1] + ","
			+ token[2] + ","
			+ token[3] + ")";
			ExecuteSQLCommand(insertResult);
		}
	}
	
	public String PrintLogFile() {
		try {
			ifs = new Scanner(outputFileSQL);
		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}
		
		String resultString = "";
		
		while(ifs.hasNext()) {
			resultString += ifs.nextLine() + '\n';
		}
		
		return resultString;
	}
	
	private void LogSQLCommand(String command) {		
		for (int i = 0; i < command.length(); i++) {
			try {
				ofsSql.write(command.charAt(i));
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
		
		try {
			ofsSql.write('\n');
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}
}
