package classes.Document;

import java.util.ArrayList;

public class Journal extends Document {

    private ArrayList<Issue> issues;

    public Journal(long id, String title, ArrayList<String> publishers, String photoId, double price, ArrayList<String> keywords) {
        super(id, title, publishers, photoId, price, keywords);
    }

    public ArrayList<Issue> getIssues() {
        return issues;
    }

    public void setIssues(ArrayList<Issue> issues) {
        this.issues = issues;
    }

    public void addIssue(Issue issue) {
        issues.add(issue);
    }
}
