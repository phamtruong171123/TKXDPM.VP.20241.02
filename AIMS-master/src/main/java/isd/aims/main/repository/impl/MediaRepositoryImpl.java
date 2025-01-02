package isd.aims.main.repository.impl;

import isd.aims.main.entity.db.DBConnection;
import isd.aims.main.entity.media.DVD;
import isd.aims.main.entity.media.Media;
import isd.aims.main.repository.IMediaRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MediaRepositoryImpl implements IMediaRepository {
    private final Statement stm;

    public MediaRepositoryImpl() throws SQLException {
        stm = DBConnection.getConnection().createStatement();
    }

    @Override
    public List<Media> getAll() throws SQLException {
        String sql = "SELECT * FROM Media";
        List<Media> medium = new ArrayList<>();
        ResultSet res = stm.executeQuery(sql);
        while (res.next()) {
            medium.add(mapToMedia(res));
        }
        System.out.println(medium);
        return medium;
    }
    public void updateMediaFieldById(int id, String field, Object value) throws SQLException {
        if (value instanceof String) {
            value = "\"" + value + "\"";
        }
        String sql = "UPDATE Media SET " + field + " = " + value + " WHERE id = " + id + ";";
        stm.executeUpdate(sql);
    }

    @Override
    public Media getById(int id) throws SQLException {
        String sql = "SELECT * FROM Media ;";
        Statement stm = DBConnection.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
		if(res.next()) {
            return mapToMedia(res);
        }
        return null;
    }

    static Media mapToMedia(ResultSet res) throws SQLException {
        int id = res.getInt("id");
        String title = res.getString("title");
        String category = res.getString("category");
        int price = res.getInt("price");
        int quantity = res.getInt("quantity");
        String type = res.getString("type");
        String imageURL = res.getString("imageURL");
        return new Media(id, title, category, price, quantity, type, imageURL);
    }

    /**
     * Lấy ngẫu nhiên sản phảm, mỗi lần lấy ra một số lượng nhất định sản phẩm
     * @Parameter limit INT: số lượng sản phẩm muốn lấy ra
     * @Parameter offset INT: chỉ sổ của phần tử bắt đầu select
     * @RETURN Danh sách sản phẩm
     **/
    public List<Media> getMediasWithPagination(int limit, int offset) throws SQLException {
        String sql = "SELECT * FROM Media LIMIT " + limit + " OFFSET " + offset;
        List<Media> medium = new ArrayList<>();
        ResultSet res = stm.executeQuery(sql);
        while (res.next()) {
            medium.add(MediaRepositoryImpl.mapToMedia(res));
        }
        return medium;
    }

    /**
     * Lọc dữ liệu bởi thuộc tính type của sản phẩm, mỗi lần lấy ra một số lượng nhất định sản phẩm
     * @Parameter type String: Kiểu sản phẩm muôn lấy (Book, Cd, Dvd,...)
     * @Parameter limit INT: số lượng sản phẩm muốn lấy ra
     * @Parameter offset INT: chỉ sổ của phần tử bắt đầu select
     * @RETURN Danh sách sản phẩm
     **/
    public List<Media> getMediasByTypeWithPagination(String type, int limit, int offset) throws SQLException {
        String sql = "SELECT * FROM Media WHERE type = \'" + type + "\'" + " LIMIT " + limit + " OFFSET " + offset;
        List<Media> medium = new ArrayList<>();
        ResultSet res = stm.executeQuery(sql);
        while (res.next()) {
            medium.add(MediaRepositoryImpl.mapToMedia(res));
        }
        return medium;
    }

    /**
     * Lọc dữ liệu với những sản phẩm trong tên có chứa query, mỗi lần lấy ra một số lượng nhất định sản phẩm
     * @Parameter query String: chuỗi dùng để truy vấn
     * @Parameter limit INT: số lượng sản phẩm muốn lấy ra
     * @Parameter offset INT: chỉ sổ của phần tử bắt đầu select
     * @RETURN Danh sách sản phẩm
     **/
    public List<Media> getMediasFilteredByQueryWithPagination(String query, int limit, int offset) throws SQLException {
        String sql = "SELECT * FROM Media WHERE title LIKE \'%" + query + "%\'" + " LIMIT " + limit + " OFFSET " + offset;
        List<Media> medium = new ArrayList<>();
        ResultSet res = stm.executeQuery(sql);
        while (res.next()) {
            medium.add(MediaRepositoryImpl.mapToMedia(res));
        }
        return medium;
    }

    /**
     * Lọc dữ liệu với những sản phẩm trong thuộc category chỉ định, mỗi lần lấy ra một số lượng nhất định sản phẩm
     * @Parameter category String: category
     * @Parameter limit INT: số lượng sản phẩm muốn lấy ra
     * @Parameter offset INT: chỉ sổ của phần tử bắt đầu select
     * @RETURN Danh sách sản phẩm
     **/
    public List<Media> getMediasFilteredByCategoryWithPagination(String category, int limit, int offset) throws SQLException {
        String sql = "SELECT * FROM Media WHERE category = \'" + category + "\'" + " LIMIT " + limit + " OFFSET " + offset;
        List<Media> medium = new ArrayList<>();
        ResultSet res = stm.executeQuery(sql);
        while (res.next()) {
            medium.add(MediaRepositoryImpl.mapToMedia(res));
        }
        return medium;
    }
}
