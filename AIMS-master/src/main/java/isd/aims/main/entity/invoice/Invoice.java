package isd.aims.main.entity.invoice;


import isd.aims.main.entity.order.Order;
import isd.aims.main.entity.order.OrderMedia;
import isd.aims.main.utils.Configs;

public class Invoice {

    private Order order;
    private int totalAmount;
    private int shippingFee;
    private int totalPriceIncludingVAT;
    private int totalPriceExcludingVAT;


    public Invoice(Order order){
        this.order = order;
        totalAmount = calculateTotalAmount();
        totalPriceIncludingVAT = calculateTotalPriceIncludingVAT();
        totalPriceExcludingVAT = calculateTotalPriceExcludingVAT();
        shippingFee = calculateShippingFee(order);
    }

    private int calculateTotalPriceExcludingVAT() {
        double amount = 0;
        for (Object object : order.getlstOrderMedia()) {
            OrderMedia om = (OrderMedia) object;
            amount += om.getPrice() * om.getQuantity();
        }
        return (int) (amount);
    }

    private int calculateTotalPriceIncludingVAT() {
        return (int) (calculateTotalPriceExcludingVAT() * (1 + Configs.PERCENT_VAT/100));
    }

    private int calculateTotalAmount() {
        int shippingFee = calculateShippingFee(order);
        System.out.println(shippingFee);
        System.out.println(shippingFee);
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


    public void saveInvoice(){
        
    }
    public int getNumberOfRushDeliveryProduct(){
        int cnt = 0;
        for(Object object : getOrder().getlstOrderMedia()){
            OrderMedia om = (OrderMedia) object;
            System.out.println(om.getMedia().isSupportRushDelivery());
            if(om.getMedia().isSupportRushDelivery()) cnt++;
        }
        return cnt;
    }
    /**
     * This method calculates the shipping fees of order
     * @param
     * @return shippingFee
     */
    public int calculateShippingFee(Order order) {
        double regularShippingCost = 0;
        double rushShippingCost = 0;

        // Tính tổng giá trị các sản phẩm không hỗ trợ giao hàng nhanh (dành cho regular delivery khi chọn rush delivery)
        double nonRushTotalValue = getNonRushOrderTotal();

        // Tính tổng giá trị tất cả sản phẩm (dành cho regular delivery nếu không chọn rush delivery)
        double totalOrderValue = getTotalOrderValue();

        // Tính phí giao hàng dựa trên trọng lượng lớn nhất của toàn bộ đơn hàng
        double maxWeight = getMaxWeight();
        double baseCost = 0;
        double baseWeight = 0;
        double additionalCostPerHalfKg = 2500;

        if (order.getDeliveryInfo().isInnerOfHanoi() || order.getDeliveryInfo().getProvince().equals("Hồ Chí Minh")) {
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

        // Tính phí giao hàng nhanh
        if (order.getDeliveryInfo().isRushDelivery()) {
            // Thêm phí cố định cho mỗi sản phẩm giao hàng nhanh
            rushShippingCost += 10000 * getNumberOfRushDeliveryProduct();
        }

        // Trả về tổng phí giao hàng
        return (int) (regularShippingCost + rushShippingCost);
    }


    private double getNonRushOrderTotal() {
        return getOrder().getlstOrderMedia().stream()
                .filter(orderMedia -> !orderMedia.getMedia().isSupportRushDelivery())
                .mapToDouble(OrderMedia::getPrice)
                .sum();
    }
    private double getTotalOrderValue() {
        // Tính tổng giá trị tất cả sản phẩm trong đơn hàng
        return getOrder().getlstOrderMedia().stream()
                .mapToDouble(OrderMedia::getPrice)
                .sum();
    }

    public double getMaxWeight(){
        double max = 0;
        for(Object object : getOrder().getlstOrderMedia()){
            OrderMedia om = (OrderMedia) object;
            if(om.getMedia().getWeight() * om.getQuantity() > max) max = om.getMedia().getWeight() * om.getQuantity();
        }
        return max;
    }
}
