package classes.Document;

import database.BookDB;
import database.CopyDB;
import org.bson.types.ObjectId;
import services.PageCreator;

import java.util.ArrayList;

/**
 * Main class from which classes "Book", "AVMaterials", "Journal" are extended.
 *
 */
public class Document {
    //all these information will be held in database of documents
    private ObjectId id;
    private String title;
    private String url;
    private ArrayList<String> authors;
    private String photoId;
    private double price;
    private ArrayList<String> keywords;
    private ArrayList<ObjectId> copyIds;

    //constructors
    public Document(ObjectId id, String title, ArrayList<String> authors, String photoId, double price, ArrayList<String> keywords) {
        setId(id);
        setTitle(title);
        setAuthors(authors);
        setPhotoId(photoId);
        setPrice(price);
        setKeywords(keywords);
    }

    //this constructor creates a new document with a new id in database, when we did not have documents like this before
    public Document(String title, ArrayList<String> authors, String photoId, double price, ArrayList<String> keywords) {
        setId(BookDB.createBook());
        setTitle(title);
        setAuthors(authors);
        setPhotoId(photoId);
        setPrice(price);
        setKeywords(keywords);
    }

    public Document(ObjectId id, String title, ArrayList<String> authors, String photoId, double price, ArrayList<String> keywords, ArrayList<ObjectId> copyIds) {
        setId(id);
        setTitle(title);
        setAuthors(authors);
        setPhotoId(photoId);
        setPrice(price);
        setKeywords(keywords);
        setCopyIds(copyIds);
    }

    public Document(ObjectId id, String url, String title, ArrayList<String> authors, String photoId, double price, ArrayList<String> keywords, ArrayList<ObjectId> copyIds) {
        setId(id);
        setUrl(url);
        setTitle(title);
        setAuthors(authors);
        setPhotoId(photoId);
        setPrice(price);
        setKeywords(keywords);
        setCopyIds(copyIds);
    }

    public ObjectId getId() {
        return id;
    }

    private void setId(ObjectId id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photo) {
        this.photoId = photo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }

    public ArrayList<ObjectId> getCopyIds() {
        return copyIds;
    }

    public void setCopyIds(ArrayList<ObjectId> copyIds) {
        this.copyIds = copyIds;
    }

    public void addCopyId(ObjectId copyId) {
        this.copyIds.add(copyId);
    }

    public boolean hasFreeCopies() {  //imlemented
        for (ObjectId copyId : copyIds) {
            Copy copy = CopyDB.getCopy(copyId);
            if (!copy.isCheckedOut()) {
                return true;
            }
        }
        return false;
    }

    public Copy getFreeCopy(boolean checkedOut) {   //implemented
        for (ObjectId copyId : copyIds) {
            Copy copy = CopyDB.getCopy(copyId);
            if (!copy.isCheckedOut()) {
                copy.setCheckedOut(checkedOut);
                if (checkedOut) CopyDB.updateCopy(copy);
                return copy;
            }
        }
        return null;
    }

    //return authors in form of line
    public String getAuthorsLine() {
        String authorsNames = "";
        for (String names : getAuthors()) {
            authorsNames += names + "; ";
        }
        return authorsNames;
    }

    public String getKeywordsLine() {
        String keywords = "";
        for (String names : getKeywords()) {
            keywords += names + "; ";
        }
        return keywords;
    }
}