package Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpCookie;
import java.sql.SQLException;

public class User extends LogIn implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            if (isCookieTypeAsAcces("student", httpExchange)) {
                String method = httpExchange.getRequestMethod();
                if(method.equals("POST")) {
                    logOut(httpExchange);
                }
                loadJtwig("templates/user.twig",httpExchange);
                HttpCookie cookie = findCurrentCookie(httpExchange).get(0);
                String currentSesion=cookie.getValue();
            }
            else {
                loadLoginSite(httpExchange);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}