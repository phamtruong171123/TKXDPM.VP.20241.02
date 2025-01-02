package isd.aims.main.business.media.search;

import isd.aims.main.entity.media.Media;

import java.util.ArrayList;
import java.util.List;

public class FilterByCD implements IFilter {

    @Override
    public List<Media> filter(List<Media> mediaList) {
        List<Media> filteredMediaList = new ArrayList<>();
        mediaList.forEach(media -> {
            if (media.getType().contains("cd")) {
                filteredMediaList.add(media);
            }
        });
        return filteredMediaList;
    }
}
