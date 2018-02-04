package classes.Document;

import services.PageCreator;

import java.util.ArrayList;

public class AVMaterial extends Document {
    public AVMaterial(String id, String title, ArrayList<String> authors, String photoId, double price, ArrayList<String> keywords) {
        super(id, title, authors, photoId, price, keywords);
    }

    public AVMaterial(String id, String url, String title, ArrayList<String> authors, String photoId, double price, ArrayList<String> keywords) {
        super(id, url, title, authors, photoId, price, keywords);
    }

    public AVMaterial(String id, String title, ArrayList<String> authors, String photoId, double price, ArrayList<String> keywords,ArrayList<String> copyIds) {
        super(id, title, authors, photoId, price, keywords, copyIds);
        super.setUrl(PageCreator.createAVMaterialPage(this));
    }

    public AVMaterial(String id, String url, String title, ArrayList<String> authors, String photoId, double price, ArrayList<String> keywords,ArrayList<String> copyIds) {
        super(id, url, title, authors, photoId, price, keywords, copyIds);
    }
}
