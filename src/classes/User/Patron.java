package classes.User;

import classes.CheckOut;
import classes.Document.Book;
import classes.Document.Document;
import com.mongodb.BasicDBObject;
import database.CheckOutDB;
import database.PatronDB;
import database.SuperDatabase;
import services.Commands;
import services.Constants;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;

/**
 * This class is extended from main class "User", it will have more functions(as book a document, or search book)
 * Also classes "Faculty" and "Student" are extended from this class(to know if user is Faculty or Student)
 *
 * Some functions will be implemented later.
 */
public class Patron extends User {

    private boolean isFaculty;

    public Patron(Long id, String name, String surname, String email, String phoneNumber, String address) {
        this(id, name, surname, email, phoneNumber, address, false);
    }

    public Patron(Long id, String name, String surname, String email, String phoneNumber, String address, boolean isFaculty) {
        super(id, name, surname, email, phoneNumber, address);
        setFaculty(isFaculty);
    }

    public CheckOut checkOutDocument(Document document, String collection) throws NoSuchElementException, SecurityException {

        if (SuperDatabase.getObject(document.getId(), collection) == null || !document.hasFreeCopies())
            throw new NoSuchElementException();

        BasicDBObject query = new BasicDBObject("patron_id", this.getId()).append("doc_id", document.getId());
        CheckOut checkOut = CheckOutDB.getCheckOut(query);
        if (checkOut != null || ((document instanceof Book) && !((Book) document).canBeCheckedOut())) {
            throw new SecurityException();
        }

        Calendar today = new GregorianCalendar();
        Calendar deadline = new GregorianCalendar();

        int time;
        if (collection.equals(Constants.BOOK_COLLECTION)) {
            if (this.isFaculty) {
                time = Constants.BOOK_CHECK_OUT_LIMIT_FOR_FACULTY;
            } else if (((Book) document).isBestSeller()) {
                time = Constants.BEST_SELLER_CHECK_OUT_LIMIT;
            } else {
                time = Constants.BOOK_CHECK_OUT_LIMIT;
            }
        } else if (collection.equals(Constants.AVMATERIAL_COLLECTION)){
            time = Constants.AVMATERIAL_CHECK_OUT_LIMIT;
        } else {
            time = Constants.JOURNAL_CHECK_OUT_LIMIT;
        }
        deadline.add(Calendar.DAY_OF_MONTH, time);

        checkOut = new CheckOut(today, deadline, getId(), document.getId(), collection, document.getFreeCopy(true).getId());
        CheckOutDB.insertCheckOut(checkOut);

        return checkOut;
    }

    public void register() {
        PatronDB.insertPatron(this);
    }

    //TODO (will be implemented later)
    public boolean returnDocument(Document document) {
        return true;
    }

    //patron is student by default
    public boolean isStudent() {
        return !isFaculty;
    }

    //
    public boolean isFaculty() {
        return isFaculty;
    }

    public void setFaculty(boolean status) { isFaculty = status; }

    @Override
    public String getInfo() {
        String info = super.getInfo();
        info += "<strong>STATUS:</strong> " + (isFaculty() ? Commands.IS_FACULTY : Commands.IS_STUDENT) + Constants.NEW_LINE;
        return info;
    }

    //TODO when come to searching system
    public void searchBook() {
        return;
    }
}
