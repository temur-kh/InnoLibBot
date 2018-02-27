package classes.User;
/**
 * This class is extended from class "Patron", to know if user is Faculty or Student
 * (with boolean function isFaculty, isStudent)
 */
public class Student extends Patron {

    public Student(Long id, String name, String surname, String email, String phoneNumber, String address) {
        super(id, name, surname, email, phoneNumber, address);
        setFaculty(false);
    }
}
