package classes.Document;

import java.util.ArrayList;

public class Journal extends Document {

    private ArrayList<String> issueIds;

    public Journal(String id, String title, ArrayList<String> publishers, String photoId, double price, ArrayList<String> keywords) {
        super(id, title, publishers, photoId, price, keywords);
    }

    public Journal(String id, String url, String title, ArrayList<String> publishers, String photoId, double price, ArrayList<String> keywords) {
        super(id, url, title, publishers, photoId, price, keywords);
    }

    public Journal(String id, String url, String title, ArrayList<String> publishers, String photoId, double price, ArrayList<String> keywords, ArrayList<String> copyIds) {
        super(id, url, title, publishers, photoId, price, keywords, copyIds);
    }

    public ArrayList<String> getIssueIds() {
        return issueIds;
    }

    public void setIssueIds(ArrayList<String> issueIds) {
        this.issueIds = issueIds;
    }

    public void addIssueId(String issueId) {
        issueIds.add(issueId);
    }
}
