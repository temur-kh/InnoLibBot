package services;

import classes.Document.*;
import org.telegram.telegrambots.logging.BotLogger;
import org.telegram.telegraph.api.methods.CreatePage;
import org.telegram.telegraph.api.objects.*;
import org.telegram.telegraph.exceptions.TelegraphException;

import java.util.ArrayList;

public class PageCreator {

    public static String LOGTAG = "Page Creator: ";

    public static String createBookPage(Book book) {
        ArrayList<Node> content = new ArrayList<>();
        String authorsNames = "";
        for(String names : book.getAuthors()) {
            authorsNames += names + "; ";
        }
        Node[] nodes = new Node[7];
        nodes[0] = new NodeElement();
        nodes[1] = new NodeText(Constants.BESTSELLER_);
        nodes[2] = new NodeText(Constants.TITLE_+book.getTitle());
        nodes[3] = new NodeText(Constants.AUTHORS_+authorsNames);
        nodes[4] = new NodeText(Constants.EDITION_+book.getEdition());
        nodes[5] = new NodeText(Constants.KEYWORDS_+book.getKeywords().toString());
        nodes[6] = new NodeText(Constants.PRICE_+book.getPrice());
        for(Node node: nodes) {
            content.add(node);
        }
        try {
            Page page = new CreatePage(TelegraphAccount.getAccount().getAccessToken(),book.getTitle(),content)
                    .setAuthorName(authorsNames)
                    .execute();
            return page.getUrl();
        } catch (TelegraphException e) {
            BotLogger.severe(LOGTAG, "cannot create a page!");
        }
        return Constants.EMPTY_LINE;
    }
}
