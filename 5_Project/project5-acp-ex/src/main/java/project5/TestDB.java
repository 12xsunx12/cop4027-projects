package project5;

import java.sql.*;

public class TestDB {
    private Connection conn;
    private Statement stat;

    public TestDB() throws Exception {
        SimpleDataSource.init("database.properties");
        this.conn = SimpleDataSource.getConnection();
        this.stat = conn.createStatement();
        System.out.println("DB connection initialized");
    }

    public void createTables() throws Exception {
    	dropAllTables();
        createTableIfNotExists("Instruments", "CREATE TABLE Instruments (instName CHAR(12),instNumber INTEGER,cost DOUBLE,descrip CHAR(20))");
        createTableIfNotExists("Locations", "CREATE TABLE Locations (locName CHAR(12),locNumber INTEGER,address CHAR(50))");
        createTableIfNotExists("Inventory", "CREATE TABLE Inventory (iNumber INTEGER,lNumber INTEGER,quantity INTEGER)");

        initializeInstruments();
        initializeLocations();
        initializeInventory();
    }
    
    public void dropAllTables() throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet resultSet = metaData.getTables(null, null, null, new String[]{"TABLE"});

        while (resultSet.next()) {
            String tableName = resultSet.getString("TABLE_NAME");
            stat.execute("DROP TABLE " + tableName);
            System.out.println("Dropped table: " + tableName);
        }
        System.out.println("All tables dropped successfully.");
    }

    
    private void createTableIfNotExists(String tableName, String createTableSQL) throws SQLException {
        ResultSet tables = conn.getMetaData().getTables(null, null, tableName, null);
        if (!tables.next()) {
            stat.execute(createTableSQL);
        }
    }

    private void initializeInstruments() throws SQLException {
        stat.execute("INSERT INTO Instruments VALUES ('guitar',1,100.0,'yamaha')");
        stat.execute("INSERT INTO Instruments VALUES ('guitar',2,500.0,'gibson')");
        // ... other initial data
    }

    private void initializeLocations() throws SQLException {
        stat.execute("INSERT INTO Locations VALUES ('PNS',1,'Pensacola Florida')");
        stat.execute("INSERT INTO Locations VALUES ('CLT',2,'Charlotte North Carolina')");
        // ... other initial data
    }

    private void initializeInventory() throws SQLException {
        stat.execute("INSERT INTO Inventory VALUES (1,1,15)");
        stat.execute("INSERT INTO Inventory VALUES (1,2,27)");
        // ... other initial data
    }

    private void dropTableIfExists(String tableName) throws SQLException {
        ResultSet tables = conn.getMetaData().getTables(null, null, tableName, null);
        if (tables.next()) {
            stat.execute("DROP TABLE " + tableName);
        }
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

    public void printInstruments() throws SQLException {
        ResultSet resultSet = stat.executeQuery("SELECT * FROM Instruments");
        printResultSet(resultSet);
    }

    public void printLocations() throws SQLException {
        ResultSet resultSet = stat.executeQuery("SELECT * FROM Locations");
        printResultSet(resultSet);
    }

    public void printInventory() throws SQLException {
        ResultSet resultSet = stat.executeQuery("SELECT * FROM Inventory");
        printResultSet(resultSet);
    }

    public static void main(String[] args) throws Exception {
        TestDB testDB = new TestDB();
        testDB.createTables();
        testDB.printInstruments();
        testDB.printLocations();
        testDB.printInventory();
    }
}
