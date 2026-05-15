import java.util.Stack;
public class BorrowStack {
    private Stack<Book> stack = new Stack<>();

    public void push(Book a){
        stack.push(a);
    }

    public void pop(){
        stack.pop();
    }
    public Book peek(){
        return stack.peek();
    }
     public void show(){
        if(stack.isEmpty()){
            System.out.println("History is Empty");
        }else{
            int count = 0;
            for(int i = stack.size()-1; i >= 0;i--){
                Book a = stack.get(i);
                System.out.println("No." + count + "[ISBN: " + a.isbn + "] " + a.title);
                count++;
            }
        }
     }

    
    
}
