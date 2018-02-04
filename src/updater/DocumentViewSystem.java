package updater;

import classes.Document.Book;
import classes.Document.Document;
import database.BookDB;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import services.Commands;
import services.Constants;
import services.Texts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class contains methods for documents view GUI.
 */
public class DocumentViewSystem {

    //commands list belonging to this class
    public static ArrayList<String> commandsList = new ArrayList<String>(
            Arrays.asList(Commands.VIEW_BOOKS, Commands.VIEW_JOURNALS, Commands.VIEW_AVMATERIALS));

    //does command belong to this class commands?
    public static boolean belongTo(String command) {
        for (String line : commandsList) {
            if (line.equals(command))
                return true;
        }
        return false;
    }

    //execute a page-message to go through list of documents
    public static SendMessage execute(Update update) {
        SendMessage msg = new SendMessage().setChatId(update.getMessage().getChatId());
        String text = update.getMessage().getText();
        if (text.equals(Commands.VIEW_BOOKS)) {
            ArrayList<Book> books = BookDB.getBooksList();
            if (!books.isEmpty()) {
                Book firstBook = books.get(0);
                msg.setText(firstBook.getUrl());
                msg.setReplyMarkup(getInlineKeyboard(firstBook, 0, books.size()));
            } else {
                msg.setText("No books found!").setReplyMarkup(GUISystem.simpleMenu());
            }
        }
        return msg;
    }

    //update the page-message: go to the next document.
    public static EditMessageText goToPage(Update update, int index, String collection) {
        EditMessageText msg = new EditMessageText().setChatId(update.getCallbackQuery().getMessage().getChatId())
                .setMessageId(update.getCallbackQuery().getMessage().getMessageId());

        if (collection.equals(Constants.BOOK_COLLECTION)) {
            ArrayList<Book> books = BookDB.getBooksList();
            Book book;
            if (!books.isEmpty()) {

                //check index bound
                if (index < 0) {
                    index = 0;
                } else if (index >= books.size()) {
                    index = books.size() - 1;
                }

                book = books.get(index);
                msg.setText(book.getUrl());
                msg.setReplyMarkup(getInlineKeyboard(book, index, books.size()));
            }
        }
        return msg;
    }

    //return inline keyboard markup for page-message.
    public static InlineKeyboardMarkup getInlineKeyboard(Document doc, int index, int size) {

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        //add 'Check out' button
        rowInline.add(new InlineKeyboardButton().setText(Texts.CHECK_OUT).setCallbackData(Commands.CHECK_OUT + "/=" + Constants.BOOK_COLLECTION + "/=" + doc.getId()));
        rowsInline.add(rowInline);

        //add scrollers
        rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText(Texts.GO_LEFT).setCallbackData(Commands.GO_LEFT + "/=" + Constants.BOOK_COLLECTION + "/=" + Integer.toString(index - 1)));
        rowInline.add(new InlineKeyboardButton().setText(Integer.toString(index + 1) + "/" + Integer.toString(size)).setCallbackData("index?=" + Integer.toString(index)));
        rowInline.add(new InlineKeyboardButton().setText(Texts.GO_RIGHT).setCallbackData(Commands.GO_RIGHT + "/=" + Constants.BOOK_COLLECTION + "/=" + Integer.toString(index + 1)));
        rowsInline.add(rowInline);

        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

}
