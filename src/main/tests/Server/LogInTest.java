package Server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import dao.SessionDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LogInTest {
    private LogIn login;
    private HttpExchange httpExchange;

    @BeforeEach
    public void setUp() throws SQLException{
        login = spy(new LogIn());
        login.sessionDAO = mock(SessionDAO.class);
        when(login.sessionDAO.getUserIdBySessionId(anyString())).thenReturn("");
        when(login.sessionDAO.getTypeBySessionId(anyString())).thenReturn("");
        httpExchange = mock(HttpExchange.class);
    }

    @Test
    public void testIfAdminPageIsLoaded() throws SQLException, IOException {
        doNothing().when(login).loadAdmin(httpExchange);
        login.loadProperUserPage(httpExchange, "admin");
        verify(login, times(1)).loadAdmin(httpExchange);
        verify(login, never()).loadStudent(httpExchange);
        verify(login, never()).loadMentor(httpExchange);
    }

    @Test
    public void testIfMentorPageIsLoaded() throws SQLException, IOException {
        doNothing().when(login).loadMentor(httpExchange);
        login.loadProperUserPage(httpExchange, "mentor");
        verify(login, never()).loadAdmin(httpExchange);
        verify(login, never()).loadStudent(httpExchange);
        verify(login, times(1)).loadMentor(httpExchange);
    }

    @Test
    public void testIfStudentPageIsLoaded() throws SQLException, IOException {
        doNothing().when(login).loadStudent(httpExchange);
        login.loadProperUserPage(httpExchange, "student");
        verify(login, never()).loadAdmin(httpExchange);
        verify(login, times(1)).loadStudent(httpExchange);
        verify(login, never()).loadMentor(httpExchange);
    }

    @Test
    public void testIfLoginSiteIsLoadedWhenThereIsNoCookie() throws IOException {
        when(httpExchange.getRequestMethod()).thenReturn("GET");
        Headers headers = mock(Headers.class);
        doNothing().when(headers).add(anyString(), anyString());
        when(headers.getFirst("Cookie")).thenReturn(null);
        when(httpExchange.getRequestHeaders()).thenReturn(headers);
        when(httpExchange.getResponseHeaders()).thenReturn(headers);
        doNothing().when(login).loadLoginSite(httpExchange);
        login.handle(httpExchange);
        verify(login, times(1)).loadLoginSite(httpExchange);
//        HttpCookie httpCookie = mock(HttpCookie.class);
//        when(httpCookie.getValue()).thenReturn("");
//        doReturn("").when(login).findCurrentCookie(httpExchange);

    }

    @Test
    public void testIfLoginSiteIsLoadedWhenThereIsNoSession() throws SQLException, IOException {
        when(httpExchange.getRequestMethod()).thenReturn("GET");
        Headers headers = mock(Headers.class);
        when(headers.getFirst("Cookie")).thenReturn("");
        when(httpExchange.getRequestHeaders()).thenReturn(headers);
        HttpCookie httpCookie = new HttpCookie("abc", "");
        List<HttpCookie> httpCookies = new ArrayList<>();
        httpCookies.add(httpCookie);
        doReturn(httpCookies).when(login).findCurrentCookie(httpExchange);
        when(login.sessionDAO.isThereSessionId(anyString())).thenReturn(false);
        doNothing().when(login).loadLoginSite(httpExchange);
        login.handle(httpExchange);
        verify(login, times(1)).loadLoginSite(httpExchange);
        verify(login, never()).loadProperUserPage(httpExchange, "");
    }

    @Test
    public void testIfUserPageIsLoadedWhenThereIsSession() throws SQLException, IOException {
        when(httpExchange.getRequestMethod()).thenReturn("GET");
        Headers headers = mock(Headers.class);
        when(headers.getFirst("Cookie")).thenReturn("");
        when(httpExchange.getRequestHeaders()).thenReturn(headers);
        HttpCookie httpCookie = new HttpCookie("abc", "");
        List<HttpCookie> httpCookies = new ArrayList<>();
        httpCookies.add(httpCookie);
        doReturn(httpCookies).when(login).findCurrentCookie(httpExchange);
        when(login.sessionDAO.isThereSessionId(anyString())).thenReturn(true);
        doNothing().when(login).loadProperUserPage(anyObject(), anyString());
        
        login.handle(httpExchange);
        verify(login, never()).loadLoginSite(httpExchange);
        verify(login, times(1)).loadProperUserPage(httpExchange, "");

    }
}