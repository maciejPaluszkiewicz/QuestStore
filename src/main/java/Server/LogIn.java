package Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.SessionDAO;
import dao.SessionDAOSQL;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.List;

public class LogIn implements HttpHandler {
    SessionDAO sessionDAO = new SessionDAOSQL();
    String userId = "";
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        HttpCookie cookie;
        if(method.equals("GET")){
            try {
            if (httpExchange.getRequestHeaders().getFirst("Cookie") == null) {

                cookie = new HttpCookie("sessionId", "123");
                httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
                loadLoginSite(httpExchange);
            }
            cookie = findCurrentCookie(httpExchange).get(0);

                String sesionId=cookie.getValue();

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
                    loadLoginSite(httpExchange);
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }

            loadLoginSite(httpExchange);
        }


    }

    private void sendResponse(String response, HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
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



    public void loadLoginSite(HttpExchange httpExchange) throws IOException {
        httpExchange.getResponseHeaders().add("Location", "/index");
        httpExchange.sendResponseHeaders(302,0);
        OutputStream os = httpExchange.getResponseBody();
        os.write("".getBytes());
        os.close();
    }

}
