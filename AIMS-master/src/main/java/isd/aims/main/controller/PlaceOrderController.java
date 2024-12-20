package isd.aims.main.controller;

import isd.aims.main.entity.cart.Cart;
import isd.aims.main.entity.cart.CartMedia;
import isd.aims.main.entity.invoice.Invoice;
import isd.aims.main.entity.order.Order;
import isd.aims.main.entity.order.OrderMedia;
import isd.aims.main.utils.Utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

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
    public void processDeliveryInfo(HashMap info) throws InterruptedException, IOException{
        LOGGER.info("Process Delivery Info");
        LOGGER.info(info.toString());
        validateDeliveryInfo(info);
    }

    /**
   * The method validates the info
   * @param info
   * @throws InterruptedException
   * @throws IOException
   */
    public void validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException{

    }

    public boolean validatePhoneNumber(String phoneNumber) {
    	// Check the phoneNumber has 10 digits
        if (phoneNumber.length() != 10) return false;

        // Check the phoneNumber starts with 0
        if (!phoneNumber.startsWith("0")) return false;

        // Check the phoneNumber contains only number
        try {
            Integer.parseInt(phoneNumber);
        } catch (NumberFormatException e) {
            return  false;
        }

        return true;
    }

    public boolean validateName(String name) {
        // Check null
        if (name == null) return false;

        // Check name has maximum of 30 characters
        if (name.length() > 30) return false;

        // Check if only letters are contained
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) continue;
            return false;
        }
    	return true;
    }

    public boolean validateAddress(String address) {
        // Check null
        if (address == null) return false;

        // Check address has maximum of 100 characters
        if (address.length() > 100) return false;

        // Check if only letters, digits and slashes are contained
        for (int i = 0; i < address.length(); i++) {
            char c = address.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) continue;
            if (c >= '0' && c <= '9') continue;
            if (c == '/' || c == '\\') continue;
            if (c == ' ') continue;
            return false;
        }
        return true;
    }

    /**
     * This method calculates the shipping fees of order
     * @param order
     * @return shippingFee
     */
    public int calculateShippingFee(Order order){
        Random rand = new Random();
        int fees = (int)( ( (rand.nextFloat() * 10) / 100 ) * order.getAmount() );
        LOGGER.info("Order Amount: " + order.getAmount() + " -- Shipping Fees: " + fees);
        return fees;
    }
}
