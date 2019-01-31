package view;

import model.Mentor;
import model.Student;
import model.Wallet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class View {

    public void print(String text) {
        System.out.println(text);
    }

    public String input() {
        String input = "";
        Scanner sc = new Scanner(System.in);
        input = sc.nextLine();
        return input;
    }

    public void printResultSet(ResultSet rs) {
        try {
            int columnAmount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnAmount; i++) {
                    System.out.print(rs.getObject(i) + " ");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void printListOfResultSet(List<ResultSet> resultSetList) {
        for (ResultSet rs : resultSetList) {
            printResultSet(rs);
        }
    }

    public void printStudent(Student student) {
        System.out.println("id: " + student.getId() + " | " + "name: " + student.getFirst_name() + " | " + "last name: " +
                student.getLast_name() + " | " + "class: " + student.getClas() + " | " + "email: " + student.getEmail() + " | " + "phone number: " +
                student.getPhone_number() + " | " + "cool coins: " + student.getCoolCoins() + " | " + "level: " + student.getLevel() + " | ");
    }

    public void printStudentList(List<Student> studentList) {
        for (Student student : studentList) {
            printStudent(student);
        }
    }

    public void printMentorList(List<Mentor> mentorList){
        for (Mentor mentor : mentorList){
            printMentor(mentor);
        }
    }

    public void printMentor(Mentor mentor) {
        System.out.println(mentor.getId() + " " + mentor.getFirst_name() + " " + mentor.getLast_name() + " " + mentor.getEmail() + " " + mentor.getPhoneNumber());
    }

    public void printWallet(Wallet wallet) {
        System.out.println(wallet.getCoolCoinsAmount() + " " + wallet.getArtifactName() + " " + wallet.getQuantity());
    }

}
