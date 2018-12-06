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
 * This class is used to make basic queries to the database using DatabaseManager to access collections.
 * The methods supported creation, insertion, get, update, modification, deletion operations on objects of classes:
 * Book, Journal, AVMaterial, CheckOut, Copy, Issue, JournalArticle, Librarian, Patron.
 */
public class SuperDatabase {
    /**
     * Used in tracing the errors in a log.
     */
    private static final String LOGTAG = "Super Database: ";

    /**
     * This method is used to create an instance of DBObject in a collection
     * to allocate a unique id (instance of ObjectId) for the object that is to be inserted later.
     *
     * @param collectionName name of the collection where the instance of DBObject is to be created.
     * @return the id of the created instance (type ObjectId)
     */
    public static ObjectId createDBObject(String collectionName) {
        DBCollection collection = DatabaseManager.getCollection(collectionName);
        BasicDBObject object = new BasicDBObject();
        collection.insert(object);
        return (ObjectId) object.get("_id");
    }

    /**
     * This method is used to insert an object (instance of BasicDBObject)
     * into the collection named by collectionName.
     *
     * @param object         object to be inserted into the collection
     * @param collectionName the name of the collection
     */
    public static void insertObject(BasicDBObject object, String collectionName) {
        DBCollection collection = DatabaseManager.getCollection(collectionName);
        BasicDBObject query = new BasicDBObject("_id", object.get("_id"));
        if (collection.find(query).one() != null) {
            updateObject(object, collectionName);
        } else {
            collection.insert(object);
        }
    }

    /**
     * This method is used to get an object (instance of Object) by its id and name of collection.
     * If there is no such an object found, the result will be null.
     *
     * @param id             the id by which the object will be found.
     * @param collectionName the name of the collection by which the object will be found.
     * @return
     */
    public static Object getObject(Object id, String collectionName) {
        DBCollection collection = DatabaseManager.getCollection(collectionName);
        DBCursor cursor = collection.find(new BasicDBObject("_id", id));
        return toObject(cursor.one(), collectionName);
    }

    /**
     * This method is used to get the list of all objects (instances of Object) in the collection named collectionName.
     *
     * @param collectionName the name of the collection.
     * @return the list of objects contained in the collection.
     */
    public static ArrayList<Object> getObjectsList(String collectionName) {
        DBCollection collection = DatabaseManager.getCollection(collectionName);
        ArrayList<Object> objects = new ArrayList<>();
        DBCursor cursor = collection.find(new BasicDBObject());
        for (DBObject dbObject : cursor) {
            objects.add(toObject(dbObject, collectionName));
        }
        return objects;
    }

    /**
     * This method is used to update an object by another object in the collection named collectionName.
     * The object is replaced by another object according to the id the object contains.
     *
     * @param object         the object that should replace another object by the same id.
     * @param collectionName the name of the collection.
     */
    public static void updateObject(BasicDBObject object, String collectionName) {
        DBCollection collection = DatabaseManager.getCollection(collectionName);
        collection.update(new BasicDBObject("_id", object.get("_id")), object);
    }

    /**
     * This method is used to modify one feature of the object found by the id and collectionName.
     *
     * @param id             the id of the object which feature should be modified.
     * @param key            the key of the feature to be modified.
     * @param value          the value to put into the feature's field.
     * @param collectionName the name of the collection.
     */
    public static void modifyObject(Object id, String key, Object value, String collectionName) {
        if ("_id".equals(key))
            return;
        DBCollection collection = DatabaseManager.getCollection(collectionName);
        collection.update(new BasicDBObject("_id", id), new BasicDBObject("$set", new BasicDBObject(key, value)));
    }

    /**
     * This method is used to remove an object from the collection by its id and collection's name.
     *
     * @param id             the id by which the removal object will be found.
     * @param collectionName the name of the collection by which the removal object will be found.
     */
    public static void removeObject(Object id, String collectionName) {
        DBCollection collection = DatabaseManager.getCollection(collectionName);
        BasicDBObject query = new BasicDBObject("_id", id);
        collection.remove(query);
    }

    /**
     * This method is used to convert an instance of Object into the instance of type BasicDBObject.
     * For each type of the instance different methods are used that are allocated in class DBObjectCreator.
     * In case the object's type is not identified an empty instance of BasicDBObject will be the result.
     *
     * @param object the object to be converted.
     * @return an instance of class BasicDBObject.
     */
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

    /**
     * This method is used to convert an instance of DBObject (found by the collection's name) into the instance of type Object.
     * For each collection's name different methods are used that are allocated
     * in classes BookDB, JournalDB, AVMaterialDB, CheckOutDB, CopyDB, IssueDB, JournalArticleDB, LibrarianDB, PatronDB.
     * Incase the collection's name is not recognised the null object is returned and the log is printed.
     *
     * @param dbObject       the object of class DBObject to be converted.
     * @param collectionName the collection's name used to find suitable convertation method.
     * @return an instance of class Object that can be casted to some specific class according to the collection's name.
     */
    public static Object toObject(DBObject dbObject, String collectionName) {
        if (dbObject == null)
            return null;
        Object object = null;
        switch (collectionName) {
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
                BotLogger.severe(LOGTAG, "COULD NOT FIND COLLECTION!\nName of the collection: " + collectionName + "\nObject: " + dbObject);
        }
        return object;
    }
}