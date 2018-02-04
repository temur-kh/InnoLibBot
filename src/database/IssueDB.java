package database;

import classes.Document.Issue;
import com.mongodb.*;
import org.telegram.telegrambots.logging.BotLogger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class IssueDB {
    private static String LOGTAG = "Issue DB: ";

    public static void insertIssue(Issue issue) {
        insertIssue(toDBObject(issue));
    }

    public static void insertIssue(BasicDBObject object) {
        DBCollection collection = DatabaseManager.getCollection("Issue");
        try {
            collection.insert(object);
        } catch (DuplicateKeyException e) {
            BotLogger.severe(LOGTAG, "duplicate found!");
        }
    }

    public static Issue getIssue(String id) {
        DBCollection collection = DatabaseManager.getCollection("Issue");
        BasicDBObject query = new BasicDBObject("_id", id);
        DBCursor cursor = collection.find(query);
        return toObject(cursor.one());
    }

    public static List<Issue> getIssuesList() {
        DBCollection collection = DatabaseManager.getCollection("Issue");
        ArrayList<Issue> issues = new ArrayList<>();
        DBCursor cursor = collection.find(new BasicDBObject());
        for (DBObject dbObject : cursor) {
            issues.add(toObject(dbObject));
        }
        return issues;
    }

    public static void updateIssue(Issue issue) {
        updateIssue(toDBObject(issue));
    }

    public static void updateIssue(BasicDBObject object) {
        DBCollection collection = DatabaseManager.getCollection("Issue");
        collection.update(new BasicDBObject("_id", object.get("_id")), object);
    }

    public static void removeIssue(String id) {
        DBCollection collection = DatabaseManager.getCollection("Issue");
        BasicDBObject query = new BasicDBObject("_id", id);
        collection.remove(query);
    }

    public static Issue toObject(DBObject issue) {
        if(issue == null) return null;
        else
            return new Issue((String) issue.get("_id"),
                    (String) issue.get("journal_id"),
                    (ArrayList<String>) issue.get("editors"),
                    Issue.createCalendarObject((String)issue.get("publication_date")),
                    (ArrayList<String>) issue.get("article_ids"));
    }
    
    public static BasicDBObject toDBObject(Issue issue) {
        return new BasicDBObject("_id", issue.getId())
                .append("journal_id", issue.getJournalId())
                .append("editors", issue.getEditors())
                .append("publication_date", issue.getPublicationDate().getTime().toString())
                .append("article_ids", issue.getArticleIds());
    }
}
