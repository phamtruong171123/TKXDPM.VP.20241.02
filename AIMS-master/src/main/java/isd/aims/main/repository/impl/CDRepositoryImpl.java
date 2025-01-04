package isd.aims.main.repository.impl;

import isd.aims.main.entity.db.DBConnection;
import isd.aims.main.entity.media.CD;
import isd.aims.main.entity.media.Media;
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
        String sql = "SELECT * FROM " +
                "CD " + "INNER JOIN Media " +
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
