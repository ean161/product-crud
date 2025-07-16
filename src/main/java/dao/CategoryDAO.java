package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Category;
import utils.DBContext;

/**
 *
 * @author CE190990 - Nguyen Hoai An
 */
public class CategoryDAO extends DBContext {
    public CategoryDAO() {}
    
    public List<Category> getAll() {
        List<Category> list = new ArrayList<>();
        
        try {
            PreparedStatement sqlQuery = db.prepareStatement("SELECT * FROM category");
            ResultSet sqlRes = sqlQuery.executeQuery();
            
            while (sqlRes.next()) {
                //int id, String name, String desc
                list.add(
                    new Category(
                        sqlRes.getInt("cat_id"),
                        sqlRes.getString("cat_name"),
                        sqlRes.getString("cat_description")
                    )
                );
            }
        } catch (Exception ex) {}
        
        return list;
    }
}
