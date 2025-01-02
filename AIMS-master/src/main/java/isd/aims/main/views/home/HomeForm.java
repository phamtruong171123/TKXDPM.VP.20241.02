package isd.aims.main.views.home;

import isd.aims.main.business.media.sort.SortOption;
import isd.aims.main.exception.ViewCartException;
import isd.aims.main.controller.HomeController;
import isd.aims.main.controller.ViewCartController;
import isd.aims.main.entity.cart.Cart;
import isd.aims.main.entity.media.Media;
import isd.aims.main.business.media.search.FilterOption;
import isd.aims.main.utils.Configs;
import isd.aims.main.utils.Utils;
import isd.aims.main.views.BaseForm;
import isd.aims.main.views.cart.CartForm;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class HomeForm extends BaseForm implements Initializable {

    public static Logger LOGGER = Utils.getLogger(HomeForm.class.getName());

    @FXML
    private Label numMediaInCart;

    @FXML
    private ImageView aimsImage;

    @FXML
    private ImageView cartImage;

    @FXML
    private VBox vboxMedia;

    @FXML
    private TextField textFieldSearchBar;

    @FXML
    private SplitMenuButton splitMenuBtnSearch;

    @FXML
    private SplitMenuButton splitMenuBtnSort;

    @FXML
    private Button previousPageBtn;

    @FXML
    private Button nextPageBtn;

    @SuppressWarnings("rawtypes")
    private List homeItems;

    public HomeForm(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    public Label getNumMediaCartLabel(){
        return this.numMediaInCart;
    }

    public HomeController getBController() {
        return (HomeController) super.getBController();
    }

    @Override
    public void show() {
        numMediaInCart.setText(String.valueOf(Cart.getCart().getListMedia().size()) + " media");
        super.show();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            setBController(new HomeController());
            getBController().setAllMedia();
            List medium = getBController().getMediaPage(1);
            getBController().setScreenMediaList(medium);
            showMediaItems(medium);

            getBController().setScreenMediaList(medium);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

        previousPageBtn.setOnAction(event -> {
            try {
                int currentPage = getBController().decrementPage();
                List<Media> mediaList = getBController().getMediaPage(currentPage);
                showMediaItems(mediaList);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        nextPageBtn.setOnAction(event -> {
            try {
                int currentPage = getBController().incrementPage();
                List<Media> mediaList = getBController().getMediaPage(currentPage);
                showMediaItems(mediaList);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        aimsImage.setOnMouseClicked(e -> {
            try {
                textFieldSearchBar.setText("");
                getBController().setScreenMediaList(getBController().getMediaPage(1));
                showMediaItems(getBController().getScreenMediaList());
            } catch (SQLException | IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        addSortMenuItems();
        setupSearchBar();
        setupCartIcon();
    }
    
    private void addSortMenuItems() {
        for (SortOption option : SortOption.values()) {
            MenuItem menuItem = new MenuItem(option.getDisplayName());
            menuItem.setOnAction(event -> {
                List<Media> sortedMediaList = getBController().applySortStrategy(option);
                try {
                    showMediaItems(sortedMediaList);

                    getBController().setScreenMediaList(sortedMediaList);
                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }
            });
            splitMenuBtnSort.getItems().add(menuItem);
        }
    }

    private void setupSearchBar() {
        splitMenuBtnSearch.setOnAction(event -> {
            try {
                String query = textFieldSearchBar.getText();
                List<Media> filteredItems = getBController().getFilteredMedia(query);
                getBController().setScreenMediaList(filteredItems);
                showMediaItems(filteredItems);
                getBController().resetCurrentPage();
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        for (FilterOption option : FilterOption.values()) {
            MenuItem menuItem = new MenuItem(option.getDisplayName());
            Label label = new Label();
            label.prefWidthProperty().bind(splitMenuBtnSearch.widthProperty().subtract(50));
            label.setTextAlignment(TextAlignment.RIGHT);
            menuItem.setGraphic(label);
            menuItem.setOnAction(event -> {
                try {
                    List<Media> filteredMediaList;
                    if (!textFieldSearchBar.getText().equals("")) {
                        filteredMediaList = getBController().filterByType(option);
                    } else {
                        filteredMediaList = getBController().chooseAllMediaWithType(option);
                    }
                    getBController().resetCurrentPage();
                    showMediaItems(filteredMediaList);
                } catch (SQLException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            splitMenuBtnSearch.getItems().add(menuItem);
        }
    }

    private void setupCartIcon() {
        // Add event listener to Open Cart
        cartImage.setOnMouseClicked(e -> {
            try {
                LOGGER.info("User clicked to view cart");
                CartForm cartScreen = new CartForm(this.stage, Configs.CART_SCREEN_PATH);
                cartScreen.setHomeScreenHandler(this);
                cartScreen.setBController(new ViewCartController());
                cartScreen.requestToViewCart(this);
            } catch (IOException | SQLException e1) {
                throw new ViewCartException(Arrays.toString(e1.getStackTrace()).replaceAll(", ", "\n"));
            }
        });
    }

    public void setImage() {
        // fix image path caused by fxml
        File file1 = new File(Configs.IMAGE_PATH_ICON + "/" + "Logo.png");
        Image img1 = new Image(file1.toURI().toString());
        aimsImage.setImage(img1);

        File file2 = new File(Configs.IMAGE_PATH_ICON + "/" + "cart.png");
        Image img2 = new Image(file2.toURI().toString());
        cartImage.setImage(img2);
    }

    private void showMediaItems(List<Media> mediaList) throws SQLException, IOException {
        this.homeItems = new ArrayList();
        for (Media media : mediaList) {
            MediaForm m1 = new MediaForm(Configs.HOME_MEDIA_PATH, media, this);
            this.homeItems.add(m1);
        }
        addMediaHome(this.homeItems);
    }

    @SuppressWarnings("rawtypes")
    public void addMediaHome(List items){
        ArrayList mediaItems = (ArrayList)((ArrayList) items).clone();
        int numberOfMedia = mediaItems.size();

        System.out.println(numberOfMedia);

        vboxMedia.getChildren().clear();
        while (!mediaItems.isEmpty()) {
            // Tạo hàng
            HBox newHBox = new HBox();
            newHBox.setPrefWidth(321.0);
            newHBox.setPrefHeight(629.0);
            newHBox.setStyle("-fx-border-color: #33adff;");

            while (newHBox.getChildren().size() < 4 && !mediaItems.isEmpty()) {
                MediaForm media = (MediaForm) mediaItems.get(0);
                newHBox.getChildren().add(media.getContent());
                mediaItems.remove(media);
            }

            vboxMedia.getChildren().add(newHBox);
        }
    }
}
