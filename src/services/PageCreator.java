package services;

import classes.Document.*;
import database.IssueDB;
import database.JournalArticleDB;
import org.telegram.telegrambots.logging.BotLogger;
import org.telegram.telegraph.api.methods.CreatePage;
import org.telegram.telegraph.api.objects.Node;
import org.telegram.telegraph.api.objects.NodeElement;
import org.telegram.telegraph.api.objects.NodeText;
import org.telegram.telegraph.api.objects.Page;
import org.telegram.telegraph.exceptions.TelegraphException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class contains methods to create pages of documents.
 */
public class PageCreator {

    public static final String LOGTAG = "Page Creator: ";

    //creates page of document
    public static ArrayList<Node> createDocumentContent(Document document) {
        ArrayList<Node> content = new ArrayList<>();
        Node[] nodes = new Node[9];

        //CHECK IT!!!
        HashMap<String, String> map = new HashMap<>();
        ArrayList<Node> nodeList = new ArrayList<>();
        map.put("src", document.getPhotoId());

        nodes[0] = new NodeElement().setTag("img").setAttrs(map);

        nodeList.add(new NodeText(Constants.TITLE_));
        nodes[1] = new NodeElement().setTag("strong").setChildren(nodeList);

        nodeList = new ArrayList<>();
        nodeList.add(new NodeText(document.getTitle()));
        nodes[2] = new NodeElement().setTag("p").setChildren(nodeList);

        nodeList = new ArrayList<>();
        nodeList.add(new NodeText(Constants.AUTHORS_));
        nodes[3] = new NodeElement().setTag("strong").setChildren(nodeList);

        nodeList = new ArrayList<>();
        nodeList.add(new NodeText(document.getAuthorsLine()));
        nodes[4] = new NodeElement().setTag("p").setChildren(nodeList);

        nodeList = new ArrayList<>();
        nodeList.add(new NodeText(Constants.KEYWORDS_));
        nodes[5] = new NodeElement().setTag("strong").setChildren(nodeList);

        nodeList = new ArrayList<>();
        nodeList.add(new NodeText(document.getKeywordsLine()));
        nodes[6] = new NodeElement().setTag("p").setChildren(nodeList);

        nodeList = new ArrayList<>();
        nodeList.add(new NodeText(Constants.PRICE_));
        nodes[7] = new NodeElement().setTag("strong").setChildren(nodeList);

        nodeList = new ArrayList<>();
        nodeList.add(new NodeText(Double.toString(document.getPrice())));
        nodes[8] = new NodeElement().setTag("p").setChildren(nodeList);

        for (Node node : nodes) {
            content.add(node);
        }
        return content;
    }

    //creates page of book
    public static String createBookPage(Book book) {
        ArrayList<Node> content = createDocumentContent(book);
        ArrayList<Node> nodeList = new ArrayList<>();
        nodeList.add(new NodeText(Constants.EDITION_));
        content.add(3, new NodeElement().setTag("strong").setChildren(nodeList));

        nodeList = new ArrayList<>();
        nodeList.add(new NodeText(book.getEdition()));
        content.add(4, new NodeElement().setTag("p").setChildren(nodeList));
        try {
            Page page = new CreatePage(TelegraphAccount.getAccount().getAccessToken(), book.getTitle(), content)
                    .setAuthorName(BotConfig.BOT_USERNAME)
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
                    .setAuthorName(BotConfig.BOT_USERNAME)
                    .execute();
            return page.getUrl();
        } catch (TelegraphException e) {
            BotLogger.severe(LOGTAG, "cannot create a page!");
        }
        return Constants.EMPTY_LINE;
    }

    //creates page of journal
    //TODO will be updated to better version
    public static String createJournalPage(Journal journal) {
        ArrayList<Node> content = createDocumentContent(journal);
        String issueAndArticles = "";
        for (String issueId : journal.getIssueIds()) {
            Issue issue = IssueDB.getIssue(issueId);
            issueAndArticles += Constants.ISSUE_ + issue.getPublicationDate().toString() + Constants.NEW_LINE;
            for (String articleId : issue.getArticleIds()) {
                JournalArticle article = JournalArticleDB.getArticle(articleId);
                issueAndArticles += article.getTitle() + Constants.NEW_LINE;
            }
        }
        Node info = new NodeText(issueAndArticles + Constants.NEW_LINE);
        content.add(info);
        try {
            Page page = new CreatePage(TelegraphAccount.getAccount().getAccessToken(), journal.getTitle(), content)
                    .setAuthorName(BotConfig.BOT_USERNAME)
                    .execute();
            return page.getUrl();
        } catch (TelegraphException e) {
            BotLogger.severe(LOGTAG, "cannot create a page!");
        }
        return Constants.EMPTY_LINE;
    }
}
