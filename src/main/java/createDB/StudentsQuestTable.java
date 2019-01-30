package createDB;

import dao.DataBaseConnector;

public class StudentsQuestTable {
    private DataBaseConnector dbConnector = new DataBaseConnector();

    public StudentsQuestTable() {
        this.dbConnector.connect();
        creatingStudentsQuestTable();
    }

    public void creatingStudentsQuestTable() {
        String querry = "CREATE TABLE IF NOT EXISTS students_quests(" +
                "id_student INTEGER, " +
                "id_quest INTEGER, " +
                "quest_status TEXT); ";
        createStudentsQuest(querry);
    }

    public void createStudentsQuest(String querry) {
        String sql = querry;
        this.dbConnector.updateQuery(sql);
    }
}