import classes.CheckOut;
import classes.Document.Book;
import classes.Document.Copy;
import classes.Document.DocAddress;
import classes.User.Faculty;
import classes.User.Patron;
import classes.User.Student;
import database.BookDB;
import database.CopyDB;
import org.bson.types.ObjectId;
import org.telegram.telegrambots.logging.BotLogger;
import services.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Class for creating tests. Was used for 1st Delivery.
 */
public class Tester {

    static private final String LOGTAG = "TESTER: ";

    /**
     * Works!!!
     */
    public static void TC1() {
        Patron patron = new Patron((long) 149477679, "Rishat", "Maksudov",
                "r.maksudov@innopolis.ru", "+77777777777", "Innopolis University");

        ArrayList<String> authors = new ArrayList<>(Arrays.asList("William L. Briggs", "Lyle Cochran", "Bernard Gillett"));
        ArrayList<String> keywords = new ArrayList<>(Arrays.asList("calculus", "William", "Briggs", "Bernard"));
        Book book = new Book("Calculus", "1st Edition", authors, "https://www.pearsonhighered.com/assets/bigcovers/0/3/2/1/0321570561.jpg",
                1699.90, keywords, true);

        //create copies, get their ids and add them to book
        ArrayList<ObjectId> copyIds = new ArrayList<>();
        DocAddress address = new DocAddress("room", "level", "docCase");

        Copy copy = new Copy(book.getId(), address);
        CopyDB.insertCopy(copy);
        copyIds.add(copy.getId());
        copy = new Copy(book.getId(), address);
        CopyDB.insertCopy(copy);
        copyIds.add(copy.getId());

        book.setCopyIds(copyIds);
        BookDB.insertBook(book);

        CheckOut checkOut = null;

        if (book.hasFreeCopies()) {
            try {
                checkOut = patron.checkOutDocument(book, Constants.BOOK_COLLECTION); //changes added
            } catch (NoSuchElementException e) {
                BotLogger.severe(LOGTAG, "could not check out document!");
            }
        }

        //Result
        if (checkOut != null) {
            System.out.println("Patron " + checkOut.getPatronId() + " checked out a book " + checkOut.getDocId() + " copy " + checkOut.getCopyId());
        }

        if (book.hasFreeCopies()) {
            System.out.println("there are free copies for this book!");
        }

    }

    /**
     * Works!!!
     */
    public static void TC2() {
        Patron patron = new Patron((long) 149477679, "Rishat", "Maksudov", "r.maksudov@innopolis.ru", "+77777777777", "Innopolis University");
        ArrayList<String> authors = new ArrayList<>();
        authors.add("Nicola Tesla");
        ArrayList<String> keywords = new ArrayList<>();
        keywords.add("Nicola");
        keywords.add("Tesla");
        Book book = new Book(new ObjectId("5a78b88d791a84116ceb7b1f"), "asdf", "1st edition", authors, "https://s00.yaplakal.com/pics/pics_original/2/0/8/10510802.jpg", 1111.1, keywords, false);
        try {
            patron.checkOutDocument(book, Constants.BOOK_COLLECTION);
        } catch (NoSuchElementException e) {
            BotLogger.severe(LOGTAG, "could not check out document!");
        }
    }

    /**
     * Works!
     */
    public static void TC3() {
        Faculty faculty = new Faculty((long) 149477679, "Rishat", "Maksudov", "r.maksudov@innopolis.ru", "+77777777777", "Innopolis University");
        Student student = new Student((long) 149477678, "Rishat1", "Maksudov1", "r.maksudov1@innopolis.ru", "+77777777771", "Innopolis University");
        ArrayList<String> authors = new ArrayList<>(Arrays.asList("William L. Briggs", "Lyle Cochran", "Bernard Gillett"));
        ArrayList<String> keywords = new ArrayList<>(Arrays.asList("calculus", "William", "Briggs", "Bernard"));
        Book book = new Book("Calculus", "1st Edition", authors, "https://www.pearsonhighered.com/assets/bigcovers/0/3/2/1/0321570561.jpg",
                1699.90, keywords, false);

        ArrayList<ObjectId> copyIds = new ArrayList<>();
        DocAddress address = new DocAddress("room", "level", "docCase");
        Copy copy = new Copy(book.getId(), address);
        CopyDB.insertCopy(copy);
        copyIds.add(copy.getId());
        book.setCopyIds(copyIds);

        BookDB.insertBook(book);
        CheckOut checkOut = faculty.checkOutDocument(book, Constants.BOOK_COLLECTION);
        System.out.println("From " + checkOut.getFromDate() + " to " + checkOut.getToDate());
    }

