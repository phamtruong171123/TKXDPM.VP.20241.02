package isd.aims.main.views.invoice;

import isd.aims.main.InterbankSubsystem.IPayment;
import isd.aims.main.InterbankSubsystem.VnPaySubsystem;
import isd.aims.main.context.DeliveryFeeContext;
import isd.aims.main.controller.PaymentController;
import isd.aims.main.controller.PlaceOrderController;
import isd.aims.main.entity.invoice.Invoice;
import isd.aims.main.entity.shipping.Shipment;
import isd.aims.main.exception.MediaNotAvailableException;
import isd.aims.main.exception.PaymentException;
import isd.aims.main.exception.ProcessInvoiceException;
import isd.aims.main.strategy.DeliveryFeeStrategy;
import isd.aims.main.strategy.impl.JoinedDeliveryFeeStrategy;
import isd.aims.main.strategy.impl.RegularDeliveryFeeStrategy;
import isd.aims.main.strategy.impl.RushDeliveryFeeStrategy;
import isd.aims.main.utils.Configs;
import isd.aims.main.utils.Utils;
import isd.aims.main.views.BaseForm;
import isd.aims.main.views.popup.PopupForm;
import isd.aims.main.views.shipping.DeliveryForm;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Logger;

public class InvoiceForm extends BaseForm {

    private static final Logger LOGGER = Utils.getLogger(InvoiceForm.class.getName());

    @FXML
    private Label pageTitle;

    @FXML
    private Label name;

    @FXML
    private Label phone;

    @FXML
    private Label email;

    @FXML
    private Label province;

    @FXML
    private Label address;

    @FXML
    private Label instructions;

    @FXML
    private Label deliveryTimeLbl;

    @FXML
    private Label rushDeliveryInstructionLbl;

    @FXML
    private Label priceIncludingVAT;
    @FXML
    private Label priceExcludingVAT;

    @FXML
    private Label shippingFees;

    @FXML
    private Label total;

    @FXML
    private TextField deliveryTime;

    @FXML
    private TextArea rushDeliveryInstruction;

    @FXML
    private RadioButton regularDelivery;

    @FXML
    private RadioButton rushDelivery;

    @FXML
    private VBox vboxItems;
    @FXML
    private Button btnConfirm;
    @FXML
    private Button btnBack;

    private final Invoice invoice;

