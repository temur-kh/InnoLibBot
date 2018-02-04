package classes;

import services.CalendarObjectCreator;

import java.util.Calendar;

public class CheckOut {
    private String id;
    private Calendar fromDate;
    private Calendar toDate;
    private String personId;
    private String docId;
    private String copyId;

    public CheckOut(String id, Calendar fromDate, Calendar toDate, String personId, String docId, String copyId) {
        setId(id);
        setDocId(docId);
        setCopyId(copyId);
        setPersonId(personId);
        setFromDate(fromDate);
        setToDate(toDate);
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
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

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String person) {
        this.personId = person;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getCopyId() {
        return copyId;
    }

    public void setCopyId(String copyId) {
        this.copyId = copyId;
    }
}
