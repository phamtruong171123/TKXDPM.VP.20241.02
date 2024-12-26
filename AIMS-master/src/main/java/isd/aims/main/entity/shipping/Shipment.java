package isd.aims.main.entity.shipping;

import java.util.Arrays;
import java.util.List;

public class Shipment {
    private String name;
    private String phone;
    private String province;
    private String district;
    private String address;
    private String instruction;
    private boolean isRushDelivery;
    private String rushDeliveryTime;
    private String rushDeliveryInstruction;

    public Shipment(String name, String phone, String province, String address, String instruction, String district) {
        this.name = name;
        this.phone = phone;
        this.province = province;
        this.address = address;
        this.instruction = instruction;
        this.district = district;
//        this.isRushDelivery = isRushDelivery;
//        this.rushDeliveryTime = rushDeliveryTime;
//        this.rushDeliveryInstruction = rushDeliveryInstruction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public boolean isRushDelivery() {
        return isRushDelivery;
    }

    public void setRushDelivery(boolean rushDelivery) {
        isRushDelivery = rushDelivery;
    }

    public String getRushDeliveryTime() {
        return rushDeliveryTime;
    }

    public void setRushDeliveryTime(String rushDeliveryTime) {
        this.rushDeliveryTime = rushDeliveryTime;
    }

    public String getRushDeliveryInstruction() {
        return rushDeliveryInstruction;
    }

    public void setRushDeliveryInstruction(String rushDeliveryInstruction) {
        this.rushDeliveryInstruction = rushDeliveryInstruction;
    }

//    public void validateDeliveryInfo(){
//        // TODO: implement later on
//    }
    public boolean validateRushDeliveryInfo(){
        if (province == null || province.isEmpty())
            return false;
        if(province.equals("Hà Nội"))
            return isInnerOfHanoi();
        return false;
    }

//    public Shipment createNewShipment(){
//        // TODO: implement later on
//        return new Shipment();
//    }
    public boolean isInnerOfHanoi() {
        // Danh sách các quận nội thành Hà Nội
//        List<String> innerDistricts = Arrays.asList(
//                "hoan kiem", "ba dinh", "dong da", "hai ba trung",
//                "cau giay", "tay ho", "thanh xuan", "hoang mai",
//                "ha dong", "long bien", "bac tu liem", "nam tu liem"
//        );

        // Lấy thông tin quận (đã chuyển thành chữ thường để so sánh không phân biệt hoa/thường)
        String district = getDistrict();
        if(district != null){
            if(district.contains("Quận")) return true;
        }
        return false;
        // Kiểm tra xem district có nằm trong danh sách các quận nội thành không
//        System.out.println(innerDistricts.contains(address));
//        boolean isInnerOfHanoi = false;
//        for(String item : innerDistricts){
//            if(address.contains(item)) isInnerOfHanoi = true;
//        }
//        return isInnerOfHanoi;
    }

    @Override
    public String toString() {
        return "Shipment{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", province='" + province + '\'' +
                ", address='" + address + '\'' +
                ", instruction='" + instruction + '\'' +
                ", isRushDelivery=" + isRushDelivery +
                ", rushDeliveryTime='" + rushDeliveryTime + '\'' +
                ", rushDeliveryInstruction='" + rushDeliveryInstruction + '\'' +
                '}';
    }
}
