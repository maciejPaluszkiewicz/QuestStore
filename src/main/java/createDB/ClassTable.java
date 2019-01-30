package createDB;

import dao.DataBaseConnector;

public class ClassTable {
    private DataBaseConnector dbConnector = new DataBaseConnector();

    public ClassTable() {
        this.dbConnector.connect();
        creatingClassTable();
    }

    public void creatingClassTable() {
        String querry = "CREATE TABLE IF NOT EXISTS class(" +
                "id_class SERIAL PRIMARY KEY," +
                "class_name text NOT NULL); ";
        createClass(querry);
    }

    public void createClass(String querry) {
        String sql = querry;
        this.dbConnector.updateQuery(sql);
    }
}
