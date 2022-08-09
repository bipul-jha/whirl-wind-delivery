package com.whirlwind.delivery;

import com.whirlwind.delivery.models.Discount;
import com.whirlwind.delivery.models.Package;
import com.whirlwind.delivery.models.Vehicle;
import com.whirlwind.delivery.utility.Utility;

import java.util.*;

public class CourierService {
    private final float baseDeliveryCost;
    private final Discount[] discounts;
    private final Package[] packages;
    private final Vehicle[] vehicles;

    public CourierService(float baseDeliveryCost, Discount[] discounts, Package[] packages, Vehicle[] vehicles) {
        this.baseDeliveryCost = baseDeliveryCost;
        this.discounts = discounts;
        this.packages = packages;
        this.vehicles = vehicles;
    }

    public void computeDiscountedAndTotalAmount(Package pkg){
        float totalAmount = computeTotalAmount(pkg);
        for(Discount discount : discounts){
            if(pkg.getOfferCode().equals(discount.getCode()) && pkg.isOfferApplicable(discount)){
                float discountedAmount = (totalAmount * discount.getPercentage()) / (100);
                pkg.setDiscountedAmount(discountedAmount);
                pkg.setTotalCost(totalAmount - discountedAmount);
                return;
            }
        }
        pkg.setTotalCost(totalAmount);
    }

    private float computeTotalAmount(Package pkg){
        float totalAmount = baseDeliveryCost + (pkg.getWeight() * 10) + (pkg.getDestinationDistance() * 5);
        pkg.setTotalCost(totalAmount);
        return totalAmount;
    }

    public void computeEstimatedDeliveryTimeForPackages(){
        float maxCarriableWeight = 0;
        for(Vehicle vehicle: vehicles){
            maxCarriableWeight = vehicle.getMaxCarriableWeight();
            break;
        }

        ArrayList<Float> weightsArray = new ArrayList<>();
        for(Package pkg : packages){
            weightsArray.add(pkg.getWeight());
        }

        List<List<Float>> weightsSubsets = Utility.getAllWeightsSubset(weightsArray);
        HashMap<List<Float>, Float> weightsSumMap = new HashMap<>();
        for(List<Float> weightSubset : weightsSubsets){
            float weightsSubsetSum = 0;
            for(float weight : weightSubset){
                weightsSubsetSum += weight;
            }
            if(weightsSubsetSum <= maxCarriableWeight)
            weightsSumMap.put(weightSubset, weightsSubsetSum);
        }

        while(!Utility.AreAllPackagesPickedForDelivery(packages)){
            List<Float> weightSubset = new ArrayList<>();
            float maxWeightsSum = Float.MIN_VALUE;
            for (Map.Entry<List<Float>, Float> pair : weightsSumMap.entrySet()) {
                if (pair.getValue() > maxWeightsSum && !containsPickedPackages(packages, pair.getKey())) {
                    maxWeightsSum = pair.getValue();
                    weightSubset = pair.getKey();
                }
            }
            pickPackagesForDelivery(weightSubset);
            weightsSumMap.remove(weightSubset);
        }
    }

    private boolean containsPickedPackages(Package[] packages, List<Float> weightsToCompare) {
        for(Package pkg: packages){
            if(pkg.isPickedForDelivery() && weightsToCompare.contains(pkg.getWeight())){
                return true;
            }
        }
        return false;
    }

    private void pickPackagesForDelivery(List<Float> weightSubset){
        List<Package> packagesForShipment = new ArrayList<>();
        for(Float weight : weightSubset){
            //Pick the right package with the current weight
            //TODO: There can be multiple packages with the same weight, handle that as well
            for(Package pkg : packages){
                if(pkg.getWeight().equals(weight)){
                    packagesForShipment.add(pkg);
                }
            }
        }
        shipAllPackagesInOneShipment(packagesForShipment);
    }

    private void shipAllPackagesInOneShipment(List<Package> packages){
        Vehicle availableVehicle = isAnyVehicleAvailable();
        float estimatedDeliveryTimeOfTheFarthestPackage = Float.MIN_VALUE;
        if(availableVehicle != null){
            for(Package pkg : packages){
                pkg.setPickedForDelivery();
                float estimatedDeliveryTimeForPackage = (float) (Math.round(pkg.getDestinationDistance()/availableVehicle.getMaxSpeed() * 100.0) / 100.0);
                pkg.setEstimatedDeliveryTime(estimatedDeliveryTimeForPackage);
                if(estimatedDeliveryTimeForPackage > estimatedDeliveryTimeOfTheFarthestPackage)
                    estimatedDeliveryTimeOfTheFarthestPackage = estimatedDeliveryTimeForPackage;
            }
            availableVehicle.setDeliveryTime(2 * estimatedDeliveryTimeOfTheFarthestPackage);
        }
        else{
            Vehicle fastReturningVehicle = null;
            float minTimeNeededForVehicleToReturn = Float.MAX_VALUE;
            for(Vehicle vehicle : vehicles){
                if(vehicle.getDeliveryTime() < minTimeNeededForVehicleToReturn){
                    minTimeNeededForVehicleToReturn = vehicle.getDeliveryTime();
                    fastReturningVehicle = vehicle;
                }
            }

            for(Package pkg : packages){
                pkg.setPickedForDelivery();
                float estimatedDeliveryTimeForPackage = (float) (Math.round(pkg.getDestinationDistance()/fastReturningVehicle.getMaxSpeed() * 100.0) / 100.0);
                pkg.setEstimatedDeliveryTime(minTimeNeededForVehicleToReturn + estimatedDeliveryTimeForPackage);
                if(estimatedDeliveryTimeForPackage > estimatedDeliveryTimeOfTheFarthestPackage)
                    estimatedDeliveryTimeOfTheFarthestPackage = estimatedDeliveryTimeForPackage;
            }
            fastReturningVehicle.setDeliveryTime(minTimeNeededForVehicleToReturn + (2 * estimatedDeliveryTimeOfTheFarthestPackage));
        }
    }

    private Vehicle isAnyVehicleAvailable() {
        for(Vehicle vehicle : vehicles){
            if(vehicle.isAvailable())
            return vehicle;
        }
        return null;
    }
}
