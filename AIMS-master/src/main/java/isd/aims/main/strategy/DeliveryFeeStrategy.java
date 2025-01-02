package isd.aims.main.strategy;

import isd.aims.main.entity.order.Order;

/**
 * This interface provides a common interface for all concrete strategies.
 * Currently, In AIMS case, there are 2 concrete strategies: RegularDeliveryFeeStrategy and RushDeliveryFeeStrategy
 * This interface declares a method which the context uses to execute a strategy.
 */
public interface DeliveryFeeStrategy {
    int calculateShippingFee(Order order);
}
