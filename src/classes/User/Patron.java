package classes.User;

import classes.CheckOut;
import classes.Document.Document;
import database.CheckOutDB;
import org.bson.types.ObjectId;
import services.CalendarObjectCreator;
import services.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

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

    public CheckOut checkOutDocument(Document document) {

        Calendar today = new GregorianCalendar();
        Calendar deadline = new GregorianCalendar();

        //used temporary constant. TODO check which king of limit to put
        deadline.add(Calendar.DAY_OF_MONTH, Constants.BEST_SELLER_CHECK_OUT_LIMIT);

        CheckOut checkOut = new CheckOut(today,deadline,getId(),document.getId(),document.getFreeCopy().getId());
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
