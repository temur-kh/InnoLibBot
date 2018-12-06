import classes.CheckOut;
import classes.Document.AVMaterial;
import classes.Document.Book;
import classes.Document.Copy;
import classes.Document.DocAddress;
import classes.User.*;
import com.mongodb.DB;
import database.*;
import org.bson.types.ObjectId;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import services.Constants;
import services.DateTime;
import updatehandler.FineSystem;
import updatehandler.NotificationSystem;
import updatehandler.RenewSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Class for creating tests. Was used for 1st Delivery.
 */
public class Tester {

    static private final String LOGTAG = "TESTER: ";
    static Book d1, d2;
    static AVMaterial d3;
    static Faculty p1, p2, p3;
    static Student s;
    static VisitingProfessor v;
    static Librarian librarian;

    public static void init() {
        System.out.println("<-----------Initialization of objects----------->");

        Constants.BLOCKED_BOT = true;

        //librarian
        librarian = new Librarian(01234, "Name", "Surname", "Email", "Phone", "Address");
        LibrarianDB.insertLibrarian(librarian);

        DocAddress address = new DocAddress("room", "level", "docCase");
        //TODO add publisher and publication_year to Book
        //d1
        ArrayList<String> authors = new ArrayList<>(Arrays.asList(": Thomas H. Cormen", "Charles E. Leiserson", "Ronald L. Rivest and Clifford Stein"));
        ArrayList<String> keywords = new ArrayList<>(Arrays.asList(""));
        d1 = new Book("Introduction to Algorithms", "Third edition", authors, "https://www.pearsonhighered.com/assets/bigcovers/0/3/2/1/0321570561.jpg",
                5000.0, keywords, false);

        //add 3 copies
        ArrayList<ObjectId> copyIds = new ArrayList<>();
        for (int i = 0; i != 3; i++) {
            Copy copy = new Copy(d1.getId(), address);
            CopyDB.insertCopy(copy);
            copyIds.add(copy.getId());
            d1.setCopyIds(copyIds);
        }
        librarian.addDocument(d1, Constants.BOOK_COLLECTION);

        //d2
        authors = new ArrayList<>(Arrays.asList("Erich Gamma", "Ralph Johnson", "John Vlissides", "Richard Helm"));
        keywords = new ArrayList<>(Arrays.asList(""));
        d2 = new Book("Design Patterns: Elements of Reusable Object-Oriented Software", "First edition", authors, "https://ars.els-cdn.com/content/image/X0012365X.jpg",
                1700.0, keywords, true);

        //add 3 copies
        copyIds = new ArrayList<>();
        for (int i = 0; i != 3; i++) {
            Copy copy = new Copy(d2.getId(), address);
            CopyDB.insertCopy(copy);
            copyIds.add(copy.getId());
            d2.setCopyIds(copyIds);
        }
        librarian.addDocument(d2, Constants.BOOK_COLLECTION);

        //d3
        authors = new ArrayList<>(Arrays.asList(" Tony Hoare"));
        keywords = new ArrayList<>(Arrays.asList(""));
        d3 = new AVMaterial("Null References: The Billion Dollar Mistake", authors, "https://s00.yaplakal.com/pics/pics_original/2/0/8/10510802.jpg", 700.0, keywords);

        copyIds = new ArrayList<>();
        for (int i = 0; i != 2; i++) {
            Copy copy = new Copy(d3.getId(), address);
            CopyDB.insertCopy(copy);
            copyIds.add(copy.getId());
            d3.setCopyIds(copyIds);
        }
        librarian.addDocument(d3, Constants.BOOK_COLLECTION);

        //p1
        p1 = new Faculty((long) 1010, "Sergey", "Afonso", Status.Professor, "Email", "30001", "Via Margutta, 3");
        PatronDB.insertPatron(p1);

        //p2
        p2 = new Faculty((long) 1011, "Nadia", "Teixeira", Status.Professor, "Email", "30002", "Via Sacra, 13");
        PatronDB.insertPatron(p2);

        //p3
        p3 = new Faculty((long) 1100, "Elvira", "Espindola", Status.Professor, "Email", "30003", "Via del Corso, 22");
        PatronDB.insertPatron(p3);

        //s
        s = new Student((long) 1101, "Andrey", "Velo", "Email", "30004", "Avenida Mazatlan 250");
        PatronDB.insertPatron(s);

        //v
        v = new VisitingProfessor((long) 1110, "Veronika", "Rama", "Email", "30005", "Stret Atocha, 27");
        PatronDB.insertPatron(v);


        System.out.println("<-----------Initialization completed------------>");
    }

