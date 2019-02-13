package Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.SessionDAO;
import dao.SessionDAOSQL;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.List;

public class LogIn implements HttpHandler {
    SessionDAO sessionDAO = new SessionDAOSQL();

    String userId = "";

    public String getUserId() {
        return userId;
    }
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
                    loadLoginSite(httpExchange);
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }

            loadLoginSite(httpExchange);
        }


    }

    public void sendResponse(String response, HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public void loadMentor(HttpExchange httpExchange) throws IOException {
        httpExchange.getResponseHeaders().add("Location", "/mentor");
        httpExchange.sendResponseHeaders(302,0);
        OutputStream os = httpExchange.getResponseBody();
        os.write("".getBytes());
        os.close();
    }

    public void loadStudent(HttpExchange httpExchange) throws IOException {
        httpExchange.getResponseHeaders().add("Location", "/user");
        httpExchange.sendResponseHeaders(302,0);
        OutputStream os = httpExchange.getResponseBody();
        os.write("".getBytes());
        os.close();
    }

    public void loadAdmin(HttpExchange httpExchange) throws IOException {
        httpExchange.getResponseHeaders().add("Location", "/admin");
        httpExchange.sendResponseHeaders(302,0);
        OutputStream os = httpExchange.getResponseBody();
        os.write("".getBytes());
        os.close();
    }
    // TODO: TEST
    public List<HttpCookie> findCurrentCookie(HttpExchange httpExchange){
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
    // TODO: TEST
    public boolean isCookieTypeAsAcces(String acces, HttpExchange httpExchange) throws SQLException{
        HttpCookie cookie = findCurrentCookie(httpExchange).get(0);
        if(sessionDAO.getTypeBySessionId(cookie.getValue()).equals(acces)){
            return true;
        }
        return false;
    }

    public void logOut(HttpExchange httpExchange) throws IOException{
            HttpCookie cookie = findCurrentCookie(httpExchange).get(0);
            try {
                sessionDAO.deleteUserBySessionId(cookie.getValue());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            loadLoginSite(httpExchange);


    }

    public void loadJtwig(String pathJtwig, HttpExchange httpExchange) throws IOException{
        JtwigTemplate template = JtwigTemplate.classpathTemplate(pathJtwig);
        JtwigModel model = JtwigModel.newModel();
        String response= template.render(model);
        sendResponse(response,httpExchange);


    }

}
