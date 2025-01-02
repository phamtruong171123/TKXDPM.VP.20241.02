package isd.aims.main.views.shipping;

import isd.aims.main.context.DeliveryFeeContext;
import isd.aims.main.controller.PlaceOrderController;
import isd.aims.main.controller.ViewCartController;
import isd.aims.main.entity.invoice.Invoice;
import isd.aims.main.entity.order.Order;
import isd.aims.main.entity.shipping.Shipment;
import isd.aims.main.exception.InvalidDeliveryInfoException;
import isd.aims.main.strategy.DeliveryFeeStrategy;
import isd.aims.main.strategy.impl.JoinedDeliveryFeeStrategy;
import isd.aims.main.strategy.impl.RegularDeliveryFeeStrategy;
import isd.aims.main.strategy.impl.RushDeliveryFeeStrategy;
import isd.aims.main.utils.Configs;
import isd.aims.main.utils.Utils;
import isd.aims.main.views.BaseForm;
import isd.aims.main.views.cart.CartForm;
import isd.aims.main.views.invoice.InvoiceForm;
import isd.aims.main.views.popup.PopupForm;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class DeliveryForm extends BaseForm implements Initializable {
    private static final Logger LOGGER = Utils.getLogger(DeliveryForm.class.getName());

    @FXML
    private Label screenTitle;

    @FXML
    private TextField name;

    @FXML
    private TextField phone;
    @FXML
    private TextField email;
    @FXML
    private TextField address;

    @FXML
    private TextField instructions;
    @FXML
    private ComboBox<String> province;
    @FXML
    private Label districtLbl;
    @FXML
    private ComboBox<String> district;

    private final Order order;
    private final HashMap<String, String> deliveryInfo = new HashMap<>();

    /**
     * The constructor of DeliveryForm class
     *
     * @param stage
     * @param screenPath
     * @param order
     * @throws IOException
     */
    public DeliveryForm(Stage stage, String screenPath, Order order) throws IOException {
        super(stage, screenPath);
        this.order = order;

    }

    /**
     * The method saves user's input to deliveryInfo HashMap
     */
    private void saveDeliveryInfo() {
        deliveryInfo.put("name", name.getText());
        deliveryInfo.put("phone", phone.getText());
        deliveryInfo.put("email", email.getText());
        deliveryInfo.put("address", address.getText());
        deliveryInfo.put("instructions", instructions.getText());
        deliveryInfo.put("province", province.getValue() != null ? province.getValue() : "");
        deliveryInfo.put("district", district.getValue() != null ? district.getValue() : "");
    }

    // Hàm khôi phục thông tin từ HashMap

    /**
     * The method restore user's information in case user returns to this screen
     */
    public void restoreDeliveryInfo() {
        name.setText(deliveryInfo.getOrDefault("name", ""));
        phone.setText(deliveryInfo.getOrDefault("phone", ""));
        email.setText(deliveryInfo.getOrDefault("email", ""));
        address.setText(deliveryInfo.getOrDefault("address", ""));
        instructions.setText(deliveryInfo.getOrDefault("instructions", ""));
        province.setValue(deliveryInfo.getOrDefault("province", null));
        district.setValue(deliveryInfo.getOrDefault("district", null));
    }

    /**
     * The method initializes the delivery form screen
     *
     * @param arg0
     * @param arg1
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load
        name.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && firstTime.get()) {
                content.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });
        districtLbl.setVisible(false);
        district.setVisible(false);
        this.province.getItems().addAll(Configs.PROVINCES);
        this.district.getItems().addAll(Configs.DISTRICTS);
        province.valueProperty().addListener((observable, oldValue, newValue) -> {
            if ("Hà Nội".equals(newValue)) {
                districtLbl.setVisible(true);
                district.setVisible(true);
            } else {
                districtLbl.setVisible(false);
                district.setVisible(false);
            }
        });
    }

    /**
     * The method handles when user clicks Back button
     *
     * @param event
     * @throws IOException
     * @throws InterruptedException
     * @throws SQLException
     */
    @FXML
    void back(MouseEvent event) throws IOException, InterruptedException, SQLException {
        LOGGER.info("Back button clicked");
//			CartForm cartForm = (CartForm) getPreviousScreen();
        saveDeliveryInfo(); // Khôi phục thông tin
//
//			cartForm.show();
        CartForm cartScreen = new CartForm(this.stage, Configs.CART_SCREEN_PATH);
        cartScreen.setPreviousScreen(this);
        cartScreen.setHomeScreenHandler(homeScreenHandler);
        cartScreen.setBController(new ViewCartController());
        cartScreen.requestToViewCart(this);

    }

    /**
     * The method handles when user clicks Confirm delivery button
     *
     * @param event
     * @throws IOException
     * @throws InterruptedException
     * @throws SQLException
     */
    @FXML
    void submitDeliveryInfo(MouseEvent event) throws IOException, InterruptedException, SQLException {
        // add info to messages
        saveDeliveryInfo();
        Shipment info = new Shipment(name.getText(), phone.getText(), email.getText(), province.getValue(), address.getText(), instructions.getText(), district.getValue());
        try {
            // process and validate delivery info
            getBController().processDeliveryInfo(info);
        } catch (InvalidDeliveryInfoException e) {
            PopupForm.error(e.getMessage());
            throw new InvalidDeliveryInfoException(e.getMessage());

        }

        // calculate shipping fees
        order.setDeliveryInfo(info);
        // create invoice screen
        Invoice invoice = getBController().createInvoice(order);
        DeliveryFeeStrategy deliveryStrategy;
        DeliveryFeeContext deliveryContext = new DeliveryFeeContext();

        if (invoice.getOrder().getDeliveryInfo().isRushDelivery()) {
            deliveryStrategy = new JoinedDeliveryFeeStrategy(new RegularDeliveryFeeStrategy(), new RushDeliveryFeeStrategy());
        } else {
            deliveryStrategy = new RegularDeliveryFeeStrategy();
        }

        // Tạo context với chiến lược tương ứng
        deliveryContext.setDeliveryStrategy(deliveryStrategy);

        // Tính phí vận chuyển mới
        int shippingFees = deliveryContext.calculateShippingFee(invoice.getOrder());
        invoice.setShippingFee(shippingFees);
        BaseForm InvoiceScreenHandler = new InvoiceForm(this.stage, Configs.INVOICE_SCREEN_PATH, invoice);
        InvoiceScreenHandler.setPreviousScreen(this);
        InvoiceScreenHandler.setHomeScreenHandler(homeScreenHandler);
        InvoiceScreenHandler.setScreenTitle("Invoice Screen");
        InvoiceScreenHandler.setBController(getBController());
        InvoiceScreenHandler.show();
    }


    public PlaceOrderController getBController() {
        return (PlaceOrderController) super.getBController();
    }

    public void notifyError() {
        // TODO: implement later on if we need
    }

}
