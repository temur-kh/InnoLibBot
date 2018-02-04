package classes.User;

/**
 * This class is extended from class "Patron", to know if user is Faculty or Student
 * (with boolean function isFaculty, isStudent)
 */
public class Faculty extends Patron {

    public Faculty(Long id, String name, String surname, String email, String phoneNumber, String address) {
        super(id, name, surname, email, phoneNumber, address);
    }

    @Override
    public boolean isFaculty() {
        return true;
    }

    @Override
    public boolean isStudent() {
        return false;
    }
}
