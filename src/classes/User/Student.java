package classes.User;

public class Student extends Patron {

    public Student(Long id, String name, String surname, String email, String phoneNumber, String address) {
        super(id, name, surname, email, phoneNumber, address);
    }

    public Student(long id, String name, String surname, String email, String phoneNumber) {
        super(id, name, surname, email, phoneNumber);
    }

    @Override
    public boolean isStudent() {
        return true;
    }

    @Override
    public boolean isFaculty() {
        return false;
    }
}
