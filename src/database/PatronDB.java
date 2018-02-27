package database;

import classes.User.Faculty;
import classes.User.Patron;
import classes.User.Student;
import com.mongodb.*;
import org.telegram.telegrambots.logging.BotLogger;
import services.Constants;

import java.util.ArrayList;
import java.util.List;

public class PatronDB extends SuperDatabase {

    private static String LOGTAG = "Patron DB: ";

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
            return (Faculty) toObject(cursor.one());
        } else {
            return (Student) toObject(cursor.one());
        }
    }

    public static List<Patron> getPatronsList() {
        DBCollection collection = DatabaseManager.getCollection("Patron");
        ArrayList<Patron> patrons = new ArrayList<>();
        DBCursor cursor = collection.find(new BasicDBObject());
        for (DBObject dbObject : cursor) {
            if ((boolean) cursor.one().get("is_faculty")) {
                patrons.add((Faculty) toObject(dbObject));
            } else {
                patrons.add((Student) toObject(dbObject));
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
                    (String) patron.get("address"));
    }
}