package createDB;

import dao.DataBaseConnector;

public class CategoryTable {
    private DataBaseConnector dbConnector = new DataBaseConnector();

    public CategoryTable() {
        this.dbConnector.connect();
        creatingCategoryTable();
    }

    public void creatingCategoryTable() {
        String querry = "CREATE TABLE IF NOT EXISTS quests_category(" +
                "quest_category_id SERIAL PRIMARY KEY, " +
                "category_name TEXT, " +
                "cool_con_bonus INTEGER" +
                "); ";
        createCategory(querry);
    }

    public void createCategory(String querry) {
        this.dbConnector.updateQuery(querry);
    }
}
