package classes.Document;

import java.util.ArrayList;
import java.util.Calendar;

public class Issue {

    private String id;
    private Journal journal;
    private ArrayList<String> editor;
    private Calendar publicationDate;
    private ArrayList<JournalArticle> articles;

    public Issue(String id, Journal journal, ArrayList<String> editor, Calendar publicationDate) {
        setId(id);
        setJournal(journal);
        setEditor(editor);
        setPublicationDate(publicationDate);
        articles = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public ArrayList<String> getEditor() {
        return editor;
    }

    public void setEditor(ArrayList<String> editor) {
        this.editor = editor;
    }

    public Calendar getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Calendar publicationDate) {
        this.publicationDate = publicationDate;
    }

    public ArrayList<JournalArticle> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<JournalArticle> articles) {
        this.articles = articles;
    }

    public boolean addArticle(JournalArticle article) {
        return articles.add(article);
    }
}
