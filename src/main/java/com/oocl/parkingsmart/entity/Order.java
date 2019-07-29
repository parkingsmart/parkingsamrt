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
    private String carNumber;

    private Long endAt;

    @Column(nullable = false)
    private Long createAt;
    @Column(nullable = false)
    private Long appointTime;
    @Column(nullable = false)
    private String appointAddress;
    private Integer status = 0;

    public Order() {
    }

    public Order(Long employeeId, Long userId, Long parkingLotId, String carNumber, Long endAt, Long createAt, Long appointTime, String appointAddress, Integer status) {
        this.employeeId = employeeId;
        this.userId = userId;
        this.parkingLotId = parkingLotId;
        this.carNumber = carNumber;
        this.endAt = endAt;
        this.createAt = createAt;
        this.appointTime = appointTime;
        this.appointAddress = appointAddress;
        this.status = status;
    }

    public Order(String carNumber, Long createAt, Long appointTime, String appointAddress, Long userId) {
        this.userId = userId;
        this.createAt = createAt;
        this.appointTime = appointTime;
        this.carNumber = carNumber;
        this.appointAddress = appointAddress;
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

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public Long getEndAt() {
        return endAt;
    }

    public void setEndAt(Long endAt) {
        this.endAt = endAt;
    }

    public Long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
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
