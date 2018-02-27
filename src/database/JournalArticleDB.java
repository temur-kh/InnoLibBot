package database;

import classes.Document.JournalArticle;
import com.mongodb.*;
import org.bson.types.ObjectId;
import services.Constants;

import java.util.ArrayList;
import java.util.List;

public class JournalArticleDB extends SuperDatabase {
    private static String LOGTAG = "JournalArticle DB: ";

    public static ObjectId createArticle() {
        return createDBObject(Constants.ARTICLE_COLLECTION);
    }

    public static void insertArticle(JournalArticle article) {
        insertObject(toDBObject(article), Constants.ARTICLE_COLLECTION);
    }

    public static JournalArticle getArticle(String id) {
        DBCollection collection = DatabaseManager.getCollection("JournalArticle");
        BasicDBObject query = new BasicDBObject("_id", id);
        DBCursor cursor = collection.find(query);
        return toObject(cursor.one());
    }

    public static List<JournalArticle> getArticlesList() {
        DBCollection collection = DatabaseManager.getCollection("JournalArticle");
        ArrayList<JournalArticle> articles = new ArrayList<>();
        DBCursor cursor = collection.find(new BasicDBObject());
        for (DBObject dbObject : cursor) {
            articles.add(toObject(dbObject));
        }
        return articles;
    }

    public static void updateArticle(JournalArticle article) {
        updateObject(toDBObject(article), Constants.ARTICLE_COLLECTION);
    }

    public static void removeArticle(ObjectId id) {
        removeObject(id, Constants.ARTICLE_COLLECTION);
    }

    public static JournalArticle toObject(DBObject article) {
        if (article == null) return null;
        else
            return new JournalArticle((ObjectId) article.get("_id"),
                    (String) article.get("title"),
                    (ArrayList<String>) article.get("authors"),
                    (ObjectId) article.get("journal_id"),
                    (ObjectId) article.get("issue_id"));
    }
}