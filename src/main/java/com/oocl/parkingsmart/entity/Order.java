package com.oocl.parkingsmart.entity;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

@Entity
@Table(name = "order_list")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String carNumebr;
    private Long parkingLotId;
    private Long employeeId;
    @Column(nullable = false)
    private Integer type;
    @Column(nullable = false)
    private Long createAt;
    @Column(nullable = false)
    private Long appointTime;
    private Integer status = 0;

    public Order() {
    }

    public Order(String carNumebr, Integer type, Long createAt, Long appointTime) {
        this.carNumebr = carNumebr;
        this.type = type;
        this.createAt = createAt;
        this.appointTime = appointTime;
    }

    public Order(String carNumebr, Long parkingLotId, Long employeeId, Integer type, Long createAt, Long appointTime, Integer status) {
        this.carNumebr = carNumebr;
        this.parkingLotId = parkingLotId;
        this.employeeId = employeeId;
        this.type = type;
        this.createAt = createAt;
        this.appointTime = appointTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarNumebr() {
        return carNumebr;
    }

    public void setCarNumebr(String carNumebr) {
        this.carNumebr = carNumebr;
    }

    public Long getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(Long parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
}
