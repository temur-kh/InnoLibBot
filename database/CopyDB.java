package database;

import classes.Document.Copy;
import classes.Document.DocAddress;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import services.Constants;

import java.util.ArrayList;
import java.util.List;

public class CopyDB extends SuperDatabase {
    private static final String LOGTAG = "Copy DB: ";

    public static ObjectId createCopy() {
        return createDBObject(Constants.COPY_COLLECTION);
    }

    public static void insertCopy(Copy copy) {
        insertObject(toDBObject(copy), Constants.COPY_COLLECTION);
    }

    public static Copy getCopy(ObjectId id) {
        DBCollection collection = DatabaseManager.getCollection("Copy");
        BasicDBObject query = new BasicDBObject("_id", id);
        DBCursor cursor = collection.find(query);
        return toObject(cursor.one());
    }

    public static List<Copy> getCopiesList() {
        DBCollection collection = DatabaseManager.getCollection("Copy");
        ArrayList<Copy> copies = new ArrayList<>();
        DBCursor cursor = collection.find(new BasicDBObject());
        for (DBObject dbObject : cursor) {
            copies.add(toObject(dbObject));
        }
        return copies;
    }

    public static void updateCopy(Copy copy) {
        updateObject(toDBObject(copy), Constants.COPY_COLLECTION);
    }

    public static void removeCopy(ObjectId id) {
        removeObject(id, Constants.COPY_COLLECTION);
    }

    public static Copy toObject(DBObject copy) {
        if (copy == null) return null;
        else
            return new Copy((ObjectId) copy.get("_id"),
                    (ObjectId) copy.get("doc_id"),
                    new DocAddress((String) copy.get("doc_address.room"),
                            (String) copy.get("doc_address.level"),
                            (String) copy.get("doc_address.doc_case")),
                    (boolean) copy.get("checked_out"));
    }
}
