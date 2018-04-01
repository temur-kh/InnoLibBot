package updatehandler;

import classes.Document.Document;
import database.SuperDatabase;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.logging.BotLogger;
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

    private static final String LOGTAG = "Document View System: ";

    //commands list belonging to this class
    public static final ArrayList<String> commandsList = new ArrayList<>(
            Arrays.asList(Commands.VIEW_BOOKS, Commands.VIEW_JOURNALS, Commands.VIEW_AVMATERIALS));

    //does command belong to this class commands?
    public static boolean belongTo(String command) {
        for (String line : commandsList) {
            if (line.equals(command))
                return true;
        }
        return false;
    }

    private static ArrayList<Document> convertToDocuments(ArrayList<Object> objects) {
        ArrayList<Document> docs = new ArrayList<>();
        for (Object object : objects) {
            docs.add((Document) object);
        }
        return docs;
    }

    //execute a page-message to go through list of documents
    public static SendMessage handle(Update update) {
        SendMessage msg = new SendMessage().setChatId(update.getMessage().getChatId());
        String text = update.getMessage().getText();
        String collection = "";
        switch (text) {
            case Commands.VIEW_BOOKS:
                collection = Constants.BOOK_COLLECTION;
                break;
            case Commands.VIEW_AVMATERIALS:
                collection = Constants.AVMATERIAL_COLLECTION;
                break;
            case Commands.VIEW_JOURNALS:
                collection = Constants.JOURNAL_COLLECTION;
                break;
            default:
                BotLogger.severe(LOGTAG, "could not find the collection!");
        }
        ArrayList<Document> docs = convertToDocuments(SuperDatabase.getObjectsList(collection));
        if (!docs.isEmpty()) {
            Document firstDoc = docs.get(0);
            msg.setText(firstDoc.getUrl());
            msg.setReplyMarkup(getInlineKeyboard(firstDoc, 0, docs.size(), collection));
        } else {
            msg.setText("No documents found!").setReplyMarkup(GUISystem.simpleMenu());
        }
        return msg;
    }

    //update the page-message: go to the next document.
    public static EditMessageText goToPage(Update update, int index, String collection) {
        EditMessageText msg = new EditMessageText().setChatId(update.getCallbackQuery().getMessage().getChatId())
                .setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        ArrayList<Document> docs = convertToDocuments(SuperDatabase.getObjectsList(collection));
        if (!docs.isEmpty()) {

            //check index bound
            if (index < 0) {
                index = 0;
            } else if (index >= docs.size()) {
                index = docs.size() - 1;
            }
            Document doc = docs.get(index);
            msg.setText(doc.getUrl());
            msg.setReplyMarkup(getInlineKeyboard(doc, index, docs.size(), collection));
        }
        return msg;
    }

    //return inline keyboard markup for page-message.
    public static InlineKeyboardMarkup getInlineKeyboard(Document doc, int index, int size, String collection) {

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        //add 'Check out' button
        rowInline.add(new InlineKeyboardButton().setText(Texts.CHECK_OUT).setCallbackData(Commands.CHECK_OUT + "/=" + collection + "/=" + doc.getId()));
        rowsInline.add(rowInline);

        //add scrollers
        rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText(Texts.GO_LEFT).setCallbackData(Commands.GO_LEFT + "/=" + collection + "/=" + Integer.toString(index - 1)));
        rowInline.add(new InlineKeyboardButton().setText(Integer.toString(index + 1) + "/" + Integer.toString(size)).setCallbackData("index?=" + Integer.toString(index)));
        rowInline.add(new InlineKeyboardButton().setText(Texts.GO_RIGHT).setCallbackData(Commands.GO_RIGHT + "/=" + collection + "/=" + Integer.toString(index + 1)));
        rowsInline.add(rowInline);

        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }
}
