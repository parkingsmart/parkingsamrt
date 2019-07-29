package com.oocl.parkingsmart.entity;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

@Entity
@Table(name = "order_list")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long employeeId;
    @Column(nullable = false)
    private Long userId;
    private Long parkingLotId;
    @Column(nullable = false)
    private String carNumebr;
    private Long endTime;
    @Column(nullable = false)
    private Long startTime;
    @Column(nullable = false)
    private Long appointTime;
    @Column(nullable = false)
    private String appointAddress;
    private Integer status = 0;

    public Order() {
    }

    public Order(Long userId, String carNumebr, Long startTime, Long appointTime, String appointAddress) {
        this.userId = userId;
        this.carNumebr = carNumebr;
        this.startTime = startTime;
        this.appointTime = appointTime;
        this.appointAddress = appointAddress;
    }

    public Order(Long id, Long employeeId, Long userId, Long parkingLotId, String carNumebr, Long endTime, Long startTime, Long appointTime, String appointAddress, Integer status) {
        this.id = id;
        this.employeeId = employeeId;
        this.userId = userId;
        this.parkingLotId = parkingLotId;
        this.carNumebr = carNumebr;
        this.endTime = endTime;
        this.startTime = startTime;
        this.appointTime = appointTime;
        this.appointAddress = appointAddress;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(Long parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public String getCarNumebr() {
        return carNumebr;
    }

    public void setCarNumebr(String carNumebr) {
        this.carNumebr = carNumebr;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getAppointTime() {
        return appointTime;
    }

    public void setAppointTime(Long appointTime) {
        this.appointTime = appointTime;
    }

    public String getAppointAddress() {
        return appointAddress;
    }

    public void setAppointAddress(String appointAddress) {
        this.appointAddress = appointAddress;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
