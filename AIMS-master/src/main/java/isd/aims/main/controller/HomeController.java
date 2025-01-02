package isd.aims.main.controller;

import isd.aims.main.entity.media.Media;
import isd.aims.main.repository.impl.MediaRepositoryImpl;
import isd.aims.main.business.media.search.FilterFactory;
import isd.aims.main.business.media.search.FilterOption;
import isd.aims.main.business.media.search.IFilter;
import isd.aims.main.business.media.sort.ISort;
import isd.aims.main.business.media.sort.SortFactory;
import isd.aims.main.business.media.sort.SortOption;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * This class controls the flow of events in homescreen
 * @author nguyenlm
 */
public class HomeController extends BaseController{

    private List<Media> mediaList;
    private List<Media> screenMediaList;

    public void setAllMedia() throws SQLException {
        this.mediaList = new MediaRepositoryImpl().getAll();
    }

    @SuppressWarnings("rawtypes")
    public List getAllMedia() {
        return mediaList;
    }

    public void setScreenMediaList(List<Media> screenMediaList) {
        this.screenMediaList = screenMediaList;
    }

    public List<Media> getScreenMediaList() {
        return screenMediaList;
    }

    public List<Media> getFilteredMedia(String query) {
        if (query == null || query.isEmpty()) {
            return mediaList;
        }
        List<Media> filteredMediaList = new ArrayList<>();
        for (Media media : mediaList) {
            if (media.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredMediaList.add(media);
            }

            if (media.getCategory().equals(query.toLowerCase())) {
                filteredMediaList.add(media);
            }
        }
        return filteredMediaList;
    }

    public List<Media> applySortStrategy(SortOption sortOption) {
        ISort sortMachine = SortFactory.getSortStrategy(sortOption);
        return sortMachine.sort(screenMediaList);
    }

    public List<Media> filterByType(FilterOption option) {
        IFilter filterMachine = FilterFactory.getFilter(option);
        return filterMachine.filter(screenMediaList);
    }
}
