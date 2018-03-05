package updatehandler;

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

    @Override
    public void onUpdateReceived(Update update) {
        try {
            handleUpdate(update);
        } catch (Exception e) {
            BotLogger.severe(LOGTAG, e);
        }
    }

    public void handleUpdate(Update update) {
        SendMessage sendMessage = new SendMessage();
        if (update != null && update.hasMessage()) {
            Message msg = update.getMessage();
            String text = msg.getText();
            if (msg.hasText()) {
                if (text.equals(Commands.START_)) {
                    ArrayList<Object> msgs = GUISystem.initialGreetingView(update);
                    for (Object message: msgs) {
                        try {
                            if (message instanceof SendMessage)
                                execute((SendMessage) message);
                            else
                                sendSticker((SendSticker) message);
                        } catch (TelegramApiException e) {
                            BotLogger.severe(LOGTAG, sendMessage.toString());
                        }
                    }
                    return;
                } else if(text.equals(Commands.MENU_)) {
                    sendMessage = GUISystem.backToInitialMenu(update);
                } else if (text.equals(Commands.VIEW_DOCUMENTS)) {
                    sendMessage = GUISystem.documentsView(update);
                } else if (text.equals(Commands.PERSONAL_INFORMATION)) {
                    sendMessage = PersonalDataSystem.personalDataView(update);
                } else if (text.equals(Commands.BACK_TO_MENU)) {
                    sendMessage = GUISystem.backToInitialMenu(update);
                } else if (DocumentViewSystem.belongTo(text)) {
                    sendMessage = DocumentViewSystem.handle(update);
                } else if (LibrarianSystem.belongTo(text)) {
                    sendMessage = LibrarianSystem.handle(update);
                }
            }
            if (msg.isReply() || PersonalDataSystem.belongTo(text) || msg.hasLocation() || msg.getContact() != null) {
                sendMessage = PersonalDataSystem.handle(update);
            }
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                BotLogger.severe(LOGTAG, e);
            }
        } else if (update != null && update.hasCallbackQuery()) {
            String callData = update.getCallbackQuery().getData();
            String command = getCallbackQueryKey(callData);
            String collection = getCallbackQueryCollection(callData);
            String value = getCallbackQueryValue(callData);
            if (collection.equals(Constants.CHECKOUT_COLLECTION)) {
                if (command.equals(Commands.REQUEST_RETURN) || command.equals(Commands.CONFIRM_RETURN)) {
                    ArrayList<SendMessage> msgs = ReturnSystem.handle(update, command, value);
                    try {
                        for (SendMessage msg: msgs) {
                            execute(msg);
                        }
                    } catch (TelegramApiException e) {
                        BotLogger.severe(LOGTAG, "Could not execute callback query! No method found...\n");
                    }
                } else if (command.equals(Commands.GO_LEFT) || command.equals(Commands.GO_RIGHT)) {
                    Object msg = LibrarianSystem.goToPage(update, Integer.parseInt(value), collection);
                    try {
                        if (msg instanceof EditMessageText) {
                            execute((EditMessageText) msg);
                        } else {
                            execute((SendMessage) msg);
                        }
                    } catch (TelegramApiException e) {
                        BotLogger.severe(LOGTAG, e);
                    }
                } else if(command.equals(Commands.BACK_TO_MENU)) {
                    SendMessage msg = GUISystem.backToInitialMenu(update);
                    try {
                        execute(msg);
                    } catch (TelegramApiException e) {
                        BotLogger.severe(LOGTAG, e);
                    }
                }
            } else {
                if (command.equals(Commands.CHECK_OUT)) {
                    SendMessage msg = BookingSystem.checkOut(update, value, collection);
                    try {
                        execute(msg);
                    } catch (TelegramApiException e) {
                        BotLogger.severe(LOGTAG, "Could not execute callback query! No method found...\n");
                    }
                } else if (command.equals(Commands.GO_LEFT) || command.equals(Commands.GO_RIGHT)) {
                    EditMessageText msg = DocumentViewSystem.goToPage(update, Integer.parseInt(value), collection);
                    try {
                        execute(msg);
                    } catch (TelegramApiException e) {
                        BotLogger.severe(LOGTAG, e);
                    }
                } else if(command.equals(Commands.BACK_TO_MENU)) {
                    SendMessage msg = GUISystem.backToInitialMenu(update);
                    try {
                        execute(msg);
                    } catch (TelegramApiException e) {
                        BotLogger.severe(LOGTAG, e);
                    }
                }
            }
        }
    }

    public String getCallbackQueryKey(String callData) {
        return callData.split("/=")[0];
    }

    public String getCallbackQueryCollection(String callData) {
        return callData.split("/=")[1];
    }

    public String getCallbackQueryValue(String callData) {
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