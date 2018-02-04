package classes.User;

import classes.Document.DocAddress;
import classes.Document.Document;

import javax.print.Doc;
import java.util.ArrayList;

/**
 * This class is extended from main class "User".
 * All librarians will have more opportunities, than Patrons.
 * One librarian(Admin) will be added at the very beginning, and librarian has opportunity to add more librarians.
 * Also librarian can add books, documents, magazines and so on.
 * Some functions will be implemented later.
 */
public class Librarian extends User {

    public Librarian(long id, String name, String surname, String email, String phoneNumber, String address) {
        super(id, name, surname, email, phoneNumber, address);
    }
    //TODO (will be implemented later)

    //opportunity to add new librarian
    public boolean setAdmin(User user) {
        return true;
    }

    //opportunity to add new document to database (id, title, authors, photoId, price, keywords)
    public boolean addDocument(Document document) {
        return true;
    }

    //opportunity to delete material by its id
    public boolean deleteDocument(long docId) {
        return true;
    }

    //if information about document has been changed(authors, keywords, photo),
    // this function will apply changes to document by id
    public boolean updateDocument(long docId) {
        return true;
    }

    //A library may have several copies of each document. Copies are stored in a
    //certain place inside the library, e.g., a room, level. For each copy we need
    //to know whether it is currently checked out and by whom.
    public boolean addCopy(Document document, DocAddress address) {
        return true;
    }

    //If copy has been
    public boolean deleteCopy(long copyId) {
        return true;
    }

    //the same as updateDocument, but for copy
    public boolean updateCopy(long copyId) {
        return true;
    }

    //shows list of all available documents at the moment(watch class "Documents")
    public ArrayList<Document> getAllDocuments() {
        return null;
    }

    //shows list of documents, that are not returned back in time
    public ArrayList<Document> getOverdueDocuments() {
        return null;
    }

    //shows list of all users from database(watch class "User")
    public ArrayList<User> getAllUsers() {
        return null;
    }

    //shows list of student, who have not returned documents in time
    public ArrayList<User> getAllDeptorUsers() {
        return null;
    }

    //TODO
    public void checkUser(long userId) {
        return;
    }
}
