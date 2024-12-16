package isd.aims.main.repository.impl;

import isd.aims.main.entity.db.DBConnection;
import isd.aims.main.entity.media.DVD;
import isd.aims.main.entity.media.Media;
import isd.aims.main.repository.IMediaRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DVDRepositoryImpl implements IMediaRepository<DVD> {
    private final Statement stm;
    public DVDRepositoryImpl() throws SQLException {
        stm = DBConnection.getConnection().createStatement();
    }

    @Override
    public DVD getById(int id) throws SQLException {
        String sql = "SELECT * FROM "+
                     "aims.DVD " +
                     "INNER JOIN aims.Media " +
                     "ON Media.id = DVD.id " +
                     "where Media.id = " + id + ";";
        ResultSet res = stm.executeQuery(sql);
        if(res.next()) {
            return mapToDVD(res);
        } else {
            throw new SQLException();
        }
    }

    @Override
    public List<DVD> getAll() throws SQLException {
        String sql = "SELECT * FROM aims.DVD " +
                "INNER JOIN aims.Media ON Media.id = DVD.id";
        List<DVD> dvds = new ArrayList<>();
        ResultSet res = stm.executeQuery(sql);
        while (res.next()) {
            dvds.add(mapToDVD(res));
        }
        return dvds;
    }
//    public void insertDVD(DVD dvd) throws SQLException {
//        String mediaSql = "INSERT INTO aims.Media (id, title, category, price, quantity, type, imageURL) VALUES (?, ?, ?, ?, ?, ?, ?);";
//        String dvdSql = "INSERT INTO aims.DVD (id, discType, director, runtime, studio, subtitles, releasedDate, filmType) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
//
//        try (PreparedStatement mediaStm = connection.prepareStatement(mediaSql);
//             PreparedStatement dvdStm = connection.prepareStatement(dvdSql)) {
//
//            // Insert into Media table
//            mediaStm.setInt(1, dvd.getId());
//            mediaStm.setString(2, dvd.getTitle());
//            mediaStm.setString(3, dvd.getCategory());
//            mediaStm.setInt(4, dvd.getPrice());
//            mediaStm.setInt(5, dvd.getQuantity());
//            mediaStm.setString(6, dvd.getType());
//            mediaStm.setString(7, dvd.getImageURL());
//            mediaStm.executeUpdate();
//
//            // Insert into Book table
//            dvdStm.setInt(1, dvd.getId());
//            dvdStm.setString(2, dvd.getDiscType());
//            dvdStm.setString(3, dvd.getDirector());
//            dvdStm.setInt(4, dvd.getRuntime());
//            dvdStm.setString(5, dvd.getStudio());
//            dvdStm.setString(6, dvd.getSubtitles());
//            dvdStm.setDate(7, new java.sql.Date(dvd.getReleasedDate().getTime()));
//            dvdStm.setString(8, dvd.getFilmType());
//            dvdStm.executeUpdate();
//        }
//    }
//    public void updateDVD(DVD dvd) throws SQLException {
//        String mediaSql = "UPDATE aims.Media SET title = ?, category = ?, price = ?, quantity = ?, type = ?, imageURL = ? WHERE id = ?;";
//        String dvdSql = "UPDATE aims.DVD SET discType= ?, director = ?, runtime = ?, studio = ?, subtitles = ?, releasedDate = ?, filmType = ? WHERE id = ?;";
//
//        try (PreparedStatement mediaStm = connection.prepareStatement(mediaSql);
//             PreparedStatement dvdStm = connection.prepareStatement(dvdSql)) {
//
//            // Update Media table
//            mediaStm.setString(1, dvd.getTitle());
//            mediaStm.setString(2, dvd.getCategory());
//            mediaStm.setInt(3, dvd.getPrice());
//            mediaStm.setInt(4, dvd.getQuantity());
//            mediaStm.setString(5, dvd.getType());
//            mediaStm.setString(6, dvd.getImageURL());
//            mediaStm.setInt(7, dvd.getId());
//            mediaStm.executeUpdate();
//
//            // Update Book table
//            dvdStm.setString(1, dvd.getDiscType());
//            dvdStm.setString(2, dvd.getDirector());
//            dvdStm.setInt(3, dvd.getRuntime());
//            dvdStm.setString(4, dvd.getStudio());
//            dvdStm.setString(5, dvd.getSubtitles());
//            dvdStm.setDate(6, new java.sql.Date(dvd.getReleasedDate().getTime()));
//            dvdStm.setString(7, dvd.getFilmType());
//            dvdStm.setInt(8, dvd.getId());
//            dvdStm.executeUpdate();
//        }
//    }
//    public void deleteDVD(int id) throws SQLException {
//        String dvdSql = "DELETE FROM aims.DVD WHERE id = ?;";
//        String mediaSql = "DELETE FROM aims.Media WHERE id = ?;";
//
//        try (PreparedStatement dvdStm = connection.prepareStatement(dvdSql);
//             PreparedStatement mediaStm = connection.prepareStatement(mediaSql)) {
//
//            // Delete from Book table
//            dvdStm.setInt(1, id);
//            dvdStm.executeUpdate();
//
//            // Delete from Media table
//            mediaStm.setInt(1, id);
//            mediaStm.executeUpdate();
//        }
//    }
    private DVD mapToDVD(ResultSet res) throws SQLException {
        int id = res.getInt("id");
        String title = res.getString("title");
        String category = res.getString("category");
        int price = res.getInt("price");
        int quantity = res.getInt("quantity");
        String type = res.getString("type");
        String imageURL = res.getString("imageURL");
        String discType = res.getString("discType");
        String director = res.getString("director");
        int runtime = res.getInt("runtime");
        String studio = res.getString("studio");
        String subtitles = res.getString("subtitle");
        Date releasedDate = res.getDate("releasedDate");
        String filmType = res.getString("filmType");
        return new DVD(id, title, category, price, quantity, type, imageURL, discType, director, runtime, studio, subtitles, releasedDate, filmType);
    }
}
