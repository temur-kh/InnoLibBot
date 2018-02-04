package updater;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import services.Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for dealing with personal data of user.
 * TODO implement a questionnaire for user to input his personal data.
 */
public class PersonalDataSystem {

    //commands belonging to this class
    public static ArrayList<String> commandsList = new ArrayList<String>(
            Arrays.asList(Commands.INPUT_NAME,Commands.INPUT_SURNAME,Commands.INPUT_EMAIL,Commands.INPUT_PHONENUMBER,Commands.INPUT_ADDRESS,Commands.INPUT_IS_FACULTY));

    //does command belong to this class commands?
    public static boolean belongTo(String command) {
        for(String line: commandsList) {
            if(line.equals(command))
                return true;
        }
        return false;
    }

    //return Personal Data Input keyboard markup
    //TODO update keyboard and system at all.
    public static ReplyKeyboardMarkup inputPersonalDataMenu() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup().setResizeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add(Commands.INPUT_NAME);
        keyboard.add(row);

        row = new KeyboardRow();
        row.add(Commands.INPUT_SURNAME);
        keyboard.add(row);

        row = new KeyboardRow();
        row.add(Commands.INPUT_EMAIL);
        keyboard.add(row);

        row = new KeyboardRow();
        row.add(Commands.INPUT_PHONENUMBER);
        keyboard.add(row);

        row = new KeyboardRow();
        row.add(Commands.INPUT_ADDRESS);
        keyboard.add(row);

        row = new KeyboardRow();
        row.add(Commands.INPUT_IS_FACULTY);
        keyboard.add(row);

        row = new KeyboardRow();
        row.add(Commands.BACK_TO_MENU);
        keyboard.add(row);

        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }
}
