package dao;

import model.Mentor;

public interface AdminDAO {
    void createMentor(String first_name, String last_name, String email, String phone_number, String password);

    void createClass(String class_name);

    void editMentor(String columnToChange, String update, int id);

    Mentor showMentorById(int id);

    void createLevelOfExperience();
}
