package Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataBaseConnector;
import dao.SessionDAO;
import dao.SessionDAOSQL;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class LogIn implements HttpHandler {
    SessionDAO sessionDAO = new SessionDAOSQL();
    String userId = "";
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        HttpCookie cookie;
        if(method.equals("GET")){
            System.out.println("weszlem");
            try {
            if (httpExchange.getRequestHeaders().getFirst("Cookie") == null) {
                System.out.println("weszlem2");

                cookie = new HttpCookie("sessionId", "123");
                httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
                sendResponse(httpExchange);
            }
            cookie = findCurrentCookie(httpExchange).get(0);
                System.out.println("weszlem"+cookie);

                String sesionId=cookie.getValue();
                System.out.println("weszlem3");

                if (sessionDAO.isThereSessionId(sesionId)) {
                    String type = sessionDAO.getTypeBySessionId(sesionId);
                    this.userId = sessionDAO.getUserIdBySessionId(sesionId);
                    switch (type){
                        case "admin":
                            loadAdmin(httpExchange);
                            break;
                        case"student":
                            loadStudent(httpExchange);
                            break;
                        case"mentor":
                            loadMentor(httpExchange);
                            break;
                    }
                }
                else {
                    System.out.println("WSZLEM");
                    loadLogIn(httpExchange);
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }

            sendResponse(httpExchange);
        }


    }

    private void loadLogIn(HttpExchange httpExchange) throws IOException {

    }

    private void loadMentor(HttpExchange httpExchange) throws IOException {
        httpExchange.getResponseHeaders().add("Location", "/mentor");
        httpExchange.sendResponseHeaders(302,0);
        OutputStream os = httpExchange.getResponseBody();
        os.write("".getBytes());
        os.close();
    }

    private void loadStudent(HttpExchange httpExchange) throws IOException {
        httpExchange.getResponseHeaders().add("Location", "/student");
        httpExchange.sendResponseHeaders(302,0);
        OutputStream os = httpExchange.getResponseBody();
        os.write("".getBytes());
        os.close();
    }

    private void loadAdmin(HttpExchange httpExchange) throws IOException {
        httpExchange.getResponseHeaders().add("Location", "/admin");
        httpExchange.sendResponseHeaders(302,0);
        OutputStream os = httpExchange.getResponseBody();
        os.write("".getBytes());
        os.close();
    }

    public List<HttpCookie> findCurrentCookie(HttpExchange httpExchange){
        System.out.println("weszlem do findCookie");
        String cookies = httpExchange.getRequestHeaders().getFirst("Cookie");
        return HttpCookie.parse(cookies);
    }



    public void sendResponse(HttpExchange httpExchange) throws IOException {
        httpExchange.getResponseHeaders().add("Location", "/index");
        httpExchange.sendResponseHeaders(302,0);
        OutputStream os = httpExchange.getResponseBody();
        os.write("".getBytes());
        os.close();
    }

}
