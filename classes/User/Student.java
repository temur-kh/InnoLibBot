package classes.User;

/**
 * This class is extended from class "Patron", to know if user is Student
 */
public class Student extends Patron {

    public Student(Long id, String name, String surname, String email, String phoneNumber, String address) {
        super(id, name, surname, Status.Student, email, phoneNumber, address);
    }
}
