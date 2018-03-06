import classes.CheckOut;
import classes.Document.AVMaterial;
import classes.Document.Book;
import classes.Document.Copy;
import classes.Document.DocAddress;
import classes.User.Librarian;
import classes.User.Patron;
import database.*;
import org.bson.types.ObjectId;
import services.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Class for creating tests. Was used for 1st Delivery.
 */
public class Tester_1 {

    static private final String LOGTAG = "TESTER: ";
    static Book b1,b2,b3;
    static AVMaterial av1,av2;
    static Patron p1,p2,p3;
    static Librarian librarian;
    public static void TC1() {
        System.out.println("<-----------Test case 1----------->");
        //librarian
        librarian = new Librarian(01234, "Name", "Surname", "Email", "Phone", "Address");
        LibrarianDB.insertLibrarian(librarian);

        DocAddress address = new DocAddress("room", "level", "docCase");
        //TODO add publisher and publication_year to Book
        //b1
        ArrayList<String> authors = new ArrayList<>(Arrays.asList(": Thomas H. Cormen", "Charles E. Leiserson", "Ronald L. Rivest and Clifford Stein"));
        ArrayList<String> keywords = new ArrayList<>(Arrays.asList("calculus", "William", "Briggs", "Bernard"));
        b1 = new Book("Introduction to Algorithms", "Third edition", authors, "https://www.pearsonhighered.com/assets/bigcovers/0/3/2/1/0321570561.jpg",
                1699.90, keywords, false);

        ArrayList<ObjectId> copyIds = new ArrayList<>();
        for (int i=0;i!=3;i++) {
            Copy copy = new Copy(b1.getId(), address);
            CopyDB.insertCopy(copy);
            copyIds.add(copy.getId());
            b1.setCopyIds(copyIds);
        }
        librarian.addDocument(b1, Constants.BOOK_COLLECTION);

        //b2
        ArrayList<String> authors1 = new ArrayList<>(Arrays.asList("Erich Gamma", "Ralph Johnson", "John Vlissides", "Richard Helm"));
        ArrayList<String> keywords1 = new ArrayList<>(Arrays.asList("Math", "Discrete", "Maggard"));
        b2 = new Book("Design Patterns: Elements of Reusable Object-Oriented Software", "First edition", authors1, "https://ars.els-cdn.com/content/image/X0012365X.jpg",
                3200.0, keywords1, true);

        copyIds = new ArrayList<>();
        for (int i=0;i!=2;i++) {
            Copy copy = new Copy(b2.getId(), address);
            CopyDB.insertCopy(copy);
            copyIds.add(copy.getId());
            b2.setCopyIds(copyIds);
        }
        librarian.addDocument(b2, Constants.BOOK_COLLECTION);

        //b3
        ArrayList<String> authors2 = new ArrayList<>(Arrays.asList("Brooks", "Jr.", "Frederick P"));
        ArrayList<String> keywords2 = new ArrayList<>(Arrays.asList("Brooks", "Jr.", "Frederick P"));
        b3 = new Book("The Mythical Man-month", "Second edition", authors2, "https://s00.yaplakal.com/pics/pics_original/2/0/8/10510802.jpg",
                13200.0, keywords2, false, true);

        copyIds = new ArrayList<>();
        for (int i=0;i!=1;i++) {
            Copy copy = new Copy(b3.getId(), address);
            CopyDB.insertCopy(copy);
            copyIds.add(copy.getId());
            b3.setCopyIds(copyIds);
        }
        librarian.addDocument(b3, Constants.BOOK_COLLECTION);

        //av1
        authors = new ArrayList<>(Arrays.asList("Tony Hoare"));
        av1 = new AVMaterial("Null References: The Billion Dollar Mistake", authors, "link", 123, keywords);
        librarian.addDocument(av1, Constants.AVMATERIAL_COLLECTION);

        //av2
        authors1 = new ArrayList<>(Arrays.asList("Information Entropy"));
        av2 = new AVMaterial("Title", authors1, "link", 312, keywords1);
        librarian.addDocument(av2, Constants.AVMATERIAL_COLLECTION);

        //p1
        p1 = new Patron((long) 1010,"Sergey", "Afonso", "Email", "30001", "Via Margutta, 3", true);
        PatronDB.insertPatron(p1);

        //p2
        p2 = new Patron((long) 1011,"Nadia", "Teixeira", "Email", "30002", "Via Sacra, 13", false);
        PatronDB.insertPatron(p2);

        //p3
        p3 = new Patron((long) 1100,"Elvira", "Espindola", "Email", "30003", "Via del Corso, 22", false);
        PatronDB.insertPatron(p3);

        int count = 0;
        for (Book book: BookDB.getBooksList()) {
            count += book.getCopyIds().size();
        }
        System.out.println("Number of documents in the system: " + (count + AVMaterialDB.getAVMaterialsList().size() + JournalDB.getJournalsList().size()));
        System.out.println("Number of users in the system: " + (LibrarianDB.getLibrariansList().size() + PatronDB.getPatronsList().size()));
        System.out.println("<-----------Test case 1----------->");
    }

