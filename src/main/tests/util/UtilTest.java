package util;

import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UtilTest {

    @Test
    void parseFormDataReturnsCorrectMap() throws Exception{
        Map<String, String> expected = new HashMap<>();
        expected.put("aa", "11");
        expected.put("bbb", "222");

        Map<String, String> result = Util.parseFormData("aa=11&bbb=222");

        assertEquals(expected, result);
    }

    @Test
    void getFormDataGetsDataFromHttpExchange() throws Exception{
        HttpExchange httpExchangeMock = mock(HttpExchange.class);
        InputStream is = new ByteArrayInputStream("someData".getBytes());
        when(httpExchangeMock.getRequestBody()).thenReturn(is);

        String result = Util.getFormData(httpExchangeMock);

        assertEquals(result, "someData");
    }
}