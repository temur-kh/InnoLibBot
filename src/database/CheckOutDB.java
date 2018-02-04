package database;

import classes.CheckOut;
import com.mongodb.*;
import org.bson.types.ObjectId;
import org.telegram.telegrambots.logging.BotLogger;
import services.CalendarObjectCreator;

import java.util.ArrayList;
import java.util.List;

public class CheckOutDB {
    private static String LOGTAG = "CheckOut DB: ";

    public static void insertCheckOut(CheckOut checkOut) {
        insertCheckOut(toDBObject(checkOut));
    }

    public static void insertCheckOut(BasicDBObject object) {
        DBCollection collection = DatabaseManager.getCollection("CheckOut");
        try {
            collection.insert(object);
        } catch (DuplicateKeyException e) {
            BotLogger.severe(LOGTAG, "duplicate found!");
        }
    }

    public static CheckOut getCheckOut(String id) {
        DBCollection collection = DatabaseManager.getCollection("CheckOut");
        BasicDBObject query = new BasicDBObject("_id", id);
        DBCursor cursor = collection.find(query);
        return toObject(cursor.one());
    }

    public static List<CheckOut> getCheckOutsList() {
        DBCollection collection = DatabaseManager.getCollection("CheckOut");
        ArrayList<CheckOut> checkOuts = new ArrayList<>();
        DBCursor cursor = collection.find(new BasicDBObject());
        for (DBObject dbObject : cursor) {
            checkOuts.add(toObject(dbObject));
        }
        return checkOuts;
    }

    public static void updateCheckOut(CheckOut checkOut) {
        updateCheckOut(toDBObject(checkOut));
    }

    public static void updateCheckOut(BasicDBObject object) {
        DBCollection collection = DatabaseManager.getCollection("CheckOut");
        collection.update(new BasicDBObject("_id", object.get("_id")), object);
    }

    public static void removeCheckOut(String id) {
        DBCollection collection = DatabaseManager.getCollection("CheckOut");
        BasicDBObject query = new BasicDBObject("_id", id);
        collection.remove(query);
    }

    public static CheckOut toObject(DBObject checkOut) {
        if (checkOut == null) return null;
        else
            return new CheckOut((ObjectId) checkOut.get("_id"),
                    CalendarObjectCreator.createCalendarObject((String) checkOut.get("from_date")),
                    CalendarObjectCreator.createCalendarObject((String) checkOut.get("to_date")),
                    (ObjectId) checkOut.get("person_id"),
                    (ObjectId) checkOut.get("doc_id"),
                    (ObjectId) checkOut.get("copy_id"));
    }

    public static BasicDBObject toDBObject(CheckOut checkOut) {
        return new BasicDBObject("_id", checkOut.getId())
                .append("from_date", checkOut.getFromDate().getTime().toString())
                .append("to_date", checkOut.getToDate().getTime().toString())
                .append("person_id", checkOut.getPersonId())
                .append("doc_id", checkOut.getDocId())
                .append("copy_id", checkOut.getCopyId());
    }
}
