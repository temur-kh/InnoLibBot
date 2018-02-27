package database;

import classes.Document.AVMaterial;
import com.mongodb.*;
import org.bson.types.ObjectId;
import org.telegram.telegrambots.logging.BotLogger;
import services.Constants;

import java.util.ArrayList;
import java.util.List;

public class AVMaterialDB extends SuperDatabase {

    private static String LOGTAG = "AVMaterial DB: ";

    public static ObjectId createAVMaterial() {
        return createDBObject(Constants.AVLMATERIAL_COLLECTION);
    }

    public static void insertAVMaterial(AVMaterial material) {
        insertObject(toDBObject(material), Constants.AVLMATERIAL_COLLECTION);
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
        updateObject(toDBObject(material), Constants.AVLMATERIAL_COLLECTION);
    }

    public static void removeAVMaterial(ObjectId id) {
        removeObject(id, Constants.AVLMATERIAL_COLLECTION);
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
