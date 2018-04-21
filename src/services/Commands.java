package services;

import classes.User.Status;

/**
 * Class of available commands.
 */
public class Commands {
    public static final String START_ = "/start";
    public static final String MENU_ = "/menu";
    public static final String BACK_TO_MENU = "Back to Menu \uD83D\uDD19";

    public static final String VIEW_DOCUMENTS = "View Documents \uD83D\uDDC3";
    public static final String PERSONAL_INFORMATION = "Personal Information \uD83D\uDCDD";
    public static final String LOGIN_AS_LIBRARIAN = "Sign in as Librarian \uD83D\uDD10";

    public static final String VIEW_BOOKS = "View Books \uD83D\uDCDA";
    public static final String VIEW_JOURNALS = "View Journals \uD83D\uDCF0";
    public static final String VIEW_AVMATERIALS = "View Audio/Video Materials \uD83D\uDCBE";
    public static final String SEARCH_DOCUMENT = "Search document";

    public static final int INPUT_NAME_STATE = 1;
    public static final int INPUT_SURNAME_STATE = 2;
    public static final int INPUT_STATUS_STATE = 3;
    public static final int INPUT_PHONENUMBER_STATE = 4;
    public static final int INPUT_ADDRESS_STATE = 5;
    public static final int INPUT_EMAIL_STATE = 6;
    public static final int VERIFICATION_STATE = 7;
    public static final String IS_INSTRUCTOR = Status.Instructor.name();
    public static final String IS_TA = Status.TA.name();
    public static final String IS_PROFESSOR = Status.Professor.name();
    public static final String IS_STUDENT = Status.Student.name();
    public static final String IS_LIBRARIAN = "Librarian \uD83D\uDC74\uD83D\uDC75";

    public static final String CHECK_OUT = "check_out";
    public static final String GO_LEFT = "go_left";
    public static final String GO_RIGHT = "go_right";

    public static final String CHECKOUTS_LIST = "Checkouts List \uD83D\uDDC2";
    public static final String REQUEST_RETURN = "request_return";
    public static final String CONFIRM_RETURN = "confirm_return";

    public static final String MY_CHECKOUTS = "MyCheckouts";
    public static final String MY_OVERDUE_CHECKOUTS  = "MyOverdueCheckouts";
    public static final String PROFILE = "My Profile";
    public static final String RETURN_DOCUMENT = "return_doc";
    public static final String RENEW_DOCUMENT = "renew_doc";
    public static final String PAY_FOR_DOCUMENT = "pay_doc";
}
