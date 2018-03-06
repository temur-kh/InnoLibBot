package classes.Document;

import database.BookDB;
import org.bson.types.ObjectId;
import services.PageCreator;

import java.util.ArrayList;

/**
 *This class is extended from main class "Documents"
 *
 */
public class Book extends Document {

    private String edition;
    private boolean bestSeller;
    private boolean canBeCheckedOut;

    //constructors
    public Book(ObjectId id, String title, String edition, ArrayList<String> authors, String photoId, double price, ArrayList<String> keywords, boolean bestSeller) {
        super(id, title, authors, photoId, price, keywords);
        setEdition(edition);
        if (bestSeller)
            setBestSeller();
        setCanBeCheckedOut(true);
        super.setUrl(PageCreator.createBookPage(this));
    }

    public Book(String title, String edition, ArrayList<String> authors, String photoId, double price, ArrayList<String> keywords, boolean bestSeller) {
        super(title, authors, photoId, price, keywords);
        setId(BookDB.createBook());
        setEdition(edition);
        if (bestSeller)
            setBestSeller();
        setCanBeCheckedOut(true);
        super.setUrl(PageCreator.createBookPage(this));
    }

    public Book(String title, String edition, ArrayList<String> authors, String photoId, double price, ArrayList<String> keywords, boolean bestSeller, boolean canBeCheckedOut) {
        super(title, authors, photoId, price, keywords);
        setId(BookDB.createBook());
        setEdition(edition);
        if (bestSeller)
            setBestSeller();
        setCanBeCheckedOut(canBeCheckedOut);
        super.setUrl(PageCreator.createBookPage(this));
    }

    public Book(ObjectId id, String url, String title, String edition, ArrayList<String> authors, String photoId, double price, ArrayList<String> keywords, ArrayList<ObjectId> copyIds, boolean bestSeller, boolean canBeCheckedOut) {
        super(id, url, title, authors, photoId, price, keywords, copyIds);
        setEdition(edition);
        if (bestSeller)
            setBestSeller();
        setCanBeCheckedOut(canBeCheckedOut);
    }


    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public boolean isBestSeller() {
        return bestSeller;
    }

    public void setBestSeller() {
        this.bestSeller = true;
    }

    public boolean canBeCheckedOut() {
        return canBeCheckedOut;
    }

    public void setCanBeCheckedOut(boolean canBeCheckedOut) {
        this.canBeCheckedOut = canBeCheckedOut;
    }
}
