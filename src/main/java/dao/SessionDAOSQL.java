package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionDAOSQL implements SessionDAO {
    private DataBaseConnector dbConnector = new DataBaseConnector();
    @Override
    public String getTypeBySessionId(String sessionId) throws SQLException {
        String type="";
        ResultSet rs = dbConnector.query("SELECT type FROM session WHERE session_id LIKE '"+sessionId+"'");
        if(rs.next()){
            type = rs.getString(1);
        }
        return type;
    }

    @Override
    public String getUserIdBySessionId(String sessionId) throws SQLException {
        int userId = 0;
        ResultSet rs = dbConnector.query("SELECT user_id FROM session WHERE session_id LIKE '"+sessionId+"'");
        if(rs.next()){
            userId = rs.getInt(1);
        }
        return Integer.toString(userId);
    }

    @Override
    public boolean isThereSessionId(String sessionId) throws SQLException {
        boolean result;
        ResultSet rs = dbConnector.query("SELECT * FROM session WHERE session_id LIKE '"+sessionId+"'");
        if(rs.next()==false){
            result=false;
        }
        else{
            result=true;
        }
        return result;
    }
}
