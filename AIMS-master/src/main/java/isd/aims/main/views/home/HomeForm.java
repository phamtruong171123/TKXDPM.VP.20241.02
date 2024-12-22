package isd.aims.main.views.home;

import isd.aims.main.exception.ViewCartException;
import isd.aims.main.controller.HomeController;
import isd.aims.main.controller.ViewCartController;
import isd.aims.main.entity.cart.Cart;
import isd.aims.main.entity.media.Media;
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
        setBController(new HomeController());
        try {
            List medium = getBController().getAllMedia();
            this.homeItems = new ArrayList();
            for (Object object : medium) {
                Media media = (Media) object;
                MediaForm m1 = new MediaForm(Configs.HOME_MEDIA_PATH, media, this);
                this.homeItems.add(m1);
            }
        } catch (SQLException | IOException e){
            LOGGER.info("Errors occured: " + e.getMessage());
            e.printStackTrace();
        }

        aimsImage.setOnMouseClicked(e -> {
            addMediaHome(this.homeItems);
        });

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

        // Add event listener to SplitMenuButtonSearch
        splitMenuBtnSearch.setOnAction(event -> {
            String selectedCategory = textFieldSearchBar.getText();
            filterMediaByCategory(selectedCategory);
        });

        // Add event listener to SplitMenuBtnSort
        for (MenuItem item : splitMenuBtnSort.getItems()) {
            item.setOnAction(event -> {
                String selectedText = item.getText();

                // Sử dụng các hàm sắp xếp tương ứng
                switch (selectedText) {
                    case "Tên A đến Z":
                        sortByName(true);
                        break;
                    case "Tên Z đến A":
                        sortByName(false);
                        break;
                    case "Giá thấp đến cao":
                        sortByPrice(true);
                        break;
                    case "Giá cao xuống thấp":
                        sortByPrice(false);
                        break;
                }

                // Cập nhật giao diện
                addMediaHome(homeItems);
                LOGGER.info("Sorted media by: " + selectedText);
            });
        }

        addMediaHome(this.homeItems);
        addMenuItem(0, "Book", splitMenuBtnSearch);
        addMenuItem(1, "DVD", splitMenuBtnSearch);
        addMenuItem(2, "CD", splitMenuBtnSearch);
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

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void addMenuItem(int position, String text, MenuButton menuButton){
        MenuItem menuItem = new MenuItem();
        Label label = new Label();
        label.prefWidthProperty().bind(menuButton.widthProperty().subtract(31));
        label.setText(text);
        label.setTextAlignment(TextAlignment.RIGHT);
        menuItem.setGraphic(label);
        menuItem.setOnAction(e -> {
            // empty home media
            vboxMedia.getChildren().forEach(node -> {
                HBox hBox = (HBox) node;
                hBox.getChildren().clear();
            });

            // filter only media with the choosen category
            List filteredItems = new ArrayList<>();
            homeItems.forEach(me -> {
                MediaForm media = (MediaForm) me;
                if (media.getMedia().getTitle().toLowerCase().startsWith(text.toLowerCase())){
                    filteredItems.add(media);
                }
            });

            // fill out the home with filted media as category
            addMediaHome(filteredItems);
        });
        menuButton.getItems().add(position, menuItem);
    }

    // Helper method to filter media by category
    private void filterMediaByCategory(String category) {
        vboxMedia.getChildren().forEach(node -> {
            HBox hBox = (HBox) node;
            hBox.getChildren().clear();
        });

        List filteredItems = new ArrayList<>();
        for (Object object : homeItems) {
            MediaForm media = (MediaForm) object;
            if (media.getMedia().getTitle().toLowerCase().contains(category.toLowerCase())){
                filteredItems.add(media);
            }
        }

        addMediaHome(filteredItems);
    }

    // Sắp xếp theo tên (tăng dần hoặc giảm dần)
    private void sortByName(boolean ascending) {
        homeItems.sort((o1, o2) -> {
            MediaForm media1 = (MediaForm) o1;
            MediaForm media2 = (MediaForm) o2;
            int comparison = media1.getMedia().getTitle().compareToIgnoreCase(media2.getMedia().getTitle());
            return ascending ? comparison : -comparison;
        });
    }

    // Sắp xếp theo giá (tăng dần hoặc giảm dần)
    private void sortByPrice(boolean ascending) {
        homeItems.sort((o1, o2) -> {
            MediaForm media1 = (MediaForm) o1;
            MediaForm media2 = (MediaForm) o2;
            int comparison = Double.compare(media1.getMedia().getPrice(), media2.getMedia().getPrice());
            return ascending ? comparison : -comparison;
        });
    }
}
