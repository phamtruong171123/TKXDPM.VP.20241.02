package isd.aims.main.strategy.impl;

import isd.aims.main.entity.order.Order;
import isd.aims.main.strategy.DeliveryFeeStrategy;

/**
 * A concrete implementation of the {@link DeliveryFeeStrategy} interface that calculate shipping fee for rush order items
 */
public class RushDeliveryFeeStrategy implements DeliveryFeeStrategy {
    private static final int RUSH_FEE_PER_ITEM = 10000; // Phí cố định cho mỗi mặt hàng
    @Override
    public int calculateShippingFee(Order order) {
        int numberOfRushDeliveryProducts = (int) order.getlstOrderMedia().stream()
                .filter(orderMedia -> orderMedia.getMedia().isSupportRushDelivery())
                .count();
        return numberOfRushDeliveryProducts * RUSH_FEE_PER_ITEM;
    }
}
