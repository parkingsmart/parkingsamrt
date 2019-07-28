package com.oocl.parkingsmart.controller;

import com.oocl.parkingsmart.entity.ParkingLot;
import com.oocl.parkingsmart.service.EmployeeService;
import com.oocl.parkingsmart.service.LoginService;
import com.oocl.parkingsmart.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

@CrossOrigin("*")
@RestController
@RequestMapping("/parking-lots")
public class ParkingLotController {
    private final Logger log = Logger.getLogger(this.getClass().getName());
    @Autowired
    private ParkingLotService parkingLotService;
    @GetMapping
    public ResponseEntity getAllParkingLots(@RequestParam(required = false, defaultValue = "1")int page) {
        HashMap parkingLotsMap = new HashMap();
        Long parkingLotsNum = parkingLotService.getAllParkingLotsNum();
        parkingLotsMap.put("AllParkingLotsNum", parkingLotsNum);
        List<ParkingLot> parkingLots = parkingLotService.getAllParkingLot(page);
        parkingLotsMap.put("AllParkingLot", parkingLots);
        return ResponseEntity.status(HttpStatus.OK).body(parkingLotsMap);
    }

    @PostMapping
    public ResponseEntity createAParkingLot(@RequestBody ParkingLot parkingLot){
        parkingLotService.addParkingLot(parkingLot);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping(path = "/{id}")
    public ResponseEntity putAPackageLot(@PathVariable Long id,@RequestBody ParkingLot parkingLot){
        ParkingLot savedparkingLot = parkingLotService.updateAParkingLot(id,parkingLot);
        return ResponseEntity.status(HttpStatus.OK).body(savedparkingLot);

    }
    @GetMapping("/{employeeid}")
    public ResponseEntity getAllParkingLotByEmployee(@PathVariable Long employeeid){
        List<ParkingLot> parkingLotList=parkingLotService.getAllParkingLotByEmploy(employeeid);
        return ResponseEntity.status(HttpStatus.OK).body(parkingLotList);
    }
}
