import java.io.Console;
import java.util.Scanner;

/**
 * Login Program with Password Masking
 * Features:
 * - Username and password authentication
 * - Password characters masked with * during input
 * - 3 attempts before lockout
 * - Appropriate comments after each entry
 * 
 * @author Your Name
 * @date 2024
 */
public class LoginSystem {
    
    // Predefined valid credentials (in real system, this would be in a database)
    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "password123";
    private static final int MAX_ATTEMPTS = 3;
    
    public static void main(String[] args) {
        // Display welcome header
        displayHeader();
        
        // Attempt to login
        boolean loginSuccessful = performLogin();
        
        // Display final result
        if (loginSuccessful) {
            System.out.println("\n╔════════════════════════════════════════════╗");
            System.out.println("║       LOGIN SUCCESSFUL!                    ║");
            System.out.println("║       Welcome to the system!               ║");
            System.out.println("╚════════════════════════════════════════════╝");
            displaySuccessScreen();
        } else {
            System.out.println("\n╔════════════════════════════════════════════╗");
            System.out.println("║       ACCOUNT LOCKED                       ║");
            System.out.println("║   Maximum login attempts exceeded!         ║");
            System.out.println("║   Please contact administrator.            ║");
            System.out.println("╚════════════════════════════════════════════╝");
        }
    }
    
    /**
     * Display welcome header
     */
    private static void displayHeader() {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║       SECURE LOGIN SYSTEM                  ║");
        System.out.println("║       Java Authentication Demo             ║");
        System.out.println("╚════════════════════════════════════════════╝");
        System.out.println("\nPlease enter your credentials to login.");
        System.out.println("You have " + MAX_ATTEMPTS + " attempts.\n");
    }
    
    /**
     * Perform login process with 3 attempts
     * @return true if login successful, false otherwise
     */
    private static boolean performLogin() {
        Scanner scanner = new Scanner(System.in);
        Console console = System.console();
        
        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            System.out.println("─────────────────────────────────────────────");
            System.out.println("Attempt " + attempt + " of " + MAX_ATTEMPTS);
            System.out.println("─────────────────────────────────────────────");
            
            // Get username
            System.out.print("Username: ");
            String username = scanner.nextLine().trim();
            
            // Comment after username entry
            if (username.isEmpty()) {
                System.out.println("⚠ Comment: Username cannot be empty.");
                continue;
            } else {
                System.out.println("✓ Comment: Username received.");
            }
            
            // Get password (with masking)
            String password = getPasswordWithMasking(scanner, console);
            
            // Comment after password entry
            if (password.isEmpty()) {
                System.out.println("⚠ Comment: Password cannot be empty.");
                continue;
            } else {
                System.out.println("✓ Comment: Password received.");
            }
            
            // Validate credentials
            System.out.println("⌛ Comment: Validating credentials...");
            
            if (validateCredentials(username, password)) {
                System.out.println("✓ Comment: Credentials validated successfully!");
                return true;
            } else {
                int remainingAttempts = MAX_ATTEMPTS - attempt;
                System.out.println("✗ Comment: Invalid username or password.");
                
                if (remainingAttempts > 0) {
                    System.out.println("⚠ Comment: You have " + remainingAttempts + 
                                     " attempt(s) remaining.\n");
                } else {
                    System.out.println("⚠ Comment: No attempts remaining.");
                }
            }
        }
        
