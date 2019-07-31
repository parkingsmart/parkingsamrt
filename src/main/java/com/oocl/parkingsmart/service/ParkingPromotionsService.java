package com.oocl.parkingsmart.service;

import com.oocl.parkingsmart.entity.ParkingPromotions;
import com.oocl.parkingsmart.repository.ParkingPromotionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

@Service
public class ParkingPromotionsService {

    @Autowired
    ParkingPromotionsRepository parkingPromotionsRepository;

    public List<ParkingPromotions> getAllPromotions() {
        return parkingPromotionsRepository.findAll();
    }

    public HashMap saveMoney(Long id, Double money) {
        HashMap amountMap = new HashMap();
        ParkingPromotions parkingPromotions = parkingPromotionsRepository.findById(id).get();
        Integer type = parkingPromotions.getType();
        Double amount = parkingPromotions.getAmount();
        DecimalFormat df = new DecimalFormat("0.0");
        if (type.equals(0)){
            amountMap.put("discount", df.format(money - amount * money));
            amountMap.put("discountAmount", df.format(money * amount));
        }else if (type.equals(1)){
            amountMap.put("discount", df.format(amount));
            if ( money-amount < 0){
                amountMap.put("discountAmount", df.format(0));
            }else {
                amountMap.put("discountAmount", df.format(money - amount));
            }
        }
        return amountMap;
    }
}
