package updater;

import classes.User.Librarian;
import database.LibrarianDB;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.generics.UpdatesHandler;
import services.BotConfig;

public class MainBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) {
            Librarian librarian = new Librarian(update.getMessage().getChat().getId(), "Temur", "Kholmatov", "tim_1998@bk.ru", "+79631577181", "Innopolis Uni");
            LibrarianDB.insertLibrarian(librarian);
            String msg = "BAD!!!!";
            if(update.getMessage().getChat().getId().equals(LibrarianDB.getLibrarian(librarian.getId()).getId()))
            {
                msg = "GOOOOD!!!!!";
            }
            SendMessage message = new SendMessage().setText(msg).setChatId(update.getMessage().getChatId());
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
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