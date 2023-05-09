package context;

import Model.Todo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TodoDAO {

    public static List<Todo> getTodoList() {
        List<Todo> todoList = new ArrayList<>();
        String query = "SELECT  [ID]\n" +
                "      ,[Title]\n" +
                "      ,[Status]\n" +
                "      ,[CreatedDate]\n" +
                "      ,[UserID]\n" +
                "  FROM [Task03_TodoList].[dbo].[Todo]";

        try {
            Connection conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
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

    public static void main(String[] args) {
        TodoDAO.changeStatus(1);
    }
}
