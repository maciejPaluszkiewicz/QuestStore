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
            try {
            if (httpExchange.getRequestHeaders().getFirst("Cookie") == null) {
                cookie = new HttpCookie("sessionId", "123");              //Musi wstawic puste cookie na potrzeby trybu incognito,
                httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
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
                else
                    loadLogIn(httpExchange);
            }
            catch (SQLException e){
                e.printStackTrace();
            }

            sendResponse("asd",httpExchange);
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

    //    Optional<HttpCookie> findCurrentCookie(HttpExchange httpExchange){
//        httpExchange.getRequestHeaders().getFirst("Cookie");
//        List<HttpCookie> cookie = HttpCookie.parse(httpExchange.getRequestHeaders().getFirst("Cookie"));
//        return Optional.ofNullable(cookie.get(1));
//    }
    public List<HttpCookie> findCurrentCookie(HttpExchange httpExchange){
        List<String> cookies = httpExchange.getRequestHeaders().get("Cookie");
        List<HttpCookie> currentCookie = HttpCookie.parse(cookies.get(0));
        return  currentCookie;
    }



    public void sendResponse(String response, HttpExchange httpExchange) throws IOException {
        httpExchange.getResponseHeaders().add("Location", "/twig");
        httpExchange.sendResponseHeaders(302,0);
        OutputStream os = httpExchange.getResponseBody();
        os.write("".getBytes());
        os.close();
    }

}
