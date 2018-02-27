package database;

import classes.Document.Issue;
import com.mongodb.*;
import org.bson.types.ObjectId;
import org.telegram.telegrambots.logging.BotLogger;
import services.CalendarObjectCreator;
import services.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class IssueDB extends SuperDatabase {
    private static String LOGTAG = "Issue DB: ";

    public static ObjectId createIssue() {
        return createDBObject(Constants.ISSUE_COLLECTION);
    }

    public static void insertIssue(Issue issue) {
        insertObject(toDBObject(issue), Constants.ISSUE_COLLECTION);
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
        updateObject(toDBObject(issue), Constants.ISSUE_COLLECTION);
    }

    public static void removeIssue(ObjectId id) {
        removeObject(id, Constants.ISSUE_COLLECTION);
    }

    public static Issue toObject(DBObject issue) {
        if (issue == null) return null;
        else
            return new Issue((ObjectId) issue.get("_id"),
                    (ObjectId) issue.get("journal_id"),
                    (ArrayList<String>) issue.get("editors"),
                    CalendarObjectCreator.createCalendarObject((String) issue.get("publication_date")),
                    (ArrayList<String>) issue.get("article_ids"));
    }
}
