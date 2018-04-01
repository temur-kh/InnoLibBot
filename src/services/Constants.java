package services;

/**
 * Class of available constants.
 */
public class Constants {
    public static final String TITLE_ = "Title: ";
    public static final String EDITION_ = "Edition: ";
    public static final String AUTHORS_ = "Authors: ";
    public static final String KEYWORDS_ = "Keywords: ";
    public static final String BESTSELLER_ = "Bestseller!";
    public static final String PRICE_ = "Price: ";
    public static final String ISSUE_ = "Issue: ";


    public static final int BOOK_CHECK_OUT_LIMIT = 3 * 7;
    public static final int BOOK_CHECK_OUT_LIMIT_FOR_FACULTY = 4 * 7;
    public static final int BEST_SELLER_CHECK_OUT_LIMIT = 2 * 7;
    public static final int AVMATERIAL_AND_JOURNAL_CHECK_OUT_LIMIT = 2 * 7;
    public static final int VISITING_PROFESSOR_CHECK_OUT_LIMIT = 7;
    public static final double FINE_PER_DAY = 100;
    public static final String CANNOT_BE_CHECKED_OUT_INFO = "Reference Book | Magazine";


    public static final String DASH = "-";
    public static final String NEW_LINE = "\n";
    public static final String EMPTY_LINE = "";
    public static final String ZERO = "0";
    public static final int SECURE_PASSWORD_LENGTH = 8;

    public static final String BOOK_COLLECTION = "Book";
    public static final String AVMATERIAL_COLLECTION = "AVMaterial";
    public static final String JOURNAL_COLLECTION = "Journal";
    public static final String JOURNAL_ARTICLE_COLLECTION = "JournalArticle";
    public static final String ISSUE_COLLECTION = "Issue";
    public static final String ARTICLE_COLLECTION = "JournalArticle";
    public static final String LIBRARIAN_COLLECTION = "Librarian";
    public static final String PATRON_COLLECTION = "Patron";
    public static final String COPY_COLLECTION = "Copy";
    public static final String CHECKOUT_COLLECTION = "CheckOut";
    public static final String STATE_COLLECTION = "State";
    public static final String PRIORITY_QUEUE_COLLECTION = "PriorityQueue";
    public static final String NOTIFICATION_COLLECTION = "Notification";


    public static final String MY_CHECKOUT_COLLECTION = "My CheckOut Collection";
    public static final String MY_OVERDUE_CHECKOUT_COLLECTION = "My Overdue CheckOuts";

}