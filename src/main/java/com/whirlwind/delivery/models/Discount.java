package com.whirlwind.delivery.models;

public class Discount {
    private final String code;
    private final float percentage;
    private final WeightRange weightRange;
    private final DistanceRange distanceRange;

    public Discount(String code, float percentage, WeightRange weightRange, DistanceRange distanceRange) {
        this.code = code;
        this.percentage = percentage;
        this.weightRange = weightRange;
        this.distanceRange = distanceRange;
    }

    public String getCode() {
        return code;
    }

    public WeightRange getWeightRange() {
        return weightRange;
    }

    public DistanceRange getDistanceRange() {
        return distanceRange;
    }

    public float getPercentage() {
        return percentage;
    }
}
