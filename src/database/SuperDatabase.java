package database;

import classes.CheckOut;
import classes.Document.Copy;
import classes.Document.Document;
import classes.Document.Issue;
import classes.Document.JournalArticle;
import classes.User.User;
import com.mongodb.*;
import org.bson.types.ObjectId;
import org.telegram.telegrambots.logging.BotLogger;
import services.Constants;

import java.util.ArrayList;

/**
 * Super class for other DBs, contains many db managing functions.
 */
public class SuperDatabase {
    private static final String LOGTAG = "Super Database: ";

    public static ObjectId createDBObject(String db) {
        DBCollection collection = DatabaseManager.getCollection(db);
        BasicDBObject object = new BasicDBObject();
        try {
            collection.insert(object);
        } catch (DuplicateKeyException e) {
            BotLogger.severe(LOGTAG, "duplicate found!");
        }
        return (ObjectId) object.get("_id");
    }

    public static void insertObject(BasicDBObject object, String db) {
        DBCollection collection = DatabaseManager.getCollection(db);
        BasicDBObject query = new BasicDBObject("_id", object.get("_id"));
        if (collection.find(query).one() != null) {
            updateObject(object, db);
        } else {
            try {
                collection.insert(object);
            } catch (DuplicateKeyException e) {
                BotLogger.severe(LOGTAG, "duplicate found!");
            }
        }
    }

    public static Object getObject(Object id, String db) {
        DBCollection collection = DatabaseManager.getCollection(db);
        ArrayList<Object> objects = new ArrayList<>();
        DBCursor cursor = collection.find(new BasicDBObject("_id", id));
        return toObject(cursor.one(), db);
    }

    public static ArrayList<Object> getObjectsList(String db) {
        DBCollection collection = DatabaseManager.getCollection(db);
        ArrayList<Object> objects = new ArrayList<>();
        DBCursor cursor = collection.find(new BasicDBObject());
        for (DBObject dbObject : cursor) {
            objects.add(toObject(dbObject, db));
        }
        return objects;
    }

    public static void updateObject(BasicDBObject object, String db) {
        DBCollection collection = DatabaseManager.getCollection(db);
        collection.update(new BasicDBObject("_id", object.get("_id")), object);
    }

    public static void modifyObject(Object id, String key, Object value, String db) {
        DBCollection collection = DatabaseManager.getCollection(db);
        collection.update(new BasicDBObject("_id", id), new BasicDBObject("$set",  new BasicDBObject(key, value)));
    }

    public static void removeObject(Object id, String db) {
        DBCollection collection = DatabaseManager.getCollection(db);
        BasicDBObject query = new BasicDBObject("_id", id);
        collection.remove(query);
    }

    public static BasicDBObject toDBObject(Object object) {
        if (object instanceof Document) {
            return DBObjectCreator.toDocumentDBObject((Document) object);
        } else if (object instanceof User) {
            return DBObjectCreator.toUserDBObject((User) object);
        } else if (object instanceof Copy) {
            return DBObjectCreator.toCopyDBObject((Copy) object);
        } else if (object instanceof Issue) {
            return DBObjectCreator.toIssueDBObject((Issue) object);
        } else if (object instanceof JournalArticle) {
            return DBObjectCreator.toArticleDBObject((JournalArticle) object);
        } else if (object instanceof CheckOut) {
            return DBObjectCreator.toCheckOutDBObject((CheckOut) object);
        } else {
            return new BasicDBObject();
        }
    }

    public static Object toObject(DBObject dbObject, String db) {
        if (dbObject == null)
            return null;
        Object object = null;
        switch (db) {
            case Constants.BOOK_COLLECTION:
                object = BookDB.toObject(dbObject);
                break;
            case Constants.JOURNAL_COLLECTION:
                object = JournalDB.toObject(dbObject);
                break;
            case Constants.AVMATERIAL_COLLECTION:
                object = AVMaterialDB.toObject(dbObject);
                break;
            case Constants.CHECKOUT_COLLECTION:
                object = CheckOutDB.toObject(dbObject);
                break;
            case Constants.COPY_COLLECTION:
                object = CopyDB.toObject(dbObject);
                break;
            case Constants.ISSUE_COLLECTION:
                object = IssueDB.toObject(dbObject);
                break;
            case Constants.JOURNAL_ARTICLE_COLLECTION:
                object = JournalArticleDB.toObject(dbObject);
                break;
            case Constants.LIBRARIAN_COLLECTION:
                object = LibrarianDB.toObject(dbObject);
                break;
            case Constants.PATRON_COLLECTION:
                object = PatronDB.toObject(dbObject);
                break;
            default:
                BotLogger.severe(LOGTAG, "could not find collection!");
        }
        return object;
    }
}