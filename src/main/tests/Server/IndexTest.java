package Server;

import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Test;


//import org.apache.commons.io.IOUtils;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import java.io.*;

class IndexTest {

//    @Test
//    void readURLthrowsIOException() throws Exception{
//        Index index = new Index();
//        HttpExchange httpExchange = mock(HttpExchange.class);
//
//        InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream("dupa".getBytes("UTF-8")));
////        InputStreamReader isr = new InputStreamReader(IOUtils.toInputStream("dupa", "UTF-16"), "UTF-16");
//        BufferedReader br = new BufferedReader(isr);
////        when(readURL(httpExchange)).thenReturn(br);
//
//
//        assertThrows(IOException.class, ()->{
//            index.readURL(httpExchange);
//
//        });
//    }
}