package database;

import classes.Notification;
import com.mongodb.*;
import org.bson.types.ObjectId;
import org.telegram.telegrambots.logging.BotLogger;
import services.Constants;
import services.DateTime;

import java.util.ArrayList;
import java.util.Date;

public class NotificationDB {
    private static final String LOGTAG = "Priority Queue DB: ";

    public static void insert(long patronId, ObjectId docId, String docCollection) {
        insert(new Notification(patronId, docId, docCollection, DateTime.tomorrowDate()));
    }

    public static void insert(Notification notification) {
        DBCollection collection = DatabaseManager.getCollection(Constants.NOTIFICATION_COLLECTION);
        BasicDBObject object = DBObjectCreator.toNotificationDBObject(notification);
        try {
            collection.insert(object);
        } catch (DuplicateKeyException e) {
            BotLogger.severe(LOGTAG, "duplicate found!");
        }
    }

    public static ArrayList<Notification> getExpiredNotifications() {
        DBCollection collection = DatabaseManager.getCollection(Constants.NOTIFICATION_COLLECTION);
        DBCursor cursor = collection.find(new BasicDBObject("time", new BasicDBObject("$lt", DateTime.now())));
        ArrayList<Notification> notifications = new ArrayList<>();
        for (DBObject dbObject : cursor) {
            notifications.add(toObject(dbObject));
        }
        return notifications;
    }

    public static Notification toObject(DBObject object) {
        if (object == null) return null;
        else
            return new Notification((long) object.get("patron_id"),
                    (ObjectId) object.get("doc_id"),
                    (String) object.get("doc_type"),
                    (Date) object.get("time"));
    }
}
