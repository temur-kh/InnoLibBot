package updater;

import classes.User.Librarian;
import database.LibrarianDB;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;
import services.BotConfig;
import services.Commands;

import javax.print.Doc;

public class MainBot extends TelegramLongPollingBot {

    private String LOGTAG = "MainBot: ";

    @Override
    public void onUpdateReceived(Update update) {
        try {
            //print user id (user card) to the console
            //System.out.println(update.getMessage().getChatId());

            handleUpdate(update);
        } catch (Exception e) {
            BotLogger.error(LOGTAG, "could not execute command!");
        }
    }

    public void handleUpdate(Update update) {
        SendMessage sendMessage = new SendMessage();
        if (update != null && update.hasMessage()) {
            Message msg = update.getMessage();
            if (msg.hasText()) {
                String text = msg.getText();
                if (text.equals(Commands.START_)) {
                    sendMessage = GUISystem.initialGreetingView(update);
                } else if (text.equals(Commands.VIEW_DOCUMENTS)) {
                    sendMessage = GUISystem.documentsView(update);
                } else if (text.equals(Commands.PERSONAL_INFORMATION)) {
                    sendMessage = GUISystem.personalDataView(update);
                } else if (text.equals(Commands.BACK_TO_MENU)) {
                    sendMessage = GUISystem.backToInitialMenu(update);
                } else if (PersonalDataSystem.belongTo(text)) {
                    //sendMessage = PersonalDataSystem.execute(update);
                } else if (DocumentViewSystem.belongTo(text)) {
                    sendMessage = DocumentViewSystem.execute(update);
                }
            }
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                BotLogger.severe(LOGTAG, "Could not execute message! No method found...\n");
            }
        } else if (update != null && update.hasCallbackQuery()) {
            String callData = update.getCallbackQuery().getData();
            String command = getCallbackQueryKey(callData);
            String collection = getCallbackQueryCollection(callData);
            String value = getCallbackQueryValue(callData);

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