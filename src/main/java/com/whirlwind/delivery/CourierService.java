package com.whirlwind.delivery;

import com.whirlwind.delivery.models.Discount;
import com.whirlwind.delivery.models.Package;

public class CourierService {
    private final float baseDeliveryCost;
    private final Discount[] discounts;

    public CourierService(float baseDeliveryCost, Discount[] discounts) {
        this.baseDeliveryCost = baseDeliveryCost;
        this.discounts = discounts;
    }

    public float computeDiscountedAmount(Package pkg){
        for(Discount discount : discounts){
            if(pkg.getOfferCode().equals(discount.getCode()) && pkg.isOfferApplicable(discount)){
                float discountedAmount = (computeTotalAmount(pkg) * discount.getPercentage()) / (100);
                pkg.setDiscountedAmount(discountedAmount);
                return discountedAmount;
            }
        }
        return 0;
    }

    public float computeTotalAmount(Package pkg){
        float totalAmount = baseDeliveryCost + (pkg.getWeight() * 10) + (pkg.getDestinationDistance() * 5);
        pkg.setTotalCost(totalAmount);
        return totalAmount;
    }
}