    /**
     * Works!
     */
    public static void TC4() {
        Faculty faculty = new Faculty((long) 149477679, "Rishat", "Maksudov", "r.maksudov@innopolis.ru", "+77777777777", "Innopolis University");
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
        CheckOut checkOut = faculty.checkOutDocument(book, Constants.BOOK_COLLECTION);

        System.out.println("From " + checkOut.getFromDate() + " to " + checkOut.getToDate());
    }

    /**
     * Works!!!
     */
    public static void TC5() {
        Patron patron1 = new Patron((long) 149477679, "Rishat1", "Maksudov1", "r.maksudov1@innopolis.ru", "+77777777777", "Innopolis University");
        Patron patron2 = new Patron((long) 149477678, "Rishat2", "Maksudov2", "r.maksudov2@innopolis.ru", "+77777777778", "Innopolis University");
        Patron patron3 = new Patron((long) 149477677, "Rishat3", "Maksudov3", "r.maksudov3@innopolis.ru", "+77777777779", "Innopolis University");
        ArrayList<String> authors = new ArrayList<>(Arrays.asList("William L. Briggs", "Lyle Cochran", "Bernard Gillett"));
        ArrayList<String> keywords = new ArrayList<>(Arrays.asList("calculus", "William", "Briggs", "Bernard"));
        Book book = new Book("Calculus", "1st Edition", authors, "https://www.pearsonhighered.com/assets/bigcovers/0/3/2/1/0321570561.jpg",
                1699.90, keywords, true);
        ArrayList<ObjectId> copies = new ArrayList<>();
        Copy copy = new Copy(book.getId(), new DocAddress("room1", "level1", "case1"));
        CopyDB.insertCopy(copy);
        copies.add(copy.getId());
        copy = new Copy(book.getId(), new DocAddress("room2", "level2", "case2"));
        CopyDB.insertCopy(copy);
        copies.add(copy.getId());
        book.setCopyIds(copies);
        BookDB.insertBook(book);
        patron1.checkOutDocument(book, Constants.BOOK_COLLECTION);
        System.out.println("p1 took copy");
        patron2.checkOutDocument(book, Constants.BOOK_COLLECTION);
        System.out.println("p2 took copy");

        if (!book.hasFreeCopies()) {
            System.out.println("There are no copies yet!");
        }
        try {
            patron3.checkOutDocument(book, Constants.BOOK_COLLECTION);
            System.out.println("p3 took copy");
        } catch (NoSuchElementException e) {
            BotLogger.severe(LOGTAG, "could not check out document!");
        }
    }

    /**
     * Works!
     */
    public static void TC6() {
        Faculty faculty = new Faculty((long) 149477679, "Rishat", "Maksudov", "r.maksudov@innopolis.ru", "+77777777777", "Innopolis University");
        Student student = new Student((long) 149477678, "Rishat1", "Maksudov1", "r.maksudov1@innopolis.ru", "+77777777771", "Innopolis University");
        ArrayList<String> authors = new ArrayList<>(Arrays.asList("William L. Briggs", "Lyle Cochran", "Bernard Gillett"));
        ArrayList<String> keywords = new ArrayList<>(Arrays.asList("calculus", "William", "Briggs", "Bernard"));
        Book book = new Book("Calculus", "1st Edition", authors, "https://www.pearsonhighered.com/assets/bigcovers/0/3/2/1/0321570561.jpg",
                1699.90, keywords, true);

        ArrayList<ObjectId> copyIds = new ArrayList<>();
        DocAddress address = new DocAddress("room", "level", "docCase");

        Copy copy = new Copy(book.getId(), address);
        CopyDB.insertCopy(copy);
        copyIds.add(copy.getId());
        copy = new Copy(book.getId(), address);
        CopyDB.insertCopy(copy);
        copyIds.add(copy.getId());

        book.setCopyIds(copyIds);

        BookDB.insertBook(book);

        if (book.hasFreeCopies()) {
            try {
                student.checkOutDocument(book, Constants.BOOK_COLLECTION);
            } catch (SecurityException e) {
                BotLogger.severe(LOGTAG, "already has checked out this document!(1)");
            }
        }

        if (book.hasFreeCopies()) {
            try {
                student.checkOutDocument(book, Constants.BOOK_COLLECTION);
            } catch (SecurityException e) {
                BotLogger.severe(LOGTAG, "already has checked out this document!(2)");
            }
        }

    }

