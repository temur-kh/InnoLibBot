package classes.Document;

import org.bson.types.ObjectId;
import services.PageCreator;

import java.util.ArrayList;

public class Journal extends Document {

    private ArrayList<String> issueIds;
    private boolean canBeCheckedOut;

    public Journal(ObjectId id, String title, ArrayList<String> publishers, String photoId, double price, ArrayList<String> keywords) {
        super(id, title, publishers, photoId, price, keywords);
        issueIds = new ArrayList<>();
        setCanBeCheckedOut(true);
        super.setUrl(PageCreator.createJournalPage(this));
    }

    public Journal(ObjectId id, String url, String title, ArrayList<String> publishers, String photoId, double price, ArrayList<String> keywords) {
        super(title, publishers, photoId, price, keywords);
        issueIds = new ArrayList<>();
        setCanBeCheckedOut(true);
        super.setUrl(PageCreator.createJournalPage(this));
    }

    public Journal(ObjectId id, String title, ArrayList<String> publishers, String photoId, double price, ArrayList<String> keywords, ArrayList<String> copyIds, boolean canBeCheckedOut) {
        super(id, title, publishers, photoId, price, keywords, copyIds);
        issueIds = new ArrayList<>();
        setCanBeCheckedOut(canBeCheckedOut);
        super.setUrl(PageCreator.createJournalPage(this));
    }

    public Journal(ObjectId id, String url, String title, ArrayList<String> publishers, String photoId, double price, ArrayList<String> keywords, ArrayList<String> copyIds, boolean canBeCheckedOut) {
        super(id, url, title, publishers, photoId, price, keywords, copyIds);
        issueIds = new ArrayList<>();
        setCanBeCheckedOut(true);
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

    public boolean canBeCheckedOut() {
        return canBeCheckedOut;
    }

    public void setCanBeCheckedOut(boolean canBeCheckedOut) {
        this.canBeCheckedOut = canBeCheckedOut;
    }
}
