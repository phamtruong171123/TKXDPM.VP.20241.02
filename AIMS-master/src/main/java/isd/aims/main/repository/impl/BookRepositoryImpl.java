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
                "Media " + "INNER JOIN Book " +
                "ON Media.id = Book.id " +
                "WHERE Media.id = " + id + ";";
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
