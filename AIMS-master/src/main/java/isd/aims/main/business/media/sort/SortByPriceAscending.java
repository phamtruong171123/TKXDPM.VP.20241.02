package isd.aims.main.business.media.sort;

import isd.aims.main.entity.media.Media;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortByPriceAscending implements ISort {

    @Override
    public List<Media> sort(List<Media> mediaList) {
        List<Media> sortedMediaList = new ArrayList<>(mediaList);
        sortedMediaList.sort(Comparator.comparingDouble(Media::getPrice));
        return sortedMediaList;
    }
}
