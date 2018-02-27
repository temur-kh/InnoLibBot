package updater;

import database.LibrarianDB;
import database.PatronDB;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import services.Commands;
import services.Texts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for dealing with personal data of user.
 * TODO implement a questionnaire for user to input his personal data.
 */
public class PersonalDataSystem {

    //commands belonging to this class
    public static ArrayList<Integer> commandsList = new ArrayList<Integer>(
            Arrays.asList(Commands.INPUT_NAME_STATE, Commands.INPUT_SURNAME_STATE, Commands.INPUT_EMAIL_STATE, Commands.INPUT_PHONENUMBER_STATE, Commands.INPUT_ADDRESS_STATE, Commands.INPUT_IS_FACULTY_STATE));

    //does command belong to this class commands?
    public static boolean belongTo(String command) {
        for (Integer line : commandsList) {
            if (line.equals(command))
                return true;
        }
        return false;
    }

    public static SendMessage handle(Update update) {
        return null;
    }

    //return personal data view and then switch to PersonalDataSystem
    public static SendMessage personalDataView(Update update) {
        SendMessage msg = new SendMessage().setChatId(update.getMessage().getChatId());
        if (PatronDB.getPatron(update.getMessage().getChatId()) == null && LibrarianDB.getLibrarian(update.getMessage().getChatId()) == null) {
            msg.setText(Texts.GIVE_PERSONAL_DATA);

        } else {
            msg.setText(Texts.ALREADY_HAVE_PERSONAL_INFO).setReplyMarkup(GUISystem.simpleMenu());
        }
        return msg;
    }
}