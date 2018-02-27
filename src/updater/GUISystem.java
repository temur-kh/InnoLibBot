package updater;

import classes.User.Librarian;
import database.LibrarianDB;
import database.PatronDB;
import database.RegistrationStateDB;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import services.Commands;
import services.Texts;

import java.util.ArrayList;
import java.util.List;

/**
 * GUI class for initial greeting, Main Menu and etc.
 */
public class GUISystem {

    //return initial greeting for user
    public static SendMessage initialGreetingView(Update update) {
        SendMessage msg = new SendMessage().setChatId(update.getMessage().getChatId())
                .setText(Texts.GREETING_).setReplyMarkup(getInitialMenu());
        return msg;
    }

    //return Main Menu keyboard markup
    private static ReplyKeyboardMarkup getInitialMenu() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup().setResizeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(Commands.VIEW_DOCUMENTS);
        keyboard.add(row);
        row = new KeyboardRow();
        row.add(Commands.PERSONAL_INFORMATION);
        keyboard.add(row);
        row = new KeyboardRow();
        row.add(Commands.LOGIN_AS_LIBRARIAN);
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    //return document view and then switch to DocumentViewSystem
    public static SendMessage documentsView(Update update) {
        SendMessage msg = new SendMessage().setChatId(update.getMessage().getChatId())
                .setText(Texts.VIEW_DOCUMENTS).setReplyMarkup(getDocumentViewMenu());
        return msg;
    }

    //return keyboard markup for document view
    private static ReplyKeyboardMarkup getDocumentViewMenu() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup().setResizeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add(Commands.VIEW_BOOKS);
        keyboard.add(row);

        row = new KeyboardRow();
        row.add(Commands.VIEW_JOURNALS);
        keyboard.add(row);

        row = new KeyboardRow();
        row.add(Commands.VIEW_AVMATERIALS);
        keyboard.add(row);

        row = new KeyboardRow();
        row.add(Commands.BACK_TO_MENU);
        keyboard.add(row);

        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    //return simple menu keyboard markup containing 'Back to Menu' button
    public static ReplyKeyboardMarkup simpleMenu() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup().setResizeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(Commands.BACK_TO_MENU);
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    //go to Main Menu
    public static SendMessage backToInitialMenu(Update update) {
        SendMessage msg = new SendMessage().setChatId(update.getMessage().getChatId())
                .setText(Texts.MAIN_MENU).setReplyMarkup(getInitialMenu());
        return msg;
    }
}
