public interface LibraryADT {
    
    void addBook(int isbn, String title, String author);
    
    void searchBook(int isbn);

    void searchBook(String title);
    
    void borrowBook(int isbn);
    
    void viewLatestHistory();

    void viewAllBooks();
    
    void removeBookFromCatalogue(int isbn);
}
