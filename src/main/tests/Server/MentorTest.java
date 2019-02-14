package Server;

import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

class MentorTest {

    @Mock
    private HttpExchange httpExchange;

    @Spy
    private Mentor mentor;

    @BeforeEach
    void setUp() {
        mentor = spy(new Mentor());
        httpExchange = mock(HttpExchange.class);
    }

    @Test
    public void testIsCookieTypeAsAccessFalse() throws SQLException, IOException {
        doReturn(false).when(mentor).isCookieTypeAsAcces(any(), any());

        doNothing().when(mentor).loadLoginSite(any());

        mentor.handle(httpExchange);

        verify(mentor, times(1)).loadLoginSite(any());
        verify(mentor, never()).loadJtwig("templates/mentor.twig", httpExchange);
    }

    @Test
    public void testIsCookieTypeAsAccessTrue() throws SQLException, IOException {
        doReturn(true).when(mentor).isCookieTypeAsAcces(any(), any());

        when(httpExchange.getRequestMethod()).thenReturn("SOMETHING");

        doNothing().when(mentor).loadJtwig("templates/mentor.twig", httpExchange);
        mentor.handle(httpExchange);

        verify(mentor, never()).loadLoginSite(any());
        verify(mentor, times(1)).loadJtwig("templates/mentor.twig", httpExchange);
    }

    @Test
    public void testIfGetRequestMethodReturnPost() throws SQLException, IOException {
        doReturn(true).when(mentor).isCookieTypeAsAcces(any(), any());

        when(httpExchange.getRequestMethod()).thenReturn("POST");

        doNothing().when(mentor).logOut(httpExchange);
        mentor.handle(httpExchange);

        verify(mentor, never()).loadJtwig("templates/mentor.twig", httpExchange);
        verify(mentor, never()).loadLoginSite(any());
        verify(mentor, times(1)).logOut(any());
    }
}