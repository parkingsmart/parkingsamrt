package com.oocl.parkingsmart.repository;

import com.oocl.parkingsmart.entity.Employee;
import com.oocl.parkingsmart.entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingLotRepository  extends JpaRepository<ParkingLot,Long> {
    List<ParkingLot> findAllByManager(Long manager);
}
