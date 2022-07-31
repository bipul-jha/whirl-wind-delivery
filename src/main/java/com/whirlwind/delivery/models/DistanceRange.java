package com.whirlwind.delivery.models;

public class DistanceRange {
    private final float minDistance;
    private final float maxDistance;

    public DistanceRange(float minDistance, float maxDistance) {
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
    }

    public float getMinDistance() {
        return minDistance;
    }

    public float getMaxDistance() {
        return maxDistance;
    }
}
