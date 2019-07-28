package com.oocl.parkingsmart.entity;

import javax.persistence.*;

@Entity
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false,unique = true)
    private String name;
    @Column(nullable = false)
    private int size;
    private boolean isActive;
    private int parkedNum;
    private Long manager;

    public ParkingLot() {
    }

    public ParkingLot(String name, int size, Long manager) {
        this.name = name;
        this.size = size;
        this.manager = manager;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getParkedNum() {
        return parkedNum;
    }

    public void setParkedNum(int parkedNum) {
        this.parkedNum = parkedNum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getManager() {
        return manager;
    }

    public void setManager(Long manager) {
        this.manager = manager;
    }
}
