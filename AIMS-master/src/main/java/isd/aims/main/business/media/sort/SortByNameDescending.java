package isd.aims.main.business.media.sort;

import isd.aims.main.entity.media.Media;

import java.util.ArrayList;
import java.util.List;

public class SortByNameDescending implements ISort {
    @Override
    public List<Media> sort(List<Media> mediaList) {
        List<Media> sortedMediaList = new ArrayList<>(mediaList);
        sortedMediaList.sort((m1, m2) -> m2.getTitle().compareToIgnoreCase(m1.getTitle()));
        return sortedMediaList;
    }
}
