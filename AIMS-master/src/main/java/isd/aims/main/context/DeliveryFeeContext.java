package isd.aims.main.context;

import isd.aims.main.entity.order.Order;
import isd.aims.main.strategy.DeliveryFeeStrategy;

/**
 * This class serves as a context in the Strategy Pattern
 * for calculating shipping fees. It holds a reference to a {@link DeliveryFeeStrategy}
 * that determines the specific algorithm used to calculate the shipping fee.
 */
public class DeliveryFeeContext {
    private DeliveryFeeStrategy deliveryStrategy;
    public void setDeliveryStrategy(DeliveryFeeStrategy deliveryStrategy) {
        this.deliveryStrategy = deliveryStrategy;
    }
    public int calculateShippingFee(Order order){
        return deliveryStrategy.calculateShippingFee(order);
    }
}
