package Server;

import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

class AdminTest {

    @Test
    void handleIsCookieTypeAsAccesTrue() throws Exception{
        Admin adminToTest = spy(new Admin());
        doReturn(true).when(adminToTest).isCookieTypeAsAcces(any(),any());
        HttpExchange httpExchange = mock(HttpExchange.class);
        when(httpExchange.getRequestMethod()).thenReturn("whatever");
        OutputStream os = new ByteArrayOutputStream();
        when(httpExchange.getResponseBody()).thenReturn(os);

        adminToTest.handle(httpExchange);

        verify(adminToTest, times(1)).loadJtwig("templates/admin.twig", httpExchange);
        verify(adminToTest, never()).loadLoginSite(httpExchange);
    }


    @Test
    void handleIsCookieTypeAsAccesFalse() throws Exception {
        Admin adminToTest = spy(new Admin());
        doReturn(false).when(adminToTest).isCookieTypeAsAcces(any(),any());
        doNothing().when(adminToTest).loadLoginSite(any());
        HttpExchange httpExchange = mock(HttpExchange.class);

        adminToTest.handle(httpExchange);

        verify(adminToTest, never()).loadJtwig("templates/admin.twig", httpExchange);
        verify(adminToTest, times(1)).loadLoginSite(httpExchange);
    }


    @Test
    void handleIsCookieTypeAsAccesTrueAndMethodEqualsPost() throws Exception{
        Admin adminToTest = spy(new Admin());
        doReturn(true).when(adminToTest).isCookieTypeAsAcces(any(),any());
        doNothing().when(adminToTest).logOut(any());
        HttpExchange httpExchange = mock(HttpExchange.class);
        when(httpExchange.getRequestMethod()).thenReturn("POST");
        OutputStream os = new ByteArrayOutputStream();
        when(httpExchange.getResponseBody()).thenReturn(os);

        adminToTest.handle(httpExchange);

        verify(adminToTest, times(1)).loadJtwig("templates/admin.twig", httpExchange);
        verify(adminToTest, never()).loadLoginSite(httpExchange);
        verify(adminToTest,times(1)).logOut(httpExchange);
    }
}