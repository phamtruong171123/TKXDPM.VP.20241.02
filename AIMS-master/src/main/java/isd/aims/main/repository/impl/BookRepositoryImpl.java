package isd.aims.main.repository.impl;

import isd.aims.main.entity.db.DBConnection;
import isd.aims.main.entity.media.Book;
import isd.aims.main.entity.media.CD;
import isd.aims.main.repository.IMediaRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookRepositoryImpl implements IMediaRepository<Book> {
    private final Statement stm;

    public BookRepositoryImpl() throws SQLException {
        stm = DBConnection.getConnection().createStatement();
    }
    @Override
    public Book getById(int id) throws SQLException {
        String sql = "SELECT * FROM "+
                "aims.Book " +
                "INNER JOIN aims.Media " +
                "ON Media.id = Book.id " +
                "where Media.id = " + id + ";";
        ResultSet res = stm.executeQuery(sql);
        if(res.next()) {
            return mapToBook(res);
        } else {
            throw new SQLException();
        }
    }

    public List<Book> getAll() throws SQLException {
        String sql = "SELECT * FROM aims.Book " +
                "INNER JOIN aims.Media ON Media.id = Book.id";
        List<Book> books = new ArrayList<>();
        ResultSet res = stm.executeQuery(sql);
        while (res.next()) {
            books.add(mapToBook(res));
        }
        return books;
    }


//    public void insertBook(Book book) throws SQLException {
//        String mediaSql = "INSERT INTO aims.Media (id, title, category, price, quantity, type, imageURL) VALUES (?, ?, ?, ?, ?, ?, ?);";
//        String bookSql = "INSERT INTO aims.Book (id, author, coverType, publisher, publishDate, numOfPages, language, bookCategory) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
//
//        try (PreparedStatement mediaStm = connection.prepareStatement(mediaSql);
//             PreparedStatement bookStm = connection.prepareStatement(bookSql)) {
//
//            // Insert into Media table
//            mediaStm.setInt(1, book.getId());
//            mediaStm.setString(2, book.getTitle());
//            mediaStm.setString(3, book.getCategory());
//            mediaStm.setInt(4, book.getPrice());
//            mediaStm.setInt(5, book.getQuantity());
//            mediaStm.setString(6, book.getType());
//            mediaStm.setString(7, book.getImageURL());
//            mediaStm.executeUpdate();
//
//            // Insert into Book table
//            bookStm.setInt(1, book.getId());
//            bookStm.setString(2, book.getAuthor());
//            bookStm.setString(3, book.getCoverType());
//            bookStm.setString(4, book.getPublisher());
//            bookStm.setDate(5, new java.sql.Date(book.getPublishDate().getTime()));
//            bookStm.setInt(6, book.getNumOfPages());
//            bookStm.setString(7, book.getLanguage());
//            bookStm.setString(8, book.getBookCategory());
//            bookStm.executeUpdate();
//        }
//    }
//    public void updateBook(Book book) throws SQLException {
//        String mediaSql = "UPDATE aims.Media SET title = ?, category = ?, price = ?, quantity = ?, type = ?, imageURL = ? WHERE id = ?;";
//        String bookSql = "UPDATE aims.Book SET author = ?, coverType = ?, publisher = ?, publishDate = ?, language = ?, numOfPages = ?, bookCategory = ? WHERE id = ?;";
//
//        try (PreparedStatement mediaStm = connection.prepareStatement(mediaSql);
//             PreparedStatement bookStm = connection.prepareStatement(bookSql)) {
//
//            // Update Media table
//            mediaStm.setString(1, book.getTitle());
//            mediaStm.setString(2, book.getCategory());
//            mediaStm.setInt(3, book.getPrice());
//            mediaStm.setInt(4, book.getQuantity());
//            mediaStm.setString(5, book.getType());
//            mediaStm.setString(6, book.getImageURL());
//            mediaStm.setInt(7, book.getId());
//            mediaStm.executeUpdate();
//
//            // Update Book table
//            bookStm.setString(1, book.getAuthor());
//            bookStm.setString(2, book.getCoverType());
//            bookStm.setString(3, book.getPublisher());
//            bookStm.setDate(4, new java.sql.Date(book.getPublishDate().getTime()));
//            bookStm.setString(5, book.getLanguage());
//            bookStm.setInt(6, book.getNumOfPages());
//            bookStm.setString(7, book.getBookCategory());
//            bookStm.setInt(8, book.getId());
//            bookStm.executeUpdate();
//        }
//    }
//    public void deleteBook(int id) throws SQLException {
//        String bookSql = "DELETE FROM aims.Book WHERE id = ?;";
//        String mediaSql = "DELETE FROM aims.Media WHERE id = ?;";
//
//        try (PreparedStatement bookStm = connection.prepareStatement(bookSql);
//             PreparedStatement mediaStm = connection.prepareStatement(mediaSql)) {
//
//            // Delete from Book table
//            bookStm.setInt(1, id);
//            bookStm.executeUpdate();
//
//            // Delete from Media table
//            mediaStm.setInt(1, id);
//            mediaStm.executeUpdate();
//        }
//    }

    private Book mapToBook(ResultSet res) throws SQLException {
        // From Media table
        int id = res.getInt("id");
        String title = res.getString("title");
        String category = res.getString("category");
        int price = res.getInt("price");
        int quantity = res.getInt("quantity");
        String type = res.getString("type");
        String imageURL = res.getString("imageURL");

        // From Book table
        String author = res.getString("author");
        String coverType = res.getString("coverType");
        String publisher = res.getString("publisher");
        Date publishDate = res.getDate("publishDate");
        int numOfPages = res.getInt("numOfPages");
        String language = res.getString("language");
        String bookCategory = res.getString("bookCategory");

        return new Book(id, title, category, price, quantity, type, imageURL, author, coverType, publisher, publishDate, numOfPages, language, bookCategory);
    }
}
