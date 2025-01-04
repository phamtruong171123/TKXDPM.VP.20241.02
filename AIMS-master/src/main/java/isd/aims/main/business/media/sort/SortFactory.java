package isd.aims.main.business.media.sort;

import isd.aims.main.business.media.sort.impl.SortByNameAscending;
import isd.aims.main.business.media.sort.impl.SortByNameDescending;
import isd.aims.main.business.media.sort.impl.SortByPriceAscending;
import isd.aims.main.business.media.sort.impl.SortByPriceDescending;

public class SortFactory {
    public static ISort getSortStrategy(SortOption option) {
        return switch (option) {
            case NAME_ASC -> new SortByNameAscending();
            case NAME_DESC -> new SortByNameDescending();
            case PRICE_ASC -> new SortByPriceAscending();
            case PRICE_DESC -> new SortByPriceDescending();
        };
    }
}
