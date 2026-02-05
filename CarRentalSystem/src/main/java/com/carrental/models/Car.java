package com.carrental.models;

/**
 * Car class representing a vehicle in the rental system
 * Encapsulates car data and behaviors following OOP principles
 */
public class Car {
    // Private attributes for encapsulation
    private String carId;
    private String brand;
    private String model;
    private int year;
    private String licensePlate;
    private double dailyRate;
    private boolean isAvailable;
    private String category; // Economy, Standard, Luxury, SUV
    
    /**
     * Constructor to initialize a Car object
     */
    public Car(String carId, String brand, String model, int year, 
               String licensePlate, double dailyRate, String category) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.licensePlate = licensePlate;
        this.dailyRate = dailyRate;
        this.isAvailable = true; // Car is available by default
        this.category = category;
    }
    
    // Getters and Setters
    public String getCarId() {
        return carId;
    }
    
    public void setCarId(String carId) {
        this.carId = carId;
    }
    
    public String getBrand() {
        return brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public int getYear() {
        return year;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
    public String getLicensePlate() {
        return licensePlate;
    }
    
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
    
    public double getDailyRate() {
        return dailyRate;
    }
    
    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }
    
    public boolean isAvailable() {
        return isAvailable;
    }
    
    public void setAvailable(boolean available) {
        isAvailable = available;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    /**
     * Calculate rental cost based on number of days
     * @param days Number of days to rent
     * @return Total cost
     */
    public double calculateRentalCost(int days) {
        if (days <= 0) {
            throw new IllegalArgumentException("Days must be greater than 0");
        }
        return dailyRate * days;
    }
    
    /**
     * Mark car as rented (unavailable)
     */
    public void rentOut() {
        if (!isAvailable) {
            throw new IllegalStateException("Car is already rented");
        }
        this.isAvailable = false;
    }
    
    /**
     * Mark car as returned (available)
     */
    public void returnCar() {
        if (isAvailable) {
            throw new IllegalStateException("Car is already available");
        }
        this.isAvailable = true;
    }
    
    /**
     * Display car information
     */
    public void displayCarInfo() {
        System.out.println("========================================");
        System.out.println("Car ID: " + carId);
        System.out.println("Brand: " + brand);
        System.out.println("Model: " + model);
        System.out.println("Year: " + year);
        System.out.println("License Plate: " + licensePlate);
        System.out.println("Category: " + category);
        System.out.println("Daily Rate: $" + dailyRate);
        System.out.println("Status: " + (isAvailable ? "Available" : "Rented"));
        System.out.println("========================================");
    }
    
    @Override
    public String toString() {
        return String.format("%s %s %s (%d) - $%.2f/day [%s]", 
            carId, brand, model, year, dailyRate, 
            isAvailable ? "Available" : "Rented");
    }
}
