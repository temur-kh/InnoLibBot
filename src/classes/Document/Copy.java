package classes.Document;

import classes.User.User;

import java.util.Calendar;

public class Copy {
    private String id;
    private String docId;
    private DocAddress address;

    public Copy(String id, String docId, DocAddress address) {
        setId(id);
        setDocId(docId);
        setAddress(address);
    }

    public boolean isCheckedOut() {
        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public DocAddress getAddress() {
        return address;
    }

    public void setAddress(DocAddress address) {
        this.address = address;
    }
}
