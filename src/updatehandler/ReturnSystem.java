package updatehandler;

import classes.CheckOut;
import classes.Document.Document;
import classes.User.Librarian;
import database.CheckOutDB;
import database.LibrarianDB;
import database.PatronDB;
import database.SuperDatabase;
import org.bson.types.ObjectId;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.logging.BotLogger;
import services.Commands;
import services.Constants;
import services.DateTime;
import services.Texts;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for requesting and confirming return of document.
 */
public class ReturnSystem {

    public static final String LOGTAG = "Return System: ";

    public static ArrayList<SendMessage> handle(Update update, String command, String id) {

        long userId = update.getCallbackQuery().getMessage().getChatId();
        CheckOut checkOut = CheckOutDB.getCheckOut(new ObjectId(id));

        ArrayList<SendMessage> msgs = new ArrayList<>();

        Document doc = (Document) SuperDatabase.getObject(checkOut.getDocId(), checkOut.getDocCollection());
        if (command.equals(Commands.REQUEST_RETURN)) {
            checkOut.setToDate(DateTime.tomorrowCalendar());
            checkOut.setRenewed(true);
            CheckOutDB.updateCheckOut(checkOut);
            msgs.add(sendLibrarianReturnRequest(checkOut.getPatronId(), doc));
            msgs.add(receiveLibrarianReturnRequest(userId));
        } else if (command.equals(Commands.CONFIRM_RETURN)) {
            SendMessage msgForLibrarian = new SendMessage().setChatId(userId);
            SendMessage msgForPatron = new SendMessage().setChatId(checkOut.getPatronId());
            Librarian librarian = LibrarianDB.getLibrarian(userId);
            if (librarian == null) {
                BotLogger.severe(LOGTAG, "you are not a librarian! SECURITY EXCEPTION!");
                return null;
            }
            librarian.confirmCheckout(checkOut);
            msgForPatron.setText(String.format(Texts.RETURN_CONFIRMED_FOR_PATRON, doc.getTitle()));
            msgForLibrarian.setText(String.format(Texts.RETURN_CONFIRMED_FOR_LIBRARIAN, doc.getTitle()));
            msgs.add(msgForPatron);
            msgs.add(msgForLibrarian);
            for (SendMessage msg : NotificationSystem.notifyPatron(userId, checkOut.getDocId(), checkOut.getDocCollection())) {
                msgs.add(msg);
            }
        }
        return msgs;
    }

    //TODO UPDATE
    public static SendMessage sendLibrarianReturnRequest(long patronId, Document doc) {
        return new SendMessage().setChatId(patronId)
                .setText(String.format(Texts.RETURN_DOC_REQUEST, doc.getTitle()))
                .setReplyMarkup(GUISystem.simpleMenu());
    }

    //TODO UPDATE
    public static SendMessage receiveLibrarianReturnRequest(long librarianId) {
        return new SendMessage().setChatId(librarianId)
                .setText(String.format(Texts.REQUEST_SENT))
                .setReplyMarkup(GUISystem.simpleMenu());
    }

    //TODO UPDATE
    public static ArrayList<SendMessage> returnDocument(Update update, String id) {
        long userId = update.getCallbackQuery().getMessage().getChatId();
        ObjectId objectId = new ObjectId(id);
        CheckOut checkout = CheckOutDB.getCheckOut(objectId);

        SendMessage msgForLibrarian = receivePatronReturnRequest(LibrarianDB.getLibrarian().getId(), userId, checkout);
        SendMessage msgForPatron = sendPatronReturnRequest(userId);

        ArrayList<SendMessage> msgs = new ArrayList<>();
        msgs.add(msgForPatron);
        msgs.add(msgForLibrarian);
        return msgs;
    }

    //TODO UPDATE
    public static SendMessage receivePatronReturnRequest(long librarianId, long userId, CheckOut checkOut) {
        return new SendMessage().setChatId(librarianId)
                .setText(String.format(Texts.RETURN_REQUESTED_BY_PATRON,
                        PatronDB.getPatron(userId).getFullName(), SuperDatabase.getObject(checkOut.getDocId(), checkOut.getDocCollection()).toString(), checkOut.getToDateLine()))
                .setReplyMarkup(receiveReturnRequestKeyboard(checkOut));
    }

    //TODO UPDATE
    public static InlineKeyboardMarkup receiveReturnRequestKeyboard(CheckOut checkOut) {
        String collection = Constants.CHECKOUT_COLLECTION;
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        rowInline.add(new InlineKeyboardButton().setText(Texts.CONFIRM_RETURN).setCallbackData(Commands.CONFIRM_RETURN + "/=" + collection + "/=" + checkOut.getId()));
        rowsInline.add(rowInline);

        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    //TODO UPDATE
    public static SendMessage sendPatronReturnRequest(long userId) {
        return new SendMessage().setChatId(userId)
                .setText(Texts.RETURN_REQUEST_SENT)
                .setReplyMarkup(GUISystem.simpleMenu());
    }
}
