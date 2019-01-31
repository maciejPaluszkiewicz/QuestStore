package createDB;

import dao.DataBaseConnector;

public class SessionTable {
    private DataBaseConnector dbConnector = new DataBaseConnector();

    public SessionTable() {
        this.dbConnector.connect();
        creatingQuestTable();
    }

    public void creatingQuestTable() {
        String querry = "CREATE TABLE IF NOT EXISTS session(" +
                "session_id TEXT, " +
                "type TEXT, " +
                "user_ID INTEGER); ";
        createQuest(querry);
    }

    public void createQuest(String querry) {
        String sql = querry;
        this.dbConnector.updateQuery(sql);
    }
}
