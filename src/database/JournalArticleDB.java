package database;

import classes.Document.JournalArticle;
import com.mongodb.*;
import org.telegram.telegrambots.logging.BotLogger;

import java.util.ArrayList;
import java.util.List;

public class JournalArticleDB {
    private static String LOGTAG = "JournalArticle DB: ";

    public static void insertArticle(JournalArticle article) {
        insertArticle(toDBObject(article));
    }

    public static void insertArticle(BasicDBObject object) {
        DBCollection collection = DatabaseManager.getCollection("JournalArticle");
        try {
            collection.insert(object);
        } catch (DuplicateKeyException e) {
            BotLogger.severe(LOGTAG, "duplicate found!");
        }
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

    public static void removeArticle(String id) {
        DBCollection collection = DatabaseManager.getCollection("JournalArticle");
        BasicDBObject query = new BasicDBObject("_id", id);
        collection.remove(query);
    }

    public static JournalArticle toObject(DBObject article) {
        if(article == null) return null;
        else
            return new JournalArticle((String) article.get("_id"),
                    (String) article.get("title"),
                    (ArrayList<String>) article.get("authors"),
                    (String) article.get("journal_id"),
                    (String) article.get("issue_id"));
    }

    public static BasicDBObject toDBObject(JournalArticle article) {
        return new BasicDBObject("_id", article.getId())
                .append("title", article.getTitle())
                .append("authors", article.getAuthors())
                .append("journal_id", article.getJournalId())
                .append("issue_id", article.getIssueId());
    }
}
