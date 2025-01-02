package isd.aims.main.views.payment;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

import isd.aims.main.InterbankSubsystem.vnPay.VnPaySubsystemController;
import isd.aims.main.listener.TransactionResultListener;
import isd.aims.main.entity.invoice.Invoice;
import isd.aims.main.InterbankSubsystem.vnPay.VnPayConfig;
import isd.aims.main.utils.Configs;
import isd.aims.main.views.BaseForm;
import isd.aims.main.views.home.HomeForm;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class VNPay extends BaseForm {

	// @FXML
	// private Button btnConfirmPayment;

	// @FXML
	// private ImageView loadingImage;

	private Invoice invoice;
    private String paymentURL;
    @FXML
    private VBox vBox;
    private TransactionResultListener listener;

    public VNPay(Stage stage, String screenPath, String paymentURL, TransactionResultListener listener) throws IOException {
        super(stage, screenPath);
        this.paymentURL = paymentURL;
        this.listener = listener;
        WebView paymentView = new WebView();
        WebEngine webEngine = paymentView.getEngine();
        webEngine.load(paymentURL);

        webEngine.locationProperty().addListener((observable, oldValue, newValue) -> {
            // Xử lý khi URL thay đổi
            if (newValue.contains(VnPayConfig.vnp_ReturnUrl)) {

            }
        });
        vBox.getChildren().clear();
        vBox.getChildren().add(paymentView);
    }

}
