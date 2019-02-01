package Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.SQLException;

public class Admin extends LogIn implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            if(isCookieTypeAsAcces("admin",httpExchange)) {
                String method = httpExchange.getRequestMethod();
                if(method.equals("POST")) {
                    logOut(httpExchange);
                }
                loadJtwig("templates/admin.twig",httpExchange);
            }
            else{
                loadLoginSite(httpExchange);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}