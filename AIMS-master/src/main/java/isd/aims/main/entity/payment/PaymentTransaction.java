package isd.aims.main.entity.payment;

import isd.aims.main.InterbankSubsystem.IPayment;
import isd.aims.main.InterbankSubsystem.vnPay.VnPaySubsystemController;
import isd.aims.main.controller.BaseController;
import isd.aims.main.entity.cart.Cart;
import isd.aims.main.listener.TransactionResultListener;

import java.io.IOException;
import java.sql.SQLException;


/**
 * This {@code PaymentController} class control the flow of the payment process
 * in our AIMS Software.
 *
 */
class PaymentController extends BaseController {

	private IPayment paymentService;
	private int amount;
	private String orderInfo;

	public PaymentController(IPayment vnPayService) {
		this.paymentService = vnPayService;
	}

	/**
	 * Generate VNPay payment URL
	 */

	public void payOrder(int amount, String orderInfo, Object cardDetails) {

    }

	public void onTransactionCompleted() {

	}

	public void emptyCart(){
        Cart.getCart().emptyCart();
    }
}
