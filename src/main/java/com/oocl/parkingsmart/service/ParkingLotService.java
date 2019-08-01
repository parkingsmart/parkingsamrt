package com.oocl.parkingsmart.service;

import com.oocl.parkingsmart.entity.ParkingLot;
import com.oocl.parkingsmart.repository.EmployeeRepository;
import com.oocl.parkingsmart.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParkingLotService {
    @Autowired
    private ParkingLotRepository parkingLotRepository;
    public static final int PAGE_SIZE = 10;

    public Long getAllParkingLotsNum() {
        return parkingLotRepository.count();
    }

    public List<ParkingLot> getAllParkingLot(int page) {
        if(page == 0 ){
            List<ParkingLot> parkingLots = parkingLotRepository.findAll();
            return parkingLots;
        }else {
            PageRequest pageRequest = PageRequest.of(page - 1, PAGE_SIZE);
            List<ParkingLot> parkingLots = parkingLotRepository.findAll(pageRequest).getContent();
            return parkingLots;
        }
    }

    public void addParkingLot(ParkingLot parkingLot) {
        parkingLotRepository.save(parkingLot);
    }

    public ParkingLot updateAParkingLot(Long id, ParkingLot parkingLot) {
        ParkingLot targetParkingLot = parkingLotRepository.findById(id).get();
        targetParkingLot.setActive(parkingLot.isActive());
        targetParkingLot.setSize(parkingLot.getSize());
        ParkingLot savedParkingLot = parkingLotRepository.save(targetParkingLot);
        return savedParkingLot;
    }

    public List<ParkingLot> getAllParkingLotByEmploy(Long employeeid) {
        List<ParkingLot> parkingLotList = parkingLotRepository.findAll();
        return parkingLotList.stream().filter(item -> item.getManager().equals(employeeid))
                .collect(Collectors.toList());


    }

    public boolean isExistParkingLot(String parkingLotName) {
        Optional<ParkingLot>  parkingLot = Optional.of(parkingLotRepository.findByName(parkingLotName));
        if(parkingLot.isPresent()){
            return true;
        }else{
            return false;
        }
    }

    public ParkingLot getParkingLotById(Long id) {
        return parkingLotRepository.findById(id).get();
    }

}
