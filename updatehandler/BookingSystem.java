package updatehandler;

import classes.CheckOut;
import classes.Document.Document;
import classes.User.Patron;
import database.*;
import org.bson.types.ObjectId;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.logging.BotLogger;
import services.Constants;
import services.Texts;

/**
 * Class of booking system.
 */
public class BookingSystem {

    private static String LOGTAG = "Booking system: ";
    //check out a document if possible
    //not possible if did not provide personal data or no copies of document are available.
    public static SendMessage checkOut(Update update, String id, String collection) {
        long userId = update.getCallbackQuery().getMessage().getChatId();
        SendMessage msg = new SendMessage().setChatId(userId);

        ObjectId objectId = new ObjectId(id);

        Document document = (Document) SuperDatabase.getObject(objectId, collection);
        Patron patron = PatronDB.getPatron(userId);

        if (LibrarianDB.getLibrarian(userId) != null) {
            msg.setText(Texts.LIBRARIAN_CANNOT_CHECK_OUT);
        } else if (patron == null) {
            msg.setText(Texts.DID_NOT_PROVIDE_PERSONAL_DATA);
        } else if (document.hasFreeCopies()) {
            CheckOut checkOut = patron.checkOutDocument(document, Constants.BOOK_COLLECTION);
            msg.setText(String.format(Texts.CHECKED_OUT_DOCUMENT_FORMAT, document.getTitle(), checkOut.getToDateLine()));
        } else if (CheckOutDB.getCheckOut(userId, objectId) == null){
            PriorityQueueDB.insert(PatronDB.getPatron(userId), objectId, collection);
            msg.setText(Texts.PUT_IN_PRIORITY_QUEUE);
        } else {
            msg.setText(Texts.NOT_AVAILABLE);
        }
        BotLogger.severe(LOGTAG, "Sent checkout message");
        return msg;
    }
}