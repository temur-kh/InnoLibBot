package database;

import classes.Document.Copy;
import classes.Document.DocAddress;
import com.mongodb.*;
import org.telegram.telegrambots.logging.BotLogger;

import java.util.ArrayList;
import java.util.List;

public class CopyDB {
    private static String LOGTAG = "Copy DB: ";

    public static void insertCopy(Copy copy) {
        insertCopy(toDBObject(copy));
    }

    public static void insertCopy(BasicDBObject object) {
        DBCollection collection = DatabaseManager.getCollection("Copy");
        try {
            collection.insert(object);
        } catch (DuplicateKeyException e) {
            BotLogger.severe(LOGTAG, "duplicate found!");
        }
    }

    public static Copy getCopy(String id) {
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

    public static void removeCopy(String id) {
        DBCollection collection = DatabaseManager.getCollection("Copy");
        BasicDBObject query = new BasicDBObject("_id", id);
        collection.remove(query);
    }

    public static Copy toObject(DBObject copy) {
        if(copy == null) return null;
        else
            return new Copy((String) copy.get("_id"),
                    (String) copy.get("doc_id"),
                    (DocAddress) new DocAddress((String)copy.get("doc_address.room"),
                            (String)copy.get("doc_address.level"),
                            (String)copy.get("doc_address.doc_case")));

    }

    public static BasicDBObject toDBObject(Copy copy) {
        return new BasicDBObject("_id", copy.getId())
                .append("doc_id", copy.getDocId())
                .append("doc_id", new BasicDBObject("room",copy.getAddress().getRoom())
                        .append("level",copy.getAddress().getLevel())
                        .append("doc_case",copy.getAddress().getDocCase()));
    }
}
