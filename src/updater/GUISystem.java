package updater;

import classes.User.Librarian;
import database.LibrarianDB;
import database.PatronDB;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import services.Commands;
import services.Texts;

import java.util.ArrayList;
import java.util.List;

public class GUISystem {

    public static SendMessage initialGreeting(Update update) {
        SendMessage msg = new SendMessage().setChatId(update.getMessage().getChatId())
                .setText(Texts.GREETING_).setReplyMarkup(getInitialMenu());
        return msg;
    }

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

    public static SendMessage documentsView(Update update) {
        SendMessage msg = new SendMessage().setChatId(update.getMessage().getChatId())
                .setText(Texts.VIEW_DOCUMENTS).setReplyMarkup(getDocumentViewMenu());
        return msg;
    }

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

    public static SendMessage personalData(Update update) {
        SendMessage msg = new SendMessage().setChatId(update.getMessage().getChatId());
        if(PatronDB.getPatron(update.getMessage().getChatId()) == null && LibrarianDB.getLibrarian(update.getMessage().getChatId()) == null) {
            msg.setText(Texts.GIVE_PERSONAL_DATA).setReplyMarkup(PersonalDataSystem.inputPersonalDataView());
        }
        else {
            msg.setText(Texts.ALREADY_HAVE_PERSONAL_INFO).setReplyMarkup(simpleMenu());
        }
        return msg;
    }

    public static ReplyKeyboardMarkup simpleMenu() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup().setResizeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(Commands.BACK_TO_MENU);
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    public static SendMessage backToMenu(Update update) {
        SendMessage msg = new SendMessage().setChatId(update.getMessage().getChatId())
                .setText(Texts.MAIN_MENU).setReplyMarkup(getInitialMenu());
        return msg;
    }
}
