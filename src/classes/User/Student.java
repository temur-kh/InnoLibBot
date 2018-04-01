package classes.User;

import services.Commands;
import services.Constants;

/**
 * This class is extended from class "Patron", to know if user is Faculty or Student
 * (with boolean function isFaculty, isStudent)
 */
public class Student extends Patron {

    public Student(Long id, String name, String surname, String email, String phoneNumber, String address) {
        super(id, name, surname, Status.Student, email, phoneNumber, address);
    }

    @Override
    public String getInfo() {
        String info = super.getInfo();
        info += "<strong>STATUS:</strong> " + Commands.IS_STUDENT + Constants.NEW_LINE;
        return info;
    }
}
