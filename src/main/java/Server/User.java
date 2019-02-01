package Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.MentorDAOSQL;
import dao.SessionDAOSQL;
import model.Wallet;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User extends LogIn implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
//            if (isCookieTypeAsAcces("student", httpExchange)) {
//                String method = httpExchange.getRequestMethod();
//                if(method.equals("POST")) {
//                    logOut(httpExchange);
//                }
//                loadJtwig("templates/user.twig",httpExchange);
                JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/user.twig");
                JtwigModel model = JtwigModel.newModel();
                HttpCookie cookie = findCurrentCookie(httpExchange).get(0);
                String currentSesion=cookie.getValue();
                SessionDAOSQL sessionDAOSQL = new SessionDAOSQL();
                String userId = sessionDAOSQL.getUserIdBySessionId(currentSesion);
                int userIntId = Integer.parseInt(userId);
                MentorDAOSQL mentorDAOSQL = new MentorDAOSQL();
                Wallet wallet = mentorDAOSQL.showStudentsWallet(userIntId);
                model.with("wallet",wallet);
                String response = template.render(model);
                sendResponse(response,httpExchange);
//            }
//            else {
//                loadLoginSite(httpExchange);
//            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}