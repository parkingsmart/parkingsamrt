package com.oocl.parkingsmart.repository;

import com.oocl.parkingsmart.entity.Employee;
import com.oocl.parkingsmart.entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ParkingLotRepository  extends JpaRepository<ParkingLot,Long> {
    List<ParkingLot> findAllByManager(Long manager);

    @Modifying
    @Transactional
    @Query(value = "UPDATE ParkingLot SET manager=:manager WHERE id IN :ids")
    void updateManagerByIds(@Param("manager") Long manager, @Param("ids") List<Long> ids);

    ParkingLot findByName(String name);
}
