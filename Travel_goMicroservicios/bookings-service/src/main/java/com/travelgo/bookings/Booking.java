package com.travelgo.bookings;

import java.time.LocalDate;

public class Booking {

    private Long id;
    private Long userId;
    private String destination;
    private LocalDate travelDate;
    private Double price;
    private String status;

    public Booking() {
    }

    public Booking(Long id, Long userId, String destination, LocalDate travelDate, Double price, String status) {
        this.id = id;
        this.userId = userId;
        this.destination = destination;
        this.travelDate = travelDate;
        this.price = price;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(LocalDate travelDate) {
        this.travelDate = travelDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
