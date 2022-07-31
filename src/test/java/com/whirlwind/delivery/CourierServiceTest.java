package com.whirlwind.delivery;

import com.whirlwind.delivery.models.Discount;
import com.whirlwind.delivery.models.DistanceRange;
import com.whirlwind.delivery.models.Package;
import com.whirlwind.delivery.models.WeightRange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourierServiceTest {
    private CourierService courierService;

    @BeforeEach
    public void setUp(){
        Discount[] discounts = new Discount[3];
        discounts[0] = new Discount("OFR001", 10, new WeightRange(70,200), new DistanceRange(0,199));
        discounts[1] = new Discount("OFR002", 7, new WeightRange(100,250), new DistanceRange(50,150));
        discounts[2] = new Discount("OFR003", 5, new WeightRange(10,150), new DistanceRange(50,250));

        courierService = new CourierService(100, discounts);
    }

    @Test
    void shouldReturnZeroForDiscountedAmountIfOfferCodeIsIncorrect(){
        Package pkg = new Package("PKG1", 5, 5, "OFR004");

        float discountedAmount = courierService.computeDiscountedAmount(pkg);

        assertEquals(0, discountedAmount);
    }

    @Test
    void shouldReturnZeroIfOfferCodeIsValidButNotApplicable(){
        Package pkg = new Package("PKG1", 5, 5, "OFR001");

        float discountedAmount = courierService.computeDiscountedAmount(pkg);

        assertEquals(0, discountedAmount);
    }

    @Test
    void shouldComputeCorrectDiscountedAmountIfOfferCodeIsValidAndApplicable(){
        Package pkg = new Package("PKG3", 10, 100, "OFR003");

        float discountedAmount = courierService.computeDiscountedAmount(pkg);

        assertEquals(35, discountedAmount);
    }

    @Test
    void shouldComputeCorrectTotalAmountForAGivenPackage(){
        Package pkg = new Package("PKG3", 10, 100, "OFR003");

        float discountedAmount = courierService.computeTotalAmount(pkg);

        assertEquals(700, discountedAmount);
    }
}