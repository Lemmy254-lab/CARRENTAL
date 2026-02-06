# Car Rental System - Java OOP Project

##  Project Overview

This is a comprehensive Car Rental System developed using Object-Oriented Programming (OOP) principles in Java. The system demonstrates encapsulation, inheritance, abstraction, and polymorphism while managing cars, customers, and rental transactions.

##  Features

### Core Functionality
- **Car Management**: Add, remove, view, and manage vehicles in the fleet
- **Customer Management**: Register customers and track rental history
- **Rental Transactions**: Create rentals, track active rentals, complete returns
- **Business Logic**: Automatic cost calculation, availability tracking, validation
- **Reporting**: Revenue reports, rental statistics, fleet analytics

### OOP Principles Demonstrated
1. **Encapsulation**: All class attributes are private with getters/setters
2. **Abstraction**: Clear separation of concerns across classes
3. **Modularity**: Reusable, independent classes
4. **Data Validation**: Input validation and error handling
5. **Business Rules**: Enforced through methods (e.g., can't rent unavailable car)

##  Project Structure

```
CarRentalSystem/
├── src/main/java/com/carrental/
│   ├── models/
│   │   ├── Car.java              # Car entity class
│   │   ├── Customer.java         # Customer entity class
│   │   ├── Rental.java           # Rental transaction class
│   │   └── RentalAgency.java     # Main business logic class
│   ├── tests/
│   │   └── CarRentalSystemTest.java  # Comprehensive test suite
│   └── CarRentalApp.java         # Main application with UI
├── LoginSystem.java              # Login system with password masking
└── README.md                     # This file
```

##  How to Compile and Run

### Prerequisites
- Java JDK 8 or higher
- Command line terminal

### Compilation

#### Option 1: Compile All Files
```bash
cd CarRentalSystem
javac -d bin src/main/java/com/carrental/*.java src/main/java/com/carrental/models/*.java src/main/java/com/carrental/tests/*.java
```

#### Option 2: Compile Individual Components
```bash
# Compile models
javac -d bin src/main/java/com/carrental/models/*.java

# Compile main application
javac -d bin -cp bin src/main/java/com/carrental/CarRentalApp.java

# Compile test suite
javac -d bin -cp bin src/main/java/com/carrental/tests/CarRentalSystemTest.java
```

### Running the Application

#### Run Main Application (Interactive UI)
```bash
java -cp bin com.carrental.CarRentalApp
```

#### Run Test Suite
```bash
java -cp bin com.carrental.tests.CarRentalSystemTest
```

#### Run Login System
```bash
javac LoginSystem.java
java LoginSystem
```

##  Class Documentation

### 1. Car Class
Represents a vehicle in the rental fleet.

**Attributes:**
- `carId`: Unique identifier
- `brand`: Car manufacturer
- `model`: Car model name
- `year`: Manufacturing year
- `licensePlate`: Vehicle registration
- `dailyRate`: Rental cost per day
- `isAvailable`: Availability status
- `category`: Vehicle category (Economy, Standard, Luxury, SUV)

**Key Methods:**
- `calculateRentalCost(int days)`: Calculate total rental cost
- `rentOut()`: Mark car as rented
- `returnCar()`: Mark car as available
- `displayCarInfo()`: Display car details

### 2. Customer Class
Represents a customer in the system.

**Attributes:**
- `customerId`: Unique identifier
- `firstName`, `lastName`: Customer name
- `email`: Contact email
- `phone`: Contact number
- `licenseNumber`: Driver's license
- `rentalHistory`: List of past rentals

**Key Methods:**
- `addRental(Rental rental)`: Add rental to history
- `getFullName()`: Get complete name
- `displayRentalHistory()`: Show all rentals

### 3. Rental Class
Represents a rental transaction.

**Attributes:**
- `rentalId`: Unique identifier
- `customer`: Customer reference
- `car`: Car reference
- `startDate`, `endDate`: Rental period
- `numberOfDays`: Duration
- `totalCost`: Total rental cost
- `status`: ACTIVE, COMPLETED, CANCELLED

**Key Methods:**
- `completeRental()`: Finalize rental and return car
- `cancelRental()`: Cancel the rental
- `displayRentalInfo()`: Show rental details

### 4. RentalAgency Class
Main business logic coordinator.

**Attributes:**
- `agencyName`, `agencyId`: Agency details
- `cars`: Fleet of vehicles
- `customers`: Registered customers
- `rentals`: All transactions

**Key Methods:**
- `addCar(Car car)`: Add vehicle to fleet
- `addCustomer(Customer customer)`: Register new customer
- `createRental(...)`: Process new rental
- `completeRental(String rentalId)`: Return vehicle
- `generateRevenueReport()`: Display business metrics

##  Testing

The project includes a comprehensive test suite with 30+ test cases covering:

### Test Categories
1. **Car Class Tests**
   - Creation and initialization
   - Rental cost calculation
   - Rent out/return operations
   - Edge cases and validation

2. **Customer Class Tests**
   - Customer creation
   - Rental history management
   - Data integrity

3. **Rental Class Tests**
   - Rental creation and validation
   - Date range validation
   - Status transitions

4. **RentalAgency Class Tests**
   - Car fleet management
   - Customer management
   - Rental transaction processing
   - Business rule enforcement

5. **Integration Tests**
   - Complete rental workflows
   - Multi-user scenarios
   - Conflict resolution

### Running Tests
```bash
java -cp bin com.carrental.tests.CarRentalSystemTest
```

**Expected Output:**
```
=== Testing Car Class ===
✓ Car Creation - PASSED
✓ Calculate Rental Cost - PASSED
✓ Rent Out Car - PASSED
...

╔════════════════════════════════════════════╗
║          TEST SUMMARY                      ║
╠════════════════════════════════════════════╣
║  Total Tests:    30                        ║
║  Tests Passed:   30                        ║
║  Tests Failed:   0                         ║
║  Success Rate:   100.0%                    ║
╚════════════════════════════════════════════╝
```

##  Login System

The `LoginSystem.java` file demonstrates secure authentication with:

- **Password Masking**: Characters displayed as * during input
- **3 Attempt Limit**: Account locks after failed attempts
- **Real-time Comments**: Feedback after each action
- **Input Validation**: Checks for empty fields

### Test Credentials
- **Username**: admin
- **Password**: password123

### Running Login System
```bash
javac LoginSystem.java
java LoginSystem
```

##  Usage Examples

### Example 1: Adding a Car
```java
Car car = new Car("C001", "Toyota", "Camry", 2022, "KAB123A", 50.0, "Standard");
agency.addCar(car);
```

### Example 2: Creating a Rental
```java
LocalDate start = LocalDate.now();
LocalDate end = start.plusDays(5);
Rental rental = agency.createRental("CUST001", "C001", start, end);
// Car is automatically marked as unavailable
```

### Example 3: Completing a Rental
```java
agency.completeRental("R1000");
// Car is automatically returned to available status
```

##  Sample Output

### Main Menu
```
╔════════════════════════════════════════════╗
║           MAIN MENU                        ║
╠════════════════════════════════════════════╣
║  1. Car Management                         ║
║  2. Customer Management                    ║
║  3. Rental Management                      ║
║  4. Generate Revenue Report                ║
║  5. Exit                                   ║
╚════════════════════════════════════════════╝
```

### Rental Information Display
```
----------------------------------------
Rental ID: R1000
Customer: John Doe
Car: Toyota Camry
Start Date: 2024-02-05
End Date: 2024-02-10
Number of Days: 5
Daily Rate: $50.0
Total Cost: $250.00
Status: ACTIVE
----------------------------------------
```

##  Key OOP Concepts Demonstrated

### 1. Encapsulation
All class fields are private with public getters/setters:
```java
private String carId;
public String getCarId() { return carId; }
```

### 2. Data Validation
Business rules enforced in methods:
```java
public void rentOut() {
    if (!isAvailable) {
        throw new IllegalStateException("Car is already rented");
    }
    this.isAvailable = false;
}
```

### 3. Object Relationships
- Customer has-many Rentals (Aggregation)
- Rental has-a Customer and Car (Association)
- RentalAgency manages all entities (Composition)

### 4. Immutability & Safety
Defensive copying of collections:
```java
public List<Car> getCars() {
    return new ArrayList<>(cars); // Return copy, not reference
}
```

##  Author

**Your Name**
- GitHub: github.com/Lemmy254-lab
- Email: koomelemuel10@gmail.com
- Institution: Zetech University
