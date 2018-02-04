package classes.Document;

import org.bson.types.ObjectId;
import services.PageCreator;

import java.util.ArrayList;

public class AVMaterial extends Document {
    public AVMaterial(ObjectId id, String title, ArrayList<String> authors, String photoId, double price, ArrayList<String> keywords) {
        super(id, title, authors, photoId, price, keywords);
        super.setUrl(PageCreator.createAVMaterialPage(this));
    }

    public AVMaterial(ObjectId id, String url, String title, ArrayList<String> authors, String photoId, double price, ArrayList<String> keywords) {
        super(title, authors, photoId, price, keywords);
        super.setUrl(PageCreator.createAVMaterialPage(this));
    }

    public AVMaterial(ObjectId id, String title, ArrayList<String> authors, String photoId, double price, ArrayList<String> keywords,ArrayList<String> copyIds) {
        super(id, title, authors, photoId, price, keywords, copyIds);
        super.setUrl(PageCreator.createAVMaterialPage(this));
    }

    public AVMaterial(ObjectId id, String url, String title, ArrayList<String> authors, String photoId, double price, ArrayList<String> keywords,ArrayList<String> copyIds) {
        super(id, url, title, authors, photoId, price, keywords, copyIds);
    }
}
