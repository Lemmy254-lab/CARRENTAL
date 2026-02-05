package com.carrental;

import com.carrental.models.*;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Main application class for Car Rental System
 * Demonstrates all OOP concepts and functionalities
 */
public class CarRentalApp {
    private static RentalAgency agency;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        
        // Initialize the rental agency
        agency = new RentalAgency("AG001", "Zetech Car Rentals");
        
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   WELCOME TO ZETECH CAR RENTAL SYSTEM      ║");
        System.out.println("╚════════════════════════════════════════════╝");
        
        // Initialize sample data
        initializeSampleData();
        
        // Main menu loop
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    handleCarManagement();
                    break;
                case 2:
                    handleCustomerManagement();
                    break;
                case 3:
                    handleRentalManagement();
                    break;
                case 4:
                    agency.generateRevenueReport();
                    break;
                case 5:
                    System.out.println("\nThank you for using Zetech Car Rental System!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
    
    /**
     * Display main menu
     */
    private static void displayMainMenu() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║           MAIN MENU                        ║");
        System.out.println("╠════════════════════════════════════════════╣");
        System.out.println("║  1. Car Management                         ║");
        System.out.println("║  2. Customer Management                    ║");
        System.out.println("║  3. Rental Management                      ║");
        System.out.println("║  4. Generate Revenue Report                ║");
        System.out.println("║  5. Exit                                   ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }
    
    /**
     * Handle car management operations
     */
    private static void handleCarManagement() {
        System.out.println("\n--- Car Management ---");
        System.out.println("1. Add New Car");
        System.out.println("2. View All Cars");
        System.out.println("3. View Available Cars");
        System.out.println("4. Remove Car");
        System.out.println("5. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                addNewCar();
                break;
            case 2:
                agency.displayAllCars();
                break;
            case 3:
                agency.displayAvailableCars();
                break;
            case 4:
                removeCar();
                break;
            case 5:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    /**
     * Add a new car to the fleet
     */
    private static void addNewCar() {
        System.out.println("\n--- Add New Car ---");
        System.out.print("Enter Car ID: ");
        String carId = scanner.nextLine();
        System.out.print("Enter Brand: ");
        String brand = scanner.nextLine();
        System.out.print("Enter Model: ");
        String model = scanner.nextLine();
        int year = getIntInput("Enter Year: ");
        System.out.print("Enter License Plate: ");
        String licensePlate = scanner.nextLine();
        double dailyRate = getDoubleInput("Enter Daily Rate: ");
        System.out.print("Enter Category (Economy/Standard/Luxury/SUV): ");
        String category = scanner.nextLine();
        
        try {
            Car car = new Car(carId, brand, model, year, licensePlate, dailyRate, category);
            agency.addCar(car);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Remove a car from the fleet
     */
    private static void removeCar() {
        System.out.print("\nEnter Car ID to remove: ");
        String carId = scanner.nextLine();
        agency.removeCar(carId);
    }
    
    /**
     * Handle customer management operations
     */
    private static void handleCustomerManagement() {
        System.out.println("\n--- Customer Management ---");
        System.out.println("1. Add New Customer");
        System.out.println("2. View All Customers");
        System.out.println("3. View Customer Details");
        System.out.println("4. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                addNewCustomer();
                break;
            case 2:
                agency.displayAllCustomers();
                break;
            case 3:
                viewCustomerDetails();
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    /**
     * Add a new customer
     */
    private static void addNewCustomer() {
        System.out.println("\n--- Add New Customer ---");
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter License Number: ");
        String licenseNumber = scanner.nextLine();
        
        try {
            Customer customer = new Customer(customerId, firstName, lastName, 
                                           email, phone, licenseNumber);
            agency.addCustomer(customer);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * View customer details and rental history
     */
    private static void viewCustomerDetails() {
        System.out.print("\nEnter Customer ID: ");
        String customerId = scanner.nextLine();
        Customer customer = agency.findCustomerById(customerId);
        
        if (customer != null) {
            customer.displayCustomerInfo();
            customer.displayRentalHistory();
        } else {
            System.out.println("Customer not found.");
        }
    }
    
    /**
     * Handle rental management operations
     */
    private static void handleRentalManagement() {
        System.out.println("\n--- Rental Management ---");
        System.out.println("1. Create New Rental");
        System.out.println("2. Complete Rental (Return Car)");
        System.out.println("3. Cancel Rental");
        System.out.println("4. View All Rentals");
        System.out.println("5. View Active Rentals");
        System.out.println("6. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                createNewRental();
                break;
            case 2:
                completeRental();
                break;
            case 3:
                cancelRental();
                break;
            case 4:
                agency.displayAllRentals();
                break;
            case 5:
                agency.displayActiveRentals();
                break;
            case 6:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    /**
     * Create a new rental
     */
    private static void createNewRental() {
        System.out.println("\n--- Create New Rental ---");
        
        // Display available cars
        agency.displayAvailableCars();
        
        System.out.print("\nEnter Customer ID: ");
        String customerId = scanner.nextLine();
        System.out.print("Enter Car ID: ");
        String carId = scanner.nextLine();
        
        System.out.print("Enter Start Date (YYYY-MM-DD): ");
        String startDateStr = scanner.nextLine();
        System.out.print("Enter End Date (YYYY-MM-DD): ");
        String endDateStr = scanner.nextLine();
        
        try {
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);
            
            agency.createRental(customerId, carId, startDate, endDate);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Complete a rental (return car)
     */
    private static void completeRental() {
        System.out.println("\n--- Complete Rental ---");
        agency.displayActiveRentals();
        
        System.out.print("\nEnter Rental ID to complete: ");
        String rentalId = scanner.nextLine();
        
        try {
            agency.completeRental(rentalId);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Cancel a rental
     */
    private static void cancelRental() {
        System.out.println("\n--- Cancel Rental ---");
        agency.displayActiveRentals();
        
        System.out.print("\nEnter Rental ID to cancel: ");
        String rentalId = scanner.nextLine();
        
        try {
            agency.cancelRental(rentalId);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Initialize sample data for demonstration
     */
    private static void initializeSampleData() {
        System.out.println("\nInitializing sample data...");
        
        // Add sample cars
        agency.addCar(new Car("C001", "Toyota", "Camry", 2022, "KAB123A", 50.0, "Standard"));
        agency.addCar(new Car("C002", "Honda", "Civic", 2023, "KAC456B", 45.0, "Economy"));
        agency.addCar(new Car("C003", "BMW", "X5", 2023, "KAD789C", 120.0, "Luxury"));
        agency.addCar(new Car("C004", "Toyota", "RAV4", 2022, "KAE012D", 70.0, "SUV"));
        agency.addCar(new Car("C005", "Mercedes", "C-Class", 2023, "KAF345E", 100.0, "Luxury"));
        
        // Add sample customers
        agency.addCustomer(new Customer("CUST001", "John", "Doe", 
                                       "john.doe@email.com", "+254700123456", "DL123456"));
        agency.addCustomer(new Customer("CUST002", "Jane", "Smith", 
                                       "jane.smith@email.com", "+254701234567", "DL234567"));
        agency.addCustomer(new Customer("CUST003", "Mike", "Johnson", 
                                       "mike.j@email.com", "+254702345678", "DL345678"));
        
        System.out.println("Sample data initialized successfully!\n");
    }
    
    /**
     * Helper method to get integer input with error handling
     */
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
    
    /**
     * Helper method to get double input with error handling
     */
    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}
