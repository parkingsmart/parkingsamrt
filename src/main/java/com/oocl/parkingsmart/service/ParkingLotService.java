package com.oocl.parkingsmart.service;

import com.oocl.parkingsmart.entity.ParkingLot;
import com.oocl.parkingsmart.repository.EmployeeRepository;
import com.oocl.parkingsmart.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingLotService {
    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public List<ParkingLot> getAllParkingLot() {
        List<ParkingLot> parkingLots =  parkingLotRepository.findAll();
        return parkingLots;

    }

    public void addParkingLot(ParkingLot parkingLot) {
        parkingLotRepository.save(parkingLot);
    }

    public ParkingLot updateAParkingLot(Long id,ParkingLot parkingLot) {
        ParkingLot targetParkingLot = parkingLotRepository.findById(id).get();
        targetParkingLot.setActive(parkingLot.isActive());
        targetParkingLot.setSize(parkingLot.getSize());
        ParkingLot savedParkingLot =  parkingLotRepository.save(targetParkingLot);
        return savedParkingLot;
    }
}
