package classes;

import database.CheckOutDB;
import database.PatronDB;
import org.bson.types.ObjectId;
import services.CalendarObjectCreator;
import services.Constants;

import java.util.Calendar;

/**
 * This class keeps information about periods of time, when Patron checks out and has to return document
 * also here we have id of document and copy, that is taken by Patron
 */
public class CheckOut {
    private ObjectId id;
    private Calendar fromDate;
    private Calendar toDate;
    private long patronId;
    private ObjectId docId;
    private String collection;
    private ObjectId copyId;

    //constructors
    public CheckOut(Calendar fromDate, Calendar toDate, long patronId, ObjectId docId, String collection, ObjectId copyId) {
        setId(CheckOutDB.createCheckOut());
        setDocId(docId);
        setDocCollection(collection);
        setCopyId(copyId);
        setPatronId(patronId);
        setFromDate(fromDate);
        setToDate(toDate);
    }

    public CheckOut(ObjectId id, Calendar fromDate, Calendar toDate, long patronId, ObjectId docId, String collection, ObjectId copyId) {
        setId(id);
        setDocId(docId);
        setDocCollection(collection);
        setCopyId(copyId);
        setPatronId(patronId);
        setFromDate(fromDate);
        setToDate(toDate);
    }

    public ObjectId getId() {
        return id;
    }

    private void setId(ObjectId id) {
        this.id = id;
    }

    public Calendar getFromDate() {
        return fromDate;
    }

    public String getFromDateLine() {
        return CalendarObjectCreator.createCalendarLine(fromDate);
    }

    public void setFromDate(Calendar fromDate) {
        this.fromDate = fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = CalendarObjectCreator.createCalendarObject(fromDate);
    }

    public Calendar getToDate() {
        return toDate;
    }

    public String getToDateLine() {
        return CalendarObjectCreator.createCalendarLine(toDate);
    }

    public void setToDate(Calendar toDate) {
        this.toDate = toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = CalendarObjectCreator.createCalendarObject(toDate);
    }

    public long getPatronId() { return patronId; }

    public void setPatronId(long patronId) {
        this.patronId = patronId;
    }

    public ObjectId getDocId() {
        return docId;
    }

    public void setDocId(ObjectId docId) {
        this.docId = docId;
    }

    public String getDocCollection() {
        return collection;
    }

    public void setDocCollection(String collection) { this.collection = collection; }

    public ObjectId getCopyId() {
        return copyId;
    }

    public void setCopyId(ObjectId copyId) {
        this.copyId = copyId;
    }

    public String getInfo() {
        String info = "";
        info += "*ID:* " + id.toString() + Constants.NEW_LINE;
        info += "*FROM DATE:* " + CalendarObjectCreator.createCalendarLine(fromDate) + Constants.NEW_LINE;
        info += "*TO DATE:* " + CalendarObjectCreator.createCalendarLine(toDate) + Constants.NEW_LINE;
        info += "*PATRON ID:* " + patronId + Constants.NEW_LINE;
        info += "*PATRON FULLNAME:* " + PatronDB.getPatron(patronId).getFullName() + Constants.NEW_LINE;
        info += "*DOC ID:* " + docId.toString() + Constants.NEW_LINE;
        info += "*DOC TYPE:* " + collection + Constants.NEW_LINE;
        info += "*COPY ID:* " + copyId.toString() + Constants.NEW_LINE;
        return info;
    }
}