    public static void TC2() {
        System.out.println("<-----------Test case 2----------->");

        //run TC1
        TC1();

        //remove 2 copies of b1
        for (int i=0;i!=2;i++) {
            Copy copy = b1.getFreeCopy(false);
            librarian.removeCopy(copy.getId());
            b1 = BookDB.getBook(b1.getId());
        }

        //remove 1 copy of b3
        for (int i=0;i!=1;i++) {
            Copy copy = b3.getFreeCopy(false);
            librarian.removeCopy(copy.getId());
            b3 = BookDB.getBook(b3.getId());
        }

        librarian.removeUser(p2.getId(), Constants.PATRON_COLLECTION);
        p2 = PatronDB.getPatron(p2.getId());

        System.out.println("Number of documents in the system: " + (BookDB.getBooksList().size() + AVMaterialDB.getAVMaterialsList().size() + JournalDB.getJournalsList().size()));
        System.out.println("Number of users in the system: " + (LibrarianDB.getLibrariansList().size() + PatronDB.getPatronsList().size()));
        System.out.println("<-----------Test case 2----------->");
    }

    public static void TC3() {
        System.out.println("<-----------Test case 3----------->");

        //run TC1
        TC1();
        long id1 = p1.getId();
        Patron pat1 = PatronDB.getPatron(id1);
        System.out.println(pat1.getInfo());
        long id3 = p3.getId();
        Patron pat3 = PatronDB.getPatron(id3);
        System.out.println(pat3.getInfo());
        System.out.println("<-----------Test case 3----------->");
    }

    public static void TC4() {
        System.out.println("<-----------Test case 4----------->");

        //run TC2
        TC2();
        try {
            long id2 = p2.getId();
            Patron pat2 = PatronDB.getPatron(id2);
            System.out.println(pat2.getInfo());
        } catch (NullPointerException e){
            System.out.println("No such patron");
        }
        long id3 = p3.getId();
        Patron pat3 = PatronDB.getPatron(id3);
        System.out.println(pat3.getInfo());
        System.out.println("<-----------Test case 4----------->");
    }

    public static void TC5() {
        System.out.println("<-----------Test case 5----------->");

        //run TC2
        TC2();
        try {
            p2.checkOutDocument(b1, Constants.BOOK_COLLECTION);
            b1 = BookDB.getBook(b1.getId());
        } catch (NullPointerException e){
            System.out.println("No such patron");
        }
        System.out.println("<-----------Test case 5----------->");
    }

    public static void TC6() {
        System.out.println("<-----------Test case 6----------->");

        //run TC2
        TC2();
        p1.checkOutDocument(b1, Constants.BOOK_COLLECTION);
        b1 = BookDB.getBook(b1.getId());
        try {
            p3.checkOutDocument(b1, Constants.BOOK_COLLECTION);
            b1 = BookDB.getBook(b1.getId());
        } catch (NoSuchElementException e){
            System.out.println("No copy is available");
        }
        p3.checkOutDocument(b2, Constants.BOOK_COLLECTION);
        b2 = BookDB.getBook(b2.getId());

        long id1 = p1.getId();
        Patron pat1 = PatronDB.getPatron(id1);
        System.out.print(pat1.getInfo());
        System.out.println("Document checked out, due date: ");
        for (CheckOut checkOut: CheckOutDB.getCheckOutsList()) {
            if (checkOut.getPatronId() == id1) {
                System.out.println((checkOut.getDocId().toString().equals(b1.getId().toString()) ? "b1" : "null") + ", " + checkOut.getToDateLine());
            }
        }

        System.out.println();

        long id3 = p3.getId();
        Patron pat3 = PatronDB.getPatron(id3);
        System.out.print(pat3.getInfo());
        System.out.println("Document checked out, due date: ");
        for (CheckOut checkOut: CheckOutDB.getCheckOutsList()) {
            if (checkOut.getPatronId() == id3) {
                System.out.println((checkOut.getDocId().toString().equals(b2.getId().toString()) ? "b2" : "null") + ", " + checkOut.getToDateLine());
            }
        }
        System.out.println("<-----------Test case 6----------->");
    }

    public static void TC7() {
        System.out.println("<-----------Test case 6----------->");

        //run TC1
        TC1();

    }
}
