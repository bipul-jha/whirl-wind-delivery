package com.whirlwind.delivery;

import com.whirlwind.delivery.models.Discount;
import com.whirlwind.delivery.models.DistanceRange;
import com.whirlwind.delivery.models.Package;
import com.whirlwind.delivery.models.WeightRange;

import java.util.Scanner;

public class WhirlWindApplication {
    public static void main(String[] args) {

        Discount[] discounts = new Discount[3];
        discounts[0] = new Discount("OFR001", 10, new WeightRange(70,200), new DistanceRange(0,199));
        discounts[1] = new Discount("OFR002", 7, new WeightRange(100,250), new DistanceRange(50,150));
        discounts[2] = new Discount("OFR003", 5, new WeightRange(10,150), new DistanceRange(50,250));

        //Input
        Scanner scn = new Scanner(System.in);
        float baseDeliveryCost = scn.nextFloat();
        int noOfPackages = scn.nextInt();

        Package[] packages = new Package[noOfPackages];

        for(int i=0; i<noOfPackages; i++){
            String id = scn.nextLine();
            float weight = scn.nextFloat();
            float destinationDistance = scn.nextFloat();
            String offerCode = scn.nextLine();

            packages[i] = new Package(id, weight, destinationDistance, offerCode);
        }

        CourierService courierService = new CourierService(baseDeliveryCost, discounts);

        //Output
        for(int i=0; i<noOfPackages; i++){
            System.out.print(courierService.computeDiscountedAmount(packages[i]));
            System.out.print(courierService.computeTotalAmount(packages[i]));
            System.out.println();
        }
    }
}
