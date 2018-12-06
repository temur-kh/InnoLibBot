package classes;

import classes.Document.Document;
import database.SuperDatabase;
import org.bson.types.ObjectId;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import services.DateTime;
import services.Texts;
import updatehandler.GUISystem;

import java.util.Date;

/**
 * This class is used to store and create notifications to send.
 */
public class Notification {
    private long patronId;
    private ObjectId docId;
    private String docType;
    private Date date;

    public Notification(long patronId, ObjectId docId, String docType, Date date) {
        this.patronId = patronId;
        this.docId = docId;
        this.docType = docType;
        this.date = date;
    }

    public Notification(long patronId, ObjectId docId, String docType) {
        this.patronId = patronId;
        this.docId = docId;
        this.docType = docType;
        date = DateTime.tomorrowDate();
    }

    public long getPatronId() {
        return patronId;
    }

    public void setPatronId(long patronId) {
        this.patronId = patronId;
    }

    public ObjectId getDocId() {
        return docId;
    }

    public void setDocId(ObjectId docId) {
        this.docId = docId;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String unavailableDocumentText() {
        Document document = (Document) SuperDatabase.getObject(docId, docType);
        return String.format(Texts.UNAVAILABLE_DUE_TO_OUTSTANDING_REQUEST, document.getTitle());
    }

    public SendMessage unavailableDocumentSendMessage() {
        return new SendMessage().setChatId(patronId).setText(unavailableDocumentText()).setReplyMarkup(GUISystem.simpleMenu());
    }

    public String expiredNotificationText() {
        Document document = (Document) SuperDatabase.getObject(docId, docType);
        return String.format(Texts.EXPIRED_NOTIFICATION, document.getTitle());
    }

    public SendMessage expiredNotificationSendMessage() {
        return new SendMessage().setChatId(patronId).setText(expiredNotificationText()).setReplyMarkup(GUISystem.simpleMenu());
    }
}
