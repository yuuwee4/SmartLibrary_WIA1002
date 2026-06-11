import java.util.*;

public class Main {
    
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        LoginManager loginManager = new LoginManager();
        SmartLibrary library = new SmartLibrary();

        while (true) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║     Smart Library Management System    ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose option (1-3): ");

            try {
                int mainChoice = Integer.parseInt(sc.nextLine().trim());
                User user = null;

                if (mainChoice == 3) {
                    System.out.println("Exiting Smart Library. Goodbye!");
                    break;
                } else if (mainChoice == 1) {
                    user = loginManager.authenticate(sc);
                } else if (mainChoice == 2) {
                    System.out.println("\n--- Registration ---");
                    System.out.println("1. Register as Admin");
                    System.out.println("2. Register as Student");
                    System.out.print("Choose role (1-2): ");
                    int roleChoice = Integer.parseInt(sc.nextLine().trim());
                    
                    if (roleChoice == 1) {
                        user = loginManager.register(sc, "admin");
                    } else if (roleChoice == 2) {
                        user = loginManager.register(sc, "student");
                    } else {
                        System.out.println("Invalid choice.");
                        continue;
                    }
                } else {
                    System.out.println("Invalid choice.");
                    continue;
                }

                if (user == null) {
                    System.out.println("Please try again.");
                    continue;
                }

                // Route to appropriate menu based on role
                if (user.getRole().equals("admin")) {
                    library.adminMenu(sc, user.getUsername());
                } else if (user.getRole().equals("student")) {
                    library.studentMenu(sc, user.getUsername());
                }

                System.out.println("\nLogging out...");
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        sc.close();
    }  
}
