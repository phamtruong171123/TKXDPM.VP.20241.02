package isd.aims.main.views.media;

import isd.aims.main.controller.ViewMediaController;
import isd.aims.main.entity.media.Book;
import isd.aims.main.entity.media.Media;
import isd.aims.main.views.home.HomeForm;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ViewBookForm extends ViewMediaForm {
    public ViewBookForm(Stage stage, String screenPath, Media media, HomeForm home) throws IOException, SQLException {
        super(stage, screenPath, media, home);
        viewDetail(media);
    }

    private void viewDetail(Media media) throws SQLException {
        Media book = getBController().getMedia(media.getId(), media.getType());
        System.out.println(book);
    }
}
