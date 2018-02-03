package classes.Document;

import services.PageCreator;

import java.util.ArrayList;

public class Book extends Document {

    private String edition;
    private boolean bestSeller;

    public Book(long id, String title, String edition, ArrayList<String> authors, String photoId, double price, ArrayList<String> keywords, boolean bestSeller) {
        super(id, title, authors, photoId, price, keywords);
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
