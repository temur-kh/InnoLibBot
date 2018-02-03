package classes.Document;

import classes.User.User;

import java.util.Calendar;

public class Copy {
    private long id;
    private long docId;
    private DocAddress address;

    public Copy(long id, long docId, DocAddress address) {
        setId(id);
        setDocId(docId);
        setAddress(address);
    }

    public boolean isCheckedOut() {
        return true;
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
