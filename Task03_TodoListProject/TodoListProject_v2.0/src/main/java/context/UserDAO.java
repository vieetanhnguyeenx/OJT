package context;

import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    public static User getUser(String username, String password) {
        try {
            String query = "select [ID], [Username], [Password] \n" +
                    "from [User] \n" +
                    "where Username = ? and Password = ?";
            Connection conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1,username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("ID"));
                user.setName(rs.getString("Username"));
                user.setPassword(rs.getString("Password"));
                return user;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;

    }

    public static void main(String[] args) {
        User user = UserDAO.getUser("anhnv", "123");
        if (user != null) {
            System.out.println(user.getId());
        } else {
            System.out.println("nah");
        }

    }
}
