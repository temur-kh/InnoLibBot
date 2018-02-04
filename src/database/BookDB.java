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
        ArrayList<Book> books = new ArrayList<>();
        DBCursor cursor = collection.find(new BasicDBObject());
        for (DBObject dbObject : cursor) {
            books.add(toObject(dbObject));
        }
        return books;
    }

    public static void updateBook(Book book) {
        updateBook(toDBObject(book));
    }

    public static void updateBook(BasicDBObject object) {
        DBCollection collection = DatabaseManager.getCollection("Book");
        collection.update(new BasicDBObject("_id", object.get("_id")), object);
    }

    public static void removeBook(String id) {
        DBCollection collection = DatabaseManager.getCollection("Book");
        BasicDBObject query = new BasicDBObject("_id", id);
        collection.remove(query);
    }

    public static Book toObject(DBObject book) {
        if(book == null) return null;
        else
            return new Book((String) book.get("_id"),
                (String) book.get("url"),
                (String) book.get("title"),
                (String) book.get("edition"),
                (ArrayList<String>) book.get("authors"),
                (String) book.get("photo_id"),
                (Double) book.get("price"),
                (ArrayList<String>) book.get("keywords"),
                (ArrayList<String>) book.get("copy_ids"),
                (boolean) book.get("bestseller"));
    }

}
