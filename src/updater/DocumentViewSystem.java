package updater;

import classes.Document.Book;
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

public class DocumentViewSystem {
    public static ArrayList<String> commandsList = new ArrayList<String>(
            Arrays.asList(Commands.VIEW_BOOKS,Commands.VIEW_JOURNALS,Commands.VIEW_AVMATERIALS));

    public static boolean belongTo(String command) {
        for(String line: commandsList) {
            if(line.equals(command))
                return true;
        }
        return false;
    }

    public static SendMessage execute(Update update) {
        SendMessage msg = new SendMessage().setChatId(update.getMessage().getChatId());
        String text = update.getMessage().getText();
        if(text.equals(Commands.VIEW_BOOKS)) {
            ArrayList<Book> books = BookDB.getBooksList();
            if(!books.isEmpty()) {
                Book firstBook = books.get(0);
                msg.setText(firstBook.getUrl());
                msg.setReplyMarkup(getBookInlineKeyboard(firstBook, 0, books.size()));
            }
            else {
                msg.setText("No books found!").setReplyMarkup(GUISystem.simpleMenu());
            }
        }
        return msg;
    }

    public static EditMessageText goToPage(Update update, int index, String collection) {
        EditMessageText msg = new EditMessageText().setChatId(update.getCallbackQuery().getMessage().getChatId())
                .setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        if(collection.equals(Constants.BOOK_COLLECTION)) {
            ArrayList<Book> books = BookDB.getBooksList();
            Book book;
            if(!books.isEmpty()) {
                if(index < 0) {
                    index = 0;
                }
                else if(index >= books.size()) {
                    index = books.size()-1;
                }
                book = books.get(index);
                msg.setText(book.getUrl());
                msg.setReplyMarkup(getBookInlineKeyboard(book, index, books.size()));
            }
        }
        return msg;
    }

    public static InlineKeyboardMarkup getBookInlineKeyboard(Book book, int index, int size) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        rowInline.add(new InlineKeyboardButton().setText(Texts.CHECK_OUT).setCallbackData(Commands.CHECK_OUT + "/=" + Constants.BOOK_COLLECTION +  "/=" + book.getId()));
        rowsInline.add(rowInline);

        rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText(Texts.GO_LEFT).setCallbackData(Commands.GO_LEFT + "/=" + Constants.BOOK_COLLECTION + "/=" + Integer.toString(index - 1)));
        rowInline.add(new InlineKeyboardButton().setText(Integer.toString(index) + "/" + Integer.toString(size)).setCallbackData("index?="+Integer.toString(index)));
        rowInline.add(new InlineKeyboardButton().setText(Texts.GO_RIGHT).setCallbackData(Commands.GO_RIGHT + "/=" + Constants.BOOK_COLLECTION + "/=" + Integer.toString(index + 1)));
        rowsInline.add(rowInline);

        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

}
