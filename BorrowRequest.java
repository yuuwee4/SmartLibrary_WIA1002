public class BorrowRequest {
    private String studentName;
    private int isbn;
    private String title;
    private String author;
    private String requestType; // "BORROW" or "RETURN"
    private String status; // "PENDING", "APPROVED", "DENIED"
    private long timestamp;

    public BorrowRequest(String studentName, int isbn, String title, String author, String requestType) {
        this.studentName = studentName;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.requestType = requestType;
        this.status = "PENDING";
        this.timestamp = System.currentTimeMillis();
    }

    public String getStudentName() {
        return studentName;
    }

    public int getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getRequestType() {
        return requestType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return studentName + "," + isbn + "," + title + "," + author + "," + requestType + "," + status;
    }
}
