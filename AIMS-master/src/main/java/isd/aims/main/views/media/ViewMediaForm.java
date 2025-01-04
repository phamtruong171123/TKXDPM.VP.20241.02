package isd.aims.main.views.media;

import isd.aims.main.controller.HomeController;
import isd.aims.main.controller.ViewMediaController;
import isd.aims.main.entity.cart.Cart;
import isd.aims.main.entity.cart.CartMedia;
import isd.aims.main.entity.media.Media;
import isd.aims.main.exception.MediaNotAvailableException;
import isd.aims.main.utils.StaticResourcesConfigs;
import isd.aims.main.utils.Utils;
import isd.aims.main.views.BaseForm;
import isd.aims.main.views.cart.CartForm;
import isd.aims.main.views.home.HomeForm;
import isd.aims.main.views.popup.PopupForm;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ViewMediaForm extends BaseForm {

    private static Logger LOGGER = Utils.getLogger(ViewMediaForm.class.getName());

    @FXML
    protected ImageView aimsImage;

    @FXML
    protected ImageView mediaImage;

    @FXML
    protected Label mediaTitle;

    @FXML
    protected Label mediaCategory;

    @FXML
    protected Label mediaAvailable;

    @FXML
    protected Label mediaPrice;

    @FXML
    protected Spinner<Integer> spinnerChangeNumber;

    @FXML
    protected Button addToCartBtn;

    protected Media media;

    public ViewMediaController getBController() {
        return (ViewMediaController) super.getBController();
    }

    public ViewMediaForm(Stage stage, String screenPath, Media media, HomeForm home) throws IOException, SQLException {
        super(stage, screenPath);
        setBController(new ViewMediaController());

        // on mouse clicked, we back to home
        aimsImage.setOnMouseClicked(e -> {
            homeScreenHandler.show();
        });

        requestToViewMedia(media, home);
    }

    protected void requestToViewMedia(Media media, HomeForm home) throws SQLException {
        addToCartBtn.setOnMouseClicked(event -> {
            try {
                if (spinnerChangeNumber.getValue() > media.getQuantity()) throw new MediaNotAvailableException();
                Cart cart = Cart.getCart();

                // if media already in cart then we will increase the quantity by 1 instead of create the new cartMedia
                CartMedia mediaInCart = home.getBController().checkMediaInCart(media);
                if (mediaInCart != null) {
                    mediaInCart.setQuantity(mediaInCart.getQuantity() + 1);
                } else {
                    CartMedia cartMedia = new CartMedia(media, cart, spinnerChangeNumber.getValue(), media.getPrice());
                    cart.getListMedia().add(cartMedia);
                    System.out.println("Added " + cartMedia.getQuantity() + " " + media.getTitle() + " to cart");
                }

                // subtract the quantity and redisplay
                media.setQuantity(media.getQuantity() - spinnerChangeNumber.getValue());
                mediaAvailable.setText(String.valueOf(media.getQuantity()));
                home.getNumMediaCartLabel().setText(String.valueOf(cart.getTotalMedia()) + " media");

                // Sua lai PopupForm
                PopupForm.success("The media " + media.getTitle() + " added to Cart");
            } catch (MediaNotAvailableException exp) {
                try {
                    String message = "Not enough media:\nRequired: " + spinnerChangeNumber.getValue() + "\nAvail: " + media.getQuantity();
                    LOGGER.severe(message);
                    PopupForm.error(message);
                } catch (Exception e) {
                    LOGGER.severe("Cannot add media to cart: ");
                }

            } catch (Exception exp) {
                LOGGER.severe("Cannot add media to cart: ");
                exp.printStackTrace();
            }
        });
        setMediaInfo(media);
        show();
    }

    protected void setMediaInfo(Media media) throws SQLException {
        // set the cover image of media
        File file = new File(StaticResourcesConfigs.IMAGE_PATH + media.getImageURL());
        Image image = new Image(file.toURI().toString());
        mediaImage.setImage(image);

        // Set the title of media
        mediaTitle.setText(media.getTitle());

        // Set category
        mediaCategory.setText(media.getCategory());

        // Set price
        mediaPrice.setText(Utils.getCurrencyFormat(media.getPrice()));

        // Set available
        mediaAvailable.setText(Integer.toString(media.getQuantity()));

        // Spinner
        spinnerChangeNumber.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1)
        );
    }
}
