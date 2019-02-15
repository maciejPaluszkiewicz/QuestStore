package Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import dao.AdminDAOSQL;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;


import static org.mockito.Mockito.*;

import java.io.IOException;
import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AdminMentorsTest {
    private AdminMentors adminMentors;
    private HttpExchange httpExchange;

    @BeforeEach
    public void setUp() {
        adminMentors = spy(new AdminMentors());
        httpExchange = mock(HttpExchange.class);
    }

    @Test
    public void testIfCreateMentorFromDAOisCalled() {
        AdminDAOSQL adminDAOSQL = spy(new AdminDAOSQL());
        doNothing().when(adminDAOSQL).createMentor(anyString(), anyString(), anyString(), anyString(), anyString());
        Map map = mock(HashMap.class);
        when(map.size()).thenReturn(4);
        adminMentors.updateMentormodList(adminDAOSQL, map);
        verify(adminDAOSQL, times(1)).createMentor(anyString(), anyString(),
                anyString(), anyString(), anyString());
        verify(adminDAOSQL, never()).removeMentorById(anyString());
    }

    @Test
    public void testIfRemoveMentorFromDAOisCalled() {
        AdminDAOSQL adminDAOSQL = spy(new AdminDAOSQL());
        doNothing().when(adminDAOSQL).removeMentorById(anyString());
        Map map = mock(HashMap.class);
        when(map.size()).thenReturn(1);
        adminMentors.updateMentormodList(adminDAOSQL, map);
        verify(adminDAOSQL, never()).createMentor(anyString(), anyString(), anyString(), anyString(), anyString());
        verify(adminDAOSQL).removeMentorById(anyString());
    }

    @Test
    public void testIfAdminMentorSiteIsLoaded() throws SQLException, IOException {
        doReturn(true).when(adminMentors).isCookieTypeAsAcces("admin", httpExchange);
        doNothing().when(adminMentors).loadAdminMentorSite(httpExchange);
        adminMentors.loadProperSite(httpExchange);
        verify(adminMentors, times(1)).loadAdminMentorSite(httpExchange);
        verify(adminMentors, never()).loadLoginSite(httpExchange);
    }

    @Test
    public void testIfLoginSiteisLoaded() throws SQLException, IOException {
        doReturn(false).when(adminMentors).isCookieTypeAsAcces(anyString(), anyObject());
        doNothing().when(adminMentors).loadLoginSite(httpExchange);
        adminMentors.loadProperSite(httpExchange);
        verify(adminMentors, never()).loadAdminMentorSite(httpExchange);
        verify(adminMentors, times(1)).loadLoginSite(httpExchange);
    }

}