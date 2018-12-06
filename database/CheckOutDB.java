package database;

import classes.CheckOut;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import services.Constants;
import services.DateTime;

import java.util.ArrayList;
import java.util.Date;

;

public class CheckOutDB extends SuperDatabase {
    private static String LOGTAG = "CheckOut DB: ";

    public static ObjectId createCheckOut() {
        return createDBObject(Constants.CHECKOUT_COLLECTION);
    }

    public static void insertCheckOut(CheckOut checkOut) {
        insertObject(toDBObject(checkOut), Constants.CHECKOUT_COLLECTION);
    }

    public static CheckOut getCheckOut(ObjectId id) {
        DBCollection collection = DatabaseManager.getCollection(Constants.CHECKOUT_COLLECTION);
        BasicDBObject query = new BasicDBObject("_id", id);
        DBCursor cursor = collection.find(query);
        return toObject(cursor.one());
    }


    public static CheckOut getCheckOut(long patronId, ObjectId docId) {
        DBCollection collection = DatabaseManager.getCollection(Constants.CHECKOUT_COLLECTION);
        BasicDBObject query = new BasicDBObject("patron_id", patronId).append("doc_id", docId);
        DBCursor cursor = collection.find(query);
        return toObject(cursor.one());
    }

    public static CheckOut getCheckOut(BasicDBObject query) {
        DBCollection collection = DatabaseManager.getCollection(Constants.CHECKOUT_COLLECTION);
        DBCursor cursor = collection.find(query);
        return toObject(cursor.one());
    }

    public static ArrayList<CheckOut> getAllCheckOutsList() {
        DBCollection collection = DatabaseManager.getCollection(Constants.CHECKOUT_COLLECTION);
        DBCursor cursor = collection.find(new BasicDBObject()).sort(new BasicDBObject("to_date", 1));
        return getCheckOutsListByCursor(cursor);
    }

    public static ArrayList<CheckOut> getCheckOutsList() {
        DBCollection collection = DatabaseManager.getCollection(Constants.CHECKOUT_COLLECTION);
        DBCursor cursor = collection.find(new BasicDBObject("to_date",
                new BasicDBObject("$gt", DateTime.daysAddedDate(DateTime.todayDate(), -1))))
                .sort(new BasicDBObject("to_date", 1));
        return getCheckOutsListByCursor(cursor);
    }

    public static ArrayList<CheckOut> getOverdueCheckOutsList() {
        DBCollection collection = DatabaseManager.getCollection(Constants.CHECKOUT_COLLECTION);
        DBCursor cursor = collection.find(new BasicDBObject("to_date", new BasicDBObject("$lt", DateTime.todayDate())))
                .sort(new BasicDBObject("to_date", 1));
        return getCheckOutsListByCursor(cursor);
    }

    public static ArrayList<CheckOut> getCheckOutsListByUserId(long userId) {
        DBCollection collection = DatabaseManager.getCollection(Constants.CHECKOUT_COLLECTION);
        DBCursor cursor = collection.find(new BasicDBObject("to_date",
                new BasicDBObject("$gt", DateTime.daysAddedDate(DateTime.todayDate(), -1)))
                .append("patron_id", userId))
                .sort(new BasicDBObject("to_date", 1));
        return getCheckOutsListByCursor(cursor);
    }

    public static ArrayList<CheckOut> getOverdueCheckOutsListByUserId(long userId) {
        DBCollection collection = DatabaseManager.getCollection(Constants.CHECKOUT_COLLECTION);
        DBCursor cursor = collection.find(new BasicDBObject("to_date", new BasicDBObject("$lt", DateTime.todayDate()))
                .append("patron_id", userId))
                .sort(new BasicDBObject("to_date", 1));
        return getCheckOutsListByCursor(cursor);
    }

    private static ArrayList<CheckOut> getCheckOutsListByCursor(DBCursor cursor) {
        ArrayList<CheckOut> checkOuts = new ArrayList<>();
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
                    DateTime.convertToCalendar((Date) checkOut.get("from_date")),
                    DateTime.convertToCalendar((Date) checkOut.get("to_date")),
                    (long) checkOut.get("patron_id"),
                    (ObjectId) checkOut.get("doc_id"),
                    (String) checkOut.get("collection"),
                    (ObjectId) checkOut.get("copy_id"),
                    (boolean) checkOut.get("is_renewed"));
    }
}
