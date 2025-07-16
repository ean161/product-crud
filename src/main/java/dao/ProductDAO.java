package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Category;
import model.Product;
import utils.DBContext;

/**
 *
 * @author CE190990 - Nguyen Hoai An
 */
public class ProductDAO extends DBContext {
    public List<Product> getAll() {
        List<Product> list = new ArrayList<>();
        
        try {
            PreparedStatement sqlQuery = db.prepareStatement("SELECT * FROM product INNER JOIN category ON product.cat_id = category.cat_id;");
            ResultSet sqlRes = sqlQuery.executeQuery();
            
            while (sqlRes.next()) {
                //int pro_id, String pro_name, String pro_description, int pro_quantity, long pro_price, Category cat) {
                list.add(
                    new Product(
                        sqlRes.getInt("pro_id"),
                        sqlRes.getString("pro_name"),
                        sqlRes.getString("pro_description"),
                        sqlRes.getInt("pro_quantity"),
                        sqlRes.getLong("pro_price"),
                        //int cat_id, String cat_name, String cat_description
                        new Category(
                            sqlRes.getInt("cat_id"),
                            sqlRes.getString("cat_name"),
                            sqlRes.getString("cat_description")
                        )
                    )
                );
            }
        } catch (Exception ex) {}
        
        return list;
    }
    
    public boolean isValid(int id) {
        try {
            PreparedStatement sqlQuery = db.prepareStatement("SELECT * FROM product INNER JOIN category ON product.cat_id = category.cat_id WHERE product.pro_id = ?");
            sqlQuery.setInt(1, id);
            ResultSet sqlRes = sqlQuery.executeQuery();
            
            //int pro_id, String pro_name, String pro_description, int pro_quantity, long pro_price, Category cat
            if (sqlRes.next())
                return true;
        } catch (Exception ex) {}
        
        return false;
    }
    
    public Product get(int id) {
        try {
            PreparedStatement sqlQuery = db.prepareStatement("SELECT * FROM product INNER JOIN category ON product.cat_id = category.cat_id WHERE product.pro_id = ?");
            sqlQuery.setInt(1, id);
            ResultSet sqlRes = sqlQuery.executeQuery();
            
            //int pro_id, String pro_name, String pro_description, int pro_quantity, long pro_price, Category cat
            if (sqlRes.next())
                return new Product(
                    sqlRes.getInt("pro_id"),
                    sqlRes.getString("pro_name"),
                    sqlRes.getString("pro_description"),
                    sqlRes.getInt("pro_quantity"),
                    sqlRes.getLong("pro_price"),
                    new Category(
                        sqlRes.getInt("cat_id"),
                        sqlRes.getString("cat_name"),
                        sqlRes.getString("cat_description")
                    )
                );
        } catch (Exception ex) {}
        
        return new Product();
    }
    
    public void create(String name, long price, int quantity, String desc, int cat) {
        try {
            PreparedStatement sqlQuery = db.prepareStatement("INSERT INTO product (pro_name, pro_price, pro_quantity, pro_description, cat_id) VALUES (?, ?, ?, ?, ?)");
            sqlQuery.setString(1, name);
            sqlQuery.setLong(2, price);
            sqlQuery.setInt(3, quantity);
            sqlQuery.setString(4, desc);
            sqlQuery.setInt(5, cat);
                        
            sqlQuery.executeUpdate();
        } catch (Exception ex) {}
    }
    
    public void update(int id, String name, long price, int quantity, String desc, int cat) {
        try {
            PreparedStatement sqlQuery = db.prepareStatement("UPDATE product SET pro_name = ?, pro_price = ?, pro_quantity = ?, pro_description = ?, cat_id = ? WHERE pro_id = ?");
            sqlQuery.setString(1, name);
            sqlQuery.setLong(2, price);
            sqlQuery.setInt(3, quantity);
            sqlQuery.setString(4, desc);
            sqlQuery.setInt(5, cat);
            sqlQuery.setInt(6, id);
                        
            sqlQuery.executeUpdate();
        } catch (Exception ex) {}
    }
    
    public void delete(int id) {
        try {
            PreparedStatement sqlQuery = db.prepareStatement("DELETE product WHERE pro_id = ?");
            sqlQuery.setInt(1, id);
                        
            sqlQuery.executeUpdate();
        } catch (Exception ex) {}
    }
}
