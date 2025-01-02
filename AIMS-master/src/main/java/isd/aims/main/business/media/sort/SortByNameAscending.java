package isd.aims.main.business.media.sort;

import isd.aims.main.entity.media.Media;

import java.util.ArrayList;
import java.util.List;

public class SortByNameAscending implements ISort {
    @Override
    public List<Media> sort(List<Media> mediaList) {
        List<Media> sortedMediaList = new ArrayList<>(mediaList);
        sortedMediaList.sort((m1, m2) -> m1.getTitle().compareToIgnoreCase(m2.getTitle()));
        return sortedMediaList;
    }
}
