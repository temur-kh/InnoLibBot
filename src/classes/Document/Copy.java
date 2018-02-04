package classes.Document;

import classes.User.User;
import org.bson.types.ObjectId;

import java.util.Calendar;

public class Copy {
    private ObjectId id;
    private ObjectId docId;
    private DocAddress address;

    public Copy(ObjectId id, ObjectId docId, DocAddress address) {
        setId(id);
        setDocId(docId);
        setAddress(address);
    }

    public boolean isCheckedOut() {
        return true;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getDocId() {
        return docId;
    }

    public void setDocId(ObjectId docId) {
        this.docId = docId;
    }

    public DocAddress getAddress() {
        return address;
    }

    public void setAddress(DocAddress address) {
        this.address = address;
    }
}
