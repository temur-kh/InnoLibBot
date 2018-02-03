package classes.User;

import services.Constants;

public class User {

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
    public User(Long id, String name, String surname, String email, String phoneNumber)
    {
        new User(id, name, surname, email, phoneNumber, Constants.EMPTY_LINE);
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

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
