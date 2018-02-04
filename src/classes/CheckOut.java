package classes;

import org.bson.types.ObjectId;
import services.CalendarObjectCreator;

import java.util.Calendar;

public class CheckOut {
    private ObjectId id;
    private Calendar fromDate;
    private Calendar toDate;
    private ObjectId personId;
    private ObjectId docId;
    private ObjectId copyId;

    public CheckOut(ObjectId id, Calendar fromDate, Calendar toDate, ObjectId personId, ObjectId docId, ObjectId copyId) {
        setId(id);
        setDocId(docId);
        setCopyId(copyId);
        setPersonId(personId);
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

    public void setFromDate(Calendar fromDate) {
        this.fromDate = fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = CalendarObjectCreator.createCalendarObject(fromDate);
    }

    public Calendar getToDate() {
        return toDate;
    }

    public void setToDate(Calendar toDate) {
        this.toDate = toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = CalendarObjectCreator.createCalendarObject(toDate);
    }

    public ObjectId getPersonId() {
        return personId;
    }

    public void setPersonId(ObjectId person) {
        this.personId = person;
    }

    public ObjectId getDocId() {
        return docId;
    }

    public void setDocId(ObjectId docId) {
        this.docId = docId;
    }

    public ObjectId getCopyId() {
        return copyId;
    }

    public void setCopyId(ObjectId copyId) {
        this.copyId = copyId;
    }
}
