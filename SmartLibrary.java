import java.util.*;


public class SmartLibrary implements LibraryADT {

    private BookBST catalogue = new BookBST();
    private BorrowStack history = new BorrowStack();
    private RequestQueue requestQueue = new RequestQueue();
    private HashMap<String, StudentBorrowed> studentBorrowedMap = new HashMap<>();
    
    private LibraryManagement fileManager = new LibraryManagement();
    private String fileName = "book_library.txt";

    @Override
    public void addBook(int isbn, String title, String author) {
        catalogue.insert(isbn, title, author);
        System.out.println("Success! "+title+" has been added to the catalogue.");
    }
    
    @Override
    public void searchBook(String name){
        ArrayList<Book> a = fileManager.searchByTitle(name,catalogue);
        if(a.isEmpty()){
            System.out.println("\nNo item found");
        }else{
            System.out.println("\nBook found!");
            for(int i =0; i < a.size();i++){
                Book s = a.get(i);
                System.out.println("ISBN : " + s.getIsbn());
                System.out.println("Title : "+ s.getTitle());
                System.out.println("Author : " + s.getAuthor());
                System.out.println("-----------------------");
            }
        }
    }

    @Override
    public void searchBook(int isbn) {
        Book b = catalogue.search(isbn);
        if ( b != null ){
            System.out.println("Book Found!");
            System.out.println("ISBN : "+b.getIsbn());
            System.out.println("Title : "+b.getTitle());
            System.out.println("Author : "+b.getAuthor());
        } else {
            System.out.println("Error! Book with ISBN "+isbn+" not found.");
        }
    }

    @Override
    public void borrowBook(int isbn) {
       Book b = catalogue.search(isbn);
       if (b != null){
            Book borrowedBook = new Book(b.getIsbn(), b.getTitle(), b.getAuthor());
            history.push(borrowedBook);
            catalogue.delete(isbn);
            System.out.println("Success! You have borrowed "+b.getTitle()+".");
       } else {
           System.out.println("Error! Cannot borrow. Book is not in catalogue.");
       }
    }

    @Override
    public void viewLatestHistory() {
        System.out.println("Borrowing History (Most Recent) : ");
        history.show();
    }

    @Override
    public void removeBookFromCatalogue(int isbn){
        Book b = catalogue.search(isbn);
        if (b != null){
            catalogue.delete(isbn);
            System.out.println("Success! "+b.getTitle()+" has been removed from the library catalogue.");
        } else {
            System.out.println("Error! Cannot remove. Book with ISBN "+isbn+" does not exist.");
        }
    }

    public void returnBook(int isbn) {
        if (history.isEmpty()) {
            System.out.println("Error! No borrowing history. Cannot return book.");
            return;
        }

        Book returned = history.pop();
        if (returned.getIsbn() == isbn) {
            catalogue.insert(isbn, returned.getTitle(), returned.getAuthor());
            System.out.println("Success! "+returned.getTitle()+" has been returned to the catalogue.");
        } else {
            history.push(returned);
            System.out.println("Error! The book being returned (ISBN: "+isbn+") does not match the most recently borrowed book.");
        }
    }

    public void viewAllHistory() {
        System.out.println("\n--- Complete Borrowing History ---");
        history.show();
    }

