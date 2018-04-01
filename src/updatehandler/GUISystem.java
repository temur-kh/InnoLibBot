package updatehandler;

import database.LibrarianDB;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendSticker;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import services.Commands;
import services.Constants;
import services.Texts;

import java.util.ArrayList;
import java.util.List;

/**
 * GUI class for initial greeting, Main Menu and etc.
 */
public class GUISystem {

    //return initial greeting for user
    public static ArrayList<Object> initialGreetingView(Update update) {
        ArrayList<Object> msgs = new ArrayList<>();
        msgs.add(new SendSticker().setChatId(update.getMessage().getChatId()).setSticker("CAADAgADOAADf72DDRsfv6O0SoOxAg")); //CAADAgADOAADf72DDRsfv6O0SoOxAg - simple sticker CAADAgADJwADyIsGAAH6wu4EMqyrvgI - meyer
        msgs.add(new SendMessage().setChatId(update.getMessage().getChatId())
                .setText(Texts.GREETING_).setReplyMarkup(getInitialMenu(update.getMessage().getChatId())));
        return msgs;
    }

    //return Main Menu keyboard markup
    private static ReplyKeyboardMarkup getInitialMenu(long userId) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup().setResizeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(Commands.VIEW_DOCUMENTS);
        keyboard.add(row);
        row = new KeyboardRow();
        row.add(Commands.PROFILE);
        keyboard.add(row);
        if (LibrarianDB.getLibrarian(userId) != null) {
            row = new KeyboardRow();
            row.add(Commands.LOGIN_AS_LIBRARIAN);
            keyboard.add(row);
        }
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

    public static SendMessage profileView(Update update) {
        SendMessage msg = new SendMessage().setChatId(update.getMessage().getChatId())
                .setText(Texts.VIEW_PROFILE).setReplyMarkup(getProfileViewMenu());
        return msg;
    }

    private static ReplyKeyboardMarkup getProfileViewMenu() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup().setResizeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add(Commands.MY_CHECKOUTS);
        keyboard.add(row);

        row = new KeyboardRow();
        row.add(Commands.MY_OVERDUE_CHECKOUTS);
        keyboard.add(row);

        row = new KeyboardRow();
        row.add(Commands.PERSONAL_INFORMATION);
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

    public static InlineKeyboardMarkup simpleInlineMenu() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        rowInline.add(new InlineKeyboardButton(Commands.BACK_TO_MENU).setCallbackData(Constants.EMPTY_LINE));
        rowsInline.add(rowInline);
        keyboardMarkup.setKeyboard(rowsInline);
        return keyboardMarkup;
    }

    //go to Main Menu
    public static SendMessage backToInitialMenu(Update update) {
        SendMessage msg = new SendMessage().setChatId(update.getMessage().getChatId())
                .setText(Texts.MAIN_MENU).setReplyMarkup(getInitialMenu(update.getMessage().getChatId()));
        return msg;
    }
}
