package updatehandler;

import classes.CheckOut;
import classes.Document.Document;
import database.CheckOutDB;
import database.SuperDatabase;
import org.bson.types.ObjectId;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import services.DateTime;
import services.Constants;
import services.Texts;

/**
 * Class is used to deal with 'Pay for Document' command of the patron.
 */
public class FineSystem {

    public static SendMessage payForDocument(Update update, String id) {
        long userId = update.getCallbackQuery().getMessage().getChatId();
        ObjectId objectId = new ObjectId(id);
        CheckOut checkout = CheckOutDB.getCheckOut(objectId);
        Document doc = (Document) SuperDatabase.getObject(checkout.getDocId(), checkout.getDocCollection());
        int numOfDays = DateTime.daysUntilToday(checkout.getToDate());

        return new SendMessage().setChatId(userId)
                .setText(String.format(Texts.INVOICE_DESCRIPTION, doc.getTitle(), numOfDays, Math.min(doc.getPrice(), Constants.FINE_PER_DAY * numOfDays)))
                .setReplyMarkup(GUISystem.simpleMenu());
    }
}
