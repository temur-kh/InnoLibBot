package classes.Document;

import java.util.ArrayList;

public class AVMaterial extends Document {

    public AVMaterial(long id, String title, ArrayList<String> authors, String photoId, double price, ArrayList<String> keywords) {
        super(id, title, authors, photoId, price, keywords);
    }
}
