package classes.User;

import services.Constants;

/**
 * This class is extended from class "Patron", to know if user is Faculty or Student
 * (with boolean function isFaculty, isStudent)
 */
public class Faculty extends Patron {

    public Faculty(Long id, String name, String surname, Status status, String email, String phoneNumber, String address) {
        super(id, name, surname, status, email, phoneNumber, address);
    }

    @Override
    public String getInfo() {
        String info = super.getInfo();
        info += "<strong>STATUS:</strong> " + getStatus().name() + " " + getStatus() + Constants.NEW_LINE;
        return info;
    }
}
