package isd.aims.main.entity.media;

import java.sql.SQLException;
import java.util.Date;

public class CD extends Media {

    String artist;
    String recordLabel;
    String musicType;
    Date releasedDate;

    public CD(int id, String title, String category, int price, int quantity, String type, String imageURL, String artist,
            String recordLabel, String musicType, Date releasedDate) throws SQLException{
        super(id, title, category, price, quantity, type, imageURL);
        this.artist = artist;
        this.recordLabel = recordLabel;
        this.musicType = musicType;
        this.releasedDate = releasedDate;
    }

    public String getArtist() {
        return this.artist;
    }

    public CD setArtist(String artist) {
        this.artist = artist;
        return this;
    }

    public String getRecordLabel() {
        return this.recordLabel;
    }

    public CD setRecordLabel(String recordLabel) {
        this.recordLabel = recordLabel;
        return this;
    }

    public String getMusicType() {
        return this.musicType;
    }

    public CD setMusicType(String musicType) {
        this.musicType = musicType;
        return this;
    }

    public Date getReleasedDate() {
        return this.releasedDate;
    }

    public CD setReleasedDate(Date releasedDate) {
        this.releasedDate = releasedDate;
        return this;
    }

    @Override
    public String toString() {
        return "{" + super.toString() + " artist='" + artist + "'" + ", recordLabel='" + recordLabel + "'"
                + "'" + ", musicType='" + musicType + "'" + ", releasedDate='"
                + releasedDate + "'" + "}";
    }

}
