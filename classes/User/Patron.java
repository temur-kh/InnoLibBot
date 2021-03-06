package classes.User;

import classes.CheckOut;
import classes.Document.Book;
import classes.Document.Document;
import database.CheckOutDB;
import database.PatronDB;
import database.PriorityQueueDB;
import database.SuperDatabase;
import org.telegram.telegrambots.logging.BotLogger;
import services.Constants;

import java.util.Calendar;
import java.util.NoSuchElementException;

/**
 * This class is extended from main class "User", it will have more functions(as book a document, or search book)
 * Also classes "Faculty" and "Student" are extended from this class(to know if user is Faculty or Student)
 *
 * Some functions will be implemented later.
 */
public class Patron extends User {

    private static String LOGTAG = "Patron: ";

    public Patron(Long id, String name, String surname, Status status, String email, String phoneNumber, String address) {
        super(id, name, surname, status, email, phoneNumber, address);
    }

    public CheckOut checkOutDocument(Document document, String collection) throws NoSuchElementException, SecurityException {

        if (SuperDatabase.getObject(document.getId(), collection) == null)
            throw new NoSuchElementException();

        CheckOut checkOut = CheckOutDB.getCheckOut(this.getId(), document.getId());
        if (checkOut != null || ((document instanceof Book) && !((Book) document).canBeCheckedOut())) {
            throw new SecurityException();
        }

        if (!document.hasFreeCopies()) {
            PriorityQueueDB.insert(this, document.getId(), collection);
            return null;
        }

        //Calendar today = DateTime.todayCalendar();
        Calendar today = (Calendar) Constants.TEMPORARY_CHANGEABLE_CALENDAR.clone();
        Calendar deadline = (Calendar) Constants.TEMPORARY_CHANGEABLE_CALENDAR.clone();

        deadline.add(Calendar.DAY_OF_MONTH, getCheckOutTime(document, collection));
        //deadline.add(Calendar.DAY_OF_MONTH, -1);


        checkOut = new CheckOut(today, deadline, getId(), document.getId(), collection, document.getFreeCopy(true).getId());
        CheckOutDB.insertCheckOut(checkOut);

        BotLogger.severe(LOGTAG, "Checkout document: " + checkOut.getId());

        return checkOut;
    }

    public int getCheckOutTime(Document document, String collection) {
        int time;
        if (this.getStatus() == Status.VisitingProfessor) {
            time = Constants.VISITING_PROFESSOR_CHECK_OUT_LIMIT;
        } else if (collection.equals(Constants.BOOK_COLLECTION)) {
            if (this.isFaculty()) {
                time = Constants.BOOK_CHECK_OUT_LIMIT_FOR_FACULTY;
            } else if (((Book) document).isBestSeller()) {
                time = Constants.BEST_SELLER_CHECK_OUT_LIMIT;
            } else {
                time = Constants.BOOK_CHECK_OUT_LIMIT;
            }
        } else {
            time = Constants.AVMATERIAL_AND_JOURNAL_CHECK_OUT_LIMIT;
        }
        return time;
    }

    public void register() {
        PatronDB.insertPatron(this);
        BotLogger.severe(LOGTAG, "Patron registered");
    }

    //TODO (will be implemented later)
    public boolean returnDocument(Document document) {
        return true;
    }

    //TODO (will be implemented later)
    public boolean renewDocument(Document docuemen) { return true; }

    //patron is student by default
    public boolean isStudent() {
        return getStatus() == Status.Student;
    }

    //
    public boolean isFaculty() {
        switch (getStatus()) {
            case Instructor:
            case TA:
            case Professor:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String getInfo() {
        String info = super.getInfo();
        info += "<strong>STATUS:</strong> " + getStatus().name();
        return info;
    }

    //TODO when come to searching system
    public void searchBook() {
        return;
    }
}