    public static void TC1() {
        init();
        System.out.println("<----------------Test 1 Started----------------->");

        Constants.TEMPORARY_CHANGEABLE_CALENDAR = new GregorianCalendar(2018, Calendar.MARCH, 7);

        p1.checkOutDocument(d1, Constants.BOOK_COLLECTION);
        p1.checkOutDocument(d2, Constants.BOOK_COLLECTION);

        librarian.confirmCheckout(CheckOutDB.getCheckOut(p1.getId(), d2.getId()));

        ArrayList<CheckOut> checkOuts = CheckOutDB.getCheckOutsList();
        ArrayList<CheckOut> overdueCheckOuts = CheckOutDB.getOverdueCheckOutsList();

        assert checkOuts.size() == 1;
        assert checkOuts.get(0).getDocId().equals(d1.getId());
        assert checkOuts.get(0).getToDate().equals(DateTime.todayCalendar());
        assert overdueCheckOuts.size() == 0;

        System.out.println("<---------------------PASS---------------------->");

        //drop database
        DB db = DatabaseManager.getDB("Library");
        db.dropDatabase();
        System.out.println("<---------------Test 1 Completed---------------->");
    }

    public static void TC2() {
        init();
        System.out.println("<----------------Test 2 Started----------------->");

        Constants.TEMPORARY_CHANGEABLE_CALENDAR = new GregorianCalendar(2018, Calendar.MARCH, 7);

        p1.checkOutDocument(d1, Constants.BOOK_COLLECTION);
        p1.checkOutDocument(d2, Constants.BOOK_COLLECTION);

        s.checkOutDocument(d1, Constants.BOOK_COLLECTION);
        s.checkOutDocument(d2, Constants.BOOK_COLLECTION);

        v.checkOutDocument(d1, Constants.BOOK_COLLECTION);
        v.checkOutDocument(d2, Constants.BOOK_COLLECTION);

        ArrayList<CheckOut> checkOuts = CheckOutDB.getCheckOutsList();
        ArrayList<CheckOut> overdueCheckOuts = CheckOutDB.getOverdueCheckOutsList();

        assert checkOuts.size() == 2;
        assert overdueCheckOuts.size() == 4;

        assert DateTime.daysUntilToday(CheckOutDB.getCheckOut(p1.getId(), d1.getId()).getToDate()) == 0;
        assert DateTime.daysUntilToday(CheckOutDB.getCheckOut(p1.getId(), d2.getId()).getToDate()) == 0;
        assert DateTime.daysUntilToday(CheckOutDB.getCheckOut(s.getId(), d1.getId()).getToDate()) == 7;
        assert DateTime.daysUntilToday(CheckOutDB.getCheckOut(s.getId(), d2.getId()).getToDate()) == 14;
        assert DateTime.daysUntilToday(CheckOutDB.getCheckOut(v.getId(), d1.getId()).getToDate()) == 21;
        assert DateTime.daysUntilToday(CheckOutDB.getCheckOut(v.getId(), d2.getId()).getToDate()) == 21;

        assert FineSystem.fine(d1.getPrice(), 0) == 0;
        assert FineSystem.fine(d2.getPrice(), 0) == 0;
        assert FineSystem.fine(d1.getPrice(), 7) == 700.0;
        assert FineSystem.fine(d2.getPrice(), 14) == 1400.0;
        assert FineSystem.fine(d1.getPrice(), 21) == 2100.0;
        assert FineSystem.fine(d2.getPrice(), 21) == 1700.0;


        System.out.println("<---------------------PASS---------------------->");

        //drop database
        DB db = DatabaseManager.getDB("Library");
        db.dropDatabase();
        System.out.println("<---------------Test 2 Completed---------------->");
    }

    public static void TC3() {
        init();
        System.out.println("<----------------Test 3 Started----------------->");

        Constants.TEMPORARY_CHANGEABLE_CALENDAR = new GregorianCalendar(2018, Calendar.MARCH, 31);

        CheckOut checkOut1 = p1.checkOutDocument(d1, Constants.BOOK_COLLECTION);
        CheckOut checkout2 = s.checkOutDocument(d2, Constants.BOOK_COLLECTION);
        CheckOut checkOut3 = v.checkOutDocument(d2, Constants.BOOK_COLLECTION);

        Constants.TEMPORARY_CHANGEABLE_CALENDAR = DateTime.todayCalendar();
        RenewSystem.handle(checkOut1);
        RenewSystem.handle(checkout2);
        RenewSystem.handle(checkOut3);

        ArrayList<CheckOut> checkOuts = CheckOutDB.getCheckOutsList();
        ArrayList<CheckOut> overdueCheckOuts = CheckOutDB.getOverdueCheckOutsList();

        assert checkOuts.size() == 3;
        assert overdueCheckOuts.size() == 0;

        assert CheckOutDB.getCheckOut(p1.getId(), d1.getId()).getToDate().equals(new GregorianCalendar(2018, Calendar.MAY, 2));
        assert CheckOutDB.getCheckOut(s.getId(), d2.getId()).getToDate().equals(new GregorianCalendar(2018, Calendar.APRIL, 18));
        assert CheckOutDB.getCheckOut(v.getId(), d2.getId()).getToDate().equals(new GregorianCalendar(2018, Calendar.APRIL, 11));

        System.out.println("<---------------------PASS---------------------->");

        //drop database
        DB db = DatabaseManager.getDB("Library");
        db.dropDatabase();
        System.out.println("<---------------Test 3 Completed---------------->");
    }

