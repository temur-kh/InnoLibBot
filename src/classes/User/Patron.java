package classes.User;

import classes.CheckOut;
import classes.Document.Book;
import classes.Document.Document;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import database.BookDB;
import database.CheckOutDB;
import org.bson.types.ObjectId;
import services.CalendarObjectCreator;
import services.Constants;

import java.util.ArrayList;
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

    public Patron(Long id, String name, String surname, String email, String phoneNumber, String address) {
        super(id, name, surname, email, phoneNumber, address);
        //checkOutList = new ArrayList<>();
    }

    public CheckOut checkOutDocument(Document document) throws NoSuchElementException, SecurityException {

        if (BookDB.getBook(document.getId()) == null || !document.hasFreeCopies())
            throw new NoSuchElementException();
        BasicDBObject query = new BasicDBObject("person_id", this.getId()).append("doc_id", document.getId());
        CheckOut checkOut = CheckOutDB.getCheckOut(query);
        if (checkOut != null || !((Book) document).canBeCheckedOut()) {
            throw new SecurityException();
        }

        Calendar today = new GregorianCalendar();
        Calendar deadline = new GregorianCalendar();

        int time;
        if (this.getClass() == Faculty.class) {
            time = Constants.BOOK_CHECK_OUT_LIMIT_FOR_FACULTY;
        } else if (((Book) document).isBestSeller()) {
            time = Constants.BEST_SELLER_CHECK_OUT_LIMIT;
        } else {
            time = Constants.BOOK_CHECK_OUT_LIMIT;
        }
        deadline.add(Calendar.DAY_OF_MONTH, time);

        Calendar cal = new GregorianCalendar(1998, 0, 31);

        System.out.println(cal.getTime().toString());

        checkOut = new CheckOut(today, deadline, getId(), document.getId(), document.getFreeCopy(true).getId());
        CheckOutDB.insertCheckOut(checkOut);

        return checkOut;
    }

    //TODO (will be implemented later)
    public boolean returnDocument(Document document) {
        return true;
    }

    //patron is student by default
    public boolean isStudent() {
        return true;
    }

    //
    public boolean isFaculty() {
        return false;
    }

    //TODO when come to searching system
    public void searchBook() {
        return;
    }
}
