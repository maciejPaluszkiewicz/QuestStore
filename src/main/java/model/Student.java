package model;

public class Student {
    private int id;
    private String first_name;
    private String last_name;
    private String clas;
    private String email;
    private String phone_number;
    int coolCoins;
    int level;

    public Student(int id, String first_name, String last_name, String clas, String email, String phone_number, int coolCoins, int level) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
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

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_number() {
        return phone_number;
    }
}