    public InvoiceForm(Stage stage, String screenPath, Invoice invoice) throws IOException {
        super(stage, screenPath);
        this.invoice = invoice;
        this.setBController(new PlaceOrderController());
        setInvoiceInfo();
        deliveryTimeLbl.setVisible(false);
        rushDeliveryInstructionLbl.setVisible(false);
        deliveryTime.setVisible(false);
        rushDeliveryInstruction.setVisible(false);
        regularDelivery.setSelected(true);
        rushDelivery.setSelected(false);

        regularDelivery.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    rushDelivery.setSelected(false);
                    invoice.getOrder().getDeliveryInfo().setRushDelivery(false);
                    updateInvoice();
                } else {
                    rushDelivery.setSelected(true);
                    deliveryTimeLbl.setVisible(false);
                    rushDeliveryInstructionLbl.setVisible(false);
                    deliveryTime.setVisible(false);
                    rushDeliveryInstruction.setVisible(false);
                }
            }
        });

        rushDelivery.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    String status = getBController().validateRushShipping(invoice);
                    regularDelivery.setSelected(false);
                    if (status.equals("EMPTY")) {
                        try {
                            PopupForm.error("Empty province");
                            rushDelivery.setSelected(false);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (status.equals("ADDRESS_NOT_SUPPORT")) {
                        try {
                            PopupForm.error("Address not support");
                            rushDelivery.setSelected(false);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (status.equals("PRODUCT_NOT_SUPPORT")) {
                        try {
                            PopupForm.error("Product not support");
                            rushDelivery.setSelected(false);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        regularDelivery.setSelected(false);
                        invoice.getOrder().getDeliveryInfo().setRushDelivery(true);
                        deliveryTimeLbl.setVisible(true);
                        rushDeliveryInstructionLbl.setVisible(true);
                        deliveryTime.setVisible(true);
                        rushDeliveryInstruction.setVisible(true);
                        updateInvoice();
                    }
                } else {
                    regularDelivery.setSelected(true);
                    deliveryTimeLbl.setVisible(false);
                    rushDeliveryInstructionLbl.setVisible(false);
                    deliveryTime.setVisible(false);
                    rushDeliveryInstruction.setVisible(false);
                }
            }
        });

        btnConfirm.setOnMouseClicked(e -> {
            LOGGER.info("Pay Order button clicked");
            try {
                requestToPayOrder();

            } catch (IOException | SQLException exp) {
                LOGGER.severe("Cannot pay the order, see the logs");
                exp.printStackTrace();
                throw new PaymentException(Arrays.toString(exp.getStackTrace()).replaceAll(", ", "\n"));
            }

        });
        btnBack.setOnMouseClicked(e -> {
            LOGGER.info("Back button clicked");
            if (getPreviousScreen() instanceof DeliveryForm deliveryForm) {
                deliveryForm.restoreDeliveryInfo(); // Khôi phục thông tin
                deliveryForm.show();
            }
        });
    }

    public PlaceOrderController getBController() {
        return (PlaceOrderController) super.getBController();
    }

    /**
     * The method updates invoice as user choose delivery method options
     */
    private void updateInvoice() {
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
        int newShippingFee = deliveryContext.calculateShippingFee(invoice.getOrder());
        invoice.setShippingFee(newShippingFee);
        invoice.setTotalAmount(invoice.getTotalPriceIncludingVAT() + newShippingFee);

        // Update labels
        shippingFees.setText(Utils.getCurrencyFormat(newShippingFee));
        total.setText(Utils.getCurrencyFormat(invoice.getTotalAmount()));
    }

    @SuppressWarnings("unchecked")
    private void setInvoiceInfo() {
        Shipment deliveryInfo = invoice.getOrder().getDeliveryInfo();
        name.setText(deliveryInfo.getName());
        phone.setText(deliveryInfo.getPhone());
        email.setText(deliveryInfo.getEmail());
        province.setText(deliveryInfo.getProvince());
        instructions.setText(deliveryInfo.getInstruction());
        if (deliveryInfo.getDistrict() != null) {
            System.out.println();
            address.setText(deliveryInfo.getAddress() + ", " + deliveryInfo.getDistrict());
        } else {
            System.out.println();
            address.setText(deliveryInfo.getAddress());
        }
        priceExcludingVAT.setText(Utils.getCurrencyFormat(invoice.getTotalPriceExcludingVAT()));
        priceIncludingVAT.setText(Utils.getCurrencyFormat(invoice.getTotalPriceIncludingVAT()));
        shippingFees.setText(Utils.getCurrencyFormat(invoice.getShippingFee()));
        total.setText(Utils.getCurrencyFormat(invoice.getTotalAmount()));
        invoice.getOrder().getlstOrderMedia().forEach(orderMedia -> {
            try {
                MediaInvoiceForm mis = new MediaInvoiceForm(Configs.INVOICE_MEDIA_SCREEN_PATH);
                mis.setOrderMedia(orderMedia);
                vboxItems.getChildren().add(mis.getContent());

            } catch (IOException | SQLException e) {
                System.err.println("errors: " + e.getMessage());
                throw new ProcessInvoiceException(e.getMessage());
            }

        });

    }

    /**
     * The method handles when user wants to pay order
     *
     * @throws SQLException
     * @throws IOException
     */
    public void requestToPayOrder() throws SQLException, IOException {
        try {
            // create placeOrderController and process the order
            IPayment vnPayService = new VnPaySubsystem();
            PaymentController payOrderController = new PaymentController(vnPayService);
            payOrderController.payOrder(invoice.getTotalAmount(), "Thanh toán hóa đơn AIMS");
            this.stage.close();
        } catch (MediaNotAvailableException e) {

        }
    }
}
