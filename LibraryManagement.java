import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class LibraryManagement {
    public void loadFromFile(String fileName, BookBST catalogue){
        try(Scanner InputStream = new Scanner(new FileInputStream(fileName))){

            while(InputStream.hasNextLine()){
                String line = InputStream.nextLine();
                String[] word = line.split(",");
                
                int isbn = Integer.parseInt(word[0].trim());
                String title = word[1].trim();
                String author = word[2].trim();

                catalogue.insert(isbn,title,author);

            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void saveToFile(String fileName, BookBST catalogue){
        try(PrintWriter wr = new PrintWriter(new FileOutputStream(fileName))){
            
            ArrayList<Book> allBooks = catalogue.getAllBooks();

            for(int i= 0; i < allBooks.size();i++){
                Book a = allBooks.get(i);
                wr.println(a.isbn + "," + a.title + "," + a.author); //overwrite
            }
        }catch(IOException e){
            System.out.println("FAIL");
        }
    }

    public ArrayList<Book> searchByTitle(String Title, BookBST catalogue){
        ArrayList<Book> allBooks = catalogue.getAllBooks();
        ArrayList<Book> newList = new ArrayList<>();
        
        for(int i =0; i < allBooks.size();i++){
            Book a = allBooks.get(i);
            
            if(a.getTitle().toLowerCase().contains(Title.toLowerCase())){
                newList.add(a);
            }
        }
        return newList;
        
    }
    public void displayAll(BookBST catalogue){
        ArrayList<Book> allBooks = catalogue.getAllBooks();

        System.out.println("\n^*^*^*^*^*^*^^*^*^*^*^*^*^*^*^*^^*^*^*^*^*^*^^*^*^*^*^**^^*^*^*^*^*^*^^*^*^*^*^*");
        System.out.printf("| %-4s | %-10s | %-45s | %-20s |\n","No.","ISBN","Title","Author");
        System.out.println("^*^*^*^*^*^*^^*^*^*^*^*^*^*^*^*^^*^*^*^*^*^*^^*^*^*^*^**^^*^*^*^*^*^*^^*^*^*^*^*");
        int count = 1;
        for(int i = 0 ; i < allBooks.size(); i++){
            Book a = allBooks.get(i);
            System.out.printf("| %-4s | %-10d | %-45s | %-20s |\n",count + ".",a.isbn,a.title,a.author);
            count++;
            }
    }



}
