package updatehandler;

import classes.User.Patron;
import classes.User.User;
import com.mongodb.BasicDBObject;
import database.LibrarianDB;
import database.PatronDB;
import database.RegistrationStateDB;
import org.apache.commons.lang3.RandomStringUtils;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Contact;
import org.telegram.telegrambots.api.objects.Location;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import services.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for dealing with personal data of user.
 */
public class UserProfileSystem {

    //commands belonging to this class
    public static final ArrayList<String> commandsList = new ArrayList<>(
            Arrays.asList(Commands.IS_INSTRUCTOR, Commands.IS_PROFESSOR, Commands.IS_TA, Commands.IS_STUDENT));

    //does command belong to this class commands?
    public static boolean belongTo(String command) {
        for (String line : commandsList) {
            if (line.equals(command))
                return true;
        }
        return false;
    }

    private static final Pattern pattern = Pattern.compile("^[a-z]{1,2}\\.[a-z]+@innopolis+\\.ru$", Pattern.CASE_INSENSITIVE);

    public static SendMessage handle(Update update) {
        long userId = update.getMessage().getChatId();
        String text = update.getMessage().getText();
        Message message = update.getMessage();
        int state = (int) RegistrationStateDB.getState(userId).get("state");
        SendMessage msg = new SendMessage().setChatId(userId);

        if (message.isReply() || belongTo(text) || message.hasLocation() || message.getContact() != null) {
            BasicDBObject object = new BasicDBObject().append("_id", userId);
            switch (state) {
                case Commands.INPUT_NAME_STATE:
                    object.append("name", text);
                    break;
                case Commands.INPUT_SURNAME_STATE:
                    object.append("surname", text);
                    break;
                case Commands.INPUT_STATUS_STATE:
                    object.append("status", text);
                    break;
                case Commands.INPUT_PHONENUMBER_STATE:
                    if (message.hasText()) {
                        object.append("phone_number", text);
                    } else {
                        Contact contact = message.getContact();
                        object.append("phone_number", contact.getPhoneNumber());
                    }
                    break;
                case Commands.INPUT_ADDRESS_STATE:
                    if (message.hasText()) {
                        object.append("address", text);
                    } else if (message.hasLocation()) {
                        Location location = message.getLocation();
                        object.append("address", LocationDecoder.getAddress(location.getLatitude(), location.getLongitude()));
                    }
                    break;
                case Commands.INPUT_EMAIL_STATE:
                    if (text == null || !isEmailCorrect(text)) {
                        return msg.setText(Texts.INCORRECT_EMAIL).setReplyMarkup(new ForceReplyKeyboard().setSelective(true));
                    }
                    object.append("email", text);
                    break;
                case Commands.VERIFICATION_STATE:
                    String securePass = (String) RegistrationStateDB.getValue(userId, "secure_pass");
                    if (!text.equals(securePass)) {
                        return msg.setText(Texts.INCORRECT_PASSWORD).setReplyMarkup(new ForceReplyKeyboard().setSelective(true));
                    }
            }
            state++;
            RegistrationStateDB.modifyState(object, state);
        }

        switch (state) {
            case Commands.INPUT_NAME_STATE:
                msg.setText(Texts.INPUT_NAME);
                break;
            case Commands.INPUT_SURNAME_STATE:
                msg.setText(Texts.INPUT_SURNAME);
                break;
            case Commands.INPUT_STATUS_STATE:
                msg.setText(Texts.ASK_FACULTY);
                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup().setResizeKeyboard(true);
                ArrayList<KeyboardRow> keyboard = new ArrayList<>();
                KeyboardRow row = new KeyboardRow();
                row.add(Commands.IS_INSTRUCTOR);
                row.add(Commands.IS_TA);
                keyboard.add(row);
                row = new KeyboardRow();
                row.add(Commands.IS_PROFESSOR);
                row.add(Commands.IS_STUDENT);
                keyboard.add(row);
                keyboardMarkup.setKeyboard(keyboard);
                msg.setReplyMarkup(keyboardMarkup);
                break;
            case Commands.INPUT_PHONENUMBER_STATE:
                msg.setText(Texts.INPUT_PHONENUMBER);
                ReplyKeyboardMarkup keyboardMarkup2 = new ReplyKeyboardMarkup().setResizeKeyboard(true);
                ArrayList<KeyboardRow> keyboard2 = new ArrayList<>();
                KeyboardRow row2 = new KeyboardRow();
                row2.add(new KeyboardButton("Share contact").setRequestContact(true));
                keyboard2.add(row2);
                keyboardMarkup2.setKeyboard(keyboard2);
                msg.setReplyMarkup(keyboardMarkup2);
                break;
            case Commands.INPUT_ADDRESS_STATE:
                msg.setText(Texts.INPUT_ADDRESS);
                ReplyKeyboardMarkup keyboardMarkup3 = new ReplyKeyboardMarkup().setResizeKeyboard(true);
                ArrayList<KeyboardRow> keyboard3 = new ArrayList<>();
                KeyboardRow row3 = new KeyboardRow();
                row3.add(new KeyboardButton("Share location").setRequestLocation(true));
                keyboard3.add(row3);
                keyboardMarkup3.setKeyboard(keyboard3);
                msg.setReplyMarkup(keyboardMarkup3);
                break;
            case Commands.INPUT_EMAIL_STATE:
                msg.setText(Texts.INPUT_EMAIL);
                break;
            case Commands.VERIFICATION_STATE:
                msg.setText(Texts.VERIFY_EMAIL);
                String securePass = getSecurePassword();
                SendMail.sendMail((String) RegistrationStateDB.getValue(userId, "email"), Texts.SUBJECT_OF_EMAIL,
                        String.format(Texts.TEXT_OF_EMAIL, securePass));
                System.out.println("Sent!");
                RegistrationStateDB.modifyState(new BasicDBObject("_id", userId).append("secure_pass", securePass), state);
                break;
            default:
                Patron patron = PatronDB.toObject(RegistrationStateDB.getState(userId));
                RegistrationStateDB.removeState(userId);
                patron.register();
                msg.setText(Texts.REGISTRATION_DONE);
                msg.setReplyMarkup(GUISystem.simpleMenu());
        }
        if (state != Commands.INPUT_STATUS_STATE && state != Commands.INPUT_PHONENUMBER_STATE
                && state != Commands.INPUT_ADDRESS_STATE && state <= Commands.VERIFICATION_STATE) {
            ForceReplyKeyboard reply = new ForceReplyKeyboard();
            msg.setReplyMarkup(reply);
        }
        return msg;
    }

    public static boolean isEmailCorrect(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    public static String getSecurePassword() {
        return RandomStringUtils.randomAlphanumeric(Constants.SECURE_PASSWORD_LENGTH);
    }

    //return personal data view and then switch to UserProfileSystem
    public static SendMessage personalDataView(Update update) {
        long userId = update.getMessage().getChatId();
        SendMessage msg = new SendMessage().setChatId(userId);
        BasicDBObject state = RegistrationStateDB.getState(userId);
        if (state != null) {
            msg = handle(update);
        } else if (PatronDB.getPatron(userId) == null && LibrarianDB.getLibrarian(userId) == null) {
            RegistrationStateDB.insertNewState(userId);
            msg = handle(update);
        } else {
            User user = PatronDB.getPatron(userId);
            if (user == null)
                user = LibrarianDB.getLibrarian(userId);
            msg.setText(Texts.PERSONAL_INFO_HEADER + user.getInfo()).setParseMode("html").setReplyMarkup(GUISystem.simpleMenu());
        }
        return msg;
    }
}