    public static void TC4() {
        init();
        System.out.println("<----------------Test 4 Started----------------->");

        Constants.TEMPORARY_CHANGEABLE_CALENDAR = new GregorianCalendar(2018, Calendar.MARCH, 31);

        p1.checkOutDocument(d1, Constants.BOOK_COLLECTION);
        s.checkOutDocument(d2, Constants.BOOK_COLLECTION);
        v.checkOutDocument(d2, Constants.BOOK_COLLECTION);

        librarian.sendOutstandingRequest(d2.getId());

        Constants.TEMPORARY_CHANGEABLE_CALENDAR = DateTime.todayCalendar();

        RenewSystem.handle(CheckOutDB.getCheckOut(p1.getId(), d1.getId()));
        RenewSystem.handle(CheckOutDB.getCheckOut(s.getId(), d2.getId()));
        RenewSystem.handle(CheckOutDB.getCheckOut(v.getId(), d2.getId()));

        ArrayList<CheckOut> checkOuts = CheckOutDB.getCheckOutsList();
        ArrayList<CheckOut> overdueCheckOuts = CheckOutDB.getOverdueCheckOutsList();

        assert checkOuts.size() == 3;
        assert overdueCheckOuts.size() == 0;

        assert CheckOutDB.getCheckOut(p1.getId(), d1.getId()).getToDate().equals(new GregorianCalendar(2018, Calendar.MAY, 2));
        assert CheckOutDB.getCheckOut(s.getId(), d2.getId()).getToDate().equals(new GregorianCalendar(2018, Calendar.APRIL, 5));
        assert CheckOutDB.getCheckOut(v.getId(), d2.getId()).getToDate().equals(new GregorianCalendar(2018, Calendar.APRIL, 5));

        System.out.println("<---------------------PASS---------------------->");

        //drop database
        DB db = DatabaseManager.getDB("Library");
        db.dropDatabase();
        System.out.println("<---------------Test 4 Completed---------------->");
    }

    public static void TC5() {
        init();
        System.out.println("<----------------Test 5 Started----------------->");

        p1.checkOutDocument(d3, Constants.AVMATERIAL_COLLECTION);
        s.checkOutDocument(d3, Constants.AVMATERIAL_COLLECTION);
        v.checkOutDocument(d3, Constants.AVMATERIAL_COLLECTION);

        ArrayList<Patron> queue = PriorityQueueDB.getQueue(d3.getId());

        assert queue.size() == 1;

        assert queue.get(0).getId() == v.getId();

        System.out.println("<---------------------PASS---------------------->");

        //drop database
        DB db = DatabaseManager.getDB("Library");
        db.dropDatabase();
        System.out.println("<---------------Test 5 Completed---------------->");
    }

    public static void TC6(boolean dropDB) {
        init();
        System.out.println("<----------------Test 6 Started----------------->");

        p1.checkOutDocument(d3, Constants.AVMATERIAL_COLLECTION);
        p2.checkOutDocument(d3, Constants.AVMATERIAL_COLLECTION);
        s.checkOutDocument(d3, Constants.AVMATERIAL_COLLECTION);
        v.checkOutDocument(d3, Constants.AVMATERIAL_COLLECTION);
        p3.checkOutDocument(d3, Constants.AVMATERIAL_COLLECTION);

        assert PriorityQueueDB.getQueue(d3.getId()).size() == 3;

        assert PriorityQueueDB.contains(s.getId(), d3.getId());
        assert PriorityQueueDB.contains(v.getId(), d3.getId());
        assert PriorityQueueDB.contains(p3.getId(), d3.getId());


        System.out.println("<---------------------PASS---------------------->");

        //drop database
        if (dropDB) {
            DB db = DatabaseManager.getDB("Library");
            db.dropDatabase();
        }
        System.out.println("<---------------Test 6 Completed---------------->");
    }

