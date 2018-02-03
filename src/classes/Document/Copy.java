package classes.Document;

import classes.User.User;

import java.util.Calendar;

public class Copy {
    private long id;
    private long docId;
    DocAddress address;

    public boolean isCheckedOut() {
        return true;
    }

    public User checkedOutBy() {
        return null;
    }

    //TODO
    private Calendar fromDate() {
        return null;
    }

    //TODO
    public Calendar toDate() {
        return null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDocId() {
        return docId;
    }

    public void setDocId(long docId) {
        this.docId = docId;
    }

    public DocAddress getAddress() {
        return address;
    }

    public void setAddress(DocAddress address) {
        this.address = address;
    }
}
