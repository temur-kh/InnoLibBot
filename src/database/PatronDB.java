package database;

import classes.User.Faculty;
import classes.User.Patron;
import classes.User.Student;
import com.mongodb.*;
import org.telegram.telegrambots.logging.BotLogger;

import java.util.ArrayList;
import java.util.List;

public class PatronDB extends UserDB {

    private static String LOGTAG = "Patron DB: ";

    public static void insertPatron(Patron patron) {
        insertPatron(toDBObject(patron).append("is_faculty", patron.isFaculty()));
    }

    public static void insertPatron(BasicDBObject object) {
        DBCollection collection = DatabaseManager.getCollection("Patron");
        try {
            collection.insert(object);
        } catch (DuplicateKeyException e) {
            BotLogger.severe(LOGTAG, "duplicate found!");
        }
    }

    public static Patron getPatron(long id) {
        DBCollection collection = DatabaseManager.getCollection("Patron");
        BasicDBObject query = new BasicDBObject("_id", id);
        DBCursor cursor = collection.find(query);
        if ((boolean) cursor.one().get("is_faculty")) {
            return toObjectFaculty(cursor.one());
        } else {
            return toObjectStudent(cursor.one());
        }
    }

    public static List<Patron> getPatronsList() {
        DBCollection collection = DatabaseManager.getCollection("Patron");
        ArrayList<Patron> patrons = new ArrayList<>();
        DBCursor cursor = collection.find(new BasicDBObject());
        for (DBObject dbObject : cursor) {
            if ((boolean) cursor.one().get("is_faculty")) {
                patrons.add(toObjectFaculty(dbObject));
            } else {
                patrons.add(toObjectStudent(dbObject));
            }
        }
        return patrons;
    }

    public static void removePatron(long id) {
        DBCollection collection = DatabaseManager.getCollection("Patron");
        BasicDBObject query = new BasicDBObject("_id", id);
        collection.remove(query);
    }

    public static Faculty toObjectFaculty(DBObject patron) {
        return new Faculty((long) patron.get("_id"),
                (String) patron.get("name"),
                (String) patron.get("surname"),
                (String) patron.get("email"),
                (String) patron.get("phone_number"),
                (String) patron.get("address"));
    }

    public static Student toObjectStudent(DBObject patron) {
        return new Student((long) patron.get("_id"),
                (String) patron.get("name"),
                (String) patron.get("surname"),
                (String) patron.get("email"),
                (String) patron.get("phone_number"),
                (String) patron.get("address"));
    }
}
