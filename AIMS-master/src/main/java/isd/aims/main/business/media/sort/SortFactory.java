package isd.aims.main.business.media.sort;

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
