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

public class SuperDatabase {
    private static String LOGTAG = "Super Database: ";

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

    public static void updateObject(BasicDBObject object, String db) {
        DBCollection collection = DatabaseManager.getCollection(db);
        collection.update(new BasicDBObject("_id", object.get("_id")), object);
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
}