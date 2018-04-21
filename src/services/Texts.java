package services;

/**
 * Class contains available texts.
 */
public class Texts {
    public static final String GREETING_ = "Hi! \uD83D\uDE03 This is Innopolis University Library Bot. Using me you can view documents and check out them. " +
            "Before checking out documents you have to provide me with your personal information (in case you did not do that before). " +
            "If you are a librarian you have an opportunity to login as admin. \uD83D\uDE09";

    public static final String VIEW_DOCUMENTS = "Here you can view document lists \uD83D\uDCCB";

    public static final String VIEW_PROFILE = "Profile System";

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

    public static final String LIBRARIAN_CANNOT_HAVE_CHECKOUTS = "Your a librarian. \uD83D\uDC74\uD83D\uDC75 You cannot have checkouts.";

    public static final String NOT_AVAILABLE = "This book is not available. \uD83D\uDE14";

    public static final String PUT_IN_PRIORITY_QUEUE = "There are no copies available, yet. We've put you in a queue for this document. We will notify you if the document is available.";

    public static final String CHECKED_OUT_DOCUMENT_FORMAT = "You checked out a document: %s. \uD83C\uDF89 Deadline for return is %s. You can get your document from 9 am till 5 pm on workdays. \uD83D\uDD52";

    public static final String DO_NOT_HAVE_ACCESS = "You do not have access to Librarian System! ⛔️";

    public static final String LIBRARIAN_SYSTEM = "You are in Librarian System \uD83D\uDDA5";

    public static final String REQUEST_RETURN = "Request return";

    public static final String CONFIRM_RETURN = "Confirm return";

    public static final String RETURN_DOC_REQUEST = "Hi! \uD83D\uDE42 Your current checkout for a document '%s' is coming up to deadline or the librarian forced a request notification to you. Please, return the document to the library as soon as you can.";

    public static final String REQUEST_SENT = "Request is sent to the patron. ✅";

    public static final String RETURN_CONFIRMED_FOR_PATRON = "You returned a document '%s'. Your return was successfully confirmed! ✅";

    public static final String RETURN_CONFIRMED_FOR_LIBRARIAN = "You confirmed a return of the document '%s'. ✅";

    public static final String RETURN_REQUESTED_BY_PATRON = "The patron %s wants to return the document %s. Deadline for checkout: %s";

    public static final String RETURN_REQUEST_SENT = "You request for returning document is sent to the librarian.";

    public static final String RETURN_DOCUMENT = "Return Document";

    public static final String RENEW_DOCUMENT = "Renew Document";

    public static final String RENEWED_DOCUMENT = "You successfully renewed the document!";

    public static final String COULD_NOT_RENEW = "You cannot renew this document.";

    public static final String PATRON_RENEWED_DOCUMENT = "Patron %s renewed a document %s.";

    public static final String PAY_FOR_DOCUMENT = "Pay for Document";

    public static final String INVOICE_DESCRIPTION = "You did not return document %s on time. So, you have to pay for %s days. " +
            "Please, pay the fine as soon as possible, otherwise the fine will be increasing everyday. Pay load: %s RUB.";

    public static final String NO_CHECKOUTS = "No checkouts found! \uD83D\uDDD1";

    public static final String PERSONAL_INFO_HEADER = "<strong>YOUR PERSONAL DATA:</strong>\n";

    public static final String NOTIFICATION_RECEIVED = "We would like to notify you that the document %s is available now for you. You can get it in the library within a day.";

    public static final String NOTIFICATION_SENT = "A notification was sent! Patron: %s. Document: %s.";

    public static final String UNAVAILABLE_DUE_TO_OUTSTANDING_REQUEST = "We are sorry but the document '%s' is unavailable due to an outstanding request from the librarian.";

    public static final String EXPIRED_NOTIFICATION = "You time to take the document %s expired. We removed you from the queue.";

    public static final String INPUT_SEARCHING_QUERY = "Search for the documents";

    public static final String GO_LEFT = "<";

    public static final String GO_RIGHT = ">";
}
