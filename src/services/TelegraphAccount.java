package services;

import database.DatabaseManager;
import org.telegram.telegrambots.logging.BotLogger;
import org.telegram.telegraph.api.methods.CreateAccount;
import org.telegram.telegraph.api.objects.Account;
import org.telegram.telegraph.exceptions.TelegraphException;

public class TelegraphAccount {
    private static volatile TelegraphAccount instance;
    private static volatile Account account;
    private static String LOGTAG = "Telegraph Account: ";
    private TelegraphAccount() {
        createAccount();
    }

    private void createAccount() {
        try {
            account = new CreateAccount(BotConfig.BOT_USERNAME)
                    .execute();
        } catch (TelegraphException e) {
            BotLogger.severe(LOGTAG, "cannot create account!");
        }
    }

    public static TelegraphAccount getInstance() {
        final TelegraphAccount currentInstance;
        if (instance == null) {
            synchronized (DatabaseManager.class) {
                if (instance == null) {
                    instance = new TelegraphAccount();
                }
                currentInstance = instance;
            }
        } else {
            currentInstance = instance;
        }
        return currentInstance;
    }

    public static Account getAccount() {
        return getInstance().account;
    }

}
