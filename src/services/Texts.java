package services;

/**
 * Class contains available texts.
 */
public class Texts {
    public static final String GREETING_ = "Hi! \uD83D\uDE03 This is Innopolis University Library Bot. Using me you can view documents and check out them. " +
            "Before checking out documents you have to provide me with your personal information (in case you did not do that before). " +
            "If you are a librarian you have an opportunity to login as admin. \uD83D\uDE09";

    public static final String VIEW_DOCUMENTS = "Here you can view document lists \uD83D\uDCCB";

    public static final String ALREADY_HAVE_PERSONAL_INFO = "We already have your personal information☑️";

    public static final String MAIN_MENU = "Main Menu \uD83D\uDCCC";

    public static final String INPUT_NAME = "1⃣ Send me your name, please.";

    public static final String INPUT_SURNAME = "2⃣ Now send me your surname";

    public static final String INPUT_EMAIL = "6⃣ Send me your email address";

    public static final String INPUT_PHONENUMBER = "4⃣ Send me your phone number";

    public static final String INPUT_ADDRESS = "5⃣ Also, send me your physical address";

    public static final String ASK_FACULTY = "3⃣ Are you a faculty or a student?";

    public static final String INCORRECT_EMAIL = "\uD83D\uDE25 You provided incorrect email. Please, send me your email in format n.surname@innopolis.ru.";

    public static final String INCORRECT_PASSWORD = "☹️ This is wrong password. Please, check it and try to resend it.";

    public static final String SUBJECT_OF_EMAIL = "Email Verification";

    public static final String TEXT_OF_EMAIL = "This is a verification letter.<br><br>" +
            "You are currently signing up to InnoLibBot.<br>Send this activation password to the bot:<br><br><strong>%s</strong><br><br>" +
            "If you did not try to register your account, delete this email." +
            "<br><br>Please do not respond to this email.";

    public static final String VERIFY_EMAIL = "7⃣ We send you an email for verification. \uD83D\uDCE9 Please, send here the password that we have sent.";

    public static final String REGISTRATION_DONE = "✅ Thank you for signing up. Now you can check out documents.";

    public static final String CHECK_OUT = "Check out";

    public static final String DID_NOT_PROVIDE_PERSONAL_DATA = "You did not provide your personal data. Provide it in Main Menu! \uD83D\uDCD2";

    public static final String LIBRARIAN_CANNOT_CHECK_OUT = "You are a librarian. \uD83D\uDC74\uD83D\uDC75 You cannot check out a document.";

    public static final String NO_COPIES_AVAILABLE = "There are no copies available at the moment. \uD83D\uDE14 Try to check out later.";

    public static final String CHECKED_OUT_DOCUMENT_FORMAT = "You checked out a document: %s. \uD83C\uDF89 Deadline for return is %s. You can get your document from 9 am till 5 pm on workdays. \uD83D\uDD52";

    public static final String DO_NOT_HAVE_ACCESS = "You do not have access to Librarian System! ⛔️";

    public static final String LIBRARIAN_SYSTEM = "You are in Librarian System \uD83D\uDDA5";

    public static final String REQUEST_RETURN = "Request return";

    public static final String CONFIRM_RETURN = "Confirm return";

    public static final String RETURN_DOC_REQUEST = "Hi! \uD83D\uDE42 Your current checkout for a document '%s' is coming up to deadline. Please, return the document to the library as soon as you can. " +
            "The deadline for this document is %s❗ ️";

    public static final String REQUEST_SENT = "Request is sent to the patron. ✅";

    public static final String RETURN_CONFIRMED_FOR_PATRON = "You returned a document '%s'. Your return was successfully confirmed! ✅";

    public static final String RETURN_CONFIRMED_FOR_LIBRARIAN = "You confirmed a return of the document '%s'. ✅";

    public static final String NO_CHECKOUTS = "No checkouts found! \uD83D\uDDD1";

    public static final String PERSONAL_INFO_HEADER = "<strong>YOUR PERSONAL DATA:</strong>\n";

    public static final String GO_LEFT = "<";

    public static final String GO_RIGHT = ">";
}
