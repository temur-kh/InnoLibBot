package database;

import classes.User.Admin;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import services.Constants;

public class AdminDB {
    public static void replaceAdmin(Admin admin) {
        DBCollection collection = DatabaseManager.getCollection(Constants.ADMIN_COLLECTION);
        DBCursor cursor = collection.find();
        if (cursor.size() != 1) {
            throw new IndexOutOfBoundsException();
        } else {
            collection.drop();
            collection.insert(DBObjectCreator.toUserDBObject(admin));
        }
    }

    public static Admin getAdmin() {
        DBCollection collection = DatabaseManager.getCollection(Constants.ADMIN_COLLECTION);
        DBCursor cursor = collection.find();
        return toObject(cursor.one());
    }

    public static Admin toObject(DBObject admin) {
        if (admin == null)
            return null;
        else
            return new Admin((long) admin.get("_id"),
                    (String) admin.get("name"),
                    (String) admin.get("surname"),
                    (String) admin.get("email"),
                    (String) admin.get("phone_number"),
                    (String) admin.get("address"));
    }
}