    public static void TC7() {
        TC6(false);
        System.out.println("<----------------Test 7 Started----------------->");

        librarian.sendOutstandingRequest(d3.getId());

        ArrayList<Patron> queue = PriorityQueueDB.getQueue(d3.getId());
        ArrayList<CheckOut> checkOuts = CheckOutDB.getCheckOutsList();

        assert queue.size() == 0;
        assert checkOuts.size() == 2;

        assert checkOuts.get(0).getToDate().equals(new GregorianCalendar(2018, Calendar.APRIL, 5));
        assert checkOuts.get(1).getToDate().equals(new GregorianCalendar(2018, Calendar.APRIL, 5));

        System.out.println("<---------------------PASS---------------------->");

        //drop database
        DB db = DatabaseManager.getDB("Library");
        db.dropDatabase();
        System.out.println("<---------------Test 7 Completed---------------->");
    }

    public static void TC8() {
        TC6(false);
        System.out.println("<----------------Test 8 Started----------------->");

        p2.returnDocument(d3);

        ArrayList<CheckOut> checkOuts = CheckOutDB.getCheckOutsList();
        assert CheckOutDB.getCheckOutsList().size() == 2;

        librarian.confirmCheckout(CheckOutDB.getCheckOut(p2.getId(), d3.getId()));
        ArrayList<SendMessage> msgs = NotificationSystem.notifyPatron(librarian.getId(), d3.getId(), Constants.AVMATERIAL_COLLECTION);

        assert CheckOutDB.getCheckOutsList().size() == 1;
        assert CheckOutDB.getCheckOutsListByUserId(p2.getId()).isEmpty();
        assert msgs.get(0).getChatId().equals(Long.toString(s.getId()));
        assert PriorityQueueDB.getQueue(d3.getId()).size() == 2;
        assert PriorityQueueDB.contains(v.getId(), d3.getId());
        assert PriorityQueueDB.contains(p3.getId(), d3.getId());


        System.out.println("<---------------------PASS---------------------->");

        //drop database
        DB db = DatabaseManager.getDB("Library");
        db.dropDatabase();
        System.out.println("<---------------Test 8 Completed---------------->");
    }

    public static void TC9() {
        TC6(false);
        System.out.println("<----------------Test 9 Started----------------->");

        Constants.TEMPORARY_CHANGEABLE_CALENDAR = new GregorianCalendar(2018, Calendar.APRIL, 18);
        RenewSystem.handle(CheckOutDB.getCheckOut(p1.getId(), d3.getId()));

        assert CheckOutDB.getCheckOut(p1.getId(), d3.getId()).getToDate().equals(new GregorianCalendar(2018, Calendar.MAY, 2));
        assert PriorityQueueDB.getQueue(d3.getId()).size() == 3;
        assert PriorityQueueDB.contains(s.getId(), d3.getId());
        assert PriorityQueueDB.contains(v.getId(), d3.getId());
        assert PriorityQueueDB.contains(p3.getId(), d3.getId());

        System.out.println("<---------------------PASS---------------------->");

        //drop database
        DB db = DatabaseManager.getDB("Library");
        db.dropDatabase();
        System.out.println("<---------------Test 9 Completed---------------->");
    }

    public static void TC10() {
        init();
        System.out.println("<----------------Test 10 Started---------------->");

        Constants.TEMPORARY_CHANGEABLE_CALENDAR = new GregorianCalendar(2018, Calendar.MARCH, 28);
        p1.checkOutDocument(d1, Constants.BOOK_COLLECTION);
        v.checkOutDocument(d1, Constants.BOOK_COLLECTION);

        Constants.TEMPORARY_CHANGEABLE_CALENDAR = new GregorianCalendar(2018, Calendar.MARCH, 31);
        RenewSystem.handle(CheckOutDB.getCheckOut(p1.getId(), d1.getId()));
        RenewSystem.handle(CheckOutDB.getCheckOut(v.getId(), d1.getId()));

        Constants.TEMPORARY_CHANGEABLE_CALENDAR = DateTime.todayCalendar();
        RenewSystem.handle(CheckOutDB.getCheckOut(p1.getId(), d1.getId()));
        RenewSystem.handle(CheckOutDB.getCheckOut(v.getId(), d1.getId()));

        assert CheckOutDB.getCheckOutsList().size() == 2;
        assert CheckOutDB.getCheckOut(p1.getId(), d1.getId()).getToDate().equals(new GregorianCalendar(2018, Calendar.APRIL, 28));
        assert CheckOutDB.getCheckOut(v.getId(), d1.getId()).getToDate().equals(new GregorianCalendar(2018, Calendar.APRIL, 11));

        System.out.println("<---------------------PASS---------------------->");

        //drop database
        DB db = DatabaseManager.getDB("Library");
        db.dropDatabase();
        System.out.println("<---------------Test 10 Completed--------------->");
    }
}
