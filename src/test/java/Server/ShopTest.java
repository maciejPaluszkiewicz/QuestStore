package Server;

import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static org.mockito.Mockito.*;

class ShopTest {

    @Test
    void handleIsCookieTypeAccessTrue() throws Exception{
        Shop shopToTest = spy(new Shop());
        doReturn(true).when(shopToTest).isCookieTypeAsAcces(any(),any());
        String result = shopToTest.createResponse();
        HttpExchange httpExchange = mock(HttpExchange.class);
        OutputStream os = new ByteArrayOutputStream();
        when(httpExchange.getResponseBody()).thenReturn(os);

        shopToTest.handle(httpExchange);

        verify(shopToTest, never()).loadLoginSite(httpExchange);
        verify(shopToTest, times(1)).sendResponse(result, httpExchange);
    }

    @Test
    void handleIsCookieTypeAccessFalse() throws Exception{
        Shop shopToTest = spy(new Shop());
        doReturn(false).when(shopToTest).isCookieTypeAsAcces(any(),any());
        doNothing().when(shopToTest).loadLoginSite(any());
        HttpExchange httpExchange = mock(HttpExchange.class);
        String result = shopToTest.createResponse();
        OutputStream os = new ByteArrayOutputStream();
        when(httpExchange.getResponseBody()).thenReturn(os);

        shopToTest.handle(httpExchange);

        verify(shopToTest, never()).sendResponse(result, httpExchange);
        verify(shopToTest, times(1)).loadLoginSite(httpExchange);
    }
}
