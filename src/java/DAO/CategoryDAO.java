package DAO;

import java.util.List;
import model.Category;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.math.BigInteger;
import java.security.MessageDigest;
import model.User;

public class CategoryDAO extends DBContext{
    
    //getAllList
    public List<Category> getAll() throws SQLException{
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM Categories";
        
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Category c = new Category();
                c.setCategoryID(rs.getInt("categoryID"));
                c.setCategoryName(rs.getString("categoryName"));
                c.setDescription(rs.getString("description"));
                
                list.add(c);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    //insertCategory
    public boolean insertCategory(String categoryName, String description){
        String sql = "INSERT INTO Categories (categoryName, description) VALUES (?, ?)";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            
            ps.setString(1, categoryName);
            ps.setString(2, description);
            
            int check = ps.executeUpdate();
            if(check>0){
                return true;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    //deleteCategory
    public boolean deleteCategory(int categoryID){
        String sql = "DELETE FROM Categories WHERE categoryID = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);            
            ps.setInt(1, categoryID);
            int check = ps.executeUpdate();
            if(check>0){
                return true;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    //updateCategory
    public boolean updateCategory(int categoryID, String categoryName, String description){
        String sql = "UPDATE Categories SET categoryName = ?, description = ? where categoryID = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, categoryName);
            ps.setString(2, description);
            ps.setInt(3, categoryID);
            int check = ps.executeUpdate();
            
            if(check>0){
                return true;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    //getByID
    public Category getByID(int categoryID){
        String sql = "SELECT * FROM Categories WHERE categoryID = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
ps.setInt(1, categoryID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Category(
                        rs.getInt("categoryID"),
                        rs.getString("categoryName"),
                        rs.getString("description")                       
                );
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
}