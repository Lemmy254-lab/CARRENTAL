package com.carrental.models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Rental class representing a rental transaction
 * Links Customer and Car with rental details
 */
public class Rental {
    // Private attributes
    private String rentalId;
    private Customer customer;
    private Car car;
    private LocalDate startDate;
    private LocalDate endDate;
    private int numberOfDays;
    private double totalCost;
    private String status; // ACTIVE, COMPLETED, CANCELLED
    
    /**
     * Constructor for creating a new rental
     */
    public Rental(String rentalId, Customer customer, Car car, 
                 LocalDate startDate, LocalDate endDate) {
        this.rentalId = rentalId;
        this.customer = customer;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfDays = calculateDays();
        this.totalCost = car.calculateRentalCost(numberOfDays);
        this.status = "ACTIVE";
    }
    
    /**
     * Calculate number of days between start and end date
     */
    private int calculateDays() {
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        if (days <= 0) {
            throw new IllegalArgumentException("End date must be after start date");
        }
        return (int) days;
    }
    
    // Getters and Setters
    public String getRentalId() {
        return rentalId;
    }
    
    public void setRentalId(String rentalId) {
        this.rentalId = rentalId;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public Car getCar() {
        return car;
    }
    
    public void setCar(Car car) {
        this.car = car;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        this.numberOfDays = calculateDays();
        this.totalCost = car.calculateRentalCost(numberOfDays);
    }
    
    public int getNumberOfDays() {
        return numberOfDays;
    }
    
    public double getTotalCost() {
        return totalCost;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * Complete the rental transaction
     */
    public void completeRental() {
        if (!"ACTIVE".equals(status)) {
            throw new IllegalStateException("Only active rentals can be completed");
        }
        this.status = "COMPLETED";
        car.returnCar();
        System.out.println("Rental completed successfully!");
    }
    
    /**
     * Cancel the rental
     */
    public void cancelRental() {
        if ("COMPLETED".equals(status)) {
            throw new IllegalStateException("Cannot cancel completed rental");
        }
        this.status = "CANCELLED";
        if (!car.isAvailable()) {
            car.returnCar();
        }
        System.out.println("Rental cancelled successfully!");
    }
    
    /**
     * Display rental information
     */
    public void displayRentalInfo() {
        System.out.println("----------------------------------------");
        System.out.println("Rental ID: " + rentalId);
        System.out.println("Customer: " + customer.getFullName());
        System.out.println("Car: " + car.getBrand() + " " + car.getModel());
        System.out.println("Start Date: " + startDate);
        System.out.println("End Date: " + endDate);
        System.out.println("Number of Days: " + numberOfDays);
        System.out.println("Daily Rate: $" + car.getDailyRate());
        System.out.println("Total Cost: $" + String.format("%.2f", totalCost));
        System.out.println("Status: " + status);
        System.out.println("----------------------------------------");
    }
    
    @Override
    public String toString() {
        return String.format("Rental %s: %s rented %s %s for %d days ($%.2f)", 
            rentalId, customer.getFullName(), car.getBrand(), car.getModel(), 
            numberOfDays, totalCost);
    }
}
