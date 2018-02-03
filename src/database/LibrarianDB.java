package database;

import classes.User.Librarian;
import com.mongodb.*;
import org.telegram.telegrambots.logging.BotLogger;

import java.util.ArrayList;
import java.util.List;

public class LibrarianDB extends UserDB{
    private static String LOGTAG = "Librarian DB: ";
    public static void insertLibrarian(Librarian librarian) {
        DBCollection collection = DatabaseManager.getInstance().getCollection("Librarian");
        DBObject object = toDBObject(librarian);
        try {
            collection.insert(object);
        } catch (DuplicateKeyException e) {
            BotLogger.severe(LOGTAG,"duplicate found!");
        }
    }

    public static Librarian getLibrarian(long id) {
        DBCollection collection = DatabaseManager.getCollection("Librarian");
        BasicDBObject query = new BasicDBObject("_id", id);
        DBCursor cursor = collection.find(query);
        return (Librarian) toObject(cursor.one());
    }

    public static List<Librarian> getLibrariansList() {
        DBCollection collection = DatabaseManager.getCollection("Librarian");
        ArrayList<Librarian> librarians = new ArrayList<>();
        DBCursor cursor = collection.find(new BasicDBObject());
        for(DBObject dbObject: cursor) {
            librarians.add((Librarian)toObject(dbObject));
        }
        return librarians;
    }

    public static void removeLibrarian(long id) {
        DBCollection collection = DatabaseManager.getCollection("Librarian");
        BasicDBObject query = new BasicDBObject("_id", id);
        collection.remove(query);
    }
}
