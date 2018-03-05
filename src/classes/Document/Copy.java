package classes.Document;

import database.CopyDB;
import org.bson.types.ObjectId;

/**
 * Used, when we create some copies of documents
 * A library may have several copies of each document. Copies are stored in a
 * certain place inside the library, e.g., a room, level. For each copy we need
 * to know whether it is currently checked out and by whom.
 */
public class Copy {
    //id of copy
    private ObjectId id;
    //id of document, to which this copy belong
    private ObjectId docId;
    //address, where we can find this copy (room and level)
    private DocAddress address;

    private boolean checkedOut;

    public Copy(ObjectId id, ObjectId docId, DocAddress address, boolean checkedOut) {
        setId(id);
        setDocId(docId);
        setAddress(address);
        setCheckedOut(checkedOut);
    }

    public Copy(ObjectId id, ObjectId docId, DocAddress address) {
        setId(id);
        setDocId(docId);
        setAddress(address);
        setCheckedOut(false);
    }

    public Copy(ObjectId docId, DocAddress address) {
        setId(CopyDB.createCopy());
        setDocId(docId);
        setAddress(address);
        setCheckedOut(false);
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
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
