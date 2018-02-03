package database;

import classes.Document.Book;
import com.mongodb.*;
import org.telegram.telegrambots.logging.BotLogger;

import java.util.ArrayList;
import java.util.List;

public class BookDB extends DocumentDB {

    private static String LOGTAG = "Book DB: ";

    public static void insertBook(Book book) {
        insertBook(toDBObject(book).append("edition", book.getEdition())
                .append("bestseller", book.isBestSeller()));
    }

    public static void insertBook(BasicDBObject object) {
        DBCollection collection = DatabaseManager.getCollection("Book");
        try {
            collection.insert(object);
        } catch (DuplicateKeyException e) {
            BotLogger.severe(LOGTAG, "duplicate found!");
        }
    }

    public static Book getBook(String id) {
        DBCollection collection = DatabaseManager.getCollection("Book");
        BasicDBObject query = new BasicDBObject("_id", id);
        DBCursor cursor = collection.find(query);
        return toObject(cursor.one());
    }

    public static List<Book> getBooksList() {
        DBCollection collection = DatabaseManager.getCollection("Book");
        ArrayList<Book> librarians = new ArrayList<>();
        DBCursor cursor = collection.find(new BasicDBObject());
        for (DBObject dbObject : cursor) {
            librarians.add(toObject(dbObject));
        }
        return librarians;
    }

    public static void removeBook(String id) {
        DBCollection collection = DatabaseManager.getCollection("Book");
        BasicDBObject query = new BasicDBObject("_id", id);
        collection.remove(query);
    }

    public static Book toObject(DBObject document) {
        return new Book((String) document.get("_id"),
                (String) document.get("url"),
                (String) document.get("title"),
                (String) document.get("edition"),
                (ArrayList<String>) document.get("authors"),
                (String) document.get("photo_id"),
                (Double) document.get("price"),
                (ArrayList<String>) document.get("keywords"),
                (ArrayList<String>) document.get("copy_ids"),
                (boolean) document.get("bestseller"));
    }

}
