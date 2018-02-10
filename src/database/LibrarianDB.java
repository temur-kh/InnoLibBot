package database;

import classes.User.Librarian;
import com.mongodb.*;
import org.telegram.telegrambots.logging.BotLogger;

import java.util.ArrayList;
import java.util.List;

public class LibrarianDB extends UserDB {
    private static String LOGTAG = "Librarian DB: ";

    public static void insertLibrarian(Librarian librarian) {
        insertLibrarian(toDBObject(librarian));
    }

    public static void insertLibrarian(BasicDBObject object) {
        DBCollection collection = DatabaseManager.getCollection("Librarian");
        try {
            collection.insert(object);
        } catch (DuplicateKeyException e) {
            BotLogger.severe(LOGTAG, "duplicate found!");
        }
    }

    public static Librarian getLibrarian(long id) {
        DBCollection collection = DatabaseManager.getCollection("Librarian");
        BasicDBObject query = new BasicDBObject("_id", id);
        DBCursor cursor = collection.find(query);
        return toObject(cursor.one());
    }

    public static List<Librarian> getLibrariansList() {
        DBCollection collection = DatabaseManager.getCollection("Librarian");
        ArrayList<Librarian> librarians = new ArrayList<>();
        DBCursor cursor = collection.find(new BasicDBObject());
        for (DBObject dbObject : cursor) {
            librarians.add(toObject(dbObject));
        }
        return librarians;
    }

    public static void updateLibrarian(Librarian librarian) {
        updateLibrarian(toDBObject(librarian));
    }

    public static void updateLibrarian(BasicDBObject object) {
        DBCollection collection = DatabaseManager.getCollection("Librarian");
        collection.update(new BasicDBObject("_id", object.get("_id")), object);
    }

    public static void removeLibrarian(long id) {
        DBCollection collection = DatabaseManager.getCollection("Librarian");
        BasicDBObject query = new BasicDBObject("_id", id);
        collection.remove(query);
    }

    public static Librarian toObject(DBObject librarian) {
        if (librarian == null)
            return null;
        else
            return new Librarian((long) librarian.get("_id"),
                    (String) librarian.get("name"),
                    (String) librarian.get("surname"),
                    (String) librarian.get("email"),
                    (String) librarian.get("phone_number"),
                    (String) librarian.get("address"));
    }
}
