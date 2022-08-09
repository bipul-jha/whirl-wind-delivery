package com.whirlwind.delivery;

import com.whirlwind.delivery.models.Package;
import com.whirlwind.delivery.models.*;

import java.util.Scanner;

public class WhirlWindApplication {
    public static void main(String[] args) {

        Discount[] discounts = new Discount[3];
        discounts[0] = new Discount("OFR001", 10, new WeightRange(70,200), new DistanceRange(0,199));
        discounts[1] = new Discount("OFR002", 7, new WeightRange(100,250), new DistanceRange(50,150));
        discounts[2] = new Discount("OFR003", 5, new WeightRange(10,150), new DistanceRange(50,250));

        //Input
        System.out.println("Give inputs here!");
        Scanner scn = new Scanner(System.in);
        float baseDeliveryCost = Float.parseFloat(scn.nextLine());
        int noOfPackages = Integer.parseInt(scn.nextLine());

        Package[] packages = new Package[noOfPackages];

        for(int i=0; i<noOfPackages; i++){
            String id = scn.nextLine();
            float weight = Float.parseFloat(scn.nextLine());
            float destinationDistance = Float.parseFloat(scn.nextLine());
            String offerCode = scn.nextLine();

            packages[i] = new Package(id, weight, destinationDistance, offerCode);
        }

        int noOfVehicles = Integer.parseInt(scn.nextLine());
        Vehicle[] vehicles = new Vehicle[noOfVehicles];
        float maxSpeed = Float.parseFloat(scn.nextLine());
        float maxCarriableWeight = Float.parseFloat(scn.nextLine());
        for(int i = 0; i < noOfVehicles; i++){
            vehicles[i] = new Vehicle(maxSpeed, maxCarriableWeight);
        }

        CourierService courierService = new CourierService(baseDeliveryCost, discounts, packages, vehicles);
        courierService.computeEstimatedDeliveryTimeForPackages();

        //Output
        for(int i=0; i<noOfPackages; i++){
            System.out.print(packages[i].getId() + " ");
            courierService.computeDiscountedAndTotalAmount(packages[i]);
            System.out.print(packages[i].getDiscountedAmount() + " ");
            System.out.print(packages[i].getTotalCost() + " ");
            System.out.print(packages[i].getEstimatedDeliveryTime() + " ");
            System.out.println();
        }
    }
}
