package isd.aims.main.business.media.sort;

import isd.aims.main.entity.media.Media;

import java.util.List;

public interface ISort {
    List<Media> sort(List<Media> mediaList);
}
