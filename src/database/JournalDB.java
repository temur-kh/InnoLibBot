package database;

import classes.Document.Journal;
import com.mongodb.*;
import org.telegram.telegrambots.logging.BotLogger;

import java.util.ArrayList;
import java.util.List;

public class JournalDB extends DocumentDB {
    private static String LOGTAG = "Journal DB: ";

    public static void insertJournal(Journal journal) {
        insertJournal(toDBObject(journal)
                .append("issue_ids",journal.getIssueIds()));
    }

    public static void insertJournal(BasicDBObject object) {
        DBCollection collection = DatabaseManager.getCollection("Journal");
        try {
            collection.insert(object);
        } catch (DuplicateKeyException e) {
            BotLogger.severe(LOGTAG, "duplicate found!");
        }
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

    public static void removeJournal(String id) {
        DBCollection collection = DatabaseManager.getCollection("Journal");
        BasicDBObject query = new BasicDBObject("_id", id);
        collection.remove(query);
    }

    public static Journal toObject(DBObject journal) {
        if(journal == null) return null;
        else
            return new Journal((String) journal.get("_id"),
                    (String) journal.get("url"),
                    (String) journal.get("title"),
                    (ArrayList<String>) journal.get("authors"),
                    (String) journal.get("photo_id"),
                    (Double) journal.get("price"),
                    (ArrayList<String>) journal.get("keywords"),
                    (ArrayList<String>) journal.get("copy_ids"));
    }
}
