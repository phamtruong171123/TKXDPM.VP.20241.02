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
