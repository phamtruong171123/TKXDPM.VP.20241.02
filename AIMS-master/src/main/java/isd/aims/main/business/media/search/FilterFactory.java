package isd.aims.main.business.media.search;

public class FilterFactory {
    public static IFilter getFilter(FilterOption option) {
        return switch (option) {
            case BOOK -> new FilterByBook();
            case CD -> new FilterByCD();
            case DVD -> new FilterByDVD();
        };
    }
}
