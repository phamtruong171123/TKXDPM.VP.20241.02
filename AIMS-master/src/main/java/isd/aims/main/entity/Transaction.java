package isd.aims.main.entity;

public class Transaction {
    private String orderID;
    private String createAt;
    private String content;

    public Transaction(String orderID, String createAt, String content) {
        this.orderID = orderID;
        this.createAt = createAt;
        this.content = content;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getCreateAt() {
        return createAt;
    }

    public String getContent() {
        return content;
    }
}
