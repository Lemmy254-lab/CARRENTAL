package com.carrental.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * RentalAgency class managing the entire car rental system
 * Encapsulates business logic and data management
 */
public class RentalAgency {
    // Private attributes
    private String agencyName;
    private String agencyId;
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;
    private int rentalCounter;
    
    /**
     * Constructor to initialize the rental agency
     */
    public RentalAgency(String agencyId, String agencyName) {
        this.agencyId = agencyId;
        this.agencyName = agencyName;
        this.cars = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.rentals = new ArrayList<>();
        this.rentalCounter = 1000;
    }
    
    // Getters
    public String getAgencyName() {
        return agencyName;
    }
    
    public String getAgencyId() {
        return agencyId;
    }
    
    public List<Car> getCars() {
        return new ArrayList<>(cars);
    }
    
    public List<Customer> getCustomers() {
        return new ArrayList<>(customers);
    }
    
    public List<Rental> getRentals() {
        return new ArrayList<>(rentals);
    }
    
    // ========== CAR MANAGEMENT ==========
    
    /**
     * Add a car to the fleet
     */
    public void addCar(Car car) {
        if (car == null) {
            throw new IllegalArgumentException("Car cannot be null");
        }
        if (findCarById(car.getCarId()) != null) {
            throw new IllegalArgumentException("Car with ID " + car.getCarId() + " already exists");
        }
        cars.add(car);
        System.out.println("Car added successfully: " + car);
    }
    
    /**
     * Remove a car from the fleet
     */
    public boolean removeCar(String carId) {
        Car car = findCarById(carId);
        if (car == null) {
            System.out.println("Car not found: " + carId);
            return false;
        }
        if (!car.isAvailable()) {
            System.out.println("Cannot remove car - currently rented");
            return false;
        }
        cars.remove(car);
        System.out.println("Car removed successfully: " + carId);
        return true;
    }
    
    /**
     * Find a car by ID
     */
    public Car findCarById(String carId) {
        return cars.stream()
                   .filter(car -> car.getCarId().equals(carId))
                   .findFirst()
                   .orElse(null);
    }
    
    /**
     * Get all available cars
     */
    public List<Car> getAvailableCars() {
        return cars.stream()
                   .filter(Car::isAvailable)
                   .collect(Collectors.toList());
    }
    
    /**
     * Get available cars by category
     */
    public List<Car> getAvailableCarsByCategory(String category) {
        return cars.stream()
                   .filter(Car::isAvailable)
                   .filter(car -> car.getCategory().equalsIgnoreCase(category))
                   .collect(Collectors.toList());
    }
    
    /**
     * Display all cars
     */
    public void displayAllCars() {
        System.out.println("\n=== All Cars in Fleet ===");
        if (cars.isEmpty()) {
            System.out.println("No cars in the fleet.");
        } else {
            cars.forEach(Car::displayCarInfo);
        }
    }
    
    /**
     * Display available cars
     */
    public void displayAvailableCars() {
        System.out.println("\n=== Available Cars ===");
        List<Car> availableCars = getAvailableCars();
        if (availableCars.isEmpty()) {
            System.out.println("No cars available at the moment.");
        } else {
            availableCars.forEach(Car::displayCarInfo);
        }
    }
    
    // ========== CUSTOMER MANAGEMENT ==========
    
