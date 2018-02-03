package classes.Document;

import services.PageCreator;

import java.util.ArrayList;

public class Book extends Document {

    private String edition;
    private boolean bestSeller;

    public Book(String id, String title, String edition, ArrayList<String> authors, String photoId, double price, ArrayList<String> keywords, boolean bestSeller) {
        super(id, title, authors, photoId, price, keywords);
        setEdition(edition);
        if (bestSeller)
            setBestSeller();
        super.setUrl(PageCreator.createBookPage(this));
    }

    public Book(String id, String url, String title, String edition, ArrayList<String> authors, String photoId, double price, ArrayList<String> keywords, boolean bestSeller) {
        super(id, url, title, authors, photoId, price, keywords);
        setEdition(edition);
        if (bestSeller)
            setBestSeller();
        super.setUrl(PageCreator.createBookPage(this));
    }

    public Book(String id, String url, String title, String edition, ArrayList<String> authors, String photoId, double price, ArrayList<String> keywords, ArrayList<String> copies, boolean bestSeller) {
        super(id, url, title, authors, photoId, price, keywords, copies);
        setEdition(edition);
        if (bestSeller)
            setBestSeller();
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

}
