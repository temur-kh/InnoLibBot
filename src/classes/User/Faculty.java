package classes.User;

public class Faculty extends Patron {

    public Faculty(Long id, String name, String surname, String email, String phoneNumber, String address) {
        super(id, name, surname, email, phoneNumber, address);
    }

    public Faculty(long id, String name, String surname, String email, String phoneNumber) {
        super(id, name, surname, email, phoneNumber);
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
