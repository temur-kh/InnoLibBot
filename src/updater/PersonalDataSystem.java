package updater;

import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import services.Commands;

import java.util.ArrayList;
import java.util.List;

public class PersonalDataSystem {
    public static ReplyKeyboardMarkup inputPersonalDataView() {
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