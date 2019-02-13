package Server;

import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

class MentorArtifactsTest {

    @Test
    void handleIsCookieTypeAccessTrue() throws Exception{
        MentorArtifacts mentorArtifactsToTest = spy(new MentorArtifacts());
        doReturn(true).when(mentorArtifactsToTest).isCookieTypeAsAcces(any(),any());
        String result = mentorArtifactsToTest.createResponse();
        HttpExchange httpExchange = mock(HttpExchange.class);
        OutputStream os = new ByteArrayOutputStream();
        when(httpExchange.getResponseBody()).thenReturn(os);

        mentorArtifactsToTest.handle(httpExchange);

        verify(mentorArtifactsToTest, never()).loadLoginSite(httpExchange);
        verify(mentorArtifactsToTest, times(1)).sendResponse(result, httpExchange);
    }

    @Test
    void handleIsCookieTypeAccessFalse() throws Exception{
        MentorArtifacts mentorArtifactsToTest = spy(new MentorArtifacts());
        doReturn(false).when(mentorArtifactsToTest).isCookieTypeAsAcces(any(),any());
        doNothing().when(mentorArtifactsToTest).loadLoginSite(any());
        HttpExchange httpExchange = mock(HttpExchange.class);
        String result = mentorArtifactsToTest.createResponse();
        OutputStream os = new ByteArrayOutputStream();
        when(httpExchange.getResponseBody()).thenReturn(os);

        mentorArtifactsToTest.handle(httpExchange);

        verify(mentorArtifactsToTest, never()).sendResponse(result, httpExchange);
        verify(mentorArtifactsToTest, times(1)).loadLoginSite(httpExchange);
    }
}