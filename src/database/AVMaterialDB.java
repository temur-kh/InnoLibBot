package database;

import classes.Document.AVMaterial;
import com.mongodb.*;
import org.bson.types.ObjectId;
import org.telegram.telegrambots.logging.BotLogger;

import java.util.ArrayList;
import java.util.List;

public class AVMaterialDB extends DocumentDB {

    private static String LOGTAG = "AVMaterial DB: ";

    public static void insertAVMaterial(AVMaterial material) {
        insertAVMaterial(toDBObject(material));
    }

    public static void insertAVMaterial(BasicDBObject object) {
        DBCollection collection = DatabaseManager.getCollection("AVMaterial");
        try {
            collection.insert(object);
        } catch (DuplicateKeyException e) {
            BotLogger.severe(LOGTAG, "duplicate found!");
        }
    }

    public static AVMaterial getAVMaterial(String id) {
        DBCollection collection = DatabaseManager.getCollection("AVMaterial");
        BasicDBObject query = new BasicDBObject("_id", id);
        DBCursor cursor = collection.find(query);
        return toObject(cursor.one());
    }

    public static List<AVMaterial> getAVMaterialsList() {
        DBCollection collection = DatabaseManager.getCollection("AVMaterial");
        ArrayList<AVMaterial> materials = new ArrayList<>();
        DBCursor cursor = collection.find(new BasicDBObject());
        for (DBObject dbObject : cursor) {
            materials.add(toObject(dbObject));
        }
        return materials;
    }

    public static void updateAVMaterial(AVMaterial material) {
        updateAVMaterial(toDBObject(material));
    }

    public static void updateAVMaterial(BasicDBObject object) {
        DBCollection collection = DatabaseManager.getCollection("AVMaterial");
        collection.update(new BasicDBObject("_id", object.get("_id")), object);
    }

    public static void removeAVMaterial(String id) {
        DBCollection collection = DatabaseManager.getCollection("AVMaterial");
        BasicDBObject query = new BasicDBObject("_id", id);
        collection.remove(query);
    }

    public static AVMaterial toObject(DBObject material) {
        if (material == null) return null;
        else
            return new AVMaterial((ObjectId) material.get("_id"),
                    (String) material.get("url"),
                    (String) material.get("title"),
                    (ArrayList<String>) material.get("authors"),
                    (String) material.get("photo_id"),
                    (Double) material.get("price"),
                    (ArrayList<String>) material.get("keywords"),
                    (ArrayList<ObjectId>) material.get("copy_ids"));
    }
}
