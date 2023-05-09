package context;
import java.sql.*;
public class DBContext {
    public Connection getConnection() throws Exception {
        String url = "jdbc:sqlserver://" + serverName + ":" + portNumber + "\\" + intance + ";databaseName=" + dbName + ";encrypt=false" ;
        if (intance == null || intance.trim().isEmpty()) {
            url = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + dbName + ";encrypt=false";
        }
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url, userID, password);

        //jdbc:sqlserver://localhost\SQLEXPRESS01:1433;databaseName=master [sa on db_accessadmin]
        //jdbc:sqlserver://localhost\SQLEXPRESS01:1433;databaseName=Student_CV [sa on Default schema]
    }

    // You can customize the connection again by editing these properties
    private final String serverName = "localhost\\SQLEXPRESS01";
    private final String dbName = "SWP";
    private final String portNumber = "1433";
    private final String intance = "";
    private final String userID = "sa";
    private final String password = "sa";

    // run main to test the connection
    public static void main(String[] args) {
        System.out.println("hello");
        try {
            DBContext d = new DBContext();
            if (d.getConnection() != null) {
                System.out.println("Successful connection to database");
            } else {
                System.out.println("Failed connection to database");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("hi");
    }
}
