package com.carrental.tests;

import com.carrental.models.*;
import java.time.LocalDate;

/**
 * Test suite for Car Rental System
 * Tests all classes and their methods
 */
public class CarRentalSystemTest {
    
    private static int testsPassed = 0;
    private static int testsFailed = 0;
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   CAR RENTAL SYSTEM - TEST SUITE          ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
        
        // Run all tests
        testCarClass();
        testCustomerClass();
        testRentalClass();
        testRentalAgencyClass();
        testIntegrationScenarios();
        
        // Print summary
        printTestSummary();
    }
    
    /**
     * Test Car class functionality
     */
    private static void testCarClass() {
        System.out.println("\n=== Testing Car Class ===");
        
        // Test 1: Car creation
        test("Car Creation", () -> {
            Car car = new Car("C001", "Toyota", "Camry", 2022, "KAB123A", 50.0, "Standard");
            assert car.getCarId().equals("C001");
            assert car.getBrand().equals("Toyota");
            assert car.isAvailable() == true;
        });
        
        // Test 2: Calculate rental cost
        test("Calculate Rental Cost", () -> {
            Car car = new Car("C001", "Toyota", "Camry", 2022, "KAB123A", 50.0, "Standard");
            double cost = car.calculateRentalCost(5);
            assert cost == 250.0 : "Expected 250.0, got " + cost;
        });
        
        // Test 3: Rent out car
        test("Rent Out Car", () -> {
            Car car = new Car("C001", "Toyota", "Camry", 2022, "KAB123A", 50.0, "Standard");
            car.rentOut();
            assert car.isAvailable() == false;
        });
        
        // Test 4: Return car
        test("Return Car", () -> {
            Car car = new Car("C001", "Toyota", "Camry", 2022, "KAB123A", 50.0, "Standard");
            car.rentOut();
            car.returnCar();
            assert car.isAvailable() == true;
        });
        
        // Test 5: Invalid rental days
        test("Invalid Rental Days", () -> {
            Car car = new Car("C001", "Toyota", "Camry", 2022, "KAB123A", 50.0, "Standard");
            try {
                car.calculateRentalCost(0);
                assert false : "Should throw exception for zero days";
            } catch (IllegalArgumentException e) {
                assert true;
            }
        });
        
        // Test 6: Double rent out (should fail)
        test("Double Rent Out Prevention", () -> {
            Car car = new Car("C001", "Toyota", "Camry", 2022, "KAB123A", 50.0, "Standard");
            car.rentOut();
            try {
                car.rentOut();
                assert false : "Should throw exception for double rent";
            } catch (IllegalStateException e) {
                assert true;
            }
        });
    }
    
    /**
     * Test Customer class functionality
     */
    private static void testCustomerClass() {
        System.out.println("\n=== Testing Customer Class ===");
        
        // Test 1: Customer creation
        test("Customer Creation", () -> {
            Customer customer = new Customer("CUST001", "John", "Doe", 
                                           "john@email.com", "+254700123456", "DL123456");
            assert customer.getCustomerId().equals("CUST001");
            assert customer.getFullName().equals("John Doe");
        });
        
        // Test 2: Add rental to history
        test("Add Rental to History", () -> {
            Customer customer = new Customer("CUST001", "John", "Doe", 
                                           "john@email.com", "+254700123456", "DL123456");
            Car car = new Car("C001", "Toyota", "Camry", 2022, "KAB123A", 50.0, "Standard");
            Rental rental = new Rental("R001", customer, car, 
                                      LocalDate.now(), LocalDate.now().plusDays(3));
            customer.addRental(rental);
            assert customer.getRentalHistory().size() == 1;
        });
        
        // Test 3: Get full name
        test("Get Full Name", () -> {
            Customer customer = new Customer("CUST001", "Jane", "Smith", 
                                           "jane@email.com", "+254701234567", "DL234567");
            assert customer.getFullName().equals("Jane Smith");
        });
    }
    
    /**
     * Test Rental class functionality
     */
    private static void testRentalClass() {
        System.out.println("\n=== Testing Rental Class ===");
        
        // Test 1: Rental creation
        test("Rental Creation", () -> {
            Customer customer = new Customer("CUST001", "John", "Doe", 
                                           "john@email.com", "+254700123456", "DL123456");
            Car car = new Car("C001", "Toyota", "Camry", 2022, "KAB123A", 50.0, "Standard");
            LocalDate start = LocalDate.of(2024, 1, 1);
            LocalDate end = LocalDate.of(2024, 1, 6);
            Rental rental = new Rental("R001", customer, car, start, end);
            
            assert rental.getRentalId().equals("R001");
            assert rental.getNumberOfDays() == 5;
            assert rental.getTotalCost() == 250.0;
            assert rental.getStatus().equals("ACTIVE");
        });
        
        // Test 2: Complete rental
        test("Complete Rental", () -> {
            Customer customer = new Customer("CUST001", "John", "Doe", 
                                           "john@email.com", "+254700123456", "DL123456");
            Car car = new Car("C001", "Toyota", "Camry", 2022, "KAB123A", 50.0, "Standard");
            car.rentOut();
            Rental rental = new Rental("R001", customer, car, 
                                      LocalDate.now(), LocalDate.now().plusDays(3));
            rental.completeRental();
            assert rental.getStatus().equals("COMPLETED");
            assert car.isAvailable() == true;
        });
        
        // Test 3: Cancel rental
        test("Cancel Rental", () -> {
            Customer customer = new Customer("CUST001", "John", "Doe", 
                                           "john@email.com", "+254700123456", "DL123456");
            Car car = new Car("C001", "Toyota", "Camry", 2022, "KAB123A", 50.0, "Standard");
            Rental rental = new Rental("R001", customer, car, 
                                      LocalDate.now(), LocalDate.now().plusDays(3));
            rental.cancelRental();
            assert rental.getStatus().equals("CANCELLED");
        });
        
        // Test 4: Invalid date range
        test("Invalid Date Range", () -> {
            Customer customer = new Customer("CUST001", "John", "Doe", 
                                           "john@email.com", "+254700123456", "DL123456");
            Car car = new Car("C001", "Toyota", "Camry", 2022, "KAB123A", 50.0, "Standard");
            try {
                Rental rental = new Rental("R001", customer, car, 
                                          LocalDate.now(), LocalDate.now().minusDays(1));
                assert false : "Should throw exception for invalid dates";
            } catch (IllegalArgumentException e) {
                assert true;
            }
        });
    }
    
    /**
     * Test RentalAgency class functionality
     */
    private static void testRentalAgencyClass() {
        System.out.println("\n=== Testing RentalAgency Class ===");
        
        // Test 1: Agency creation
        test("Agency Creation", () -> {
            RentalAgency agency = new RentalAgency("AG001", "Zetech Rentals");
            assert agency.getAgencyId().equals("AG001");
            assert agency.getAgencyName().equals("Zetech Rentals");
        });
        
        // Test 2: Add car to fleet
        test("Add Car to Fleet", () -> {
            RentalAgency agency = new RentalAgency("AG001", "Zetech Rentals");
            Car car = new Car("C001", "Toyota", "Camry", 2022, "KAB123A", 50.0, "Standard");
            agency.addCar(car);
            assert agency.getCars().size() == 1;
        });
        
        // Test 3: Find car by ID
        test("Find Car by ID", () -> {
            RentalAgency agency = new RentalAgency("AG001", "Zetech Rentals");
            Car car = new Car("C001", "Toyota", "Camry", 2022, "KAB123A", 50.0, "Standard");
            agency.addCar(car);
            Car found = agency.findCarById("C001");
            assert found != null;
            assert found.getCarId().equals("C001");
        });
        
        // Test 4: Get available cars
        test("Get Available Cars", () -> {
            RentalAgency agency = new RentalAgency("AG001", "Zetech Rentals");
            Car car1 = new Car("C001", "Toyota", "Camry", 2022, "KAB123A", 50.0, "Standard");
            Car car2 = new Car("C002", "Honda", "Civic", 2023, "KAC456B", 45.0, "Economy");
            agency.addCar(car1);
            agency.addCar(car2);
            car1.rentOut();
            assert agency.getAvailableCars().size() == 1;
        });
        
        // Test 5: Add customer
        test("Add Customer", () -> {
            RentalAgency agency = new RentalAgency("AG001", "Zetech Rentals");
            Customer customer = new Customer("CUST001", "John", "Doe", 
                                           "john@email.com", "+254700123456", "DL123456");
            agency.addCustomer(customer);
            assert agency.getCustomers().size() == 1;
        });
        
        // Test 6: Create rental
        test("Create Rental", () -> {
            RentalAgency agency = new RentalAgency("AG001", "Zetech Rentals");
            Car car = new Car("C001", "Toyota", "Camry", 2022, "KAB123A", 50.0, "Standard");
            Customer customer = new Customer("CUST001", "John", "Doe", 
                                           "john@email.com", "+254700123456", "DL123456");
            agency.addCar(car);
            agency.addCustomer(customer);
            
            Rental rental = agency.createRental("CUST001", "C001", 
                                               LocalDate.now(), LocalDate.now().plusDays(5));
            assert rental != null;
            assert !car.isAvailable();
            assert agency.getRentals().size() == 1;
        });
        
        // Test 7: Complete rental
        test("Complete Rental via Agency", () -> {
            RentalAgency agency = new RentalAgency("AG001", "Zetech Rentals");
            Car car = new Car("C001", "Toyota", "Camry", 2022, "KAB123A", 50.0, "Standard");
            Customer customer = new Customer("CUST001", "John", "Doe", 
                                           "john@email.com", "+254700123456", "DL123456");
            agency.addCar(car);
            agency.addCustomer(customer);
            
            Rental rental = agency.createRental("CUST001", "C001", 
                                               LocalDate.now(), LocalDate.now().plusDays(5));
            String rentalId = rental.getRentalId();
            agency.completeRental(rentalId);
            
            assert car.isAvailable();
            assert rental.getStatus().equals("COMPLETED");
        });
        
        // Test 8: Remove car
        test("Remove Car", () -> {
            RentalAgency agency = new RentalAgency("AG001", "Zetech Rentals");
            Car car = new Car("C001", "Toyota", "Camry", 2022, "KAB123A", 50.0, "Standard");
            agency.addCar(car);
            boolean removed = agency.removeCar("C001");
            assert removed == true;
            assert agency.getCars().size() == 0;
        });
        
        // Test 9: Cannot remove rented car
        test("Cannot Remove Rented Car", () -> {
            RentalAgency agency = new RentalAgency("AG001", "Zetech Rentals");
            Car car = new Car("C001", "Toyota", "Camry", 2022, "KAB123A", 50.0, "Standard");
            Customer customer = new Customer("CUST001", "John", "Doe", 
                                           "john@email.com", "+254700123456", "DL123456");
            agency.addCar(car);
            agency.addCustomer(customer);
            agency.createRental("CUST001", "C001", 
                              LocalDate.now(), LocalDate.now().plusDays(5));
            
            boolean removed = agency.removeCar("C001");
            assert removed == false;
        });
    }
    
    /**
     * Test integration scenarios
     */
    private static void testIntegrationScenarios() {
        System.out.println("\n=== Testing Integration Scenarios ===");
        
        // Test 1: Complete rental workflow
        test("Complete Rental Workflow", () -> {
            RentalAgency agency = new RentalAgency("AG001", "Zetech Rentals");
            
            // Add cars
            Car car1 = new Car("C001", "Toyota", "Camry", 2022, "KAB123A", 50.0, "Standard");
            Car car2 = new Car("C002", "Honda", "Civic", 2023, "KAC456B", 45.0, "Economy");
            agency.addCar(car1);
            agency.addCar(car2);
            
            // Add customer
            Customer customer = new Customer("CUST001", "John", "Doe", 
                                           "john@email.com", "+254700123456", "DL123456");
            agency.addCustomer(customer);
            
            // Create rental
            Rental rental = agency.createRental("CUST001", "C001", 
                                               LocalDate.now(), LocalDate.now().plusDays(3));
            
            // Verify rental
            assert rental != null;
            assert !car1.isAvailable();
            assert customer.getRentalHistory().size() == 1;
            
            // Complete rental
            agency.completeRental(rental.getRentalId());
            assert car1.isAvailable();
            assert rental.getStatus().equals("COMPLETED");
        });
        
        // Test 2: Multiple rentals for same customer
        test("Multiple Rentals for Same Customer", () -> {
            RentalAgency agency = new RentalAgency("AG001", "Zetech Rentals");
            
            Car car1 = new Car("C001", "Toyota", "Camry", 2022, "KAB123A", 50.0, "Standard");
            Car car2 = new Car("C002", "Honda", "Civic", 2023, "KAC456B", 45.0, "Economy");
            agency.addCar(car1);
            agency.addCar(car2);
            
            Customer customer = new Customer("CUST001", "John", "Doe", 
                                           "john@email.com", "+254700123456", "DL123456");
            agency.addCustomer(customer);
            
            // Create first rental
            Rental rental1 = agency.createRental("CUST001", "C001", 
                                                LocalDate.now(), LocalDate.now().plusDays(3));
            agency.completeRental(rental1.getRentalId());
            
            // Create second rental
            Rental rental2 = agency.createRental("CUST001", "C002", 
                                                LocalDate.now(), LocalDate.now().plusDays(5));
            
            assert customer.getRentalHistory().size() == 2;
        });
        
        // Test 3: Cannot rent unavailable car
        test("Cannot Rent Unavailable Car", () -> {
            RentalAgency agency = new RentalAgency("AG001", "Zetech Rentals");
            
            Car car = new Car("C001", "Toyota", "Camry", 2022, "KAB123A", 50.0, "Standard");
            agency.addCar(car);
            
            Customer customer1 = new Customer("CUST001", "John", "Doe", 
                                            "john@email.com", "+254700123456", "DL123456");
            Customer customer2 = new Customer("CUST002", "Jane", "Smith", 
                                            "jane@email.com", "+254701234567", "DL234567");
            agency.addCustomer(customer1);
            agency.addCustomer(customer2);
            
            // First rental
            agency.createRental("CUST001", "C001", 
                              LocalDate.now(), LocalDate.now().plusDays(3));
            
            // Try to rent same car
            try {
                agency.createRental("CUST002", "C001", 
                                  LocalDate.now(), LocalDate.now().plusDays(2));
                assert false : "Should not allow renting unavailable car";
            } catch (IllegalStateException e) {
                assert true;
            }
        });
    }
    
    /**
     * Helper method to run a test
     */
    private static void test(String testName, TestCase testCase) {
        try {
            testCase.run();
            testsPassed++;
            System.out.println("✓ " + testName + " - PASSED");
        } catch (AssertionError e) {
            testsFailed++;
            System.out.println("✗ " + testName + " - FAILED: " + e.getMessage());
        } catch (Exception e) {
            testsFailed++;
            System.out.println("✗ " + testName + " - ERROR: " + e.getMessage());
        }
    }
    
    /**
     * Print test summary
     */
    private static void printTestSummary() {
        int totalTests = testsPassed + testsFailed;
        double successRate = (totalTests > 0) ? (testsPassed * 100.0 / totalTests) : 0;
        
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║          TEST SUMMARY                      ║");
        System.out.println("╠════════════════════════════════════════════╣");
        System.out.printf("║  Total Tests:    %-25d ║%n", totalTests);
        System.out.printf("║  Tests Passed:   %-25d ║%n", testsPassed);
        System.out.printf("║  Tests Failed:   %-25d ║%n", testsFailed);
        System.out.printf("║  Success Rate:   %-25.1f%% ║%n", successRate);
        System.out.println("╚════════════════════════════════════════════╝");
    }
    
    /**
     * Functional interface for test cases
     */
    @FunctionalInterface
    interface TestCase {
        void run() throws Exception;
    }
}
