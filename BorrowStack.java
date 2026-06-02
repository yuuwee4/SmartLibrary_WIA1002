import java.util.Stack;
/**
 * BorrowStack.java
 * Resposibility: Implement a Stack to keep track of books checked out (Most recent on top).
 */
public class BorrowStack {
    // private stack ensuring that not every method in stack is availabe
    private Stack<Book> stack = new Stack<>();

    /**
     * Pushes a book onto the top of the stack, following the LIFO principle
     * @param a The book to be added to the borrowing history
     */
    public void push(Book a){
        stack.push(a);
    }
    /**
     * Removes and return the most recent book from the stack
     */
    public Book pop(){
        return stack.pop();
    }
    /**
     * Returns the most recently borrowed book withouth removing it from the stack
     * @return The most recent (top) Book object.
     */
    public Book peek(){
        return stack.peek();
    }
    /**
     * Check whether the borrowing history stack is empty
     * @return true if the stackk is empty
     */
    public boolean isEmpty(){
        return stack.isEmpty();
    }
    /**
     * Clears all borrowing history from the stack
     */
    public void clear(){
        stack.clear();
    }
    /**
     * Returns the total number of borrowed books currently in the stack
     * @return
     */
    public int totalBooksBorrowed(){
        return stack.size();
    }
    
    /**
     * Displays the borrowing history in a tabular format
     */
    public void show(){
        if(stack.isEmpty()){
            System.out.println("History is Empty");
        }else{
            System.out.println("\n------------------ Borrowing History ------------------");
            System.out.println("^*^*^*^*^*^*^^*^*^*^*^*^*^*^*^*^^*^*^*^*^*^*^^*^*^*^*^*");
            System.out.printf("| %-4s | %-15s | %-25s |\n","No.","ISBN","Title");
            System.out.println("^*^*^*^*^*^*^^*^*^*^*^*^*^*^*^*^^*^*^*^*^*^*^^*^*^*^*^*");
            int count = 1;
            for(int i = stack.size() -1; i >= 0;i--){
                Book a = stack.get(i);
                System.out.printf("| %-4s | %-15d | %-25s |\n",count + ".",a.isbn,a.title);
                count++;
            }
        }
     } 
}
