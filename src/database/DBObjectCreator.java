package database;

import classes.CheckOut;
import classes.Document.Copy;
import classes.Document.Document;
import classes.Document.Issue;
import classes.Document.JournalArticle;
import classes.User.User;
import com.mongodb.BasicDBObject;
import services.CalendarObjectCreator;

public class DBObjectCreator {
    private static String LOGTAG = "DB Ovject Creator: ";

    public static BasicDBObject toDocumentDBObject(Document document) {
        return new BasicDBObject("_id", document.getId())
                .append("url", document.getUrl())
                .append("title", document.getTitle())
                .append("authors", document.getAuthors())
                .append("photo_id", document.getPhotoId())
                .append("price", document.getPrice())
                .append("keywords", document.getKeywords())
                .append("copy_ids", document.getCopyIds());
    }

    public static BasicDBObject toUserDBObject(User user) {
        return new BasicDBObject("_id", user.getId())
                .append("name", user.getName())
                .append("surname", user.getSurname())
                .append("email", user.getEmail())
                .append("phone_number", user.getPhoneNumber())
                .append("address", user.getAddress());
    }

    public static BasicDBObject toCopyDBObject(Copy copy) {
        return new BasicDBObject("_id", copy.getId())
                .append("doc_id", copy.getDocId())
                .append("doc_address", new BasicDBObject("room", copy.getAddress().getRoom())
                        .append("level", copy.getAddress().getLevel())
                        .append("doc_case", copy.getAddress().getDocCase()))
                .append("checked_out", copy.isCheckedOut());
    }

    public static BasicDBObject toIssueDBObject(Issue issue) {
        return new BasicDBObject("_id", issue.getId())
                .append("journal_id", issue.getJournalId())
                .append("editors", issue.getEditors())
                .append("publication_date", CalendarObjectCreator.createCalendarLine(issue.getPublicationDate()))
                .append("article_ids", issue.getArticleIds());
    }

    public static BasicDBObject toArticleDBObject(JournalArticle article) {
        return new BasicDBObject("_id", article.getId())
                .append("title", article.getTitle())
                .append("authors", article.getAuthors())
                .append("journal_id", article.getJournalId())
                .append("issue_id", article.getIssueId());
    }

    public static BasicDBObject toCheckOutDBObject(CheckOut checkOut) {
        return new BasicDBObject("_id", checkOut.getId())
                .append("from_date", CalendarObjectCreator.createCalendarLine(checkOut.getFromDate()))
                .append("to_date", CalendarObjectCreator.createCalendarLine(checkOut.getToDate()))
                .append("person_id", checkOut.getPersonId())
                .append("doc_id", checkOut.getDocId())
                .append("copy_id", checkOut.getCopyId());
    }
}
