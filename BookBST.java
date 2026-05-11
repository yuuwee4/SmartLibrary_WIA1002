/**
 * BookBST.java
 * Responsibility: Manages the collection of books using a Binary Search Tree (BST)
 * to ensure O(log n) search efficiency.
 */

public class BookBST {
    // Private root node to ensure "Information Hiding"
    private Book root;

    /**
     * Public method to add a new book to the catalogue.
     * This acts as the entry point for the Admin Logic.
     */
    public void insert(int isbn, String title, String author) {
        // Update root by calling the private recursive method
        root = ins(root, isbn, title, author);
    }

    /**
     * Private helper method to handle recursive insertion.
     * Efficiency: O(log n) for a balanced tree.
     */
    private Book ins(Book current, int i, String t, String a) {
        // Base Case: If we reach a null position, create a new Book node
        if (current == null) {
            return new Book(i, t, a);
        }

        // Recursive Step: Decide to traverse left or right based on ISBN
        if (i < current.isbn) {
            // New ISBN is smaller, go to the left subtree
            current.left = ins(current.left, i, t, a);
        } else if (i > current.isbn) {
            // New ISBN is larger, go to the right subtree
            current.right = ins(current.right, i, t, a);
        }

        // Return the current node to keep the tree structure intact
        return current;
    }

    /**
     * Public method to find a book by its ISBN.
     * Provided for the Record Finder and Admin Logic.
     */
    public Book search(int i) {
        // Start the recursive search from the root
        return sea(root, i);
    }

    /**
     * Private recursive search method.
     * Efficiency: O(log n). 
     */
    private Book sea(Book r, int i) {
       // Base Case: Not found (null) or ISBN matches current node
       if (r == null || r.isbn == i) {
        return r;
       }

       // Recursive Search Step using ternary operator for clean logic 
        // If target is smaller, search left; otherwise, search right.
        return (i < r.isbn) ? sea(r.left, i) : sea(r.right, i);
    }
}
