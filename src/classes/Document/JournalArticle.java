package classes.Document;

import java.util.ArrayList;

public class JournalArticle {

    private String id;
    private String title;
    private ArrayList<String> authors;
    private Journal journal;
    private Issue issue;

    public JournalArticle(String id, String title, ArrayList<String> authors, Journal journal, Issue issue) {
        setId(id);
        setTitle(title);
        setAuthors(authors);
        setJournal(journal);
        setIssue(issue);
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

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }
}
