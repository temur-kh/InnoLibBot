package classes.Document;

import org.bson.types.ObjectId;

import java.util.ArrayList;

/**
 * This class is used in Journal(By articles we can find exact Journal)
 *
 */
public class JournalArticle {

    private ObjectId id;
    private String title;
    private ArrayList<String> authors;
    private ObjectId journalId;
    private ObjectId issueId;

    //constructor
    public JournalArticle(ObjectId id, String title, ArrayList<String> authors, ObjectId journalId, ObjectId issueId) {
        setId(id);
        setTitle(title);
        setAuthors(authors);
        setJournalId(journalId);
        setIssueId(issueId);
    }

    public ObjectId getId() {
        return id;
    }

    private void setId(ObjectId id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public ObjectId getJournalId() {
        return journalId;
    }

    public void setJournalId(ObjectId journal) {
        this.journalId = journalId;
    }

    public ObjectId getIssueId() {
        return issueId;
    }

    public void setIssueId(ObjectId issueId) {
        this.issueId = issueId;
    }
}
