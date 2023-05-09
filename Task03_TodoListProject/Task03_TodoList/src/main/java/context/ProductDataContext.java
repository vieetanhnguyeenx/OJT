package context;

import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDataContext {
    public List<Product> getAllProduct() {
        List<Product> productList = new ArrayList<>();
        try {
            String query = "SELECT TOP (10) [product_id]\n" +
                    "      ,[name]\n" +
                    "      ,[type]\n" +
                    "      ,[os]\n" +
                    "  FROM [SWP].[dbo].[Product]";
            Connection conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("product_id"));
                p.setName(rs.getString("name"));
                p.setType(rs.getBoolean("type"));
                p.setOs(rs.getString("os"));
                productList.add(p);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return productList;
    }

    public static void main(String[] args) {
        List<Product> productList = new ProductDataContext().getAllProduct();
        for (Product p: productList) {
            System.out.println(p);
        }
    }
}
