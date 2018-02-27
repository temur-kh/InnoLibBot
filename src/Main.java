import classes.CheckOut;
import classes.Document.Book;
import classes.Document.Copy;
import classes.Document.DocAddress;
import classes.Document.Document;
import classes.User.Faculty;
import classes.User.Patron;
import classes.User.Student;
import database.BookDB;
import database.CopyDB;
import database.PatronDB;
import javassist.compiler.ast.Keyword;
import org.bson.types.ObjectId;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;
import org.telegram.telegrambots.logging.BotsFileHandler;
import org.telegram.telegraph.*;
import services.CalendarObjectCreator;
import updater.MainBot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

public class Main {

    private static String LOGTAG = "Class Main: ";

    public static void main(String[] args) {

        //Configure logging system
        BotLogger.setLevel(Level.ALL);
        BotLogger.registerLogger(new ConsoleHandler());
        try {
            BotLogger.registerLogger(new BotsFileHandler());
        } catch (IOException e) {
            String logInfo = "BotLogger IOException";
            BotLogger.severe(LOGTAG + logInfo, e);
        }

        // Initialize Telegraph context
        TelegraphContextInitializer.init();
        TelegraphContext.registerInstance(ExecutorOptions.class, new ExecutorOptions());

        // Initialize Telegram Api Context
        ApiContextInitializer.init();
        //test1();
        //Run tests
        //Tester.TC1();
        //Tester.TC2();
        //Tester.TC3();
        //Tester.TC4();
        //Tester.TC5();
        //Tester.TC6();
        //Tester.TC7();
        //Tester.TC8();
        //Tester.TC9();
        //Tester.TC10();


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

    /**
     * Run test: create and add books to database.
     */
    public static void test1() {
        ArrayList<String> authors = new ArrayList<>(Arrays.asList("William L. Briggs", "Lyle Cochran", "Bernard Gillett"));
        ArrayList<String> keywords = new ArrayList<>(Arrays.asList("calculus", "William", "Briggs", "Bernard"));
        Book book = new Book("Calculus", "1st Edition", authors, "https://www.pearsonhighered.com/assets/bigcovers/0/3/2/1/0321570561.jpg",
                1699.90, keywords, true);

        ArrayList<ObjectId> copyIds = new ArrayList<>();
        DocAddress address = new DocAddress("room", "level", "docCase");

        Copy copy = new Copy(book.getId(), address);
        CopyDB.insertCopy(copy);
        copyIds.add(copy.getId());
        book.setCopyIds(copyIds);
        BookDB.insertBook(book);

        ArrayList<String> authors1 = new ArrayList<>(Arrays.asList("Maggard Thomson", "Jonh Jones", "Maksudov Rishat"));
        ArrayList<String> keywords1 = new ArrayList<>(Arrays.asList("Math", "Discrete", "Maggard"));
        Book book1 = new Book("Discrete Math", "1st edition", authors1, "https://ars.els-cdn.com/content/image/X0012365X.jpg",
                3200.0, keywords1, false);
        copyIds = new ArrayList<>();
        address = new DocAddress("room", "level", "docCase");

        copy = new Copy(book.getId(), address);
        CopyDB.insertCopy(copy);
        copyIds.add(copy.getId());
        book1.setCopyIds(copyIds);

        BookDB.insertBook(book1);


        ArrayList<String> authors2 = new ArrayList<>(Arrays.asList("Arthur Conan Doyle"));
        ArrayList<String> keywords2 = new ArrayList<>(Arrays.asList("Holmes", "Conan", "Doyle", "Sherlock"));
        Book book2 = new Book("Sherlock Holmes", "1st edition", authors2, "https://s00.yaplakal.com/pics/pics_original/2/0/8/10510802.jpg",
                13200.0, keywords2, true);

        copyIds = new ArrayList<>();
        address = new DocAddress("room", "level", "docCase");

        copy = new Copy(book.getId(), address);
        CopyDB.insertCopy(copy);
        copyIds.add(copy.getId());
        book2.setCopyIds(copyIds);

        BookDB.insertBook(book2);
    }

    /**
     * Run test: create and add patron to database.
     */
    public static void test2() {
        //get id by asking person to send message to bot
        Patron patron = new Patron((long) 149477679, "Rishat", "Maksudov", "r.maksudov@innopolis.ru", "+77777777777", "Innopolis University");
        PatronDB.insertPatron(patron);
    }
}