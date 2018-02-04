package classes.Document;

import org.bson.types.ObjectId;
import services.CalendarObjectCreator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This class is used in Journal(By issue we can find exact Journal)
 */
public class Issue {

    private ObjectId id;
    private ObjectId journalId;
    private ArrayList<String> editors;
    private Calendar publicationDate;
    private ArrayList<String> articleIds;

    public Issue(ObjectId id, ObjectId journalId, ArrayList<String> editors, Calendar publicationDate) {
        setId(id);
        setJournalId(journalId);
        setEditors(editors);
        setPublicationDate(publicationDate);
        articleIds = new ArrayList<>();
    }

    public Issue(ObjectId id, ObjectId journalId, ArrayList<String> editors, Calendar publicationDate, ArrayList<String> articleIds) {
        setId(id);
        setJournalId(journalId);
        setEditors(editors);
        setPublicationDate(publicationDate);
        setArticleIds(articleIds);
    }

    public ObjectId getId() {
        return id;
    }

    private void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getJournalId() {
        return journalId;
    }

    public void setJournalId(ObjectId journalId) {
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
