package createDB;

import dao.DataBaseConnector;

public class MentorTable {
    public MentorTable() {
        createMentorTable();
    }

    public void createMentorTable() {
        String sql = "CREATE TABLE IF NOT EXISTS mentors (" +
                "ID SERIAL NOT NULL PRIMARY KEY," +
                "first_name text NOT NULL," +
                "last_name text NOT NULL," +
                "email text NOT NULL," +
                "phone_number text NOT NULL," +
                "password text NOT NULL" +
                ");";
        DataBaseConnector dataBaseConnector = new DataBaseConnector();
        dataBaseConnector.updateQuery(sql);
    }
}
