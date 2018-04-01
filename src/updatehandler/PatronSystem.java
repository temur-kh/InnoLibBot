package updatehandler;

import classes.CheckOut;
import database.CheckOutDB;
import database.LibrarianDB;
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

public class PatronSystem {
    private static final String LOGTAG = "Patron System: ";

    //commands list belonging to this class
    private static final ArrayList<String> commandsList = new ArrayList<>(
            Arrays.asList(Commands.MY_CHECKOUTS, Commands.MY_OVERDUE_CHECKOUTS));

    //does command belong to this class commands?
    public static boolean belongTo(String command) {
        for (String line : commandsList) {
            if (line.equals(command))
                return true;
        }
        return false;
    }

    public static SendMessage handle(Update update) {
        long userId = update.getMessage().getChatId();
        SendMessage msg = new SendMessage().setChatId(userId);

        if (LibrarianDB.getLibrarian(userId) != null) {
            msg.setText(Texts.LIBRARIAN_CANNOT_HAVE_CHECKOUTS).setReplyMarkup(GUISystem.simpleMenu());
            return msg;
        }

        String text = update.getMessage().getText();
        ArrayList<CheckOut> checkOuts;
        String collection;
        switch (text) {
            case Commands.MY_CHECKOUTS:
                collection = Constants.MY_CHECKOUT_COLLECTION;
                checkOuts = CheckOutDB.getCheckOutsListByUserId(userId);
                break;
            case Commands.MY_OVERDUE_CHECKOUTS:
                collection = Constants.MY_OVERDUE_CHECKOUT_COLLECTION;
                checkOuts = CheckOutDB.getOverdueCheckOutsListByUserId(userId);
                break;
            default:
                msg.setText("ERROR!").setReplyMarkup(GUISystem.simpleMenu());
                return msg;
        }

        if (!checkOuts.isEmpty()) {
            CheckOut firstCheckOut = checkOuts.get(0);
            msg.setText(firstCheckOut.getInfoForPatron()).setParseMode("markdown");
            msg.setReplyMarkup(getMyCheckOutInlineKeyboard(firstCheckOut, 0, checkOuts.size(), collection));
        } else {
            msg.setText(Texts.NO_CHECKOUTS).setReplyMarkup(GUISystem.simpleMenu());
        }
        return msg;
    }

    //TODO UPDATE
    public static Object goToPage(Update update, int index, String collection) {
        long userId = update.getCallbackQuery().getMessage().getChatId();
        EditMessageText msg = new EditMessageText().setChatId(userId)
                .setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        ArrayList<CheckOut> checkOuts = new ArrayList<>();
        if (collection.equals(Constants.MY_CHECKOUT_COLLECTION)) {
            checkOuts = CheckOutDB.getCheckOutsListByUserId(userId);
        } else if (collection.equals(Constants.MY_OVERDUE_CHECKOUT_COLLECTION)) {
            checkOuts = CheckOutDB.getOverdueCheckOutsListByUserId(userId);
        }
        if (!checkOuts.isEmpty()) {

            //check index bound
            if (index < 0) {
                index = 0;
            } else if (index >= checkOuts.size()) {
                index = checkOuts.size() - 1;
            }
            CheckOut doc = checkOuts.get(index);
            msg.setText(doc.getInfoForPatron()).setParseMode("markdown");
            msg.setReplyMarkup(getMyCheckOutInlineKeyboard(doc, index, checkOuts.size(), collection));
        } else {
            return new SendMessage().setChatId(userId)
                    .setText(Texts.NO_CHECKOUTS).setReplyMarkup(GUISystem.simpleMenu());
        }
        return msg;
    }

    //TODO UPDATE
    private static InlineKeyboardMarkup getMyCheckOutInlineKeyboard(CheckOut checkOut, int index, int size, String collection) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        //add scrollers
        rowInline.add(new InlineKeyboardButton().setText(Texts.GO_LEFT).setCallbackData(Commands.GO_LEFT + "/=" + collection + "/=" + Integer.toString(index - 1)));
        rowInline.add(new InlineKeyboardButton().setText(Integer.toString(index + 1) + "/" + Integer.toString(size)).setCallbackData(Commands.GO_LEFT + "/=" + collection + "/=" + Integer.toString(index)));
        rowInline.add(new InlineKeyboardButton().setText(Texts.GO_RIGHT).setCallbackData(Commands.GO_RIGHT + "/=" + collection + "/=" + Integer.toString(index + 1)));
        rowsInline.add(rowInline);

        //add 'Return document' and 'Renew document' buttons / add 'Pay for document' button
        if (collection.equals(Constants.MY_CHECKOUT_COLLECTION)) {
            rowInline = new ArrayList<>();
            rowInline.add(new InlineKeyboardButton().setText(Texts.RETURN_DOCUMENT).setCallbackData(Commands.RETURN_DOCUMENT + "/=" + collection + "/=" + checkOut.getId()));
            rowsInline.add(rowInline);

            rowInline = new ArrayList<>();
            rowInline.add(new InlineKeyboardButton().setText(Texts.RENEW_DOCUMENT).setCallbackData(Commands.RENEW_DOCUMENT + "/=" + collection + "/=" + checkOut.getId()));
            rowsInline.add(rowInline);
        } else if (collection.equals(Constants.MY_OVERDUE_CHECKOUT_COLLECTION)){
            rowInline = new ArrayList<>();
            rowInline.add(new InlineKeyboardButton().setText(Texts.PAY_FOR_DOCUMENT).setCallbackData(Commands.PAY_FOR_DOCUMENT + "/=" + collection + "/=" + checkOut.getId()));
            rowsInline.add(rowInline);
        }

        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

}
