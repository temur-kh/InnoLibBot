package services;

import java.time.Duration;

public class Constants {
    public static final String EMPTY_ADDRESS = "";
    public static final Duration BOOK_CHECK_OUT_LIMIT = Duration.ofDays(3 * 7);
    public static final Duration BOOK_CHECK_OUT_LIMIT_FOR_FACULTY = Duration.ofDays(4 * 7);
    public static final Duration BEST_SELLER_CHECK_OUT_LIMIT = Duration.ofDays(2 * 7);
    public static final Duration AVMATERIAL_CHECK_OUT_LIMIT = Duration.ofDays(2 * 7);
    public static final Duration JOURNAL_CHECK_OUT_LIMIT = Duration.ofDays(2 * 7);
}