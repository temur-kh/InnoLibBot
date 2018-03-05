package classes.User;

import classes.CheckOut;
import classes.Document.*;
import com.mongodb.BasicDBObject;
import database.*;
import org.bson.types.ObjectId;
import org.telegram.telegrambots.logging.BotLogger;
import services.Commands;
import services.Constants;

import java.util.ArrayList;

/**
 * This class is extended from main class "User".
 * All librarians will have more opportunities, than Patrons.
 * One librarian(Admin) will be added at the very beginning, and librarian has opportunity to add more librarians.
 * Also librarian can add books, documents, magazines and so on.
 * Some functions will be implemented later.
 */
public class Librarian extends User {

    private static String LOGTAG = "Librarian: ";

    public Librarian(long id, String name, String surname, String email, String phoneNumber, String address) {
        super(id, name, surname, email, phoneNumber, address);
    }

    //TODO opportunity to add new librarian
    public void setAdmin(User user) {
        if (LibrarianDB.getLibrarian(user.getId()) != null) {
            BotLogger.severe(LOGTAG, "user is already a librarian");
            return;
        }
        Librarian librarian = new Librarian(user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getPhoneNumber(), user.getAddress());
        PatronDB.removePatron(user.getId());
        LibrarianDB.insertLibrarian(librarian);
    }

    public void modifyUser(long userId, String key, Object value, String collection) {
        if(key.equals("_id")) {
            BotLogger.severe(LOGTAG, "cannot modify user Id!");
            return;
        }
        SuperDatabase.modifyObject(userId, key, value, collection);
    }

    public void removeUser(long userId, String collection) {
        SuperDatabase.removeObject(userId, collection);
    }

    public void confirmCheckout(CheckOut checkOut) {
        CopyDB.modifyObject(checkOut.getCopyId(), "checked_out", false, Constants.COPY_COLLECTION);
        CheckOutDB.removeCheckOut(checkOut.getId());
    }

    public void addDocument(BasicDBObject dbObject, String collection) {
        if(dbObject.get("_id") == null) {
            dbObject.append("_id", SuperDatabase.createDBObject(collection));
        }
        addDocument((Document) SuperDatabase.toObject(dbObject, collection), collection);
    }

    //opportunity to add new document to database (id, title, authors, photoId, price, keywords)
    public void addDocument(Document doc, String collection) {
        SuperDatabase.insertObject(DBObjectCreator.toDocumentDBObject(doc), collection);
    }

    //opportunity to delete material by its id
    public void removeDocument(ObjectId docId, String collection) {
        SuperDatabase.removeObject(docId, collection);
    }

    public void updateDocument(BasicDBObject dbObject, String collection) {
        if(dbObject.get("_id") == null) {
            BotLogger.severe(LOGTAG, "dbObject for updating does not contain an Id!");
            return;
        }
        SuperDatabase.updateObject(dbObject, collection);
    }

    public void updateDocument(Document doc, String collection) {
        SuperDatabase.updateObject(DBObjectCreator.toDocumentDBObject(doc), collection);
    }

    public void modifyDocument(ObjectId docId, String key, Object value, String collection) {
        SuperDatabase.modifyObject(docId, key, value, collection);
    }

    //A library may have several copies of each document. Copies are stored in a
    //certain place inside the library, e.g., a room, level.
    public void addCopy(ObjectId docId, DocAddress address) {
        Copy copy = new Copy(docId, address);
        addCopy(copy);
    }

    public void addCopy(Copy copy) {
        CopyDB.insertCopy(copy);
    }

    public void removeCopy(ObjectId copyId) {
        CopyDB.removeCopy(copyId);
    }

    public void updateCopy(BasicDBObject dbObject) {
        if(dbObject.get("_id") == null) {
            BotLogger.severe(LOGTAG, "dbObject for updating does not contain an Id!");
            return;
        }
        updateCopy(CopyDB.toObject(dbObject));
    }

    public void updateCopy(Copy copy) {
        CopyDB.updateCopy(copy);
    }

    public void modifyCopy(ObjectId copyId, String key, Object value) {
        CopyDB.modifyObject(copyId, key, value, Constants.COPY_COLLECTION);
    }

    public void addIssue(BasicDBObject dbObject) {
        if(dbObject.get("_id") == null) {
            dbObject.append("_id", IssueDB.createIssue());
        }
        addIssue(IssueDB.toObject(dbObject));
    }

    public void addIssue(Issue issue) {
        IssueDB.insertIssue(issue);
    }

    public void removeIssue(ObjectId issueId) {
        IssueDB.removeIssue(issueId);
    }

    public void updateIssue(BasicDBObject dbObject) {
        if(dbObject.get("_id") == null) {
            BotLogger.severe(LOGTAG, "dbObject for updating does not contain an Id!");
            return;
        }
        updateIssue(IssueDB.toObject(dbObject));
    }

    public void updateIssue(Issue issue) {
        IssueDB.updateIssue(issue);
    }

    public void modifyIssue(ObjectId issueId, String key, Object value) {
        IssueDB.modifyObject(issueId, key, value, Constants.ISSUE_COLLECTION);
    }

    public void addArticle(BasicDBObject dbObject) {
        if(dbObject.get("_id") == null) {
            dbObject.append("_id", IssueDB.createIssue());
        }
        addArticle(JournalArticleDB.toObject(dbObject));
    }

    public void addArticle(JournalArticle article) {
        JournalArticleDB.insertArticle(article);
    }

    public void removeArticle(ObjectId articleId) {
        JournalArticleDB.removeArticle(articleId);
    }

    public void updateArticle(BasicDBObject dbObject) {
        if(dbObject.get("_id") == null) {
            BotLogger.severe(LOGTAG, "dbObject for updating does not contain an Id!");
            return;
        }
        updateArticle(JournalArticleDB.toObject(dbObject));
    }

    public void updateArticle(JournalArticle article) {
        JournalArticleDB.updateArticle(article);
    }

    public void modifyArticle(ObjectId articleId, String key, Object value) {
        JournalArticleDB.modifyObject(articleId, key, value, Constants.JOURNAL_ARTICLE_COLLECTION);
    }

    public ArrayList<CheckOut> getCheckOutsListByDate() {
        return CheckOutDB.getCheckOutsListByDate();
    }

    //TODO (will be implemented later)
    //shows list of all available documents at the moment(watch class "Documents")
    public ArrayList<Document> getAllDocuments() {
        return null;
    }

    //shows list of documents, that are not returned back in time
    public ArrayList<Document> getOverdueDocuments() {
        return null;
    }

    //shows list of all users from database(watch class "User")
    public ArrayList<User> getAllUsers() {
        return null;
    }

    //shows list of student, who have not returned documents in time
    public ArrayList<User> getAllDeptorUsers() {
        return null;
    }

    //TODO
    public void checkUser(long userId) {
        return;
    }

    @Override
    public String getInfo() {
        String info = super.getInfo();
        info += "<strong>STATUS:</strong> " + Commands.IS_LIBRARIAN + Constants.NEW_LINE;
        return info;
    }
}
