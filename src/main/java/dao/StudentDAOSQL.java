package dao;

import view.View;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOSQL implements StudentDAO {
    private DataBaseConnector dataBaseConnector = new DataBaseConnector();
    private View view = new View();

    @Override
    public List<ResultSet> showWallet(int id) {
        List<ResultSet> resultSetList = new ArrayList<>();
        ResultSet rs = dataBaseConnector.query("SELECT cool_coins FROM students WHERE id =" + id);
        ResultSet rs2 = dataBaseConnector.query("SELECT artifact_name FROM artifacts RIGHT JOIN students_artifacts ON artifacts.id_artifact = students_artifacts.id_artifact WHERE students_artifacts.id_student = " + id);
        resultSetList.add(rs);
        resultSetList.add(rs2);
        return resultSetList;
    }

    @Override
    public void buyArtifacts(int howMuch, int id_artifact, int id_student) {
//        student 1 chce kupic 2 artifact ilosc 5, wiec w artifacts updateuje quantity o minus 5 dla artifactu 2
//        student 1 traci cool_coinsy o ilosc artifact value * ilosc kupionych,
//        students artifact table ma insertowane lub updateowane ze student 1 ma 5 artifactow nr 2
        String sql = "UPDATE artifacts SET quantity = quantity-? WHERE id_artifact = ?;" +
                "UPDATE students SET cool_coins = cool_coins - ((SELECT artifact_cost FROM artifacts WHERE id_artifact = ?) * ?) WHERE students.id = ?;";
        String sql2 = "";
        updateQuantityAndCoolCoins(howMuch, id_artifact, id_student, sql, sql2);

        try {
            if (dataBaseConnector.query("SELECT * FROM students_artifacts WHERE id_artifact = " + id_artifact + " AND id_student = " + id_student + ";").next() == false) {
                insertToStudentsArtifactsIfNotExists(howMuch, id_artifact, id_student);
            } else {
                updateStudentsArtifacts(howMuch, id_artifact, id_student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void updateStudentsArtifacts(int howMuch, int id_artifact, int id_student) throws SQLException {
        String sql2;
        sql2 = "UPDATE students_artifacts SET quantity = quantity + ? WHERE id_artifact = " + id_artifact + " AND id_student = " + id_student + ";";
        dataBaseConnector.connect();
        PreparedStatement stmt = dataBaseConnector.getConnection().prepareStatement(sql2);
        stmt.setInt(1, howMuch);
        stmt.executeUpdate();
    }

    private void insertToStudentsArtifactsIfNotExists(int howMuch, int id_artifact, int id_student) throws SQLException {
        String sql2;
        sql2 = "INSERT INTO students_artifacts VALUES (?,?,?);";
        dataBaseConnector.connect();
        PreparedStatement stmt = dataBaseConnector.getConnection().prepareStatement(sql2);
        stmt.setInt(1, id_artifact);
        stmt.setInt(2, id_student);
        stmt.setInt(3, howMuch);
        stmt.executeUpdate();
    }

    private void updateQuantityAndCoolCoins(int howMuch, int id_artifact, int id_student, String sql, String sql2) {
        try {
            dataBaseConnector.connect();
            PreparedStatement stmt = dataBaseConnector.getConnection().prepareStatement(sql);
            PreparedStatement stmt2 = dataBaseConnector.getConnection().prepareStatement(sql2);
            stmt.setInt(1, howMuch);
            stmt.setInt(2, id_artifact);
            stmt.setInt(3, id_artifact);
            stmt.setInt(4, howMuch);
            stmt.setInt(5, id_student);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void buyArtifactTogether() {

    }

    @Override
    public ResultSet showMyLevel(int id) {
        ResultSet rs = dataBaseConnector.query("SELECT level FROM students WHERE id =" + id);
        return rs;
    }

//    public static void main(String[] args) {
//        StudentDAOSQL studentDAOSQL = new StudentDAOSQL();
//        View view = new View();
//        view.printListOfResultSet(studentDAOSQL.showWallet(1));
//        view.printResultSet(studentDAOSQL.showMyLevel(1));
//        studentDAOSQL.buyArtifacts(3, 2, 4);
//
//    }
}
