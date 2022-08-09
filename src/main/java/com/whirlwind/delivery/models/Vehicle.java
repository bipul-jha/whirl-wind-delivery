package com.whirlwind.delivery.models;

public class Vehicle {
    private final float maxSpeed;
    private final float maxCarriableWeight;
    private boolean isAvailable = true;
    private float deliveryTime = 0;

    public Vehicle(float maxSpeed, float maxCarriableWeight) {
        this.maxSpeed = maxSpeed;
        this.maxCarriableWeight = maxCarriableWeight;
    }

    public float getMaxCarriableWeight() {
        return maxCarriableWeight;
    }

    public void setDeliveryTime(float deliveryTime) {
        isAvailable = false;
        this.deliveryTime = deliveryTime;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public float getDeliveryTime() {
        return deliveryTime;
    }
}
