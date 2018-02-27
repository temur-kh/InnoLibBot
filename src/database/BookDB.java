package database;

import classes.Document.Book;
import com.mongodb.*;
import org.bson.types.ObjectId;
import org.telegram.telegrambots.logging.BotLogger;
import services.Constants;

import java.util.ArrayList;

public class BookDB extends SuperDatabase {
    private static String LOGTAG = "Book DB: ";

    public static ObjectId createBook() {
        return createDBObject(Constants.BOOK_COLLECTION);
    }

    public static void insertBook(Book book) {
        insertObject(toDBObject(book).append("edition", book.getEdition())
                        .append("bestseller", book.isBestSeller())
                        .append("can_be_checked_out", book.canBeCheckedOut()),
                Constants.BOOK_COLLECTION);
    }

    public static Book getBook(ObjectId id) {
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
        updateObject(toDBObject(book), Constants.BOOK_COLLECTION);
    }

    public static void removeBook(ObjectId id) {
        removeObject(id, Constants.BOOK_COLLECTION);
    }


    public static Book toObject(DBObject book) {
        if (book == null) return null;
        else
            return new Book((ObjectId) book.get("_id"),
                    (String) book.get("url"),
                    (String) book.get("title"),
                    (String) book.get("edition"),
                    (ArrayList<String>) book.get("authors"),
                    (String) book.get("photo_id"),
                    (Double) book.get("price"),
                    (ArrayList<String>) book.get("keywords"),
                    (ArrayList<ObjectId>) book.get("copy_ids"),
                    (boolean) book.get("bestseller"),
                    (boolean) book.get("can_be_checked_out"));
    }
}