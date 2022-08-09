package com.whirlwind.delivery.utility;

import com.whirlwind.delivery.models.Package;

import java.util.ArrayList;
import java.util.List;

public class Utility {

    public static List<List<Float>> getAllWeightsSubset(ArrayList<Float> weights){
        List<List<Float>> outer=new ArrayList<>();
        outer.add(new ArrayList<>());
        for (float num : weights) {
            int size=outer.size();
            for (int i=0;i<size;i++){
                List<Float> inner=new ArrayList<>(outer.get(i));
                inner.add(num);
                outer.add(inner);
            }
        }
        return outer;
    }

    public static boolean AreAllPackagesPickedForDelivery(Package[] packages){
        for(Package pkg : packages){
            if (!pkg.isPickedForDelivery())
                return false;
        }
        return true;
    }
}
