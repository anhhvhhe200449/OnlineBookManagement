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
import model.Chapter;

public class ChapterDAO extends DBContext {

    //getAll chapter by Book ID
    public List<Chapter> getChapterByBookID(int bookID) {
        List<Chapter> list = new ArrayList<>();

        String sql = "SELECT * FROM Chapters WHERE bookID = ? ORDER BY chapterNumber";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, bookID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Chapter c = new Chapter(rs.getInt("chapterID"),
                               rs.getInt("bookID"),
                               rs.getInt("chapterNumber"),
                               rs.getString("ChapTitle"),
                               rs.getString("content"),
                               rs.getTimestamp("createdAt")
                );
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //get chapter by id
    public Chapter getChapterByID(int chapterID) {
        String sql = "SELECT * FROM Chapters WHERE chapterID = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, chapterID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Chapter(
                               rs.getInt("chapterID"),
                               rs.getInt("bookID"),
                               rs.getInt("chapterNumber"),
                               rs.getString("ChapTitle"),
                               rs.getString("content"),
                               rs.getTimestamp("createdAt")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //check chapter
    public boolean checkChapter(String ChapTitle, int bookID) throws SQLException {
        String sql = "SELECT ChapTitle FROM Chapters WHERE ChapTitle = ? AND bookID = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, ChapTitle);
        ps.setInt(2, bookID);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return true;
        }
        return false;
    }

    //insert chapter
    public boolean insertChapter(int bookID, int chapterNumber, String ChapTitle, String content) throws SQLException {
        if (checkChapter(ChapTitle, bookID)) {
            return false;
        }

        String sql = "INSERT INTO Chapters(bookID, chapterNumber, ChapTitle, content, createdAt) VALUES (?, ?, ?, ?, GETDATE())";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, bookID);
            ps.setInt(2, chapterNumber);
            ps.setString(3, ChapTitle);
            ps.setString(4, content);

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //count chapter in book
    public int countChapter(int bookID) {
        String sql = "SELECT COUNT(*) FROM Chapters WHERE bookID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, bookID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //delete chapter
    public boolean deleteChapter(int chapterID) {

        String sql = "DELETE FROM Chapters WHERE chapterID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, chapterID);
            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //edit chapter
    public boolean editChapter(int chapterID, int bookID, int chapterNumber, String ChapTitle, String content) {
        String sql = "UPDATE Chapters SET bookID = ?, chapterNumber = ?, ChapTitle = ?, content = ? WHERE chapterID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, bookID);
            ps.setInt(2, chapterNumber);
            ps.setString(3, ChapTitle);
            ps.setString(4, content);
            ps.setInt(5, chapterID);
            int check = ps.executeUpdate();

            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Chapter getLatestChapter(int bookID) {

        String sql = """
        SELECT TOP 1 *
        FROM Chapters
        WHERE bookID = ?
        ORDER BY createdAt DESC
    """;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, bookID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Chapter(
                               rs.getInt("chapterID"),
                               rs.getInt("bookID"),
                               rs.getInt("chapterNumber"),
                               rs.getString("ChapTitle"),
                               rs.getString("content"),
                               rs.getTimestamp("createdAt")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    

}
