package updatehandler;

import classes.Document.Document;
import classes.Notification;
import database.NotificationDB;
import database.PriorityQueueDB;
import database.SuperDatabase;
import org.bson.types.ObjectId;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import services.Texts;

import java.util.ArrayList;

/**
 * Class is used to notify patrons for available documents.
 */
public class NotificationSystem {

    private static String LOGTAG = "Notification system: ";

    public static ArrayList<SendMessage> notifyPatron(long librarianId, ObjectId docId, String collection) {
        long patronId = PriorityQueueDB.getNextPatron(docId, true).getId();
        ArrayList<SendMessage> msgs = new ArrayList<>();
        SendMessage msgForLibrarian = new SendMessage().setChatId(librarianId);
        SendMessage msgForPatron = new SendMessage().setChatId(patronId);
        Document doc = (Document) SuperDatabase.getObject(docId, collection);

        Notification notification = new Notification(patronId, docId, collection);
        NotificationDB.insert(notification);
        msgForPatron.setText(String.format(Texts.NOTIFICATION_RECEIVED, doc.getTitle()));
        msgForLibrarian.setText(String.format(Texts.NOTIFICATION_SENT, patronId, doc.getTitle()));
        msgs.add(msgForPatron);
        msgs.add(msgForLibrarian);

        BotLogger.severe(LOGTAG, "Notified patrons with id:", patronId);

        return msgs;
    }
}