    /**
     * Add a customer
     */
    public void addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (findCustomerById(customer.getCustomerId()) != null) {
            throw new IllegalArgumentException("Customer with ID " + customer.getCustomerId() + " already exists");
        }
        customers.add(customer);
        System.out.println("Customer added successfully: " + customer);
    }
    
    /**
     * Find a customer by ID
     */
    public Customer findCustomerById(String customerId) {
        return customers.stream()
                       .filter(customer -> customer.getCustomerId().equals(customerId))
                       .findFirst()
                       .orElse(null);
    }
    
    /**
     * Display all customers
     */
    public void displayAllCustomers() {
        System.out.println("\n=== All Customers ===");
        if (customers.isEmpty()) {
            System.out.println("No customers registered.");
        } else {
            customers.forEach(Customer::displayCustomerInfo);
        }
    }
    
    // ========== RENTAL MANAGEMENT ==========
    
    /**
     * Create a new rental
     */
    public Rental createRental(String customerId, String carId, 
                              LocalDate startDate, LocalDate endDate) {
        // Validate customer
        Customer customer = findCustomerById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found: " + customerId);
        }
        
        // Validate car
        Car car = findCarById(carId);
        if (car == null) {
            throw new IllegalArgumentException("Car not found: " + carId);
        }
        
        // Check car availability
        if (!car.isAvailable()) {
            throw new IllegalStateException("Car is not available for rent");
        }
        
        // Create rental
        String rentalId = "R" + (rentalCounter++);
        Rental rental = new Rental(rentalId, customer, car, startDate, endDate);
        
        // Update car status
        car.rentOut();
        
        // Add rental to lists
        rentals.add(rental);
        customer.addRental(rental);
        
        System.out.println("\nRental created successfully!");
        rental.displayRentalInfo();
        
        return rental;
    }
    
    /**
     * Complete a rental (return car)
     */
    public boolean completeRental(String rentalId) {
        Rental rental = findRentalById(rentalId);
        if (rental == null) {
            System.out.println("Rental not found: " + rentalId);
            return false;
        }
        
        rental.completeRental();
        return true;
    }
    
    /**
     * Cancel a rental
     */
    public boolean cancelRental(String rentalId) {
        Rental rental = findRentalById(rentalId);
        if (rental == null) {
            System.out.println("Rental not found: " + rentalId);
            return false;
        }
        
        rental.cancelRental();
        return true;
    }
    
    /**
     * Find a rental by ID
     */
    public Rental findRentalById(String rentalId) {
        return rentals.stream()
                     .filter(rental -> rental.getRentalId().equals(rentalId))
                     .findFirst()
                     .orElse(null);
    }
    
    /**
     * Get active rentals
     */
    public List<Rental> getActiveRentals() {
        return rentals.stream()
                     .filter(rental -> "ACTIVE".equals(rental.getStatus()))
                     .collect(Collectors.toList());
    }
    
    /**
     * Display all rentals
     */
    public void displayAllRentals() {
        System.out.println("\n=== All Rentals ===");
        if (rentals.isEmpty()) {
            System.out.println("No rentals found.");
        } else {
            rentals.forEach(Rental::displayRentalInfo);
        }
    }
    
    /**
     * Display active rentals
     */
    public void displayActiveRentals() {
        System.out.println("\n=== Active Rentals ===");
        List<Rental> activeRentals = getActiveRentals();
        if (activeRentals.isEmpty()) {
            System.out.println("No active rentals.");
        } else {
            activeRentals.forEach(Rental::displayRentalInfo);
        }
    }
    
    /**
     * Generate revenue report
     */
    public void generateRevenueReport() {
        double totalRevenue = rentals.stream()
                                    .filter(rental -> "COMPLETED".equals(rental.getStatus()))
                                    .mapToDouble(Rental::getTotalCost)
                                    .sum();
        
        long completedRentals = rentals.stream()
                                      .filter(rental -> "COMPLETED".equals(rental.getStatus()))
                                      .count();
        
        System.out.println("\n=== Revenue Report ===");
        System.out.println("Agency: " + agencyName);
        System.out.println("Total Cars: " + cars.size());
        System.out.println("Available Cars: " + getAvailableCars().size());
        System.out.println("Total Customers: " + customers.size());
        System.out.println("Total Rentals: " + rentals.size());
        System.out.println("Completed Rentals: " + completedRentals);
        System.out.println("Active Rentals: " + getActiveRentals().size());
        System.out.println("Total Revenue: $" + String.format("%.2f", totalRevenue));
        System.out.println("======================");
    }
}
