package updatehandler;

import classes.CheckOut;
import classes.Document.Document;
import classes.User.Patron;
import classes.User.Status;
import database.CheckOutDB;
import database.LibrarianDB;
import database.PatronDB;
import database.SuperDatabase;
import org.bson.types.ObjectId;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import services.DateTime;
import services.Commands;
import services.Constants;
import services.Texts;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RenewSystem {

    //TODO UPDATE
    public static ArrayList<SendMessage> renewDocument(Update update, String id) {
        long userId = update.getCallbackQuery().getMessage().getChatId();
        ObjectId objectId = new ObjectId(id);
        CheckOut checkOut = CheckOutDB.getCheckOut(objectId);
        ArrayList<SendMessage> msgs = new ArrayList<>();

        if (handle(checkOut)) {
            SendMessage msgForLibrarian = new SendMessage().setChatId(LibrarianDB.getLibrarian().getId())
                    .setText(String.format(Texts.PATRON_RENEWED_DOCUMENT, userId, SuperDatabase.getObject(checkOut.getDocId(), checkOut.getDocCollection()).toString()))
                    .setReplyMarkup(requestReturnKeyboard(checkOut));
            SendMessage msgForPatron = new SendMessage().setChatId(userId).setText(Texts.RENEWED_DOCUMENT).setReplyMarkup(GUISystem.simpleMenu());
            msgs.add(msgForPatron);
            msgs.add(msgForLibrarian);
        } else {
            SendMessage msg = new SendMessage().setChatId(userId).setText(Texts.COULD_NOT_RENEW).setReplyMarkup(GUISystem.simpleMenu());
            msgs.add(msg);
        }

        return msgs;
    }

    public static boolean handle(CheckOut checkOut) {
        Calendar deadline = checkOut.getToDate();
        String collection = checkOut.getDocCollection();
        Document document = (Document) SuperDatabase.getObject(checkOut.getDocId(), checkOut.getDocCollection());
        Patron patron = PatronDB.getPatron(checkOut.getPatronId());
        Calendar today = DateTime.todayCalendar();

        if ((checkOut.isRenewed() && patron.getStatus() != Status.VisitingProfessor) || deadline.before(today))
            return false;

        deadline.add(Calendar.DAY_OF_MONTH, patron.getCheckOutTime(document, collection));
        checkOut.setToDate(deadline);
        checkOut.setRenewed(true);
        CheckOutDB.updateCheckOut(checkOut);
        return true;
    }

    //TODO UPDATE
    private static InlineKeyboardMarkup requestReturnKeyboard(CheckOut checkOut) {
        String collection = Constants.CHECKOUT_COLLECTION;
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        rowInline.add(new InlineKeyboardButton().setText(Texts.REQUEST_RETURN).setCallbackData(Commands.REQUEST_RETURN + "/=" + collection + "/=" + checkOut.getId()));
        rowsInline.add(rowInline);

        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }
}
