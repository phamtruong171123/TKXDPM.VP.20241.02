package isd.aims.main.business.media.search;

import isd.aims.main.entity.media.Media;

import java.util.List;

public interface IFilter {
    List<Media> filter(List<Media> mediaList);
}
