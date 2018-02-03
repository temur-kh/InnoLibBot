package services;

import classes.Document.*;
import org.telegram.telegraph.api.objects.*;

import java.util.ArrayList;

public class PageCreator {
    public static String createBookPage(Book book) {
        ArrayList<Node> content = new ArrayList<>();
        String title = book.getTitle();
        String authorsNames = "";
        for(String names : book.getAuthors()) {
            authorsNames += names + "; ";
        }
        Node
    }
}
