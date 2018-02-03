package classes;

import java.util.Calendar;

public class CheckOut {
    private long id;
    private Calendar fromDate;
    private Calendar toDate;
    private long personId;
    private long docId;
    private long copyId;

    public CheckOut(long id, long docId, long copyId, long personId, Calendar fromDate, Calendar toDate) {
        setId(id);
        setDocId(docId);
        setCopyId(copyId);
        setPersonId(personId);
        setFromDate(fromDate);
        setToDate(toDate);
    }

    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    public Calendar getFromDate() {
        return fromDate;
    }

    public void setFromDate(Calendar fromDate) {
        this.fromDate = fromDate;
    }

    public Calendar getToDate() {
        return toDate;
    }

    public void setToDate(Calendar toDate) {
        this.toDate = toDate;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long person) {
        this.personId = person;
    }

    public long getDocId() {
        return docId;
    }

    public void setDocId(long docId) {
        this.docId = docId;
    }

    public long getCopyId() {
        return copyId;
    }

    public void setCopyId(long copyId) {
        this.copyId = copyId;
    }
}
