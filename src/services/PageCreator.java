package services;

import classes.Document.*;
import database.IssueDB;
import database.JournalArticleDB;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.logging.BotLogger;
import org.telegram.telegraph.api.methods.CreatePage;
import org.telegram.telegraph.api.objects.*;
import org.telegram.telegraph.exceptions.TelegraphException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class contains methods to create pages of documents.
 */
public class PageCreator {

    public static String LOGTAG = "Page Creator: ";

    //creates page of document
    public static ArrayList<Node> createDocumentContent(Document document) {
        ArrayList<Node> content = new ArrayList<>();
        Node[] nodes = new Node[6];

        //CHECK IT!!!
        HashMap<String,String> map = new HashMap<>();
        map.put("src",document.getPhotoId());
        nodes[0] = new NodeElement().setTag("img").setAttrs(map);
        //nodes[0] = new NodeText(String.format("<img href=\"%s\">", document.getPhotoId()));
        nodes[1] = new NodeText(Constants.BESTSELLER_+Constants.NEW_LINE);
        nodes[2] = new NodeText(Constants.TITLE_ + document.getTitle()+Constants.NEW_LINE);
        nodes[3] = new NodeText(Constants.AUTHORS_ + document.getAuthorsLine()+Constants.NEW_LINE);
        nodes[4] = new NodeText(Constants.KEYWORDS_ + document.getKeywordsLine()+Constants.NEW_LINE);
        nodes[5] = new NodeText(Constants.PRICE_ + document.getPrice()+Constants.NEW_LINE);
        for (Node node : nodes) {
            content.add(node);
        }
        return content;
    }

    //creates page of book
    public static String createBookPage(Book book) {
        ArrayList<Node> content = createDocumentContent(book);
        Node edition = new NodeText(Constants.EDITION_ + book.getEdition()+Constants.NEW_LINE);
        content.add(3, edition);
        try {
            Page page = new CreatePage(TelegraphAccount.getAccount().getAccessToken(), book.getTitle(), content)
                    .setAuthorName(book.getAuthorsLine())
                    .execute();
            return page.getUrl();
        } catch (TelegraphException e) {
            BotLogger.severe(LOGTAG, "cannot create a page!");
        }
        return Constants.EMPTY_LINE;
    }

    //creates page of AV material
    public static String createAVMaterialPage(AVMaterial material) {
        ArrayList<Node> content = createDocumentContent(material);
        try {
            Page page = new CreatePage(TelegraphAccount.getAccount().getAccessToken(), material.getTitle(), content)
                    .setAuthorName(material.getAuthorsLine())
                    .execute();
            return page.getUrl();
        } catch (TelegraphException e) {
            BotLogger.severe(LOGTAG, "cannot create a page!");
        }
        return Constants.EMPTY_LINE;
    }

    //creates page of journal
    public static String createJournalPage(Journal journal) {
        ArrayList<Node> content = createDocumentContent(journal);
        String issueAndArticles = "";
        for (String issueId : journal.getIssueIds()) {
            Issue issue = IssueDB.getIssue(issueId);
            issueAndArticles +=  Constants.ISSUE_ + issue.getPublicationDate().getTime().toString() + Constants.NEW_LINE;
            for (String articleId : issue.getArticleIds()) {
                JournalArticle article = JournalArticleDB.getArticle(articleId);
                issueAndArticles += article.getTitle() + Constants.NEW_LINE;
            }
        }
        Node info = new NodeText(issueAndArticles+Constants.NEW_LINE);
        content.add(info);
        try {
            Page page = new CreatePage(TelegraphAccount.getAccount().getAccessToken(), journal.getTitle(), content)
                    .setAuthorName(journal.getAuthorsLine())
                    .execute();
            return page.getUrl();
        } catch (TelegraphException e) {
            BotLogger.severe(LOGTAG, "cannot create a page!");
        }
        return Constants.EMPTY_LINE;
    }

}
