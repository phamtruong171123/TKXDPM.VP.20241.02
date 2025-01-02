package isd.aims.main.controller;

import isd.aims.main.entity.cart.Cart;
import isd.aims.main.entity.cart.CartMedia;
import isd.aims.main.entity.invoice.Invoice;
import isd.aims.main.entity.order.Order;
import isd.aims.main.entity.order.OrderMedia;
import isd.aims.main.entity.shipping.Shipment;
import isd.aims.main.exception.InvalidDeliveryInfoException;
import isd.aims.main.utils.Utils;
import isd.aims.main.views.popup.PopupForm;
import isd.aims.main.views.shipping.DeliveryForm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * This class controls the flow of place order usecase in our AIMS project
 * @author nguyenlm
 */
public class PlaceOrderController extends BaseController{

    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = Utils.getLogger(PlaceOrderController.class.getName());

    /**
     * This method checks the avalibility of product when user click PlaceOrder button
     * @throws SQLException
     */
    public void placeOrder() throws SQLException{
        Cart.getCart().checkAvailabilityOfProduct();
    }

    /**
     * This method creates the new Order based on the Cart
     * @return Order
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public Order createOrder() throws SQLException{
        Order order = new Order();
        for (Object object : Cart.getCart().getListMedia()) {
            CartMedia cartMedia = (CartMedia) object;
            OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(),
                                                   cartMedia.getQuantity(),
                                                   cartMedia.getPrice());
            order.getlstOrderMedia().add(orderMedia);
        }
        return order;
    }

    /**
     * This method creates the new Invoice based on order
     * @param order
     * @return Invoice
     */
    public Invoice createInvoice(Order order) {
        return new Invoice(order);
    }

    /**
     * This method takes responsibility for processing the shipping info from user
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    public void processDeliveryInfo(Shipment info) throws InterruptedException, IOException{
        LOGGER.info("Process Delivery Info");
        LOGGER.info(info.toString());
        if(!validateDeliveryInfo(info)){
            LOGGER.warning("Invalid delivery information provided: " + info);
            throw new InvalidDeliveryInfoException("Delivery information validation failed. Please check the provided details.");
        }
        LOGGER.info("Delivery information is valid. Proceeding with order processing...");
    }

    /**
   * The method validates the info, including name, phone, and address
   * @param deliveryInfo
   * @throws InterruptedException
   * @throws IOException
   */
    public boolean validateDeliveryInfo(Shipment deliveryInfo) throws InterruptedException, IOException{
        if (deliveryInfo == null) return false;
        String name = deliveryInfo.getName();
        String phone = deliveryInfo.getPhone();
        String email = deliveryInfo.getEmail();
        String address = deliveryInfo.getAddress();
        return validateName(name) && validatePhoneNumber(phone) && validateEmail(email) && validateAddress(address);
    }

    /**
     * The method validates the email
     * @param email
     * @return
     */
    private boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (email == null || email.isEmpty()) {
            return false;
        }
        return Pattern.matches(emailRegex, email);
    }


    /**
     * The method validates the phone number
     * @param phoneNumber
     * @return
     */
    public boolean validatePhoneNumber(String phoneNumber) {
        // Check if the phone number starts with '0'
        if (!phoneNumber.startsWith("0")) {
            return false;
        }

        // Remove spaces for convenience, as they are not part of the allowed format

        // Check if the phone number contains more than one type of separator
        if (phoneNumber.contains(".") && phoneNumber.contains("-")) {
            return false;
        }
        if (phoneNumber.contains("-") && phoneNumber.contains("/")) {
            return false;
        }
        if (phoneNumber.contains(".") && phoneNumber.contains("/")) {
            return false;
        }
        String cleanedPhoneNumber = phoneNumber.replaceAll("[\\s/.-]", "");
        // Check if the cleaned phone number has exactly 10 digits
        if (cleanedPhoneNumber.length() != 10) {
            return false;
        }
        // Ensure the phone number consists of only digits
        try {
            Long.parseLong(cleanedPhoneNumber); // try parsing as long to ensure no non-numeric characters
        } catch (NumberFormatException e) {
            return false;
        }
        // If all conditions passed, the phone number is valid
        return true;
    }

    /**
     * The method validates the customer's name
     * @param name
     * @return
     */
    public boolean validateName(String name) {
        if (name == null || name.isEmpty()) {
            return false;  // Return false if name is null
        }
        if (name.length() > 30) {
            return false;  // Return false if name length exceeds 30 characters
        }
        System.out.println(name.matches("^[a-zA-Z\\s]+$"));
        return name.matches("^[a-zA-Z\\s]+$");  // Check if the name contains only letters (a-z, A-Z)
    }

    /**
     * The method validates the customer's address
     * @param address
     * @return
     */
    public boolean validateAddress(String address) {
        if (address == null || address.isEmpty()) {
            return false;  // Address must not be null or empty
        }
        if (address.length() > 100) {
            return false;  // Address length must not exceed 100 characters
        }
        System.out.println(address.matches("[a-zA-Z0-9\\s]+"));
        return address.matches("[a-zA-Z0-9\\s]+");  // Only letters, digits, or slashes are allowed
    }

    /**
     * The method validates the rush delivery information
     * @param invoice
     * @return
     */
    public String validateRushShipping(Invoice invoice){
        if(invoice.getOrder().getDeliveryInfo().getProvince() == null ) return "EMPTY";
        if(!invoice.getOrder().getDeliveryInfo().validateRushDeliveryInfo()) return "ADDRESS_NOT_SUPPORT";
        if(invoice.getNumberOfRushDeliveryProduct() == 0) return "PRODUCT_NOT_SUPPORT";
        return "VALID";
    }
}
