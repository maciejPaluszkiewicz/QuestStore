package Server;

import com.sun.net.httpserver.HttpExchange;
import dao.DataBaseConnector;
import dao.MentorDAOSQL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

class MentorStudentsTest {

    private MentorStudents mentorStudents;
    private MentorDAOSQL mentorDAOSQL;
    private HttpExchange httpExchange;
    private DataBaseConnector dbConnector;

    @BeforeEach
    void setUp() {
        mentorStudents = spy(new MentorStudents());
        mentorDAOSQL = mock(MentorDAOSQL.class);
        httpExchange = mock(HttpExchange.class);
        dbConnector = mock(DataBaseConnector.class);
    }

    @Test
    public void testIsCookieTypeFalse() throws SQLException, IOException {
        doReturn(false).when(mentorStudents).isCookieTypeAsAcces(any(), any());

        doNothing().when(mentorStudents).loadLoginSite(any());
        mentorStudents.handle(httpExchange);

        verify(mentorDAOSQL, never()).createStudent(any(), any(), any(), any(), any(), any(), any(), any());
        verify(mentorDAOSQL, never()).removeStudentById(any());
        verify(mentorStudents, never()).sendResponse("<html>", httpExchange);
        verify(mentorStudents, times(1)).loadLoginSite(httpExchange);
    }

//    @Test
//    public void testIsCookieTypeTrue() throws SQLException, IOException {
//        doReturn(true).when(mentorStudents).isCookieTypeAsAcces(any(), any());
//        when(httpExchange.getRequestMethod()).thenReturn("GET");
////        doReturn("<html>").when(mentorStudents).renderMentorStudents(mentorDAOSQL);
//        when(mentorStudents.renderMentorStudents(mentorDAOSQL)).thenReturn();
//
//        doNothing().when(mentorStudents).sendResponse(any(), any());
//
//        mentorStudents.handle(httpExchange);
//
//        verify(mentorDAOSQL, never()).createStudent(any(), any(), any(), any(), any(), any(), any(), any());
//        verify(mentorDAOSQL, never()).removeStudentById(any());
//        verify(mentorStudents, never()).loadLoginSite(httpExchange);
//        verify(mentorStudents, times(1)).sendResponse(any(), httpExchange);
//    }


}