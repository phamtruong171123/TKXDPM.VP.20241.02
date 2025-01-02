package isd.aims.main.business.media.sort;

public enum SortOption {
    NAME_ASC("Tên A đến Z"),
    NAME_DESC("Tên Z đến A"),
    PRICE_ASC("Giá thấp đến cao"),
    PRICE_DESC("Giá cao xuống thấp");

    private final String displayName;

    SortOption(final String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
