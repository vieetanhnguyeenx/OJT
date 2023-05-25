package context;

import jdk.dynalink.beans.StaticClass;
import model.Todo;
import model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TodoDAO {
    private static Connection conn = DBContext.getConnection();
    public static List<Todo> getTodoList(int userId) {
        List<Todo> todoList = new ArrayList<>();
        String query = "SELECT ID, Title, Status, CreatedDate, UserID FROM task03_httpserver.task where UserID = ?;";

        try {
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
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, taskId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                status = rs.getBoolean("Status");
            }

            if (status != null) {
                ps = conn.prepareStatement(query2);
                ps.setBoolean( 1 ,!status);
                ps.setInt(2, taskId);
                ps.executeUpdate();
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Todo changStatusAndReturn(int userId, int taskId) {
        Todo todo = null;
        String query = "SELECT ID, Title, Status, CreatedDate, UserID FROM task03_httpserver.task where ID = ? and UserID = ?;";
        String query2 = "UPDATE `task03_httpserver`.`task`\n" +
                "SET\n" +
                "`Status` = ?\n" +
                "WHERE `ID` = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, taskId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                todo = new Todo();
                todo.setId(taskId);
                todo.setStatus(rs.getBoolean("Status"));
                todo.setTitle(rs.getString("Title"));
                todo.setCreatedDate(rs.getDate("CreatedDate").toLocalDate());
            }

            if (todo != null) {
                ps = conn.prepareStatement(query2);
                ps.setBoolean( 1 ,!todo.isStatus());
                ps.setInt(2, taskId);
                ps.executeUpdate();
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return todo;
    }

    public static void updateTitle(int userId, int taskId, String title) {
        String query = "UPDATE `task03_httpserver`.`task`\n" +
                "SET\n" +
                "`Title` = ?\n" +
                "WHERE `ID` = ? and `UserID` = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, title);
            ps.setInt(2, taskId);
            ps.setInt(3, userId);
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Todo updateTitleAndReturn(int userId, int taskId, String title) {
        Todo todo = null;
        String query = "UPDATE `task03_httpserver`.`task`\n" +
                "SET\n" +
                "`Title` = ?\n" +
                "WHERE `ID` = ? and `UserID` = ?;";
        String query2 = "SELECT ID, Title, Status, CreatedDate, UserID FROM task03_httpserver.task where ID = ? and UserID = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, title);
            ps.setInt(2, taskId);
            ps.setInt(3, userId);
            ps.executeUpdate();

            PreparedStatement ps1 = conn.prepareStatement(query2);
            ps1.setInt(1, taskId);
            ps1.setInt(2, userId);
            ResultSet rs = ps1.executeQuery();

            while (rs.next()) {
                todo = new Todo();
                todo.setId(taskId);
                todo.setTitle(rs.getString("Title"));
                todo.setStatus(rs.getBoolean("Status"));
                todo.setCreatedDate(rs.getDate("CreatedDate").toLocalDate());
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todo;
    }

    public static void deleteTask(int userId, int taskId) {
        String query = "DELETE FROM `task03_httpserver`.`task`\n" +
                "WHERE `ID` = ? and `UserID` = ?;";
        try {
            Long t = System.currentTimeMillis();
//            Connection conn = DBContext.getConnection();
            Long t2 = System.currentTimeMillis();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, taskId);
            ps.setInt(2, userId);
            ps.executeUpdate();
            Long t3 = System.currentTimeMillis();
            ps.close();
            System.out.println("Delete task " + taskId);
            System.out.println(t2 - t);
            System.out.println(t3 - t2);
        } catch (SQLException e) {
            e.printStackTrace();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Todo addNewTaskAndReturn(int userId, String title) {
        Todo todo = null;
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
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, title);
            ps.setBoolean(2, false);
            ps.setDate(3, Date.valueOf(LocalDate.now()));
            ps.setInt(4, userId);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                todo = new Todo();
                todo.setId(rs.getInt(1));
                todo.setStatus(false);
                todo.setTitle(title);
                todo.setCreatedDate(LocalDate.now());
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todo;
    }

    public static void main(String[] args) {
        Todo todo = TodoDAO.addNewTaskAndReturn(1, "doiden");
        if (todo != null) {
            System.out.println(todo);
        } else {
            System.out.println("null");
        }
    }
}
