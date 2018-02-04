import classes.Document.Book;
import database.BookDB;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;
import org.telegram.telegrambots.logging.BotsFileHandler;
import org.telegram.telegraph.*;
import updater.MainBot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

public class Main {

    private static String LOGTAG = "Class Main: ";

    public static void main(String[] args) {

        BotLogger.setLevel(Level.ALL);
        BotLogger.registerLogger(new ConsoleHandler());
        try {
            BotLogger.registerLogger(new BotsFileHandler());
        } catch (IOException e) {
            String logInfo = "BotLogger IOException";
            BotLogger.severe(LOGTAG + logInfo, e);
        }

        // Initialize context
        TelegraphContextInitializer.init();
        TelegraphContext.registerInstance(ExecutorOptions.class, new ExecutorOptions());

        // Initialize Api Context
        ApiContextInitializer.init();

        ArrayList<String> authors = new ArrayList<>(Arrays.asList("William L. Briggs", "Lyle Cochran", "Bernard Gillett"));
        ArrayList<String> keywords = new ArrayList<>(Arrays.asList("calculus", "William", "Briggs", "Bernard"));
        Book book = new Book("Calculus", "1st Edition", authors, "https://www.pearsonhighered.com/assets/bigcovers/0/3/2/1/0321570561.jpg",
                1699.90, keywords, true);
        BookDB.insertBook(book);

        // Instantiate Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi();

        // Register our bot
        TelegramLongPollingBot bot = new MainBot();
        try {
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            String logInfo = "BotsAPI TelegramApiException";
            BotLogger.severe(LOGTAG + logInfo, e);
        }
    }
}