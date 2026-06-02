import java.util.*;
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

    public void inorder() {
        if(root==null){
         System.out.println("List is empty! Please add book first.");
         return;
        }
        inorderRec(root);
    }

    private void inorderRec(Book root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.println("ISBN: " + root.isbn + " Title: " + root.title);
            inorderRec(root.right);
        }
    }
    
    public void delete(int i) {
        root = deleteRec(root, i);
    }

    private Book deleteRec(Book root, int i) {
        if (root == null){
            return root;
        }

        if (i < root.isbn){
            root.left = deleteRec(root.left, i);
        }
        else if (i > root.isbn){
            root.right = deleteRec(root.right, i);
        }
        else {
            if (root.left == null){
                return root.right;
            }
            else if (root.right == null){
                return root.left;
            }
           Book successor = findMin(root.right);

           root.isbn = successor.isbn;
           root.title = successor.title;
           root.author = successor.author;

           root.right = deleteRec(root.right, successor.isbn);
        }

        return root;
    }

    private Book findMin(Book root) {
      while (root.left != null) {
        root = root.left;
      }
        return root;
    }   

    /**
     * Public method to get the total number of books currently in the catalogue.
     * This showcases recursive tree traversal.
     */
    public int getTotalBooksCount() {
        return countNodes(root);
    }

    /**
     * Private recursive helper to count nodes in the BST.
     * Efficiency: O(n) as it visits every node once.
     */
    private int countNodes(Book current) {
        if (current == null) {
            return 0;
        }
        return countNodes(current.left) + countNodes(current.right) + 1;
    }

    /**
     * Public method to retrieve all books in the catalogue as a list.
     * Often used by LibraryManagement for external file operations.
     */
    public ArrayList<Book> getAllBooks(){
        ArrayList<Book> list = new ArrayList<>();
        collectInorder(root,list);
        return list;
    }

    /**
     *  Private recursive helper to collect nodes into an ArrayList
     *  Uses in-order traversal to ensure the list is sorted by ISBN
     */
    private void collectInorder(Book node, ArrayList<Book> list){
        if(node != null){
            collectInorder(node.left,list);
            list.add(node);
            collectInorder(node.right,list);
        }
    }
}
