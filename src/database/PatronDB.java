package database;

import classes.User.Faculty;
import classes.User.Patron;
import classes.User.Student;
import com.mongodb.*;
import org.telegram.telegrambots.logging.BotLogger;

import java.util.ArrayList;
import java.util.List;

public class PatronDB extends UserDB {

    private static String LOGTAG = "Patron DB:";

    public static void insertPatron(Patron patron) {
        DBCollection collection = DatabaseManager.getInstance().getCollection("Patron");
        DBObject object = toDBObject(patron).append("is_faculty",patron.isFaculty());
        try {
            collection.insert(object);
        } catch (DuplicateKeyException e) {
            BotLogger.severe(LOGTAG,"duplicate found!");
        }
    }

    public static Patron getPatron(long id) {
        DBCollection collection = DatabaseManager.getCollection("Patron");
        BasicDBObject query = new BasicDBObject("_id", id);
        DBCursor cursor = collection.find(query);
        if((boolean)cursor.one().get("is_faculty")) {
            return (Faculty)toObject(cursor.one());
        }
        else {
            return ((Student)toObject(cursor.one()));
        }
    }

    public static List<Patron> getPatronsList() {
        DBCollection collection = DatabaseManager.getCollection("Patron");
        ArrayList<Patron> patrons = new ArrayList<>();
        DBCursor cursor = collection.find(new BasicDBObject());
        for(DBObject dbObject: cursor) {
            if((boolean)cursor.one().get("is_faculty")) {
                patrons.add((Faculty)toObject(dbObject));
            }
            else {
                patrons.add((Student)toObject(dbObject));
            }
        }
        return patrons;
    }

    public static void removePatron(long id) {
        DBCollection collection = DatabaseManager.getCollection("Patron");
        BasicDBObject query = new BasicDBObject("_id", id);
        collection.remove(query);
    }
}
