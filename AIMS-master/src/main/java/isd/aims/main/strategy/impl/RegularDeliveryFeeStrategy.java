package isd.aims.main.strategy.impl;

import isd.aims.main.entity.order.Order;
import isd.aims.main.entity.order.OrderMedia;
import isd.aims.main.strategy.DeliveryFeeStrategy;

/**
 * A concrete implementation of the {@link DeliveryFeeStrategy} interface that calculate shipping fee for regular deliveries.
 * This strategy considers factors such as the
 *  weight of the order,
 *  delivery location,
 *  and the total value of the order
 *  to determine the final shipping fee.
 */
public class RegularDeliveryFeeStrategy implements DeliveryFeeStrategy {
    @Override
    public int calculateShippingFee(Order order) {
        double regularShippingCost = 0;

        // Tính tổng giá trị các sản phẩm không hỗ trợ giao hàng nhanh
        double nonRushTotalValue = getNonRushOrderTotal(order);

        // Tính tổng giá trị tất cả sản phẩm
        double totalOrderValue = getTotalOrderValue(order);

        // Tính phí giao hàng dựa trên trọng lượng lớn nhất của toàn bộ đơn hàng
        double maxWeight = getMaxWeight(order);
        double baseCost = 0;
        double baseWeight = 0;
        double additionalCostPerHalfKg = 2500;

        if (order.getDeliveryInfo().isInnerOfHanoi() || "Hồ Chí Minh".equals(order.getDeliveryInfo().getProvince())) {
            baseCost = 22000;
            baseWeight = 3;
        } else {
            baseCost = 30000;
            baseWeight = 0.5;
        }

        if (maxWeight <= baseWeight) {
            regularShippingCost = baseCost;
        } else {
            regularShippingCost = baseCost + Math.ceil((maxWeight - baseWeight) * 2) * additionalCostPerHalfKg;
        }

        // Áp dụng miễn phí giao hàng thường nếu đủ điều kiện
        if (!order.getDeliveryInfo().isRushDelivery() && totalOrderValue > 100000) {
            regularShippingCost = Math.max(0, regularShippingCost - 25000);
        } else if (order.getDeliveryInfo().isRushDelivery() && nonRushTotalValue > 100000) {
            regularShippingCost = Math.max(0, regularShippingCost - 25000);
        }

        return (int) regularShippingCost;
    }

    // Tính tổng giá trị các sản phẩm không hỗ trợ giao hàng nhanh

    /**
     * The method calculates the price of products that don't support rush delivery
     * @param order
     * @return
     */
    private double getNonRushOrderTotal(Order order) {
        return order.getlstOrderMedia().stream()
                .filter(orderMedia -> !orderMedia.getMedia().isSupportRushDelivery())
                .mapToDouble(OrderMedia::getPrice)
                .sum();
    }

    // Tính tổng giá trị tất cả sản phẩm

    /**
     * The method calculates the price of all products
     * @param order
     * @return
     */
    private double getTotalOrderValue(Order order) {
        return order.getlstOrderMedia().stream()
                .mapToDouble(OrderMedia::getPrice)
                .sum();
    }

    // Tính trọng lượng lớn nhất của đơn hàng

    /**
     * The method calculates the weight of the heaviest item
     * @param order
     * @return
     */
    private double getMaxWeight(Order order) {
        double max = 0;
        for(Object object : order.getlstOrderMedia()){
            OrderMedia om = (OrderMedia) object;
            if(om.getMedia().getWeight() * om.getQuantity() > max) max = om.getMedia().getWeight() * om.getQuantity();
        }
        return max;

    }
}
