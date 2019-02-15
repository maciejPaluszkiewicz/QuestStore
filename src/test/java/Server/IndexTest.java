package Server;

import com.sun.net.httpserver.HttpExchange;
import dao.SessionDAO;
import model.baseUserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class IndexTest {

    @Mock
    private HttpExchange httpExchangeMock;
    @Mock
    private baseUserData userMock;
    @Mock
    private SessionDAO sesDAOMock;

    @Spy
    private Index index;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        index = spy(new Index());
    }

    @Test
    public void testIfCookieAddedToList() {
        Index indexObject = new Index();
        List<HttpCookie> expected = new ArrayList<>();
        HttpCookie cookie = new HttpCookie("sessionId", "123456");
        expected.add(cookie);

        List<HttpCookie> actual = indexObject.addCookieSession(123456);

        assertEquals(expected, actual);
    }


    @Test
    public void testIfUrlParsedCorrect() {
        String url = "localhost:8003/index?login=postgres&password=123";
        Map<String, String> expectedHashMap = new HashMap<>();
        expectedHashMap.put("login", "postgres");
        expectedHashMap.put("password", "123");

        doReturn(expectedHashMap).when(index).parseURL(url);
        Map<String, String> actual = index.parseURL(url);

        assertEquals(expectedHashMap, actual);
    }

    @Test
    public void testIfUrlReadCorrect() throws IOException {
        String expectedUrl = "localhost:8003/index?login=postgres&password=123";

        doReturn(expectedUrl).when(index).readURL(httpExchangeMock);
        String actualUrl = index.readURL(httpExchangeMock);

        assertEquals(expectedUrl, actualUrl);
    }
}