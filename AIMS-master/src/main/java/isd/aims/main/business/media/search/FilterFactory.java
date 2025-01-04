package isd.aims.main.business.media.search;

import isd.aims.main.business.media.search.impl.FilterByBook;
import isd.aims.main.business.media.search.impl.FilterByCD;
import isd.aims.main.business.media.search.impl.FilterByDVD;

public class FilterFactory {
    public static IFilter getFilter(FilterOption option) {
        return switch (option) {
            case BOOK -> new FilterByBook();
            case CD -> new FilterByCD();
            case DVD -> new FilterByDVD();
        };
    }
}
