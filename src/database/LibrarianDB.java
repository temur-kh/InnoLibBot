package database;

import classes.User.Librarian;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import services.Constants;

import java.util.ArrayList;
import java.util.List;

public class LibrarianDB extends SuperDatabase {
    private static final String LOGTAG = "Librarian DB: ";

    public static void insertLibrarian(Librarian librarian) {
        insertObject(toDBObject(librarian), Constants.LIBRARIAN_COLLECTION);
    }

    public static Librarian getLibrarian() {
        DBCollection collection = DatabaseManager.getCollection("Librarian");
        BasicDBObject query = new BasicDBObject();
        DBCursor cursor = collection.find(query);
        return toObject(cursor.one());
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
        updateObject(toDBObject(librarian), Constants.LIBRARIAN_COLLECTION);
    }

    public static void updateLibrarian(BasicDBObject object) {
        updateObject(object, Constants.LIBRARIAN_COLLECTION);
    }

    public static void removeLibrarian(long id) {
        removeObject(id, Constants.LIBRARIAN_COLLECTION);
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
