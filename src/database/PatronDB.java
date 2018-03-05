package database;

import classes.User.Patron;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import services.Constants;

import java.util.ArrayList;
import java.util.List;

public class PatronDB extends SuperDatabase {

    private static final String LOGTAG = "Patron DB: ";

    public static void insertPatron(Patron patron) {
        insertObject(toDBObject(patron).append("is_faculty", patron.isFaculty()), Constants.PATRON_COLLECTION);
    }

    public static Patron getPatron(long id) {
        DBCollection collection = DatabaseManager.getCollection("Patron");
        BasicDBObject query = new BasicDBObject("_id", id);
        DBCursor cursor = collection.find(query);
        if (cursor.one() == null)
            return null;
        if ((boolean) cursor.one().get("is_faculty")) {
            return toObject(cursor.one());
        } else {
            return toObject(cursor.one());
        }
    }

    public static List<Patron> getPatronsList() {
        DBCollection collection = DatabaseManager.getCollection("Patron");
        ArrayList<Patron> patrons = new ArrayList<>();
        DBCursor cursor = collection.find(new BasicDBObject());
        for (DBObject dbObject : cursor) {
            if ((boolean) cursor.one().get("is_faculty")) {
                patrons.add(toObject(dbObject));
            } else {
                patrons.add(toObject(dbObject));
            }
        }
        return patrons;
    }

    public static void updatePatron(Patron patron) {
        updateObject(toDBObject(patron), Constants.PATRON_COLLECTION);
    }

    public static void removePatron(long id) {
        removeObject(id, Constants.PATRON_COLLECTION);
    }

    public static Patron toObject(DBObject patron) {
        if (patron == null) return null;
        else
            return new Patron((long) patron.get("_id"),
                    (String) patron.get("name"),
                    (String) patron.get("surname"),
                    (String) patron.get("email"),
                    (String) patron.get("phone_number"),
                    (String) patron.get("address"),
                    (boolean) patron.get("is_faculty"));
    }
}