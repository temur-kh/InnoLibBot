package database;

import classes.Document.Document;
import com.mongodb.*;

import java.util.ArrayList;

public class DocumentDB {
    private static String LOGTAG = "Document DB: ";
    public static BasicDBObject toDBObject(Document document) {
        return new BasicDBObject("_id", document.getId())
                .append("url", document.getUrl())
                .append("title", document.getTitle())
                .append("authors", document.getAuthors())
                .append("photo_id", document.getPhotoId())
                .append("price", document.getPrice())
                .append("keywords", document.getKeywords())
                .append("copy_ids", document.getCopyIds());
    }

    public static Document toObject(DBObject document) {
        return new Document((long) document.get("_id"),
                (String) document.get("url"),
                (String) document.get("title"),
                (ArrayList<String>) document.get("authors"),
                (String) document.get("photo_id"),
                (Double) document.get("price"),
                (ArrayList<String>) document.get("keywords"),
                (ArrayList<String>) document.get("copy_ids"));
    }
}
