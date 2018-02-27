package database;

import classes.User.Librarian;
import classes.User.User;
import com.mongodb.*;
import org.bson.types.ObjectId;
import org.telegram.telegrambots.logging.BotLogger;
import services.Commands;
import services.Constants;

import java.util.ArrayList;

public class RegistrationStateDB {
    private static String LOGTAG = "Registration State DB: ";

    public static void insertNewState(long userId) {
        DBCollection collection = DatabaseManager.getCollection("States");
        BasicDBObject object = new BasicDBObject("_id", userId).append("state", Commands.INPUT_NAME_STATE);
        try {
            collection.insert(object);
        } catch (DuplicateKeyException e) {
            BotLogger.severe(LOGTAG, "duplicate found!");
        }
    }

    public static BasicDBObject getState(long userId) {
        DBCollection collection = DatabaseManager.getCollection("States");
        BasicDBObject query = new BasicDBObject("_id", userId);
        DBCursor cursor = collection.find(query);
        return (BasicDBObject) cursor.one();
    }

    public static ArrayList<BasicDBObject> getStatesList() {
        DBCollection collection = DatabaseManager.getCollection("States");
        ArrayList<BasicDBObject> states = new ArrayList<>();
        DBCursor cursor = collection.find(new BasicDBObject());
        for (DBObject dbObject : cursor) {
            states.add((BasicDBObject) dbObject);
        }
        return states;
    }

    public static void dropStateCollection(User user) {
        if(user.getClass() != Librarian.class) {
            throw new SecurityException(LOGTAG + "permission denied!");
        }
        DBCollection collection = DatabaseManager.getCollection("States");
        collection.drop();
    }

    public static void modifyState(BasicDBObject object, Integer state) {
        DBCollection collection = DatabaseManager.getCollection("States");
        long userId = (long) object.get("_id");
        collection.update(new BasicDBObject("_id", userId), new BasicDBObject("$set", object));
    }

    public static void removeState(long userId) {
        DBCollection collection = DatabaseManager.getCollection("States");
        BasicDBObject query = new BasicDBObject("_id", userId);
        collection.remove(query);
    }
}
