import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.sun.javaws.exceptions.ExitException;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;
import org.telegram.telegrambots.logging.BotsFileHandler;
import org.telegram.telegraph.*;
import updater.MainBot;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

public class Main {
    private static String LOGTAG = "Class Main: ";
    //public static MongoClient mongoClient;
    public static void main(String[] args) {
        //Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("In shutdown hook"), "Shutdown-thread"));
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

        // Instantiate Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi();

        // Register our bot
        TelegramLongPollingBot bot = new MainBot();
        try {
            botsApi.registerBot(bot);
            //mongoClient = new MongoClient(new MongoClientURI(String.format("mongodb+srv://toyo:<%s>@cluster0-ovczs.mongodb.net/test",BotConfig.MONGODB_ADMIN_PASSWORD)));
        } catch (TelegramApiException e) {
            String logInfo = "BotsAPI TelegramApiException";
            BotLogger.severe(LOGTAG + logInfo, e);
        }
    }
}