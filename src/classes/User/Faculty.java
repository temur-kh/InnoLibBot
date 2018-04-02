package classes.User;

/**
 * This class is extended from class "Patron", to know if user is Faculty
 */
public class Faculty extends Patron {

    public Faculty(Long id, String name, String surname, Status status, String email, String phoneNumber, String address) {
        super(id, name, surname, status, email, phoneNumber, address);
    }
}
