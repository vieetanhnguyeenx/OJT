package context;

import com.fasterxml.jackson.core.JsonProcessingException;
import helper.TokenHelper;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class UserDAO {
    private static final Base64.Decoder decoder = Base64.getDecoder();
    private static Connection conn = DBContext.getConnection();
    public static User getUser(String username, String password) {
        User user = null;
        String query = "SELECT ID, UserName from task03_httpserver.user where UserName = ? and Password = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("ID"));
                user.setName(rs.getString("UserName"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public static User authenticationToken(String token) throws JsonProcessingException {
        Long t = System.currentTimeMillis();
        User authenticatedUser = null;
        User u = TokenHelper.tokenToUser(token);
        if (u == null) {
            return null;
        }

        try {
            String query = "SELECT ID, UserName from task03_httpserver.user where UserName = ? and ID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, u.getName());
            ps.setInt(2, u.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                authenticatedUser = new User();
                authenticatedUser.setId(rs.getInt("ID"));
                authenticatedUser.setName(rs.getString("UserName"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Long t1 = System.currentTimeMillis();
        System.out.println(t1 - t);
        return authenticatedUser;
    }

    public static void main(String[] args) {
        try {
            User u = authenticationToken("eyJpZCI6MSwibmFtZSI6ImFuaG52IiwicGFzc3dvcmQiOm51bGx9");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
