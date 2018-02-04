package classes.Document;

import services.PageCreator;

import java.util.ArrayList;

public class Book extends Document {

    private String edition;
    private boolean bestSeller;
    private boolean canBeCheckedOut;

    public Book(String id, String title, String edition, ArrayList<String> authors, String photoId, double price, ArrayList<String> keywords, boolean bestSeller) {
        super(id, title, authors, photoId, price, keywords);
        setEdition(edition);
        if (bestSeller)
            setBestSeller();
        setCanBeCheckedOut(true);
        super.setUrl(PageCreator.createBookPage(this));
    }

    public Book(String id, String url, String title, String edition, ArrayList<String> authors, String photoId, double price, ArrayList<String> keywords, boolean bestSeller) {
        super(id, url, title, authors, photoId, price, keywords);
        setEdition(edition);
        if (bestSeller)
            setBestSeller();
        setCanBeCheckedOut(true);
        super.setUrl(PageCreator.createBookPage(this));
    }

    public Book(String id, String title, String edition, ArrayList<String> authors, String photoId, double price, ArrayList<String> keywords, ArrayList<String> copyIds, boolean bestSeller, boolean canBeCheckedOut) {
        super(id, title, authors, photoId, price, keywords, copyIds);
        setEdition(edition);
        if (bestSeller)
            setBestSeller();
        setCanBeCheckedOut(true);
        super.setUrl(PageCreator.createBookPage(this));
    }

    public Book(String id, String url, String title, String edition, ArrayList<String> authors, String photoId, double price, ArrayList<String> keywords, ArrayList<String> copyIds, boolean bestSeller, boolean canBeCheckedOut) {
        super(id, url, title, authors, photoId, price, keywords, copyIds);
        setEdition(edition);
        if (bestSeller)
            setBestSeller();
        setCanBeCheckedOut(canBeCheckedOut);
        super.setUrl(PageCreator.createBookPage(this));
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
