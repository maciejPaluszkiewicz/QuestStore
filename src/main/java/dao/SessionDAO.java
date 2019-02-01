package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SessionDAO {
    void deleteUserBySessionId(String sessionId) throws SQLException;
    String getTypeBySessionId(String sessionId) throws SQLException;
    String getUserIdBySessionId(String sessionId) throws SQLException;
    boolean isThereSessionId(String sessionId) throws SQLException;
    void addSession(String sessionId, String type, int Id);
}
