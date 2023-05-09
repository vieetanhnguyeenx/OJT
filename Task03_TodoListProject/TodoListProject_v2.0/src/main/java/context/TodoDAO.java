package context;

import Model.Todo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TodoDAO {

    public static List<Todo> getTodoList(int userId) {
        List<Todo> todoList = new ArrayList<>();
        String query = "SELECT [ID]\n" +
                "      ,[Title]\n" +
                "      ,[Status]\n" +
                "      ,[CreatedDate]\n" +
                "      ,[UserID]\n" +
                "  FROM [dbo].[Todo]\n" +
                "  WHERE UserID = ?";

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

    public static void changeStatus(int id) {
        String query2 = "select [Status] from Todo\n" +
                "where ID = ?";
        boolean status = true;
        try {
            Connection conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(query2);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                status = !rs.getBoolean("Status");
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String query1 = "UPDATE [dbo].[Todo]\n" +
                "   SET [Status] = ?\n" +
                " WHERE ID = ?";
        try {
            Connection conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(query1);
            ps.setBoolean(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();

            ps.close();
            conn.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public static void deleteItem(int id) {
        String query = "DELETE FROM [dbo].[Todo]\n" +
                "      WHERE ID = ?";

        try {
            Connection conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();

            ps.close();
            conn.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateTitle(int id, String newTitle) {
        String query = "UPDATE [dbo].[Todo]\n" +
                "   SET [Title] = ?\n" +
                " WHERE ID = ?";

        try {
            Connection conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, newTitle);
            ps.setInt(2, id);
            ps.executeUpdate();

            ps.close();
            conn.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void addNewTodo(String title, int userId) {
        String query = "INSERT INTO [dbo].[Todo]\n" +
                "           ([Title]\n" +
                "           ,[Status]\n" +
                "           ,[CreatedDate]\n" +
                "           ,[UserID])\n" +
                "     VALUES\n" +
                "           (?\n" +
                "           ,?\n" +
                "           ,?\n" +
                "           ,?)";

        try {
            Connection conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, title);
            ps.setBoolean(2, false);
            ps.setDate(3, Date.valueOf(LocalDate.now()));
            ps.setInt(4, userId);
            ps.executeUpdate();

            ps.close();
            conn.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        TodoDAO.updateTitle(7, "cuocdoi");
    }
}
