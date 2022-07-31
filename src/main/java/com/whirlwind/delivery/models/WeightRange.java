package com.whirlwind.delivery.models;

public class WeightRange {
    private final float minWeight;
    private final float maxWeight;

    public WeightRange(float minWeight, float maxWeight) {
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
    }

    public float getMinWeight() {
        return minWeight;
    }

    public float getMaxWeight() {
        return maxWeight;
    }
}
