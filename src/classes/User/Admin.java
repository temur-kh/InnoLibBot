package classes.User;

import database.LibrarianDB;
import services.Constants;

import java.io.*;

public class Admin extends User {

    private static final String LOGTAG = "Admin: ";

    public Admin(long id, String name, String surname, String email, String phoneNumber, String address) {
        super(id, name, surname, Status.Admin, email, phoneNumber, address);
    }

    public void addLibrarian(Librarian librarian) {
        LibrarianDB.insertLibrarian(librarian);
        BotLogger.severe(LOGTAG, "Added librarian");
    }

    public void addLibrarian(Librarian librarian, Permission permission) {
        librarian.setPermission(permission);
        LibrarianDB.insertLibrarian(librarian);
        BotLogger.severe(LOGTAG, "Added librarian");
    }

    public void assignPermission(long id, Permission permission) {
        LibrarianDB.modifyObject(id, "permission", permission.name(), Constants.LIBRARIAN_COLLECTION);
        BotLogger.severe(LOGTAG, "Added permission");
    }

    public void deleteLibrarian(long id) {
        LibrarianDB.removeLibrarian(id);
        BotLogger.severe(LOGTAG, "Deleted librarian");
    }

    public void modifyLibrarian(long id, String key, Object value) {
        LibrarianDB.modifyObject(id, key, value, Constants.LIBRARIAN_COLLECTION);
        BotLogger.severe(LOGTAG, "Modified librarian with id:", id);
    }

    public String readLogFile() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(new File("C:\\Users\\Temur\\IdeaProjects\\InnoLibBot\\TelegramBots0.0.log")));
        String logs = "";
        String st;
        while ((st = br.readLine()) != null)
            logs += st + "\n";
        br.close();
        return logs;
    }

    public void eraseLogFile() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Users\\Temur\\IdeaProjects\\InnoLibBot\\TelegramBots0.0.log")));
        bw.write("");
        bw.close();
    }
}
