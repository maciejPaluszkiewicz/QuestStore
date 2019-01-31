package dao;
import model.Student;
import model.Wallet;
import view.View;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

public class MentorDAOSQL implements MentorDAO {
    DataBaseConnector dbConnector = new DataBaseConnector();

    @Override
    public void createStudent(String name, String last_name, String _class, String email, String phone_number, int coolCoins, int lvl, String password) {
        String sql = "INSERT INTO students (first_name, last_name, class, email, phone_number, cool_coins, level) " +
                "VALUES(?,?,?,?,?,?,?,?);";
        try {
            dbConnector.connect();
            PreparedStatement stmt = dbConnector.getConnection().prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, last_name);
            stmt.setString(3, _class);
            stmt.setString(4, email);
            stmt.setString(5, phone_number);
            stmt.setInt(6, coolCoins);
            stmt.setInt(7, lvl);
            stmt.setString(8, password);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addQuest(String quest_name, int quest_value, int category) {
        String sql = "INSERT INTO students (quest_name, quest_value, category) " +
                "VALUES(?,?,?);";
        try {
            dbConnector.connect();
            PreparedStatement stmt = dbConnector.getConnection().prepareStatement(sql);
            stmt.setString(1, quest_name);
            stmt.setInt(2, quest_value);
            stmt.setInt(3, category);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addQuestCategory(String name, int bonus) {
        String sql = "INSERT INTO category (category_name, cool_coin_bonus) " +
                "VALUES(?,?);";
        try {
            dbConnector.connect();
            PreparedStatement stmt = dbConnector.getConnection().prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, bonus);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void addArtifactToShop(String artifact_name, int artifact_value, int artifact_quantity) {
        PreparedStatement stmt;
        try {
            if (dbConnector.query("SELECT * FROM artifacts WHERE artifact_name LIKE " + " '" + artifact_name + "';").next() == false) {
                insertIntoArtifactToshop(artifact_name, artifact_value, artifact_quantity);
            } else {
                updateArtifactToShop(artifact_name, artifact_quantity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> showStudents() {
        List<Student> studentList = new ArrayList<>();
        ResultSet rs = dbConnector.query("SELECT * FROM students");
        try {
            while (rs.next()) {
                Student student = new Student(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8));
                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    private void updateArtifactToShop(String artifact_name, int artifact_quantity) throws SQLException {
        PreparedStatement stmt;
        String sql = "UPDATE artifacts SET quantity = quantity + ?" +
                "WHERE artifact_name = ?;";
        dbConnector.connect();
        stmt = dbConnector.getConnection().prepareStatement(sql);
        stmt.setInt(1, artifact_quantity);
        stmt.setString(2, artifact_name);
        stmt.executeUpdate();
    }

    private void insertIntoArtifactToshop(String artifact_name, int artifact_value, int artifact_quantity) throws SQLException {
        PreparedStatement stmt;
        String sql = "INSERT INTO artifacts (artifact_name, artifact_cost, quantity) " +
                "VALUES (?,?,?);";
        dbConnector.connect();
        stmt = dbConnector.getConnection().prepareStatement(sql);
        stmt.setString(1, artifact_name);
        stmt.setInt(2, artifact_value);
        stmt.setInt(3, artifact_quantity);
        stmt.executeUpdate();
    }

    @Override
    public void editQuest(int id, String quest_name, int quest_value, int quest_category) {
        String sql = "UPDATE artifacts SET quest_name = ?, quest_value = ?, quest_category = ?" +
                "WHERE quest_id = ?;";
        dbConnector.connect();
        try {
            PreparedStatement stmt = dbConnector.getConnection().prepareStatement(sql);
            stmt.setString(1, quest_name);
            stmt.setInt(2, quest_value);
            stmt.setInt(3, quest_category);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public void archiveQuest(int id_student, int id_quest, String status) {
        String sql = "UPDATE students_quests SET quest_status = ?" +
                "WHERE id_quest= ? AND id_student = ?;";
        dbConnector.connect();
        try {
            PreparedStatement stmt = dbConnector.getConnection().prepareStatement(sql);
            stmt.setString(1, status);
            stmt.setInt(2, id_quest);
            stmt.setInt(3, id_student);
            stmt.executeUpdate();
        } catch (SQLException e) {
        }

    }

    @Override
    public Wallet showStudentsWallet(int id) {
        ResultSet rs = dbConnector.query("SELECT cool_coins FROM students WHERE id =" + id);
        ResultSet rs2 = dbConnector.query("SELECT artifact_name, students_artifacts.quantity FROM artifacts RIGHT JOIN students_artifacts ON artifacts.id_artifact = students_artifacts.id_artifact WHERE students_artifacts.id_student = " + id);
        int coolCoins = 0;
        String artifactName = "";
        int quantity = 0;
        coolCoins = getCoolCoins(rs, coolCoins);
        try {
            while (rs2.next()) {
                artifactName = rs2.getString("artifact_name");
                quantity = rs2.getInt("quantity");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Wallet wallet = new Wallet(coolCoins, artifactName, quantity);
        return wallet;

    }


    private int getCoolCoins(ResultSet rs, int coolCoins) {
        try {
            while (rs.next()) {
                coolCoins = rs.getInt("cool_coins");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coolCoins;
    }

//    public static void main(String[] args) {
//        MentorDAOSQL mds = new MentorDAOSQL();
//        View view = new View();
//        mds.createStudent("adam", "maczek", "1b", "anna.naan@buziaczek.pl", "0700990880", 45, 0);
//          mds.addQuest("zrobic_sniadanie",100, 2);
//        mds.addArtifactToShop("skecz", 10, 2);
//        view.printResultSet(mds.showStudents());
//        view.printStudentList(mds.showStudents());
//        view.printResultSet(mds.showStudentsWallet(3).get(0));
//        view.printResultSet(mds.showStudentsWallet(3).get(1));
//        view.printWallet(mds.showStudentsWallet(1));

//    }
}
