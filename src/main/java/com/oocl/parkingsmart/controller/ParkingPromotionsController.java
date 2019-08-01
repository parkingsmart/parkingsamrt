package com.oocl.parkingsmart.controller;

import com.oocl.parkingsmart.entity.ParkingPromotions;
import com.oocl.parkingsmart.service.ParkingPromotionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/parking-promotions")
public class ParkingPromotionsController {

    @Autowired
    ParkingPromotionsService parkingPromotionsService;

    @GetMapping
    public ResponseEntity getAllPromotions() {
        List<ParkingPromotions> parkingPromotions = parkingPromotionsService.getAllPromotions();
        return ResponseEntity.status(HttpStatus.OK).body(parkingPromotions);
    }

    @GetMapping(value = "/{id}",params = {"amount"})
    public ResponseEntity getDiscountAmount(@PathVariable Long id,
                                            @RequestParam(name = "amount") Double amount){
        HashMap amountMap = parkingPromotionsService.saveMoney(id, amount);
        return ResponseEntity.ok().body(amountMap);
    }

}
