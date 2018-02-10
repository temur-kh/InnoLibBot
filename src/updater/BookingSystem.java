package updater;

import classes.CheckOut;
import classes.Document.Book;
import classes.User.Patron;
import database.BookDB;
import database.PatronDB;
import org.bson.types.ObjectId;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import services.Constants;
import services.Texts;

/**
 * Class of booking system.
 */
public class BookingSystem {

    //check out a document if possible
    //not possible if did not provide personal data or no copies of document are available.
    public static SendMessage checkOut(Update update, String id, String collection) {
        long userId = update.getCallbackQuery().getMessage().getChatId();
        SendMessage msg = new SendMessage().setChatId(userId);

        ObjectId objectId = new ObjectId(id);
        if (collection.equals(Constants.BOOK_COLLECTION)) {

            Book book = BookDB.getBook(objectId);
            Patron patron = PatronDB.getPatron(userId);

            if (patron == null) {
                msg.setText(Texts.DID_NOT_PROVIDE_PERSONAL_DATA);
            } else if (book.hasFreeCopies()) {
                CheckOut checkOut = patron.checkOutDocument(book);
                msg.setText(String.format(Texts.CHECKED_OUT_DOCUMENT_FORMAT, book.getTitle(), checkOut.getToDate().getTime()));
            } else {
                msg.setText(Texts.NO_COPIES_AVAILABLE);
            }
        }
        return msg;
    }
}