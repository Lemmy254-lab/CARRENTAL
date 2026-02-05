package com.carrental.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Customer class representing a customer in the rental system
 * Encapsulates customer data and rental history
 */
public class Customer {
    // Private attributes
    private String customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String licenseNumber;
    private List<Rental> rentalHistory;
    
    /**
     * Constructor to initialize a Customer object
     */
    public Customer(String customerId, String firstName, String lastName, 
                   String email, String phone, String licenseNumber) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.licenseNumber = licenseNumber;
        this.rentalHistory = new ArrayList<>();
    }
    
    // Getters and Setters
    public String getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getLicenseNumber() {
        return licenseNumber;
    }
    
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
    
    public List<Rental> getRentalHistory() {
        return new ArrayList<>(rentalHistory); // Return copy for encapsulation
    }
    
    /**
     * Add a rental to customer's history
     * @param rental The rental to add
     */
    public void addRental(Rental rental) {
        if (rental == null) {
            throw new IllegalArgumentException("Rental cannot be null");
        }
        this.rentalHistory.add(rental);
    }
    
    /**
     * Get full name of customer
     * @return Full name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    /**
     * Display customer information
     */
    public void displayCustomerInfo() {
        System.out.println("========================================");
        System.out.println("Customer ID: " + customerId);
        System.out.println("Name: " + getFullName());
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println("License Number: " + licenseNumber);
        System.out.println("Total Rentals: " + rentalHistory.size());
        System.out.println("========================================");
    }
    
    /**
     * Display rental history
     */
    public void displayRentalHistory() {
        System.out.println("\n=== Rental History for " + getFullName() + " ===");
        if (rentalHistory.isEmpty()) {
            System.out.println("No rental history found.");
        } else {
            for (int i = 0; i < rentalHistory.size(); i++) {
                System.out.println("\nRental #" + (i + 1));
                rentalHistory.get(i).displayRentalInfo();
            }
        }
    }
    
    @Override
    public String toString() {
        return String.format("%s - %s (%s)", customerId, getFullName(), email);
    }
}
