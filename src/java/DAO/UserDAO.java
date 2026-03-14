package DAO;

import java.sql.*;
import java.util.*;
import model.User;

public class UserDAO extends DBContext {

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(extractUser(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE username=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return extractUser(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isUsernameExist(String username) {
        String sql = "SELECT 1 FROM Users WHERE username=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean register(User u) {
        String sql = "INSERT INTO Users(username,password,email,role) VALUES (?,?,?,?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, u.getUsername());
            st.setString(2, u.getPassword());
            st.setString(3, u.getEmail());
            st.setString(4, "user");
            return st.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public User login(String username, String password) {
        String sql = "SELECT * FROM Users WHERE username=? AND password=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return new User(
                               rs.getInt("userID"),
                               rs.getString("username"),
                               rs.getString("password"),
                               rs.getString("email"),
                               rs.getString("role"),
                               rs.getString("avatar")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private User extractUser(ResultSet rs) throws SQLException {
        return new User(
                       rs.getInt("userID"),
                       rs.getString("username"),
                       rs.getString("password"),
                       rs.getString("email"),
                       rs.getString("role"),
                       rs.getString("avatar")
        );
    }

    public boolean checkUser(String username) {
        String sql = "SELECT * FROM Users WHERE username = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean register(String username, String password, String email) {
        String sql = "INSERT INTO Users(username, password, email) VALUES(?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, email);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public User checkUserEmail(String username, String email) {
        String sql = "SELECT * FROM Users WHERE username=? AND email=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                               rs.getInt("userID"),
                               rs.getString("username"),
                               rs.getString("password"),
                               rs.getString("email"),
                               rs.getString("role"),
                               rs.getString("avatar")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updatePassword(String username, String newPass) {
        String sql = "UPDATE Users SET password=? WHERE username=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, newPass);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(User u) {
        String sql = "UPDATE Users SET username=?, password=?, email=?, avatar=? WHERE userID=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, u.getUsername());
            st.setString(2, u.getPassword());
            st.setString(3, u.getEmail());
            st.setString(4, u.getAvatar());
            st.setInt(5, u.getUserID());
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //findUsertoResetpass
    public User find(String username, String email) {
        String sql = "SELECT * FROM Users WHERE username = ? AND email = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                               rs.getInt("userID"),
                               rs.getString("username"),
                               rs.getString("password"),
                               rs.getString("email"),
                               rs.getString("role"),
                               rs.getString("avatar")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //resetpassword
    public boolean reset(int userID, String newpass) {
        String sql = "UPDATE Users SET password = ? where userID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, newpass);
            ps.setInt(2, userID);

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
