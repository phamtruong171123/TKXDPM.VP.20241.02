package isd.aims.main.controller;

import isd.aims.main.entity.media.Media;
import isd.aims.main.repository.impl.MediaRepositoryImpl;

import java.sql.SQLException;
import java.util.List;


/**
 * This class controls the flow of events in homescreen
 * @author nguyenlm
 */
public class HomeController extends BaseController{


    /**
     * this method gets all Media in DB and return back to home to display
     * @return List[Media]
     * @throws SQLException
     */
    @SuppressWarnings("rawtypes")
    public List getAllMedia() throws SQLException{
        return new MediaRepositoryImpl().getAll();
    }

}
