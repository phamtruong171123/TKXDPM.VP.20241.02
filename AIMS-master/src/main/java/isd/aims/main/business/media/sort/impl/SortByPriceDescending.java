package isd.aims.main.business.media.sort.impl;

import isd.aims.main.business.media.sort.ISort;
import isd.aims.main.entity.media.Media;

import java.util.ArrayList;
import java.util.List;

public class SortByPriceDescending implements ISort {
    @Override
    public List<Media> sort(List<Media> mediaList) {
        List<Media> sortedMediaList = new ArrayList<>(mediaList);
        sortedMediaList.sort((m1, m2) -> Double.compare(m2.getPrice(), m1.getPrice()));
        return sortedMediaList;
    }
}
