package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;
import utils.DBContext;
import utils.Lib;

/**
 *
 * @author CE190990 - Nguyen Hoai An
 */
public class UserDAO extends DBContext {
    public User login(String username, String password) {
        try {
            PreparedStatement sqlQuery = db.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            sqlQuery.setString(1, username);
            sqlQuery.setString(2, password);
            ResultSet sqlRes = sqlQuery.executeQuery();
            
            if (sqlRes.next())
                return new User(
                    sqlRes.getInt("id"),
                    sqlRes.getInt("role"),
                    sqlRes.getString("username"),
                    sqlRes.getString("password")
                );
        } catch (Exception ex) {}
        
        return new User();
    }
}
