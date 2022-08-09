package com.whirlwind.delivery;

import com.whirlwind.delivery.models.*;
import com.whirlwind.delivery.models.Package;
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

        Package[] packages = new Package[2];
        packages[0] = new Package("PKG1", 50, 30, "OFR001");
        packages[1] = new Package("PKG2", 75, 125, "OFFR0008");

        Vehicle[] vehicles = new Vehicle[2];
        vehicles[0] = new Vehicle(70, 200);
        vehicles[0] = new Vehicle(70, 200);
        courierService = new CourierService(100, discounts, packages, vehicles);
    }

    @Test
    void shouldReturnZeroForDiscountedAmountIfOfferCodeIsIncorrect(){
        Package pkg = new Package("PKG1", 5, 5, "OFR004");

        courierService.computeDiscountedAndTotalAmount(pkg);

        assertEquals(0, pkg.getDiscountedAmount());
    }

    @Test
    void shouldReturnZeroIfOfferCodeIsValidButNotApplicable(){
        Package pkg = new Package("PKG1", 5, 5, "OFR001");

        courierService.computeDiscountedAndTotalAmount(pkg);

        assertEquals(0, pkg.getDiscountedAmount());
    }

    @Test
    void shouldComputeCorrectDiscountedAmountIfOfferCodeIsValidAndApplicable(){
        Package pkg = new Package("PKG3", 10, 100, "OFR003");

        courierService.computeDiscountedAndTotalAmount(pkg);

        assertEquals(35, pkg.getDiscountedAmount());
    }

    @Test
    void shouldComputeCorrectTotalAmountForAGivenPackage(){
        Package pkg = new Package("PKG3", 10, 100, "OFR003");

        courierService.computeDiscountedAndTotalAmount(pkg);

        assertEquals(665, pkg.getTotalCost());
    }
}