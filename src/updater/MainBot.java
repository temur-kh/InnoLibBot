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
            handleUpdate(update);
        } catch (Exception e) {
            BotLogger.error(LOGTAG, e);
        }
    }

    public void handleUpdate(Update update) {
        SendMessage sendMessage = new SendMessage();
        if(update!=null && update.hasMessage()) {
            Message msg = update.getMessage();
            if(msg.hasText()) {
                String text = msg.getText();
                if(text.equals(Commands.START_)) {
                    sendMessage = GUISystem.initialGreeting(update);
                }
                else if(text.equals(Commands.VIEW_DOCUMENTS)) {
                    sendMessage = GUISystem.documentsView(update);
                }
                else if(text.equals(Commands.PERSONAL_INFORMATION)) {
                    sendMessage = GUISystem.personalData(update);
                }
                else if(text.equals(Commands.BACK_TO_MENU)) {
                    sendMessage = GUISystem.backToMenu(update);
                }
                else if(PersonalDataSystem.belongTo(text)) {
                    //sendMessage = PersonalDataSystem.execute(update);
                }
                else if(DocumentViewSystem.belongTo(text)) {
                    sendMessage = DocumentViewSystem.execute(update);
                }
            }
        }
        else if(update!=null && update.hasCallbackQuery()) {
            String callData = update.getCallbackQuery().getData();
            String command = getCallbackQueryKey(callData);
            String collection = getCallbackQueryCollection(callData);
            String value = getCallbackQueryValue(callData);
            EditMessageText msg = new EditMessageText();
            if (command.equals(Commands.CHECK_OUT)) {

            } else if (command.equals(Commands.GO_LEFT) || command.equals(Commands.GO_RIGHT)) {
                msg = DocumentViewSystem.goToPage(update, Integer.parseInt(value), collection);
            }
        }
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            BotLogger.severe(LOGTAG, e);
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