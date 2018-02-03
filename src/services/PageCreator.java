package services;

import classes.Document.*;
import org.telegram.telegrambots.logging.BotLogger;
import org.telegram.telegraph.api.methods.CreatePage;
import org.telegram.telegraph.api.objects.*;
import org.telegram.telegraph.exceptions.TelegraphException;

import java.util.ArrayList;

public class PageCreator {

    public static String LOGTAG = "Page Creator: ";

    public static ArrayList<Node> createDocumentContent(Document document) {
        ArrayList<Node> content = new ArrayList<>();
        Node[] nodes = new Node[6];
        nodes[0] = new NodeElement();
        nodes[1] = new NodeText(Constants.BESTSELLER_);
        nodes[2] = new NodeText(Constants.TITLE_+document.getTitle());
        nodes[3] = new NodeText(Constants.AUTHORS_+document.getAuthorsLine());
        nodes[4] = new NodeText(Constants.KEYWORDS_+document.getKeywords().toString());
        nodes[5] = new NodeText(Constants.PRICE_+document.getPrice());
        for(Node node: nodes) {
            content.add(node);
        }
        return content;
    }

    public static String createBookPage(Book book) {
        ArrayList<Node> content = createDocumentContent(book);
        Node edition = new NodeText(Constants.EDITION_+book.getEdition());
        content.add(3,edition);
        try {
            Page page = new CreatePage(TelegraphAccount.getAccount().getAccessToken(),book.getTitle(),content)
                    .setAuthorName(book.getAuthorsLine())
                    .execute();
            return page.getUrl();
        } catch (TelegraphException e) {
            BotLogger.severe(LOGTAG, "cannot create a page!");
        }
        return Constants.EMPTY_LINE;
    }
}
