package Server;

import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static org.mockito.Mockito.*;

class AdminCoinsTest {

    @Test
    void handleIsCookieTypeAccessTrue() throws Exception{
        AdminCoins adminCoinsToTest = spy(new AdminCoins());
        doReturn(true).when(adminCoinsToTest).isCookieTypeAsAcces(any(),any());
        String result = adminCoinsToTest.createResponse();
        HttpExchange httpExchange = mock(HttpExchange.class);
        OutputStream os = new ByteArrayOutputStream();
        when(httpExchange.getResponseBody()).thenReturn(os);

        adminCoinsToTest.handle(httpExchange);

        verify(adminCoinsToTest, never()).loadLoginSite(httpExchange);
        verify(adminCoinsToTest, times(1)).sendResponse(result, httpExchange);
    }

    @Test
    void handleIsCookieTypeAccessFalse() throws Exception{
        AdminCoins adminCoinsToTest = spy(new AdminCoins());
        doReturn(false).when(adminCoinsToTest).isCookieTypeAsAcces(any(),any());
        doNothing().when(adminCoinsToTest).loadLoginSite(any());
        HttpExchange httpExchange = mock(HttpExchange.class);
        String result = adminCoinsToTest.createResponse();
        OutputStream os = new ByteArrayOutputStream();
        when(httpExchange.getResponseBody()).thenReturn(os);

        adminCoinsToTest.handle(httpExchange);

        verify(adminCoinsToTest, never()).sendResponse(result, httpExchange);
        verify(adminCoinsToTest, times(1)).loadLoginSite(httpExchange);
    }

}