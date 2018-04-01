package updatehandler;

import classes.CheckOut;
import database.CheckOutDB;
import database.LibrarianDB;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import services.Commands;
import services.Constants;
import services.Texts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for librarian's managing system (only for librarian)
 */
public class LibrarianSystem {
    private static final String LOGTAG = "Librarian System: ";

    //commands list belonging to this class
    public static final ArrayList<String> commandsList = new ArrayList<>(
            Arrays.asList(Commands.LOGIN_AS_LIBRARIAN, Commands.CHECKOUTS_LIST));

    //does command belong to this class commands?
    public static boolean belongTo(String command) {
        for (String line : commandsList) {
            if (line.equals(command))
                return true;
        }
        return false;
    }

    public static ReplyKeyboardMarkup librarianMenu() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup().setResizeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(Commands.CHECKOUTS_LIST);
        keyboard.add(row);
        row = new KeyboardRow();
        row.add(Commands.BACK_TO_MENU);
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    public static SendMessage handle(Update update) {
        long userId = update.getMessage().getChatId();
        SendMessage msg = new SendMessage().setChatId(userId);
        if (LibrarianDB.getLibrarian(userId) == null) {
            msg.setText(Texts.DO_NOT_HAVE_ACCESS).setReplyMarkup(GUISystem.simpleMenu());
            return msg;
        }
        String text = update.getMessage().getText();
        if (text.equals(Commands.LOGIN_AS_LIBRARIAN)) {
            msg.setText(Texts.LIBRARIAN_SYSTEM);
            msg.setReplyMarkup(librarianMenu());
        } else if (text.equals(Commands.CHECKOUTS_LIST)) {
            ArrayList<CheckOut> checkOuts = CheckOutDB.getCheckOutsList();
            if (!checkOuts.isEmpty()) {
                CheckOut firstCheckOut = checkOuts.get(0);
                msg.setText(firstCheckOut.getInfo()).setParseMode("markdown");
                msg.setReplyMarkup(getCheckOutInlineKeyboard(firstCheckOut, 0, checkOuts.size()));
            } else {
                msg.setText(Texts.NO_CHECKOUTS).setReplyMarkup(GUISystem.simpleMenu());
            }
        }
        return msg;
    }

    //update the page-message: go to the next checkout.
    public static Object goToPage(Update update, int index, String collection) {
        EditMessageText msg = new EditMessageText().setChatId(update.getCallbackQuery().getMessage().getChatId())
                .setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        ArrayList<CheckOut> checkOuts = CheckOutDB.getCheckOutsList();
        if (!checkOuts.isEmpty()) {

            //check index bound
            if (index < 0) {
                index = 0;
            } else if (index >= checkOuts.size()) {
                index = checkOuts.size() - 1;
            }
            CheckOut doc = checkOuts.get(index);
            msg.setText(doc.getInfo()).setParseMode("markdown");
            msg.setReplyMarkup(getCheckOutInlineKeyboard(doc, index, checkOuts.size()));
        } else {
            SendMessage sendMessage = new SendMessage().setChatId(update.getCallbackQuery().getMessage().getChatId())
                    .setText(Texts.NO_CHECKOUTS).setReplyMarkup(GUISystem.simpleMenu());
            return sendMessage;
        }
        return msg;
    }

    public static InlineKeyboardMarkup getCheckOutInlineKeyboard(CheckOut checkOut, int index, int size) {

        String collection = Constants.CHECKOUT_COLLECTION;
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        //add scrollers
        rowInline.add(new InlineKeyboardButton().setText(Texts.GO_LEFT).setCallbackData(Commands.GO_LEFT + "/=" + collection + "/=" + Integer.toString(index - 1)));
        rowInline.add(new InlineKeyboardButton().setText(Integer.toString(index + 1) + "/" + Integer.toString(size)).setCallbackData(Commands.GO_LEFT + "/=" + collection + "/=" + Integer.toString(index)));
        rowInline.add(new InlineKeyboardButton().setText(Texts.GO_RIGHT).setCallbackData(Commands.GO_RIGHT + "/=" + collection + "/=" + Integer.toString(index + 1)));
        rowsInline.add(rowInline);

        //add 'Request return' and 'Confirm return' buttons
        rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText(Texts.REQUEST_RETURN).setCallbackData(Commands.REQUEST_RETURN + "/=" + collection + "/=" + checkOut.getId()));
        rowsInline.add(rowInline);
        rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText(Texts.CONFIRM_RETURN).setCallbackData(Commands.CONFIRM_RETURN + "/=" + collection + "/=" + checkOut.getId()));
        rowsInline.add(rowInline);

        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }
}
