package isd.aims.main.business.media.search.impl;

import isd.aims.main.business.media.search.IFilter;
import isd.aims.main.entity.media.Media;

import java.util.ArrayList;
import java.util.List;

public class FilterByDVD implements IFilter {
    @Override
    public List<Media> filter(List<Media> mediaList) {
        List<Media> filteredMediaList = new ArrayList<>();
        mediaList.forEach(media -> {
            if (media.getType().contains("dvd")) {
                filteredMediaList.add(media);
            }
        });
        return filteredMediaList;
    }
}
