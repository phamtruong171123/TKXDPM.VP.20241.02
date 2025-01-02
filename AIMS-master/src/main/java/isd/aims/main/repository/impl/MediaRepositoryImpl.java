package isd.aims.main.repository.impl;

import isd.aims.main.entity.db.DBConnection;
import isd.aims.main.entity.media.DVD;
import isd.aims.main.entity.media.Media;
import isd.aims.main.repository.IMediaRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    private Media mapToMedia(ResultSet res) throws SQLException {
        int id = res.getInt("id");
        String title = res.getString("title");
        String category = res.getString("category");
        int price = res.getInt("price");
        int quantity = res.getInt("quantity");
        String type = res.getString("type");
        String imageURL = res.getString("imageURL");

        return new Media(id, title, category, price, quantity, type, imageURL);
    }


}
