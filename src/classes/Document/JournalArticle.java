package classes.Document;

import java.util.ArrayList;

public class JournalArticle {

    private long id;
    private String title;
    private ArrayList<String> authors;
    private Journal journal;
    private Issue issue;

    public JournalArticle(long id, String title, ArrayList<String> authors, Journal journal, Issue issue) {
        setId(id);
        setTitle(title);
        setAuthors(authors);
        setJournal(journal);
        setIssue(issue);
    }

    public long getId() {
        return id;
    }

    private void setId(long id) {
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
