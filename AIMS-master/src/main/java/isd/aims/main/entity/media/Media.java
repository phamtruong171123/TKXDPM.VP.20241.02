package isd.aims.main.entity.media;

import isd.aims.main.repository.impl.MediaRepositoryImpl;
import isd.aims.main.utils.Utils;

import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Logger;

/**
 * The general media class, for another media it can be done by inheriting this class
 * @author nguyenlm
 */
public class Media {

    private static Logger LOGGER = Utils.getLogger(Media.class.getName());

//    protected Statement stm;
    protected int id;
    protected String title;
    protected String category;
    protected int value; // the real price of product (eg: 450)
    protected int price; // the price which will be displayed on browser (eg: 500)
    protected int quantity;
    protected String type;
    protected String imageURL;
    protected boolean isSupportRushDelivery;
    protected double weight;

    private MediaRepositoryImpl mediaRepository;

    public Media (int id, String title, String category, int price, int quantity, String type, String imageURL) throws SQLException{
        this.id = id;
        this.title = title;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
        this.imageURL = imageURL;
        double weight = Math.round((0.5 + (0.9 - 0.5) * new Random().nextDouble()) * 100.0) / 100.0;
        boolean isRush = new Random().nextBoolean();
        this.isSupportRushDelivery = isRush;
        this.weight = weight;
        mediaRepository = new MediaRepositoryImpl();

        //stm = DBConnection.getConnection().createStatement();
    }
    public int getQuantity() throws SQLException {
        Media updatedMedia = mediaRepository.getById(id);
        if (updatedMedia != null) {
            this.quantity = updatedMedia.quantity;
        }
        return this.quantity;
    }

    // getter and setter 
    public int getId() {
        return this.id;
    }

    private Media setId(int id){
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Media setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCategory() {
        return this.category;
    }

    public Media setCategory(String category) {
        this.category = category;
        return this;
    }

    public int getPrice() {
        return this.price * 1000;
    }

    public Media setPrice(int price) {
        this.price = price;
        return this;
    }

    public String getImageURL(){
        return this.imageURL;
    }

    public Media setMediaURL(String url){
        this.imageURL = url;
        return this;
    }

    public Media setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public Media setType(String type) {
        this.type = type;
        return this;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public boolean isSupportRushDelivery() {
        return isSupportRushDelivery;
    }

    public void setSupportRushDelivery(boolean supportRushDelivery) {
        isSupportRushDelivery = supportRushDelivery;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + id + "'" +
            ", title='" + title + "'" +
            ", category='" + category + "'" +
            ", price='" + price + "'" +
            ", quantity='" + quantity + "'" +
            ", type='" + type + "'" +
            ", imageURL='" + imageURL + "'" +
            "}";
    }    

}