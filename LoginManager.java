import java.io.*;
import java.util.*;

public class LoginManager {
    private ArrayList<User> users = new ArrayList<>();
    private String credentialsFile = "credentials.txt";

    public LoginManager() {
        loadCredentials();
    }

    private void loadCredentials() {
        try (BufferedReader br = new BufferedReader(new FileReader(credentialsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    users.add(new User(parts[0], parts[1], parts[2]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("credentials.txt not found. Creating default credentials...");
            createDefaultCredentials();
            loadCredentials();
        } catch (IOException e) {
            System.out.println("Error reading credentials: " + e.getMessage());
        }
    }

    private void createDefaultCredentials() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(credentialsFile))) {
            System.out.println("Empty credentials.txt created. Please register.");
        } catch (IOException e) {
            System.out.println("Error creating credentials file: " + e.getMessage());
        }
    }

    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public void displayLoginPage() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║     Smart Library Management System    ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("\nLogin Options:");
        System.out.println("1. Admin Login");
        System.out.println("2. Student Login");
        System.out.println("3. Exit");
        System.out.print("Choose option (1-3): ");
    }

    public User authenticate(Scanner sc) {
        displayLoginPage();
        try {
            int choice = Integer.parseInt(sc.nextLine().trim());
            
            if (choice == 3) {
                return null;
            }

            if (choice < 1 || choice > 3) {
                System.out.println("Invalid choice.");
                return null;
            }

            System.out.print("Enter username: ");
            String username = sc.nextLine().trim();
            System.out.print("Enter password: ");
            String password = sc.nextLine().trim();

            User user = login(username, password);
            if (user != null) {
                String expectedRole = (choice == 1) ? "admin" : "student";
                if (user.getRole().equals(expectedRole)) {
                    System.out.println("\n✓ Login successful! Welcome, " + username + "!");
                    Thread.sleep(1500);
                    return user;
                } else {
                    System.out.println("✗ Invalid credentials for this role.");
                }
            } else {
                System.out.println("✗ Invalid username or password.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return null;
    }

    public User register(Scanner sc, String role) {
        System.out.print("Enter username: ");
        String username = sc.nextLine().trim();

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("✗ Username already exists. Please choose another.");
                return null;
            }
        }

        System.out.print("Enter password: ");
        String password = sc.nextLine().trim();
        System.out.print("Confirm password: ");
        String confirmPassword = sc.nextLine().trim();

        if (!password.equals(confirmPassword)) {
            System.out.println("✗ Passwords do not match.");
            return null;
        }

        if (password.length() < 4) {
            System.out.println("✗ Password must be at least 4 characters.");
            return null;
        }

        User newUser = new User(username, password, role);
        users.add(newUser);
        saveCredentials();

        System.out.println("✓ Registration successful! Welcome, " + username + "!");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return newUser;
    }

    private void saveCredentials() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(credentialsFile))) {
            for (User user : users) {
                writer.println(user.toString());
            }
        } catch (IOException e) {
            System.out.println("Error saving credentials: " + e.getMessage());
        }
    }
}
