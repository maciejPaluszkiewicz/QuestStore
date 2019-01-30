package model;

public class Mentor {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String phoneNumber;

    public Mentor(int id, String first_name, String last_name, String email, String phoneNumber) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
