package updatehandler;

import classes.CheckOut;
import classes.Document.Document;
import classes.User.Librarian;
import database.CheckOutDB;
import database.LibrarianDB;
import database.SuperDatabase;
import org.bson.types.ObjectId;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.logging.BotLogger;
import services.Commands;
import services.Texts;

import java.util.ArrayList;

/**
 * Class for requesting and confirming return of document.
 */
public class ReturnSystem {

    private static final String LOGTAG = "Return System: ";

    public static ArrayList<SendMessage> handle(Update update, String command, String id) {

        long userId = update.getCallbackQuery().getMessage().getChatId();
        ObjectId objectId = new ObjectId(id);
        CheckOut checkOut = CheckOutDB.getCheckOut(objectId);

        SendMessage msgForLibrarian = new SendMessage().setChatId(userId);
        SendMessage msgForPatron = new SendMessage().setChatId(checkOut.getPatronId());

        Document doc = (Document) SuperDatabase.getObject(checkOut.getDocId(), checkOut.getDocCollection());
        if (command.equals(Commands.REQUEST_RETURN)) {
            msgForPatron.setText(String.format(Texts.RETURN_DOC_REQUEST, doc.getTitle(), checkOut.getToDateLine()));
            msgForLibrarian.setText(Texts.REQUEST_SENT);
        } else if (command.equals(Commands.CONFIRM_RETURN)) {
            Librarian librarian = LibrarianDB.getLibrarian(userId);
            if (librarian == null) {
                BotLogger.severe(LOGTAG, "you are not a librarian! SECURITY EXCEPTION!");
                return null;
            }
            librarian.confirmCheckout(checkOut);
            msgForPatron.setText(String.format(Texts.RETURN_CONFIRMED_FOR_PATRON, doc.getTitle()));
            msgForLibrarian.setText(String.format(Texts.RETURN_CONFIRMED_FOR_LIBRARIAN, doc.getTitle()));
        }
        ArrayList<SendMessage> msgs = new ArrayList<>();
        msgs.add(msgForPatron);
        msgs.add(msgForLibrarian);
        return msgs;
    }
}
