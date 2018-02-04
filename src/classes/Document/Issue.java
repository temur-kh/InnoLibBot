package classes.Document;

import services.CalendarObjectCreator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Issue {

    private String id;
    private String journalId;
    private ArrayList<String> editors;
    private Calendar publicationDate;
    private ArrayList<String> articleIds;

    public Issue(String id, String journalId, ArrayList<String> editors, Calendar publicationDate) {
        setId(id);
        setJournalId(journalId);
        setEditors(editors);
        setPublicationDate(publicationDate);
        articleIds = new ArrayList<>();
    }

    public Issue(String id, String journalId, ArrayList<String> editors, Calendar publicationDate, ArrayList<String> articleIds) {
        setId(id);
        setJournalId(journalId);
        setEditors(editors);
        setPublicationDate(publicationDate);
        setArticleIds(articleIds);
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public String getJournalId() {
        return journalId;
    }

    public void setJournalId(String journalId) {
        this.journalId = journalId;
    }

    public ArrayList<String> getEditors() {
        return editors;
    }

    public void setEditors(ArrayList<String> editors) {
        this.editors = editors;
    }

    public void addEditor(String editor) { editors.add(editor);}

    public Calendar getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Calendar publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setPublicationDate(String date) {
        publicationDate = CalendarObjectCreator.createCalendarObject(date);
    }

    public ArrayList<String> getArticleIds() {
        return articleIds;
    }

    public void setArticleIds(ArrayList<String> articleIds) {
        this.articleIds = articleIds;
    }

    public boolean addArticleId(String articleId) {
        return articleIds.add(articleId);
    }
}
