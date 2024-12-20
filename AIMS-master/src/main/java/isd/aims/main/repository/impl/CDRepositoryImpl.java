package isd.aims.main.repository.impl;

import isd.aims.main.entity.db.DBConnection;
import isd.aims.main.entity.media.CD;
import isd.aims.main.repository.IMediaRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CDRepositoryImpl implements IMediaRepository<CD> {
    private final Statement stm;

    public CDRepositoryImpl() throws SQLException {
        stm = DBConnection.getConnection().createStatement();
    }
    @Override
    public CD getById(int id) throws SQLException {
        String sql = "SELECT * FROM "+
                "aims.CD " +
                "INNER JOIN aims.Media " +
                "ON Media.id = CD.id " +
                "where Media.id = " + id + ";";
        ResultSet res = stm.executeQuery(sql);
        if(res.next()) {
            return mapToCD(res);
        } else {
            throw new SQLException();
        }
    }

    @Override
    public List<CD> getAll() throws SQLException {
        String sql = "SELECT * FROM aims.CD " +
                "INNER JOIN aims.Media ON Media.id = CD.id";
        List<CD> cds = new ArrayList<>();
        ResultSet res = stm.executeQuery(sql);
        while (res.next()) {
            cds.add(mapToCD(res));
        }
        return cds;
    }
//    public void insertCD(CD cd) throws SQLException {
//        String mediaSql = "INSERT INTO aims.Media (id, title, category, price, quantity, type, imageURL) VALUES (?, ?, ?, ?, ?, ?, ?);";
//        String cdSql = "INSERT INTO aims.CD (id, artist, recordLabel, musicType, releasedDate) VALUES (?, ?, ?, ?, ?);";
//
//        try (PreparedStatement mediaStm = connection.prepareStatement(mediaSql);
//             PreparedStatement cdStm = connection.prepareStatement(cdSql)) {
//
//            // Insert into Media table
//            mediaStm.setInt(1, cd.getId());
//            mediaStm.setString(2, cd.getTitle());
//            mediaStm.setString(3, cd.getCategory());
//            mediaStm.setInt(4, cd.getPrice());
//            mediaStm.setInt(5, cd.getQuantity());
//            mediaStm.setString(6, cd.getType());
//            mediaStm.setString(7, cd.getImageURL());
//            mediaStm.executeUpdate();
//
//            // Insert into Book table
//            cdStm.setInt(1, cd.getId());
//            cdStm.setString(2, cd.getArtist());
//            cdStm.setString(3, cd.getRecordLabel());
//            cdStm.setString(4, cd.getMusicType());
//            cdStm.setDate(5, new java.sql.Date(cd.getReleasedDate().getTime()));
//            cdStm.executeUpdate();
//        }
//    }
//    public void updateCD(CD cd) throws SQLException {
//        String mediaSql = "UPDATE aims.Media SET title = ?, category = ?, price = ?, quantity = ?, type = ?, imageURL = ? WHERE id = ?;";
//        String cdSql = "UPDATE aims.CD SET artist = ?, recordLabel = ?, musicType = ?, releasedDate = ? WHERE id = ?;";
//
//        try (PreparedStatement mediaStm = connection.prepareStatement(mediaSql);
//             PreparedStatement cdStm = connection.prepareStatement(cdSql)) {
//
//            // Update Media table
//            mediaStm.setString(1, cd.getTitle());
//            mediaStm.setString(2, cd.getCategory());
//            mediaStm.setInt(3, cd.getPrice());
//            mediaStm.setInt(4, cd.getQuantity());
//            mediaStm.setString(5, cd.getType());
//            mediaStm.setString(6, cd.getImageURL());
//            mediaStm.setInt(7, cd.getId());
//            mediaStm.executeUpdate();
//
//            // Update Book table
//            cdStm.setString(1, cd.getArtist());
//            cdStm.setString(2, cd.getRecordLabel());
//            cdStm.setString(3, cd.getMusicType());
//            cdStm.setDate(4, new java.sql.Date(cd.getReleasedDate().getTime()));
//            cdStm.setInt(5, cd.getId());
//            cdStm.executeUpdate();
//        }
//    }
//    public void deleteCD(int id) throws SQLException {
//        String cdSql = "DELETE FROM aims.CD WHERE id = ?;";
//        String mediaSql = "DELETE FROM aims.Media WHERE id = ?;";
//
//        try (PreparedStatement cdStm = connection.prepareStatement(cdSql);
//             PreparedStatement mediaStm = connection.prepareStatement(mediaSql)) {
//
//            // Delete from Book table
//            cdStm.setInt(1, id);
//            cdStm.executeUpdate();
//
//            // Delete from Media table
//            mediaStm.setInt(1, id);
//            mediaStm.executeUpdate();
//        }
//    }
    private CD mapToCD(ResultSet res) throws SQLException {
        int id = res.getInt("id");
        String title = res.getString("title");
        String category = res.getString("category");
        int price = res.getInt("price");
        int quantity = res.getInt("quantity");
        String type = res.getString("type");
        String imageURL = res.getString("imageURL");

        String artist = res.getString("artist");
        String recordLabel = res.getString("recordLabel");
        String musicType = res.getString("musicType");
        Date releasedDate = res.getDate("releasedDate");
        return new CD(id, title, category, price, quantity, type, imageURL, artist, recordLabel, musicType, releasedDate);
    }
}
