package isd.aims.main.controller;

import isd.aims.main.entity.media.Media;
import isd.aims.main.repository.impl.BookRepositoryImpl;
import isd.aims.main.repository.impl.CDRepositoryImpl;
import isd.aims.main.repository.impl.DVDRepositoryImpl;

import java.sql.SQLException;

public class ViewMediaController extends BaseController {
    public ViewMediaController() {

    }

    public Media getMedia(int id, String type) throws SQLException {
        Media media = null;
        if (type.equals("book")) {
            media = new CDRepositoryImpl().getById(id);
        } else if (type.equals("cd")) {
            media = new CDRepositoryImpl().getById(id);
        } else if (type.equals("dvd")) {
            media = new DVDRepositoryImpl().getById(id);
        }
        return media;
    }
}
