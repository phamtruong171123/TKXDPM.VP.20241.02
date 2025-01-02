package isd.aims.main.business.media.search;

public enum FilterOption {
    BOOK("Book"),
    CD("CD"),
    DVD("DVD");

    private final String displayName;

    FilterOption(final String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
