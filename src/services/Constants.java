package services;

import java.time.Duration;

public class Constants {
    public static final String TITLE_ = "Title: ";
    public static final String EDITION_ = "Edition: ";
    public static final String AUTHORS_ = "Authors: ";
    public static final String KEYWORDS_ = "Keywords: ";
    public static final String BESTSELLER_ = "Bestseller!";
    public static final String PRICE_ = "Price: ";
    public static final String ISSUE_ = "Issue: ";


    public static final Duration BOOK_CHECK_OUT_LIMIT = Duration.ofDays(3 * 7);
    public static final Duration BOOK_CHECK_OUT_LIMIT_FOR_FACULTY = Duration.ofDays(4 * 7);
    public static final Duration BEST_SELLER_CHECK_OUT_LIMIT = Duration.ofDays(2 * 7);
    public static final Duration AVMATERIAL_CHECK_OUT_LIMIT = Duration.ofDays(2 * 7);
    public static final Duration JOURNAL_CHECK_OUT_LIMIT = Duration.ofDays(2 * 7);
    public static final String CANNOT_BE_CHECKED_OUT_INFO = "Reference Book | Magazine";


    public static final String NEW_LINE = "\n";
    public static final String EMPTY_LINE = "";

    public static final String BOOK_COLLECTION  = "Book";
    public static final String JOURNAL_COLLECTION = "JOURNAL";
    public static final String AVLMATERIAL_COLLECTION = "AVMaterial";
}