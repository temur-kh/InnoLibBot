package database;

import classes.Document.Journal;
import com.mongodb.*;
import org.bson.types.ObjectId;
import services.Constants;

import java.util.ArrayList;
import java.util.List;

public class JournalDB extends SuperDatabase {
    private static final String LOGTAG = "Journal DB: ";

    public static ObjectId createJournal() {
        return createDBObject(Constants.JOURNAL_COLLECTION);
    }

    public static void insertJournal(Journal journal) {
        insertObject(toDBObject(journal)
                        .append("issue_ids", journal.getIssueIds())
                        .append("can_be_checked_out", journal.canBeCheckedOut()),
                Constants.JOURNAL_COLLECTION);
    }

    public static Journal getJournal(String id) {
        DBCollection collection = DatabaseManager.getCollection("Journal");
        BasicDBObject query = new BasicDBObject("_id", id);
        DBCursor cursor = collection.find(query);
        return toObject(cursor.one());
    }

    public static List<Journal> getJournalsList() {
        DBCollection collection = DatabaseManager.getCollection("Journal");
        ArrayList<Journal> journals = new ArrayList<>();
        DBCursor cursor = collection.find(new BasicDBObject());
        for (DBObject dbObject : cursor) {
            journals.add(toObject(dbObject));
        }
        return journals;
    }

    public static void updateJournal(Journal journal) {
        updateObject(toDBObject(journal), Constants.JOURNAL_COLLECTION);
    }

    public static void removeJournal(ObjectId id) {
        removeObject(id, Constants.JOURNAL_COLLECTION);
    }

    public static Journal toObject(DBObject journal) {
        if (journal == null) return null;
        else
            return new Journal((ObjectId) journal.get("_id"),
                    (String) journal.get("url"),
                    (String) journal.get("title"),
                    (ArrayList<String>) journal.get("authors"),
                    (String) journal.get("photo_id"),
                    (Double) journal.get("price"),
                    (ArrayList<String>) journal.get("keywords"),
                    (ArrayList<ObjectId>) journal.get("copy_ids"),
                    (boolean) journal.get("can_be_checked_out"));
    }
}
