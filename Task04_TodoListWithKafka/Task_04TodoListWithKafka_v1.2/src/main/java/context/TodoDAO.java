package context;

import model.Todo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TodoDAO {
    public static List<Todo> getTodoList(int userId) {
        List<Todo> todoList = new ArrayList<>();
        String query = "SELECT ID, Title, Status, CreatedDate, UserID FROM task03_httpserver.task where UserID = ?;";

        try {
            Connection conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Todo todo = new Todo();
                todo.setId(rs.getInt("ID"));
                todo.setTitle(rs.getString("Title"));
                todo.setStatus(rs.getBoolean("Status"));
                todo.setCreatedDate(rs.getDate("CreatedDate").toLocalDate());
                todoList.add(todo);
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return todoList;

    }

    public static void changStatus(int userId, int taskId) {
        Boolean status = null;
        String query = "SELECT Status FROM task03_httpserver.task \n" +
                "where id = ? and UserID = ?;";
        String query2 = "UPDATE `task03_httpserver`.`task`\n" +
                "SET\n" +
                "`Status` = ?\n" +
                "WHERE `ID` = ?;";
        try {
            Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, taskId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                status = rs.getBoolean("Status");
                continue;
            }

            if (status != null) {
                ps = conn.prepareStatement(query2);
                ps.setBoolean(1, !status);
                ps.setInt(2, taskId);
                ps.executeUpdate();
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateTitle(int userId, int taskId, String title) {
        String query = "UPDATE `task03_httpserver`.`task`\n" +
                "SET\n" +
                "`Title` = ?\n" +
                "WHERE `ID` = ? and `UserID` = ?;";
        try {
            Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, title);
            ps.setInt(2, taskId);
            ps.setInt(3, userId);
            ps.executeUpdate();

            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteTask(int userId, int taskId) {
        String query = "DELETE FROM `task03_httpserver`.`task`\n" +
                "WHERE `ID` = ? and `UserID` = ?;";
        try {
            Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, taskId);
            ps.setInt(2, userId);
            ps.executeUpdate();

            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addNewTask(int userId, String title) {
        String query = "INSERT INTO `task03_httpserver`.`task`\n" +
                "(`Title`,\n" +
                "`Status`,\n" +
                "`CreatedDate`,\n" +
                "`UserID`)\n" +
                "VALUES\n" +
                "(?,\n" +
                "?,\n" +
                "?,\n" +
                "?);";
        try {
            Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, title);
            ps.setBoolean(2, false);
            ps.setDate(3, Date.valueOf(LocalDate.now()));
            ps.setInt(4, userId);
            ps.executeUpdate();

            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        changStatus(1, 2);
    }
}
