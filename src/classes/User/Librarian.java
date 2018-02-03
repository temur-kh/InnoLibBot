package classes.User;

import classes.Document.DocAddress;
import classes.Document.Document;

import javax.print.Doc;
import java.util.ArrayList;

public class Librarian extends User {

    public Librarian(long id, String name, String surname, String email, String phoneNumber, String address) {
        super(id, name, surname, email, phoneNumber, address);
    }

    public Librarian(long id, String name, String surname, String email, String phoneNumber) {
        super(id, name, surname, email, phoneNumber);
    }

    public boolean setAdmin(User user) {
        return true;
    }

    public boolean addDocument(Document document) {
        return true;
    }

    public boolean deleteDocument(long docId) {
        return true;
    }

    public boolean updateDocument(long docId) {
        return true;
    }

    public boolean addCopy(Document document, DocAddress address) {
        return true;
    }

    public boolean deleteCopy(long copyId) {
        return true;
    }

    public boolean updateCopy(long copyId) {
        return true;
    }

    public ArrayList<Document> getAllDocuments() {
        return null;
    }

    public ArrayList<Document> getOverdueDocuments() {
        return null;
    }

    public ArrayList<User> getAllUsers() {
        return null;
    }

    public ArrayList<User> getAllDeptorUsers() {
        return null;
    }

    //TODO
    public void checkUser(long userId) {
        return;
    }
}
