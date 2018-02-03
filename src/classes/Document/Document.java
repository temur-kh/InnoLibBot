package classes.Document;

import services.PageCreator;

import java.util.ArrayList;

public class Document {

    private long id;
    private String title;
    private String url;
    private ArrayList<String> authors;
    private String photoId;
    private double price;
    private ArrayList<String> keywords;
    private ArrayList<String> copyIds;

    public Document(long id, String title, ArrayList<String> authors, String photoId, double price, ArrayList<String> keywords) {
        setId(id);
        setTitle(title);
        setAuthors(authors);
        setPhotoId(photoId);
        setPrice(price);
        setKeywords(keywords);
        setUrl(PageCreator.createDocumentPage(this));
    }

    public Document(long id, String url, String title, ArrayList<String> authors, String photoId, double price, ArrayList<String> keywords) {
        setId(id);
        setUrl(url);
        setTitle(title);
        setAuthors(authors);
        setPhotoId(photoId);
        setPrice(price);
        setKeywords(keywords);
        setUrl(PageCreator.createDocumentPage(this));
    }

    public Document(long id, String url, String title, ArrayList<String> authors, String photoId, double price, ArrayList<String> keywords, ArrayList<String> copies) {
        setId(id);
        setUrl(url);
        setTitle(title);
        setAuthors(authors);
        setPhotoId(photoId);
        setPrice(price);
        setKeywords(keywords);
        setUrl(PageCreator.createDocumentPage(this));
        setCopyIds(copies);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public ArrayList<String> getCopyIds() {
        return copyIds;
    }

    public void setCopyIds(ArrayList<String> copyIds) {
        this.copyIds = copyIds;
    }

    public void addCopyId(String copyId) {
        this.copyIds.add(copyId);
    }
}
