package isd.aims.main.strategy.impl;

import isd.aims.main.entity.order.Order;
import isd.aims.main.strategy.DeliveryFeeStrategy;

/**
 *  A concrete implementation of the {@link DeliveryFeeStrategy} interface that combines two different
 *  delivery fee strategies. This strategy calculates the shipping fee by summing the results
 *  of two individual strategies.
 */
public class JoinedDeliveryFeeStrategy implements DeliveryFeeStrategy {
    private DeliveryFeeStrategy strategy1;
    private DeliveryFeeStrategy strategy2;

    public JoinedDeliveryFeeStrategy(DeliveryFeeStrategy strategy1, DeliveryFeeStrategy strategy2) {
        this.strategy1 = strategy1;
        this.strategy2 = strategy2;
    }

    @Override
    public int calculateShippingFee(Order order) {
        return strategy1.calculateShippingFee(order) + strategy2.calculateShippingFee(order);
    }
}
