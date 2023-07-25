package context;

import model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import helper.TokenHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class UserDAO {
    private static final Base64.Decoder decoder = Base64.getDecoder();

    public static User getUser(String username, String password) {
        String query = "SELECT ID, UserName from task03_httpserver.user where UserName = ? and Password = ?";
        try {
            Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("ID"));
                user.setName(rs.getString("UserName"));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static User authenticationToken(String token) throws JsonProcessingException {
        User u = TokenHelper.tokenToUser(token);
        if (u == null) {
            return null;
        }

        try {
            String query = "SELECT ID, UserName from task03_httpserver.user where UserName = ? and ID = ?";
            Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, u.getName());
            ps.setInt(2, u.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User authenticatedUser = new User();
                authenticatedUser.setId(rs.getInt("ID"));
                authenticatedUser.setName(rs.getString("UserName"));
                return authenticatedUser;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void main(String[] args) {
        User u = null;
        try {
            u = UserDAO.authenticationToken("eyJpZCI6MSwibmFtZSI6ImFuaG52IiwicGFzc3dvcmQiOm51bGx9");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if (u != null) {
            System.out.println(u);
        } else {
            System.out.println("null mtfk");
        }
    }
}
