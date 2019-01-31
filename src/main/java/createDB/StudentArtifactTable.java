package createDB;

import dao.DataBaseConnector;

public class StudentArtifactTable {
    private DataBaseConnector dbConnector = new DataBaseConnector();

    public StudentArtifactTable() {
        this.dbConnector.connect();
        createStudentArtifactTable();
    }

    public void createStudentArtifactTable() {
        String sql = "CREATE TABLE IF NOT EXISTS students_artifacts (" +
                "id_artifact INTEGER NOT NULL, " +
                "id_student INTEGER NOT NULL, " +
                "quantity INTEGER" +
                ");";
        DataBaseConnector dataBaseConnector = new DataBaseConnector();
        dataBaseConnector.updateQuery(sql);
    }
}
