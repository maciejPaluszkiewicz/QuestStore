package Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.sql.SQLException;

public class User extends LogIn implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            if (isCookieTypeAsAcces("student", httpExchange)) {
                JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/user.twig");
                JtwigModel model = JtwigModel.newModel();
                String response = template.render(model);
                sendResponse(response,httpExchange);
                HttpCookie cookie = findCurrentCookie(httpExchange).get(0);
                String currentSesion=cookie.getValue();
                System.out.println(currentSesion);
            }
            else {
                loadLoginSite(httpExchange);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}