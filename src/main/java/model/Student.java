package model;

public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private String clas;
    private String email;
    private String phone_number;
    int coolCoins;
    int level;

    public Student(int id, String firstName, String lastName, String clas, String email, String phone_number, int coolCoins, int level) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.clas = clas;
        this.email = email;
        this.phone_number = phone_number;
        this.coolCoins = coolCoins;
        this.level = level;
    }

    public int getCoolCoins() {
        return coolCoins;
    }

    public int getLevel() {
        return level;
    }

    public String getClas() {
        return clas;
    }

    public int getId() {
        return id;
    }

    public String getfirstName() {
        return firstName;
    }

    public String getlastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_number() {
        return phone_number;
    }
}
