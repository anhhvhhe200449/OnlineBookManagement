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
import model.Book;
import utils.StringSimilarity;

public class BookDAO extends DBContext {

    //get book by category
    public List<Book> getBookByCategory(int categoryID) {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM Books WHERE categoryID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, categoryID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book b = new Book(
                               rs.getInt("bookID"),
                               rs.getString("title"),
                               rs.getString("description"),
                               rs.getString("coverImage"),
                               rs.getInt("categoryID"),
                               rs.getString("author"),
                               rs.getInt("views"),
                               rs.getString("status"),
                               rs.getTimestamp("createdAt")
                );
                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Book getBookByID(int bookID) {
        String sql = "SELECT * FROM Books WHERE bookID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, bookID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Book(
                               rs.getInt("bookID"),
                               rs.getString("title"),
                               rs.getString("description"),
                               rs.getString("coverImage"),
                               rs.getInt("categoryID"),
                               rs.getString("author"),
                               rs.getInt("views"),
                               rs.getString("status"),
                               rs.getTimestamp("createdAt")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //getAll
    public List<Book> getAll() {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM Books";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book c = new Book();
                c.setBookID(rs.getInt("bookID"));
                c.setTitle(rs.getString("title"));
                c.setDescription(rs.getString("description"));
                c.setCoverImage(rs.getString("coverImage"));
                c.setCategoryID(rs.getInt("categoryID"));
                c.setAuthor(rs.getString("author"));
                c.setStatus(rs.getString("status"));
                c.setCreatedAt(rs.getTimestamp("createdAt"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //check book exist
    public boolean checkBook(String title) throws SQLException {
        String sql = "SELECT title FROM Books WHERE title = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, title);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return true;
        }
        return false;
    }

    //insert book
    public boolean insertBook(String title, String description, String coverImage, int categoryID, String author) throws SQLException {
        if (checkBook(title)) {
            return false;
        }
        String sql = "INSERT INTO Books(title, description, coverImage, categoryID, author) VALUES(?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, coverImage);
            ps.setInt(4, categoryID);
            ps.setString(5, author);

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //delete book
    public boolean deleteBook(int bookID) {
        String sql = "DELETE FROM Books WHERE bookID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, bookID);
            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //update book
    public boolean updateBook(String title, String description, String coverImage, String author, int bookID) {
        String sql = "UPDATE Books SET title = ?, description = ?, coverImage = ?, author = ? where bookID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, coverImage);
            ps.setString(4, author);
            ps.setInt(5, bookID);
            int check = ps.executeUpdate();

            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //count book in category
    public int countBookByCategory(int categoryID) {
        String sql = "SELECT COUNT(*) FROM Books WHERE categoryID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, categoryID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public List<Book> getTopBooksPaging(int offset, int pageSize) {

        List<Book> list = new ArrayList<>();

        String sql = """
        SELECT * 
        FROM (
            SELECT TOP 10 * 
            FROM Books
            ORDER BY views DESC
        ) AS TopBooks
        ORDER BY views DESC
        OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
        """;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, offset);
            ps.setInt(2, pageSize);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Book b = new Book(
                               rs.getInt("bookID"),
                               rs.getString("title"),
                               rs.getString("description"),
                               rs.getString("coverImage"),
                               rs.getInt("categoryID"),
                               rs.getString("author"),
                               rs.getInt("views"),
                               rs.getString("status"),
                               rs.getTimestamp("createdAt")
                );

                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int getTotalBooks() {

        String sql = "SELECT COUNT(*) FROM Books";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public void increaseView(int bookID) {

        String sql = "UPDATE Books SET views = views + 1 WHERE bookID = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, bookID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Book> searchBook(String keyword) {

        List<Book> result = new ArrayList<>();
        List<Book> allBooks = getAll();

        keyword = keyword.toLowerCase();

        for (Book b : allBooks) {

            String title = b.getTitle().toLowerCase();

            // 1️⃣ nếu chứa keyword thì add luôn
            if (title.contains(keyword)) {
                result.add(b);
                continue;
            }

            // 2️⃣ fuzzy search
            int distance = StringSimilarity.levenshteinDistance(keyword, title);

            int maxLength = Math.max(keyword.length(), title.length());

            double similarity = 1 - (double) distance / maxLength;

            if (similarity > 0.4) {
                result.add(b);
            }
        }

        return result;
    }

    public List<Book> getLatestBooks(int limit) {
        List<Book> list = new ArrayList<>();
        String sql = """
                 SELECT TOP (?) *
                 FROM Books
                 ORDER BY createdAt DESC
                 """;

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, limit);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Book b = new Book(
                               rs.getInt("bookID"),
                               rs.getString("title"),
                               rs.getString("description"),
                               rs.getString("coverImage"),
                               rs.getInt("categoryID"),
                               rs.getString("author"),
                               rs.getInt("views"),
                               rs.getString("status"),
                               rs.getTimestamp("createdAt")
                );

                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Book> getLatestUpdatedBooks(int offset, int pageSize) {

        List<Book> list = new ArrayList<>();

        String sql = """
        SELECT *
        FROM Books
        ORDER BY createdAt DESC
        OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
    """;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, offset);
            ps.setInt(2, pageSize);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Book b = new Book(
                               rs.getInt("bookID"),
                               rs.getString("title"),
                               rs.getString("description"),
                               rs.getString("coverImage"),
                               rs.getInt("categoryID"),
                               rs.getString("author"),
                               rs.getInt("views"),
                               rs.getString("status"),
                               rs.getTimestamp("createdAt")
                );

                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int countLatestUpdatedBooks() {

        String sql = """
        SELECT COUNT(*) 
        FROM Books
    """;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

}
