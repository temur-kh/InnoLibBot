package database;

import classes.Document.Book;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import com.mongodb.*;
import org.bson.types.ObjectId;
import org.telegram.telegrambots.logging.BotLogger;

import java.util.ArrayList;

public class BookDB extends DocumentDB {

    private static String LOGTAG = "Book DB: ";

    public static ObjectId createBook() {
        System.out.println("OK");
        DBCollection collection = DatabaseManager.getCollection("Book");
        BasicDBObject object = new BasicDBObject();
        try {
            collection.insert(object);
            System.out.println(object.get("_id"));
        } catch (DuplicateKeyException e) {
            BotLogger.severe(LOGTAG, "duplicate found!");
        }
        return (ObjectId) object.get("_id");
    }

    public static void insertBook(Book book) {
        insertBook(toDBObject(book).append("edition", book.getEdition())
                .append("bestseller", book.isBestSeller())
                .append("can_be_checked_out", book.canBeCheckedOut()));
    }

    public static void insertBook(BasicDBObject object) {
        DBCollection collection = DatabaseManager.getCollection("Book");
        BasicDBObject query = new BasicDBObject("_id",object.get("_id"));
        if(collection.find(query).one()!=null) {
            updateBook(object);
        } else {
            try {
                collection.insert(object);
            } catch (DuplicateKeyException e) {
                BotLogger.severe(LOGTAG, "duplicate found!");
            }
        }
    }

    public static Book getBook(String id) {
        DBCollection collection = DatabaseManager.getCollection("Book");
        BasicDBObject query = new BasicDBObject("_id", id);
        DBCursor cursor = collection.find(query);
        return toObject(cursor.one());
    }

    public static ArrayList<Book> getBooksList() {
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
            return new Book((ObjectId) book.get("_id"),
                (String) book.get("url"),
                (String) book.get("title"),
                (String) book.get("edition"),
                (ArrayList<String>) book.get("authors"),
                (String) book.get("photo_id"),
                (Double) book.get("price"),
                (ArrayList<String>) book.get("keywords"),
                (ArrayList<String>) book.get("copy_ids"),
                (boolean) book.get("bestseller"),
                (boolean) book.get("can_be_checked_out"));
    }

}
