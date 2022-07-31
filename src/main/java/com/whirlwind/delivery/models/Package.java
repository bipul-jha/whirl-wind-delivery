package com.whirlwind.delivery.models;

public class Package {
    private final String id;
    private final float weight;
    private final float destinationDistance;
    private final String offerCode;
    private float totalCost;
    private float discountedAmount;

    public Package(String id, float weight, float destinationDistance, String offerCode) {
        this.id = id;
        this.weight = weight;
        this.destinationDistance = destinationDistance;
        this.offerCode = offerCode;
        this.totalCost = 0;
        this.discountedAmount = 0;
    }

    public boolean isOfferApplicable(Discount discount) {
        return  (isWeightInRangeForDiscount(discount) && isDistanceInRangeForDiscount(discount));
    }

    private boolean isWeightInRangeForDiscount(Discount discount) {
        return (this.weight >= discount.getWeightRange().getMinWeight() &&
                this.weight <= discount.getWeightRange().getMaxWeight());
    }

    private boolean isDistanceInRangeForDiscount(Discount discount) {
        return (this.destinationDistance >= discount.getDistanceRange().getMinDistance() &&
                this.destinationDistance <= discount.getDistanceRange().getMaxDistance());
    }

    public String getOfferCode() {
        return offerCode;
    }

    public Float getWeight() {
        return weight;
    }

    public Float getDestinationDistance() {
        return destinationDistance;
    }

    public String getId() {
        return id;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public void setDiscountedAmount(float discountedAmount) {
        this.discountedAmount = discountedAmount;
    }
}
