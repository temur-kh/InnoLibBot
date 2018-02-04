package classes.User;

import services.Constants;

/**
 * Main class from which classes "Patron" and "Librarian" are extended.
 * From very beginning we will add the only librarian(admin) to this bot,
 * and he will be able to add more librarians(admins)
 */
public class User {
    //all these information will be held in database of users
    private long id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String address;

    public User(long id, String name, String surname, String email, String phoneNumber, String address)
    {
        setId(id);
        setName(name);
        setSurname(surname);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setAddress(address);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
