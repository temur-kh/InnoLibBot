package updatehandler;

import classes.Document.AVMaterial;
import classes.Document.Document;
import classes.Document.Journal;
import database.AVMaterialDB;
import database.BookDB;
import database.JournalDB;
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
import java.util.List;

public class SearchSystem {

    private static final String LOGTAG = "SearchSystem: ";

    public static SendMessage query(Update update) {
        return new SendMessage().setChatId(update.getMessage().getChatId())
                .setText(Texts.INPUT_SEARCHING_QUERY).setReplyMarkup(searchingMarkup());
    }

    public static ReplyKeyboardMarkup searchingMarkup() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup().setResizeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add(Commands.SEARCH_DOCUMENT);
        keyboard.add(row);

        row = new KeyboardRow();
        row.add(Commands.BACK_TO_MENU);
        keyboard.add(row);

        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    //execute a page-message to go through list of documents
    public static SendMessage handle(Update update) {
        SendMessage msg = new SendMessage().setChatId(update.getMessage().getChatId());
        String text = update.getMessage().getText();
        ArrayList<Document> docs = getListByQuery(text);
        if (!docs.isEmpty()) {
            Document firstDoc = docs.get(0);
            msg.setText(getText(text) + firstDoc.getUrl());
            msg.setReplyMarkup(getInlineKeyboard(firstDoc, 0, docs.size(), getCollection(docs.get(0))));
        } else {
            msg.setText("Could not find anything :(").setReplyMarkup(GUISystem.simpleMenu());
        }
        return msg;
    }

    //update the page-message: go to the next document.
    public static EditMessageText goToPage(Update update, int index, String collection) {
        EditMessageText msg = new EditMessageText().setChatId(update.getCallbackQuery().getMessage().getChatId())
                .setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        String query = getQuery(update.getCallbackQuery().getMessage().getText());
        ArrayList<Document> docs = getListByQuery(query);
        if (!docs.isEmpty()) {
            //check index bound
            if (index < 0) {
                index = 0;
            } else if (index >= docs.size()) {
                index = docs.size() - 1;
            }
            Document doc = docs.get(index);
            msg.setText(getText(query) + doc.getUrl());
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

    public static String getText(String query) {
        return "Query: " + query + " \n";
    }

    public static String getQuery(String string) {
        return string.split(" ")[1];
    }

    public static ArrayList<Document> getListByQuery(String query) {
        ArrayList<Document> docs = new ArrayList<>();
        for (Document doc : BookDB.getBooksList())
            if (test(doc, query))
                docs.add(doc);
        for (Document doc : JournalDB.getJournalsList())
            if (test(doc, query))
                docs.add(doc);
        for (Document doc : AVMaterialDB.getAVMaterialsList())
            if (test(doc, query))
                docs.add(doc);
        return docs;
    }

    public static String getCollection(Document doc) {
        if (doc instanceof Journal)
            return Constants.JOURNAL_COLLECTION;
        else if (doc instanceof AVMaterial)
            return Constants.AVMATERIAL_COLLECTION;
        else
            return Constants.BOOK_COLLECTION;
    }

    public static boolean test(Document document, String line) {
        for (String string : line.split(" ")) {
            if (check(document, string))
                return true;
        }
        return false;
    }

    public static boolean check(Document document, String string) {
        string = clear(string);
        return document.getTitle().toLowerCase().contains(string)
                || document.getAuthorsLine().toLowerCase().contains(string)
                || document.getKeywordsLine().toLowerCase().contains(string);
    }

    private static String clear(String string) {
        int start = 0, end = string.length() ;
        for (int i=0;i!=string.length();i++) {
            if (Character.isLetterOrDigit(string.charAt(i))) {
                start = i;
                break;
            }
        }
        for (int i=string.length()-1;i>=0;i--) {
            if (Character.isLetterOrDigit(string.charAt(i))) {
                end = i;
                break;
            }
        }
        return string.substring(start, end + 1).toLowerCase();
    }
}
