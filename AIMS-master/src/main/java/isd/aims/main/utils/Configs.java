package isd.aims.main.utils;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
/**
 * @author nguyenlm Contains the configs for AIMS Project
 */
public class Configs {

	// api constants
	public static final String GET_BALANCE_URL = "https://ecopark-system-api.herokuapp.com/api/card/balance/118609_group1_2020";
	public static final String GET_VEHICLECODE_URL = "https://ecopark-system-api.herokuapp.com/api/get-vehicle-code/1rjdfasdfas";
	public static final String PROCESS_TRANSACTION_URL = "https://ecopark-system-api.herokuapp.com/api/card/processTransaction";
	public static final String RESET_URL = "https://ecopark-system-api.herokuapp.com/api/card/reset";

	// demo data
	public static final String POST_DATA = "{"
			+ " \"secretKey\": \"BUXj/7/gHHI=\" ,"
			+ " \"transaction\": {"
			+ " \"command\": \"pay\" ,"
			+ " \"cardCode\": \"118609_group1_2020\" ,"
			+ " \"owner\": \"Group 1\" ,"
			+ " \"cvvCode\": \"185\" ,"
			+ " \"dateExpried\": \"1125\" ,"
			+ " \"transactionContent\": \"Pei debt\" ,"
			+ " \"amount\": 50000 "
			+ "}"
		+ "}";
	public static final String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiIxMTg2MDlfZ3JvdXAxXzIwMjAiLCJpYXQiOjE1OTkxMTk5NDl9.y81pBkM0pVn31YDPFwMGXXkQRKW5RaPIJ5WW5r9OW-Y";

	// database Configs
	public static final String DB_NAME = "aims";
	public static final String DB_USERNAME = System.getenv("DB_USERNAME");
	public static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

	public static String CURRENCY = "VND";
	public static float PERCENT_VAT = 10;

	// static resource
	public static final String IMAGE_PATH = "src/main/resources/isd/aims/main/fxml/";
	public static final String IMAGE_PATH_ICON = "src/main/resources/isd/aims/main/fxml/images";
	public static final String RESOURCES_PATH = "src/main/resources/isd/aims/main";
	public static final String INVOICE_SCREEN_PATH = "fxml/invoice.fxml";
	public static final String INVOICE_MEDIA_SCREEN_PATH = "fxml/media_invoice.fxml";
	public static final String PAYMENT_SCREEN_PATH = "fxml/payment.fxml";
	public static final String RESULT_SCREEN_PATH = "fxml/result.fxml";
	public static final String SPLASH_SCREEN_PATH = "fxml/splash.fxml";
	public static final String CART_SCREEN_PATH = "fxml/cart.fxml";
	public static final String VIEW_MEDIA_PATH = "fxml/view_media.fxml";
	public static final String SHIPPING_SCREEN_PATH = "fxml/shipping.fxml";
	public static final String CART_MEDIA_PATH = "fxml/media_cart.fxml";
	public static final String HOME_PATH  = "fxml/home.fxml";
	public static final String HOME_MEDIA_PATH = "fxml/media_home.fxml";
	public static final String POPUP_PATH = "fxml/popup.fxml";

	public static Font REGULAR_FONT = Font.font("Segoe UI", FontWeight.NORMAL, FontPosture.REGULAR, 24);

	public static String[] PROVINCES = { "An Giang", "Bà Rịa-Vũng Tàu", "Bình Dương", "Bình Phước", "Bình Thuận", "Bình Định", "Bạc Liêu", "Bắc Giang", "Bắc Kạn", "Bắc Ninh", "Bến Tre", "Cao Bằng", "Cà Mau", "Cần Thơ", "Gia Lai", "Hà Giang", "Hà Nam", "Hà Nội", "Hà Tĩnh", "Hòa Bình", "Hưng Yên", "Hải Dương", "Hải Phòng", "Hậu Giang", "Hồ Chí Minh", "Khánh Hòa", "Kiên Giang", "Kon Tum", "Lai Châu", "Long An", "Lâm Đồng", "Lạng Sơn", "Nam Định", "Nghệ An", "Ninh Bình", "Ninh Thuận", "Phú Thọ", "Phú Yên", "Quảng Bình", "Quảng Nam", "Quảng Ngãi", "Quảng Ninh", "Quảng Trị", "Sóc Trăng", "Sơn La", "Thanh Hóa", "Thái Bình", "Thái Nguyên", "Thừa Thiên-Huế", "Tiền Giang", "Trà Vinh", "Tuyên Quang", "Tây Ninh", "Vĩnh Long", "Vĩnh Phúc", "Yên Bái", "Điện Biên", "Đà Nẵng", "Đắk Lắk", "Đắk Nông", "Đồng Nai", "Đồng Tháp" };
	public static String[] DISTRICTS = { "Quận Ba Đình", "Quận Cầu Giấy", "Quận Đống Đa", "Quận Hai Bà Trưng", "Quận Hoàn Kiếm", "Quận Thanh Xuân", "Quận Hoàng Mai", "Quận Long Biên", "Quận Hà Đông", "Quận Tây Hồ", "Quận Nam Từ Liêm", "Quận Bắc Từ Liêm",
			"Huyện Thanh Trì", "Huyện Ba Vì", "Huyện Đan Phượng", "Huyện Gia Lâm", "Huyện Đông Anh","Huyện Thường Tín","Huyện Thanh Oai","Huyện Chương Mỹ","Huyện Hoài Đức","Huyện Mỹ Đức","Huyện Phúc Thọ","Huyện Thạch Thất","Huyện Quốc Oai","Huyện Phú Xuyên","Huyện Ứng Hòa","Huyện Mê Linh","Huyện Sóc Sơn", "Thị xã Sơn Tây" };}
