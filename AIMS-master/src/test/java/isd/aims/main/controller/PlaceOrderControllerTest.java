package isd.aims.main.controller;

import isd.aims.main.entity.media.*;
import isd.aims.main.entity.order.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaceOrderControllerTest {

    private PlaceOrderController placeOrderController;

    @BeforeEach
    void setUp() throws Exception {
        placeOrderController = new PlaceOrderController();
    }

    @ParameterizedTest
    @CsvSource({
            "0123456789,true",
            "01234,false",
            "abc123,false",
            "1234567890,false"
    })
    void validatePhoneNumber(String phone, boolean expected) {
        // when
        boolean isValid = placeOrderController.validatePhoneNumber(phone);

        // then
        assertEquals(expected, isValid);
    }

    @ParameterizedTest
    @CsvSource({
            "nguyenlm,true",
            "nguyen01234,false",
            "$#nguyen,false",
            ",false"
    })
    void validateName(String name, boolean expected) {
        // when
        boolean isValid = placeOrderController.validateName(name);

        // then
        assertEquals(expected, isValid);
    }

    @ParameterizedTest
    @CsvSource({
            "hanoi,true",
            "so 15 Hai Ba Trung Ha Noi,true",
            "$#Hanoi,false",
            ",false"
    })
    void validateAddress(String address, boolean expected) {
        // when
        boolean isValid = placeOrderController.validateAddress(address);

        // then
        assertEquals(expected, isValid);
    }

    @Test
    void calculateShippingFee() {
        try {
            ArrayList<OrderMedia> lstOrderMedia = new ArrayList();
            lstOrderMedia.add(new OrderMedia(new Book(), 5, 500));
            lstOrderMedia.add(new OrderMedia(new CD(), 1, 200));
            lstOrderMedia.add(new OrderMedia(new DVD(), 2, 300));

            // when
            int fees = placeOrderController.calculateShippingFee(new Order(lstOrderMedia));

            // then
            assertEquals(50, fees);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}