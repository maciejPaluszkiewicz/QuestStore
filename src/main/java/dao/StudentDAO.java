package dao;

import java.sql.ResultSet;
import java.util.List;

public interface StudentDAO {
    List<ResultSet> showWallet(int id);

    void buyArtifacts(int howMuch, int id_artifact, int id_student);

    void buyArtifactTogether();

    ResultSet showMyLevel(int id);
}