    public void adminMenu(Scanner sc, String adminName) {
        fileManager.loadFromFile(fileName, catalogue);
        boolean running = true;
        
        while (running) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║           ADMIN MENU ("+adminName+")          ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book from Catalogue");
            System.out.println("3. View All Books");
            System.out.println("4. Approve Borrow Request");
            System.out.println("5. Approve Return Request");
            System.out.println("6. View Borrow History");
            System.out.println("7. Total Books");
            System.out.println("8. Logout");
            System.out.print("Choose option: ");
            
            try {
                int choice = Integer.parseInt(sc.nextLine().trim());
                
                switch(choice) {
                    case 1:
                        System.out.print("Enter ISBN: ");
                        int isbn = Integer.parseInt(sc.nextLine().trim());
                        
                        if (catalogue.search(isbn) != null) {
                            System.out.println("Error! ISBN already exists.");
                            sleep();
                            break;
                        }
                        
                        System.out.print("Enter title: ");
                        String title = sc.nextLine().trim();
                        System.out.print("Enter author: ");
                        String author = sc.nextLine().trim();
                        
                        if (!title.isEmpty() && !author.isEmpty()) {
                            addBook(isbn, title, author);
                        } else {
                            System.out.println("Error! Title and author cannot be empty.");
                        }
                        sleep();
                        break;
                        
                    case 2:
                        System.out.print("Enter ISBN to remove: ");
                        int removeIsbn = Integer.parseInt(sc.nextLine().trim());
                        removeBookFromCatalogue(removeIsbn);
                        sleep();
                        break;
                        
                    case 3:
                        if (catalogue.isEmpty()) {
                            System.out.println("Catalogue is empty.");
                        } else {
                            fileManager.displayAll(catalogue);
                        }
                        sleep();
                        break;
                        
                    case 4:
                        approveBorrowRequest(sc);
                        sleep();
                        break;
                        
                    case 5:
                        approveReturnRequest(sc);
                        sleep();
                        break;
                        
                    case 6:
                        viewLatestHistory();
                        sleep();
                        break;
                        
                    case 7:
                        System.out.println("Total books in catalogue: " + catalogue.getTotalBooksCount());
                        sleep();
                        break;
                        
                    case 8:
                        fileManager.saveToFile(fileName, catalogue);
                        running = false;
                        break;
                        
                    default:
                        System.out.println("Invalid option.");
                        sleep();
                }
            } catch (NumberFormatException e) {
                System.out.println("Error! Invalid input.");
            }
        }
    }

    public void studentMenu(Scanner sc, String studentName) {
        fileManager.loadFromFile(fileName, catalogue);
        boolean running = true;
        
        while (running) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║         STUDENT MENU ("+studentName+")        ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("1. Search Book");
            System.out.println("2. View All Books");
            System.out.println("3. View Approved Books");
            System.out.println("4. Request to Borrow");
            System.out.println("5. Request to Return");
            System.out.println("6. Logout");
            System.out.print("Choose option: ");
            
            try {
                int choice = Integer.parseInt(sc.nextLine().trim());
                
                switch(choice) {
                    case 1:
                        if (catalogue.isEmpty()) {
                            System.out.println("Catalogue is empty.");
                            sleep();
                            break;
                        }
                        
                        System.out.println("Search options:");
                        System.out.println("1. Search by title\n2. Search by ISBN");
                        System.out.print("Choose option: ");
                        int searchChoice = Integer.parseInt(sc.nextLine().trim());
                        
                        if (searchChoice == 1) {
                            System.out.print("Enter title: ");
                            String search = sc.nextLine().trim();
                            searchBook(search);
                        } else if (searchChoice == 2) {
                            System.out.print("Enter ISBN: ");
                            int searchIsbn = Integer.parseInt(sc.nextLine().trim());
                            searchBook(searchIsbn);
                        } else {
                            System.out.println("Invalid option.");
                        }
                        sleep();
                        break;
                        
                    case 2:
                        if (catalogue.isEmpty()) {
                            System.out.println("Catalogue is empty.");
                        } else {
                            fileManager.displayAll(catalogue);
                        }
                        sleep();
                        break;
                        
                   case 3:
                        requestQueue.displayApprovedBorrows(studentName);
                        ArrayList<BorrowRequest> approved = requestQueue.getApprovedRequestsByStudent(studentName);
                        if (approved.isEmpty()) {
                            sleep();
                            break;
                        }
                        System.out.print("\nEnter book number to confirm pickup (0 to skip): ");
                        try {
                            int pickupChoice = Integer.parseInt(sc.nextLine().trim());
                            if (pickupChoice > 0) {
                                if (pickupChoice <= approved.size()) {
                                    BorrowRequest approvedReq = approved.get(pickupChoice - 1);
                                    borrowBook(approvedReq.getIsbn());
                                    
                                    StudentBorrowed studentBorrow = studentBorrowedMap.getOrDefault(studentName, new StudentBorrowed(studentName));
                                    studentBorrow.addBook(approvedReq.getIsbn(), approvedReq.getTitle(), approvedReq.getAuthor());
                                    studentBorrowedMap.put(studentName, studentBorrow);
                                    
                                    approvedReq.setStatus("PICKED_UP");
                                    fileManager.saveToFile(fileName, catalogue); // <-- ADD THIS
                                    System.out.println("Book borrowed successfully!");
                                } else {
                                    System.out.println("Invalid book number.");
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input.");
                        }
                        sleep();
                        break;
                        
                    case 4:
                        if (catalogue.isEmpty()) {
                            System.out.println("No books available to borrow.");
                            sleep();
                            break;
                        }
                        
                        fileManager.displayAll(catalogue);
                        System.out.print("Enter ISBN to request: ");
                        int borrowIsbn = Integer.parseInt(sc.nextLine().trim());
                        
                        Book bookToBorrow = catalogue.search(borrowIsbn);
                        if (bookToBorrow != null) {
                            BorrowRequest req = new BorrowRequest(studentName, borrowIsbn, bookToBorrow.getTitle(), bookToBorrow.getAuthor(), "BORROW");
                            requestQueue.addRequest(req);
                            System.out.println("Borrow request submitted. Waiting for admin approval...");
                        } else {
                            System.out.println("Error! Book with ISBN " + borrowIsbn + " not found in catalogue.");
                        }
                        sleep();
                        break;
                        
                    case 5:
                        StudentBorrowed borrowed = studentBorrowedMap.get(studentName);
                        if (borrowed == null || borrowed.isEmpty()) {
                            System.out.println("You have no borrowed books to return.");
                            sleep();
                            break;
                        }
                        
                        borrowed.displayBorrowedBooks();
                        System.out.print("\nEnter book number to return (0 to skip): ");
                        try {
                            int returnChoice = Integer.parseInt(sc.nextLine().trim());
                            if (returnChoice > 0) {
                                StudentBorrowed.BorrowedBook bookToReturn = borrowed.getBookByIndex(returnChoice - 1);
                                if (bookToReturn != null) {
                                    BorrowRequest returnReq = new BorrowRequest(studentName, bookToReturn.isbn, bookToReturn.title, bookToReturn.author, "RETURN");
                                    requestQueue.addRequest(returnReq);
                                    System.out.println("✓ Return request submitted. Waiting for admin approval...");
                                } else {
                                    System.out.println("Invalid book number.");
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input.");
                        }
                        sleep();
                        break;
                        
                    case 6:
                        running = false;
                        break;
                        
                    default:
                        System.out.println("Invalid option.");
                        sleep();
                }
            } catch (NumberFormatException e) {
                System.out.println("Error! Invalid input.");
            }
        }
    }

    private void approveBorrowRequest(Scanner sc) {
        ArrayList<BorrowRequest> pending = requestQueue.getPendingBorrowRequests();
        
        if (pending.isEmpty()) {
            System.out.println("No pending borrow requests.");
            return;
        }

        requestQueue.displayPendingRequests("BORROW");
        System.out.print("\nEnter request number to approve (0 to cancel): ");
        
        try {
            int choice = Integer.parseInt(sc.nextLine().trim());
            if (choice == 0) return;
            
            if (choice > 0 && choice <= pending.size()) {
                BorrowRequest req = pending.get(choice - 1);
                
                System.out.println("\n--- Approve/Deny Request ---");
                System.out.println("Request from: " + req.getStudentName());
                System.out.println("Book: " + req.getTitle() + " (ISBN: " + req.getIsbn() + ")");
                System.out.println("1. Approve");
                System.out.println("2. Deny");
                System.out.print("Choose action: ");
                
                int action = Integer.parseInt(sc.nextLine().trim());
                
            if (action == 1) {
                req.setStatus("APPROVED");
                // Auto-deny all other pending requests for the same ISBN
                requestQueue.denyOtherPendingBorrowRequests(req.getIsbn(), req.getStudentName());
                System.out.println("✓ Request approved! Student can now pick up the book.");
            } else if (action == 2) {
                req.setStatus("DENIED");
                System.out.println("✓ Request denied.");
            }
            } else {
                System.out.println("Invalid request number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error! Invalid input.");
        }
    }

    private void approveReturnRequest(Scanner sc) {
        ArrayList<BorrowRequest> pending = requestQueue.getPendingReturnRequests();
        
        if (pending.isEmpty()) {
            System.out.println("No pending return requests.");
            return;
        }

        requestQueue.displayPendingRequests("RETURN");
        System.out.print("\nEnter request number to approve (0 to cancel): ");
        
        try {
            int choice = Integer.parseInt(sc.nextLine().trim());
            if (choice == 0) return;
            
            if (choice > 0 && choice <= pending.size()) {
                BorrowRequest req = pending.get(choice - 1);
                
                System.out.println("\n--- Approve/Deny Return Request ---");
                System.out.println("Request from: " + req.getStudentName());
                System.out.println("Book: " + req.getTitle() + " (ISBN: " + req.getIsbn() + ")");
                System.out.println("1. Approve Return");
                System.out.println("2. Deny Return");
                System.out.print("Choose action: ");
                
                int action = Integer.parseInt(sc.nextLine().trim());
                
                        if (action == 1) {
            addBook(req.getIsbn(), req.getTitle(), req.getAuthor());
            
            StudentBorrowed borrowed = studentBorrowedMap.get(req.getStudentName());
            if (borrowed != null) {
                borrowed.removeBook(req.getIsbn());
            }
            
            req.setStatus("APPROVED");
            fileManager.saveToFile(fileName, catalogue); // <-- ADD THIS
            System.out.println("✓ Return approved! Book returned to catalogue.");
        }else if (action == 2) {
                    req.setStatus("DENIED");
                    System.out.println("✓ Return denied.");
                }
            } else {
                System.out.println("Invalid request number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error! Invalid input.");
        }
    }

    public void sleep(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