        scanner.close();
        return false;
    }
    
    /**
     * Get password input with masking (displays * for each character)
     * @param scanner Scanner for input
     * @param console Console for secure input (if available)
     * @return The entered password
     */
    private static String getPasswordWithMasking(Scanner scanner, Console console) {
        // If console is available, use it for secure password input
        if (console != null) {
            char[] passwordChars = console.readPassword("Password: ");
            return new String(passwordChars);
        } else {
            // Fallback: Manual masking using thread
            System.out.print("Password: ");
            return maskPassword(scanner);
        }
    }
    
    /**
     * Manually mask password input with * characters
     * This method creates a separate thread to print * while reading input
     * @param scanner Scanner for input
     * @return The entered password
     */
    private static String maskPassword(Scanner scanner) {
        final StringBuilder password = new StringBuilder();
        final boolean[] stopMasking = {false};
        
        // Create masking thread
        Thread maskingThread = new Thread(() -> {
            while (!stopMasking[0]) {
                System.out.print("\b*"); // Print backspace then asterisk
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        
        try {
            maskingThread.start();
            
            // Read password character by character
            int ch;
            while ((ch = System.in.read()) != '\n' && ch != '\r') {
                if (ch == '\b' || ch == 127) { // Backspace
                    if (password.length() > 0) {
                        password.deleteCharAt(password.length() - 1);
                    }
                } else if (ch >= 32 && ch <= 126) { // Printable characters
                    password.append((char) ch);
                }
            }
        } catch (Exception e) {
            // If masking fails, fall back to normal input
            return scanner.nextLine();
        } finally {
            stopMasking[0] = true;
            try {
                maskingThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println(); // New line after password
        }
        
        return password.toString();
    }
    
    /**
     * Validate username and password
     * @param username The entered username
     * @param password The entered password
     * @return true if credentials are valid, false otherwise
     */
    private static boolean validateCredentials(String username, String password) {
        // Simulate authentication delay (real system would query database)
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        return VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password);
    }
    
    /**
     * Display success screen with system information
     */
    private static void displaySuccessScreen() {
        System.out.println("\n┌────────────────────────────────────────────┐");
        System.out.println("│ Session Information:                       │");
        System.out.println("├────────────────────────────────────────────┤");
        System.out.println("│ User:      " + VALID_USERNAME + "                            │");
        System.out.println("│ Status:    Active                          │");
        System.out.println("│ Time:      " + java.time.LocalTime.now().toString().substring(0, 8) + "                              │");
        System.out.println("│ Date:      " + java.time.LocalDate.now() + "                    │");
        System.out.println("└────────────────────────────────────────────┘");
        
        System.out.println("\n📝 Valid Credentials for Testing:");
        System.out.println("   Username: admin");
        System.out.println("   Password: password123");
    }
    
    /**
     * BONUS: Alternative simple version using Scanner only
     * Uncomment main2 and comment main above to use this version
     */
    @SuppressWarnings("unused")
    private static void main2(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int attempts = 0;
        boolean loggedIn = false;
        
        System.out.println("=== LOGIN SYSTEM ===");
        System.out.println("You have 3 attempts to login.\n");
        
        while (attempts < MAX_ATTEMPTS && !loggedIn) {
            attempts++;
            System.out.println("Attempt " + attempts + " of " + MAX_ATTEMPTS);
            
            // Username input
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.println("// Comment: Username entered");
            
            // Password input with simple masking
            System.out.print("Enter password: ");
            String password = readPasswordSimple(scanner);
            System.out.println("// Comment: Password entered (characters masked with *)");
            
            // Validation
            System.out.println("// Comment: Validating credentials...");
            
            if (VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password)) {
                loggedIn = true;
                System.out.println("// Comment: Login successful!");
                System.out.println("\n✓ ACCESS GRANTED!");
            } else {
                System.out.println("// Comment: Invalid credentials");
                int remaining = MAX_ATTEMPTS - attempts;
                if (remaining > 0) {
                    System.out.println("// Comment: " + remaining + " attempts remaining\n");
                }
            }
        }
        
        if (!loggedIn) {
            System.out.println("\n✗ ACCESS DENIED - Account locked");
        }
        
        scanner.close();
    }
    
    /**
     * Simple password reading with echo to console
     */
    private static String readPasswordSimple(Scanner scanner) {
        // Note: This is a simplified version that just echoes *
        // For real masking, use the maskPassword() method above
        String password = scanner.nextLine();
        
        // Display asterisks for visual effect (after input)
        System.out.print("// Entered: ");
        for (int i = 0; i < password.length(); i++) {
            System.out.print("*");
        }
        System.out.println();
        
        return password;
    }
}
