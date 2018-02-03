package classes.User;

import classes.Document.Document;

import java.util.ArrayList;

public class Patron extends User {

    //private boolean isFaculty;
    ArrayList<Document> checkOutList;

    public Patron(Long id, String name, String surname, String email, String phoneNumber, String address) {
        super(id, name, surname, email, phoneNumber, address);
        checkOutList = new ArrayList<>();
    }

    public Patron(long id, String name, String surname, String email, String phoneNumber) {
        super(id, name, surname, email, phoneNumber);
    }

    public boolean checkOutDocument(Document document) {
        return true;
    }

    public boolean returnDocument(Document document) {
        return true;
    }

    public boolean buyDocument(Document document) {
        return true;
    }

    public boolean isStudent() {
        return true;
    }

    /*public void setFaculty() {
        isFaculty = true;
    }*/

    public boolean isFaculty() {
        return false;
    }

    //TODO
    public void searchBook() {
        return;
    }
}
