import java.util.Stack;
/**
 * BorrowStack.java
 * Resposibility: Implement a Stack to keep track of books checked out (Most recent on top).
 */
public class BorrowStack {
    // private stack ensuring that not every method in stack is availabe
    private Stack<Book> stack = new Stack<>();

    /**
     * Add book in the last of the node in order to apply LIFO method
     * @param a
     */
    public void push(Book a){
        stack.push(a);
    }
    /**
     * Remove the most recent book from the stach
     */
    public Book pop(){
        return stack.pop();
    }
    /**
     * see the most recent book added
     * @return
     */
    public Book peek(){
        return stack.peek();
    }
    /**
     * Check whether stack empty/not
     * @return
     */
    public boolean isEmpty(){
        return stack.isEmpty();
    }
    /**
     * clear all the stack
     */
    public void clear(){
        stack.clear();
    }
    public int totalBooksBorrowed(){
        return stack.size();
    }
    
    /**
     * Showing all of the books borrowed starting from the most recent borrowed
     */
    public void show(){
        if(stack.isEmpty()){
            System.out.println("History is Empty");
        }else{
            int count = 0;
            for(int i = stack.size(); i >= 0;i--){
                Book a = stack.get(i);
                System.out.println("No." + count + "[ISBN: " + a.isbn + "] " + a.title);
                count++;
            }
        }
     }

    
    
}
