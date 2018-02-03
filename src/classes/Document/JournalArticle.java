package classes.Document;

import java.util.ArrayList;

public class JournalArticle {

    private String id;
    private String title;
    private ArrayList<String> authors;
    private String journalId;
    private String issueId;

    public JournalArticle(String id, String title, ArrayList<String> authors, String journalId, String issueId) {
        setId(id);
        setTitle(title);
        setAuthors(authors);
        setJournalId(journalId);
        setIssueId(issueId);
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
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

    public String getJournalId() {
        return journalId;
    }

    public void setJournalId(String journal) {
        this.journalId = journalId;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }
}
