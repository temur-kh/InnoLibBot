package database;

import classes.Notification;
import classes.User.Patron;
import com.mongodb.*;
import org.bson.types.ObjectId;
import org.telegram.telegrambots.logging.BotLogger;
import services.Constants;

import java.util.ArrayList;

public class PriorityQueueDB {
    private static final String LOGTAG = "Priority Queue DB: ";

    public static void insert(Patron patron, ObjectId docId, String docType) {
        DBCollection collection = DatabaseManager.getCollection(Constants.PRIORITY_QUEUE_COLLECTION);
        BasicDBObject object = DBObjectCreator.toPriorityQueueDBObject(patron, docId, docType);
        if (!contains(patron.getId(), docId)) {
            try {
                collection.insert(object);
            } catch (DuplicateKeyException e) {
                BotLogger.severe(LOGTAG, "duplicate found!");
            }
        }
    }

    public static boolean contains(long patronId, ObjectId docId) {
        DBCollection collection = DatabaseManager.getCollection(Constants.PRIORITY_QUEUE_COLLECTION);
        DBCursor cursor = collection.find(new BasicDBObject("doc_id", docId).append("patron_id", patronId));
        return cursor.one() != null;
    }

    public static Patron getNextPatron(ObjectId docId, boolean delete) {
        DBCollection collection = DatabaseManager.getCollection(Constants.PRIORITY_QUEUE_COLLECTION);
        DBCursor cursor = collection.find(new BasicDBObject("doc_id", docId)).sort(new BasicDBObject("patron_status", 1).append("time", 1));
        Patron next = PatronDB.getPatron((long) cursor.one().get("patron_id"));
        if (delete)
            collection.remove(new BasicDBObject("doc_id", docId).append("patron_id", next.getId()));
        return next;
    }

    public static ArrayList<Patron> getQueue(ObjectId docId) {
        DBCollection collection = DatabaseManager.getCollection(Constants.PRIORITY_QUEUE_COLLECTION);
        DBCursor cursor = collection.find(new BasicDBObject("doc_id", docId)).sort(new BasicDBObject("patron_status", 1).append("time", 1));
        ArrayList<Patron> patrons = new ArrayList<>();
        for (DBObject dbObject : cursor) {
            patrons.add(PatronDB.getPatron((long) dbObject.get("patron_id")));
        }
        return patrons;
    }

    public static ArrayList<Notification> getNotifications(ObjectId docId) {
        DBCollection collection = DatabaseManager.getCollection(Constants.PRIORITY_QUEUE_COLLECTION);
        DBCursor cursor = collection.find(new BasicDBObject("doc_id", docId));
        ArrayList<Notification> notifications = new ArrayList<>();
        for (DBObject dbObject : cursor) {
            notifications.add(new Notification((long) dbObject.get("patron_id"), (ObjectId) dbObject.get("doc_id"), (String) dbObject.get("doc_type")));
        }
        return notifications;
    }

    public static void removeQueue(ObjectId docId) {
        DBCollection collection = DatabaseManager.getCollection(Constants.PRIORITY_QUEUE_COLLECTION);
        BasicDBObject query = new BasicDBObject("doc_id", docId);
        collection.remove(query);
    }

    public static void removePatron(long patronId, ObjectId docId) {
        DBCollection collection = DatabaseManager.getCollection(Constants.PRIORITY_QUEUE_COLLECTION);
        BasicDBObject query = new BasicDBObject("doc_id", docId).append("patron_id", patronId);
        collection.remove(query);
    }
}
