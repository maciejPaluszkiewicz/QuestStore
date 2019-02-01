package dao;

import model.Mentormod;

import java.util.List;

public interface AdminDAO {

    String autoriseAdmin(String login,String password);

    void createMentor(String first_name, String last_name, String email, String phone_number, String password);

    void createClass(String class_name);

    void editMentor(String columnToChange, String update, int id);

    List<Mentormod> getMentors();

    Mentormod showMentorById(int id);

    void createLevelOfExperience();

    void removeMentorById(String id);
}
