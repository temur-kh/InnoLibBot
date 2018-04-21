package updatehandler;

import classes.Notification;
import classes.User.Patron;
import database.DatabaseManager;
import database.NotificationDB;
import database.PriorityQueueDB;
import org.telegram.telegrambots.api.methods.AnswerPreCheckoutQuery;
import org.telegram.telegrambots.api.methods.send.SendInvoice;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendSticker;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;
import services.BotConfig;
import services.Commands;
import services.Constants;

import java.util.ArrayList;

/**
 * Class for catching updates and handling them.
 */
public class MainBot extends TelegramLongPollingBot {

    private static final String LOGTAG = "MainBot: ";
    private static volatile MainBot instance;

    public static MainBot getInstance() {
        final MainBot currentInstance;
        if (instance == null) {
            synchronized (DatabaseManager.class) {
                if (instance == null) {
                    instance = new MainBot();
                }
                currentInstance = instance;
            }
        } else {
            currentInstance = instance;
        }
        return currentInstance;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            handleUpdate(update);
        } catch (Exception e) {
            BotLogger.severe(LOGTAG, e);
        }
    }

    public void sendNotifications() {
        ArrayList<SendMessage> msgs = new ArrayList<>();
        for (Notification notification : NotificationDB.getExpiredNotifications()) {

            msgs.add(notification.expiredNotificationSendMessage());

            Patron nextPatron = PriorityQueueDB.getNextPatron(notification.getDocId(), true);
            msgs.addAll(NotificationSystem.notifyPatron(nextPatron.getId(), notification.getDocId(), notification.getDocType()));
        }
        executeMessages(msgs);
    }

    private void handleUpdate(Update update) {
        assert update != null;
        SendMessage sendMessage = new SendMessage();
        if (update.hasMessage()) {
            Message msg = update.getMessage();
            String text = msg.getText();
            if (msg.hasText()) {
                if (text.equals(Commands.START_)) {
                    ArrayList<Object> msgs = GUISystem.initialGreetingView(update);
                    executeAll(msgs);
                    return;
                } else if (text.equals(Commands.MENU_) || text.equals(Commands.BACK_TO_MENU)) {
                    sendMessage = GUISystem.backToInitialMenu(update);
                } else if (text.equals(Commands.VIEW_DOCUMENTS)) {
                    sendMessage = GUISystem.documentsView(update);
                } else if (text.equals(Commands.PROFILE)) {
                    sendMessage = GUISystem.profileView(update);
                } else if (text.equals(Commands.PERSONAL_INFORMATION)) {
                    sendMessage = UserProfileSystem.personalDataView(update);
                } else if (DocumentViewSystem.belongTo(text)) {
                    sendMessage = DocumentViewSystem.handle(update);
                } else if (LibrarianSystem.belongTo(text)) {
                    sendMessage = LibrarianSystem.handle(update);
                } else if (PatronSystem.belongTo(text)) {
                    sendMessage = PatronSystem.handle(update);
                } else {
                    sendMessage = SearchSystem.handle(update);
                }
            }
            if (msg.isReply() || UserProfileSystem.belongTo(text) || msg.hasLocation() || msg.getContact() != null) {
                sendMessage = UserProfileSystem.handle(update);
            }
            executeMessage(sendMessage);
        } else if (update.hasCallbackQuery()) {
            String callData = update.getCallbackQuery().getData();
            String command = getCallbackQueryKey(callData);
            String collection = getCallbackQueryCollection(callData);
            String value = getCallbackQueryValue(callData);
            if (collection.equals(Constants.CHECKOUT_COLLECTION) || collection.equals(Constants.OVERDUE_CHECKOUT_COLLECTION)) {

                if (command.equals(Commands.REQUEST_RETURN) || command.equals(Commands.CONFIRM_RETURN)) {
                    ArrayList<SendMessage> msgs = ReturnSystem.handle(update, command, value);
                    executeMessages(msgs);
                } else if (command.equals(Commands.GO_LEFT) || command.equals(Commands.GO_RIGHT)) {
                    Object msg = LibrarianSystem.goToPage(update, Integer.parseInt(value), collection);
                    executeMessage(msg);
                }

            } else if (collection.equals(Constants.MY_CHECKOUT_COLLECTION) || collection.equals(Constants.MY_OVERDUE_CHECKOUT_COLLECTION)) {

                if (command.equals(Commands.RETURN_DOCUMENT)) {
                    ArrayList<SendMessage> msgs = ReturnSystem.returnDocument(update, value);
                    executeMessages(msgs);
                } else if (command.equals(Commands.RENEW_DOCUMENT)) {
                    ArrayList<SendMessage> msgs = RenewSystem.renewDocument(update, value);
                    executeMessages(msgs);
                } else if (command.equals(Commands.PAY_FOR_DOCUMENT)) {
                    Object msg = FineSystem.payForDocument(update, value);
                    executeMessage(msg);
                } else if (command.equals(Commands.GO_LEFT) || command.equals(Commands.GO_RIGHT)) {
                    Object msg = PatronSystem.goToPage(update, Integer.parseInt(value), collection);
                    executeMessage(msg);
                }

            } else {

                Object msg = null;
                if (command.equals(Commands.CHECK_OUT)) {
                    msg = BookingSystem.checkOut(update, value, collection);
                } else if (command.equals(Commands.GO_LEFT) || command.equals(Commands.GO_RIGHT)) {
                    if (update.getCallbackQuery().getMessage().getText().startsWith("Query: "))
                        msg = SearchSystem.goToPage(update, Integer.parseInt(value), collection);
                    else
                        msg = DocumentViewSystem.goToPage(update, Integer.parseInt(value), collection);
                }
                executeMessage(msg);

            }
        } else if (update.hasPreCheckoutQuery()) {
            AnswerPreCheckoutQuery answer = new AnswerPreCheckoutQuery()
                    .setPreCheckoutQueryId(update.getPreCheckoutQuery().getId()).setOk(true);
            try {
                sendApiMethod(answer);
            } catch (TelegramApiException e) {
                BotLogger.severe(LOGTAG, e);
            }
        }
    }

    public void executeMessage(Object msg) {
        if (Constants.BLOCKED_BOT) {
            System.out.println("Attempt to send message to id# " + ((SendMessage) msg).getChatId() + ".\nText of the message: " + ((SendMessage) msg).getText());
            return;
        }
        try {
            if (msg instanceof EditMessageText) {
                execute((EditMessageText) msg);
            } else if (msg instanceof SendSticker) {
                sendSticker((SendSticker) msg);
            } else if (msg instanceof SendInvoice) {
                execute((SendInvoice) msg);
            } else {
                execute((SendMessage) msg);
            }
        } catch (TelegramApiException e) {
            BotLogger.severe(LOGTAG, e);
        }
    }

    public void executeMessages(ArrayList<SendMessage> msgs) {
        for (Object msg : msgs) {
            executeMessage(msg);
        }
    }

    private void executeAll(ArrayList<Object> msgs) {
        for (Object msg : msgs) {
            executeMessage(msg);
        }
    }

    private String getCallbackQueryKey(String callData) {
        return callData.split("/=")[0];
    }

    private String getCallbackQueryCollection(String callData) {
        return callData.split("/=")[1];
    }

    private String getCallbackQueryValue(String callData) {
        return callData.split("/=")[2];
    }

    @Override
    public String getBotUsername() {
        // Return bot username
        return BotConfig.BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        // Return bot token from BotFather
        return BotConfig.BOT_TOKEN;
    }
}