import java.util.ArrayList;

public class RequestQueue {
    private ArrayList<BorrowRequest> requests = new ArrayList<>();

    public void addRequest(BorrowRequest request) {
        requests.add(request);
    }

    public ArrayList<BorrowRequest> getPendingRequests() {
        ArrayList<BorrowRequest> pending = new ArrayList<>();
        for (BorrowRequest req : requests) {
            if (req.getStatus().equals("PENDING")) {
                pending.add(req);
            }
        }
        return pending;
    }

    public ArrayList<BorrowRequest> getPendingBorrowRequests() {
        ArrayList<BorrowRequest> pending = new ArrayList<>();
        for (BorrowRequest req : requests) {
            if (req.getStatus().equals("PENDING") && req.getRequestType().equals("BORROW")) {
                pending.add(req);
            }
        }
        return pending;
    }

    public ArrayList<BorrowRequest> getPendingReturnRequests() {
        ArrayList<BorrowRequest> pending = new ArrayList<>();
        for (BorrowRequest req : requests) {
            if (req.getStatus().equals("PENDING") && req.getRequestType().equals("RETURN")) {
                pending.add(req);
            }
        }
        return pending;
    }

    public ArrayList<BorrowRequest> getApprovedRequestsByStudent(String studentName) {
        ArrayList<BorrowRequest> approved = new ArrayList<>();
        for (BorrowRequest req : requests) {
            if (req.getStatus().equals("APPROVED") && req.getStudentName().equals(studentName)) {
                approved.add(req);
            }
        }
        return approved;
    }

    public void displayApprovedBorrows(String studentName) {
        ArrayList<BorrowRequest> approved = getApprovedRequestsByStudent(studentName);
        
        if (approved.isEmpty()) {
            System.out.println("No approved books waiting for you.");
            return;
        }

        System.out.println("\n--- Your Approved Books (Ready to Borrow) ---");
        System.out.println("^*^*^*^*^*^*^^*^*^*^*^*^*^*^*^*^^*^*^*^*^*^*^^*^*^*^*^**^^*^*^*^*^*^*^^*^*^*^*^*");
        System.out.printf("| %-3s | %-10s | %-45s | %-20s |\n", "No.", "ISBN", "Title", "Author");
        System.out.println("^*^*^*^*^*^*^^*^*^*^*^*^*^*^*^*^^*^*^*^*^*^*^^*^*^*^*^**^^*^*^*^*^*^*^^*^*^*^*^*");
        
        int count = 1;
        for (BorrowRequest req : approved) {
            System.out.printf("| %-3d | %-10d | %-45s | %-20s |\n", 
                count, req.getIsbn(), req.getTitle(), req.getAuthor());
            count++;
        }
    }

    public BorrowRequest getApprovedBorrowByIndex(String studentName, int index) {
        ArrayList<BorrowRequest> approved = getApprovedRequestsByStudent(studentName);
        if (index >= 0 && index < approved.size()) {
            return approved.get(index);
        }
        return null;
    }

    public boolean isEmpty() {
        return requests.isEmpty();
    }

    public int getTotalRequests() {
        return requests.size();
    }

    public void displayPendingRequests(String type) {
        ArrayList<BorrowRequest> pending = type.equals("BORROW") ? getPendingBorrowRequests() : getPendingReturnRequests();
        
        if (pending.isEmpty()) {
            System.out.println("No pending " + type.toLowerCase() + " requests.");
            return;
        }

        System.out.println("\n--- Pending " + type + " Requests ---");
        System.out.println("^*^*^*^*^*^*^^*^*^*^*^*^*^*^*^*^^*^*^*^*^*^*^^*^*^*^*^**^^*^*^*^*^*^*^^*^*^*^*^*");
        System.out.printf("| %-3s | %-15s | %-10s | %-45s | %-20s |\n", "No.", "Student", "ISBN", "Title", "Author");
        System.out.println("^*^*^*^*^*^*^^*^*^*^*^*^*^*^*^*^^*^*^*^*^*^*^^*^*^*^*^**^^*^*^*^*^*^*^^*^*^*^*^*");
        
        int count = 1;
        for (BorrowRequest req : pending) {
            System.out.printf("| %-3d | %-15s | %-10d | %-45s | %-20s |\n", 
                count, req.getStudentName(), req.getIsbn(), req.getTitle(), req.getAuthor());
            count++;
        }
    }

    public void denyOtherPendingBorrowRequests(int isbn, String approvedStudentName) {
    for (BorrowRequest req : requests) {
        if (req.getIsbn() == isbn
                && req.getRequestType().equals("BORROW")
                && req.getStatus().equals("PENDING")
                && !req.getStudentName().equals(approvedStudentName)) {
            req.setStatus("DENIED");
            System.out.println("! Auto-denied request for \"" + req.getTitle()
                    + "\" from " + req.getStudentName() + " (book already approved for another student).");
        }
    }
}
}
