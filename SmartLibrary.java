import java.util.*;

public class SmartLibrary implements LibraryADT {

    private BookBST catalogue = new BookBST();
    private BorrowStack history = new BorrowStack();
    
    // Addition : Handles file operations to load and save data
    private LibraryManagement fileManager = new LibraryManagement();

    // Addition : Default text file for storing the library catalogue
    private String fileName = "book_library.txt";

    @Override
    public void addBook(int isbn, String title, String author) {
        
        catalogue.insert(isbn, title, author);
        System.out.println("Success! "+title+" has been added to the catalogue.");
    }
    
    // Addition : Searches for books by title using the file manager.
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
            //Create a copy for history
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
    
    public void runMenu() {
        fileManager.loadFromFile(fileName, catalogue);
        Scanner sc = new Scanner(System.in);
        while (true){
            System.out.println("\n^*^*^*^*^*^*^^*^*^*^*^*^*^*^*^*^");
            System.out.println("Welcome to Smart Library!");
            System.out.println("^*^*^*^*^*^*^^*^*^*^*^*^*^*^*^*^");
            System.out.println("1. Add Book");
            System.out.println("2. Search Book");
            System.out.println("3. Borrow Book");
            System.out.println("4. View History");
            System.out.println("5. Exit");
            System.out.print("Please enter your choice : ");
            
            try {
                int choice = Integer.parseInt(sc.nextLine().trim());
                
                if (choice == 5){
                    System.out.println("Exiting Smart Library System. Goodbye!");
                    fileManager.saveToFile("book_library.txt",catalogue);
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
                   System.out.print("Enter ISBN (Integers only) : ");
                   int isbn = Integer.parseInt(sc.nextLine().trim());
                   System.out.print("Enter title : ");

                   //check for duplicate ISBN
                   if (catalogue.search(isbn) != null) {
                       System.out.println("Error! ISBN " + isbn + " already exists.");
                       return;
                   }
                   
                   String title = sc.nextLine().trim();
                   System.out.print("Enter author : ");
                   String author = sc.nextLine().trim();
                   
                   if (title.isEmpty() || author.isEmpty()){
                       System.out.println("Error! Title and author cannot be empty!");
                   } else {
                       addBook(isbn, title, author);
                   }
                   break;
                   
               case 2 :
                   //Will stop operate and return to the menu if catalogue is empty
                   if(catalogue.isEmpty()){
                       System.out.println("Catalogue is empty. No record found.");
                       return;
                   }
                   
                   fileManager.displayAll(catalogue);
                   System.out.println("\nSearch options: ");
                   System.out.println("1. Search by title\n2. Search by ISBN");
                   System.out.print("Choose option (1 or 2) : ");
                   int userInput = Integer.parseInt(sc.nextLine().trim());
                   try{
                    switch(userInput){
                        case 1:
                            // Addition : Handles title search
                            System.out.print("Enter title to search : ");
                            String search = sc.nextLine().trim();
                            searchBook(search);
                            break;
                        case 2:
                            System.out.println("Enter ISBN to search : ");
                            int searchIsbn = Integer.parseInt(sc.nextLine().trim());
                            searchBook(searchIsbn);
                            break;
                        default: 
                            System.out.println("Invalid choice. Please select an option between 1-2");
                            break;
                    }
                   }catch(NumberFormatException e){
                        System.out.println("Error! Must be a valid integer number. Operation canceled.");
                   }
                   break;
                   
               case 3 :
                   //Will stop operate and return to the menu if catalogue is empty
                   if(catalogue.isEmpty()){
                       System.out.println("Books cannot be borrowed. Catalogue is empty.");
                       return;
                   }
                   
                   fileManager.displayAll(catalogue);
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
