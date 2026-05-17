import java.util.Scanner;

public class SmartLibrary implements LibraryADT {

    private BookBST catalogue = new BookBST();
    private BorrowStack history = new BorrowStack();
    
    @Override
    public void addBook(int isbn, String title, String author) {
       catalogue.insert(isbn, title, author);
       System.out.println("Success! "+title+" has been added to the catalogue.");
    }

    @Override
    public void searchBook(int isbn) {
        Book b = catalogue.search(isbn);
        if ( b != null ){
            System.out.println("Book Found!");
            System.out.println("ISBN : "+b.isbn);
            System.out.println("Title : "+b.title);
            System.out.println("Author : "+b.author);
        } else {
            System.out.println("Error! Book with ISBN "+isbn+" not found.");
        }
    }

    @Override
    public void borrowBook(int isbn) {
       Book b = catalogue.search(isbn);
       if (b != null){
           history.push(b);
           System.out.println("Success! You have borrowed "+b.title+".");
       } else {
           System.out.println("Error! Cannot borrow. Book is not in catalogue.");
       }
    }

    @Override
    public void viewLatestHistory() {
        System.out.println("Borrowing History (Most Recent) : ");
        history.show();
    }
    
    public void runMenu() {
        Scanner sc = new Scanner(System.in);
        while (true){
            System.out.println("^*^*^*^*^*^*^^*^*^*^*^*^*^*^*^*^");
            System.out.println("Welcome to Smart Library!");
            System.out.println("^*^*^*^*^*^*^^*^*^*^*^*^*^*^*^*^");
            System.out.println("1. Add Book");
            System.out.println("2. Search Book");
            System.out.println("3. Borrow Book");
            System.out.println("4. View History");
            System.out.println("5. Exit");
            System.out.println("Please enter your choice : ");
            
            try {
                int choice = Integer.parseInt(sc.nextLine().trim());
                
                if (choice == 5){
                    System.out.println("Exiting Smart Library System. Goodbye!");
                    break;
                }
                
                handleChoice(choice, sc);
                
            } catch (NumberFormatException e){
                System.out.println("Invalid input. Please enter a valid number (1-5).");
            }
                    
        }
        sc.close();
    }
    
   private void handleChoice(int choice, Scanner sc){
       try {
           switch (choice){
               case 1 :
                   System.out.println("Enter ISBN (Integers only) : ");
                   int isbn = Integer.parseInt(sc.nextLine().trim());
                   System.out.println("Enter title : ");
                   String title = sc.nextLine().trim();
                   System.out.println("Enter author : ");
                   String author = sc.nextLine().trim();
                   
                   if (title.isEmpty() || author.isEmpty()){
                       System.out.println("Error! Title and author cannot be empty!");
                   } else {
                       addBook(isbn, title, author);
                   }
                   break;
                   
               case 2 :
                   System.out.println("Enter ISBN to search : ");
                   int searchIsbn = Integer.parseInt(sc.nextLine().trim());
                   searchBook(searchIsbn);
                   break;
                   
               case 3 :
                   System.out.println("Enter ISBN to borrow : ");
                   int borrowIsbn = Integer.parseInt(sc.nextLine().trim());
                   borrowBook(borrowIsbn);
                   break;
                   
               case 4 :
                   viewLatestHistory();
                   break;
                   
               default :
                   System.out.println("Invalid choice. Please select an option between 1-5.");
           }
       } catch (NumberFormatException e){
           System.out.println("Error! ISBN must be a valid integer number. Operation canceled.");
       }
   }
    
}
