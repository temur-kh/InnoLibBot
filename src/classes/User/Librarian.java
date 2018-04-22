package classes.User;

import classes.CheckOut;
import classes.Document.*;
import classes.Notification;
import com.mongodb.BasicDBObject;
import database.*;
import org.bson.types.ObjectId;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.logging.BotLogger;
import services.Commands;
import services.Constants;
import services.DateTime;
import updatehandler.MainBot;
import updatehandler.ReturnSystem;

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

    private Permission permission;

    public Librarian(long id, String name, String surname, String email, String phoneNumber, String address) {
        super(id, name, surname, Status.Librarian, email, phoneNumber, address);
        permission = Permission.Priv1;
    }

    public Librarian(long id, String name, String surname, String email, String phoneNumber, String address, Permission permission) {
        super(id, name, surname, Status.Librarian, email, phoneNumber, address);
        setPermission(permission);
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public void addPatron(Patron patron) {
        if (this.permission.ordinal() < Permission.Priv2.ordinal()) {
            BotLogger.error(LOGTAG, "Permission denied!");
            return;
        }
        PatronDB.insertPatron(patron);
        BotLogger.severe(LOGTAG, "Inserted Patron!");
    }

    public void modifyUser(long userId, String key, Object value, String collection) {
        if(key.equals("_id")) {
            BotLogger.severe(LOGTAG, "cannot modify user Id!");
            return;
        }
        SuperDatabase.modifyObject(userId, key, value, collection);
        BotLogger.severe(LOGTAG, "Modified user with id:" + userId);
    }

    public void removeUser(long userId, String collection) {
        if (this.permission.ordinal() < Permission.Priv3.ordinal()) {
            BotLogger.error(LOGTAG, "Permission denied!");
            return;
        }
        SuperDatabase.removeObject(userId, collection);
        BotLogger.severe(LOGTAG, "Removed user");
    }

    public void confirmCheckout(CheckOut checkOut) {
        CopyDB.modifyObject(checkOut.getCopyId(), "checked_out", false, Constants.COPY_COLLECTION);
        CheckOutDB.removeCheckOut(checkOut.getId());
        BotLogger.severe(LOGTAG, "Confirmed checkout");
    }

    public void addDocument(BasicDBObject dbObject, String collection) {
        if (this.permission.ordinal() < Permission.Priv2.ordinal()) {
            BotLogger.error(LOGTAG, "Permission denied!");
            return;
        }
        if(dbObject.get("_id") == null) {
            dbObject.append("_id", SuperDatabase.createDBObject(collection));
        }
        addDocument((Document) SuperDatabase.toObject(dbObject, collection), collection);
        BotLogger.severe(LOGTAG, "Added document");
    }

    //opportunity to add new document to database (id, title, authors, photoId, price, keywords)
    public void addDocument(Document doc, String collection) {
        if (this.permission.ordinal() < Permission.Priv2.ordinal()) {
            BotLogger.error(LOGTAG, "Permission denied!");
            return;
        }
        if (doc instanceof Book) {
            BookDB.insertBook((Book) doc);
        } else if (doc instanceof AVMaterial) {
            AVMaterialDB.insertAVMaterial((AVMaterial) doc);
        } else if (doc instanceof Journal) {
            JournalDB.insertJournal((Journal) doc);
        }
        BotLogger.severe(LOGTAG, "Added document");
        //SuperDatabase.insertObject(DBObjectCreator.toDocumentDBObject(doc), collection);
    }

    //opportunity to delete material by its id
    public void removeDocument(ObjectId docId, String collection) {
        if (this.permission.ordinal() < Permission.Priv3.ordinal()) {
            BotLogger.error(LOGTAG, "Permission denied!");
            return;
        }
        SuperDatabase.removeObject(docId, collection);
        BotLogger.severe(LOGTAG, "Removed document with id:" + docId);
    }

    public void updateDocument(BasicDBObject dbObject, String collection) {
        if(dbObject.get("_id") == null) {
            BotLogger.severe(LOGTAG, "dbObject for updating does not contain an Id!");
            return;
        }
        SuperDatabase.updateObject(dbObject, collection);
        BotLogger.severe(LOGTAG, "Updated document");
    }

    public void updateDocument(Document doc, String collection) {
        SuperDatabase.updateObject(DBObjectCreator.toDocumentDBObject(doc), collection);
        BotLogger.severe(LOGTAG, "Updated document");
    }

    public void modifyDocument(ObjectId docId, String key, Object value, String collection) {
        SuperDatabase.modifyObject(docId, key, value, collection);
        BotLogger.severe(LOGTAG, "Modified document");
    }

    //A library may have several copies of each document. Copies are stored in a
    //certain place inside the library, e.g., a room, level.
    public void addCopy(ObjectId docId, DocAddress address) {
        if (this.permission.ordinal() < Permission.Priv2.ordinal()) {
            BotLogger.error(LOGTAG, "Permission denied!");
            return;
        }
        Copy copy = new Copy(docId, address);
        addCopy(copy);
        BotLogger.severe(LOGTAG, "Added copy of document with id:" + docId);
    }

    public void addCopy(Copy copy) {
        if (this.permission.ordinal() < Permission.Priv2.ordinal()) {
            BotLogger.error(LOGTAG, "Permission denied!");
            return;
        }
        CopyDB.insertCopy(copy);
        BotLogger.severe(LOGTAG, "Added copy");
    }

    public void removeCopy(ObjectId copyId) {
        if (this.permission.ordinal() < Permission.Priv3.ordinal()) {
            BotLogger.error(LOGTAG, "Permission denied!");
            return;
        }
        Copy copy = CopyDB.getCopy(copyId);
        ObjectId docId = copy.getDocId();

        CopyDB.removeCopy(copyId);

        Book book = BookDB.getBook(docId);
        ArrayList<ObjectId> copyIds = new ArrayList<>();
        for (ObjectId id: book.getCopyIds()) {
            if (!id.toString().equals(copyId.toString())) {
                copyIds.add(id);
            }
        }
        book.setCopyIds(copyIds);
        BookDB.updateBook(book);
        BotLogger.severe(LOGTAG, "Removed copy of document with id:" + docId);
    }

    public void updateCopy(BasicDBObject dbObject) {
        if(dbObject.get("_id") == null) {
            BotLogger.severe(LOGTAG, "dbObject for updating does not contain an Id!");
            return;
        }
        updateCopy(CopyDB.toObject(dbObject));
        BotLogger.severe(LOGTAG, "Updated copy");
    }

    public void updateCopy(Copy copy) {
        CopyDB.updateCopy(copy);
        BotLogger.severe(LOGTAG, "Updated copy");
    }

    public void modifyCopy(ObjectId copyId, String key, Object value) {
        CopyDB.modifyObject(copyId, key, value, Constants.COPY_COLLECTION);
        BotLogger.severe(LOGTAG, "Modified copy with id:" + copyId);
    }

    public void addIssue(BasicDBObject dbObject) {
        if(dbObject.get("_id") == null) {
            dbObject.append("_id", IssueDB.createIssue());
        }
        addIssue(IssueDB.toObject(dbObject));
        BotLogger.severe(LOGTAG, "Added issue");
    }

    public void addIssue(Issue issue) {
        if (this.permission.ordinal() < Permission.Priv2.ordinal()) {
            BotLogger.error(LOGTAG, "Permission denied!");
            return;
        }
        IssueDB.insertIssue(issue);
        BotLogger.severe(LOGTAG, "Added issue");
    }

    public void removeIssue(ObjectId issueId) {
        if (this.permission.ordinal() < Permission.Priv2.ordinal()) {
            BotLogger.error(LOGTAG, "Permission denied!");
            return;
        }
        IssueDB.removeIssue(issueId);
        BotLogger.severe(LOGTAG, "Removed issue with id:" + issueId);
    }

    public void updateIssue(BasicDBObject dbObject) {
        if(dbObject.get("_id") == null) {
            BotLogger.severe(LOGTAG, "dbObject for updating does not contain an Id!");
            return;
        }
        updateIssue(IssueDB.toObject(dbObject));
        BotLogger.severe(LOGTAG, "Updated issue");
    }

    public void updateIssue(Issue issue) {
        IssueDB.updateIssue(issue);
        BotLogger.severe(LOGTAG, "Updated issue");
    }

    public void modifyIssue(ObjectId issueId, String key, Object value) {
        IssueDB.modifyObject(issueId, key, value, Constants.ISSUE_COLLECTION);
        BotLogger.severe(LOGTAG, "Modified issue with id:" + issueId);
    }

    public void addArticle(BasicDBObject dbObject) {
        if (this.permission.ordinal() < Permission.Priv2.ordinal()) {
            BotLogger.error(LOGTAG, "Permission denied!");
            return;
        }
        if(dbObject.get("_id") == null) {
            dbObject.append("_id", IssueDB.createIssue());
        }
        addArticle(JournalArticleDB.toObject(dbObject));
        BotLogger.severe(LOGTAG, "Added article");
    }

    public void addArticle(JournalArticle article) {
        if (this.permission.ordinal() < Permission.Priv2.ordinal()) {
            BotLogger.error(LOGTAG, "Permission denied!");
            return;
        }
        JournalArticleDB.insertArticle(article);
        BotLogger.severe(LOGTAG, "Added article");
    }

    public void removeArticle(ObjectId articleId) {
        if (this.permission.ordinal() < Permission.Priv3.ordinal()) {
            BotLogger.error(LOGTAG, "Permission denied!");
            return;
        }
        JournalArticleDB.removeArticle(articleId);
        BotLogger.severe(LOGTAG, "Removed article with id:" + articleId);
    }

    public void updateArticle(BasicDBObject dbObject) {
        if(dbObject.get("_id") == null) {
            BotLogger.severe(LOGTAG, "dbObject for updating does not contain an Id!");
            return;
        }
        updateArticle(JournalArticleDB.toObject(dbObject));
        BotLogger.severe(LOGTAG, "Updated article");
    }

    public void updateArticle(JournalArticle article) {
        JournalArticleDB.updateArticle(article);
        BotLogger.severe(LOGTAG, "Updated article");
    }

    public void modifyArticle(ObjectId articleId, String key, Object value) {
        JournalArticleDB.modifyObject(articleId, key, value, Constants.JOURNAL_ARTICLE_COLLECTION);
        BotLogger.severe(LOGTAG, "Modified article with id:" + articleId);
    }

    public ArrayList<CheckOut> getCheckOutsList() {
        return CheckOutDB.getAllCheckOutsList();
    }

    public void sendOutstandingRequest(ObjectId docId) {
        if (this.permission.ordinal() != Permission.Priv2.ordinal()) {
            BotLogger.error(LOGTAG, "Permission denied!");
            return;
        }
        ArrayList<Notification> notifications = PriorityQueueDB.getNotifications(docId);
        PriorityQueueDB.removeQueue(docId);
        ArrayList<SendMessage> msgs = new ArrayList<>();
        for (Notification notification : notifications) {
            msgs.add(notification.unavailableDocumentSendMessage());
        }

        for (CheckOut checkOut : CheckOutDB.getAllCheckOutsList()) {
            if (checkOut.getDocId().equals(docId)) {
                Document doc = (Document) SuperDatabase.getObject(docId, checkOut.getDocCollection());
                msgs.add(ReturnSystem.sendLibrarianReturnRequest(checkOut.getPatronId(), doc));
                checkOut.setToDate(DateTime.tomorrowCalendar());
                checkOut.setRenewed(true);
                CheckOutDB.updateCheckOut(checkOut);
            }
        }
        MainBot.getInstance().executeMessages(msgs);
        System.out.println(String.format("Queue for the document %s was deleted!", docId));
        BotLogger.severe(LOGTAG, "Sended outstanding request");
    }

//    //TODO (will be implemented later)
//    //shows list of all available documents at the moment(watch class "Documents")
//    public ArrayList<Document> getAllDocuments() {
//        return null;
//    }
//
//    //shows list of documents, that are not returned back in time
//    public ArrayList<Document> getOverdueDocuments() {
//        return null;
//    }
//
//    //shows list of all users from database(watch class "User")
//    public ArrayList<User> getAllUsers() {
//        return null;
//    }
//
//    //shows list of student, who have not returned documents in time
//    public ArrayList<User> getAllDeptorUsers() {
//        return null;
//    }
//
//    //TODO
//    public void checkUser(long userId) {
//        return;
//    }

    @Override
    public String getInfo() {
        String info = super.getInfo();
        info += "<strong>STATUS:</strong> " + Commands.IS_LIBRARIAN + Constants.NEW_LINE;
        return info;
    }
}
