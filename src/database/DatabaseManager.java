package database;

import com.mongodb.*;
import org.telegram.telegrambots.logging.BotLogger;
import services.BotConfig;

public class DatabaseManager {
    private static volatile DatabaseManager instance;
    private static volatile MongoClient mongoClient;
    private String LOGTAG = "DatabaseManager: ";

    private DatabaseManager() {
        mongoClient = new MongoClient(new MongoClientURI(String.format("mongodb+srv://toyo:%s@cluster0-ovczs.mongodb.net/test", BotConfig.MONGODB_ADMIN_PASSWORD)));
    }

    //Copyrights https://github.com/rubenlagus/TelegramBotsExample/blob/master/src/main/java/org/telegram/database/DatabaseManager.java
    public static DatabaseManager getInstance() {
        final DatabaseManager currentInstance;
        if (instance == null) {
            synchronized (DatabaseManager.class) {
                if (instance == null) {
                    instance = new DatabaseManager();
                }
                currentInstance = instance;
            }
        } else {
            currentInstance = instance;
        }
        return currentInstance;
    }

    public static MongoClient getClient() {
        return getInstance().mongoClient;
    }

    public static DB getDB(String name) {
        return getClient().getDB(name);
    }

    public static DBCollection getCollection(String name) {
        return getDB(BotConfig.DATABASE_NAME).getCollection(name);
    }

}
