package classes.Document;

import java.util.ArrayList;

public class Journal extends Document {

    ArrayList<Issue> issues;

    public Journal(long id, String title, ArrayList<String> publishers, String photoId, double price, ArrayList<String> keywords) {
        super(id, title, publishers, photoId, price, keywords);
    }

    //TODO
}
