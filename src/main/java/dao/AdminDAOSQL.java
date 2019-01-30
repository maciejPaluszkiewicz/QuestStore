package dao;

import model.Mentor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAOSQL implements AdminDAO {
    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    @Override
    public void createMentor(String first_name, String last_name, String email, String phone_number, String password) {
        String sql = "INSERT INTO mentors (first_name, last_name, email, phone_number,password) " +
                "VALUES(?,?,?,?,?);";
        try {
            dataBaseConnector.connect();
            PreparedStatement stmt = dataBaseConnector.getConnection().prepareStatement(sql);
            stmt.setString(1, first_name);
            stmt.setString(2, last_name);
            stmt.setString(3, email);
            stmt.setString(4, phone_number);
            stmt.setString(5, password);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void createClass(String class_name) {
        String sql = "INSERT INTO class (class_name)" +
                "VALUES(?);";
        try {
            dataBaseConnector.connect();
            PreparedStatement stmt = dataBaseConnector.getConnection().prepareStatement(sql);
            stmt.setString(1, class_name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void editMentor(String columnToChange, String update, int id) {
        String sql = "UPDATE mentors SET " + columnToChange + " = ? WHERE id = ?;";
        try {
            dataBaseConnector.connect();
            PreparedStatement stmt = dataBaseConnector.getConnection().prepareStatement(sql);
            stmt.setString(1, update);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Mentor showMentorById(int idMentor) {
//        return dataBaseConnector.query("SELECT * FROM mentors WHERE id =" + id+";");
        ResultSet rs = dataBaseConnector.query("SELECT * FROM mentors WHERE id =" + idMentor + ";");
        Mentor mentor = null;
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                String email = rs.getString("email");
                String phone_number = rs.getString("phone_number");
                mentor = new Mentor(id, first_name, last_name, email, phone_number);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mentor;
    }

    @Override
    public void createLevelOfExperience() {

    }

//    public static void main(String[] args) {
//        view view = new view();
//        AdminDAOSQL adminDAOSQL = new AdminDAOSQL();
//        adminDAOSQL.createMentor("adam", "maczek", "asd@assa.pl", "0700990880l");
//        adminDAOSQL.createClass("klsaas");
//        view.printMentor(adminDAOSQL.showMentorById(1));
//        adminDAOSQL.editMentor("email", "piotrek3", 2);
//    }
}
