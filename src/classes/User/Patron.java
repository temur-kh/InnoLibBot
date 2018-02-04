package classes.User;

import classes.Document.Document;

import java.util.ArrayList;

/**
 * This class is extended from main class "User", it will have more functions(as book a document, or search book)
 * Also classes "Faculty" and "Student" are extended from this class(to know if user is Faculty or Student)
 *
 * Some functions will be implemented later.
 */
public class Patron extends User {

    //private boolean isFaculty;
    //ArrayList<Document> checkOutList;

    public Patron(Long id, String name, String surname, String email, String phoneNumber, String address) {
        super(id, name, surname, email, phoneNumber, address);
        //checkOutList = new ArrayList<>();
    }

    //TODO (will be implemented later)

    //
    public boolean checkOutDocument(Document document) {
        return true;
    }

    //
    public boolean returnDocument(Document document) {
        return true;
    }

    //
    public boolean buyDocument(Document document) {
        return true;
    }

    //
    public boolean isStudent() {
        return true;
    }

    /*public void setFaculty() {
        isFaculty = true;
    }*/

    //
    public boolean isFaculty() {
        return false;
    }

    //TODO
    public void searchBook() {
        return;
    }
}
