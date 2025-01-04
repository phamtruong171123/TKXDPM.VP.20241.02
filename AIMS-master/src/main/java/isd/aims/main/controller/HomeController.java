package isd.aims.main.controller;

import isd.aims.main.entity.media.Media;
import isd.aims.main.repository.impl.MediaRepositoryImpl;
import isd.aims.main.business.media.search.FilterFactory;
import isd.aims.main.business.media.search.FilterOption;
import isd.aims.main.business.media.search.IFilter;
import isd.aims.main.business.media.sort.ISort;
import isd.aims.main.business.media.sort.SortFactory;
import isd.aims.main.business.media.sort.SortOption;
import isd.aims.main.utils.Configs;
import isd.aims.main.views.BaseForm;
import isd.aims.main.views.home.HomeForm;
import isd.aims.main.views.media.ViewMediaForm;

import java.io.IOException;
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

    private final MediaRepositoryImpl mediaRepository = new MediaRepositoryImpl();

    private final int PAGESIZE = 20;
    private int currentPage = 1;

    public void resetCurrentPage() {
        currentPage = 1;
    }

    public int incrementPage() {
        currentPage++;
        return currentPage;
    }

    public int decrementPage() {
        if (currentPage > 1) {
            currentPage--;
        }
        return currentPage;
    }

    public HomeController() throws SQLException {
    }

    public void setAllMedia() throws SQLException {
        this.mediaList = mediaRepository.getAll();
    }

    @SuppressWarnings("rawtypes")
    public List getAllMedia() {
        return mediaList;
    }

    public List<Media> getMediaPage(int pageNumber) throws SQLException {
        int offset = (pageNumber - 1) * PAGESIZE; // Tính toán OFFSET
        return mediaRepository.getMediasWithPagination(PAGESIZE, offset);
    }

    public void setScreenMediaList(List<Media> screenMediaList) {
        this.screenMediaList = screenMediaList;
    }

    public List<Media> getScreenMediaList() {
        return screenMediaList;
    }

    public List<Media> getFilteredMedia(String query) throws SQLException {
        if (query == null || query.isEmpty()) {
            return getMediaPage(1);
        }
        List<Media> filteredMediaList = new ArrayList<>();
        filteredMediaList.addAll(mediaRepository.getMediasFilteredByCategoryWithPagination(query.toLowerCase(), 100, 0));
        filteredMediaList.addAll(mediaRepository.getMediasFilteredByQueryWithPagination(query.toLowerCase(), 100, 0));
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

    public List<Media> chooseAllMediaWithType(FilterOption option) throws SQLException {
        String type = option.getDisplayName().toLowerCase();
        return mediaRepository.getMediasByTypeWithPagination(type, 100, 0);
    }
}