    /**
     * Works!!!
     */
    public static void TC7() {
        Patron patron1 = new Patron((long) 149477679, "Rishat1", "Maksudov1", "r.maksudov1@innopolis.ru", "+77777777777", "Innopolis University");
        Patron patron2 = new Patron((long) 149477678, "Rishat2", "Maksudov2", "r.maksudov2@innopolis.ru", "+77777777778", "Innopolis University");
        ArrayList<String> authors = new ArrayList<>(Arrays.asList("William L. Briggs", "Lyle Cochran", "Bernard Gillett"));
        ArrayList<String> keywords = new ArrayList<>(Arrays.asList("calculus", "William", "Briggs", "Bernard"));
        Book book = new Book("Calculus", "1st Edition", authors, "https://www.pearsonhighered.com/assets/bigcovers/0/3/2/1/0321570561.jpg",
                1699.90, keywords, true);

        ArrayList<ObjectId> copyIds = new ArrayList<>();
        DocAddress address = new DocAddress("room", "level", "docCase");

        Copy copy = new Copy(book.getId(), address);
        CopyDB.insertCopy(copy);
        copyIds.add(copy.getId());
        copy = new Copy(book.getId(), address);
        CopyDB.insertCopy(copy);
        copyIds.add(copy.getId());

        book.setCopyIds(copyIds);
        BookDB.insertBook(book);
        if (book.hasFreeCopies()) {
            patron1.checkOutDocument(book, Constants.BOOK_COLLECTION);
            if (book.hasFreeCopies()) {
                patron2.checkOutDocument(book, Constants.BOOK_COLLECTION);
                System.out.println("both took books");
            }
        }


    }

    /**
     * Works!!!
     */
    public static void TC8() {
        Faculty faculty = new Faculty((long) 149477679, "Rishat", "Maksudov", "r.maksudov@innopolis.ru", "+77777777777", "Innopolis University");
        Student student = new Student((long) 149477678, "Rishat1", "Maksudov1", "r.maksudov1@innopolis.ru", "+77777777771", "Innopolis University");
        ArrayList<String> authors = new ArrayList<>(Arrays.asList("William L. Briggs", "Lyle Cochran", "Bernard Gillett"));
        ArrayList<String> keywords = new ArrayList<>(Arrays.asList("calculus", "William", "Briggs", "Bernard"));
        Book book = new Book("Calculus", "1st Edition", authors, "https://www.pearsonhighered.com/assets/bigcovers/0/3/2/1/0321570561.jpg",
                1699.90, keywords, false);

        ArrayList<ObjectId> copyIds = new ArrayList<>();
        DocAddress address = new DocAddress("room", "level", "docCase");
        Copy copy = new Copy(book.getId(), address);
        CopyDB.insertCopy(copy);
        copyIds.add(copy.getId());
        book.setCopyIds(copyIds);

        BookDB.insertBook(book);

        CheckOut checkOut = student.checkOutDocument(book, Constants.BOOK_COLLECTION);
        System.out.println("From " + checkOut.getFromDate() + " to " + checkOut.getToDate());
    }

    /**
     * Works!!!
     */
    public static void TC9() {
        Faculty faculty = new Faculty((long) 149477679, "Rishat", "Maksudov", "r.maksudov@innopolis.ru", "+77777777777", "Innopolis University");
        Student student = new Student((long) 149477678, "Rishat1", "Maksudov1", "r.maksudov1@innopolis.ru", "+77777777771", "Innopolis University");
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
        CheckOut checkOut = student.checkOutDocument(book, Constants.BOOK_COLLECTION);
        System.out.println("From " + checkOut.getFromDate() + " to " + checkOut.getToDate());
    }

    /**
     * Works!!!
     */
    public static void TC10() {
        Patron patron = new Student((long) 149477678, "Rishat1", "Maksudov1", "r.maksudov1@innopolis.ru", "+77777777771", "Innopolis University");

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

        ArrayList<String> authors2 = new ArrayList<>(Arrays.asList("Arthur Conan Doyle"));
        ArrayList<String> keywords2 = new ArrayList<>(Arrays.asList("Holmes", "Conan", "Doyle", "Sherlock"));
        Book book2 = new Book("Sherlock Holmes", "1st edition", authors2, "https://s00.yaplakal.com/pics/pics_original/2/0/8/10510802.jpg",
                13200.0, keywords2, true, false);

        copyIds = new ArrayList<>();
        address = new DocAddress("room", "level", "docCase");
        copy = new Copy(book.getId(), address);
        CopyDB.insertCopy(copy);
        copyIds.add(copy.getId());
        book2.setCopyIds(copyIds);

        BookDB.insertBook(book2);

        try {
            CheckOut checkOut = patron.checkOutDocument(book, Constants.BOOK_COLLECTION);
            System.out.println("From " + checkOut.getFromDate() + " to " + checkOut.getToDate());
        } catch (SecurityException e) {
            BotLogger.severe(LOGTAG, "WRONG EXCEPTION!!!");
        }

        try {
            CheckOut checkOut = patron.checkOutDocument(book2, Constants.BOOK_COLLECTION);
            System.out.println("From " + checkOut.getFromDate() + " to " + checkOut.getToDate());
        } catch (SecurityException e) {
            BotLogger.severe(LOGTAG, "document cannot be checked out!");
        }
    }
}
