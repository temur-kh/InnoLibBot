package database;

import classes.CheckOut;
import com.mongodb.*;
import org.bson.types.ObjectId;
import org.telegram.telegrambots.logging.BotLogger;
import services.CalendarObjectCreator;
import services.Constants;

import java.util.ArrayList;
import java.util.List;

public class CheckOutDB extends SuperDatabase {
    private static String LOGTAG = "CheckOut DB: ";

    public static ObjectId createCheckOut() {
        return createDBObject(Constants.CHECKOUT_COLLECTION);
    }

    public static void insertCheckOut(CheckOut checkOut) {
        insertObject(toDBObject(checkOut), Constants.CHECKOUT_COLLECTION);
    }

    public static CheckOut getCheckOut(String id) {
        DBCollection collection = DatabaseManager.getCollection("CheckOut");
        BasicDBObject query = new BasicDBObject("_id", id);
        DBCursor cursor = collection.find(query);
        return toObject(cursor.one());
    }

    //TODO
    public static CheckOut getCheckOut(BasicDBObject query) {
        DBCollection collection = DatabaseManager.getCollection("CheckOut");
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
        updateObject(toDBObject(checkOut), Constants.CHECKOUT_COLLECTION);
    }

    public static void removeCheckOut(ObjectId id) {
        removeObject(id, Constants.CHECKOUT_COLLECTION);
    }

    public static CheckOut toObject(DBObject checkOut) {
        if (checkOut == null) return null;
        else
            return new CheckOut((ObjectId) checkOut.get("_id"),
                    CalendarObjectCreator.createCalendarObject((String) checkOut.get("from_date")),
                    CalendarObjectCreator.createCalendarObject((String) checkOut.get("to_date")),
                    (long) checkOut.get("person_id"),
                    (ObjectId) checkOut.get("doc_id"),
                    (ObjectId) checkOut.get("copy_id"));
    }
}
