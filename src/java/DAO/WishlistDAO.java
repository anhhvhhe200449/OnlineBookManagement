package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Book;

public class WishlistDAO extends DBContext {

    public void addWishlist(int userID, int bookID) {

        String sql = "INSERT INTO Wishlist(userID, bookID) VALUES (?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, userID);
            ps.setInt(2, bookID);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Book> getWishlistByUser(int userID) {

        List<Book> list = new ArrayList<>();

        String sql = """
        SELECT b.*
        FROM Wishlist w
        JOIN Books b ON w.bookID = b.bookID
        WHERE w.userID = ?
    """;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userID);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Book b = new Book();

                b.setBookID(rs.getInt("bookID"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setCoverImage(rs.getString("coverImage"));
                b.setViews(rs.getInt("views"));

                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void removeWishlist(int userID, int bookID) {

        String sql = "DELETE FROM Wishlist WHERE userID = ? AND bookID = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, userID);
            ps.setInt(2, bookID);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
