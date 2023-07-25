package context;

import java.sql.*;

public class DBContext {

    public static Connection getConnection() {
        final String url = "jdbc:mysql://localhost:3306/";
        final String dbName = "task03_httpserver";
        final String username = "root";

        final String password = "anhnv";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url + dbName, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Connection conn = DBContext.getConnection();
        String query = "SELECT * FROM task03_httpserver.task;";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt("UserID"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
