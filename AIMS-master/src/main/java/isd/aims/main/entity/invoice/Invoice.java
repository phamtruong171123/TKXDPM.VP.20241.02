package isd.aims.main.entity.invoice;


import isd.aims.main.context.DeliveryFeeContext;
import isd.aims.main.entity.order.Order;
import isd.aims.main.entity.order.OrderMedia;
import isd.aims.main.strategy.impl.JoinedDeliveryFeeStrategy;
import isd.aims.main.strategy.impl.RegularDeliveryFeeStrategy;
import isd.aims.main.strategy.impl.RushDeliveryFeeStrategy;
import isd.aims.main.utils.Configs;

/**
 * This class acts as a client in the Strategy Pattern.
 * It creates specific strategy objects and passes them to the context.
 * The context exposes a setter which lets clients replace the strategy associated with the context at runtime.
 */
public class Invoice {
    private Order order;
    private int totalAmount;
    private int shippingFee;
    private int totalPriceIncludingVAT;
    private int totalPriceExcludingVAT;
    private final DeliveryFeeContext deliveryContext;

    public Invoice(Order order) {
        this.order = order;
        this.deliveryContext = new DeliveryFeeContext();
        if (order.getDeliveryInfo().isRushDelivery()) {
            deliveryContext.setDeliveryStrategy(new JoinedDeliveryFeeStrategy(new RegularDeliveryFeeStrategy(), new RushDeliveryFeeStrategy()));
        } else {
            deliveryContext.setDeliveryStrategy(new RegularDeliveryFeeStrategy());
        }
        totalAmount = calculateTotalAmount();
        totalPriceIncludingVAT = calculateTotalPriceIncludingVAT();
        totalPriceExcludingVAT = calculateTotalPriceExcludingVAT();
        shippingFee = deliveryContext.calculateShippingFee(order);
    }

    /**
     * The method calculates total price excluding VAT
     * @return
     */
    private int calculateTotalPriceExcludingVAT() {
        double amount = 0;
        for (Object object : order.getlstOrderMedia()) {
            OrderMedia om = (OrderMedia) object;
            amount += om.getPrice() * om.getQuantity();
        }
        return (int) (amount);
    }

    /**
     * The method calculate total price including VAT
     * @return
     */
    private int calculateTotalPriceIncludingVAT() {
        return (int) (calculateTotalPriceExcludingVAT() * (1 + Configs.PERCENT_VAT / 100));
    }

    /**
     * The method calculates total amount (total price including VAT + shipping fee)
     * @return
     */
    private int calculateTotalAmount() {
        int shippingFee = deliveryContext.calculateShippingFee(order);
        return calculateTotalPriceIncludingVAT() + shippingFee;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalPriceIncludingVAT() {
        return totalPriceIncludingVAT;
    }

    public void setTotalPriceIncludingVAT(int totalPriceIncludingVAT) {
        this.totalPriceIncludingVAT = totalPriceIncludingVAT;
    }

    public int getTotalPriceExcludingVAT() {
        return totalPriceExcludingVAT;
    }

    public void setTotalPriceExcludingVAT(int totalPriceExcludingVAT) {
        this.totalPriceExcludingVAT = totalPriceExcludingVAT;
    }

    public int getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(int shippingFee) {
        this.shippingFee = shippingFee;
    }

    public void saveInvoice() {

    }

    /**
     * The method counts the number of rush delivery products in list
     * @return
     */
    public int getNumberOfRushDeliveryProduct() {
        int cnt = 0;
        for (Object object : getOrder().getlstOrderMedia()) {
            OrderMedia om = (OrderMedia) object;
            System.out.println(om.getMedia().isSupportRushDelivery());
            if (om.getMedia().isSupportRushDelivery()) cnt++;
        }
        return cnt;
    }
}
