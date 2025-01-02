package isd.aims.main.business.media.search;

import isd.aims.main.entity.media.Media;
import isd.aims.main.repository.impl.MediaRepositoryImpl;

import java.sql.SQLException;
import java.util.List;

public interface IFilter {
    List<Media> filter(List<Media> mediaList);
}
