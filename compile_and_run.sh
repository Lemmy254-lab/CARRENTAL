#!/bin/bash

# Car Rental System - Compilation and Execution Script
# This script compiles and runs the Car Rental System

echo "╔════════════════════════════════════════════╗"
echo "║  Car Rental System - Build Script         ║"
echo "╚════════════════════════════════════════════╝"
echo ""

# Create bin directory if it doesn't exist
mkdir -p bin

echo "📦 Compiling Java files..."
echo ""

# Compile models
echo "1️⃣  Compiling model classes..."
javac -d bin src/main/java/com/carrental/models/*.java
if [ $? -eq 0 ]; then
    echo "   ✓ Models compiled successfully"
else
    echo "   ✗ Error compiling models"
    exit 1
fi

# Compile main application
echo "2️⃣  Compiling main application..."
javac -d bin -cp bin src/main/java/com/carrental/CarRentalApp.java
if [ $? -eq 0 ]; then
    echo "   ✓ Main application compiled successfully"
else
    echo "   ✗ Error compiling main application"
    exit 1
fi

# Compile test suite
echo "3️⃣  Compiling test suite..."
javac -d bin -cp bin src/main/java/com/carrental/tests/CarRentalSystemTest.java
if [ $? -eq 0 ]; then
    echo "   ✓ Test suite compiled successfully"
else
    echo "   ✗ Error compiling test suite"
    exit 1
fi

# Compile login system
echo "4️⃣  Compiling login system..."
javac LoginSystem.java
if [ $? -eq 0 ]; then
    echo "   ✓ Login system compiled successfully"
else
    echo "   ✗ Error compiling login system"
    exit 1
fi

echo ""
echo "✅ All files compiled successfully!"
echo ""
echo "╔════════════════════════════════════════════╗"
echo "║  How to Run:                               ║"
echo "╠════════════════════════════════════════════╣"
echo "║  1. Main Application:                      ║"
echo "║     java -cp bin com.carrental.CarRentalApp║"
echo "║                                            ║"
echo "║  2. Test Suite:                            ║"
echo "║     java -cp bin com.carrental.tests.CarRentalSystemTest║"
echo "║                                            ║"
echo "║  3. Login System:                          ║"
echo "║     java LoginSystem                       ║"
echo "╚════════════════════════════════════════════╝"
echo ""

# Ask user which program to run
read -p "Would you like to run a program now? (1=App, 2=Tests, 3=Login, N=No): " choice

case $choice in
    1)
        echo ""
        echo "🚀 Starting Car Rental Application..."
        echo ""
        java -cp bin com.carrental.CarRentalApp
        ;;
    2)
        echo ""
        echo "🧪 Running Test Suite..."
        echo ""
        java -cp bin com.carrental.tests.CarRentalSystemTest
        ;;
    3)
        echo ""
        echo "🔐 Starting Login System..."
        echo ""
        java LoginSystem
        ;;
    *)
        echo "Exiting. You can run programs manually using the commands above."
        ;;
esac
