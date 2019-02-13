package Server;

import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

class MentorQuestTest {

    @Test
    void handleIsCookieTypeAccessTrue() throws Exception{
        MentorQuest mentorQuestToTest = spy(new MentorQuest());
        doReturn(true).when(mentorQuestToTest).isCookieTypeAsAcces(any(),any());
        String result = mentorQuestToTest.createResponse();
        HttpExchange httpExchange = mock(HttpExchange.class);
        OutputStream os = new ByteArrayOutputStream();
        when(httpExchange.getResponseBody()).thenReturn(os);

        mentorQuestToTest.handle(httpExchange);

        verify(mentorQuestToTest, never()).loadLoginSite(httpExchange);
        verify(mentorQuestToTest, times(1)).sendResponse(result, httpExchange);
    }

    @Test
    void handleIsCookieTypeAccessFalse() throws Exception{
        MentorQuest mentorQuestToTest = spy(new MentorQuest());
        doReturn(false).when(mentorQuestToTest).isCookieTypeAsAcces(any(),any());
        doNothing().when(mentorQuestToTest).loadLoginSite(any());
        HttpExchange httpExchange = mock(HttpExchange.class);
        String result = mentorQuestToTest.createResponse();
        OutputStream os = new ByteArrayOutputStream();
        when(httpExchange.getResponseBody()).thenReturn(os);

        mentorQuestToTest.handle(httpExchange);

        verify(mentorQuestToTest, never()).sendResponse(result, httpExchange);
        verify(mentorQuestToTest, times(1)).loadLoginSite(httpExchange);
    }
}