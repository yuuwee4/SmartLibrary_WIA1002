import java.util.ArrayList;

public class StudentBorrowed {
    private String studentName;
    private ArrayList<BorrowedBook> borrowedBooks = new ArrayList<>();

    public StudentBorrowed(String studentName) {
        this.studentName = studentName;
    }

    public static class BorrowedBook {
        public int isbn;
        public String title;
        public String author;

        public BorrowedBook(int isbn, String title, String author) {
            this.isbn = isbn;
            this.title = title;
            this.author = author;
        }
    }

    public String getStudentName() {
        return studentName;
    }

    public void addBook(int isbn, String title, String author) {
        borrowedBooks.add(new BorrowedBook(isbn, title, author));
    }

    public boolean removeBook(int isbn) {
        for (BorrowedBook book : borrowedBooks) {
            if (book.isbn == isbn) {
                borrowedBooks.remove(book);
                return true;
            }
        }
        return false;
    }

    public ArrayList<BorrowedBook> getBorrowedBooks() {
        return borrowedBooks;
    }

    public BorrowedBook getBookByIndex(int index) {
        if (index >= 0 && index < borrowedBooks.size()) {
            return borrowedBooks.get(index);
        }
        return null;
    }

    public boolean isEmpty() {
        return borrowedBooks.isEmpty();
    }

    public void displayBorrowedBooks() {
        if (borrowedBooks.isEmpty()) {
            System.out.println("You have no borrowed books.");
            return;
        }

        System.out.println("\n--- Your Borrowed Books ---");
        System.out.println("^*^*^*^*^*^*^^*^*^*^*^*^*^*^*^*^^*^*^*^*^*^*^^*^*^*^*^**^^*^*^*^*^*^*^^*^*^*^*^*");
        System.out.printf("| %-3s | %-10s | %-45s | %-20s |\n", "No.", "ISBN", "Title", "Author");
        System.out.println("^*^*^*^*^*^*^^*^*^*^*^*^*^*^*^*^^*^*^*^*^*^*^^*^*^*^*^**^^*^*^*^*^*^*^^*^*^*^*^*");
        
        int count = 1;
        for (BorrowedBook book : borrowedBooks) {
            System.out.printf("| %-3d | %-10d | %-45s | %-20s |\n", 
                count, book.isbn, book.title, book.author);
            count++;
        }
    }
}
