package classes.User;

public class VisitingProfessor extends Patron {
    public VisitingProfessor(long id, String name, String surname, String email, String phoneNumber, String address) {
        super(id, name, surname, Status.VisitingProfessor, email, phoneNumber, address);
    }
}
